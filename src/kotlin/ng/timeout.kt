package angular

native trait Timeout {
    fun invoke(func: () -> Unit, x: Int, y: Boolean)
}

val InjectorAware.ngTimeout: (()->Unit,Long,Boolean)->Unit
    get() = instance("\$timeout")

