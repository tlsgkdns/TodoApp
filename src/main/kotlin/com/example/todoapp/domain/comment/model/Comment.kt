package com.example.todoapp.domain.comment.model

import com.example.todoapp.domain.common.Post
import com.example.todoapp.domain.todo.model.Todo
import jakarta.persistence.*

@Entity
@Table(name = "comment")
@DiscriminatorValue("COMMENT")
data class Comment(
    @Column(name = "content")
    var content: String,
    @Column(name = "password")
    var password: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todo: Todo
): Post()
{
    fun modifyComment(content: String)
    {
        this.content = content
    }
}
