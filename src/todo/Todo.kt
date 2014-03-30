package todo

import angular.*
import angular.qunit.*
import todo.mock.MockStorage

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

    testModule("todomvc-tests",array("todomvc")) {
        test("TodoStorage exist") {
            ok(ijTodoStorage != null, "ijTodoStorage != null")
            ok(ijTodoStorage is ITodoStorage && ijTodoStorage is TodoStorage, "ijTodoStorage has right class")
        }
    }

    testModule("todomvc-tests-mocks",array("todomvc")) {
        factory(MockStorage)

        test("tests initialized") {
            ok(true, "Everything is OK")
        }

        test("TodoStorage exist") {
            ok(ijTodoStorage != null, "ijTodoStorage != null")
            ok(ijTodoStorage is ITodoStorage, "ijTodoStorage is ITodoStorage")
            ok(ijTodoStorage is MockStorage, "ijTodoStorage is mock")
        }

        test("TodoService exist") {
            ok(ijTodoService != null, "ijTodoService != null")
            ok(ijTodoService is TodoService, "ijTodoService has right class")
        }

        test("TodoService.add increments todos.length") {
            var before = ijTodoService.todos.length
            ijTodoService.addTodo("lala from test")
            ok(ijTodoService.todos.length == before + 1, "length incremets")
        }

        test("TodoService.add empty does not increments todos.length") {
            var before = ijTodoService.todos.length
            ijTodoService.addTodo("")
            ok(ijTodoService.todos.length == before, "length incremets")
        }
    }
}