package jsondatabase.server

import jsondatabase.JsonResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val ERROR_RES = "ERROR"
const val SUCCESS_RES = "OK"

interface Command {
    fun execute(): String
}

class SetCommand(private val db: TextDatabase, private val key: String, private val text: String) : Command {
    override fun execute(): String {
        val response = if (db.set(key, text)) SUCCESS_RES else ERROR_RES
        return Json.encodeToString(JsonResponse(response, null, null))
    }
}

class GetCommand(private val db: TextDatabase, private val key: String) : Command {
    override fun execute(): String {
        val text = db.get(key)
        val response = text?.let { SUCCESS_RES } ?: ERROR_RES
        val reason = if (text == null) "No such key" else null
        return Json.encodeToString(JsonResponse(response, reason, text))
    }
}

class DeleteCommand(private val db: TextDatabase, private val key: String) : Command {
    override fun execute(): String {
        val delete = db.delete(key)
        val response = if (delete) SUCCESS_RES else ERROR_RES
        val reason = if (delete) null else "No such key"
        return Json.encodeToString(JsonResponse(response, reason, null))
    }
}

class CommandProcessor(private val db: TextDatabase) {
    private val commands = mapOf(
        "set" to { key: String, args: List<String> -> SetCommand(db, key, args.joinToString(" ")) },
        "get" to { key: String, _: List<String> -> GetCommand(db, key) },
        "delete" to { key: String, _: List<String> -> DeleteCommand(db, key) }
    )

    fun getCommand(cmd: String, key: String, args: String): Command {
        val commandFunc = commands[cmd] ?: throw IllegalArgumentException("Unknown command")
        return commandFunc(key, args.split(" "))
    }
}
