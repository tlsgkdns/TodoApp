package com.example.todoapp.domain.todo.model

import jakarta.persistence.*
import org.hibernate.annotations.Comments
import java.util.Date

@Entity
@Table(name = "todo")
data class Todo(
    @Column(name = "title", nullable = false)
    var title: String,
    @Column(name = "content", nullable = false)
    var content: String,
    @Column(name = "writer", nullable = false)
    var writer: String,
    @Column(name = "created_date", nullable = false)
    var createdDate: Date,
    @Enumerated(EnumType.STRING)
    @Column(name = "complete")
    var complete: CompletStatus = CompletStatus.INCOMPLETE,
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}