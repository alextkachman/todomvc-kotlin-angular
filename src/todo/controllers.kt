package todo

import angular.*

native("Object")
class StatusFilter {
    native var completed: Boolean? = js.noImpl
}

fun StatusFilter(v: Boolean): StatusFilter {
    var sf = json() as StatusFilter
    sf.completed = v
    return sf
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

        val todoService = TodoModule.todoService
        todos = todoService.todos
        newTodo = ""
        editedTodo = null

        watch<Unit>("todos", true) {
            remainingCount = Ng.filter("filter")(todos, StatusFilter(false)).size
            completedCount = todoService.todos.length - remainingCount
            allChecked = remainingCount == 0
            todoService.save();
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
