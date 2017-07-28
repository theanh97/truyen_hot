package com.theanhdev97.truyenhot.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.ListTruyenByTypePresenter
import com.theanhdev97.truyenhot.R
import com.theanhdev97.truyenhot.Presenter.Interface.ListTruyenByTypeInterface

class ListTruyenByTypeActivity : AppCompatActivity(){

    var mPresenter: ListTruyenByTypePresenter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_truyen_by_type)
        mPresenter = ListTruyenByTypePresenter(this)
    }
}
