package yhsbj.cjb.apps.inheritance

import yhsbj.cjb.hncjb.Grinfo
import yhsbj.cjb.hncjb.GrinfoQuery
import yhsbj.cjb.hncjb.Session
import yhsbj.util.*
import yhsbj.util.excel.Excels
import yhsbj.util.excel.mustGetCell
import yhsbj.util.excel.save

fun main(args: Array<String>) {
    val fn = "D:\\暂停终止\\死亡继承\\死亡继承汇总表.xls"
    Excels.load(fn).use {
        val sheet = it.getSheetAt(0)
        Session.user002 {
            for (i in 2..105) {
                val row = sheet.getRow(i)
                val idcard = row.getCell(1).stringCellValue
                print("${i - 1} $idcard: ")

                val cbxx = getCbxx(it, idcard)
                row.mustGetCell(2).setCellValue(cbxx.v1)
                row.mustGetCell(4).setCellValue(cbxx.v2)
                row.mustGetCell(5).setCellValue(cbxx.v3)
                row.mustGetCell(6).setCellValue(cbxx.v4)
                print("$cbxx")

                println()
            }
        }
        it.save(Utils.appendToFileName(fn, ".new"))
    }
}

fun getCbxx(s: Session, idcard: String): Tuple4<String, String, String, String> {
    var name = ""
    var hjszd = ""
    var dwmc = ""
    var cbqk: String
    s.send(GrinfoQuery(idcard))
    val rs = s.get<Grinfo>()
    if (rs.datas.size <= 0)
        cbqk = "未参保"
    else {
        val info = rs.datas[0]
        name = info.name
        hjszd = info.czmc
        dwmc = info.dwmc
        cbqk = info.jbztCN
    }
    return Tuple4(name, hjszd, dwmc, cbqk)
}