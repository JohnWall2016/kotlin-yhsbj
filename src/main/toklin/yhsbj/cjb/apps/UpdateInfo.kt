package yhsbj.cjb.apps

import yhsbj.cjb.hncjb.Grinfo
import yhsbj.cjb.hncjb.GrinfoQuery
import yhsbj.cjb.hncjb.Result
import yhsbj.cjb.hncjb.Session
import yhsbj.util.Excels
import yhsbj.util.Utils
import yhsbj.util.save

fun main(args: Array<String>) {
    val fn = "D:\\待遇认证\\2018年\\乡镇街上报认证汇总表\\汇总表2.xls"
    val wb = Excels.load(fn)
    val sheet = wb.getSheetAt(0)

    Session.user002 {
        for (i in 4..sheet.lastRowNum) {
            val row = sheet.getRow(i)
            val idcard = row.getCell(5).stringCellValue

            it.send(GrinfoQuery(idcard))
            val rs = it.get<Grinfo>()

            var state = "未在我区参加居保"
            if (rs.datas.size > 0) {
                state = rs.datas[0].jbztCN
            }
            (row.getCell(9) ?: row.createCell(9)).setCellValue(state)

            println("$idcard: $state")
        }
    }

    wb.save(Utils.appendToFileName(fn, ".new"))
}