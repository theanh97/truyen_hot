package com.theanhdev97.truyenhot.View

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.theanhdev97.truyenhot.Adapter.TruyenByTypeAdapter
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.ListChaptersPresenter
import com.theanhdev97.truyenhot.R
import com.theanhdev97.truyenhot.Utils.Constants

class ListChaptersActivity : AppCompatActivity() {
    var mPresenter: ListChaptersPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_chapters)
        val truyen = intent.getBundleExtra(Constants.TRUYEN_INFORMATION_TO_LIST_CHAPTERS_INTENT)
                .getSerializable(Constants.DATA_INTENT) as Truyen
        mPresenter = ListChaptersPresenter(this, truyen)
    }
}
