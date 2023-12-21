package com.example.todoapp.domain.comment.controller

import com.example.todoapp.domain.comment.dto.CommentDTO
import com.example.todoapp.domain.comment.dto.CommentModifyDTO
import com.example.todoapp.domain.comment.dto.CommentPostDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos/{todoId}/comments")
class CommentController {

    @GetMapping()
    fun getCommentList(@PathVariable todoId: Long): ResponseEntity<List<CommentDTO>>{
        TODO()
    }

    @GetMapping("/{commentId}")
    fun getComment(@PathVariable todoId: Long, @PathVariable commentId: Long): ResponseEntity<CommentDTO>{
        TODO()
    }

    @PostMapping("/{commentId}")
    fun postComment(@PathVariable todoId: Long, @PathVariable commentId: Long,
                    @RequestBody commentPostDTO: CommentPostDTO
    ): ResponseEntity<CommentDTO> {
        TODO()
    }
    @PutMapping("/{commentId}")
    fun modifyComment(@PathVariable todoId: Long, @PathVariable commentId: Long,
                    @RequestBody commentPostDTO: CommentPostDTO
    ): ResponseEntity<CommentDTO> {
        TODO()
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable todoId: Long, @PathVariable commentId: Long): ResponseEntity<Unit>
    {
        TODO()
    }
}