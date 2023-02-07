package com.nextgen.beritaku.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.core.utils.ObjectConverter
import com.nextgen.beritaku.detail.DetailFragment
import com.nextgen.beritaku.favorite.databinding.ActivityFavoriteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteActivity : AppCompatActivity() {
    private val viewModel: FavoriteViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }
    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteNews.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
            newsAdapter.viewType = 2
            viewModel.favoriteNews.observe(this@FavoriteActivity){ result ->
                newsAdapter.setData(result)
            }
            newsAdapter.onClick = { selectedItem ->
                val uri = Uri.parse("beritaku://detail")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                    .putExtra(DetailFragment.DATA_ITEM, selectedItem)
                startActivity(intent)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}