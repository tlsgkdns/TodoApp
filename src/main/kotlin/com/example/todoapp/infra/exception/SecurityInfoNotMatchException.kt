package com.example.todoapp.infra.exception

data class SecurityInfoNotMatchException(val password: String):
    RuntimeException("$password is not matched!")