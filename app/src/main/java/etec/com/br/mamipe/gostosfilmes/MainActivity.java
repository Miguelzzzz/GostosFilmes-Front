package etec.com.br.mamipe.gostosfilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btIniciar, btIniciarDois;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btIniciar = findViewById(R.id.btnIniciar);
        btIniciarDois = findViewById(R.id.btnIniciarDois);

        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iniciar = new Intent(MainActivity.this,questionarioPessoal.class);
                startActivity(iniciar);            }
        });

        btIniciarDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iniciar = new Intent(MainActivity.this,questionarioGostosFilmes.class);
                startActivity(iniciar);            }
        });


    }
}