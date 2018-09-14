package yhsbj.cjb.hncjb

import yhsbj.util.Config

object Configs {
    val serverIP get() = getValue("hncjb_ip")
    val serverPort get() = getValue("hncjb_port")
    val userId002 get() = getValue("user002_id")
    val userPwd002 get() = getValue("user002_pwd")

    fun getJbztCN(jfzt: String, cbzt: String) = getMapValue("jbzt_map", jfzt, cbzt)

    fun getXzhqCN(code: String) = getMapValue("xzqh_map", code)

    class CannotFoundException : Exception("Can't find the config value")

    private fun getValue(key: String) = Config.getValue(key) ?: throw CannotFoundException()

    private fun getMapValue(key: String, vararg subkeys: String)
            = Config.getMapValue(key, *subkeys) ?: throw CannotFoundException()
}