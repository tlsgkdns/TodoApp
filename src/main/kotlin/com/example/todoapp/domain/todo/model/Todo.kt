package com.example.todoapp.domain.todo.model

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.service.MemberService
import com.example.todoapp.domain.todo.dto.TodoCreateDTO
import com.example.todoapp.domain.todo.dto.TodoDTO
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import jakarta.persistence.*
import org.hibernate.annotations.Comments
import org.springframework.data.jpa.repository.EntityGraph
import java.time.LocalDateTime
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    var writer: Member,
    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),
    @Column(name = "complete_status")
    var complete: Boolean = false,
)
{
    @get:EntityGraph(attributePaths = ["comment"])
    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val commentSet: MutableSet<Comment> = mutableSetOf()
    fun getComments(): List<CommentDTO>
    {
        return commentSet.map { CommentDTO.from(it) }
    }
    companion object {
        fun from(createDTO: TodoCreateDTO, writer: Member): Todo
        {
            return Todo(title = createDTO.title, content = createDTO.content,
                writer = writer, createdDate = createDTO.createdDate, complete = false)
        }
    }

}