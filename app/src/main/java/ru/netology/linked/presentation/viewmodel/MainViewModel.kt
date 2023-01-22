package ru.netology.linked.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import ru.netology.linked.domain.Repository
import ru.netology.linked.domain.dto.*
import ru.netology.linked.presentation.auth.AppAuth
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
    private val appAuth: AppAuth,
) : ViewModel() {

    private val cachedPosts = repository
        .data
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val data: Flow<PagingData<FeedItem>> = appAuth.authStateFlow
        .flatMapLatest { cachedPosts }

    private val cachedEvents = repository
        .eventsDataPagingFlow
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val dataEvens: Flow<PagingData<FeedItem>> = appAuth.authStateFlow
        .flatMapLatest { cachedEvents }

    private val _state = MutableLiveData<FeedModelState>()
    val state: LiveData<FeedModelState>
        get() = _state

    private val _menuChecked = MutableLiveData<MenuChecked>()
    val menuChecked: LiveData<MenuChecked>
        get() = _menuChecked

    private val _menuAction = MutableLiveData(MenuAction.IDLE)
    val menuAction: LiveData<MenuAction>
        get() = _menuAction

    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        getPosts()
    }

    private fun setMenuChecked(checked: MenuChecked){
        _menuChecked.value = checked
    }

    fun bottomMenuAction(action: MenuAction) {
        menuAction.value?.let {
            if (it != action){
                _menuAction.value = action
            }
        }

        when(action) {
            MenuAction.HOME -> {
                getPosts()
                setMenuChecked(MenuChecked.HOME)
            }
            MenuAction.USERS -> {
                getUsers()
                setMenuChecked(MenuChecked.USERS)
            }
            MenuAction.EVENTS -> {
                getEvents()
                setMenuChecked(MenuChecked.EVENTS)
            }
            MenuAction.ADD -> {

            }
            else -> {}
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            _postCreated.value = Unit
            try {
                repository.getPosts()
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
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
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }

    fun refreshPost() {
        viewModelScope.launch {
            _state.value = FeedModelState.Refresh
            try {
                repository.getPosts()
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }

    fun likePost(post: Post) {
        viewModelScope.launch {
            try {
                repository.likePost(post)
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            _postCreated.value = Unit
            try {
                repository.getUsers()
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }

    fun getEvents() {
        viewModelScope.launch {
            _postCreated.value = Unit
            try {
                repository.getEvents()
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }
}