package com.theanhdev97.truyenhot.Asynctask

import android.os.AsyncTask
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.ListTruyenByTypePresenter
import com.theanhdev97.truyenhot.Utils.Internet
import com.theanhdev97.truyenhot.View.ListTruyenByTypeActivity
import org.jsoup.Jsoup

/**
 * Created by DELL on 06/07/2017.
 */
class GetListTruyenByTypeAsynctask(presenter: ListTruyenByTypePresenter) : AsyncTask<String, Void,
        Void>() {
    private var mPresenter: ListTruyenByTypePresenter? = null
    private var mError: String = ""

    init {
        this.mPresenter = presenter
    }

    override fun doInBackground(vararg params: String?): Void? {
        var listTruyen = ArrayList<Truyen>()
        Internet.getContentStringFromInternetURL(
                params[0]!!,
                mPresenter!!.mActivity!!,
                object : Internet.getContentCallback {
                    override fun onSuccess(content: String) {
                        var doc = Jsoup.parse(content)
                        var root = doc.select("div.col-xs-12.col-sm-12.col-md-9.col-truyen-main")
                        var array = root.select("div.row")
                        for (it in array) {
                            // LINK
                            var link = it.getElementsByTag("a").attr("href")

                            // IMAGE
                            var src = it.getElementsByAttribute("data-image").attr("data-image")

                            // MAX CHAPTER
                            var maxChapter = it.getElementsByTag("a").text().split("Chương ")[1]

                            // TITLE
                            var title = it.getElementsByTag("a").text().split("Chương")[0]

                            // AUTHOR
                            var author = it.select("span.author").text()

                            // ADD TO ARRAY
                            listTruyen.add(Truyen(link, title, maxChapter, src, author, 0.0, "", "",
                                    "", "", null))
                        }
                        var repairUI = mPresenter
                        repairUI!!.onLoadListTruyenSuccess(listTruyen)
                    }

                    override fun onFailure(error: String) {
                        mError = error
                    }
                }
        )
        return null
    }
}