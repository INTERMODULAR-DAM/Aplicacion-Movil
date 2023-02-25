package ejercicios.dam.intermodulardam.dependencyInjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ejercicios.dam.intermodulardam.comments.data.network.CommentClient
import ejercicios.dam.intermodulardam.login.data.network.LoginClient
import ejercicios.dam.intermodulardam.profile.data.network.ProfileClient
import ejercicios.dam.intermodulardam.register.data.network.RegisterClient
import ejercicios.dam.intermodulardam.main.data.network.RouteClient
import ejercicios.dam.intermodulardam.utils.Constants.IP_ADDRESS
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRegisterClient(retrofit: Retrofit) : RegisterClient {
        return retrofit.create(RegisterClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRutasClient(retrofit: Retrofit): RouteClient {
        return retrofit.create(RouteClient::class.java)
    }

    @Singleton
    @Provides
    fun provideComentariosClient(retrofit: Retrofit):CommentClient {
        return retrofit.create(CommentClient::class.java)
    }

    @Singleton
    @Provides
    fun providePerfilClient(retrofit: Retrofit):ProfileClient {
        return retrofit.create(ProfileClient::class.java)
    }
}