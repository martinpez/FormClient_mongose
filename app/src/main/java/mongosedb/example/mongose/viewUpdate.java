package mongosedb.example.mongose;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mongosedb.example.mongose.api.ApiResponse;
import mongosedb.example.mongose.api.ApiResponseget;
import mongosedb.example.mongose.api.UserApi;
import mongosedb.example.mongose.models.User;
import mongosedb.example.mongose.network.ApiClient;
import mongosedb.example.mongose.view.ShowViewAlert;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class viewUpdate extends Fragment {

    private ImageView backView, img_entry_acc;
    private Spinner spinner_rol, spinner_num;
    private EditText input_name, input_lastname, input_email, input_phone, input_password;
    ShowViewAlert alert = new ShowViewAlert();




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public viewUpdate() {

    }

    public static viewUpdate newInstance(String param1, String param2) {
        viewUpdate fragment = new viewUpdate();
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

        return inflater.inflate(R.layout.fragment_view_update, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        input_name = view.findViewById(R.id.input_name);
        input_email = view.findViewById(R.id.input_email);
        spinner_num = view.findViewById(R.id.spinner_num);
        spinner_rol = view.findViewById(R.id.spinner_rol);
        input_phone = view.findViewById(R.id.input_phone);
        input_password = view.findViewById(R.id.input_password);
        input_lastname = view.findViewById(R.id.input_lastname);
        backView = view.findViewById(R.id.backView);
        img_entry_acc = view.findViewById(R.id.img_entry_acc);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item,
                getResources().getStringArray(R.array.phone_identifiers_array)
        );
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_num.setAdapter(adapter);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), R.layout.spinner_item,
                getResources().getStringArray(R.array.rolEmpresa)
        );
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_rol.setAdapter(adapter2);


        System.out.println("Correo del cliente " + getArguments().get("email"));
       getUserData(getArguments().getString("email"));


        backView.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.view_user);
        });

        img_entry_acc.setOnClickListener(v -> {
        validador();
        });

    }

    public void validador() {
        String input1 = input_name.getText().toString().trim();
        String input2 = input_email.getText().toString().trim();
        String input3 = input_phone.getText().toString().trim();
        String input4 = input_password.getText().toString().trim();
        String input5 = input_lastname.getText().toString().trim();

        if (!(input1.isEmpty() || input2.isEmpty() || input3.isEmpty() || input4.isEmpty() || input5.isEmpty())) {
            String rol_empresa = spinner_rol.getSelectedItem().toString();
            String Telef = spinner_num.getSelectedItem().toString() + input3;
            String password = input4;
            String name = input1;
            String lastname = input5;
            String email = input2;
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            boolean isValid = true;

            if (!input_email.getText().toString().matches(emailPattern)) {
                input_email.setError("Email no válido");
                isValid = false;
            }

            if (input_password.getText().toString().length() < 8) {
                input_password.setError("La contraseña debe tener al menos 8 caracteres");
                isValid = false;
            }

            if (input_phone.getText().toString().length() < 10) {
                input_phone.setError("El teléfono debe tener al menos 10 caracteres");
                isValid = false;
            }
            if (isValid) {
                User user = new User(name, lastname, email, Telef, password, rol_empresa);

                System.out.println("name:" + name + "lastname:" + lastname + "email:" + email + "phone:" + Telef + "password:" + password + "rol_empresa:" + rol_empresa);
                User updatedUser = new User(name, lastname, email, Telef, password, rol_empresa);
                UserApi userApi = ApiClient.getRetrofitInstance().create(UserApi.class);
                Call<ApiResponseget> call = userApi.updateUser(email, updatedUser);
                call.enqueue(new Callback<ApiResponseget>(){
                    @Override
                    public void onResponse(Call<ApiResponseget> call, Response<ApiResponseget> response) {

                        if (response.isSuccessful()) {
                            alert.showAlertSuccessful(getContext(), "Éxito", "Usuario Actualizado exitosamente", () ->
                                            Navigation.findNavController(getView()).navigate(R.id.view_user)
                                    ,
                                    input_name, input_lastname, input_email, input_phone, input_password
                            );

                        } else {
                            try {
                                String errorBody = response.errorBody().string();
                                JSONObject errorObject = new JSONObject(errorBody);
                                String errorMessage = errorObject.getString("message");

                                if (response.code() == 400 && errorMessage.equals("El correo ya está registrado.")) {
                                    alert.showAlertDialogErorConexiondb(getContext(), "Error", "El correo ya está registrado.");
                                } else {
                                    alert.showAlertDialog(getContext(), "Error", "Error al Actualizar el usuario");
                                }
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                                alert.showAlertDialogErorConexiondb(getContext() , "Error" ,"Error al procesar la respuesta del servidor" );
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponseget> call, Throwable t) {
                        alert.showAlertDialog(getContext(), "Error", "Fallo en la conexión");
                        System.out.println(t.getMessage());
                    }

                });
            } else {
                alert.showAlertDialog(getContext(), "Error", "Por favor corrige los Campos.");

            }

        } else {
            alert.showAlertDialog(getContext(), "Error", "Por favor, complete todos los campos.");
        }
    }


    private void populateFields(User user) {
        input_name.setText(user.getName());
        input_email.setText(user.getEmail());
        input_phone.setText(user.getPhone());
        input_password.setText(user.getPassword());
        input_lastname.setText(user.getLastname());
        setRoleSpinnerSelection(user.getRol());
    }

    private void setRoleSpinnerSelection(String rol) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner_rol.getAdapter();
        int position = adapter.getPosition(rol);
        if (position >= 0) {
            spinner_rol.setSelection(position);
        }
    }

    private void getUserData(String email) {
        UserApi userApi = ApiClient.getRetrofitInstance().create(UserApi.class);
        Call<ApiResponseget> call = userApi.getUserByEmail(email);
        call.enqueue(new Callback<ApiResponseget>() {
            @Override
            public void onResponse(Call<ApiResponseget> call, Response<ApiResponseget> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponseget apiResponse = response.body();
                    User user = apiResponse.getData();
                    populateFields(user);
                } else {

                    alert.showAlertDialog(getContext(), "Eroor","Usuario no encontrado o error en la API");
                }
            }

            @Override
            public void onFailure(Call<ApiResponseget> call, Throwable t) {
                alert.showAlertDialog(getContext(), "Error", "Error en la conexión");
                System.out.println("API Error" + t.getMessage());
            }
        });
    }




}