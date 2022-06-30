package com.jap.twStockApp.ui.condition.filter

class FilterModel(var conditionType: ConditionType, var operator: BiggerOrSmaller, var value: Double) {
}

enum class ConditionType(val displayName: String) {
    Price("現價"),
    UpAndDown("漲跌"),
    UpAndDownPercent("漲跌現價比"),
    WeekUpAndDownPercent("周漲跌現價比"),
    HighestAndLowestPercent("最高最低振福"),
    Open("開盤價"),
    High("最高價"),
    Low("最低價"),
    DealVolume("交易量"),
    DealTotalValue("交易總值"),
    DividendYield("殖利率"),
    PriceToEarningRatio("本益比"),
    PriceBookRatio("股價淨值比"),
    OperatingRevenue("營業收入"),
    MoM("月增率"),
    YoY("年增率"),
    DirectorsSupervisorsRatio("董監持股比例"),
    ForeignInvestmentRatio("外商持股比例"),
    InvestmentRation("投信持股比例"),
    SelfEmployedRation("自營商持股"),
    ThreeBigRatio("三大法人持股比例"),
}

enum class BiggerOrSmaller(val displayName: String){ Bigger(">"), Smaller("<")}