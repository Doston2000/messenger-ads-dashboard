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
import uz.codingtech.messengerdashboard.data.repository.OrderRepositoryImpl
import uz.codingtech.messengerdashboard.data.repository.UserRepositoryImpl
import uz.codingtech.messengerdashboard.domain.models.AuthUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.OrderUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.UserUseCaseModel
import uz.codingtech.messengerdashboard.domain.repository.AuthRepository
import uz.codingtech.messengerdashboard.domain.repository.OrderRepository
import uz.codingtech.messengerdashboard.domain.repository.UserRepository
import uz.codingtech.messengerdashboard.domain.usecase.auth.CheckTokenUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.ClearAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.GetAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.LoginUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.RefreshTokenUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.SaveAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.CancelOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.ChangeActiveOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.GetOrderByIdUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.GetOrdersUseCase
import uz.codingtech.messengerdashboard.domain.usecase.order.PostOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.user.GetBalanceUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://185.203.241.191:8000/api/"

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
        apiService: ApiService,
    ): AuthRepository {
        return AuthRepositoryImpl(tokenDataStore, apiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        tokenDataStore: TokenDataStore,
        apiService: ApiService,
    ): UserRepository {
        return UserRepositoryImpl(tokenDataStore, apiService)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        tokenDataStore: TokenDataStore,
        apiService: ApiService,
    ): OrderRepository {
        return OrderRepositoryImpl(tokenDataStore, apiService)
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCaseModel {
        return AuthUseCaseModel(
            loginUseCase = LoginUseCase(authRepository),
            checkTokenUseCase = CheckTokenUseCase(authRepository),
            refreshTokenUseCase = RefreshTokenUseCase(authRepository),
            getAuthUseCase = GetAuthUseCase(authRepository),
            saveAuthUseCase = SaveAuthUseCase(authRepository),
            clearAuthUseCase = ClearAuthUseCase(authRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCaseModel {
        return UserUseCaseModel(
            getBalanceUseCase = GetBalanceUseCase(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideOrderUseCase(orderRepository: OrderRepository): OrderUseCaseModel {
        return OrderUseCaseModel(
            getOrdersUseCase = GetOrdersUseCase(orderRepository),
            postOrderUseCase = PostOrderUseCase(orderRepository),
            getOrderByIdUseCase = GetOrderByIdUseCase(orderRepository),
            cancelOrderUseCase = CancelOrderUseCase(orderRepository),
            changeActiveOrderUseCase = ChangeActiveOrderUseCase(orderRepository)
        )
    }

}