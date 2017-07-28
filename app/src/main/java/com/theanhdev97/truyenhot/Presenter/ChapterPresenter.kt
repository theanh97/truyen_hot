package com.theanhdev97.truyenhot.Presenter

import android.content.SharedPreferences
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.NavigationView
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.theanhdev97.truyenhot.Asynctask.GetChapterAsynctask
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.Presenter.Interface.ChapterInterface
import com.theanhdev97.truyenhot.R
import com.theanhdev97.truyenhot.Utils.Constants
import com.theanhdev97.truyenhot.View.ChapterActivity
import kotlinx.android.synthetic.main.activity_chapter.*
import android.graphics.Typeface
import android.widget.*
import com.theanhdev97.truyenhot.Adapter.TextFontsAdapter
import com.theanhdev97.truyenhot.Model.ChapterSetting


/**
 * Created by DELL on 18/07/2017.
 */
class ChapterPresenter(activity: ChapterActivity) :
        ChapterInterface,
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemSelectedListener,
        SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {

    var mActivity: ChapterActivity? = null
    var mTruyen: Truyen? = null
    var mListFont: ArrayList<String>? = null
    var mListFontFromAsset: ArrayList<String>? = null
    var mListBackGroundColor: ArrayList<Int>? = null
    var mCurChapter: Chapter? = null
    var mPrevChapter: Chapter? = null
    var mNextChapter: Chapter? = null
    var mHasNextChapter: Boolean = true
    var mHasPrevChapter: Boolean = true
    var mCountChapterLoadSuccess: Int = 3
    var mBottomSheetMenu: BottomSheetBehavior<View>? = null
    var mBottomSheetTextSetting: BottomSheetBehavior<View>? = null
    var mBottomSheetLightSetting: BottomSheetBehavior<View>? = null
    var mSharedPreferenced: SharedPreferences? = null
    var mSharedPreferencedEditor: SharedPreferences.Editor? = null
    var mChapterSetting: ChapterSetting? = null

    init {
        this.mActivity = activity
        initSharedPreferenced()
        setEventListener()
        getAndInitChapterData()
        setCollapsingToolbar()
        setBottomSheetingDialog()
    }

    override fun initSharedPreferenced() {
        mSharedPreferenced = mActivity!!.getSharedPreferences(Constants.SHARED_PREFERENCED,
                Constants.SHARED_PREFERENCED_MODE)
        mSharedPreferencedEditor = mSharedPreferenced!!.edit()

    }

    override fun setTextSettingDialog() {
        mActivity!!.runOnUiThread {
            // text size
            mActivity!!.tv_text_size.text = mChapterSetting!!.textSize.toString()
            // text line spacing size
            mActivity!!.tv_line_space_size.text = mChapterSetting!!.textLineSpacingSize.toString()

            // text font
            for (i in 0 until mListFont!!.size)
                if (mListFont!!.get(i).equals(mChapterSetting!!.textFont)) {
                    mActivity!!.spn_text_font.setSelection(i)
                    break
                }
        }
    }

    override fun setLightSettingDialog() {
        mActivity!!.runOnUiThread {
            // light level
            mActivity!!.sb_light_level.progress = mChapterSetting!!.lightLevel!!

            // background color
            val backgroundID = mChapterSetting!!.backgroundColor
            when (backgroundID) {
                0 -> mActivity!!.group_rdb_background_color.check(R.id.rdb_color_1)
                1 -> mActivity!!.group_rdb_background_color.check(R.id.rdb_color_2)
                2 -> mActivity!!.group_rdb_background_color.check(R.id.rdb_color_3)
                3 -> mActivity!!.group_rdb_background_color.check(R.id.rdb_color_4)
                4 -> mActivity!!.group_rdb_background_color.check(R.id.rdb_color_5)
                5 -> mActivity!!.group_rdb_background_color.check(R.id.rdb_color_6)
                6 -> mActivity!!.group_rdb_background_color.check(R.id.rdb_color_7)
                7 -> mActivity!!.group_rdb_background_color.check(R.id.rdb_color_8)
            }
        }
    }

    override fun getChapterSetting(): ChapterSetting {
        // text size
        val textSize = mSharedPreferenced!!.getInt(Constants.CHAPTER_TEXT_SIZE,
                Constants.CHAPTER_TEXT_SIZE_DEFAULT)

        // text font
        val textFont = mSharedPreferenced!!.getString(Constants.CHAPTER_TEXT_FONT, Constants
                .CHAPTER_TEXT_FONT_DEFAULT)

        // text line spacing size
        val textLineSpacing = mSharedPreferenced!!.getInt(Constants.CHAPTER_TEXT_LINE_SPACING,
                Constants.CHAPTER_TEXT_LINE_SPACING_DEFAULT)

        // light setting
        val lightLevel = mSharedPreferenced!!.getInt(Constants.CHAPTER_LIGHT_LEVEL,
                Constants.CHAPTER_LIGHT_LEVEL_DEFAULT)

        // background color
        val backgroundColor = mSharedPreferenced!!.getInt(Constants.CHAPTER_BACKGROUND_COLOR,
                Constants.CHAPTER_BACKGROUND_COLOR_DEFAULT)

        return ChapterSetting(textSize, textLineSpacing, textFont, lightLevel, backgroundColor)
    }

    override fun setChapterSetting(textSize: Int?, textLineSpacingSize: Int?, textFont: String?,
                                   lightLevel: Int?, backgroundColor: Int?) {
        // text size
        if (textSize != null) {
            mChapterSetting!!.textSize = textSize
            mSharedPreferencedEditor!!.putInt(Constants.CHAPTER_TEXT_SIZE, mChapterSetting!!.textSize!!)
        }
        // text line spacing size
        if (textLineSpacingSize != null) {
            mChapterSetting!!.textLineSpacingSize = textLineSpacingSize
            mSharedPreferencedEditor!!.putInt(Constants.CHAPTER_TEXT_LINE_SPACING,
                    mChapterSetting!!.textLineSpacingSize!!)
        }
        // text font
        if (textFont != null) {
            mChapterSetting!!.textFont = textFont
            mSharedPreferencedEditor!!.putString(Constants.CHAPTER_TEXT_FONT, mChapterSetting!!
                    .textFont!!)
        }

        // light level
        if (lightLevel != null) {
            mChapterSetting!!.textLineSpacingSize = lightLevel
            mSharedPreferencedEditor!!.putInt(Constants.CHAPTER_LIGHT_LEVEL,
                    mChapterSetting!!.lightLevel!!)
        }

        // background color
        if (backgroundColor != null) {
            mChapterSetting!!.backgroundColor = backgroundColor
            mSharedPreferencedEditor!!.putInt(Constants.CHAPTER_BACKGROUND_COLOR,
                    mChapterSetting!!.backgroundColor!!)
        }

        mSharedPreferencedEditor!!.commit()
    }

    override fun setBottomSheetingDialog() {
        // menu
        mBottomSheetMenu = BottomSheetBehavior.from(mActivity!!.bottom_sheet_menu)
        mBottomSheetMenu!!.state = BottomSheetBehavior.STATE_COLLAPSED

        // Text setting
        mBottomSheetTextSetting = BottomSheetBehavior.from(mActivity!!.bottom_sheet_text_setting)
        mBottomSheetTextSetting!!.state = BottomSheetBehavior.STATE_COLLAPSED

        // Light setting
        mBottomSheetLightSetting = BottomSheetBehavior.from(mActivity!!.bottom_sheet_light_setting)
        mBottomSheetLightSetting!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun showBottomSettingDialog(isShow: Boolean, bottomSheetBehavior: BottomSheetBehavior<View>) {
        if (isShow) {
            if (bottomSheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) {
                // Update Text Setting
                if (bottomSheetBehavior.equals(mBottomSheetTextSetting)) {
                    setTextSettingDialog()
                }
                if (bottomSheetBehavior.equals(mBottomSheetLightSetting)) {
                    setLightSettingDialog()
                }
                bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            } else
                bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        } else
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Toast.makeText(mActivity, "${item.itemId}", Toast.LENGTH_LONG).show()
        when (item.itemId) {
            R.id.menu_tai_truyen -> Toast.makeText(mActivity, "DOWN LOAD", Toast.LENGTH_LONG).show()
            R.id.menu_truyen_info -> Toast.makeText(mActivity, "COMIC INFORMATION", Toast.LENGTH_LONG)
                    .show()
        }
        return false
    }

    override fun setCollapsingToolbar() {
        mActivity!!.setSupportActionBar(mActivity!!.toolbar)
        mActivity!!.collapsing_toolbar!!.isTitleEnabled = false
        mActivity!!.app_bar_layout.setExpanded(true)
        mActivity!!.supportActionBar!!.setTitle("")
        val chapterTitle = "Chương ${mCurChapter!!.chapter} : ${mCurChapter!!.title}"
        mActivity!!.tv_toolbar_title.text = chapterTitle

        // hiding & showing the title when toolbar expanded & collapsed
        mActivity!!.app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout
        .OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // COLLAPSED
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    showBottomSettingDialog(false, mBottomSheetMenu!!)
                    showBottomSettingDialog(false, mBottomSheetTextSetting!!)
                    showBottomSettingDialog(false, mBottomSheetLightSetting!!)
                }
                // EXPANDED
                else if (isShow) {
                    isShow = false
                    mActivity!!.tv_toolbar_title.text = chapterTitle
                    showBottomSettingDialog(true, mBottomSheetMenu!!)
                    Constants.showLog("State : ${mBottomSheetMenu!!.state}")
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
        // DISABLE ALL BOTTOM SHEET && TOOLBAR
            R.id.tv_chapter_content -> {
                showBottomSettingDialog(false, mBottomSheetMenu!!)
                showBottomSettingDialog(false, mBottomSheetTextSetting!!)
                showBottomSettingDialog(false, mBottomSheetLightSetting!!)
            }
        // LIST CHAPTER
            R.id.imb_list_chapters -> {
                mActivity!!.finish()
            }
        // TEXT SETTING
            R.id.imb_text -> {
                showBottomSettingDialog(true, mBottomSheetTextSetting!!)
            }
        // --------TEXT SIZE---------
            R.id.imb_descrease_text_size -> {
                var textSize = mActivity!!.tv_text_size.text.toString().toInt() - 1
                if (textSize > 10) {
                    mActivity!!.tv_text_size.text = textSize.toString()
                    setChapterSetting(textSize, null, null, null, null)
                    setChapterUI()
                }
            }
            R.id.imb_inscrease_text_size -> {
                var textSize = mActivity!!.tv_text_size.text.toString().toInt() + 1
                mActivity!!.tv_text_size.text = textSize.toString()
                setChapterSetting(textSize, null, null, null, null)
                setChapterUI()
            }
        // ---- TEXT LINE SPACING SIZE -----
            R.id.imb_descrease_line_space -> {
                var textLineSpacingSize = mActivity!!.tv_line_space_size.text.toString().toInt()
                if (textLineSpacingSize > 1) {
                    textLineSpacingSize -= 1
                    mActivity!!.tv_line_space_size.text = textLineSpacingSize.toString()
                    setChapterSetting(null, textLineSpacingSize, null, null, null)
                    setChapterUI()
                }
            }
            R.id.imb_inscrease_line_space -> {
                val textLineSpacingSize = mActivity!!.tv_line_space_size.text.toString().toInt() + 1
                mActivity!!.tv_line_space_size.text = textLineSpacingSize.toString()
                setChapterSetting(null, textLineSpacingSize, null, null, null)
                setChapterUI()
            }
        // -- LIGHT SETTING -----
            R.id.imb_light -> {
                showBottomSettingDialog(true, mBottomSheetLightSetting!!)
            }
            else -> Log.d(Constants.TAG, "tag123")
        }
    }

    override fun getAndInitChapterData() {
        // list background color
        mListBackGroundColor = ArrayList<Int>()
        mListBackGroundColor!!.addAll(mActivity!!.resources.getIntArray(R.array.chapter_background).toList())

        // list font
        mListFont = ArrayList<String>()
        mListFont!!.addAll(mActivity!!.resources.getStringArray(R.array.font_styles))

        // list font from asset
        mListFontFromAsset = ArrayList<String>()
        mListFontFromAsset!!.addAll(mActivity!!.resources.getStringArray(R.array.font_styles_from_assets))

        // chapter
        mChapterSetting = getChapterSetting()
        val data = mActivity!!.intent.getBundleExtra(Constants.OPEN_CHAPTER_INTENT)
        this.mTruyen = data.getSerializable(Constants.DATA_INTENT) as Truyen
        var curPosition = data.getInt(Constants.CURRENT_CHAPTER_POSITION_INTENT)

        mCurChapter = mTruyen!!.listChapter!![curPosition]
        // Check has NEXT && PREV chapter
        if (curPosition - 1 > -1)
            mPrevChapter = mTruyen!!.listChapter!![curPosition - 1]
        else
            mHasPrevChapter = false

        if (curPosition + 1 < mTruyen!!.listChapter!!.size)
            mNextChapter = mTruyen!!.listChapter!![curPosition + 1]
        else
            mHasNextChapter = false

        // Check chapters have data ?
        if (mCurChapter!!.content.equals("")) {
            mCountChapterLoadSuccess--
            GetChapterAsynctask(GetChapterAsynctask.DownLoadType.ONE, this, null).execute(mCurChapter)
        }
        if (mHasPrevChapter && mPrevChapter!!.content.equals("")) {
            mCountChapterLoadSuccess--
            GetChapterAsynctask(GetChapterAsynctask.DownLoadType.ONE, this, null).execute(mPrevChapter)
        }
        if (mHasNextChapter && mNextChapter!!.content.equals("")) {
            mCountChapterLoadSuccess--
            GetChapterAsynctask(GetChapterAsynctask.DownLoadType.ONE, this, null).execute(mNextChapter)
        }

        // Set up UI if load chapter success
        onLoadChapterSuccess()
    }

    override fun setChapterUI() {
        mActivity!!.runOnUiThread {
            // text size
            mActivity!!.tv_chapter_content.textSize = mChapterSetting!!.textSize!!.toFloat()
            // text line spacing size
            mActivity!!.tv_chapter_content.setLineSpacing(
                    0f,
                    mChapterSetting!!.textLineSpacingSize!!.toFloat())
            // text font
            val fontStyle = mListFontFromAsset!!.get(mActivity!!.spn_text_font.selectedItemPosition)
            val face = Typeface.createFromAsset(mActivity!!.assets, fontStyle)
            mActivity!!.tv_chapter_content.setTypeface(face, Typeface.NORMAL)

            Constants.showLog("LIGHT LEVEL : ${mChapterSetting!!.lightLevel}" +
                    "BACKGROUND COLOR : ${mChapterSetting!!.backgroundColor}")

            // light level
            val brightness = (mChapterSetting!!.lightLevel!!.toFloat() / 255f);
            var lp = mActivity!!.getWindow().getAttributes();
            lp.screenBrightness = brightness;
            mActivity!!.getWindow().setAttributes(lp);

            // background color && text color
            val colorID = mChapterSetting!!.backgroundColor!!
            mActivity!!.tv_chapter_content.setBackgroundColor(mListBackGroundColor!!.get(colorID))
            mActivity!!.toolbar.setBackgroundColor(mListBackGroundColor!!.get(colorID))

            if (colorID == 7) {
                mActivity!!.tv_chapter_content.setTextColor(mListBackGroundColor!!.get(6))
                mActivity!!.tv_toolbar_title.setTextColor(mListBackGroundColor!!.get(6))
            } else if (colorID == 6) {
                mActivity!!.tv_chapter_content.setTextColor(mListBackGroundColor!!.get(7))
                mActivity!!.tv_toolbar_title.setTextColor(mListBackGroundColor!!.get(7))
            } else {
                mActivity!!.tv_chapter_content.setTextColor(mListBackGroundColor!!.get(6))
                mActivity!!.tv_toolbar_title.setTextColor(mListBackGroundColor!!.get(6))
            }
        }
    }

    // background color listener
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        var id = 0
        when (checkedId) {
            R.id.rdb_color_1 -> {
                id = 0
            }
            R.id.rdb_color_2 -> {
                id = 1
            }
            R.id.rdb_color_3 -> {
                id = 2
            }
            R.id.rdb_color_4 -> {
                id = 3
            }
            R.id.rdb_color_5 -> {
                id = 4
            }
            R.id.rdb_color_6 -> {
                id = 5
            }
            R.id.rdb_color_7 -> {
                id = 6
            }
            R.id.rdb_color_8 -> {
                id = 7
            }
        }
        mChapterSetting!!.backgroundColor = id
        mSharedPreferencedEditor!!.putInt(Constants.CHAPTER_BACKGROUND_COLOR, id)
        mSharedPreferencedEditor!!.commit()
        setChapterUI()
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.d(Constants.TODO, "on progress changed")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.d(Constants.TODO, "start tracking touch")
    }

    // light level listener
    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // light level
        mChapterSetting!!.lightLevel = seekBar!!.progress
        mSharedPreferencedEditor!!.putInt(Constants.CHAPTER_LIGHT_LEVEL, seekBar!!.progress)
        mSharedPreferencedEditor!!.commit()
        setChapterUI()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d(Constants.TAG, "nothing")
    }

    // font selected listener
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mChapterSetting!!.textFont = mActivity!!.spn_text_font.selectedItem.toString()
        mSharedPreferencedEditor!!.putString(Constants.CHAPTER_TEXT_FONT, mChapterSetting!!.textFont)
        mSharedPreferencedEditor!!.commit()
        setChapterUI()
    }

    override fun setEventListener() {
        mActivity!!.tv_chapter_content.setOnClickListener(this)
        mActivity!!.spn_text_font.setOnItemSelectedListener(this)
        mActivity!!.group_rdb_background_color.setOnCheckedChangeListener(this)
        mActivity!!.sb_light_level.setOnSeekBarChangeListener(this)
        mActivity!!.imb_list_chapters.setOnClickListener(this)
        mActivity!!.imb_light.setOnClickListener(this)
        mActivity!!.imb_text.setOnClickListener(this)

        mActivity!!.imb_descrease_text_size.setOnClickListener(this)
        mActivity!!.imb_inscrease_text_size.setOnClickListener(this)
        mActivity!!.imb_descrease_line_space.setOnClickListener(this)
        mActivity!!.imb_inscrease_line_space.setOnClickListener(this)

    }

    override fun onLoadChapterSuccess() {
        if (mCountChapterLoadSuccess == 3) {
            // First load
            mActivity!!.tv_chapter_content.text = mCurChapter!!.content

            val adapter = TextFontsAdapter(mActivity!!, mListFont!!)
            //            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            mActivity!!.spn_text_font.adapter = adapter

            setChapterUI()
        } else {
            mCountChapterLoadSuccess++
            if (mCountChapterLoadSuccess == 3) {
                // first load
                mActivity!!.tv_chapter_content.text = mCurChapter!!.content
                val adapter = TextFontsAdapter(mActivity!!, mListFont!!)

//                val adapter = ArrayAdapter<String>(mActivity!!.applicationContext, android.R.layout
//                        .simple_spinner_item, mListFont)
//                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                mActivity!!.spn_text_font.adapter = adapter

                setChapterUI()
            }
        }
    }

    override fun onLoadChapterFailure() {
        mCountChapterLoadSuccess = 0
        mActivity!!.finish()
    }
}