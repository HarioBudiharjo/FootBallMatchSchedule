package com.hariobudiharjo.footballmatchschedule.Match

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.hariobudiharjo.footballmatchschedule.R
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.hariobudiharjo.footballmatchschedule.NextMatch.NextMatchFragment
import com.hariobudiharjo.footballmatchschedule.PrevMatch.PrevMatchFragment
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.Toast
import org.jetbrains.anko.backgroundColorResource

class MatchFragment : Fragment() {

    private lateinit var mToolbar: Toolbar
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var viewku: View
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewku = inflater.inflate(R.layout.fragment_match, container, false)
        viewPager = viewku.findViewById(R.id.viewpager)

        mToolbar = viewku.findViewById(R.id.toolbar_match)
        setupViewPager(viewPager);

        tabLayout = viewku.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        return viewku
    }


    private fun setupViewPager(viewPager: ViewPager) {
//        val adapter = ViewPagerAdapter(fragmentManager!!)

        val adapter = fragmentManager?.let { ViewPagerAdapter(it) }
        val prev = PrevMatchFragment.prevMatchInstance()
        adapter?.addFragment(prev, "PAST")
        val next = NextMatchFragment.nextMatchInstance()
        adapter?.addFragment(next, "NEXT")
        viewPager?.adapter = adapter
    }

    companion object {
        fun matchInstance(): MatchFragment = MatchFragment()
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
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
