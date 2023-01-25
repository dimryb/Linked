package ru.netology.linked.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.linked.R
import ru.netology.linked.databinding.FragmentUsersBinding
import ru.netology.linked.presentation.adapter.UsersAdapter
import ru.netology.linked.presentation.viewmodel.MainViewModel
import ru.netology.linked.presentation.viewmodel.MenuAction

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
        menuNavigation()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.usersData.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
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
                }
                else -> {}
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}