package com.nextgen.beritaku.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.nextgen.beritaku.core.databinding.ItemExploreNewsBinding
import com.nextgen.beritaku.core.databinding.ItemHeadlineNewsBinding
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.utils.DateUtils

private const val HEADLINE = 1
private const val EXPLORE = 2

class NewsAdapter(): RecyclerView.Adapter<ViewHolder>() {

    public companion object{
        var VIEW_TYPE = 1
    }

    private var listNews = ArrayList<NewsModel>()
    var viewType = 1

    fun setData(newListNews: List<NewsModel>?){
        if (newListNews == null) return
        listNews.clear()
        listNews.addAll(newListNews)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == HEADLINE){
            ViewHolderHeadlines(
                ItemHeadlineNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }else{
            ViewHolderExplore(
                ItemExploreNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listNews[position]
        if (viewType == HEADLINE) (holder as ViewHolderHeadlines).bind(data)
        else (holder as ViewHolderExplore).bind(data)
    }

    override fun getItemViewType(position: Int): Int = viewType

    override fun getItemCount(): Int = listNews.size

    class ViewHolderExplore(private val binding: ItemExploreNewsBinding) : ViewHolder(binding.root) {
        fun bind(data: NewsModel) {
            binding.apply {
                tvTitlenews.text = data.title
                author.text = data.author ?: ""
                time.text = DateUtils.dateFormat(data.publishedAt)
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .centerCrop()
                    .into(ivThumbnail)
            }
        }

    }

    inner class ViewHolderHeadlines(private val binding: ItemHeadlineNewsBinding): ViewHolder(binding.root) {
        fun bind(data: NewsModel) {
            binding.apply {
                titleHeadline.text = data.title
                timeHeadline.text = DateUtils.dateFormat(data.publishedAt)
                author.text = data.author
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .centerCrop()
                    .into(thumbnailHeadline)
            }
        }

    }
}