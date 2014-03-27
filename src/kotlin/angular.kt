package angular

native trait Angular {
    fun module(name: String, deps: Array<String>): Module

    fun injector(modules: Array<String>) : Injector

    val noop: () -> Any
}

native trait Module {
    fun directive(name: String, injectsAndDef: Array<Any>): Module
    fun factory (name: String, injectsAndDef: Array<Any>): Module
    fun constant (name: String, value: Any?): Module
    fun value (name: String, value: Any?): Module
    fun controller(name: String, injectsAndDef: Array<Any>): Module
    fun config(injectsAndDef: Array<Any>): Module
    fun run(injectsAndDef: Array<Any>): Module
}

native trait Injector {
    fun <T> get(name: String) : T
}

native trait ElemNode {
    fun focus()
}

native trait Elem {
    fun bind(name: String, func: () -> Unit)
    fun get(index: Int): ElemNode
}

native trait AngularAttrs {
}

native trait Timeout {
    fun invoke(func: () -> Unit, x: Int, y: Boolean)
}

native trait Location {
    fun path(): String
    fun path(p: String)
}

native trait Scope : InjectorAware {
    fun <T> `$watch`(exp: Any, todo: (T) -> Unit, deepWatch: Boolean) = js.noImpl
    fun <T> `$watch`(exp: Any, todo: (T) -> Unit)  = js.noImpl
    fun `$apply`(func: Any): Unit  = js.noImpl
}

fun <T> Scope.watch(what: String, onChange: (T)-> Unit) = this.`$watch` (what, onChange)
fun <T> Scope.watch(what: String, deepWatch: Boolean, onChange: (T)-> Unit) = this.`$watch` (what, onChange, deepWatch)

fun Scope.apply(what: String)   = this.`$apply` (what)
fun Scope.apply(what: ()->Unit) = this.`$apply` (what)

native trait Log {
    fun info(msg: Any)
    fun debug(msg: Any)
    fun error(msg: Any)
    fun warn(msg: Any)
}

native("angular") val angular: Angular = js.noImpl

fun <T> noop() = angular.noop as T

