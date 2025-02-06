package com.example.racialroast;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.racialroast.DB.Consulta;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//SE ESTABLECE COMO SE VA A RELACIONAR EL ACTIVITY CON EL LAYOUT
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registerView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //SE RELACIONAN VARIABLES CON ELEMENTOS DEL LAYOUT
        EditText newName =findViewById(R.id.NombreUsuario);
        EditText newSurname =findViewById(R.id.Apellidos);
        EditText newNickname =findViewById(R.id.NickName);
        EditText newPassword =findViewById(R.id.Contrasena);
        EditText newEmail =findViewById(R.id.email);
        ImageButton btnRegistrar =findViewById(R.id.BotonRegistro);
        CheckBox termAndConditions = findViewById(R.id.TerminosCondiciones);
        Consulta consulta = new Consulta();


        //METODO VINCULADO AL BOTÓN HITLER(REGISTRAR) DEL REGISTRO
        btnRegistrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String nwName =newName.getText().toString().trim();
                String nwSurName =newSurname.getText().toString().trim();
                String nwNickname =newNickname.getText().toString().trim();
                String nwPassword =newPassword.getText().toString().trim();
                String nwEmail =newEmail.getText().toString().trim();
                Boolean termAndCon =termAndConditions.isChecked();
                //COMPROBAMOS SI ESTAN TODAS LAS CREDENCIALES
                if (TextUtils.isEmpty(nwName) || TextUtils.isEmpty(nwSurName)|| TextUtils.isEmpty(nwNickname)||
                        TextUtils.isEmpty(nwPassword)|| TextUtils.isEmpty(nwEmail)|| !termAndCon)
                    Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                else {
                    //COMPROBAMOS SI EXISTE EL EMAIL Y/O EL NICKNAME Y MOSTRAR MENSAJE EN ESE CASO
                    Boolean checkEmail = consulta.emailExistente(RegisterActivity.this,nwEmail);
                    Boolean checkNickname = consulta.nicknameExistente(RegisterActivity.this,nwNickname);

                    if (checkEmail == true) {
                        Toast.makeText(RegisterActivity.this, "Correo ya existente", Toast.LENGTH_SHORT).show();


                    }else if(checkNickname==true) {
                        Toast.makeText(RegisterActivity.this, "Nickname ya existente, piensa otro", Toast.LENGTH_SHORT).show();
                    }else{
                        //INGRESAMOS LOS DATOS EN LA BASE DE DATOS DEL EMULADOR
                       consulta.addUsuario(RegisterActivity.this,nwName,nwSurName,nwNickname,nwEmail,nwPassword);
                        //if(exito) {
                            Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                       //CAMBIAMOS DE VENTANA A LA DEL LOGIN
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        //}else {
                         //   Toast.makeText(RegisterActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                        //}
                    }
                }
            }

        });


    }

}
