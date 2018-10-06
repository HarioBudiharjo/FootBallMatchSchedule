package com.hariobudiharjo.footballmatchschedule.Activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.ClipData
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Database.database
import com.hariobudiharjo.footballmatchschedule.Model.favoriteModel
import com.hariobudiharjo.footballmatchschedule.Model.matchModel
import com.hariobudiharjo.footballmatchschedule.Model.matchResponse
import com.hariobudiharjo.footballmatchschedule.Model.teamResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.R
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import org.jetbrains.anko.db.delete

class DetailActivity : AppCompatActivity() {

    //    https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=576526

    lateinit var imageHome: ImageView
    lateinit var imageAway: ImageView
    lateinit var data: matchResponse
    lateinit var idevent: String
    var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
//        api key
//        4012921

        setSupportActionBar(toolbar)

        val request = ApiMatch()
        val request2 = ApiMatch()
        val gson = Gson()

        idevent = intent.getStringExtra("id")

        isFavorite = cekFavorite()
        Log.d("DEBUG", "favorite $isFavorite")

        imageHome = findViewById(R.id.iv_club_home)
        imageAway = findViewById(R.id.iv_club_away)

        var progress = ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show()

        doAsync {
            data = gson.fromJson(request
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

                        tv_def_home.text = data.events[0].strHomeLineupDefense?.replace(";", "\n")
                        tv_def_away.text = data.events[0].strAwayLineupDefense?.replace(";", "\n")

                        tv_mid_home.text = data.events[0].strHomeLineupMidfield?.replace(";", "\n")
                        tv_mid_away.text = data.events[0].strAwayLineupMidfield?.replace(";", "\n")

                        tv_forward_home.text = data.events[0].strHomeLineupForward?.replace(";", "\n")
                        tv_forward_away.text = data.events[0].strAwayLineupForward?.replace(";", "\n")

                        tv_sub_home.text = data.events[0].strHomeLineupSubstitutes?.replace(";", "\n")
                        tv_sub_away.text = data.events[0].strAwayLineupSubstitutes?.replace(";", "\n")

                        progress.dismiss()
                    }

//
                    Log.d("TEAM", team.teams[0].strTeamBadge)
                    Log.d("TEAM", team2.teams[0].strTeamBadge)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        if (isFavorite) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        item.icon = getDrawable(R.drawable.ic_favorite_black_home_24dp)
            menu.getItem(0).setIcon(R.drawable.ic_favorite_black_24dp)
//                    }
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_border_black_24dp)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_favorite -> {
                klikFavorite()
                Log.d("DEBUG", isFavorite.toString())
                if (isFavorite) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        item.icon = getDrawable(R.drawable.ic_favorite_black_home_24dp)
                    item.icon = ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_black_24dp)
//                    }
                } else {
                    item.icon = ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_border_black_24dp)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun klikFavorite() {
        if (isFavorite)
            deleteFromFavorite()
        else
            addToFavorite()
    }

    private fun cekFavorite(): Boolean {
        var balikan = false
        baseContext.database.use {
            select(favoriteModel.TABLE_FAVORITE)
                    .whereArgs("(ID_ = {idevent})",
                            "idevent" to idevent).exec {
                        //do something with your result
//                        return@exec this.columnCount > 0
                        balikan = this.count > 0
                        Log.d("DEBUG", "Balikan $balikan")
                    }

        }
        return balikan

    }

    private fun addToFavorite() {
        try {
            baseContext.database.use {
                insert(favoriteModel.TABLE_FAVORITE,
                        favoriteModel.ID to idevent,
                        favoriteModel.TEAM_NAME_HOME to data.events[0].homeTeam,
                        favoriteModel.TEAM_NAME_AWAY to data.events[0].awayTeam,
                        favoriteModel.TEAM_SCORE_HOME to data.events[0].homeScore,
                        favoriteModel.TEAM_SCORE_AWAY to data.events[0].awayScore,
                        favoriteModel.DATE_EVENT to data.events[0].dateEvent
                )
                isFavorite = true
                Log.d("DEBUG", "SUKSES FAVORITE")
            }
        } catch (e: SQLiteConstraintException) {
            Log.d("DEBUG", e.message)
        }
    }

    private fun deleteFromFavorite() {
        try {
            baseContext.database.use {
                delete(favoriteModel.TABLE_FAVORITE, "(ID_ = {idevent})",
                        "idevent" to idevent)
                isFavorite = false
                Log.d("DEBUG", "SUKSES DELETE")
            }
        } catch (e: SQLiteConstraintException) {
            Log.d("DEBUG", e.message)
        }
    }

}
