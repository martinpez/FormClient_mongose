package mongosedb.example.mongose;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;


import java.util.List;

import mongosedb.example.mongose.api.ApiResponse;
import mongosedb.example.mongose.api.UserApi;


import mongosedb.example.mongose.models.User;
import mongosedb.example.mongose.network.ApiClient;
import mongosedb.example.mongose.view.ShowViewAlert;
import mongosedb.example.mongose.view.UserAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class view_user extends Fragment {

    private RecyclerView RecyView;

    private ProgressBar progressBar;
    private ImageView backView2;

    ShowViewAlert alert = new ShowViewAlert();



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public view_user() {

    }



    public static view_user newInstance(String param1, String param2) {
        view_user fragment = new view_user();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyView = view.findViewById(R.id.RecyView);
        progressBar = view.findViewById(R.id.progressBar);
        backView2 = view.findViewById(R.id.backView2);

        backView2.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.home2);
        });

        RecyView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadUsers();

    }

    private void loadUsers() {
        progressBar.setVisibility(View.VISIBLE);

        UserApi userApi = ApiClient.getRetrofitInstance().create(UserApi.class);
        Call<ApiResponse> call = userApi.getUsers();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {

                    List<User> userList = response.body().getData();
                    System.out.println(response.body().getData());


                    UserAdapter adapter = new UserAdapter(userList, getContext(), new UserAdapter.OnUserActionListener() {
                        @Override
                        public void onUpdate(String email) {
                            showUpdateDialog(email);

                        }

                        @Override
                        public void onDelete(String email) {

                            alert.showAlertQuestions(getContext(),"Eliminar" ,"¿Desea eliminar este usuario?" , () ->

                                    deleteUser(email)
                                    );


                        }
                    });
                    RecyView.setAdapter(adapter);
                } else {
                    alert.showAlertDialog(getContext(), "Error", "No se pudieron cargar los usuarios");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                alert.showAlertDialogErorConexiondb(getContext(), "Error", "Fallo en la conexión");
            }
        });

    }


    private void showUpdateDialog(String email) {
        System.out.println("Boton presionado para actualizar");
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        Navigation.findNavController(getView()).navigate(R.id.viewUpdate, bundle);
    }

    private void deleteUser(String email) {
        System.out.println("Boton presionado eliminar");

        UserApi userApi = ApiClient.getRetrofitInstance().create(UserApi.class);

        Call<ApiResponse> call = userApi.deleteUser(email);
        call.enqueue(new Callback<ApiResponse>(){
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response){
                if (response.isSuccessful()) {
                    alert.showAlertSuccessful(getContext(), "Éxito", "Usuario eliminado correctamente." ,  () ->
                        loadUsers()
                            ,
                            null, null, null, null, null
                    );

                } else {
                    alert.showAlertDialog(getContext(), "Error", "No se pudo eliminar el usuario.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                alert.showAlertDialogErorConexiondb(getContext(), "Error", "Error de conexión.");
                System.out.println(t.getMessage());
            }
        });
    }

}
