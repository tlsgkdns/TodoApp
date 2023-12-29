package com.example.todoapp.domain.comment.service

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.dto.CommentModifyDTO
import com.example.todoapp.domain.comment.dto.CommentPostDTO
import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.comment.repository.CommentRepository
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.domain.member.service.MemberService
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.domain.todo.repository.TodoRepository
import com.example.todoapp.domain.todo.service.TodoService
import com.example.todoapp.infra.exception.ModelNotFoundException
import com.example.todoapp.infra.exception.NotHaveAuthorityException
import com.example.todoapp.infra.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val todoRepository: TodoRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository,
): CommentService
{

    private fun checkTodoAndCommentAreSame(todoId: Long, comment: Comment)
    {
       if(todoId != comment.todo.id)
           throw IllegalStateException("$todoId And ${comment.todo.id} is not Matched!")
    }
    private fun checkUserCanModifyThisComment(comment: Comment)
    {
        if(SecurityUtil.isDifferentWithLoginMember(comment.writer))
            throw NotHaveAuthorityException("comment")
    }
    private fun getValidatedComment(commentId: Long): Comment
    {
        return commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
    }
    @Transactional
    override fun getCommentList(todoId: Long): List<CommentDTO> {
        val todo = todoRepository.findByIdOrNull(todoId) ?: ModelNotFoundException("Todo", todoId)
        return (todo as Todo).getCommentList()
    }
    @Transactional
    override fun getComment(todoId: Long, commentId: Long): CommentDTO {
        val comment = getValidatedComment(commentId)
        checkTodoAndCommentAreSame(todoId, comment)
        return comment.toDTO()
    }
    @Transactional
    override fun postComment(todoId: Long, commentPostDTO: CommentPostDTO): CommentDTO {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        val comment = Comment.from(commentPostDTO, todo, SecurityUtil.getLoginMember(memberRepository))
        return commentRepository.save(comment).toDTO()
    }
    @Transactional
    override fun modifyComment(todoId: Long, commentId: Long, modifyDTO: CommentModifyDTO): CommentDTO {
        val (content, password) = modifyDTO
        val comment = getValidatedComment(commentId)
        checkTodoAndCommentAreSame(todoId, comment)
        checkUserCanModifyThisComment(comment)
        if(password != comment.password) throw IllegalStateException("Password Not Matched!")
        comment.modifyComment(content)
        return commentRepository.save(comment).toDTO()
    }
    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long) {
        val comment = getValidatedComment(commentId)
        checkTodoAndCommentAreSame(todoId, comment)
        checkUserCanModifyThisComment(comment)
        commentRepository.delete(comment)
    }
}