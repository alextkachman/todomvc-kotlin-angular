package angular

native trait Location {
    fun path(): String
    fun path(p: String)
}

val InjectorAware.ijLocation: Location
    get() = instance("\$location")
