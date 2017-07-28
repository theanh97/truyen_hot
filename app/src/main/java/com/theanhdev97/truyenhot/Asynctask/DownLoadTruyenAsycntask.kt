package com.theanhdev97.truyenhot.Asynctask

import android.os.AsyncTask
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Toast
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.TruyenInformationPresenter
import com.theanhdev97.truyenhot.Utils.Constants
import com.theanhdev97.truyenhot.View.TruyenInformationActivity

/**
 * Created by DELL on 14/07/2017.
 */
class DownLoadTruyenAsycntask(presenter: TruyenInformationPresenter) : AsyncTask<Truyen, Chapter,
        Void>
(), DownLoadCallback {
    var maxChapter = 0
    var countDownloadedChapter = 0
    var mPresenter: TruyenInformationPresenter? = null

    init {
        this.mPresenter = presenter
    }

    override fun doInBackground(vararg params: Truyen?): Void? {
        var truyen = params[0]
        var listChapter = truyen!!.listChapter
        maxChapter = listChapter!!.size
        for (item in listChapter!!)
            publishProgress(item)
        return null
    }

    override fun onProgressUpdate(vararg values: Chapter?) {
        var link = values[0]
        GetChapterAsynctask(GetChapterAsynctask.DownLoadType.FULL, null, this)
                .execute(link)
    }

    override fun isDownLoadFullChapters() {
        countDownloadedChapter++
        Log.d(Constants.TAG, "size : $countDownloadedChapter")
        if (countDownloadedChapter == maxChapter)
            mPresenter!!.onDownLoadTruyenSuccess()
    }
}

interface DownLoadCallback {
    fun isDownLoadFullChapters()
}