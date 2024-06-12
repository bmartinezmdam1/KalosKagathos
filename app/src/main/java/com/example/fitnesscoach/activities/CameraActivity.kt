package com.example.fitnesscoach.activities

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fitnesscoach.databinding.CameraBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    // Enlace a la vista de binding
    private lateinit var viewBinding: CameraBinding
    // Objeto de captura de imagen
    private var imageCapture: ImageCapture? = null
    // ExecutorService para operaciones de cámara en segundo plano
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = CameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (allPermissionsGranted()) {
            // Inicia la cámara si todos los permisos están otorgados
            startCamera()
        } else {
            // Solicita permisos si no están otorgados
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        // Listener para el botón de captura de imagen
        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }

        // Inicializa el ExecutorService
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {
        // Obtiene la instancia de ImageCapture
        val imageCapture = imageCapture ?: return

        // Formatea el nombre de la imagen usando la fecha y hora actual
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        // Configura los valores de contenido para la imagen
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Configura las opciones de salida para la captura de imagen
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // Captura la imagen
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    // Maneja errores de captura de imagen
                    Log.e(TAG, "Error al capturar la foto: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    // Muestra un mensaje de éxito
                    val msg = "Captura de foto exitosa: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)

                    // Envía la URI de la imagen de vuelta a la actividad anterior
                    val intent = Intent().apply {
                        putExtra("image_uri", output.savedUri.toString())
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        )
    }

    private fun startCamera() {
        // Obtiene el futuro proveedor de cámara
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        // Añade un listener para cuando el proveedor esté listo
        cameraProviderFuture.addListener({
            // Obtiene el proveedor de cámara
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Construye la previsualización
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            // Configura la captura de imagen
            imageCapture = ImageCapture.Builder().build()

            // Selecciona la cámara trasera por defecto
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Desenlaza todos los casos de uso y enlaza la cámara con los nuevos casos de uso
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Error al enlazar casos de uso", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    // Verifica si todos los permisos necesarios están otorgados
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        // Apaga el ExecutorService
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                // Inicia la cámara si los permisos fueron otorgados
                startCamera()
            } else {
                // Informa al usuario si los permisos no están otorgados
                Toast.makeText(this,
                    "Permisos no otorgados por el usuario.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        // Tag para el logging
        private const val TAG = "CameraXApp"
        // Formato de nombre de archivo
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        // Código de solicitud de permisos
        private const val REQUEST_CODE_PERMISSIONS = 10
        // Lista de permisos requeridos
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
