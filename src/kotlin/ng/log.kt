package angular

native trait Log {
    fun info(msg: Any)
    fun debug(msg: Any)
    fun error(msg: Any)
    fun warn(msg: Any)
}

val InjectorAware.ijLog: Log
  get ()= instance("\$log")
