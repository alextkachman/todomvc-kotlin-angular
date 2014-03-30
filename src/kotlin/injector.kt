package angular

native trait Injector {
    fun <T> get(name: String) : T
}

native("Object") trait InjectorAware {
    native val injector: Injector
}

fun <T> InjectorAware.instance(name: String) = injector.get<T>(name)

open class InjectorAwareImpl : InjectorAware {
    class object {
        var now: Injector? = null

        fun <T> with(injector: Injector, action: ()->T): T {
            now = injector
            try {
                return action()
            }
            finally {
                now = null
            }
        }
    }

    override val injector: Injector
        get() = (this as Json).get("__injector") as Injector

    {
        (this as Json).set("__injector", now!!)
    }
}
