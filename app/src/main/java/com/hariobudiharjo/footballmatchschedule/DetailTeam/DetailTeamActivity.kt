package com.hariobudiharjo.footballmatchschedule.DetailTeam

import android.app.ProgressDialog
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Database.database
import com.hariobudiharjo.footballmatchschedule.DetailTeam.OverviewTeam.OverviewFragment
import com.hariobudiharjo.footballmatchschedule.DetailTeam.PlayerTeam.PlayerTeamFragment
import com.hariobudiharjo.footballmatchschedule.Model.favoriteTeamModel
import com.hariobudiharjo.footballmatchschedule.Model.teamModel
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.R

import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.content_detail_team.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showDetail(dataku: MutableList<teamModel>) {
        teams.addAll(dataku)
        data = dataku

        Log.d("DEBUG Detail Team", teams.toString())
        Glide.with(this@DetailTeamActivity).load(teams[0].strTeamBadge).into(ivClubDetailTeam)
        tvNamaDetailTeam.text = teams[0].strTeam
        tvTahunDetailTeam.text = teams[0].intFormedYear
        tvStadiunDetailTeam.text = teams[0].strStadium
        setupViewPager(viewPager, teams[0].strDescriptionEN.toString(), teams[0].idTeam.toString());
    }

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private var teams: MutableList<teamModel> = mutableListOf()
    lateinit var presenter: DetailTeamPresenter
    lateinit var progress: ProgressDialog
    private var isFavorite = false
    private lateinit var idteam: String
    private lateinit var data: MutableList<teamModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        setSupportActionBar(toolbar)

        idteam = intent.getStringExtra("id")

        viewPager = findViewById(R.id.viewpager)

        tabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        val request = ApiMatch()
        val gson = Gson()

        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)

        presenter = DetailTeamPresenter(this, request, gson)
        presenter.getTeamDetail(idteam)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        isFavorite = cekFavorite()
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
                    Toast.makeText(this, "Added to favorite", Toast.LENGTH_LONG).show()
                } else {
                    item.icon = ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_border_black_24dp)
                    Toast.makeText(this, "Delete from favorite", Toast.LENGTH_LONG).show()
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
            select(favoriteTeamModel.TABLE_FAVORITE_TEAM)
                    .whereArgs("(ID_ = {idevent})",
                            "idevent" to idteam).exec {
                        balikan = this.count > 0
                        Log.d("DEBUG", "Balikan $balikan")
                    }

        }
        return balikan
    }

    private fun addToFavorite() {
        try {
            baseContext.database.use {
                insert(favoriteTeamModel.TABLE_FAVORITE_TEAM,
                        favoriteTeamModel.ID to idteam,
                        favoriteTeamModel.TEAM_NAME to data[0].strTeam,
                        favoriteTeamModel.TEAM_IMAGE to data[0].strTeamBadge
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
                delete(favoriteTeamModel.TABLE_FAVORITE_TEAM, "(ID_ = {idevent})",
                        "idevent" to idteam)
                isFavorite = false
                Log.d("DEBUG", "SUKSES DELETE")
            }
        } catch (e: SQLiteConstraintException) {
            Log.d("DEBUG", e.message)
        }
    }

    private fun setupViewPager(viewPager: ViewPager, overView: String, idTeam: String) {

        val bundleOverView = Bundle()
        bundleOverView.putString("overview", overView)

        val bundleTeam = Bundle()
        bundleTeam.putString("idteam", idTeam)

        val adapter = ViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(OverviewFragment(), "Overview")
//        adapter.addFragment(PlayerTeamFragment(), "Player")

        val overviewFragment = OverviewFragment()
        val playerTeamFragment = PlayerTeamFragment()

        overviewFragment.arguments = bundleOverView
        playerTeamFragment.arguments = bundleTeam

        adapter.addFragment(overviewFragment, "Overview")
        adapter.addFragment(playerTeamFragment, "Player")
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = arrayListOf<Fragment>()
        private val mFragmentTitleList = arrayListOf<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList.get(position)
        }
    }
}
