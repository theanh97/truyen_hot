package com.theanhdev97.truyenhot.View

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils.replace
import android.util.Log
import com.theanhdev97.truyenhot.Asynctask.GetChapterAsynctask
import com.theanhdev97.truyenhot.Asynctask.GetTruyenInformationAsynctask
import com.theanhdev97.truyenhot.R
import com.theanhdev97.truyenhot.Utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        GetListTruyenByTypeAsynctask(this).execute(Constants.TRUYEN_TIEN_HIEP_URL)
        var thread = Thread()
        thread.run {
            var fullString = StringBuilder()
            val url = URL("http://truyenfull.vn/van-co-chi-ton/chuong-27/")
            val reader = BufferedReader(InputStreamReader(url.openStream()))
            var line = reader.readLine()
            while (line != null) {
                fullString.append(line)
                line = reader.readLine()
            }


        }
    }
}
