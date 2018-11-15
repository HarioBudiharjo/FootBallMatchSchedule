package com.hariobudiharjo.footballmatchschedule.FavoriteEvent

import android.content.Context
import android.content.Intent
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hariobudiharjo.footballmatchschedule.DetailMatch.DetailActivity
import com.hariobudiharjo.footballmatchschedule.DetailTeam.DetailTeamActivity
import com.hariobudiharjo.footballmatchschedule.Model.favoriteModel
import com.hariobudiharjo.footballmatchschedule.Model.favoriteTeamModel
import com.hariobudiharjo.footballmatchschedule.R

class RVFavoriteTeamAdapter(var context: Context, var matchs: List<favoriteTeamModel>) : RecyclerView.Adapter<RVFavoriteTeamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_list_team_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matchs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(matchs[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(data: favoriteTeamModel) {

            val iv_club: ImageView = itemView.findViewById(R.id.iv_club)
            val tv_club: TextView = itemView.findViewById(R.id.tv_club)

            tv_club.text = data.nameTeam
            Glide.with(itemView.context).load(data.imageTeam).into(iv_club)

            itemView.setOnClickListener({
                val intent = Intent(it.context, DetailTeamActivity::class.java)
                intent.putExtra("id", data.idTeam.toString())
                it.context.startActivity(intent)
            })
        }
    }
}