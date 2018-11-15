package com.hariobudiharjo.footballmatchschedule.DetailMatch

import android.app.ProgressDialog
import android.database.sqlite.SQLiteConstraintException
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
import com.hariobudiharjo.footballmatchschedule.Model.matchResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.R
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var imageHome: ImageView
    private lateinit var imageAway: ImageView
    private lateinit var data: matchResponse
    private lateinit var idevent: String
    private lateinit var presenter: DetailPresenter
    lateinit var progress: ProgressDialog
    private var isFavorite = false

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showDetail(dataku: matchResponse, badgeHome: String, badgeAway: String) {
        Glide.with(this@DetailActivity).load(badgeHome).into(imageHome)
        Glide.with(this@DetailActivity).load(badgeAway).into(imageAway)
        tv_score_home.text = dataku.events[0].homeScore
        tv_score_away.text = dataku.events[0].awayScore
        tv_club_home.text = dataku.events[0].homeTeam
        tv_club_away.text = dataku.events[0].awayTeam

        tv_goal_home.text = dataku.events[0].homeScore
        tv_goal_away.text = dataku.events[0].awayScore

        tv_shot_home.text = dataku.events[0].intHomeShots
        tv_shot_away.text = dataku.events[0].intAwayShots

        tv_gk_home.text = dataku.events[0].strHomeLineupGoalkeeper
        tv_gk_away.text = dataku.events[0].strAwayLineupGoalkeeper

        tv_def_home.text = dataku.events[0].strHomeLineupDefense?.replace(";", "\n")
        tv_def_away.text = dataku.events[0].strAwayLineupDefense?.replace(";", "\n")

        tv_mid_home.text = dataku.events[0].strHomeLineupMidfield?.replace(";", "\n")
        tv_mid_away.text = dataku.events[0].strAwayLineupMidfield?.replace(";", "\n")

        tv_forward_home.text = dataku.events[0].strHomeLineupForward?.replace(";", "\n")
        tv_forward_away.text = dataku.events[0].strAwayLineupForward?.replace(";", "\n")

        tv_sub_home.text = dataku.events[0].strHomeLineupSubstitutes?.replace(";", "\n")
        tv_sub_away.text = dataku.events[0].strAwayLineupSubstitutes?.replace(";", "\n")
        data = dataku

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val request = ApiMatch()
        val gson = Gson()

        idevent = intent.getStringExtra("id")

        isFavorite = cekFavorite()
        Log.d("DEBUG", "favorite $isFavorite")

        imageHome = findViewById(R.id.iv_club_home)
        imageAway = findViewById(R.id.iv_club_away)

        progress = ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        presenter = DetailPresenter(this, request, gson)
        presenter.getTeamDetail(idevent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
        return when (item.itemId) {
            R.id.action_favorite_ab -> {
                klikFavorite()
                Log.d("DEBUG", isFavorite.toString())
                if (isFavorite) {
                    item.icon = ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_black_24dp)
                    Toast.makeText(this,"Added to favorite",Toast.LENGTH_LONG).show()
                } else {
                    item.icon = ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_border_black_24dp)
                    Toast.makeText(this,"Delete from favorite",Toast.LENGTH_LONG).show()
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
