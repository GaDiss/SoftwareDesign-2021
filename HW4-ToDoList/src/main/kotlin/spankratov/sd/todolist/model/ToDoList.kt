package spankratov.sd.todolist.model

class ToDoList {
    var id: Int = 0
    var title: String = ""
    var description: String = ""

    var items: List<ToDoItem>? = null
}