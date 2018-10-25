package com.hariobudiharjo.footballmatchschedule.DetailTeam

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.DetailTeam.OverviewTeam.OverviewFragment
import com.hariobudiharjo.footballmatchschedule.DetailTeam.PlayerTeam.PlayerTeamFragment
import com.hariobudiharjo.footballmatchschedule.Model.teamModel
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.R

import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.content_detail_team.*

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    override fun showLoading() {
        Log.d("DEBUG Detail Team", "SHOW")
    }

    override fun hideLoading() {
        Log.d("DEBUG Detail Team", "DISMISS")
    }

    override fun showDetail(data: MutableList<teamModel>) {
        teams.addAll(data)
        Log.d("DEBUG Detail Team", teams.toString())


        Glide.with(this@DetailTeamActivity).load(teams[0].strTeamBadge).into(ivClubDetailTeam)
        tvNamaDetailTeam.text = teams[0].strTeam
        tvTahunDetailTeam.text = teams[0].intFormedYear
        tvStadiunDetailTeam.text = teams[0].strStadium
    }

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private var teams: MutableList<teamModel> = mutableListOf()
    lateinit var presenter: DetailTeamPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        setSupportActionBar(toolbar)

        val idTeam = intent.getStringExtra("id")

        viewPager = findViewById(R.id.viewpager)
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        val request = ApiMatch()
        val gson = Gson()

        presenter = DetailTeamPresenter(this, request, gson)
        presenter.getTeamDetail(idTeam)
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OverviewFragment(), "Overview")
        adapter.addFragment(PlayerTeamFragment(), "Player")
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
