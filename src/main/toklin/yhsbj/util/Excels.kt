package yhsbj.util

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.nio.file.Files
import java.nio.file.Paths

object Excels {
    enum class Type { XLS, XLSX, AUTO }

    class UnsupportedTypeException: Exception("Unsupported excel type")

    fun load(fileName: String, type: Type = Type.AUTO): Workbook {
        var tp = type
        if (fileName.endsWith(".xls", true))
            tp = Type.XLS
        else if (fileName.endsWith(".xlsx", true))
            tp = Type.XLSX
        when (tp) {
            Type.XLS -> return HSSFWorkbook(Files.newInputStream(Paths.get(fileName)))
            Type.XLSX -> return XSSFWorkbook(Files.newInputStream(Paths.get(fileName)))
            else -> throw UnsupportedTypeException()
        }
    }

    fun Workbook.save(fileName: String) {
        Files.newOutputStream(Paths.get(fileName)).use {
            this.write(it)
        }
    }

    fun Sheet.createRow(dstRowIdx: Int, srcRowIdx: Int): Row {
        val dstRow = this.createRow(dstRowIdx)
        val srcRow = this.getRow(srcRowIdx)
        dstRow.height = srcRow.height
        for (idx in srcRow.firstCellNum until srcRow.physicalNumberOfCells) {
            val dstCell = dstRow.createCell(idx)
            val srcCell = srcRow.getCell(idx)
            dstCell.cellType = srcCell.cellType
            dstCell.cellStyle = srcCell.cellStyle
            dstCell.setCellValue("")
        }
        return dstRow
    }

    fun Sheet.getOrCopyRow(dstRowIdx: Int, srcRowIdx: Int): Row {
        if (dstRowIdx <= srcRowIdx) {
            return this.getRow(srcRowIdx)
        }
        else {
            if (this.lastRowNum >= dstRowIdx)
                this.shiftRows(dstRowIdx, this.lastRowNum, 1, true, false)
            return createRow(dstRowIdx, srcRowIdx)
        }
    }

    fun Sheet.copyRows(start: Int, count: Int, srcRowIdx: Int) {
        this.shiftRows(start, this.lastRowNum, count, true, false)
        for (i in 0 until count)
            createRow(start + i, srcRowIdx)
    }
}