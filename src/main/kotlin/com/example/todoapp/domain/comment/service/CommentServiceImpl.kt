package com.example.todoapp.domain.comment.service

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.dto.CommentModifyDTO
import com.example.todoapp.domain.comment.dto.CommentPostDTO
import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.comment.repository.CommentRepository
import com.example.todoapp.domain.member.service.MemberService
import com.example.todoapp.domain.todo.service.TodoService
import com.example.todoapp.infra.exception.ModelNotFoundException
import com.example.todoapp.infra.exception.NotHaveAuthorityException
import com.example.todoapp.infra.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val todoService: TodoService,
    private val memberService: MemberService,
    private val commentRepository: CommentRepository,
): CommentService
{
    private fun checkTodoAndCommentAreSame(todoId: Long, comment: Comment)
    {
       if(todoId != comment.todo.id)
           throw IllegalStateException("$todoId And ${comment.todo.id} is not Matched!")
    }
    private fun checkUserWroteThisComment(comment: Comment)
    {
        if(SecurityUtil.isDifferentWithLoginMember(comment.writer))
            throw NotHaveAuthorityException("comment")
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
        val comment = Comment.from(commentPostDTO, todoService.getTodo(todoId).toEntity(memberService)
            , SecurityUtil.getLoginMember(memberService))
        return commentRepository.save(comment).toDTO()
    }
    override fun modifyComment(todoId: Long, commentId: Long, modifyDTO: CommentModifyDTO): CommentDTO {
        val (content, password) = modifyDTO
        val comment = getValidatedComment(commentId)
        checkTodoAndCommentAreSame(todoId, comment)
        checkUserWroteThisComment(comment)
        if(password != comment.password) throw IllegalStateException("Password Not Matched!")
        comment.modifyComment(content)
        return commentRepository.save(comment).toDTO()
    }

    @PreAuthorize("hasAuthority('USER')")
    override fun deleteComment(todoId: Long, commentId: Long) {
        val comment = getValidatedComment(commentId)
        checkTodoAndCommentAreSame(todoId, comment)
        checkUserWroteThisComment(comment)
        commentRepository.delete(comment)
    }
    private fun Comment.toDTO(): CommentDTO{
        return CommentDTO(
            id = id,
            content = content,
            writer = writer.id!!
        )
    }
}