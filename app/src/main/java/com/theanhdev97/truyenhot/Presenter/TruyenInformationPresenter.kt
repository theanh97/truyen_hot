package com.theanhdev97.truyenhot.Presenter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.theanhdev97.truyenhot.Adapter.TruyenByTypeAdapter
import com.theanhdev97.truyenhot.Asynctask.DownLoadTruyenAsycntask
import com.theanhdev97.truyenhot.Asynctask.GetListChapterAsynctask
import com.theanhdev97.truyenhot.Asynctask.GetListTruyenByTypeAsynctask
import com.theanhdev97.truyenhot.Asynctask.GetTruyenInformationAsynctask
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.Interface.TruyenInformationInterface
import com.theanhdev97.truyenhot.R.id.btn_down_load
import com.theanhdev97.truyenhot.Utils.Constants
import com.theanhdev97.truyenhot.Utils.InternalStorage
import com.theanhdev97.truyenhot.View.ListChaptersActivity
import com.theanhdev97.truyenhot.View.ListTruyenByTypeActivity
import com.theanhdev97.truyenhot.View.TruyenInformationActivity
import kotlinx.android.synthetic.main.activity_list_truyen_by_type.*
import kotlinx.android.synthetic.main.activity_truyen_information.*

/**
 * Created by DELL on 09/07/2017.
 */
class TruyenInformationPresenter(activity: TruyenInformationActivity, truyen: Truyen) : TruyenInformationInterface {

    var mActivity: TruyenInformationActivity? = null
    var mTruyen: Truyen? = null

    init {
        mActivity = activity
        mTruyen = truyen

        GetListChapterAsynctask(this).execute(mTruyen!!.link)
        GetTruyenInformationAsynctask(this).execute(mTruyen!!.link)
        registerEvent()
    }

    override fun onDownLoadTruyenSuccess() {
        saveTruyenToInternalStorage(mTruyen!!)
//        var lastChapter = mTruyen!!.listChapter!![mTruyen!!.listChapter!!.size - 1]
//        Toast.makeText(mActivity, "Content Last Chapters : ${lastChapter.content}", Toast
//                .LENGTH_LONG).show()
    }

    override fun onDownLoadTruyenFailure() {
        Toast.makeText(mActivity, "DownLoad Failure", Toast.LENGTH_LONG).show()
    }

    override fun onLoadTruyenInformationSuccss(truyen: Truyen) {
        setUI(truyen)
    }

    override fun onLoadListChapterSuccess(listChapter: MutableList<MutableMap<Int, ArrayList<Chapter>>>) {
        initListChapters(listChapter)
    }

    override fun onLoadUIFailure() {
        Toast.makeText(mActivity, "Failure", Toast.LENGTH_LONG).show()
    }


    override fun registerEvent() {
        mActivity!!.btn_down_load.setOnClickListener(mActivity)
        mActivity!!.btn_danh_sach_chuong.setOnClickListener(mActivity)
    }

    override fun setUI(truyen: Truyen) {
        Log.d(Constants.TAG, "Set UI")
        initTruyen(truyen)
        mActivity!!.repairUI(mTruyen!!)
    }

    override fun saveTruyenToInternalStorage(truyen: Truyen) {
        mActivity!!.runOnUiThread {
            InternalStorage.saveTruyen(mActivity!!.baseContext, truyen!!.title, truyen!!)
            val comic = InternalStorage.getTruyen(mActivity!!.baseContext, truyen!!.title)
            Toast.makeText(mActivity!!.applicationContext, "Title : ${comic.title}", Toast
                    .LENGTH_LONG).show()
        }
    }

    override fun initListChapters(listChapters: MutableList<MutableMap<Int, ArrayList<Chapter>>>) {
        Log.d(Constants.TAG, "Set List Chapters")
        mTruyen!!.listChapter = ArrayList<Chapter>()
        var index = 0
        for (item in listChapters!!) {
            var arrayList = item.getValue(index)
            for (item2 in arrayList) {
                mTruyen!!.listChapter!!.add(item2)
            }
            index++
        }
    }

    override fun showListChaptersActivity() {
        val intent = Intent(mActivity, ListChaptersActivity::class.java)
        var bundle = Bundle()
        bundle.putSerializable(Constants.DATA_INTENT, mTruyen)
        intent.putExtra(Constants.TRUYEN_INFORMATION_TO_LIST_CHAPTERS_INTENT, bundle)
        mActivity!!.startActivity(intent)
    }

    override fun initTruyen(truyen: Truyen) {
        mTruyen!!.description = truyen.description
        mTruyen!!.imageTwo = truyen.imageTwo
        mTruyen!!.type = truyen.type
        mTruyen!!.status = truyen.status
        mTruyen!!.rate = truyen.rate
    }

    override fun downLoadTruyen() {
        DownLoadTruyenAsycntask(this).execute(mTruyen)
    }
}