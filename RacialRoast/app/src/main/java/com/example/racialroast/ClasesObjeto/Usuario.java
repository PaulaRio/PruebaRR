package com.example.racialroast.ClasesObjeto;
import java.util.Objects;

public class Usuario {

    private int id_usuario;
    private String nombre;
    private String apellidos;
    private String nickname;
    private String email;
    private String password;
    private byte[] imagen;

    public Usuario(){

    }

    public Usuario(int id_usuario, String nombre, String password, String apellidos, String nickname,String email, byte[] imagen)
    {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellidos= apellidos;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.imagen = imagen;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreUsuario) {
        this.nombre = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String contrasena) {
        this.password = contrasena;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return getId_usuario() == usuario.getId_usuario() && Objects.equals(getNombre(), usuario.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_usuario(), getNombre());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id_usuario +
                ", nombreUsuario='" + nombre + '\'' +
                ", contrasena='" + password + '\'' +
                '}';
    }

}