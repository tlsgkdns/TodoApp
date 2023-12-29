package com.example.todoapp.domain.todo.model

import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.service.MemberService
import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import jakarta.persistence.*
import org.hibernate.annotations.Comments
import java.util.Date

@Entity
@Table(name = "todo")
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "title", nullable = false)
    var title: String,
    @Column(name = "content", nullable = false)
    var content: String,
    @ManyToOne
    @JoinColumn(name = "writer")
    var writer: Member,
    @Column(name = "created_date", nullable = false)
    var createdDate: Date,
    @Column(name = "complete_status")
    var complete: Boolean = false,
)
{
    companion object {
        fun from(createDTO: TodoCreateDTO, writer: Member): Todo
        {
            return Todo(title = createDTO.title, content = createDTO.content,
                writer = writer, createdDate = createDTO.createdDate, complete = false)
        }
    }
}