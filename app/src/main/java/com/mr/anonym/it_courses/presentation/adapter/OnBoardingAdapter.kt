package com.mr.anonym.it_courses.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.mr.anonym.it_courses.presentation.delegates.OnBoardingDelegate
import com.mr.anonym.it_courses.presentation.delegates.TextItem
import com.mr.anonym.it_courses.presentation.viewHolder.OnBoardingViewHolder

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingViewHolder>(){

    val delegatesManager = AdapterDelegatesManager<List<TextItem>>()
    val items: List<TextItem> = emptyList()

    init {

        delegatesManager.addDelegate(OnBoardingDelegate())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnBoardingViewHolder {

        return delegatesManager.onCreateViewHolder(parent,viewType) as OnBoardingViewHolder
    }

    override fun onBindViewHolder(
        holder: OnBoardingViewHolder,
        position: Int
    ) {

        delegatesManager.onBindViewHolder(items,position,holder)
    }

    override fun getItemCount(): Int {
        items.size
    }
}
