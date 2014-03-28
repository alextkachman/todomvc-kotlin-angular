package todo

import js.LocalStorage
import angular.*

class TodoService() : Service() {
    class object : ServiceFactory<TodoService>("todoService") {
        override fun create() = TodoService()
    }

    val todoStorage = TodoStorage.instance(this)
    var todos = todoStorage.get()

    fun addTodo(newTodo: String) {
        if(newTodo.length > 0) {
            todos.push(Todo(newTodo))
        }
    }

    fun remove(todo: Todo) {
        todos.splice(todos.indexOf(todo), 1)
    }

    fun save() {
        todoStorage.put(todos)
    }

    fun markAll(completed: Boolean) {
        todos.forEach({ it.completed = completed })
    }

    fun clearCompletedTodos() {
        todos = todos.filter({ !it.completed })
    }
}
