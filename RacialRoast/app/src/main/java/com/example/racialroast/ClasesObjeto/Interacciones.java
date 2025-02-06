package com.example.racialroast.ClasesObjeto;
import java.util.Date;

public class Interacciones {
    String id_iteraccion;
    int id_usuario;
    int id_post;
    boolean tipo_interaccion;
    Date fecha;

    public Interacciones(String id_interaccion, int id_usuario, int id_post, boolean tipo_interaccion, Date fecha)
    {
        this.id_iteraccion = id_interaccion;
        this.id_usuario = id_usuario;
        this.tipo_interaccion = tipo_interaccion;
        this.id_post = id_post;
        this.fecha = fecha;
    }

    public String getId_iteraccion() {
        return id_iteraccion;
    }

    public void setId_iteraccion(String id_iteraccion) {
        this.id_iteraccion = id_iteraccion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public boolean isTipo_interaccion() {
        return tipo_interaccion;
    }

    public void setTipo_interaccion(boolean tipo_interaccion) {
        this.tipo_interaccion = tipo_interaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


}
