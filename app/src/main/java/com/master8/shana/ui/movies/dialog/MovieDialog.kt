package com.master8.shana.ui.movies.dialog

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.databinding.DialogMovieBinding
import com.master8.shana.ui.EventObserver
import com.master8.shana.ui.ext.hide
import com.master8.shana.ui.ext.inverseVisibility

class MovieDialog : BottomSheetDialogFragment() {

    private val viewModel by activityViewModels<MovieDialogViewModel>() { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val postersAdapter = PostersAdapter(viewModel, viewLifecycleOwner)

        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            binding.movie = it

            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.groupChangePoster.hide()
            binding.headerMarker.hide()
        }

        viewModel.posters.observe(viewLifecycleOwner) {
            postersAdapter.submitList(it)
        }

        viewModel.closeDialog.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })

        binding.pagerPosters.adapter = postersAdapter

        binding.image.setOnClickListener {
            binding.headerMarker.inverseVisibility()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.groupChangePoster.inverseVisibility()
            viewModel.searchPosters()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val bottomSheetBehavior = BottomSheetBehavior.from(requireView().parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}