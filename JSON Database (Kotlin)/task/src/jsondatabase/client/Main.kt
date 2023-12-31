package jsondatabase.client

fun main(args: Array<String>) {
    val clientSocket = ClientSocket()
    val msg = args.joinToString(" ")
    clientSocket.sendMessage(msg)
    println("Sent: $msg")
    val readMessage = clientSocket.readMessage()
    print("Received: $readMessage")
}
