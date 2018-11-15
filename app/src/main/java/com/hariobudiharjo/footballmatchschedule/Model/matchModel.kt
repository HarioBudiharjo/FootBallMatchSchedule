package com.hariobudiharjo.footballmatchschedule.Model

import com.google.gson.annotations.SerializedName

data class matchModel(
        val idEvent: String?,
        @SerializedName("strHomeTeam")
        val homeTeam: String?,
        @SerializedName("strAwayTeam")
        val awayTeam: String?,
        val dateEvent: String?,
        @SerializedName("intHomeScore")
        val homeScore: String? = "-",
        @SerializedName("intAwayScore")
        val awayScore: String? = "-",
        val strTime: String?,
        val idHomeTeam: String?,
        val idAwayTeam: String?,
        val strHomeRedCards: String?,
        val strHomeYellowCards: String?,
        val strHomeLineupGoalkeeper: String? = "-",
        val strHomeLineupDefense: String? = "-",
        val strHomeLineupMidfield: String? = "-",
        val strHomeLineupSubstitutes: String? = "-",
        val strHomeLineupForward: String? = "-",
        val strAwayRedCards: String?,
        val strAwayYellowCards: String?,
        val strAwayLineupGoalkeeper: String? = "-",
        val strAwayLineupDefense: String? = "-",
        val strAwayLineupMidfield: String? = "-",
        val strAwayLineupForward: String? = "-",
        val strAwayLineupSubstitutes: String? = "-",
        val intHomeShots: String? = "-",
        val intAwayShots: String? = "-"
)
//lineup (Goal keeper, Defense,Midfield,Forward,Substitutes)
//goal
//shot