package yhsbj.cjb.hncjb

import org.junit.Test

class JsonServiceTest {
    @Test fun test() {
        println(JsonService.withoutParams("fetchid"))
        println(JsonService.create(GrinfoQuery("43031119591225052X")))
    }
}