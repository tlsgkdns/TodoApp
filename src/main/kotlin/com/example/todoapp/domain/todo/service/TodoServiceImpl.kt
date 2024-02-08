package com.example.todoapp.domain.todo.service

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.repository.CommentRepository
import com.example.todoapp.domain.comment.service.CommentService
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.domain.member.service.MemberService
import com.example.todoapp.domain.todo.dto.SearchKeywordDTO
import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.example.todoapp.domain.todo.dto.TodoModifyDTO
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.domain.todo.repository.TodoRepository
import com.example.todoapp.infra.exception.ModelNotFoundException
import com.example.todoapp.infra.exception.NotHaveAuthorityException
import com.example.todoapp.infra.security.SecurityUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Writer
import java.util.stream.Collectors

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val memberRepository: MemberRepository
): TodoService {
    @Transactional
    override fun getTodo(todoId: Long): TodoDTO {
        val todo = getValidatedTodo(todoId)
        return TodoDTO.from(todo)
    }
    @Transactional
    override fun getTodos(pageable: Pageable, searchKeywordDTO: SearchKeywordDTO?, orderByASC: Boolean): Page<TodoDTO> {
        val list = todoRepository.getTodos(pageable, orderByASC, searchKeywordDTO)
        return list.map { TodoDTO.from(it) }
    }
    @Transactional
    override fun modifyTodo(todoId: Long, todoModifyDTO: TodoModifyDTO): TodoDTO {
        val todo = getValidatedTodo(todoId)
        val (title, content) = todoModifyDTO
        SecurityUtil.checkWriterEqualsLoginMember(todo, memberRepository)
        return TodoDTO.from(todoRepository.save(todo.apply
            { todo.title = title; todo.content = content }))
    }
    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = getValidatedTodo(todoId)
        SecurityUtil.checkWriterEqualsLoginMember(todo, memberRepository)
        return todoRepository.delete(todo)
    }
    @Transactional
    override fun createTodo(createDTO: TodoCreateDTO): TodoDTO {
        return TodoDTO.from(todoRepository.save(createDTO.to()))
    }
    private fun getValidatedTodo(todoId: Long): Todo
    {
        return todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId);
    }
}