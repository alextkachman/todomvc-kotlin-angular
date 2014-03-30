package angular

import todo.ITodoStorage

native trait Module {
    fun directive(name: String, injectsAndDef: Array<Any>): Module
    fun factory (name: String, injectsAndDef: Array<Any>): Module
    fun constant (name: String, value: Any?): Module
    fun value (name: String, value: Any?): Module
    fun controller(name: String, injectsAndDef: Array<Any>): Module
    fun config(injectsAndDef: Array<Any>): Module
    fun run(injectsAndDef: Array<Any>): Module
}

private object AnchorModule {
    val name = "AngularAnchor";

    {
        angular.module(name, array()).run(array("\$injector", "\$rootScope", { (injector: Injector, rootScope: Scope): Unit ->
            (rootScope as Json).set("injector", injector)
        }))
        .factory("ijInjectorAware", array("\$injector", { (injector: Injector): InjectorAware ->
            InjectorAwareImpl.with(injector) {
                InjectorAwareImpl()
            }
        }))
    }
}

private fun buildDependencies(deps: Array<String>?) = Array<String>(if (deps == null) 1 else deps.size + 1, { index ->
    if (index == 0) {
        AnchorModule.name
    }
    else {
        deps!![index-1]
    }
})

fun module(name: String, deps: Array<String>? = null, declaration: (Module.()->Unit)? = null) : Module {
    var module = angular.module(name, if(deps != null || declaration != null) buildDependencies(deps) else null)
    if (declaration != null) {
        module.declaration()
    }
    return module
}

fun Module.run(body: InjectorAware.()->Unit) = run(array("ijInjectorAware",{ (ijInjector: InjectorAware): Unit ->
    ijInjector.body()
}))