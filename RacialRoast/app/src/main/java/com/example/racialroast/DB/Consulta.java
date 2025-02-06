package com.example.racialroast.DB;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.racialroast.ClasesObjeto.Post;
import com.example.racialroast.ClasesObjeto.Usuario;

import java.util.ArrayList;
import java.util.LinkedList;

public class Consulta {


    //METODO DE PRUEBA PARA TRAER TODOS LOS CHISTES. SE BORRARA EN EL FUTURO
    public ArrayList<Post> consultaAll(Context c) {

        Conector conector = new Conector(c);
        SQLiteDatabase db = conector.getReadableDatabase();

        Cursor cur = db.rawQuery("select * from posts", null);

        ArrayList<Post> lista = new ArrayList<Post>();

        cur.moveToFirst();
        int columna = cur.getColumnCount();
        do {
            Post post = new Post();

            post.setId_post(cur.getInt(0));
            post.setContenido(cur.getString(1));
            post.setIdUsuario(cur.getInt(2));
            lista.add(post);

        } while (cur.moveToNext());

        cur.close();
        return lista;
    }

    //METODO QUE DEVUELVE EL NOMBRE DE TODAS LAS CATEGORIAS QUE SE ENCUENTRAN EN LA BASE DE DATOS
    public LinkedList<String> consultaCategorias(Context c) {

        Conector conector = new Conector(c);
        SQLiteDatabase db = conector.getReadableDatabase();

        Cursor cur = db.rawQuery("select nombre from categorias order by id asc;", null);

        LinkedList<String> lista = new LinkedList<String>();
        lista.add("TODOS");

        cur.moveToFirst();
        int columna = cur.getColumnCount();
        do {
            lista.add(cur.getString(0));
        } while (cur.moveToNext());
        cur.close();
        return lista;
    }

    //METODO QUE DEVUELVE EL "nickname" Y "foto" DE LOS USUARIOS A PARTIR DE LOS CHISTES PASADOS POR PARAMETRO
    public ArrayList<Usuario> consultaUsuario(Context c, ArrayList<Post> post){
        Conector conector = new Conector(c);
        SQLiteDatabase db = conector.getReadableDatabase();

        ArrayList<Usuario> lista = new ArrayList<Usuario>();

        String[] selection = {"nickname", "foto"};
        for (Post ver : post){
            String[] where = {String.valueOf(ver.getIdUsuario())};
            Cursor cur = db.query("usuarios",selection,"id =?",where,null,null,null);

            cur.moveToFirst();
            Usuario user = new Usuario();
            user.setNickname(cur.getString(0));
            user.setImagen(cur.getBlob(1));

            cur.close();
            lista.add(user);
        }

        return lista;
    }

    //METODO QUE DEVUELVE TODOS LOS CHISTES PERTENECIENTES A LAS CATEGORIAS PASADAS POR PARAMETRO
    public ArrayList<Post> consultaFiltrada(Context c, String[] categorias){
        Conector conector = new Conector(c);
        SQLiteDatabase db = conector.getReadableDatabase();

        ArrayList<Post> lista = new ArrayList<Post>();

        //CONSULTAMOS LA ID DE LOS CHISTES QUE PERTENECEN A LAS CATEGORIAS
        String table = "post_categorias";
        String[] columns = {"id_post"};

        String selection = null;
        String[] selectionArgs = null;

        if (categorias !=  null){
            selection = generarSelectionIN("id_categoria",categorias);
            selectionArgs = categorias;
        }

        Cursor cur = db.query(table, columns, selection, selectionArgs,null,null,null);
        cur.moveToFirst();

        int rows = cur.getCount();
        selectionArgs = new String[rows];
        for (int i = 0; i<rows;i++){
            selectionArgs[i] = cur.getString(0);
            cur.moveToNext();
        }


        //CONSULTAMOS EL CONTENIDO DE LOS CHISTES A PARTIR DE LAS IDS CONSEGUIDAS ANTERIORMENTE
        table = "posts";
        columns = new String[]{"contenido", "id_usuario"};
        selection = generarSelectionIN("id",selectionArgs);
        cur = db.query(table, columns, selection, selectionArgs, null, null, null);


        cur.moveToFirst();
        int columna = cur.getColumnCount();
        if (cur.getCount() > 0) {


        do {
            Post post = new Post();

            post.setContenido(cur.getString(0));
            post.setIdUsuario(cur.getInt(1));
            lista.add(post);

        } while (cur.moveToNext());
        }
        cur.close();
        return lista;

    }

    //METODO QUE GENERA UN STRING PARA EL "selection" DEL METODO QUERY
    //AGREGA UN "?" POR CADA CATEGORIA
    //EJEMPLO: "id in (?,?,?)"
    private static String generarSelectionIN(String selection,String[] categorias) {

        StringBuilder selectionBuilder = new StringBuilder(selection+" IN (");
        for (int i = 0; i < categorias.length; i++) {
            selectionBuilder.append("?");
            if (i < categorias.length - 1) {
                selectionBuilder.append(",");
            }
        }
        selectionBuilder.append(")");
        return selectionBuilder.toString();
    }
    //METODO QUE CONFIRMA SI LAS CREDENCIALES DEL LOGIN SON CORRECTAS
    public boolean validarLogin(Context context, String email, String contrasena) {

        Conector conector = new Conector(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
         try{

        db = conector.getReadableDatabase();

        String query = "SELECT password FROM usuarios WHERE email = ?";
        String[] args = new String[] {email};

        cursor = db.rawQuery(query, args);

        if (cursor.moveToFirst()) {

            String contrasenaGuardada = cursor.getString(0);


            return contrasenaGuardada.equals(contrasena);
        }
         }catch(Exception e) {
         e.printStackTrace();
         }finally {
             if (cursor != null) cursor.close();
             if (db != null) db.close();
         }
        return false;
    }
    //METODO QUE COMPRUEBA SI EL EMAIL INTRODUCIDO EN EL REGISTRO YA EXISTE EN LA BASE DE DATOS
    public boolean emailExistente(Context context, String email) {

        Conector conector = new Conector(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{

            db = conector.getReadableDatabase();

            String query = "SELECT * FROM usuarios WHERE email = ?";
            String[] args = new String[] {email};

            cursor = db.rawQuery(query, args);

            if(cursor.getCount() > 0) {
                return true;
            }else {
                return false;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return false;
    }
    //METODO QUE COMPRUEBA SI EL NICKNAME INTRODUCIDO EN EL REGISTRO YA EXISTE EN LA BASE DE DATOS
    public boolean nicknameExistente(Context context, String nickname) {

        Conector conector = new Conector(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{

            db = conector.getReadableDatabase();

            String query = "SELECT * FROM usuarios WHERE nickname = ?";
            String[] args = new String[] {nickname};

            cursor = db.rawQuery(query, args);

            if(cursor.getCount() > 0) {
                return true;
            }else {
                return false;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return false;
    }

    public ArrayList<Post> consultaChistesUsuario(Context c, int idUsuario) {
        Conector conector = new Conector(c);
        SQLiteDatabase db = conector.getReadableDatabase();

        ArrayList<Post> lista = new ArrayList<>();

        // Consulta los chistes subidos por el usuario
        String[] columns = {"id", "contenido", "id_usuario"};
        String selection = "id_usuario = ?";
        String[] selectionArgs = {String.valueOf(idUsuario)};

        Cursor cur = db.query("posts", columns, selection, selectionArgs, null, null, null);

        if (cur.moveToFirst()) {
            do {
                Post post = new Post();
                post.setId_post(cur.getInt(0));
                post.setContenido(cur.getString(1));
                post.setIdUsuario(cur.getInt(2));
                lista.add(post);
            } while (cur.moveToNext());
        }
        cur.close();

        return lista;
    }
    //METODO QUE AÑADE LOS DATOS DEL USUARIO QUE SE ESTA REGISTRANDO A LA BASE DE DATOS
    public Boolean addUsuario(Context c, String nombre, String apellidos, String nickname, String email, String password) {
        Boolean exito=false;
        Conector conector = new Conector(c);
        SQLiteDatabase db = conector.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellidos", apellidos);
        values.put("nickname", nickname);
        values.put("email", email);
        values.put("password", password);
        // values.put("es_premium", es_premium);
        // values.put("foto", imagen);

        long result = db.insert("usuarios", null, values);
        Log.d("DB_INSERT", "Resultado de inserción: " + result);
        db.close();
        Log.d("DB_CLOSE", "Base de datos cerrada.");
        if (result == -1) {
            Log.e("DB_ERROR", "Error al insertar el usuario en la base de datos.");
            return false;
            //throw new RuntimeException("Error al insertar el usuario en la base de datos.");
        }
        Log.d("DB_SUCCESS", "Usuario insertado correctamente con ID: " + result);
        return true;
//        String sql = "INSERT INTO usuarios(nombre, apellidos, nickname, email, password, es_premium,  imagen) VALUES(?, ?, ?, ?, ?, ?, ?)";
//        db.execSQL(sql, new Object[]{nombre, apellidos, nickname, email, password, es_premium,  imagen});



    }

    public int obtenerIdUsuario(Context context, String email) {
        Conector conector = new Conector(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int userId = -1;

        try {
            db = conector.getReadableDatabase(); // Inicializa db correctamente

            // Realiza la consulta para obtener el ID del usuario basado en su email
            cursor = db.rawQuery("SELECT id FROM usuarios WHERE email = ?", new String[]{email});

            if (cursor.moveToFirst()) {
                userId = cursor.getInt(0); // Obtener el ID del usuario
            }

        } catch (Exception e) {
            e.printStackTrace(); // Maneja cualquier excepción, si ocurre
        } finally {
            if (cursor != null) {
                cursor.close(); // Cierra el cursor
            }
            if (db != null) {
                db.close(); // Cierra la base de datos
            }
        }

        return userId; // Retorna el ID o -1 si no encontró el usuario
    }

    public void addChiste(Context c, String contenido, int id_usuario) {
        Conector conector = new Conector(c);
        SQLiteDatabase db = conector.getWritableDatabase();

        String sql= "INSERT INTO posts(contenido, id_usuario) VALUES(?, ?)";
        db.execSQL(sql, new Object[]{contenido, id_usuario});

        db.close();
    }

    public int getIdUsuarioLogueado(Context context) {
        // Obtenemos las preferencias compartidas con el nombre "SesionUsuario"
        SharedPreferences sharedPreferences = context.getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);

        // Retornamos el ID del usuario logueado, o -1 si no hay usuario guardado
        return sharedPreferences.getInt("id", -1);
    }

}