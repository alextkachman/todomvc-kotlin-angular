package angular

fun InjectorAware.ngInjector() = injector

fun InjectorAware.ngLog() = instance<Log>("\$log")
fun InjectorAware.ngLocation() = instance<Location>("\$location")
fun InjectorAware.ngFilter() = instance<(name: String)->((data: Array<*>, how: Any?)->Array<*>)>("\$filter")
fun InjectorAware.ngTimeout() = instance<(()->Unit,Long,Boolean)->Unit>("\$timeout")

//class Ng() : InjectorAware() {
//    val rootScope: Scope by instanceRef("\$rootScope")
//    val exceptionHandler: (err: Throwable, reason: String?)->Unit by instanceRef("\$exceptionHandler")
//    val injector: Injector by instanceRef("\$injector")
//    val parse: (code: String)->((scope: Scope)->Any?) by instanceRef("\$parse")
//}
