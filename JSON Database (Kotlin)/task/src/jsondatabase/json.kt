package jsondatabase

import kotlinx.serialization.Serializable

/**
 * Created with IntelliJ IDEA.
$ Project: json-database-kt
 * User: rodrigotroy
 * Date: 31-12-23
 * Time: 21:41
 */
@Serializable
data class JsonCommand(val type: String?, val key: String? = null, val value: String? = null) {
    fun isExitCommand() = type == "exit"

    fun isSetCommand() = type == "set"

    fun isGetCommand() = type == "get"
}

@Serializable
data class JsonResponse(val response: String, val reason: String? = null, val value: String? = null)


