package ru.netology.linked.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.linked.domain.Repository
import ru.netology.linked.presentation.model.FeedModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val data: LiveData<FeedModel> = repository.data.map { films ->
        FeedModel(films, films.isEmpty())
    }.asLiveData(Dispatchers.Default)

    fun getPosts(){
        viewModelScope.launch {
            try {
                repository.getPosts()
            } catch (e: Exception) {
                TODO("getPosts Exception")
            }
        }
    }

    fun savePost() {
        TODO("Not yet implemented")
    }

    fun editContent(toString: String) {

    }
}