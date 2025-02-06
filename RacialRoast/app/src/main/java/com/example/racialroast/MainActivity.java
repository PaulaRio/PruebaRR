package com.example.racialroast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.racialroast.ClasesObjeto.Post;
import com.example.racialroast.ClasesObjeto.Usuario;
import com.example.racialroast.DB.Consulta;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
//
        });

        //Se inicializa la clase que realiza las consultas
        Consulta consulta = new Consulta();


        //Traemos todos los nombres de categorias de la base de datos

        LinkedList<String> listaCategorias = consulta.consultaCategorias(this);

        //Creamos el desplegable con las categorias de la base de datos
        Spinner spinnerFiltrar = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,                            // El contexto de la actividad
                android.R.layout.simple_spinner_item, // Layout para cada ítem del Spinner
                listaCategorias               // Lista de resultados obtenidos de la consulta
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerFiltrar.setAdapter(adapter);

        // Personalizar el diseño desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Personalizar elementos del spinner
        ArrayAdapter<CharSequence> customAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, (List)listaCategorias) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Elemento seleccionado
                TextView textView = (TextView) super.getView(position, convertView, parent); //ESTE ES EL DEL MENU
                textView.setTextColor(Color.parseColor("#ffffff")); // Cambiar color
                textView.setTypeface(Typeface.SANS_SERIF); // Cambiar tipo de letra
                textView.setPadding(0,20,0,20);
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Elementos del menú desplegable
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#000000")); // Cambiar color
                textView.setTypeface(Typeface.SANS_SERIF); // Cambiar tipo de letra
                textView.setBackgroundColor(Color.parseColor("#949494"));
                textView.setTextSize(24);
                return textView;
            }
        };

        spinnerFiltrar.setAdapter(customAdapter);

        //
        spinnerFiltrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Obtiene el "id" de la categoria seleccionada, si la "id" es "0" busca en todas las categorias
                String selectedItem = String.valueOf(position).equals("0")?null:String.valueOf(position);
                String [] idCategoria = null;
                if (selectedItem != null){
                    idCategoria = new String[]{selectedItem};
                }

                //CONSULTA
                Consulta consulta = new Consulta();

                //Devuelve todos los chistes que pertenecen a la categoria seleccionada
                ArrayList<Post> listaPost =  consulta.consultaFiltrada(parentView.getContext(), idCategoria);

                //Devuelve los usuarios que han posteado los chistes
                ArrayList<Usuario> listaUsuario = consulta.consultaUsuario(parentView.getContext(),listaPost);

                //Agrega los chistes a la view
                addItems(listaPost, listaUsuario);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Si no se selecciona nada (opcional)
                Log.d("Spinner", "Nada seleccionado");
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else if (id == R.id.SubirChiste) {
                startActivity(new Intent(getApplicationContext(), AnadirChisteActivity.class));
            } else if (id == R.id.perfilLayout) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
            return true;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.premium) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else if (id == R.id.configuracion) {
                startActivity(new Intent(getApplicationContext(), AnadirChisteActivity.class));
            } else if (id == R.id.addChiste) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottonvavigationview, menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    //Transforma el Blop a un Bitmap
    public Bitmap convertBlobToBitmap(byte[] blob) {
        return BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }

    //Agrega los chistes a la view
    public void addItems(List<Post> listaPost,   List<Usuario> listaUsuario)
    {
        //Donde se cargan los chistes
        LinearLayout contenedor = findViewById(R.id.perfil_contenedor);

        //Elimina los chistes cargados anteriormente
        contenedor.removeAllViews();

        //Carga los chistes uno a uno
        for(int i = 0; i < listaPost.size(); i++)
        {
            //View donde se cargan los chistes
            View itemView = LayoutInflater.from(this).inflate(R.layout.itempubli,null);
            ImageView imagen = itemView.findViewById(R.id.perfil_imagenUsuarioPerfil);
            TextView nombreUsuario = itemView.findViewById(R.id.nombreUsuario);
            TextView post = itemView.findViewById(R.id.chisteView);

            //Se cargan los datos
            String nombre = listaUsuario.get(i).getNickname();
            byte[] imagenUsuario = listaUsuario.get(i).getImagen();
            String chiste = listaPost.get(i).getContenido();

            if (imagenUsuario!= null) {
                Bitmap bitmap = convertBlobToBitmap(imagenUsuario);
                imagen.setImageBitmap(bitmap);
            }

            nombreUsuario.setText(nombre);
            post.setText(chiste);

            //Se añade al contenedor
            contenedor.addView(itemView);



        }

    }
}