package com.example.racialroast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.racialroast.DB.Consulta;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//SE ESTABLECE COMO SE VA A RELACIONAR EL ACTIVITY CON EL LAYOUT
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//SE RELACIONAN VARIABLES CON ELEMENTOS DEL LAYOUT
        Button btnEnviar = findViewById(R.id.Enviar);
        EditText name = findViewById(R.id.name);
        EditText contrasena = findViewById(R.id.contrasena);
        Consulta consulta = new Consulta();

        //BORRAR LUEGO
        name.setText("nico.princich@email.com");
        contrasena.setText("contraseña123");
       //METODO VINCULADO AL BOTÓN EMVIAR DEL LOGIN
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = name.getText().toString().trim();
                String pass = contrasena.getText().toString().trim();
                //COMPROBAMOS SI LOS CAMPOS IMPRESCINDIBLES ESTAN RELLENADOS
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                    Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                else {
                    //COMPROBAMOS SI LAS CREDENCIALES SON CORRECTAS Y MOSTRAMOS MENSAJE DE ERORR O EXITO
                    Boolean checkuserpass = consulta.validarLogin(LoginActivity.this, user, pass);
                    if (checkuserpass == true) {
                        Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                        int userId = consulta.obtenerIdUsuario(LoginActivity.this, user);
                        if (userId != -1) {
                            guardarIdUsuario(userId);
                        }

                        //NOS LLEVA A LA VENTANA DEL MAIN
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this,  "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Guardar el ID del usuario en SharedPreferences
    private void guardarIdUsuario(int idUsuario) {
        SharedPreferences sharedPreferences = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", idUsuario);
        editor.apply();
    }

    //METODO QUE NOS LLEVA A LA VENTANA DE REGISTRO
    public void registerClick (View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}