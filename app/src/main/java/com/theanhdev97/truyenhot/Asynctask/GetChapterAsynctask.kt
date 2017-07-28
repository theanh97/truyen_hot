package com.theanhdev97.truyenhot.Asynctask

import android.os.AsyncTask
import android.util.Log
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Presenter.ChapterPresenter
import com.theanhdev97.truyenhot.Presenter.ListChaptersPresenter
import com.theanhdev97.truyenhot.Presenter.TruyenInformationPresenter
import com.theanhdev97.truyenhot.View.MainActivity
import com.theanhdev97.truyenhot.Utils.Constants
import com.theanhdev97.truyenhot.Utils.Internet
import com.theanhdev97.truyenhot.View.TruyenInformationActivity
import org.jsoup.Jsoup

/**
 * Created by DELL on 07/07/2017.
 */
class GetChapterAsynctask(
        downLoadType: DownLoadType,
        chapterPresenter: ChapterPresenter?,
        downLoadTruyenAsycntask: DownLoadTruyenAsycntask?)
    : AsyncTask<Chapter, Void, Void>() {
    private var mDownLoadType: DownLoadType? = null
    private var mChapterPresenter: ChapterPresenter? = null
    private var mDownLoadTruyenAsynctask: DownLoadTruyenAsycntask? = null
    private var mError: String = ""

    init {
        this.mDownLoadType = downLoadType
        this.mChapterPresenter = chapterPresenter
        this.mDownLoadTruyenAsynctask = downLoadTruyenAsycntask
    }

    override fun doInBackground(vararg params: Chapter?): Void? {
        var truyen = params[0]
        // DOWN LOAD FULL CHAPTERS
        if (mDownLoadType == DownLoadType.FULL) {
            Internet.getContentStringFromInternetURL(
                    truyen!!.link,
                    mDownLoadTruyenAsynctask!!.mPresenter!!.mActivity!!,
                    object : Internet.getContentCallback {
                        override fun onSuccess(content: String) {
//                            var doc = Jsoup.parse(content)
////                        // CHAPTER CONTENT
//                            var content = doc.select("div.chapter-c").html().replace(" - ",
//                                    "\n\t-")

                            var root = content.split("chapter-c")
                            var content0 = root.get(root.size - 1).split("</div>")[0].replace("\">", "\t")
                            var content1 = content0.replace("<i>", "")
                            var content2 = content1.replace("</i>", "")
                            var content3 = content2.replace("<br><br>", "\n\t")
                            var content4 = content3.replace("&#8211;", "-")

                            truyen.content = content4

                            var callback = mDownLoadTruyenAsynctask
                            callback!!.isDownLoadFullChapters()
//                        Log.d(Constants.TAG, "title : ${truyen.title} --- Chapter : ${truyen.chapter}" +
//                                "--- Content : ${truyen.content}")
                        }

                        override fun onFailure(error: String) {
                            mError = error
                        }
                    }
            )
        }
        // DOWN LOAD ONE CHAPTER
        else {
            var callback = mChapterPresenter
            Internet.getContentStringFromInternetURL(
                    truyen!!.link,
                    mChapterPresenter!!.mActivity!!,
                    object : Internet.getContentCallback {
                        override fun onSuccess(content: String) {
                            var doc = Jsoup.parse(content)
//                        // CHAPTER CONTENT
                            var root = content.split("chapter-c")
                            var content0 = root.get(root.size - 1).split("</div>")[0].replace("\">", "\t")
                            var content1 = content0.replace("<i>", "")
                            var content2 = content1.replace("</i>", "")
                            var content3 = content2.replace("<br><br>", "\n\t")
                            var content4 = content3.replace("&#8211;", "-")

                            truyen.content = content4

                            callback!!.onLoadChapterSuccess()

//                        Log.d(Constants.TAG, "title : ${truyen.title} --- Chapter : ${truyen.chapter}" +
//                                "--- Content : ${truyen.content}")
                        }

                        override fun onFailure(error: String) {
                            callback!!.onLoadChapterFailure()
                        }
                    }
            )
        }
        return null
    }

    enum class DownLoadType {
        ONE,
        FULL
    }
}