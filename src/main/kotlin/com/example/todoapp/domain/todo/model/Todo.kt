package com.example.todoapp.domain.todo.model

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.model.Comment
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.member.service.MemberService
import com.example.todoapp.domain.todo.dto.TodoCreateDTO
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
    var createdDate: LocalDateTime,
    @Column(name = "complete_status")
    var complete: Boolean = false,
)
{
    @get:EntityGraph(attributePaths = ["comment"])
    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val commentSet: MutableSet<Comment> = mutableSetOf()
    fun getCommentList(): List<CommentDTO>
    {
        return commentSet.map { it.toDTO() }
    }
    companion object {
        fun from(createDTO: TodoCreateDTO, writer: Member): Todo
        {
            return Todo(title = createDTO.title, content = createDTO.content,
                writer = writer, createdDate = createDTO.createdDate, complete = false)
        }
    }

}