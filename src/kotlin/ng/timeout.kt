package angular

native trait Timeout {
    fun invoke(func: () -> Unit, x: Int, y: Boolean)
}

fun InjectorAware.ngTimeout() = instance<(()->Unit,Long,Boolean)->Unit>("\$timeout")

