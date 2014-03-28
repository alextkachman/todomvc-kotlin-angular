package todo

import angular.*

/**
 * API for TodoStorage
 */
abstract class ITodoStorage() : Service() {
    class object : ServiceFactory<ITodoStorage>("todoStorage")

    abstract fun get(): Array<Todo>
    abstract fun put(todos: Array<Todo>): Unit
}

/**
 *  Implementation for TodoStorage
 */
class TodoStorage() : ITodoStorage() {
    class object : ServiceFactory<ITodoStorage>(ITodoStorage.name) {
        private val STORAGE_ID = "TODOS-angularjs"

        override fun create() = TodoStorage()
    }

    override fun get() : Array<Todo> {
        var data = localStorage.getItem(STORAGE_ID)
        if (data == null) {
            data = "[]"
        }
        ngLog().info(data!!)
        return JSON.parse<Array<Todo>>(data!!)
    }

    override fun put(todos: Array<Todo>) : Unit {
        val stringify = JSON.stringify(todos)
        ngLog().info(stringify)
        localStorage.setItem(STORAGE_ID, stringify)
    }
}
