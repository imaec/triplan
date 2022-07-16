package com.imaec.triplan.rule.activityscenario

import androidx.test.espresso.IdlingRegistry
import com.imaec.triplan.rule.activityscenario.DataBindingIdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A JUnit rule that registers an idling resource for all fragment views that use data binding.
 */
class DataBindingIdlingResourceRule() : TestWatcher() {
    val idlingResource = DataBindingIdlingResource()

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }
}
