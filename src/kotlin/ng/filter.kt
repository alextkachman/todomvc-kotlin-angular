package angular

val InjectorAware.ijFilter: (String)->((Array<*>, Any?)->Array<*>)
    get() = instance("\$filter")

fun InjectorAware.ijFilter(name: String) = ijFilter.invoke(name)


