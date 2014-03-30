package angular

val InjectorAware.ijTimeout: (()->Unit,Long,Boolean)->Unit
    get() = instance("\$timeout")

