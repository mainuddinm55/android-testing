package com.example.testingtemplate

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


fun <T> LiveData<T>.getOrWaitValue(
    count: Int = 1,//This for after how many set you want to get value.
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(count)
    val observer = Observer<T> { value ->
        data = value
        latch.countDown()
    }
    this.observeForever(observer)
    try {
        afterObserve.invoke()

        if (!latch.await(time, timeUnit)) {
            if (data != null) {
                removeObserver(observer)
                return data!!
            }
            throw TimeoutException("livedata value was never set.")
        }
    } finally {
        removeObserver(observer)
    }
    return data!!
}