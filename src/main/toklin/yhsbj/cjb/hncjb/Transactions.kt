package yhsbj.cjb.hncjb

import com.google.gson.annotations.SerializedName

class SysLogin(val username: String, val passwd: String): CustomService("syslogin") {}

class GrinfoQuery(idcard: String): PageService("zhcxgrinfoQuery") {
    // 行政区划编码
    @SerializedName("aaf013")
    var xzqh = ""

    //村级编码
    @SerializedName("aaz070")
    var cjbm = ""

    var aaf101 = ""
    var aac009 = ""

    // 参保状态: "1"-正常参保 "2"-暂停参保 "4"-终止参保 "0"-未参保
    @SerializedName("aac008")
    var cbzt = ""

    // 缴费状态: "1"-参保缴费 "2"-暂停缴费 "3"-终止缴费
    @SerializedName("aac031")
    var jfzt = ""

    var aac006str = ""
    var aac006end = ""
    var aac066 = ""
    var aae030str = ""
    var aae030end = ""
    var aae476 = ""

    @SerializedName("aac003")
    var name = ""

    // 身份证号码
    @SerializedName("aac002")
    var idcard = idcard

    var aae478 = ""
}

class Grinfo {
    // 个人编号
    @SerializedName("aac001")
    var grbh = 0

    // 身份证号码
    @SerializedName("aac002")
    var pid = ""

    @SerializedName("aac003")
    var name = ""

    @SerializedName("aac006")
    var birthday = 0

    // 参保状态: "1"-正常参保 "2"-暂停参保 "4"-终止参保 "0"-未参保
    @SerializedName("aac008")
    var cbzt = ""

    // 户口所在地
    @SerializedName("aac010")
    var hkszd = ""

    // 缴费状态: "1"-参保缴费 "2"-暂停缴费 "3"-终止缴费
    @SerializedName("aac031")
    var jfzt = ""

    @SerializedName("aae005")
    var phone = ""

    @SerializedName("aae006")
    var address = ""

    @SerializedName("aae010")
    var bankcard = ""

    // 村组行政区划编码
    @SerializedName("aaf101")
    var czqh = ""

    // 村组名称
    @SerializedName("aaf102")
    var czmc = ""

    // 村社区名称
    @SerializedName("aaf103")
    var csmc = ""

    val jbztCN: String
        get() = Configs.getJbztCN(jfzt, cbzt)

    /**
     * @return 所属单位名称
     */
    val dwmc: String
        get() = Configs.getXzhqCN(czqh.substring(0, 8))
}
