package angular

native trait Scope : InjectorAware {
    fun <T> `$watch`(exp: Any, todo: (T) -> Unit, deepWatch: Boolean) = js.noImpl
    fun <T> `$watch`(exp: Any, todo: (T) -> Unit)  = js.noImpl
    fun `$apply`(func: Any): Unit  = js.noImpl
}

fun <T> Scope.watch(what: String, onChange: (T)-> Unit) = this.`$watch` (what, onChange)
fun <T> Scope.watch(what: String, deepWatch: Boolean, onChange: (T)-> Unit) = this.`$watch` (what, onChange, deepWatch)

fun Scope.apply(what: String)   = this.`$apply` (what)
fun Scope.apply(what: ()->Unit) = this.`$apply` (what)
