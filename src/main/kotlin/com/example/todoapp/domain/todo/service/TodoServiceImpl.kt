package com.example.todoapp.domain.todo.service

import com.example.todoapp.domain.member.model.Member
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
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Writer

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val memberService: MemberService
): TodoService {
    override fun getTodo(todoId: Long): TodoDTO {
        val todo = getValidatedTodo(todoId)
        return todo.toDTO()
    }
    override fun getTodoList(orderByASC: Boolean, writer: String?, pageable: Pageable): List<TodoDTO> {
        val list = if(orderByASC) todoRepository.getTodoListWithAsc(pageable)
        else todoRepository.getTodoListWithDesc(pageable)
        writer ?: return list.map { it.toDTO() }
        return list.map { it.toDTO() }.filter { it.writer == memberService.getMember(writer).id}
    }
    @PreAuthorize("hasAuthority('USER')")
    @Transactional
    override fun modifyTodo(todoId: Long, todoModifyDTO: TodoModifyDTO): TodoDTO {
        val todo = getValidatedTodo(todoId)
        val (title, content) = todoModifyDTO
        if(SecurityUtil.isDifferentWithLoginMember(todo.writer)) throw NotHaveAuthorityException("Todo")
        todo.title = title; todo.content = content;
        return todoRepository.save(todo).toDTO()
    }
    @PreAuthorize("hasAuthority('USER')")
    override fun deleteTodo(todoId: Long) {
        val todo = getValidatedTodo(todoId)
        if(SecurityUtil.isDifferentWithLoginMember(todo.writer)) throw NotHaveAuthorityException("Todo")
        return todoRepository.delete(todo)
    }
    override fun createTodo(createDTO: TodoCreateDTO): TodoDTO {
        return todoRepository.save(Todo.from(createDTO, SecurityUtil.getLoginMember(memberService))).toDTO()
    }

    private fun Todo.toDTO(): TodoDTO {
        return TodoDTO(
            id = id!!,
            title = title,
            content = content,
            writer = writer.id!!,
            createdDate = createdDate,
            complete = complete
        )
    }
    private fun getValidatedTodo(todoId: Long): Todo
    {
        return todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId);
    }
}