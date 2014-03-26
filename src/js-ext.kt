package js

native fun <T> Array<T>.push(x: T) = js.noImpl
native fun <T> Array<T>.splice(i1: Int, i2: Int) = js.noImpl
native fun <T> Array<T>.indexOf(x: T) = js.noImpl
native fun <T> Array<T>.filter(x: (T)->Boolean) = js.noImpl
native fun <T> Array<T>.forEach(x: (T)->Unit) = js.noImpl
native val <T> Array<T>.length: Int = js.noImpl

native trait LocalStorage {
    fun getItem(id: String): String?
    fun setItem(id: String, data: String)
}

native("localStorage") val localStorage: LocalStorage = js.noImpl

native("alert") fun alert(s: Any) = js.noImpl

