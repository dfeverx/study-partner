package app.dfeverx.learningpartner.di


import android.app.Application
import android.content.Context
import app.dfeverx.learningpartner.LearningPartnerApplication
import app.dfeverx.learningpartner.db.AppDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val DATABASE_NAME = "study_partner_db"

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): LearningPartnerApplication {
        return app as LearningPartnerApplication

    }


    @Module
    @InstallIn(SingletonComponent::class)
    class AppModule {
        @Singleton
        @Provides
        fun provideContext(application: Application): Context = application.applicationContext
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context, DATABASE_NAME)
    }


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

}







