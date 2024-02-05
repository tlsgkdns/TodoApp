package com.example.todoapp.infra.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAop {

    @Around("execution(* com.example.todoapp.domain.todo.service..*.*(..))")
    fun loggingTodoService(proceedingJoinPoint: ProceedingJoinPoint): Any?
    {
        val method = (proceedingJoinPoint.signature as MethodSignature).method
        println("${method.name} 메소드를 실행합니다.")
        val ret = proceedingJoinPoint.proceed()
        println("${method.name} 메소드 실행을 마쳤습니다.")
        return ret
    }
}