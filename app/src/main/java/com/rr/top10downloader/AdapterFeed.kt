package com.rr.top10downloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import android.widget.ArrayAdapter
import java.security.KeyStore

class AdapterFeed (context: Context, private val resource:Int, private val applications: List<EntryFeed>): ArrayAdapter<EntryFeed>(context, resource){
    private val TAG = "AdapterFeed"
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int{
        Log.d(TAG, "getCount Called")
        return applications.size
     }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val view: View
               if (convertView == null){
                   Log.d(TAG, "view was created since it was null")
                   view = inflater.inflate(resource, parent, false)
               }else{
                   Log.d(TAG, "getView has provided a convert view")
                   view = convertView
               }
        val tvName : TextView = view.findViewById(R.id.tvName)
        val tvArtist : TextView = view.findViewById(R.id.tvArtist)
        val tvSummary : TextView = view.findViewById(R.id.tvSummary)

        val current = applications[position]
        tvName.text = current.name
        tvArtist.text = current.artist
        //
        // tvSummary.text = current.summury
        return view
    }
}