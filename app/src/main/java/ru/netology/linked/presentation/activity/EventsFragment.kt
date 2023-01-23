package ru.netology.linked.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.netology.linked.R
import ru.netology.linked.databinding.FragmentEventsBinding
import ru.netology.linked.presentation.activity.NewPostFragment.Companion.textArg
import ru.netology.linked.presentation.adapter.FeedAdapter
import ru.netology.linked.presentation.viewmodel.*

@AndroidEntryPoint
class EventsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private var _binding: FragmentEventsBinding? = null
    private val binding: FragmentEventsBinding
        get() = _binding ?: throw RuntimeException("FragmentEventsBinding == null!")

    private lateinit var adapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(
            inflater,
            container,
            false
        )

        adapter = FeedAdapter(
            PostOnInteractionListener(authViewModel, viewModel),
            EventOnInteractionListener(authViewModel, viewModel),
        )

        binding.postsList.adapter = adapter

        observeViewModel()
        setupListeners()
        menuNavigation()

        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.dataEvens.collectLatest {
                adapter.submitData(it)
            }
        }

        viewModel.editedEvent.observe(viewLifecycleOwner) { edited ->
            if (edited.id == 0L) {
                return@observe
            }
            launchEditEvent()
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state is FeedModelState.Loading
            if (state is FeedModelState.Error) {
                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) {
                        viewModel.refreshPost()
                    }.show()
            }
            binding.swipeRefresh.isRefreshing = state is FeedModelState.Refresh
        }

        viewModel.menuChecked.observe(viewLifecycleOwner) { checked ->
            with(binding.panelMenuBottom) {
                homeButton.isChecked = (checked == MenuChecked.HOME)
                usersButton.isChecked = (checked == MenuChecked.USERS)
                eventsButton.isChecked = (checked == MenuChecked.EVENTS)
            }
        }
    }

    private fun setupListeners() {

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshPost()
        }

        with(binding.panelMenuBottom) {
            homeButton.setOnClickListener {
                viewModel.bottomMenuAction(MenuAction.HOME)
            }
            usersButton.setOnClickListener {
                viewModel.bottomMenuAction(MenuAction.USERS)
            }
            eventsButton.setOnClickListener {
                viewModel.bottomMenuAction(MenuAction.EVENTS)
            }
            createPostButton.setOnClickListener {
                viewModel.bottomMenuAction(MenuAction.ADD)
            }
        }
    }

    private fun menuNavigation() {
        viewModel.menuAction.observe(viewLifecycleOwner) { action ->
            when (action) {
                MenuAction.HOME -> {

                }
                MenuAction.USERS -> {

                }
                MenuAction.EVENTS -> {

                }
                MenuAction.ADD -> {
                    if (authViewModel.authorized) {
                        findNavController().navigate(R.id.action_eventsFragment_to_newEventFragment)
                    } else {
                        authViewModel.signIn()
                    }
                }
                else -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun launchEditEvent() {
        findNavController()
            .navigate(
                R.id.action_eventsFragment_to_newEventFragment,
                Bundle().apply {
                    viewModel.editedEvent.value?.content.let {
                        textArg = it
                    }
                }
            )
    }
}