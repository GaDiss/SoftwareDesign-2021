package spankratov.sd.todolist.dao

import spankratov.sd.todolist.model.ToDoItem
import spankratov.sd.todolist.model.ToDoList
import java.util.concurrent.atomic.AtomicInteger

class ToDoInMemoryDao : ToDoDao {
    private val toDoLists = mutableListOf<ToDoList>()
    private val nextToDoListId = AtomicInteger(0)

    private val toDoItems = mutableListOf<ToDoItem>()
    private val nextToDoItemId = AtomicInteger(0)

    override fun getToDoLists() = toDoLists.toList()

    override fun addToDoList(toDoList: ToDoList): Int {
        val id = nextToDoListId.getAndIncrement()
        toDoList.id = id
        toDoLists.add(toDoList)
        return id
    }

    override fun cleanToDoItems(listId: Int) {
        toDoItems.removeIf { it.listId == listId }
    }

    override fun deleteToDoList(id: Int) {
        toDoLists.removeIf { it.id == id }
    }

    override fun getToDoList(listId: Int): ToDoList {
        return toDoLists.single { it.id == listId }
    }

    override fun getToDoItemsByListId(listId: Int): List<ToDoItem> {
        return toDoItems.filter { it.listId == listId }.toList()
    }

    override fun addToDoItem(toDoItem: ToDoItem): Int {
        val id = nextToDoItemId.getAndIncrement()
        toDoItem.id = id
        toDoItems.add(toDoItem)
        return id
    }

    override fun markToDoItem(itemId: Int, done: Boolean) {
        toDoItems.single { it.id == itemId }.done = done
    }
}