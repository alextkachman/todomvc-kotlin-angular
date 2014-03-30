package todo.mock

import todo.ITodoStorage
import todo.Todo
import angular.ServiceFactory
import todo.TodoStorage

class MockStorage : ITodoStorage() {
    class object : ServiceFactory<ITodoStorage>(ITodoStorage.name) {
        override fun create() = MockStorage()
    }

    var todos = array<Todo>()

    override fun get(): Array<Todo> = todos

    override fun put(newTodos: Array<Todo>) {
        todos.splice(0,todos.length)
        todos = Array(todos.length) {
            newTodos[it]
        }
    }
    override fun clear() {
        todos.splice(0,todos.length)
    }
}
