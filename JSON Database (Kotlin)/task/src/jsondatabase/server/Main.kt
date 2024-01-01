package jsondatabase.server

import jsondatabase.JsonCommand
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun main() {
    val db = TextDatabase()
    val processor = CommandProcessor(db)
    val server = ServerSocket(23456, 50, "127.0.0.1")

    try {
        while (true) {
            server.acceptClient()
            val readMessage = server.readMessage()
            //println("Received: $readMessage")

            val jsonCommand = Json.decodeFromString<JsonCommand>(readMessage)

            if (jsonCommand.isExitCommand()) {
                server.sendMessage(SUCCESS_RES)
                break
            }

            if (jsonCommand.type == null) {
                server.sendMessage("Unknown command. Type set, get, delete, or exit.")
                continue
            }

            if (jsonCommand.key == null) {
                server.sendMessage("Invalid index")
                continue
            }

            val result = processor.getCommand(
                jsonCommand.type, jsonCommand.key, jsonCommand.value ?: ""
            ).execute()

            println("Result: $result")

            server.sendMessage(result)
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        println("Closing server")
        server.close()
    }
}
