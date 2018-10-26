package com.hariobudiharjo.footballmatchschedule.DetailPlayer

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.playerModel
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.R

import kotlinx.android.synthetic.main.activity_detail_player.*
import kotlinx.android.synthetic.main.content_detail_player.*

class DetailPlayerActivity : AppCompatActivity(), DetailPlayerView {

    private var matchs: MutableList<playerModel> = mutableListOf()
    lateinit var progress: ProgressDialog
    lateinit var presenter: DetailPlayerPresenter

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showDetail(data: MutableList<playerModel>) {
        Log.d("DEBUGDETAIL", data.toString())
        Glide.with(this@DetailPlayerActivity).load(data[0].strFanart1).into(ivFanart)
        tvWeight.text = data[0].strWeight
        tvHeight.text = data[0].strHeight
        tvPosition.text = data[0].strPosition
        tvDescription.text = data[0].strDescriptionEN

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        setSupportActionBar(toolbar)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        val idplayer = intent.getStringExtra("id")
        val request = ApiMatch()
        val gson = Gson()

        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)

        presenter = DetailPlayerPresenter(this, request, gson)
        presenter.getPlayerList(idplayer)
    }

}
