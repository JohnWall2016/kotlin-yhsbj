package yhsbj.util

object Utils {
    fun appendToFileName(fileName: String, appendText: String): String {
        val idx = fileName.lastIndexOf(".")
        return if (idx > 0) {
            fileName.substring(0, idx) + appendText + fileName.substring(idx)
        } else {
            fileName + appendText
        }
    }
}