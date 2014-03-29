package angular

native trait Location {
    fun path(): String
    fun path(p: String)
}

val InjectorAware.ngLocation: Location
    get() = instance("\$location")
