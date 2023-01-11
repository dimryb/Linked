package ru.netology.linked.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.linked.databinding.FragmentFeedBinding
import ru.netology.linked.domain.dto.*
import ru.netology.linked.presentation.adapter.FeedAdapter
import ru.netology.linked.presentation.viewholder.OnInteractionListener

@AndroidEntryPoint
class FeedFragment : Fragment() {

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

        return binding.root
    }

    private fun observeViewModel() {

        val posts: List<Post> = listOf(
            Post(
                id = 1,
                authorId = 1,
                author = "author",
                content = "content",
                published = "published",
                linkedOwnerIds = emptyList(),
                mentionIds = emptyList(),
                mentionedMe = false,
                likedByMe = false,
                ownedByMe = false,
                users = Users(UserPreview(name = "name")),
            ),
            Post(
                id = 2,
                authorId = 2,
                author = "author2",
                content = "content2",
                published = "published2",
                linkedOwnerIds = emptyList(),
                mentionIds = emptyList(),
                mentionedMe = false,
                likedByMe = false,
                ownedByMe = false,
                users = Users(UserPreview(name = "name2")),
            ),
        )

        adapter.submitList(posts)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}