package com.theanhdev97.truyenhot.Presenter

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.theanhdev97.truyenhot.Adapter.ChaptersAdapter
import com.theanhdev97.truyenhot.Adapter.TruyenByTypeAdapter
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.Interface.ListChaptersInterface
import com.theanhdev97.truyenhot.Utils.Constants
import com.theanhdev97.truyenhot.View.ChapterActivity
import com.theanhdev97.truyenhot.View.ListChaptersActivity
import kotlinx.android.synthetic.main.activity_list_chapters.*

/**
 * Created by DELL on 17/07/2017.
 */
class ListChaptersPresenter(activity: ListChaptersActivity, truyen: Truyen) :
        ListChaptersInterface, ChaptersAdapter.MyClickListener {

    var mActivity: ListChaptersActivity? = null
    var mTruyen: Truyen? = null
    var mListChapters: ArrayList<Chapter>? = null
    var mListChaptersAdapters: ChaptersAdapter? = null

    init {
        this.mActivity = activity
        this.mTruyen = truyen

        setListChaptersToUI()
    }

    override fun setListChaptersToUI() {
        mActivity!!.runOnUiThread {
            mListChapters = ArrayList<Chapter>()
            mListChapters!!.addAll(mTruyen!!.listChapter!!)
            mActivity!!.rclv_list_chapters!!.layoutManager = LinearLayoutManager(mActivity) as RecyclerView.LayoutManager?
            mListChaptersAdapters = ChaptersAdapter(mActivity!!, mListChapters!!)
            mListChaptersAdapters!!.setOnClickListener(this)
            mActivity!!.rclv_list_chapters!!.adapter = mListChaptersAdapters
        }
    }

    override fun OnItemClickListener(position: Int, view: View) {
        var intent = Intent(mActivity, ChapterActivity::class.java)
        var bundle = Bundle()
        bundle.putSerializable(Constants.DATA_INTENT, mTruyen)
        bundle.putInt(Constants.CURRENT_CHAPTER_POSITION_INTENT, position)
        Log.d(Constants.TAG,"POSITION : $position")
        intent.putExtra(Constants.OPEN_CHAPTER_INTENT, bundle)
        mActivity!!.startActivity(intent)
    }
}
