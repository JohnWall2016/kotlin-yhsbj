package yhsbj.util

import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class ConfigTest {
    @Test fun test() {
        val bundle = ResourceBundle.getBundle("config")
        println(bundle.getString("user002_id"))
        println(bundle.getString("jbzt_map"))
    }
}