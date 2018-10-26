package com.hariobudiharjo.footballmatchschedule.DetailTeam.PlayerTeam

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hariobudiharjo.footballmatchschedule.DetailMatch.DetailActivity
import com.hariobudiharjo.footballmatchschedule.DetailPlayer.DetailPlayerActivity
import com.hariobudiharjo.footballmatchschedule.DetailTeam.DetailTeamActivity
import com.hariobudiharjo.footballmatchschedule.Model.matchModel
import com.hariobudiharjo.footballmatchschedule.Model.playerModel
import com.hariobudiharjo.footballmatchschedule.Model.teamModel
import com.hariobudiharjo.footballmatchschedule.R

class RVPlayerAdapter(var context: Context, var matchs: List<playerModel>) : RecyclerView.Adapter<RVPlayerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_list_player_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matchs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(matchs[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(data: playerModel) {

            val iv_player: ImageView = itemView.findViewById(R.id.iv_player)
            val tv_player: TextView = itemView.findViewById(R.id.tv_player)
            val tv_player_position: TextView = itemView.findViewById(R.id.tv_player_position)

            tv_player.text = data.strPlayer
            tv_player_position.text = data.strPosition
            Glide.with(itemView.context).load(data.strCutout).into(iv_player)

            itemView.setOnClickListener({
                val intent = Intent(it.context, DetailPlayerActivity::class.java)
                intent.putExtra("id", data.idPlayer)
                it.context.startActivity(intent)
            })
        }
    }
}