package com.example.todoapp.domain.comment.service

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.dto.CommentModifyDTO
import com.example.todoapp.domain.comment.dto.CommentPostDTO
import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.comment.repository.CommentRepository
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.domain.todo.service.TodoService
import com.example.todoapp.infra.exception.ModelNotFoundException
import com.example.todoapp.infra.exception.SecurityInfoNotMatchException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val todoService: TodoService,
    private val commentRepository: CommentRepository,
): CommentService
{
    private fun checkTodoAndCommentAreSame(todoId: Long, comment: Comment)
    {
       if(todoId != comment.todo.id)
           throw IllegalStateException("$todoId And ${comment.todo.id} is not Matched!")
    }
    private fun getValidatedComment(commentId: Long): Comment
    {
        return commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
    }
    override fun getCommentList(todoId: Long): List<CommentDTO> {
        val todo = todoService.getTodo(todoId)
        return commentRepository.findAllByTodoId(todo.id).map { it.toDTO() }
    }

    override fun getComment(todoId: Long, commentId: Long): CommentDTO {
        val comment = getValidatedComment(commentId)
        checkTodoAndCommentAreSame(todoId, comment)
        return comment.toDTO()
    }

    override fun postComment(todoId: Long, commentPostDTO: CommentPostDTO): CommentDTO {
        val (id, title, content, writer, createdDate, complete) = todoService.getTodo(todoId);
        val comment = Comment(content = commentPostDTO.content, writer = commentPostDTO.writer,
            password = commentPostDTO.password, todo = Todo(id, title, content, writer, createdDate, complete))
        return commentRepository.save(comment).toDTO()
    }

    override fun modifyComment(todoId: Long, commentId: Long, modifyDTO: CommentModifyDTO): CommentDTO {
        val (content, writer, password) = modifyDTO
        val comment = getValidatedComment(commentId)
        checkTodoAndCommentAreSame(todoId, comment)
        if(writer != comment.writer || password != comment.password) throw SecurityInfoNotMatchException("$writer $password")
        comment.modifyComment(content)
        return commentRepository.save(comment).toDTO()
    }

    override fun deleteComment(todoId: Long, commentId: Long) {
        val comment = getValidatedComment(commentId)
        checkTodoAndCommentAreSame(todoId, comment)
        commentRepository.delete(comment)
    }
    private fun Comment.toDTO(): CommentDTO{
        return CommentDTO(
            id = id,
            content = content,
            writer = writer
        )
    }
}