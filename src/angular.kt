package angular

native trait Angular {
    fun module(name: String, deps: Array<String>): AngularModule

    fun injector(modules: Array<String>) : Injector
}

native trait AngularModule {
    fun directive(name: String, injectsAndDef: Array<Any>): AngularModule
    fun factory (name: String, injectsAndDef: Array<Any>): AngularModule
    fun constant (name: String, value: Any?): AngularModule
    fun value (name: String, value: Any?): AngularModule
    fun controller(name: String, injectsAndDef: Array<Any>): AngularModule
    fun config(injectsAndDef: Array<Any>): AngularModule
    fun run(injectsAndDef: Array<Any>): AngularModule
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

native("Object")
open class AngularDirective {
    var link: (scope: Scope, elem: Elem, attrs: AngularAttrs) -> Unit = js.noImpl
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

native trait Scope {
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
