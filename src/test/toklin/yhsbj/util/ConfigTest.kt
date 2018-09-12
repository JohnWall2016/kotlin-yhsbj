package yhsbj.util

import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

class ConfigTest {
    @Test fun test() {
        println(Paths.get("./").toAbsolutePath())
        //println(Files.readAllLines(Paths.get("config.properties")))
        //println(Config.getValue("user002_id"))
    }
}