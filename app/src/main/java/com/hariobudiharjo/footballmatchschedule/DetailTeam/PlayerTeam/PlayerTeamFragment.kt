package com.hariobudiharjo.footballmatchschedule.DetailTeam.PlayerTeam


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hariobudiharjo.footballmatchschedule.R

class PlayerTeamFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewku = inflater.inflate(R.layout.fragment_player_team, container, false)
        return viewku
    }


}
