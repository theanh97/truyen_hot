package com.theanhdev97.truyenhot.Model

import android.provider.SyncStateContract
import com.theanhdev97.truyenhot.Utils.Constants
import java.io.Console
import java.io.Serializable

/**
 * Created by DELL on 06/07/2017.
 */
data class Truyen
(var link: String, var title: String, var maxChapter: String, var imageOne: String, var author:
String,
 var rate: Double, var description: String, var type: String, var status: String, var
 imageTwo: String, var listChapter: ArrayList<Chapter>?) :
        Serializable {
}