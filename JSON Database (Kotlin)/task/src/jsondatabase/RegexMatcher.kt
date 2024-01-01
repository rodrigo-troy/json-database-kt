package jsondatabase

/**
 * Created with IntelliJ IDEA.
$ Project: json-database-kt
 * User: rodrigotroy
 * Date: 31-12-23
 * Time: 18:28
 */
class RegexMatcher {
    private val regexExit = Regex("-t (exit)")
    private val regexSetGetDelete = Regex("-t (set|get|delete) -k (\"[^\"]*\"|\\w+)( -v (\"[^\"]*\"|\\w+!?))?")

    fun isExitCommand(input: String): Boolean {
        return regexExit.find(input) != null
    }

    fun isNullCommand(input: String): Boolean {
        return regexExit.find(input) != null && regexSetGetDelete.find(input) != null
    }

    fun getSetGetDeleteValues(input: String): Triple<String?, String?, String?> {
        val matchResult = regexSetGetDelete.find(input)
        val type = matchResult?.groups?.get(1)?.value
        val key = matchResult?.groups?.get(2)?.value
        val message = matchResult?.groups?.get(4)?.value
        return Triple(type, key?.replace("\"", ""), message?.replace("\"", ""))
    }
}
