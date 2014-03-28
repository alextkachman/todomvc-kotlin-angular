package angular

fun InjectorAware.ngFilter() = instance<(name: String)->((data: Array<*>, how: Any?)->Array<*>)>("\$filter")

fun InjectorAware.ngFilter(name: String) = ngFilter()(name)


