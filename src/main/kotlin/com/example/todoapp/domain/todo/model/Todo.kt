package com.example.todoapp.domain.todo.model

import com.example.todoapp.domain.common.Post
import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.model.Comment
import jakarta.persistence.*
import org.springframework.data.jpa.repository.EntityGraph

@Entity
@Table(name = "todo")
@DiscriminatorValue("TODO")
data class Todo(
    @Column(name = "title", nullable = false)
    var title: String,
    @Column(name = "content", nullable = false)
    var content: String,
    @Column(name = "complete_status")
    var complete: Boolean = false,
): Post()
{
    @get:EntityGraph(attributePaths = ["comment"])
    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val commentSet: MutableSet<Comment> = mutableSetOf()
    fun getComments(): List<CommentDTO>
    {
        return commentSet.map { CommentDTO.from(it) }
    }
}