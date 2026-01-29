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
import uz.codingtech.messengerdashboard.data.repository.ChatOrderRepositoryImpl
import uz.codingtech.messengerdashboard.data.repository.PostOrderRepositoryImpl
import uz.codingtech.messengerdashboard.data.repository.UserRepositoryImpl
import uz.codingtech.messengerdashboard.domain.models.AuthUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.ChatOrderUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderUseCaseModel
import uz.codingtech.messengerdashboard.domain.models.UserUseCaseModel
import uz.codingtech.messengerdashboard.domain.repository.AuthRepository
import uz.codingtech.messengerdashboard.domain.repository.ChatOrderRepository
import uz.codingtech.messengerdashboard.domain.repository.PostOrderRepository
import uz.codingtech.messengerdashboard.domain.repository.UserRepository
import uz.codingtech.messengerdashboard.domain.usecase.auth.CheckTokenUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.ClearAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.GetAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.LoginUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.RefreshTokenUseCase
import uz.codingtech.messengerdashboard.domain.usecase.auth.SaveAuthUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.CancelChatOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.ChangeActiveChatOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.GetChatOrderByIdUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.GetChatOrdersUseCase
import uz.codingtech.messengerdashboard.domain.usecase.chat_order.PostChatOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.CancelPostOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.ChangeActivePostOrderUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.GetPostOrderByIdUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.GetPostOrdersUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.PostOrderPostUseCase
import uz.codingtech.messengerdashboard.domain.usecase.post_order.UploadMediaUseCase
import uz.codingtech.messengerdashboard.domain.usecase.user.GetBalanceUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //    private const val BASE_URL = "http://192.168.1.12:8000/api/"
    private const val BASE_URL = "https://nonmutational-hipolito-unravaged.ngrok-free.dev/api/"

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
    fun providesContext(
        @ApplicationContext context: Context
    ): Context = context

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
    fun provideChatOrderRepository(
        tokenDataStore: TokenDataStore,
        apiService: ApiService,
    ): ChatOrderRepository {
        return ChatOrderRepositoryImpl(tokenDataStore, apiService)
    }

    @Provides
    @Singleton
    fun providePostOrderRepository(
        tokenDataStore: TokenDataStore,
        apiService: ApiService,
        @ApplicationContext context: Context
    ): PostOrderRepository {
        return PostOrderRepositoryImpl(tokenDataStore, apiService, context )
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
    fun provideChatOrderUseCase(orderRepository: ChatOrderRepository): ChatOrderUseCaseModel {
        return ChatOrderUseCaseModel(
            getOrdersUseCase = GetChatOrdersUseCase(orderRepository),
            postOrderUseCase = PostChatOrderUseCase(orderRepository),
            getOrderByIdUseCase = GetChatOrderByIdUseCase(orderRepository),
            cancelOrderUseCase = CancelChatOrderUseCase(orderRepository),
            changeActiveOrderUseCase = ChangeActiveChatOrderUseCase(orderRepository)
        )
    }

    @Provides
    @Singleton
    fun providePostOrderUseCase(orderRepository: PostOrderRepository): PostOrderUseCaseModel {
        return PostOrderUseCaseModel(
            uploadMediaUseCase = UploadMediaUseCase(orderRepository),
            postOrderPostUseCase = PostOrderPostUseCase(orderRepository),
            getPostOrdersUseCase = GetPostOrdersUseCase(orderRepository),
            getPostOrderByIdUseCase = GetPostOrderByIdUseCase(orderRepository),
            cancelPostOrderUseCase = CancelPostOrderUseCase(orderRepository),
            changeActivePostOrderUseCase = ChangeActivePostOrderUseCase(orderRepository)
        )
    }

}