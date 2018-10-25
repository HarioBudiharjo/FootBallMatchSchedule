package com.hariobudiharjo.footballmatchschedule.Home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.hariobudiharjo.footballmatchschedule.Favorite.FavoriteFragment
import com.hariobudiharjo.footballmatchschedule.FavoriteEvent.FavoriteEventFragment
import com.hariobudiharjo.footballmatchschedule.Match.MatchFragment
import com.hariobudiharjo.footballmatchschedule.R
import com.hariobudiharjo.footballmatchschedule.Team.TeamFragment
import kotlinx.android.synthetic.main.activity_utama.*

class UtamaActivity : AppCompatActivity() {

//    https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328
//    https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328

    lateinit var currentFragment: Fragment

    lateinit var ft: FragmentTransaction

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
//                currentFragment = PrevMatchFragment()
                currentFragment = MatchFragment.matchInstance()
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fl_main, currentFragment)
                ft.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                currentFragment = TeamFragment()
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fl_main, currentFragment)
                ft.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                currentFragment = FavoriteFragment()
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fl_main, currentFragment)
                ft.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utama)

        currentFragment = MatchFragment()

        ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_main, currentFragment)
        ft.commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
