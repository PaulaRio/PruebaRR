package com.example.racialroast.ClasesObjeto;

import java.util.Objects;

public class Categoria
{
    private int id_catergoria;
    private String nombre_cat;


    public Categoria(int id_catergoria, String etiqueta)
    {
        this.id_catergoria = id_catergoria;
        this.nombre_cat = etiqueta;
    }

    public int getId() {
        return id_catergoria;
    }

    public void setId(int id) {
        this.id_catergoria = id_catergoria;
    }

    public String getEtiqueta() {
        return nombre_cat;
    }

    public void setEtiqueta(String etiqueta) {
        this.nombre_cat = etiqueta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return getId() == categoria.getId() && Objects.equals(getEtiqueta(), categoria.getEtiqueta());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEtiqueta());
    }

}
