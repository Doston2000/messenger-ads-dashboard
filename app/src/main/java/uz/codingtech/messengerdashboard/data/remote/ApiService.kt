package uz.codingtech.messengerdashboard.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import uz.codingtech.messengerdashboard.domain.models.AuthData
import uz.codingtech.messengerdashboard.domain.models.User

interface ApiService {

    @GET("login")
    suspend fun login(@Body user: User): AuthData

    suspend fun checkToken(token: String){

    }

//    @GET("notes")
//    suspend fun getNotes(): List<NoteEntity>
//
//    @POST("notes")
//    suspend fun postNote(@Body note: NoteEntity)
//
//    @PUT("notes/{id}")
//    suspend fun updateNote(
//        @Path("id") id: Int,
//        @Body note: NoteEntity
//    )
//
//    @DELETE("notes/{id}")
//    suspend fun deleteNote(
//        @Path("id") id: Int
//    )

}