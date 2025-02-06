package com.example.racialroast.ClasesObjeto;

import java.util.Objects;

public class Post
{
    int id_post;
    int idUsuario;
    String imagen;
    String contenido;
    String tipo_contenido;

    public  Post(int id_post, String contenido, int idUsuario, String imagen, String tipo_contenido)
    {
        this.id_post = id_post;
        this.idUsuario = idUsuario;
        this.contenido = contenido;
        this.imagen = imagen;
        this.tipo_contenido = tipo_contenido;
    }

    public Post(){

    }

    public String getTipo_contenido() {
        return tipo_contenido;
    }

    public void setTipo_contenido(String tipo_contenido) {
        this.tipo_contenido = tipo_contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return getId_post() == post.getId_post();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId_post());
    }

    @Override
    public String toString() {
        return "Chiste{" +
                "id=" + id_post +
                ", idUsuario=" + idUsuario +
                ", contenido='" + contenido + '\'' +
                '}';
    }

}
