package com.jap.twstockapp.di.condition

import com.jap.twstockapp.ui.condition.ConditionFragment
import dagger.Subcomponent

@ConditionScope
@Subcomponent(modules = [ConditionModule::class])
interface ConditionComponent {
    fun inject(conditionFragment: ConditionFragment)
}
