package todo

import angular.*

object TodoCtrl : Controller<TodoCtrl.TodoScope>("TodoCtrl") {
    native trait TodoScope: Scope {
        var todos: Array<Todo>
        var newTodo: String
        var editedTodo: Todo?
        var remainingCount: Int
        var completedCount: Int
        var allChecked: Boolean
        var statusFilter: Json?
        var location: Location
        var addTodo: () -> Unit
        var editTodo: (Todo) -> Unit
        var doneEditing: (Todo) -> Unit
        var removeTodo: (Todo) -> Unit
        var clearCompletedTodos: () -> Unit
        var markAll: (Boolean) -> Unit
    }

    fun statusFilter(v: Boolean) : Json{
        // todo: better syntax needed
        val json = json()
        json["completed"] = v
        return json
    }

    override fun TodoScope.invoke() {
        location = ngLocation()

        val todoService = TodoService.instance(this)

        todos = todoService.todos
        newTodo = ""
        editedTodo = null

        val filter = ngFilter("filter")
        watch<Unit>("todos", true) {
            remainingCount = filter(todos, statusFilter(false)).size
            completedCount = todoService.todos.length - remainingCount
            allChecked = remainingCount == 0
            todoService.save();
        }

        if(location.path() == "") {
            location.path("/")
        }

        watch<String>("location.path()", { path ->
            ngLog().info(path)
            statusFilter = when(path) {
                "/active" -> statusFilter(false)
                "/completed" -> statusFilter(true)
                else -> null
            }
        })

        addTodo = {
            if(newTodo.length > 0) {
                todoService.addTodo(newTodo)
                newTodo = ""
            }
        }

        removeTodo = { todo ->
            todoService.remove(todo)
        }

        editTodo = { todo ->
            editedTodo = todo
        }

        doneEditing = { todo ->
            editedTodo = null
            if(todo.title == null) {
                todoService.remove(todo)
            }
        }

        clearCompletedTodos = {
            todoService.clearCompletedTodos()
            todos = todoService.todos
        }

        markAll = { completed ->
            todoService.markAll(completed)
        }
    }
}

