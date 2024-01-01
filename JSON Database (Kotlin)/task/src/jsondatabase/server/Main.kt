package jsondatabase.server

import jsondatabase.JsonCommand
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun main() {
    val db = TextDatabase(1000)
    val processor = CommandProcessor(db)
    val server = ServerSocket()

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
            listOf(
                jsonCommand.type,
                jsonCommand.key,
                jsonCommand.value ?: ""
            )
        ).execute()



        println("Result: $result")

        server.sendMessage(result)
    }

    server.close()
}
