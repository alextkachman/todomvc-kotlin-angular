package angular

native trait Location {
    fun path(): String
    fun path(p: String)
}

fun InjectorAware.ngLocation() = instance<Location>("\$location")
