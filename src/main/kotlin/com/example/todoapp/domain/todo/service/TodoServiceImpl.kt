package com.example.todoapp.domain.todo.service

import com.example.todoapp.domain.comment.repository.CommentRepository
import com.example.todoapp.domain.comment.service.CommentService
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.repository.MemberRepository
import com.example.todoapp.domain.member.service.MemberService
import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.example.todoapp.domain.todo.dto.TodoModifyDTO
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.domain.todo.repository.TodoRepository
import com.example.todoapp.infra.exception.ModelNotFoundException
import com.example.todoapp.infra.exception.NotHaveAuthorityException
import com.example.todoapp.infra.security.SecurityUtil
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
        return todo.toDTO()
    }
    @Transactional
    override fun getTodos(orderByASC: Boolean, writer: String?, pageable: Pageable): List<TodoDTO> {
        val list = if(writer != null)
        {
            if(orderByASC) {
                todoRepository.findByWriterUsernameOrderByCreatedDateAsc(pageable, writer)
            }
            else {
                todoRepository.findByWriterUsernameOrderByCreatedDateDesc(pageable, writer)
            }
        }
        else
        {
            if(orderByASC)
            {
                todoRepository.findByOrderByCreatedDateAsc(pageable)
            }
            else
            {
                todoRepository.findByOrderByCreatedDateDesc(pageable)
            }
        }

        return list.map { it.toDTO() }
    }
    @Transactional
    override fun modifyTodo(todoId: Long, todoModifyDTO: TodoModifyDTO): TodoDTO {
        val todo = getValidatedTodo(todoId)
        val (title, content) = todoModifyDTO
        SecurityUtil.checkUserCanAccessThis(todo.writer, "Todo")
        todo.title = title; todo.content = content;
        return todoRepository.save(todo).toDTO()
    }
    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = getValidatedTodo(todoId)
        SecurityUtil.checkUserCanAccessThis(todo.writer, "Todo")
        return todoRepository.delete(todo)
    }
    @Transactional
    override fun createTodo(createDTO: TodoCreateDTO): TodoDTO {
        return todoRepository.save(Todo.from(createDTO, SecurityUtil.getLoginMember(memberRepository))).toDTO()
    }
    private fun Todo.toDTO(): TodoDTO {
        return TodoDTO(
            id = id!!,
            title = title,
            content = content,
            writer = writer.id!!,
            createdDate = createdDate,
            complete = complete,
            commentList = commentSet.map { it.toDTO() }
        )
    }
    private fun getValidatedTodo(todoId: Long): Todo
    {
        return todoRepository.findByIdOrNull(todoId)
            ?: throw ModelNotFoundException("Todo", todoId);
    }
}