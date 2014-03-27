package angular

native("Object") trait InjectorAware {
    native val injector: Injector
}

fun <T> InjectorAware.instance(name: String) = injector.get<T>(name)

open class InjectorAwareImpl : InjectorAware {
    class object {
        var now: Injector? = null
    }
    override val injector: Injector
        get() = (this as Json).get("__injector") as Injector

    {
        (this as Json).set("__injector", now!!)
    }
}

abstract class Controller<S: Scope>(val name: String) {
    abstract fun S.invoke() : Unit
}

open class Directive(val name: String) : InjectorAwareImpl() {
    native var link: (scope: Scope, elem: Elem, attrs: AngularAttrs) -> Unit = noop()
}

open class Service(): InjectorAwareImpl() {
}

object AngularAnchor {
    val name = "AngularAnchor";

    {
        angular.module(name, array()).run(array("\$injector", "\$rootScope", { (injector: Injector, rootScope: Scope): Unit ->
            (rootScope as Json).set("injector", injector)
        }))
    }
}
