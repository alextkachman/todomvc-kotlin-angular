package todo

import angular.*

native trait TodoAttrs : AngularAttrs {
    val todoBlur: String
    val todoFocus: String
}

var todoFocus = { Directive.() ->
    link = { scope, elem, attrs ->
        attrs as TodoAttrs
        scope.watch<Boolean>(attrs.todoFocus, { newVal ->
            if(newVal) {
                ijTimeout({
                    (elem as Array<ElemNode>)[0].focus()
                }, 0, false)
            }
        })
    }
}

var todoBlur = { Directive.() ->
    link = { scope, elem, attrs ->
        attrs as TodoAttrs
        elem.bind("blur") {
            scope.apply(attrs.todoBlur)
        }
    }
}
