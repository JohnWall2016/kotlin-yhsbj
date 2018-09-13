package yhsbj.cjb.hncjb

import org.junit.Assert.*
import org.junit.Test

class SessionTest {
    @Test fun test() {
        Session.user002 {
            val query = it.dump(GrinfoQuery("130503193510300329"))
            println("query: $query")
            it.send(query)
            val rs = it.get<Result<Grinfo>>()
            println("rs: ${rs}")
        }
    }
}