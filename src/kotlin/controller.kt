package angular

abstract class Controller<S>(val name: String) {
    abstract fun S.invoke()
}

public fun <S: Scope> Module.controller(controller: Controller<S>) : Module =
    controller(controller.name, array("\$scope", { (scope: S) : Any ->
        scope.controller()
    }))
