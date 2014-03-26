package angular.demo

import angular.*

object HelloWorldModule : Module("HelloWorld") {
    val hello = constant("hello", "Hello")

    val world: String by factory("world") {"World"}
}

object AppModule : Module("App",array(HelloWorldModule)) {
    {
        controller<AppScope>("AppController") {
            phrase = HelloWorldModule.hello + ", " + HelloWorldModule.world

            clickCount = 0
            addClick = { clickCount++ }

            watch<Int>("clickCount") { value ->
                if (value == 5) {
                    clickCount = 0
                }
            }
        }

        controller<InnerScope>("InnerController") {
            val log = Ng.log

            buttonText = "Click"
            buttonClick = {
                addClick()
                log.info("button clicked " + clickCount)
            }
        }
    }
}

native trait AppScope : Scope {
    var phrase : String
    var clickCount: Int
    var addClick: ()->Unit
}

native trait InnerScope : AppScope {
    var buttonText : String
    var buttonClick : InnerScope.()->Unit
}
