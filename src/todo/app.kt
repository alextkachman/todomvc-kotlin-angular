package todo

import angular.*
import angular.demo.AppScope

native trait FilterFilter {
    fun invoke(data: Array<Todo>, completed: Boolean): Array<Todo>
}

object TodoModule : Module("todomvc") {
    val todoStorage by factory("todoStorage", { TodoStorage() });

    {
        directive(TodoFocus(), TodoBlur())
        controller(TodoCtrl())
    }
}

fun main(args: Array<String>) {
    // seems there is bug with object initialization
    TodoModule
}