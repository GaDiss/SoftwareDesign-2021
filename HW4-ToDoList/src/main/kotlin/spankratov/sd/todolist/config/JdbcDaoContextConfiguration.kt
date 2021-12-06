package spankratov.sd.todolist.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import spankratov.sd.todolist.dao.ToDoJdbcDao
import javax.sql.DataSource

@Configuration
class JdbcDaoContextConfiguration {
    @Bean
    fun productJdbcDao(dataSource: DataSource): ToDoJdbcDao {
        return ToDoJdbcDao(dataSource)
    }

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.sqlite.JDBC")
        dataSource.url = "jdbc:sqlite:todolist.db"
        dataSource.username = ""
        dataSource.password = ""
        return dataSource
    }
}