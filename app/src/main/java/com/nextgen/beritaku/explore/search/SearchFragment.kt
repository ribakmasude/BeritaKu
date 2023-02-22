package com.nextgen.beritaku.explore.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentSearchBinding
import com.nextgen.beritaku.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
        setupRecyclerView()
        getSearchNews()

    }

    private fun getSearchNews() {

        viewModel.searchResult.observe(viewLifecycleOwner){result->
            lifecycleScope.launch {
                result
                    .onStart {
                        binding.loadData.visibility = View.VISIBLE
                    }
                    .collect{
                        binding.noData.root.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                        binding.loadData.visibility = View.GONE
                        if (it.isNotEmpty()){
                            newsAdapter.setData(it)
                        }
                    }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter.viewType = 2
        newsAdapter.onClick = {selectedItem->
            val bundle = Bundle()
            bundle.putParcelable(DetailFragment.DATA_ITEM, selectedItem)
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
        }
        binding.rvItemSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun setupSearchView() {
        val searchField = binding.searchField
        searchField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lifecycleScope.launch {
                    viewModel.searchQuery.value = query.toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}