package angular

class FactoryDelegate<T>(val name: String) {
    fun get(module: Any?, desc: PropertyMetadataImpl) : T  = AngularAnchor.instanceInjector.get(name)
}

object AngularAnchor {
    val name = "AngularAnchor";

    private var _providerInjector : Injector? = null
    private var _instanceInjector : Injector? = null

    val providerInjector : Injector
        get () = _providerInjector!!

    val instanceInjector : Injector
        get () = _instanceInjector!!

    {
        angular.module(name, array()).config(array("\$injector", { (injector: Injector): Unit ->
            _providerInjector = injector
        }))
        angular.module(name, array()).run(array("\$injector", { (injector: Injector): Unit ->
            _instanceInjector = injector
        }))
    }
}

abstract class Module(val name: String, deps: Array<Module>? = null) {
    class object {
        private fun buildDependencies(deps: Array<Module>?) = Array<String>(if (deps == null) 1 else deps.size + 1, { index ->
            if (index == 0) {
                AngularAnchor.name
            }
            else {
                deps!![index-1].name
            }
        })
    }

    protected val module: AngularModule = angular.module(name, buildDependencies(deps));

    protected fun <T> constant(name: String, value: T) : T {
        module.constant(name, value)
        return value
    }

    protected fun directive(vararg directive: Directive): Module {
        for (d in directive) {
            module.directive(d.name, array({ d }))
        }
        return this
    }

    protected fun <T> value(name: String, value: T) : T {
        module.value(name, value)
        return value
    }

    protected fun <T> injected(name: String) : T = AngularAnchor.instanceInjector.get(name)

    protected fun <T> factory(name: String, factoryDefinition: ()->T) : FactoryDelegate<T> {
        module.factory(name, array(factoryDefinition))
        return FactoryDelegate(name)
    }

    protected fun <S: Scope> controller(name: String, declaration: S.()->Unit) {
        module.controller(name, array("\$scope", { (scope: S) : Any ->
            scope.declaration()
        }))
    }

    protected fun <S: Scope> controller(controller: Controller<S>) {
        module.controller(controller.name, array("\$scope", { (scope: S) : Any ->
            scope.controller()
        }))
    }
}

abstract class Controller<S: Scope>(val name: String) {
    abstract fun S.invoke() : Unit
}

open class Directive(val name: String) : AngularDirective() {
    {
        link = { (scope: Scope, elem: Elem, attrs: AngularAttrs) -> link(scope, elem, attrs) }
    }

    open fun link(scope: Scope, elem: Elem, attrs: AngularAttrs) {}
}

open class WellKnown {
    fun <T> predefined(name: String) = FactoryDelegate<T>(name)
}

object Ng : WellKnown() {
    val log: Log by predefined("\$log")
    val rootScope: Scope by predefined("\$rootScope")
    val exceptionHandler: (err: Throwable, reason: String?)->Unit by predefined("\$exceptionHandler")
    val injector: Injector by predefined("\$injector")
    val timeout: (()->Unit,Long,Boolean)->Unit by predefined("\$timeout")
    val location: Location by predefined("\$location")
    val parse: (code: String)->((scope: Scope)->Any?) by predefined("\$parse")
    val filter: (name: String)->((data: Array<*>, how: Any?)->Array<*>) by predefined("\$filter")
}
