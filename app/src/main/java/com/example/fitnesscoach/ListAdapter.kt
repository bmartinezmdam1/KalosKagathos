package com.example.customlistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fitnesscoach.R

class ListAdapter(context: Context, dataArrayList: ArrayList<ListData>) :
    ArrayAdapter<ListData>(context, R.layout.list_item, dataArrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listData = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val listImage = convertView!!.findViewById<ImageView>(R.id.listImage)
        val listName = convertView.findViewById<TextView>(R.id.listName)
        val listTime = convertView.findViewById<TextView>(R.id.listTime)

        listImage.setImageResource(listData?.image ?: R.drawable.principiante)
        listName.text = listData?.name
        listTime.text = listData?.time

        return convertView
    }
}
