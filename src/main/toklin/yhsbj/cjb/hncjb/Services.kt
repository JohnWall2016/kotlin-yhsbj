package yhsbj.cjb.hncjb

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class JsonService<T>(serviceId: String, params: T) {
    val serviceid = serviceId
    val target = ""
    var sessionid = ""
    var loginname = ""
    var password = ""

    var params: T = params
    val datas = listOf(params)

    companion object {
        fun withoutParms(serviceId: String) = JsonService<Map<Any,Any>>(serviceId, mapOf())
    }

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}

interface IService {
    val id: String
}

open class CustomService(@Transient override val id: String) : IService {}

open class PageService(id: String, page: Int = 1, size: Int = 15): CustomService(id) {
    val page = page;
    val size = size;
    var filtering = listOf<Any>()
    var sorting = listOf<Any>()
    var totals = listOf<Any>()
}

open class RowsService<T>(id: String): CustomService(id) {
    val rows = ArrayList<T>()
}

class Result<T> {
    var rowcount = 0
    var page = 0
    var pagesize = 0
    var serviceid = ""
    var type = ""
    var vcode = ""
    var message = ""
    var messagedetail = ""

    var datas = ArrayList<T>()

    companion object {
        inline fun <reified T> fromJson(json: String) = Gson().fromJson(json, T::class.java)
    }

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}