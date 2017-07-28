package com.theanhdev97.truyenhot.Asynctask

import android.os.AsyncTask
import android.util.Log
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Presenter.TruyenInformationPresenter
import com.theanhdev97.truyenhot.Utils.Constants
import com.theanhdev97.truyenhot.Utils.Internet
import com.theanhdev97.truyenhot.View.MainActivity
import com.theanhdev97.truyenhot.View.TruyenInformationActivity
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by DELL on 10/07/2017.
 */
class GetListChapterAsynctask(presenter: TruyenInformationPresenter) : AsyncTask<String, String, Void>() {
    private var mPresenter: TruyenInformationPresenter? = null
    private var mMainURL: String? = null
    private var mError: Boolean = false
    private var mMaxPageSize: Int? = 0
    private var mListChapters: MutableList<MutableMap<Int, ArrayList<Chapter>>>? = null

    init {
        this.mPresenter = presenter
        this.mListChapters = mutableListOf()
    }

    override fun doInBackground(vararg params: String?): Void? {
        mMainURL = params[0]
        Internet.getContentStringFromInternetURL(
                params[0]!!,
                mPresenter!!.mActivity!!,
                object : Internet.getContentCallback {
                    override fun onSuccess(content: String) {
                        var doc = Jsoup.parse(content)
                        var root = doc.select("ul.pagination.pagination-sm").select("li")
                        if (root.size > 0)
                            mMaxPageSize = root[root.size - 2].getElementsByTag("a")[0].attr("title").split(" - Trang ")[1].toInt()
                        else
                            mMaxPageSize = 1
                        for (i in 0..mMaxPageSize!! - 1) {
                            publishProgress(i.toString())
                            Thread.sleep(Constants.TIME_LOAD_SLEEP.toLong())
                        }
                    }

                    override fun onFailure(error: String) {
                        mError = true
                    }
                }
        )
        return null
    }

    override fun onProgressUpdate(vararg values: String?) {
        var index = values[0]!!.toInt() + 1
        var url: String = ""
        if (index > 1) {
            url = "${mMainURL!!}trang-${index}/#list-chapter"
        } else
            url = mMainURL!!
        GetChaptersInOnePage(index).execute(url)
    }

    inner class GetChaptersInOnePage(pageIndex: Int) : AsyncTask<String, Void, Void>() {
        var mIndex: Int? = null

        init {
            mIndex = pageIndex
        }

        override fun doInBackground(vararg params: String?): Void? {
            Internet.getContentStringFromInternetURL(
                    params[0]!!,
                    mPresenter!!.mActivity!!,
                    object : Internet.getContentCallback {
                        override fun onSuccess(content: String) {
                            var doc = Jsoup.parse(content)
                            var root = doc.select("div#list-chapter").first().select("div.row").first()

                            var mainRoot = root.getElementsByTag("li")

                            var listChapters = ArrayList<Chapter>()
                            for (i in 0..mainRoot.size - 1) {
                                var information = mainRoot[i].getElementsByTag("a").attr("title")
                                        .split("Chương ")[1]
                                var chapter = information.split(":")[0]
                                var title = ""
                                if (information.split(":").size > 1)
                                    title = information.split(":")[1]
                                var link = "${mMainURL}chuong-${chapter}"
//                                Log.d(Constants.TAG, "$link")
                                listChapters.add(Chapter(link, title, chapter, ""))
                            }
                            // add To listChapters
                            addItemToListChapters(listChapters, mIndex!! - 1)
                        }

                        override fun onFailure(error: String) {
                            mError = true
                        }
                    }
            )
            return null
        }
    }


    var countItemsAddedToListChapters = 0
    fun addItemToListChapters(listChapters: ArrayList<Chapter>, index: Int) {
        var list = mutableMapOf<Int, ArrayList<Chapter>>(Pair(index, listChapters))
        mListChapters!!.add(list)

        countItemsAddedToListChapters++
        if (countItemsAddedToListChapters == mMaxPageSize) {


            //  Callback when load success
            loadFullChapterCallback()
        }
    }

    fun loadFullChapterCallback() {
        // Sort Chapters
        Collections.sort(mListChapters, kotlin.Comparator { o1, o2 -> o1.keys.toIntArray()[0] - o2.keys.toIntArray()[0] })

        // callback
        var callback = mPresenter
        callback!!.onLoadListChapterSuccess(mListChapters!!)
    }
}