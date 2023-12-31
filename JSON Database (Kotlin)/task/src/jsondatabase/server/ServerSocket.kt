package jsondatabase.server

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

/**
 * Represents a server socket that listens for incoming client connections and allows sending and receiving messages.
 */
class ServerSocket {
    private val server = ServerSocket(23456, 50, InetAddress.getByName("127.0.0.1"))
    private lateinit var socket: Socket
    private lateinit var input: DataInputStream
    private lateinit var output: DataOutputStream

    init {
        println("Server started!")
    }

    fun acceptClient() {
        socket = server.accept()
        input = DataInputStream(socket.getInputStream())
        output = DataOutputStream(socket.getOutputStream())
    }

    fun sendMessage(msg: String) {
        output.writeUTF(msg)
        output.flush()
    }

    fun readMessage(): String {
        return input.readUTF()
    }

    fun close() {
        socket.close()
        input.close()
        output.close()
    }
}
