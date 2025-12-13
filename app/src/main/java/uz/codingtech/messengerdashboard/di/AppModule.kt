package uz.codingtech.messengerdashboard.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.codingtech.messengerdashboard.data.datastore.TokenDataStore
import uz.codingtech.messengerdashboard.data.remote.ApiService
import uz.codingtech.messengerdashboard.data.repository.AuthRepositoryImpl
import uz.codingtech.messengerdashboard.domain.models.AuthUseCaseModel
import uz.codingtech.messengerdashboard.domain.repository.AuthRepository
import uz.codingtech.messengerdashboard.domain.usecase.auth.CheckTokenUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.ClearAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.GetAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.LoginUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.SaveAuthUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://yourapiurl.com/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context
    ): TokenDataStore = TokenDataStore(context)

    @Provides
    @Singleton
    fun provideAuthRepository(
        tokenDataStore: TokenDataStore,
        apiService: ApiService
    ): AuthRepository {
        return AuthRepositoryImpl(tokenDataStore, apiService)
    }

    @Provides
    @Singleton
    fun provideUseCase(authRepository: AuthRepository): AuthUseCaseModel {
        return AuthUseCaseModel(
            loginUseCase = LoginUseCase(authRepository),
            checkTokenUseCase = CheckTokenUseCase(authRepository),
            getAuthUseCase = GetAuthUseCase(authRepository),
            saveAuthUseCase = SaveAuthUseCase(authRepository),
            clearAuthUseCase = ClearAuthUseCase(authRepository)
        )
    }

}