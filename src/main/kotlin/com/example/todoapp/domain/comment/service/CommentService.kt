package com.example.todoapp.domain.comment.service

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.dto.CommentModifyDTO
import com.example.todoapp.domain.comment.dto.CommentPostDTO

interface CommentService {
    fun getCommentList(todoId: Long): List<CommentDTO>
    fun getComment(todoId: Long, commentId: Long): CommentDTO
    fun postComment(todoId: Long, commentPostDTO: CommentPostDTO): CommentDTO

    fun modifyComment(todoId: Long, commentId: Long, modifyDTO: CommentModifyDTO): CommentDTO

    fun deleteComment(todoId: Long, commentId: Long)
}