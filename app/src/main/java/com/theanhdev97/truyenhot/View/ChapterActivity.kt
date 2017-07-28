package com.theanhdev97.truyenhot.View

import android.content.Context
import android.content.Intent
import android.gesture.Gesture
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.GestureDetectorCompat
import android.telephony.gsm.GsmCellLocation
import android.util.Log
import android.view.*
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.ChapterPresenter
import com.theanhdev97.truyenhot.R
import com.theanhdev97.truyenhot.Utils.Constants
import kotlinx.android.synthetic.main.activity_chapter.*
import kotlinx.android.synthetic.main.activity_truyen_information.*

class ChapterActivity : AppCompatActivity(), GestureDetector.OnGestureListener, View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        this.mGesture!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    var mPresenter: ChapterPresenter? = null
    var mGesture: GestureDetectorCompat? = null
    val THRESHOLD = 200
    val THRESHOLD_VELOCITY = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_chapter)
        mPresenter = ChapterPresenter(this)
        tv_chapter_content.setOnTouchListener(this)
        mGesture = GestureDetectorCompat(this, this)
        Constants.showLog("left -> right    ")
    }

    fun setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        this.mGesture!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if (e2!!.getX() - e1!!.getX() > THRESHOLD && Math.abs(velocityX) > THRESHOLD_VELOCITY)
            Constants.showToast("left -> right ", this@ChapterActivity)
        if (e1!!.getX() - e2!!.getX() > THRESHOLD && Math.abs(velocityX) > THRESHOLD_VELOCITY)
            Constants.showToast("right -> left ", this@ChapterActivity)
        return true
    }
    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Constants.showToast("1 tap", this@ChapterActivity)
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }


    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

}
