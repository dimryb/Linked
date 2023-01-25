package ru.netology.linked.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.linked.R
import ru.netology.linked.databinding.FragmentUsersBinding
import ru.netology.linked.presentation.adapter.UsersAdapter
import ru.netology.linked.presentation.viewmodel.FeedModelState
import ru.netology.linked.presentation.viewmodel.MainViewModel
import ru.netology.linked.presentation.viewmodel.MenuAction
import ru.netology.linked.presentation.viewmodel.MenuChecked

class UsersFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentUsersBinding? = null
    private val binding: FragmentUsersBinding
        get() = _binding ?: throw RuntimeException("FragmentUsersBinding == null!")

    private val adapter = UsersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        binding.usersList.adapter = adapter

        observeViewModel()
        setupListeners()

        binding.panelMenuBottom.createPostButton.isEnabled = false

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.usersData.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
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
            viewModel.refreshUsers()
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}