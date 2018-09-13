package yhsbj.util

import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class ConfigTest {
    @Test fun test() {
        println(Config.getValue("user002_id"))
        println(Config.getValue("jbzt_map"))
        println(Config.getMapValue("jbzt_map", "3"))
        println(Config.getMapValue("jbzt_map", "3", "4"))
    }
}