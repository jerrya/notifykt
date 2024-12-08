package io.github.jerrya.notifykt

import android.content.Context
import androidx.startup.Initializer

class ContextInitializer : Initializer<ContextProvider> {

    override fun create(context: Context): ContextProvider {
        return ContextProvider.create(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
