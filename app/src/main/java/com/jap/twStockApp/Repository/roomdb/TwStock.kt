package com.jap.twStockApp.Repository.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

fun TwStock.getParamsByName(name: String): Number? {
    when (name) {
        "現價" -> return Price
        "漲跌" -> return filterInValidStringAndToDouble(UpAndDown)
        "漲跌現價比" -> return filterInValidStringAndToDouble(UpAndDownPercent)
        "周漲跌現價比" -> return filterInValidStringAndToDouble(WeekUpAndDownPercent)
        "最高最低振福" -> return filterInValidStringAndToDouble(HighestAndLowestPercent)
        "開盤價" -> return Open
        "最高價" -> return High
        "最低價" -> return Low
        "交易量" -> return DealVolume
        "交易總值" -> return DealTotalValue
        "殖利率" -> return DividendYield
        "本益比" -> return PriceToEarningRatio
        "股價淨值比" -> return PriceBookRatio
        "營業收入" -> return OperatingRevenue
        "月增率" -> return MoM
        "年增率" -> return YoY
        "董監持股比例" -> return DirectorsSupervisorsRatio
        "外商持股比例" -> return ForeignInvestmentRatio
        "投信持股比例" -> return InvestmentRation
        "自營商持股" -> return SelfEmployedRation
        "三大法人持股比例" -> return ThreeBigRation
    }
    return null
}

private fun filterInValidStringAndToDouble(s: String?): Double? {
    if (s == null) return null
    if (s == "--") return null
    val resultString = s.replaceWithSpace("▲").replaceWithSpace("▼").replaceWithSpace("+")
    return if (resultString.contains("%")) {
        resultString.replaceWithSpace("%").toDouble() * 0.01
    } else {
        resultString.toDouble()
    }
}

private fun String.replaceWithSpace(s: String) = replace(s, "")

@Entity
data class TwStock(
    @PrimaryKey val StockNo: String,
    @ColumnInfo(name = "Name") val Name: String?,
    @ColumnInfo(name = "Price") val Price: Double?,
    @ColumnInfo(name = "UpAndDown") val UpAndDown: String?,
    @ColumnInfo(name = "UpAndDownPercent") val UpAndDownPercent: String?,
    @ColumnInfo(name = "WeekUpAndDownPercent") val WeekUpAndDownPercent: String?,
    @ColumnInfo(name = "HighestAndLowestPercent") val HighestAndLowestPercent: String?,
    @ColumnInfo(name = "Open") val Open: Double?,
    @ColumnInfo(name = "High") val High: Double?,
    @ColumnInfo(name = "Low") val Low: Double?,
    @ColumnInfo(name = "DealVolume") val DealVolume: Int?,
    @ColumnInfo(name = "DealTotalValue") val DealTotalValue: Int?,
    @ColumnInfo(name = "DividendYield") val DividendYield: Double?,
    @ColumnInfo(name = "PriceToEarningRatio") val PriceToEarningRatio: Double?,
    @ColumnInfo(name = "PriceBookRatio") val PriceBookRatio: Double?,
    @ColumnInfo(name = "OperatingRevenue") val OperatingRevenue: Long?,
    @ColumnInfo(name = "MoM") val MoM: Double?,
    @ColumnInfo(name = "YoY") val YoY: Double?,
    @ColumnInfo(name = "DirectorsSupervisorsRatio") val DirectorsSupervisorsRatio: Double?,
    @ColumnInfo(name = "ForeignInvestmentRatio") val ForeignInvestmentRatio: Double?,
    @ColumnInfo(name = "InvestmentRation") val InvestmentRation: Double?,
    @ColumnInfo(name = "SelfEmployedRation") val SelfEmployedRation: Double?,
    @ColumnInfo(name = "ThreeBigRation") val ThreeBigRation: Double?
)

/**
 * key : 代號
 * value schema:
 *
 * Name - 公司名稱
 * Price - 股票現價
 * UpAndDown - 漲跌
 * UpAndDownPercent - 漲跌現價比
 * WeekUpAndDownPercent - 周漲跌現價比
 * HighestAndLowestPercent - 最高最低振福
 * Open - 開盤價
 * High - 最高價
 * Low - 最低價
 * DealVolume - 交易量
 * DealTotalValue - 交易總值
 * DividendYield - 殖利率
 * PriceToEarningRatio - 本益比
 * PriceBookRatio - 股價淨值比
 * OperatingRevenue - 營業收入
 * MoM - 「月增率」指的是跟上個月比起來增加了多少
 * YoY - 「年增率」就是當月營收與去年同期相比的年增率
 * DirectorsSupervisorsRatio - 董監持股比例
 * ForeignInvestmentRatio - 外商持股比例
 * InvestmentRation - 投信持股比例
 * SelfEmployedRation - 自營商持股
 * ThreeBigRation - 三大法人持股比例
 * */
/**
 * 2474=
{DealTotalValue=2.103
, Low=200.5
, PriceBookRatio=1.07
, Price=202
, HighestAndLowestPercent=1.24%
, Open=202
, DividendYield=4.96
, UpAndDownPercent=+0.25%
, UpAndDown=▲0.50
, DealVolume=1,041
, WeekUpAndDownPercent=3.06%
, High=203
, PriceToEarningRatio=14.33
, Name=可成}
 *
 */
