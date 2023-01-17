package ru.netology.linked.presentation.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.linked.domain.Repository
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.domain.dto.UserPreview
import ru.netology.linked.domain.dto.Users
import ru.netology.linked.presentation.model.FeedModel
import ru.netology.linked.presentation.model.FeedModelState
import ru.netology.linked.presentation.util.SingleLiveEvent
import javax.inject.Inject

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    authorId = 0L,
    authorAvatar = "",
    likedByMe = false,
    published = "",
    mentionedMe = false,
    ownedByMe = false,
    users = Users(user = UserPreview(name = "")),
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val data: LiveData<FeedModel> = repository.data.map { films ->
        FeedModel(films, films.isEmpty())
    }.asLiveData(Dispatchers.Default)

    private val _state = MutableLiveData<FeedModelState>()
    val state: LiveData<FeedModelState>
        get() = _state

    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            try {
                repository.getPosts()
            } catch (e: Exception) {
                TODO("getPosts Exception")
            }
        }
    }

    fun savePost() {
        edited.value?.let { post ->
            viewModelScope.launch {
                _postCreated.value = Unit
                try {
                    repository.setPost(post)
                    _state.value = FeedModelState.Idle
                } catch (e: Exception) {
                    _state.value = FeedModelState.Error
                }
            }
        }
        edited.value = empty
    }

    fun editPost(post: Post) {
        edited.value = post
    }

    fun editContent(content: String) {
        val value = edited.value
        value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun removePostById(postId: Long) {
        viewModelScope.launch {
            try {
                repository.removePost(postId)
            } catch (e: Exception) {
            }
        }
    }
}