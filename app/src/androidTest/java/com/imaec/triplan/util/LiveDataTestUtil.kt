package com.imaec.triplan.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Safely handles observables from LiveData for testing.
 */
object LiveDataTestUtil {

    /**
     * Gets the value of a LiveData safely.
     */
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T? {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data
    }
}

/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(observer: Observer<T> = Observer {}, block: () -> Unit) {
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}
