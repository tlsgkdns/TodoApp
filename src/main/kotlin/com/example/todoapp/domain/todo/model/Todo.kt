package com.example.todoapp.domain.todo.model

import com.example.todoapp.domain.comment.model.Comment
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
    @Column(name = "writer", nullable = false)
    var writer: String,
    @Column(name = "created_date", nullable = false)
    var createdDate: Date,
    @Column(name = "complete_status")
    var complete: Boolean = false,
)