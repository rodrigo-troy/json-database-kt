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
    private val regexSetGetDelete = Regex("-t (set|get|delete) -i (\\d+)( -m (.+))?")

    fun isExitCommand(input: String): Boolean {
        return regexExit.find(input) != null
    }

    fun isNullCommand(input: String): Boolean {
        return regexExit.find(input) != null && regexSetGetDelete.find(input) != null
    }

    fun getSetGetDeleteValues(input: String): Triple<String?, Int?, String?> {
        val matchResultSetGetDelete = regexSetGetDelete.find(input)
        val type = matchResultSetGetDelete?.groups?.get(1)?.value
        val index = matchResultSetGetDelete?.groups?.get(2)?.value?.toInt()
        val message = matchResultSetGetDelete?.groups?.get(4)?.value
        return Triple(type, index, message)
    }
}
