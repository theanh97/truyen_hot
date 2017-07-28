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
import com.theanhdev97.truyenhot.Asynctask.GetListTruyenByTypeAsynctask
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.Interface.ListTruyenByTypeInterface
import com.theanhdev97.truyenhot.Utils.Constants
import com.theanhdev97.truyenhot.View.ListTruyenByTypeActivity
import com.theanhdev97.truyenhot.View.MainActivity
import com.theanhdev97.truyenhot.View.TruyenInformationActivity
import kotlinx.android.synthetic.main.activity_list_truyen_by_type.*

/**
 * Created by DELL on 09/07/2017.
 */
class ListTruyenByTypePresenter(activity: ListTruyenByTypeActivity) : TruyenByTypeAdapter
.MyClickListener, ListTruyenByTypeInterface {

    var mActivity: ListTruyenByTypeActivity? = null
    var mListTruyenAdapter: TruyenByTypeAdapter? = null
    var mListTruyenRecyclerview: RecyclerView? = null
    var mListTruyen: ArrayList<Truyen>? = null

    init {
        mActivity = activity
        // repair UI
        mListTruyenRecyclerview = mActivity!!.rclv_list_truyen
        GetListTruyenByTypeAsynctask(this).execute("http://truyenfull" +
                ".vn/the-loai/tien-hiep/")
    }

    override fun onLoadListTruyenSuccess(listTruyen: ArrayList<Truyen>) {
        setListBooksToUI(listTruyen)
    }

    override fun setListBooksToUI(listTruyen: ArrayList<Truyen>) {
        mListTruyen = ArrayList<Truyen>()
        mListTruyen!!.addAll(listTruyen)
        mListTruyenRecyclerview!!.layoutManager = LinearLayoutManager(mActivity)
        mListTruyenAdapter = TruyenByTypeAdapter(mActivity!!, mListTruyen!!)
        mListTruyenAdapter!!.setOnClickListener(this)
        mListTruyenRecyclerview!!.adapter = mListTruyenAdapter

    }

    override fun OnItemClickListener(position: Int, view: View) {
        handlingTruyenClickEvent(position, view)
    }

    override fun handlingTruyenClickEvent(position: Int, view: View) {
        var curTruyen = mListTruyen!![position]
        var goToInformationActivity = Intent(mActivity, TruyenInformationActivity::class.java)
        var bundle = Bundle()
        bundle.putSerializable(Constants.DATA_INTENT, curTruyen)
        goToInformationActivity.putExtra(Constants.LIST_TRUYEN_BY_TYPE_TO_TRUYEN_INFORMATION_INTENT, bundle)
        mActivity!!.startActivity(goToInformationActivity)
    }
}
