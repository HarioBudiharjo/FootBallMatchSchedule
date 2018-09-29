package com.hariobudiharjo.footballmatchschedule.Activity

import android.app.Activity
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.matchResponse
import com.hariobudiharjo.footballmatchschedule.Model.teamResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.R
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailActivity : AppCompatActivity() {

    //    https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=576526

    lateinit var imageHome : ImageView
    lateinit var imageAway : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
//        api key
//        4012921

        val request = ApiMatch()
        val request2 = ApiMatch()
        val gson = Gson()

        val idevent = intent.getStringExtra("id")

        imageHome = findViewById(R.id.iv_club_home)
        imageAway = findViewById(R.id.iv_club_away)

        var progress = ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show()

        doAsync {
            val data = gson.fromJson(request
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=${idevent}"),
                    matchResponse::class.java)

            Log.d("DETAIL", data.toString())

            uiThread {
                doAsync {
                    val team = gson.fromJson(request2
                            .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=${data.events[0].idHomeTeam}"),
                            teamResponse::class.java)

                    val team2 = gson.fromJson(request2
                            .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=${data.events[0].idAwayTeam}"),
                            teamResponse::class.java)
                    uiThread {
                        Glide.with(this@DetailActivity).load(team.teams[0].strTeamBadge).into(imageHome)
                        Glide.with(this@DetailActivity).load(team2.teams[0].strTeamBadge).into(imageAway)
                        tv_score_home.text = data.events[0].homeScore
                        tv_score_away.text = data.events[0].awayScore
                        tv_club_home.text = data.events[0].homeTeam
                        tv_club_away.text = data.events[0].awayTeam

                        tv_goal_home.text = data.events[0].homeScore
                        tv_goal_away.text = data.events[0].awayScore

                        tv_shot_home.text = data.events[0].intHomeShots
                        tv_shot_away.text = data.events[0].intAwayShots

                        tv_gk_home.text = data.events[0].strHomeLineupGoalkeeper
                        tv_gk_away.text = data.events[0].strAwayLineupGoalkeeper

                        tv_def_home.text = data.events[0].strHomeLineupDefense?.replace(";","\n")
                        tv_def_away.text = data.events[0].strAwayLineupDefense?.replace(";","\n")

                        tv_mid_home.text = data.events[0].strHomeLineupMidfield?.replace(";","\n")
                        tv_mid_away.text = data.events[0].strAwayLineupMidfield?.replace(";","\n")

                        tv_forward_home.text = data.events[0].strHomeLineupForward?.replace(";","\n")
                        tv_forward_away.text = data.events[0].strAwayLineupForward?.replace(";","\n")

                        tv_sub_home.text = data.events[0].strHomeLineupSubstitutes?.replace(";","\n")
                        tv_sub_away.text = data.events[0].strAwayLineupSubstitutes?.replace(";","\n")

                        progress.dismiss()
                    }

//
                    Log.d("TEAM", team.teams[0].strTeamBadge)
                    Log.d("TEAM", team2.teams[0].strTeamBadge)
                }
            }
        }
    }
}
