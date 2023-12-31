package jsondatabase.client

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ClientSocket {
    private val socket = Socket("127.0.0.1", 23456)
    private val input = DataInputStream(socket.getInputStream())
    private val output = DataOutputStream(socket.getOutputStream())

    init {
        println("Client started!")
    }

    fun sendMessage(msg: String) {
        output.writeUTF(msg)
        output.flush()
    }

    fun readMessage() = input.readUTF()

    fun close() = socket.close()
}
