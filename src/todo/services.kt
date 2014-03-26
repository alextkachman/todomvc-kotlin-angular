package todo

import js.LocalStorage

class TodoStorage {
    private val STORAGE_ID = "TODOS-angularjs"

    fun get(): Array<Todo> {
        var data = localStorage.getItem(STORAGE_ID)
        if(data == null) {
            data = "[]"
        }
        return JSON.parse<String>(data!!) as Array<Todo>
    }

    fun put(data: Array<Todo>) {
        localStorage.setItem(STORAGE_ID, JSON.stringify(data))
    }
}
