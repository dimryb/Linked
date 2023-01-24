package ru.netology.linked.presentation.viewmodel

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.linked.domain.Repository
import ru.netology.linked.domain.dto.*
import ru.netology.linked.presentation.auth.AppAuth
import ru.netology.linked.presentation.util.SingleLiveEvent
import javax.inject.Inject

private val emptyPost = Post(
    id = 0,
    content = "",
    author = "",
    authorId = 0L,
    authorAvatar = "",
    likedByMe = false,
    published = "",
    mentionedMe = false,
    ownedByMe = false,
    users = emptyMap(),
)

private val emptyEvent = Event(
    id = 0,
    authorId = 0,
    author = "",
    content = "",
    datetime = "",
    published = "",
    type = EventType.OFFLINE,
    likedByMe = false,
    participatedByMe = false,
    ownerByMe = false,
    users = emptyMap(),
)

private val noPhoto = PhotoModel()

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

    val usersData: LiveData<List<User>> = repository.usersDataFlow.asLiveData(Dispatchers.Default)

    private val _state = MutableLiveData<FeedModelState>()
    val state: LiveData<FeedModelState>
        get() = _state

    private val _menuChecked = MutableLiveData<MenuChecked>()
    val menuChecked: LiveData<MenuChecked>
        get() = _menuChecked

    private val _menuAction = MutableLiveData(MenuAction.IDLE)
    val menuAction: LiveData<MenuAction>
        get() = _menuAction

    val editedPost = MutableLiveData(emptyPost)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    val editedEvent = MutableLiveData(emptyEvent)
    private val _eventCreated = SingleLiveEvent<Unit>()
    val eventCreated: LiveData<Unit>
        get() = _eventCreated

    private val _photo = MutableLiveData<PhotoModel?>(null)
    val photo: LiveData<PhotoModel?>
        get() = _photo

    init {
        getPosts()
    }

    fun navigationUp() {
        _menuAction.value = MenuAction.UP
    }

    private fun setMenuChecked(checked: MenuChecked) {
        _menuChecked.value = checked
    }

    fun bottomMenuAction(action: MenuAction) {
        menuAction.value?.let {
            if (it != action) {
                _menuAction.value = action
            }
        }

        when (action) {
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
        editedPost.value?.let { post ->
            viewModelScope.launch {
                _postCreated.value = Unit
                try {
                    repository.setPost(post, _photo.value?.uri?.let { MediaUpload(it.toFile()) })
                    _state.value = FeedModelState.Idle
                } catch (e: Exception) {
                    _state.value = FeedModelState.Error
                }
            }
        }
        editedPost.value = emptyPost
        _photo.value = noPhoto
    }

    fun editPost(post: Post) {
        editedPost.value = post
    }

    fun editPostContent(content: String) {
        val value = editedPost.value
        value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            editedPost.value = it.copy(content = text)
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

    //events

    fun getEvents() {
        viewModelScope.launch {
            _eventCreated.value = Unit
            try {
                repository.getEvents()
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }

    fun saveEvent() {
        editedEvent.value?.let { event ->
            viewModelScope.launch {
                _eventCreated.value = Unit
                try {
                    repository.setEvent(event, _photo.value?.uri?.let { MediaUpload(it.toFile()) })
                    _state.value = FeedModelState.Idle
                } catch (e: Exception) {
                    _state.value = FeedModelState.Error
                }
            }
        }
        editedPost.value = emptyPost
        _photo.value = noPhoto
    }

    fun editEvent(event: Event) {
        editedEvent.value = event
    }

    fun editEventContent(content: String, dateTime: String) {
        val value = editedEvent.value
        value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            editedEvent.value = it.copy(content = text, datetime = dateTime)
        }
    }

    fun removeEventById(eventId: Long) {
        viewModelScope.launch {
            try {
                repository.removeEvent(eventId)
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }

    fun refreshEvent() {
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

    fun likeEvent(event: Event) {
        viewModelScope.launch {
            try {
                repository.likeEvent(event)
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }

    // user
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

    fun changePhoto(uri: Uri?) {
        _photo.value = PhotoModel(uri)
    }
}