package com.jap.twstockapp.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TwStock(
    @PrimaryKey(autoGenerate = true) val StockNo: String?,
    @ColumnInfo(name = "Name") val Name: String?,
    @ColumnInfo(name = "Price") val Price: Float?,
    @ColumnInfo(name = "UpAndDown") val UpAndDown: Float?,
    @ColumnInfo(name = "UpAndDownPercent") val UpAndDownPercent: Float?,
    @ColumnInfo(name = "Name") val Name: Float?,
    @ColumnInfo(name = "Name") val Name: Float?,
    @ColumnInfo(name = "Name") val Name: Float?,
    @ColumnInfo(name = "Name") val Name: Float?,
    @ColumnInfo(name = "Name") val Name: Float?,
    @ColumnInfo(name = "Name") val Name: Float?
){
    constructor(Name: String?,Price: Float?) : this(null,Name,Price)
}

/**
 * key : 代號
 * value schema:
 *
 * Name - 公司名稱
 * Price - 股票現價
 * UpAndDown - 漲跌
 * UpAndDownPercent - 漲跌現價比
 * WeekUpAndDownPercent - 周漲跌現價比
 *  - 最高最低振福
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
 * ThreeBigRation - 三大法人持股比例
 * */