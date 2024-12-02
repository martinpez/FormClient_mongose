package mongosedb.example.mongose.api;




import mongosedb.example.mongose.models.User;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserApi {

    // Endpoint para crear un usuario
    @POST("users")
    Call<User> createUser(@Body User user);

    // Endpoint para obtener todos los usuarios
    @GET("users/")
    Call<ApiResponse> getUsers();
    @GET("users/getbyemail")
    Call<ApiResponseget> getUserByEmail(@Query("email") String email);


    @DELETE("users/delete")
    Call<ApiResponse> deleteUser(@Query("email") String email);

    @PUT("users/update/{email}")
    Call<ApiResponseget> updateUser(
            @Path("email") String email,
            @Body User user
    );







}

