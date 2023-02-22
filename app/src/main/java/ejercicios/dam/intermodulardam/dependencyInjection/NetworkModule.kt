package ejercicios.dam.intermodulardam.dependencyInjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ejercicios.dam.intermodulardam.comments.data.network.ComentariosClient
import ejercicios.dam.intermodulardam.login.data.network.LoginClient
import ejercicios.dam.intermodulardam.profile.data.network.ProfileClient
import ejercicios.dam.intermodulardam.register.data.network.RegistroClient
import ejercicios.dam.intermodulardam.rutas.data.network.RutasClient
import ejercicios.dam.intermodulardam.utils.Constants.IP_ADDRESS
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun getInterceptor():Interceptor {
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
        return Retrofit.Builder().baseUrl("http://$IP_ADDRESS/")
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

    @Singleton
    @Provides
    fun provideRutasClient(retrofit: Retrofit):RutasClient {
        return retrofit.create(RutasClient::class.java)
    }

    @Singleton
    @Provides
    fun provideComentariosClient(retrofit: Retrofit):ComentariosClient {
        return retrofit.create(ComentariosClient::class.java)
    }

    @Singleton
    @Provides
    fun providePerfilClient(retrofit: Retrofit):ProfileClient {
        return retrofit.create(ProfileClient::class.java)
    }
}