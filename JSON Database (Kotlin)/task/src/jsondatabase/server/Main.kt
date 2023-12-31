package jsondatabase.server

fun main() {
    val db = TextDatabase(1000)
    val processor = CommandProcessor(db)
    val server = ServerSocket()

    while (true) {
        val readMessage = server.readMessage()
        println("Received: $readMessage")

        val matchResult = Regex("-t (\\w+) -i (\\d+)( -m (.*))?").find(readMessage)
        val type = matchResult?.groups?.get(1)?.value
        val index = matchResult?.groups?.get(2)?.value?.toInt()
        val message = matchResult?.groups?.get(4)?.value

        println("Type: $type")
        println("Index: $index")
        println("Message: $message")

        if (type == null) {
            server.sendMessage("Unknown command. Type set, get, delete, or exit.")
            continue
        }

        if (type == "exit") {
            server.sendMessage(SUCCESS_RES)
            break
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
