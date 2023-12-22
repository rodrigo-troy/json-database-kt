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

interface Command {
    fun execute(): String
}

class SetCommand(private val db: TextDatabase, private val index: Int, private val text: String) : Command {
    override fun execute(): String =
        if (db.set(index, text)) SUCCESS_RES else ERROR_RES
}

class GetCommand(private val db: TextDatabase, private val index: Int) : Command {
    override fun execute(): String =
        db.get(index) ?: ERROR_RES
}

class DeleteCommand(private val db: TextDatabase, private val index: Int) : Command {
    override fun execute(): String =
        if (db.delete(index)) SUCCESS_RES else ERROR_RES
}

class CommandProcessor(private val db: TextDatabase) {
    private val commands = mapOf(
        "set" to { index: Int, args: List<String> -> SetCommand(db, index, args.joinToString(" ")) },
        "get" to { index: Int, _: List<String> -> GetCommand(db, index) },
        "delete" to { index: Int, _: List<String> -> DeleteCommand(db, index) }
    )

    fun getCommand(command: List<String>?): Command {
        val cmd = command?.getOrNull(0)
        val index = command?.getOrNull(1)?.toIntOrNull() ?: throw IllegalArgumentException("Invalid index")
        val args = command.drop(2)
        val commandFunc = commands[cmd] ?: throw IllegalArgumentException("Unknown command")
        return commandFunc(index, args)
    }
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
