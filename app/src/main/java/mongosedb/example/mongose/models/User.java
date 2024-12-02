package mongosedb.example.mongose.models;

public class User {
    private String lastname;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String rol;

    // Constructor, getters y setters
    public User(String name, String lastname, String email, String Telef , String password, String rol_empresa) {
     this.name = name;
     this.lastname = lastname;
     this.email = email;
     this.phone = Telef;
     this.password = password;
     this.rol = rol_empresa;
     
    }

    // Getters y Setters
    public String getLastname() {
        return lastname;

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}