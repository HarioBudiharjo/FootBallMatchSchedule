package com.hariobudiharjo.footballmatchschedule.Model

data class favoriteModel(val idEvent: Long?,
                         val homeTeam: String?,
                         val awayTeam: String?,
                         val homeScore: String?,
                         val awayScore: String?,
                         val dateEvent: String
) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val TEAM_NAME_HOME: String = "TEAM_NAME_HOME"
        const val TEAM_NAME_AWAY: String = "TEAM_NAME_AWAY"
        const val TEAM_SCORE_HOME: String = "TEAM_SCORE_HOME"
        const val TEAM_SCORE_AWAY: String = "TEAM_SCORE_AWAY"
        const val DATE_EVENT: String = "DATE_EVENT"
    }

    override fun toString(): String {
        return "favoriteModel(idEvent=$idEvent, homeTeam=$homeTeam, awayTeam=$awayTeam, homeScore=$homeScore, awayScore=$awayScore)"
    }


}