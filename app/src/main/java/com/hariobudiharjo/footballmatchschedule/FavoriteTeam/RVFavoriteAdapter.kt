package com.hariobudiharjo.footballmatchschedule.FavoriteEvent

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hariobudiharjo.footballmatchschedule.DetailMatch.DetailActivity
import com.hariobudiharjo.footballmatchschedule.Model.favoriteModel
import com.hariobudiharjo.footballmatchschedule.R

class RVFavoriteTeamAdapter(var context: Context, var matchs: List<favoriteModel>) : RecyclerView.Adapter<RVFavoriteTeamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_list_schedule_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matchs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(matchs[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(data: favoriteModel) {

            val tv_tanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
            val tv_score_home: TextView = itemView.findViewById(R.id.tv_score_home)
            val tv_club_home: TextView = itemView.findViewById(R.id.tv_club_home)
            val tv_score_away: TextView = itemView.findViewById(R.id.tv_score_away)
            val tv_club_away: TextView = itemView.findViewById(R.id.tv_club_away)

            tv_tanggal.text = data.dateEvent
            tv_score_home.text = data.homeScore
            tv_club_home.text = data.homeTeam
            tv_score_away.text = data.awayScore
            tv_club_away.text = data.awayTeam

            itemView.setOnClickListener({
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("id", data.idEvent.toString())
                Log.d("DEBUG", "ID EVENT ${data.idEvent}")
                it.context.startActivity(intent)
            })
        }
    }
}