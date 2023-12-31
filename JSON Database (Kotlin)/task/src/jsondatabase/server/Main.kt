package jsondatabase.server


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
