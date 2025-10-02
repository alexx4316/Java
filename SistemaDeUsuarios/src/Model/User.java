package Model;

public abstract class User {
    private String name;
    private String email;
    private String password;
    private boolean status = true;
    private String rol;

    // Constructor
    public User(String name, String email, String password, String rol) {
        setName(name);
        setEmail(email);
        setPassword(password);
        this.rol = rol;
        this.status = true;
    }

    // Setters y Getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("The name can not be empty");
        }
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")){
            throw new IllegalArgumentException("Invalid Email");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 6){
            throw new IllegalArgumentException("The contrast must be at least 6 characters");
        }
        this.password = password;
    }
    public boolean isActive() {
        return this.status; // ✅ devuelve el valor del campo
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void setActivo(boolean activo){
        this.status = activo;
    }

    public String getRol(){
        return rol;
    }

    public abstract void viewProfile();
}
