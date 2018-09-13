package yhsbj.util

import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap

class Config {
    companion object {
        private val bundle = ResourceBundle.getBundle("config")

        fun getValue(key: String): String? = bundle.getString(key)

        private const val splitString = ";"
        private const val matchString = ":"
        private const val wildcardString = "*"
        private const val hyphenString = "-"

        private val mapCache = HashMap<String, HashMap<String, String>>()

        private fun loadMap(key: String): HashMap<String, String> {
            val map = HashMap<String, String>()
            val mapString = getValue(key)
            if (mapString != null) {
                val pairs = mapString.split(splitString)
                for (pair in pairs) {
                    val p = pair.split(matchString)
                    if (p.size == 2 && p[0] != "")
                        map[p[0]] = p[1]
                }
            }
            mapCache[key] = map
            return map
        }

        fun getMapValue(key: String, vararg subKeys: String): String? {
            val map = if (mapCache.containsKey(key))
                mapCache[key]
            else
                loadMap(key)
            var subKey = subKeys.joinToString(hyphenString)
            if (map!!.containsKey(subKey))
                return map[subKey]
            else {
                for (i in 1 until subKeys.size) {
                    subKey = subKeys.drop(i).joinToString(hyphenString)
                    subKey += hyphenString + wildcardString
                    if (map.containsKey(subKey)) {
                        return map[subKey]
                    }
                }
                return map[wildcardString]
            }
        }
    }
}