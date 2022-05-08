package com.egoriku.retrosheetkmm.util

object SheetUtils {

    fun toLetterMap(vararg columns: String): Map<String, String> {
       return toLetterMap(columns.toList())
    }

    fun toLetterMap(columns: List<String>): Map<String, String> {
        return columns.mapIndexed { index, columnName ->
            Pair(columnName, getLetterAt(index + 1))
        }.toMap()
    }

    fun getLetterAt(_columnNumber: Int): String {
        var columnNumber = _columnNumber
        // To store result (Excel column name)
        val columnName = StringBuilder()

        while (columnNumber > 0) {
            val rem: Int = columnNumber % 26
            if (rem == 0) {
                columnName.append("Z")
                columnNumber = columnNumber / 26 - 1
            } else {
                columnName.append((rem - 1 + 'A'.code).toChar())
                columnNumber /= 26
            }
        }

        // Reverse the string and print result
        return columnName.reverse().toString()
    }
}
