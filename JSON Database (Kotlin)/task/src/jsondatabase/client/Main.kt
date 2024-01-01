package jsondatabase.client

import jsondatabase.JsonCommand
import jsondatabase.RegexMatcher
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    args.forEachIndexed() { index, arg ->
        println("Argument $index: $arg")
    }
    val regexMatcher = RegexMatcher()
    val clientSocket = ClientSocket()
    val msg = args.joinToString(" ") { arg ->
        if (arg.contains(" ")) {
            "\"$arg\""
        } else {
            arg
        }
    }

    try {
        if (regexMatcher.isExitCommand(msg)) {
            val json = Json.encodeToString(JsonCommand("exit", null, null))
            clientSocket.sendMessage(json)
            println("Sent: $json")
        } else if (regexMatcher.isNullCommand(msg)) {
            println("Sent: null")
        } else {
            val (type, index, message) = regexMatcher.getSetGetDeleteValues(msg)
            val json = Json.encodeToString(JsonCommand(type, index, message))
            clientSocket.sendMessage(json)
            println("Sent: $json")
        }

        val readMessage = clientSocket.readMessage()
        println("Received: $readMessage")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        clientSocket.close()
    }
}
