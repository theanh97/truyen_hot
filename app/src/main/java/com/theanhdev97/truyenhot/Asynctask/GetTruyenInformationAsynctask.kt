package com.theanhdev97.truyenhot.Asynctask

import android.os.AsyncTask
import com.theanhdev97.truyenhot.View.MainActivity
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.TruyenInformationPresenter
import com.theanhdev97.truyenhot.Utils.Internet
import com.theanhdev97.truyenhot.View.TruyenInformationActivity
import org.jsoup.Jsoup

/**
 * Created by DELL on 06/07/2017.
 */
class GetTruyenInformationAsynctask(presenter: TruyenInformationPresenter) : AsyncTask<String, Void,
        Void>
() {
    private var mPresenter: TruyenInformationPresenter? = null
    private var mError: String = ""

    init {
        this.mPresenter = presenter
    }

    override fun doInBackground(vararg params: String?): Void? {
        Internet.getContentStringFromInternetURL(
                params[0]!!,
                mPresenter!!.mActivity!!,
                object : Internet.getContentCallback {
                    override fun onSuccess(content: String) {
                        var doc = Jsoup.parse(content)
                        var root = doc.select("div.col-xs-12.col-info-desc")
                        var root1 = root.select("div.col-xs-12")[0]
                        var root2 = root.select("div.col-xs-12")[2]
                        var root3 = root1.select("div.info")

                        // IMAGE TWO
                        var imageTwo = root1.select("div.book")[0].getElementsByTag("img").attr("src").toString()

                        // AUTHOR
                        var author = root3[0].getElementsByTag("a")[0].text()

                        // TYPE
                        var rootType = root3[0].getElementsByTag("div")[0].getElementsByTag("a")
                        var content1 = StringBuilder()
                        for (index in 1..rootType.size - 1)
                            content1.append(", ${rootType[index].text()}")
                        var type = content1.toString().split(" , ")[0].removeRange(0, 2)

                        // STATUS
                        var status: String = ""
                        if (root3[0].getElementsByTag("span").size > 1)
                            status = root3[0].getElementsByTag("span")[1].text()
                        else
                            status = root3[0].getElementsByTag("span")[0].text()

                        // DESCRIPTION
                        var descriptionRoot = root2.select("div.desc-text")[0].text()
                        var description = descriptionRoot.replace(" - ", "\n - ")

                        // TITLE
                        var title = root2.select("h3.title").first().text()

                        // RATING
                        var rating = root2.getElementsByTag("span").first().text()
                        if (rating.isEmpty())
                            rating = "0.0"

                        // OBJECT
                        var truyen = Truyen("", title, "", "", author, rating.toDouble(), description,
                                type,
                                status, imageTwo, null)

                        // call back
                        var callback = mPresenter
                        callback!!.onLoadTruyenInformationSuccss(truyen)
                    }

                    override fun onFailure(error: String) {
                        mError = error
                    }
                }
        )
        return null
    }
}