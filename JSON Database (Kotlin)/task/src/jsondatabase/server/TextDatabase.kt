package jsondatabase.server

import java.util.concurrent.ConcurrentHashMap

class TextDatabase() {
    private val database: ConcurrentHashMap<String, String> = ConcurrentHashMap()

    fun set(key: String, text: String): Boolean =
        if (key.isNotEmpty()) {
            database[key] = text
            true
        } else false

    fun get(key: String): String? =
        if (key.isNotEmpty() && database.containsKey(key)) database[key] else null

    fun delete(key: String): Boolean =
        if (key.isNotEmpty() && database.containsKey(key)) {
            database.remove(key)
            true
        } else false
}
