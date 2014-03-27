package todo

import angular.*

val todomvc = module("todomvc") {
    factory("todoStorage") {
        TodoStorage()
    }
    factory("todoService") {
        TodoService()
    }
    directive("todoFocus") {
        TodoFocus()
    }
    directive("todoBlur") {
        TodoBlur()
    }
    controller(TodoCtrl())
}

fun main(args: Array<String>) {
    todomvc
}