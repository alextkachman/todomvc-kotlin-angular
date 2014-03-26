package todo

import angular.*

native trait TodoAttrs : AngularAttrs {
    val todoBlur: String
    val todoFocus: String
}

class TodoFocus : Directive("todoFocus") {
    override fun link(scope: Scope, elem: Elem, attrs: AngularAttrs) {
        attrs as TodoAttrs
        Ng.log.info("todoFocus link")
        scope.watch<Boolean>(attrs.todoFocus, { newVal ->
            if(newVal) {
                Ng.timeout({ (elem as Array<ElemNode>)[0].focus() }, 0, false)
            }
        })
    }
}

class TodoBlur : Directive("todoBlur") {
    override fun link(scope: Scope, elem: Elem, attrs: AngularAttrs) {
        attrs as TodoAttrs
        Ng.log.info("todoBlur link")

        elem.bind("blur") {
            scope.apply(attrs.todoBlur)
        }
    }
}
