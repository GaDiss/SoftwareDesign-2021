package spankratov.sd.todolist.dao

import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport
import spankratov.sd.todolist.model.ToDoItem
import spankratov.sd.todolist.model.ToDoList
import javax.sql.DataSource

class ToDoJdbcDao(dataSource: DataSource) : ToDoDao, JdbcDaoSupport() {
    init {
        setDataSource(dataSource)
    }

    override fun getToDoLists(): List<ToDoList> {
        val sql = "select * from ToDoLists"
        return jdbcTemplate!!.query(sql, BeanPropertyRowMapper(ToDoList::class.java))
    }

    override fun addToDoList(toDoList: ToDoList): Int {
        val sql = "insert into ToDoLists (title, description) values (?, ?)"
        return jdbcTemplate!!.update(sql, toDoList.title, toDoList.description)
    }

    override fun cleanToDoItems(listId: Int) {
        val sql = "delete from ToDoItems where listId = ?"
        jdbcTemplate!!.update(sql, listId)
    }

    override fun deleteToDoList(id: Int) {
        val sql = "delete from ToDoLists where id = ?"
        jdbcTemplate!!.update(sql, id)
    }

    override fun getToDoList(listId: Int): ToDoList {
        val sql = "select * from ToDoLists where id = $listId"
        return jdbcTemplate!!.query(sql, BeanPropertyRowMapper(ToDoList::class.java)).single()
    }

    override fun getToDoItemsByListId(listId: Int): List<ToDoItem> {
        val sql = "select * from ToDoItems where listId = $listId"
        return jdbcTemplate!!.query(sql, BeanPropertyRowMapper(ToDoItem::class.java))
    }

    override fun addToDoItem(toDoItem: ToDoItem): Int {
        val sql = "insert into ToDoItems (listId, title, done) values (?, ?, ?)"
        return jdbcTemplate!!.update(sql, toDoItem.listId, toDoItem.title, toDoItem.done)
    }

    override fun markToDoItem(itemId: Int, done: Boolean) {
        val sql = "UPDATE ToDoItems SET done = ? WHERE id = ?"
        jdbcTemplate!!.update(sql, if (done) 1 else 0, itemId)
    }
}