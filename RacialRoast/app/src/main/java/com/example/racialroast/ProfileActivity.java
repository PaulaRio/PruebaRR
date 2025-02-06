package com.example.racialroast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.racialroast.DB.Consulta;
import com.example.racialroast.ClasesObjeto.Post;
import com.example.racialroast.ClasesObjeto.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        // Obtén el contenedor de chistes
        LinearLayout contenedor = findViewById(R.id.perfil_contenedor);

        Consulta consulta = new Consulta();

        int usuarioId = consulta.getIdUsuarioLogueado(this);
        if (usuarioId != -1) {
            Toast.makeText(this, "Usuario logueado ID: " + usuarioId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show();
        }

        // Obtén los chistes del usuario
        ArrayList<Post> chistesUsuario = consulta.consultaChistesUsuario(this, usuarioId);

        // Obtén los usuarios correspondientes a los chistes
        ArrayList<Usuario> usuarios = consulta.consultaUsuario(this, chistesUsuario);

        // Obtener los datos
        Usuario usuario = usuarios.get(0);

        ImageView imagenPerfil = findViewById(R.id.perfil_imagenUsuario);
        TextView perfil_nickname = findViewById(R.id.perfil_nickname);
        perfil_nickname.setText(usuario.getNickname());

        // Cargar la imagen del usuario
        if (usuario.getImagen() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(usuario.getImagen(), 0, usuario.getImagen().length);
            imagenPerfil.setImageBitmap(bitmap);
        }

        // Agrega los chistes al contenedor
        for (int i = 0; i < chistesUsuario.size(); i++) {
            // Inflar el layout para cada chiste
            View itemView = getLayoutInflater().inflate(R.layout.itempubli, null);
            ImageView imagen = itemView.findViewById(R.id.perfil_imagenUsuarioPerfil);
            TextView nombreUsuario = itemView.findViewById(R.id.nombreUsuario);
            TextView post = itemView.findViewById(R.id.chisteView);

            Post chiste = chistesUsuario.get(i);

            // Cargar la imagen del usuario
            if (usuario.getImagen() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(usuario.getImagen(), 0, usuario.getImagen().length);
                imagen.setImageBitmap(bitmap);
            }

            // Establecer el nombre de usuario y el contenido del chiste
            nombreUsuario.setText(usuario.getNickname());
            post.setText(chiste.getContenido());

            // Agregar el item al contenedor
            contenedor.addView(itemView);


        }

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        EditarPerfil ep = new EditarPerfil();

        //Poner el fragment la primera vez
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, ep).addToBackStack(null).commit();

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
        //Cambiar un fragment por otro
        //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, ep).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();

        //Para quitar el fragment
        getSupportFragmentManager().beginTransaction().remove(ep).commit();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottonvavigationview, menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
}

