package com.theanhdev97.truyenhot.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.TruyenInformationPresenter
import com.theanhdev97.truyenhot.R
import com.theanhdev97.truyenhot.Utils.Constants
import com.theanhdev97.truyenhot.Presenter.Interface.TruyenInformationInterface
import kotlinx.android.synthetic.main.activity_truyen_information.*

class TruyenInformationActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_danh_sach_chuong -> mPresenter!!.showListChaptersActivity()
            R.id.btn_down_load -> mPresenter!!.downLoadTruyen()
        }
    }


    var mPresenter: TruyenInformationPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truyen_information)
        var data = intent.getBundleExtra(Constants.LIST_TRUYEN_BY_TYPE_TO_TRUYEN_INFORMATION_INTENT)
                .getSerializable(Constants.DATA_INTENT)
        mPresenter = TruyenInformationPresenter(this, data as Truyen)
    }


    fun repairUI(truyen: Truyen) {
        runOnUiThread {
            // IMAGE
            Glide.with(this)
                    .load(truyen.imageTwo)
                    .into(imv_image)

            // TITLE
            tv_title.text = truyen.title

            // RATING
            rtb_rating.rating = truyen.rate.toFloat()

            // AUTHOR
            tv_author.text = "Tác giả : ${truyen.author}"

            // TYPE
            tv_type.text = "Thể loại : ${truyen.type}"

            // STATUS
            tv_status.text = "Tình Trạng : ${truyen.status}"

            // DESCRIPTION
            tv_description.text = "\t" + truyen.description

            // MAX CHAPTER
            tv_max_chapter.text = "Số chương : ${truyen.maxChapter}"
        }
    }
}
