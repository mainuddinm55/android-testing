package com.example.testingtemplate.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.databinding.FragmentHomeBinding
import com.example.testingtemplate.ui.base.BaseFragment
import com.example.testingtemplate.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private val TAG = "HomeFragment"
    override val viewModel: HomeViewModel by viewModels()
    private val adapter: QuoteAdapter by lazy {
        QuoteAdapter { item: Quote, _: View, _: Int ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    item
                )
            )
        }
    }

    override fun inflateLayout(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        lifecycleScope.launch {
            viewModel.quotes.collectLatest { resource ->
                renderUi(resource)
            }
        }
    }

    private fun initRecyclerView() {
        binding!!.quoteRecyclerView.apply {
            adapter = this@HomeFragment.adapter
        }
    }

    private fun showProgress(isShow: Boolean) {
        binding!!.progressCircular.visibility = if (isShow) View.VISIBLE else View.GONE
        binding!!.quoteRecyclerView.visibility = if (isShow) View.GONE else View.VISIBLE
    }

    fun renderUi(resource: Resource<List<Quote>>) {
        when (resource) {
            is Resource.Failed -> {
                Log.d(TAG, "onViewCreated: failed>> " + resource.msg)
                showProgress(false)
                Toast.makeText(requireContext(), resource.msg, Toast.LENGTH_LONG).show()
            }

            Resource.Loading -> {
                Log.d(TAG, "onViewCreated: loading>> ")
                showProgress(true)
            }

            is Resource.Success -> {
                Log.d(TAG, "onViewCreated: success>> " + resource.data)
                showProgress(false)
                adapter.addItems(resource.data)
            }
        }
    }
}