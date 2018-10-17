package com.hariobudiharjo.footballmatchschedule.Match


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hariobudiharjo.footballmatchschedule.R
import android.support.v4.app.FragmentPagerAdapter
import com.hariobudiharjo.footballmatchschedule.Match.MatchFragment.ViewPagerAdapter
import android.support.v4.view.ViewPager
import com.hariobudiharjo.footballmatchschedule.NextMatch.NextMatchFragment
import com.hariobudiharjo.footballmatchschedule.PrevMatch.PrevMatchFragment
import android.support.design.widget.TabLayout
import android.util.Log


class MatchFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var viewku: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewku = inflater.inflate(R.layout.fragment_match, container, false)
        viewPager =  viewku.findViewById(R.id.viewpager)
        setupViewPager(viewPager);

        tabLayout =  viewku.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        return viewku
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(fragmentManager!!)
        adapter.addFragment(PrevMatchFragment.prevMatchInstance(), "PAST")
        adapter.addFragment(NextMatchFragment.nextMatchInstance(), "NEXT")
        viewPager.adapter = adapter
    }

    companion object {
        fun matchInstance() : MatchFragment = MatchFragment()
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
