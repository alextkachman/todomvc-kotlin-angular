package angular

import angular.Module
import angular.Injector
import angular.InjectorAwareImpl
import angular.InjectorAware
import angular.instance

open class Factory<T>(val name: String) {
    open fun create() : T = throw UnsupportedOperationException()
}

open class ServiceFactory<T: Service>(name: String) : Factory<T>(name) {
    fun instance(injectorAware: InjectorAware) = injectorAware.instance<T>(name)
}

open class Service(): InjectorAwareImpl() {
}

public fun <T> Module.factory(name: String, factoryDefinition: ()->T) : Module {
    factory(name, array("\$injector",{ (injector: Injector):T ->
        InjectorAwareImpl.with(injector) {
            factoryDefinition()
        }
    }))
    return this
}


fun <T> Module.factory(factory: Factory<T>) = factory<T>(factory.name) {
    factory.create()
}
