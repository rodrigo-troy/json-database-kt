package jsondatabase.client


fun main() {
    val clientSocket = ClientSocket()
    val s = "Give me a record # 12"
    clientSocket.sendMessage(s)
    println("Sent: $s")
    val readMessage = clientSocket.readMessage()
    print("Received: $readMessage")
}
