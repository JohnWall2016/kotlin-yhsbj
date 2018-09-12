package yhsbj.cjb.hncjb

import yhsbj.util.HttpSession

class Session(host: String, port: Int, val userId: String, val password: String): HttpSession(host, port) {
    private var sessionId = ""
    private var cxCookie = ""

    private fun buildSendContent(content: String): String {
        val url = "$host:$port"
        var result = "POST /hncjb/reports/crud HTTP/1.1\n" +
                "Host: $url\n" +
                "Connection: keep-alive\n" +
                "Content-Length: ${getBytes(content).size}\n" +
                "Accept: application/json, text/javascript, */*; q=0.01\n" +
                "Origin: http://$url\n" +
                "X-Requested-With: XMLHttpRequest\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36\n" +
                "Content-Type: multipart/form-data;charset=UTF-8\n" +
                "Referer: http://$url/hncjb/pages/html/index.html\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Accept-Language: zh-CN,zh;q=0.8\n"
        if (sessionId != "" && sessionId != "")
            result += "Cookie: jsessionid_ylzcbp=$sessionId; cxcookie=$cxCookie\n"
        result += "\n" + content
        return result
    }

    fun send(content: String) = write(buildSendContent(content))

    fun <T> send(service: JsonService<T>) = send(service.toString())

    fun <T> send(serviceId: String, params: T) = send(JsonService(serviceId, params).apply { loginname = userId; password = this@Session.password })

    fun send(service: IService) = send(service.id, service)

    fun dump(service: IService) = JsonService(service.id, service).apply { loginname = userId; password = this@Session.password }.toString()

    fun get() = readBody()

    inline fun <reified T> get(): T = Result.fromJson<T>(get())

    fun login(): String {
        send(JsonService.withoutParms("loadCurrentUser"))
        val header = readHeader()
        var m = "Set-Cookie: jsessionid_ylzcbp=(.+?);".toRegex().find(header)
        if (m != null)
            sessionId = m.groupValues[1]
        m = "Set-Cookie: cxcookie=(.+?);".toRegex().find(header)
        if (m != null)
            cxCookie = m.groupValues[1]
        readBody(header)
        send(SysLogin(userId, password))
        return get()
    }

    fun logout(): String {
        send(JsonService.withoutParms("syslogout"))
        return get()
    }
}