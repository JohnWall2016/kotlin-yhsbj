package yhsbj.util

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

open class HttpSession(val host: String, val port: Int): AutoCloseable {
    private val socket: Socket = Socket(host, port)
    private val input: InputStream = socket.getInputStream()
    private val output: OutputStream = socket.getOutputStream()

    var charset = Charsets.UTF_8

    override fun close() {
        output.close()
        input.close()
        socket.close()
    }

    fun write(content: String) {
        output.write(content.toByteArray(charset))
    }

    fun readLine(): String {
        ByteArrayOutputStream(512).use {
            var c: Int
            var n: Int
            while (true) {
                c = input.read()
                if (c == -1) break
                if (c == 0x0d) {
                    n = input.read()
                    if (n == 0x0a) break
                    else if (n == -1) {
                        it.write(c)
                        break
                    } else {
                        it.write(c)
                        it.write(n)
                    }
                } else
                    it.write(c)
            }
            return it.toString(charset)
        }
    }

    fun readHeader(): String {
        val result = StringBuffer(512)
        while (true) {
            val line = readLine()
            if (line == "" ) break
            result.append(line + "\n")
        }
        return result.toString()
    }

    fun readBody(header: String = ""): String {
        val hdr = if (header != "") header else readHeader()
        ByteArrayOutputStream(512).use {
            if ("Transfer-Encoding: chunked".toRegex().find(hdr) != null) {
                while (true) {
                    val len = readLine().toInt(16)
                    if (len <= 0) {
                        readLine()
                        break
                    }
                    val b = ByteArray(len)
                    val rlen = input.readNBytes(b, 0, len)
                    if (rlen != len)
                        throw IllegalStateException("Length of data is shorter than expected")
                    it.write(b, 0, rlen)
                    readLine()
                }
            } else {
                val regex = "Content-Length: (\\d+)".toRegex()
                val m = regex.find(hdr)
                if (m != null) {
                    val len = m.groupValues[1].toInt(10)
                    if (len > 0) {
                        val b = ByteArray(len)
                        val rlen = input.readNBytes(b, 0, len)
                        if (rlen != len)
                            throw IllegalStateException("Length of data is shorter than expected")
                        it.write(b, 0, rlen)
                    }
                } else {
                    throw UnsupportedOperationException("Unsupported transfer mode")
                }
            }
            return it.toString(charset)
        }
    }

    fun getBytes(content: String): ByteArray = content.toByteArray(charset)

}