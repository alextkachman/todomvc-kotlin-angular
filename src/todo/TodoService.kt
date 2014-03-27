package todo

import js.LocalStorage
import angular.*

native class Todo() {
    var title: String? = js.noImpl
    var completed: Boolean = js.noImpl
}

fun Todo(title: String, completed: Boolean = false) : Todo {
    val todo = json() as Todo
    todo.title = title
    todo.completed = completed
    return todo
}


fun InjectorAware.ngTodoService() = instance<TodoService>("todoService")

class TodoService() : Service() {
    val todoStorage = ngTodoStorage()
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
