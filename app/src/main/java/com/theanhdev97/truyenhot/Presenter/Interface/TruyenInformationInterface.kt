package com.theanhdev97.truyenhot.Presenter.Interface

import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.Truyen

/**
 * Created by DELL on 12/07/2017.
 */
interface TruyenInformationInterface {
    fun onDownLoadTruyenSuccess()

    fun initTruyen(truyen: Truyen)

    fun onDownLoadTruyenFailure()

    fun onLoadTruyenInformationSuccss(truyen: Truyen)

    fun onLoadListChapterSuccess(listChapter: MutableList<MutableMap<Int, ArrayList<Chapter>>>)

    fun initListChapters(listChapters: MutableList<MutableMap<Int, ArrayList<Chapter>>>)

    fun onLoadUIFailure()

    fun downLoadTruyen()

    fun registerEvent()

    fun setUI(truyen: Truyen)

    fun saveTruyenToInternalStorage(truyen: Truyen)

    fun showListChaptersActivity()
}