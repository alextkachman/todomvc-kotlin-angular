package todo

import angular.*

native trait TodoAttrs : AngularAttrs {
    val todoBlur: String
    val todoFocus: String
}

class TodoFocus : Directive("todoFocus") {
    {
        link = { scope, elem, attrs ->
            attrs as TodoAttrs
            scope.watch<Boolean>(attrs.todoFocus, { newVal ->
                if(newVal) {
                    ngTimeout()({ (elem as Array<ElemNode>)[0].focus() }, 0, false)
                }
            })
        }
    }
}

class TodoBlur : Directive("todoBlur") {
    {
        link = { scope, elem, attrs ->
            attrs as TodoAttrs
            elem.bind("blur") {
                scope.apply(attrs.todoBlur)
            }
        }
    }
}
