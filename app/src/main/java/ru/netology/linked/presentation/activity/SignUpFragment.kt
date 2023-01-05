package ru.netology.linked.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.linked.R
import ru.netology.linked.databinding.FragmentSignUpBinding
import ru.netology.linked.presentation.viewmodel.AuthViewModel

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("SignUpFragment == null")

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        setupClickListeners()
        return binding.root
    }

    private fun setupClickListeners() {
        with(binding) {
            signInButton.setOnClickListener {
                viewModel.registration(
                    fieldLogin.text.toString(),
                    fieldPassword.text.toString(),
                    fieldRepeatPassword.text.toString(),
                    fieldName.text.toString(),
                    null
                )
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.registerErrorSignal.observe(viewLifecycleOwner) { error ->
            val toastMessage: String = when (error) {
                AuthViewModel.RegistrationError.UNKNOWN -> getString(R.string.register_error)
                AuthViewModel.RegistrationError.PASSWORDS_DONT_MATCH -> getString(R.string.passwords_dont_match)
                AuthViewModel.RegistrationError.NAME_IS_BLANK -> getString(R.string.name_is_blank)
                AuthViewModel.RegistrationError.LOGIN_IS_BLANK -> getString(R.string.login_is_blank)
                AuthViewModel.RegistrationError.PASSWORD_IS_BLANK -> getString(R.string.password_is_blank)
                else -> getString(R.string.register_error)
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}