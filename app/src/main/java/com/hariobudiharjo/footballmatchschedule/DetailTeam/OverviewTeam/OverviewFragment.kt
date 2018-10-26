package com.hariobudiharjo.footballmatchschedule.DetailTeam.OverviewTeam


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.hariobudiharjo.footballmatchschedule.R

class OverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewku = inflater.inflate(R.layout.fragment_overview, container, false)

        val strtext = arguments!!.getString("overview")
        val tvOverview = viewku.findViewById<TextView>(R.id.tvOverview)
        tvOverview.text = strtext


        Log.d("OVERVIEW", strtext)

        return viewku
    }


}
