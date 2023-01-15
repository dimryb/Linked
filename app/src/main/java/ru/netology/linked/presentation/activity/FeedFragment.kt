package ru.netology.linked.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.linked.R
import ru.netology.linked.databinding.FragmentFeedBinding
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.domain.dto.UserPreview
import ru.netology.linked.domain.dto.Users
import ru.netology.linked.presentation.adapter.FeedAdapter
import ru.netology.linked.presentation.viewholder.OnInteractionListener
import ru.netology.linked.presentation.viewmodel.AuthViewModel
import ru.netology.linked.presentation.viewmodel.MainViewModel

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding
        get() = _binding ?: throw RuntimeException("FragmentFeedBinding == null!")

    private val adapter = FeedAdapter(object : OnInteractionListener {
        override fun onLike(post: Post) {

        }

        override fun onComment(post: Post, comment: String) {

        }

        override fun onDetails(post: Post) {

        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
        binding.postsList.adapter = adapter

        observeViewModel()
        setupListeners()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner){ feedModel ->
            adapter.submitList(feedModel.posts)
        }
    }

    private fun setupListeners() {
        binding.createButton.setOnClickListener {
            if (authViewModel.authorized) {
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
            } else {
                authViewModel.signIn()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}