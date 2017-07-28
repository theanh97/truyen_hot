package com.theanhdev97.truyenhot.Presenter.Interface

import android.view.View
import com.theanhdev97.truyenhot.Model.Truyen

/**
 * Created by DELL on 09/07/2017.
 */
interface ListTruyenByTypeInterface {
    fun onLoadListTruyenSuccess(listTruyen: ArrayList<Truyen>)

    fun setListBooksToUI(listTruyen: ArrayList<Truyen>)

    fun handlingTruyenClickEvent(position: Int, view: View)
}