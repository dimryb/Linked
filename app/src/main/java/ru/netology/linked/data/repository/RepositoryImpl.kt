package ru.netology.linked.data.repository

import ru.netology.linked.domain.Repository
import ru.netology.linked.domain.dto.Authentication
import ru.netology.linked.domain.dto.Event
import ru.netology.linked.domain.dto.Job
import ru.netology.linked.domain.dto.Post
import javax.inject.Inject

class RepositoryImpl @Inject constructor(

) : Repository {
    override fun getEvents() {
        TODO("Not yet implemented")
    }

    override fun setEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override fun getEventsLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override fun getEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun removeEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun getEventsAfter(count: Int, eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun getEventsBefore(count: Int, eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun likeEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun getEventsNewer(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun getEventParticipants(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun saveMedia(file: String) {
        TODO("Not yet implemented")
    }

    override fun getJobs() {
        TODO("Not yet implemented")
    }

    override fun setJob(job: Job) {
        TODO("Not yet implemented")
    }

    override fun removeJob(jobId: Long) {
        TODO("Not yet implemented")
    }

    override fun getMyWall() {
        TODO("Not yet implemented")
    }

    override fun getMyWallLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override fun getMyWallAfter(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getMyWallBefore(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getMyWallNewer(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPosts() {
        TODO("Not yet implemented")
    }

    override fun setPost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getPostsLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override fun getPost(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun removePost(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPostsAfter(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPostsBefore(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun likePost(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPostsNewer(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getUsers() {
        TODO("Not yet implemented")
    }

//    override fun authentication(authentication: Authentication) {
//        TODO("Not yet implemented")
//    }
//
//    override fun registration(login: String, password: String, name: String, file: String?) {
//        TODO("Not yet implemented")
//    }

    override fun getUser(userId: Long) {
        TODO("Not yet implemented")
    }

    override fun getWall(authorId: Long) {
        TODO("Not yet implemented")
    }

    override fun getWallLatest(authorId: Long, count: Int) {
        TODO("Not yet implemented")
    }

    override fun getWallAfter(authorId: Long, count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getWallBefore(authorId: Long, count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getWallNewer(authorId: Long, postId: Long) {
        TODO("Not yet implemented")
    }
}