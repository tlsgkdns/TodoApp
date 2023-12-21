package com.example.todoapp.domain.comment.model

import com.example.todoapp.domain.todo.model.Todo
import jakarta.persistence.*

@Entity
@Table(name = "comment")
data class Comment(
    @Column(name = "content")
    var content: String,
    @Column(name = "writer")
    var writer: String,
    @Column(name = "password")
    var password: String,
    @ManyToOne
    @JoinColumn(name = "todo_id")
    val todo: Todo
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;

    fun modifyComment(content: String)
    {
        this.content = content
    }
}
