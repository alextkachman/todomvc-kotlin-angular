package todo

import angular.*

native("Object")
class Todo {
    var title: String? = js.noImpl
    var completed: Boolean = js.noImpl
}

native("Object")
class StatusFilter(c : Boolean) {
    native var completed: Boolean? = js.noImpl

    {
        completed = c
    }
}

native trait TodoScope: Scope {
    var todos: Array<Todo>
    var newTodo: String
    var editedTodo: Todo?
    var remainingCount: Int
    var completedCount: Int
    var allChecked: Boolean
    var statusFilter: StatusFilter?
    var location: Location
    var addTodo: () -> Unit
    var editTodo: (Todo) -> Unit
    var doneEditing: (Todo) -> Unit
    var removeTodo: (Todo) -> Unit
    var clearCompletedTodos: () -> Unit
    var markAll: (Boolean) -> Unit
}



class TodoCtrl: Controller<TodoScope>("TodoCtrl") {
    override fun TodoScope.invoke() {
        this.location = Ng.location

        todos = TodoModule.todoStorage.get()
        newTodo = ""
        editedTodo = null

        watch<Unit>("todos", true) {
            remainingCount = 0 // Ng.filter(todos, false).size
            completedCount = todos.length - remainingCount
            completedCount = todos.length - remainingCount
            allChecked = remainingCount == 0
        }

        if(location.path() == "") {
            location.path("/")
        }

        watch<String>("location.path()", { path ->
            Ng.log.info(path)
            statusFilter = when(path) {
                "/active" -> StatusFilter(false)
                "/completed" -> StatusFilter(true)
                else -> null
            }
        })

        addTodo = {
            if(newTodo.length > 0) {
                val todo = Todo()
                todo.title = newTodo
                todo.completed = false
                todos.push(todo)
                newTodo = ""
            }
        }

        editTodo = { todo ->
            editedTodo = todo
        }

        doneEditing = { todo ->
            editedTodo = null
            if(todo.title == null) {
                removeTodo(todo)
            }
        }

        removeTodo = { todo ->
            todos.splice(todos.indexOf(todo), 1)
        }

        clearCompletedTodos = {
            todos = todos.filter({ !it.completed })
        }

        markAll = { completed ->
            todos.forEach({ it.completed = completed })
        }
    }
}
