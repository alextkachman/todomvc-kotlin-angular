package angular

native trait AngularAttrs {
}

class Directive() : InjectorAwareImpl() {
    native var link: ((scope: Scope, elem: Elem, attrs: AngularAttrs) -> Unit)? = null
}

public fun Module.directive(name: String, directiveConfig: Directive.()->Unit): Module =
    directive(name, array("\$injector", { (injector: Injector):Directive ->
        InjectorAwareImpl.with(injector) {
            var directive = Directive()
            directive.directiveConfig()
            directive
        }
    }))
