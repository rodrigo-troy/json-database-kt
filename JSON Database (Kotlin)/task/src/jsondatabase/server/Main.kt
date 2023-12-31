package jsondatabase.server


fun main() {
    val server = ServerSocket()
    val readMessage = server.readMessage()
    println("Received: $readMessage")
    val msg = "A record # 12 was sent!"
    server.sendMessage(msg)
    println("Sent: $msg")
    server.close()
}

private fun stage1() {
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
