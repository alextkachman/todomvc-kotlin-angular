package angular

val InjectorAware.ngFilter : (String)->((Array<*>, Any?)->Array<*>)
    get() = instance("\$filter")

fun InjectorAware.ngFilter(name: String) = ngFilter.invoke(name)


