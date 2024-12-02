package mongosedb.example.mongose.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {
    // Trabajar localmente Emulador Android
  private static final String BASE_URL = "http://10.0.2.2:3000/api/";

    //Para trabajar por medio de cualquiera otra red consultando su direccion ip el cumputador debe estar en la misma red
 //   private static final String BASE_URL = "https://6da4-190-242-99-227.ngrok-free.app/api/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}


