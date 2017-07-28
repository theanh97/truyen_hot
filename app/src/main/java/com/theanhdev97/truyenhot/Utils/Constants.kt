package com.theanhdev97.truyenhot.Utils

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by DELL on 05/07/2017.
 */
class Constants {
    companion object {
        val TAG = "tag123"
        val TODO = "todo"
        val NO_CHAPTER = "Không hoặc chưa có chương tiếp theo"
        val TIME_LOAD_SLEEP = 10
        val MAX_CHAPTERS_IN_1_PAGE = 50

        // -------------------DATA INTENT---------------------
        val DATA_INTENT = "data"
        val CURRENT_CHAPTER_POSITION_INTENT = "0"
        val LIST_TRUYEN_BY_TYPE_TO_TRUYEN_INFORMATION_INTENT = "1"
        val TRUYEN_INFORMATION_TO_LIST_CHAPTERS_INTENT = "2"
        val OPEN_CHAPTER_INTENT = "3"

        // -------------------URL------------------------
        val TRANG_CHU_URL = "http://truyenfull.vn/"
        val TRUYEN_TIEN_HIEP_URL = "http://truyenfull.vn/the-loai/tien-hiep/"

        //  ----------------SHARED PREFERENCES--------------------
        val SHARED_PREFERENCED = "shared_preferenced"
        val SHARED_PREFERENCED_MODE = Context.MODE_PRIVATE

        // SETTING CHAPTER
        val CHAPTER_TEXT_SIZE = "chapter_text_size"
        val CHAPTER_TEXT_SIZE_DEFAULT = 20

        val CHAPTER_TEXT_FONT = "chapter_text_font"
        val CHAPTER_TEXT_FONT_DEFAULT = "Palatino Linotype"

        val CHAPTER_TEXT_LINE_SPACING = "chapter_text_line_spacing"
        val CHAPTER_TEXT_LINE_SPACING_DEFAULT = 2

        val CHAPTER_LIGHT_LEVEL = "chapter_light_level"
        val CHAPTER_LIGHT_LEVEL_DEFAULT = 127

        val CHAPTER_BACKGROUND_COLOR = "chapter_background_color"
        val CHAPTER_BACKGROUND_COLOR_DEFAULT = 1


        // show Log
        fun showLog(content: String) {
            Log.d(TAG, content)
        }

        // show Toast
        fun showToast(content: String, context: Context) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
        }
    }
}