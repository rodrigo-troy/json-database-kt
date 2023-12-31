package jsondatabase.server

import jsondatabase.RegexMatcher

fun main() {
    val db = TextDatabase(1000)
    val processor = CommandProcessor(db)
    val server = ServerSocket()
    val regexMatcher = RegexMatcher()

    while (true) {
        server.acceptClient()
        val readMessage = server.readMessage()
        println("Received: $readMessage")

        if (regexMatcher.isExitCommand(readMessage)) {
            server.sendMessage(SUCCESS_RES)
            break
        }

        val (type, index, message) = regexMatcher.getSetGetDeleteValues(readMessage)

        if (type == null) {
            server.sendMessage("Unknown command. Type set, get, delete, or exit.")
            continue
        }

        if (index == null) {
            server.sendMessage("Invalid index")
            continue
        }

        val result = processor.getCommand(type, index, message ?: "").execute()
        println("Result: $result")

        server.sendMessage(result)
    }

    server.close()
}
