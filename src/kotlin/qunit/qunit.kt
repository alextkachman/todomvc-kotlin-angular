package angular.qunit

import angular.*
import java.util.LinkedList
import java.util.ArrayList

native trait QUnit {
    fun test(name: String, body: (assert: QUnitAssert)->Unit)
    fun module(name: String, config: Any)
}

native trait QUnitAssert: InjectorAware {
    override native var injector: Injector

    fun ok(result: Boolean, message: String)
}

native("QUnit") val qunit: QUnit = js.noImpl

native("Object") class QUnitModuleConfig {

}

class TestDeclaration(val name: String, val body: QUnitAssert.()->Unit) {
    class object {
        private var accumulated = array<TestDeclaration>()

        fun execute() {
            for (i in accumulated) {
                val body = i.body
                qunit.test(i.name) { assert ->
                    assert.body()
                }
            }
        }

        fun clear() {
            accumulated.splice(0,accumulated.length)
        }
    }

    {
        accumulated.push(this)
    }
}

fun Module.test(name: String, body: QUnitAssert.()->Unit)  = TestDeclaration(name, body)

fun testModule(name: String, deps: Array<String>, testModuleDeclaration: (Module.()->Unit)? = null) {
    try {
        module(name, deps, testModuleDeclaration)

        val config = QUnitModuleConfig() as Json
        config["setup"] = { (assert: QUnitAssert): Unit ->
            (assert as Json).set("injector", angular.injector(array("ng",name)))
        }
        qunit.module(name, config)

        TestDeclaration.execute()
    }
    finally {
        TestDeclaration.clear()
    }
}