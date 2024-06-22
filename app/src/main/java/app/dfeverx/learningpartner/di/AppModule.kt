package app.dfeverx.learningpartner.di


import android.app.AlarmManager
import android.app.Application
import android.content.Context
import app.dfeverx.learningpartner.LearningPartnerApplication
import app.dfeverx.learningpartner.db.AppDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.ocpsoft.prettytime.PrettyTime
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

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext app: Context): AlarmManager {
        return app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    }

    /*   @Singleton
       @Provides
       fun provideGso(@ApplicationContext app: Context): GoogleSignInOptions {
           return GoogleSignInOptions
               .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(ContextCompat.getString(app, R.string.web_client_id))
               .requestEmail().build()

       }*/


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

    @Provides
    @Singleton
    fun providePrettyTime(): PrettyTime {
        return PrettyTime()
    }

    @Provides
    @Singleton
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
//        setting default value
//        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
        return remoteConfig
    }

}







