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

class TodoService() {
    class object {
        private val STORAGE_ID = "TODOS-angularjs"
    }

    var todos = {
        var data = localStorage.getItem(STORAGE_ID)
        if (data == null) {
            data = "[]"
        }
        Ng.log.info(data!!)
        JSON.parse<Array<Todo>>(data!!)
    }()

    fun addTodo(newTodo: String) {
        if(newTodo.length > 0) {
            todos.push(Todo(newTodo))
        }
    }

    fun remove(todo: Todo) {
        todos.splice(todos.indexOf(todo), 1)
    }

    fun save() {
        val stringify = JSON.stringify(todos)
        Ng.log.info(stringify)
        localStorage.setItem(STORAGE_ID, stringify)
    }

    fun markAll(completed: Boolean) {
        todos.forEach({ it.completed = completed })
    }

    fun clearCompletedTodos() {
        todos = todos.filter({ !it.completed })
    }
}
