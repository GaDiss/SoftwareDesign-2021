package spankratov.sd.todolist.config

import org.springframework.context.annotation.Bean
import spankratov.sd.todolist.dao.ToDoInMemoryDao

class InMemoryDaoContextConfiguration {
    @Bean
    fun productDao() = ToDoInMemoryDao()
}