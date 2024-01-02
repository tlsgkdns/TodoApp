package com.example.todoapp.domain.comment.model

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.dto.CommentPostDTO
import com.example.todoapp.domain.member.model.Member
import com.example.todoapp.domain.todo.model.Todo
import jakarta.persistence.*

@Entity
@Table(name = "comment")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "content")
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "writer")
    var writer: Member?,
    @Column(name = "password")
    var password: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todo: Todo
)
{
    fun modifyComment(content: String)
    {
        this.content = content
    }
    fun toDTO(): CommentDTO = CommentDTO(
        id = id,
        content = content,
        writer = writer?.id!!
    )
    companion object {
        fun from(contentPostDTO: CommentPostDTO, todo: Todo, writer: Member): Comment
        {
            return Comment(content = contentPostDTO.content, writer = writer,
                password = contentPostDTO.password, todo = todo)
        }
    }
}
