package ejercicios.dam.intermodulardam.core.network.dependencyInjection


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ejercicios.dam.intermodulardam.login.data.db.UserPreferenceService
import ejercicios.dam.intermodulardam.login.data.network.LoginClient
import ejercicios.dam.intermodulardam.registro.data.network.RegistroClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule @Inject constructor() {


    @Provides
    @Singleton
    fun getInterceptor(context:Context):Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            request.addHeader("Accept", "application/json")
            val actualRequest = request.build()
            it.proceed(actualRequest)
        }
    }

    @Provides
    @Singleton
    fun getHttpClient(interceptor:Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("http://192.168.230.74:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient).build()
    }
    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit) : LoginClient {
        return retrofit.create(LoginClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterClient(retrofit: Retrofit) : RegistroClient {
        return retrofit.create(RegistroClient::class.java)
    }
}