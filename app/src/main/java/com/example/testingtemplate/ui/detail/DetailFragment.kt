package com.example.testingtemplate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testingtemplate.databinding.FragmentDetailBinding
import com.example.testingtemplate.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {

    override val viewModel: DetailViewModel by viewModels()

    private val args by navArgs<DetailFragmentArgs>()

    override fun inflateLayout(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.quote = args.quote

        binding!!.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}