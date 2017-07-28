package com.theanhdev97.truyenhot.Adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.theanhdev97.truyenhot.Model.Chapter
import com.theanhdev97.truyenhot.Model.Truyen
import com.theanhdev97.truyenhot.R
import kotlinx.android.synthetic.main.chapter_row.view.*
import kotlinx.android.synthetic.main.truyen_by_type_row.view.*
import android.text.style.RelativeSizeSpan
import android.text.SpannableString
import android.text.style.StyleSpan


/**
 * Created by DELL on 17/07/2017.
 */
class ChaptersAdapter(context: Context, listTruyen: ArrayList<Chapter>) :
        RecyclerView.Adapter<ChaptersAdapter.itemHolder>() {
    var mContext: Context? = null
    var mListChapters: ArrayList<Chapter>? = null
    var mClickListener: MyClickListener? = null

    init {
        this.mContext = context
        this.mListChapters = listTruyen
    }

    override fun onBindViewHolder(holder: ChaptersAdapter.itemHolder?, position: Int) {
        val truyen = mListChapters!![position]


        val chapterTitle = "Chương ${truyen.chapter} : ${truyen.title}"   // index
        // 103 - 112

        val chapterTitleStyle = SpannableString(chapterTitle)
        // make the text twice as large
        chapterTitleStyle.setSpan(StyleSpan(Typeface.BOLD), 0, chapterTitle.split(": ")[0].length +
                1, 0)
        holder!!.chapter!!.text = chapterTitleStyle
    }


    override fun getItemCount(): Int {
        return mListChapters!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): itemHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.chapter_row, parent, false)
        return itemHolder(view)
    }

    inner class itemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View?) {
            mClickListener!!.OnItemClickListener(adapterPosition, v!!)
        }

        var chapter: TextView? = null

        init {
            chapter = view.tv_chapter
            view.setOnClickListener(this)
        }
    }

    fun setOnClickListener(onClickListener: MyClickListener) {
        this.mClickListener = onClickListener
    }

    interface MyClickListener {
        fun OnItemClickListener(position: Int, view: View)
    }
}