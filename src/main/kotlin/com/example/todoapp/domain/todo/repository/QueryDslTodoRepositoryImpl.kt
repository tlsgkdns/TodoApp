package com.example.todoapp.domain.todo.repository

import com.example.todoapp.domain.todo.dto.SearchKeywordDTO
import com.example.todoapp.domain.todo.model.QTodo
import com.example.todoapp.domain.todo.model.Todo
import com.example.todoapp.infra.querydsl.QueryDslSupport
import com.fasterxml.jackson.databind.ObjectMapper
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.core.types.dsl.StringExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import kotlin.reflect.full.memberProperties

class QueryDslTodoRepositoryImpl: QueryDslTodoRepository, QueryDslSupport() {

    private val todo = QTodo.todo
    private val keywordMap = mapOf(Pair("title", todo.title),
        Pair("content", todo.content))
    private fun getOrder(pageable: Pageable, path: EntityPathBase<*>, orderByAsc: Boolean): Array<OrderSpecifier<*>>
    {
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map { order ->
            OrderSpecifier(
                if(orderByAsc) Order.ASC else Order.DESC,
                pathBuilder.get(order.property) as Expression<Comparable<*>>
            )
        }.toTypedArray()
    }
    override fun getTodos(pageable: Pageable, orderByAsc: Boolean, searchKeywordDTO: SearchKeywordDTO?): Page<Todo>
    {
        var whereClause = BooleanBuilder(todo.id.isNotNull)
        searchKeywordDTO?.also {
            whereClause = BooleanBuilder()
            SearchKeywordDTO::class.memberProperties.forEach(){
                    search -> whereClause.or((keywordMap[search.name] as StringExpression)
                .contains(search.get(it).toString()))}
        }
        val content = queryFactory.selectFrom(todo)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrder(pageable, todo, orderByAsc))
            .fetch()
        return PageImpl(content, pageable, queryFactory.select(todo.count()).from(todo).fetchOne() ?: 0L)
    }
}