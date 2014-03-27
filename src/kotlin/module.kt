package angular

private fun buildDependencies(deps: Array<String>?) = Array<String>(if (deps == null) 1 else deps.size + 1, { index ->
    if (index == 0) {
        AngularAnchor.name
    }
    else {
        deps!![index-1]
    }
})

fun module(name: String, deps: Array<String>? = null, declaration: (Module.()->Unit)? = null) : Module {
    var module = angular.module(name, buildDependencies(deps))
    if (declaration != null) {
        module.declaration()
    }
    return module
}

public fun Module.directive(name: String, directiveFactory: ()->Directive): Module {
    directive(name, array("\$injector", { (injector: Injector):Directive ->
        InjectorAwareImpl.now = injector
        var result = directiveFactory()
        InjectorAwareImpl.now = null
        result
    }))
    return this
}

public fun <T> Module.factory(name: String, factoryDefinition: ()->T) : Module {
    factory(name, array("\$injector",{ (injector: Injector):T ->
        InjectorAwareImpl.now = injector
        var result = factoryDefinition()
        InjectorAwareImpl.now = null
        result
    }))
    return this
}

public fun <S: Scope> Module.controller(controller: Controller<S>) : Module {
    controller(controller.name, array("\$scope", { (scope: S) : Any ->
        scope.controller()
    }))
    return this
}
