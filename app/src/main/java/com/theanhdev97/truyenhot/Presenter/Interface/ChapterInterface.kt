package com.theanhdev97.truyenhot.Presenter.Interface

import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.ChapterSetting

/**
 * Created by DELL on 18/07/2017.
 */
interface ChapterInterface {
    fun setChapterUI()

    fun getAndInitChapterData()

    fun initSharedPreferenced()

    fun onLoadChapterSuccess()

    fun onLoadChapterFailure()

    fun setEventListener()

    fun setCollapsingToolbar()

    fun setBottomSheetingDialog()

    fun showBottomSettingDialog(isShow: Boolean, bottomSheetBehavior: BottomSheetBehavior<View>)

    fun setTextSettingDialog()

    fun setLightSettingDialog()

    fun getChapterSetting(): ChapterSetting

    fun setChapterSetting(textSize: Int?, textLineSpacingSize: Int?, textFont: String? ,
                          lightLevel : Int? , backgroundColor : Int?)
}