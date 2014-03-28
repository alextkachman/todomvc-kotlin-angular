package todo

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

fun main(args: Array<String>) {
    module("todomvc") {
        factory(TodoStorage)

        factory("todoService") {
            TodoService()
        }

        directive("todoFocus", todoFocus)
        directive("todoBlur", todoBlur)

        controller(TodoCtrl)
    }
}