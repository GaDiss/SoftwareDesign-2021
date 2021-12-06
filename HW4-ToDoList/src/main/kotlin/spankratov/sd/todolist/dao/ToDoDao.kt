package spankratov.sd.todolist.dao

import spankratov.sd.todolist.model.ToDoItem
import spankratov.sd.todolist.model.ToDoList

interface ToDoDao {

    fun getToDoLists(): List<ToDoList>

    fun addToDoList(toDoList: ToDoList): Int

    fun cleanToDoItems(listId: Int)

    fun deleteToDoList(id: Int)

    fun getToDoList(listId: Int): ToDoList

    fun getToDoItemsByListId(listId: Int): List<ToDoItem>

    fun addToDoItem(toDoItem: ToDoItem): Int

    fun markToDoItem(itemId: Int, done: Boolean)
}