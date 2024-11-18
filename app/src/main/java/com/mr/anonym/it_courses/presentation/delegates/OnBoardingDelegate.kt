package com.mr.anonym.it_courses.presentation.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mr.anonym.it_courses.R
import com.mr.anonym.it_courses.presentation.viewHolder.OnBoardingViewHolder

class OnBoardingDelegate : AdapterDelegate<List<TextItem>>() {
    override fun isForViewType(
        items: List<TextItem>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboard, parent, false)
        return OnBoardingViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<TextItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any?>
    ) {

        val textItem = items[position]
        (holder as OnBoardingViewHolder).tvText.text = textItem.text
    }
}

data class TextItem(val text: String)
