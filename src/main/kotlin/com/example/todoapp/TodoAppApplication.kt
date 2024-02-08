package com.example.todoapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TodoAppApplication
fun main(args: Array<String>) {
    runApplication<TodoAppApplication>(*args)
}
