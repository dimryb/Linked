package ru.netology.linked.presentation.activity

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.linked.R
import ru.netology.linked.databinding.FragmentNewEventBinding
import ru.netology.linked.presentation.viewmodel.AuthViewModel
import ru.netology.linked.presentation.viewmodel.MainViewModel
import ru.netology.nmedia.util.AndroidUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class NewEventFragment : Fragment() {
    private var _binding: FragmentNewEventBinding? = null
    private val binding: FragmentNewEventBinding
        get() = _binding ?: throw RuntimeException("FragmentNewEventBinding == null")

    private val viewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), "Image pick error", Toast.LENGTH_SHORT).show()
                }
                Activity.RESULT_OK -> {
                    viewModel.changePhoto(it.data?.data)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.create_posts_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                when (menuItem.itemId) {
                    R.id.save -> {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        val datetime = LocalDateTime.now().format(formatter)
                        viewModel.editEventContent(
                            binding.contentEditText.text.toString(),
                            datetime,
                            binding.onlineSwitch.isChecked
                        )
                        viewModel.saveEvent()
                        AndroidUtils.hideKeyboard(requireView())
                        true
                    }
                    else -> false
                }

        }, viewLifecycleOwner)

        _binding = FragmentNewEventBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel.isEditedFragment = true
        setupClickListeners()
        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        arguments?.textArg?.let(binding.contentEditText::setText)
        arguments?.typeArg?.let(binding.onlineSwitch::setChecked)

        viewModel.eventCreated.observe(viewLifecycleOwner) {
            viewModel.navigationUp()
        }

        viewModel.photo.observe(viewLifecycleOwner) {
            binding.photoLayout.isVisible = (it != null) && (it.uri != null)
            binding.photo.setImageURI(it?.uri)
        }
    }

    private fun setupClickListeners() {
        binding.takePhoto.setOnClickListener {
            ImagePicker.Builder(this)
                .cameraOnly()
                .maxResultSize(2048, 2048)
                .createIntent(photoLauncher::launch)
        }

        binding.pickPhoto.setOnClickListener {
            ImagePicker.Builder(this)
                .galleryOnly()
                .maxResultSize(2048, 2048)
                .createIntent(photoLauncher::launch)
        }

        binding.removePhoto.setOnClickListener {
            viewModel.changePhoto(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isEditedFragment = false
        _binding = null
    }

    companion object {
        private const val TEXT_KEY = "TEXT_KEY"
        var Bundle.textArg: String?
            set(value) = putString(TEXT_KEY, value)
            get() = getString(TEXT_KEY)

        private const val TYPE_KEY = "TYPE_KEY"
        var Bundle.typeArg: Boolean?
            set(value) = putBoolean(TYPE_KEY, value ?: false)
            get() = getBoolean(TYPE_KEY)
    }
}