package com.example.racialroast.ClasesObjeto;

public class  PostCategoria {

    int idPost;
    int idCategoria;

    public PostCategoria(int idPost,int idCategoria)
        {
            this.idCategoria = idCategoria;
            this.idPost = idPost;
        }


    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

}
