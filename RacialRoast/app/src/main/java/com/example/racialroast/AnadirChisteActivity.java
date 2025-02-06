package com.example.racialroast;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.racialroast.DB.Consulta;

public class AnadirChisteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.anadirchiste);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.introducirChiste), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText contenido = findViewById(R.id.anadirChiste);
        ImageButton enviar = findViewById(R.id.BotonEnviar);

        Consulta consulta = new Consulta();

        int usuarioId = consulta.getIdUsuarioLogueado(this);
        if (usuarioId != -1) {
            Toast.makeText(this, "Usuario logueado ID: " + usuarioId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show();
        }

        enviar.setOnClickListener(v -> {
            String textoChiste = contenido.getText().toString().trim();
            if (!textoChiste.isEmpty()) {
                consulta.addChiste(this,textoChiste,usuarioId);

                Toast.makeText(AnadirChisteActivity.this, "Chiste añadido correctamente", Toast.LENGTH_SHORT).show();

                contenido.setText("");
            } else {
                Toast.makeText(AnadirChisteActivity.this, "El contenido no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
