package jsondatabase.server

const val SUCCESS_RES = "OK"

class TextDatabase(dbSize: Int) {
    private var database = MutableList(dbSize) { "" }

    fun set(index: Int, text: String): Boolean =
        if (index in 1..database.size) {
            database[index - 1] = text
            true
        } else false

    fun get(index: Int): String? =
        if (index in 1..database.size) {
            database[index - 1].ifEmpty { null }
        } else null

    fun delete(index: Int): Boolean =
        if (index in 1..database.size) {
            database[index - 1] = ""
            true
        } else false
}
