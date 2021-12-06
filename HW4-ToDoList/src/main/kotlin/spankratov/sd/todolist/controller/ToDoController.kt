package spankratov.sd.todolist.controller


import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import spankratov.sd.todolist.dao.ToDoDao
import spankratov.sd.todolist.model.ToDoItem
import spankratov.sd.todolist.model.ToDoList

@Controller
class ToDoController(private val toDoDao: ToDoDao) {

    @GetMapping("/")
    fun getToDoLists(map: ModelMap): String {
        val todoLists = toDoDao.getToDoLists()
        map.addAttribute("toDoLists", todoLists)
        map.addAttribute("toDoList", ToDoList())
        return "index"
    }

    @PostMapping("/add-todo-list")
    fun addToDoList(@ModelAttribute("toDoList") toDoList: ToDoList): String {
        toDoDao.addToDoList(toDoList)
        return "redirect:/"
    }

    @PostMapping("/delete-todo-list")
    fun deleteToDoList(@ModelAttribute("id") id: Int): String {
        toDoDao.cleanToDoItems(id)
        toDoDao.deleteToDoList(id)
        return "redirect:/"
    }

    @GetMapping("/todo-list")
    fun getToDoList(@RequestParam listId: Int, map: ModelMap): String {
        val todoList = toDoDao.getToDoList(listId)
        todoList.items = toDoDao.getToDoItemsByListId(listId)
        map.addAttribute("toDoList", todoList)
        map.addAttribute("toDoItem", ToDoItem())
        return "todo-list"
    }

    @PostMapping("/add-todo-item")
    fun addToDoItem(@ModelAttribute("toDoItem") toDoItem: ToDoItem): String {
        toDoDao.addToDoItem(toDoItem)
        return "redirect:/todo-list?listId=${toDoItem.listId}"
    }

    @PostMapping("/mark-todo-item")
    fun markToDoItem(
        @ModelAttribute("listId") listId: Int,
        @ModelAttribute("id") id: Int,
        @ModelAttribute("done") done: Boolean,
    ): String {
        toDoDao.markToDoItem(id, done)
        return "redirect:/todo-list?listId=${listId}"
    }
}