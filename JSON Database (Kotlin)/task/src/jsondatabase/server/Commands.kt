package jsondatabase.server

const val ERROR_RES = "ERROR"

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

    fun getCommand(cmd: String, index: Int, args: String): Command {
        val commandFunc = commands[cmd] ?: throw IllegalArgumentException("Unknown command")
        return commandFunc(index, args.split(" "))
    }

    fun getCommand(command: List<String>?): Command {
        val cmd = command?.getOrNull(0)
        val index = command?.getOrNull(1)?.toIntOrNull() ?: throw IllegalArgumentException("Invalid index")
        val args = command.drop(2)
        val commandFunc = commands[cmd] ?: throw IllegalArgumentException("Unknown command")
        return commandFunc(index, args)
    }
}
