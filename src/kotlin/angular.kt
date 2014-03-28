package angular

native trait Angular {
    fun module(name: String, deps: Array<String>?): Module

    fun injector(modules: Array<String>) : Injector

    val noop: () -> Any
}

native trait ElemNode {
    fun focus()
}

native trait Elem {
    fun bind(name: String, func: () -> Unit)
    fun get(index: Int): ElemNode
}

native("angular") val angular: Angular = js.noImpl

fun <T> noop() = angular.noop as T

