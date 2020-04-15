package irina.activityreminder.di

import android.content.Context

object ComponentContainer {
    lateinit var component: AppComponent
    fun buildComponent(context: Context) {
        component = DaggerAppComponent.builder()
            .singletonModule(SingletonModule)
            .appModule(AppModule(context))
            .dataModule(DataModule())
            .build()
    }
}