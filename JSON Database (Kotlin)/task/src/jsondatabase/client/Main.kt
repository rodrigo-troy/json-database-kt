package jsondatabase.client

import jsondatabase.RegexMatcher

fun main(args: Array<String>) {
    val regexMatcher = RegexMatcher()
    val clientSocket = ClientSocket()
    val msg = args.joinToString(" ")
    clientSocket.sendMessage(msg)

    if (regexMatcher.isExitCommand(msg)) {
        println("Sent: exit")
    } else if (regexMatcher.isNullCommand(msg)) {
        println("Sent: null")
    } else {
        val (type, index, message) = regexMatcher.getSetGetDeleteValues(msg)
        println("Sent: $type $index $message")
    }

    val readMessage = clientSocket.readMessage()
    print("Received: $readMessage")
    clientSocket.close()
}
