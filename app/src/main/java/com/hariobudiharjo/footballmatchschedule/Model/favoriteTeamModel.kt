package com.hariobudiharjo.footballmatchschedule.Model

data class favoriteTeamModel(
        val idTeam: Long?,
        val nameTeam: String?,
        val imageTeam: String?
) {

    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_IMAGE: String = "TEAM_IMAGE"
    }

    override fun toString(): String {
        return super.toString()
    }
}