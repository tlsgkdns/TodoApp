package com.example.todoapp.infra.exception

class NotHaveAuthorityException (val model: String): RuntimeException("Don't have authority to access: $model")