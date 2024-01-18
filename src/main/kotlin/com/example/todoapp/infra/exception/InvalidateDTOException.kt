package com.example.todoapp.infra.exception

data class InvalidateDTOException(val dto: String, val msg: String):
    RuntimeException("$dto has Invalid Value: $msg")