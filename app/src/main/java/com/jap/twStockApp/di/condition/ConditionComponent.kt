package com.jap.twStockApp.di.condition

import com.jap.twStockApp.ui.condition.ConditionFragment
import dagger.Subcomponent

@ConditionScope
@Subcomponent(modules = [ConditionModule::class])
interface ConditionComponent {
    fun inject(conditionFragment: ConditionFragment)
}
