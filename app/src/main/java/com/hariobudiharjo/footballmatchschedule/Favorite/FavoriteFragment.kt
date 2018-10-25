package com.hariobudiharjo.footballmatchschedule.Favorite


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hariobudiharjo.footballmatchschedule.FavoriteEvent.FavoriteEventFragment
import com.hariobudiharjo.footballmatchschedule.FavoriteEvent.FavoriteTeamFragment
import com.hariobudiharjo.footballmatchschedule.NextMatch.NextMatchFragment
import com.hariobudiharjo.footballmatchschedule.PrevMatch.PrevMatchFragment

import com.hariobudiharjo.footballmatchschedule.R


class FavoriteFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var viewku: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewku = inflater.inflate(R.layout.fragment_favorite2, container, false)
        viewPager = viewku.findViewById(R.id.viewpager)
        setupViewPager(viewPager);

        tabLayout = viewku.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        return viewku
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(fragmentManager!!)
        adapter.addFragment(FavoriteEventFragment.favoriteMatchInstance(), "EVENT")
        adapter.addFragment(FavoriteTeamFragment.favoriteTeamInstance(), "TEAM")
        viewPager.adapter = adapter
    }

    companion object {
        fun matchInstance(): FavoriteFragment = FavoriteFragment()
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
