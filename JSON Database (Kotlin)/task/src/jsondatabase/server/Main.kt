package jsondatabase.server

const val DB_SIZE = 100
const val SUCCESS_RES = "OK"
const val ERROR_RES = "ERROR"

class TextDatabase {
    private var database = MutableList(DB_SIZE) { "" }

    fun set(index: Int, text: String): Boolean =
        if (index in 1..DB_SIZE) {
            database[index - 1] = text
            true
        } else false

    fun get(index: Int): String? =
        if (index in 1..DB_SIZE) {
            database[index - 1].ifEmpty { null }
        } else null

    fun delete(index: Int): Boolean =
        if (index in 1..DB_SIZE) {
            database[index - 1] = ""
            true
        } else false
}

fun main() {
    val db = TextDatabase()
    val processor = CommandProcessor(db)
    var command = readlnOrNull()?.split(" ")

    while (command?.get(0) != "exit") {
        try {
            val result = processor.getCommand(command).execute()
            println(result)
        } catch (e: IllegalArgumentException) {
            println("Unknown command. Type set, get, delete, or exit.")
        }
        command = readlnOrNull()?.split(" ")
    }
}
