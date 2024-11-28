package etec.com.br.mamipe.gostosfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class questionarioGostosFilmes extends AppCompatActivity {
    Button btnCad, btnEdt, btnCon, btnLim;
    EditText edfilmeFavorito, edgeneroFavorito, edfilmeOdiado, edgeneroOdiado, edatorFavorito, edfilmeSequencia, edcodCliente;
    ListView lstGostosFilmes;

    QuesGostosFilmes pessoa = new QuesGostosFilmes();
    private List<QuesGostosFilmes> listaQuestionarioGostos = new ArrayList<>();
    private ArrayAdapter<QuesGostosFilmes> adapterQuestionarioGostosFilmes;
    private static String ip = "192.168.1.28";
    private int codigo;

    public static String caminho = "http://"+ ip +"/API-GostosFilmes/controller/questionarioGostos.php?acao=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questionario_gostos_filmes);

        edfilmeFavorito = findViewById(R.id.edtfilmeFavorito);
        edgeneroFavorito = findViewById(R.id.edtgeneroFavorito);
        edfilmeOdiado = findViewById(R.id.edtfilmeOdiado);
        edgeneroOdiado = findViewById(R.id.edtgeneroOdiado);
        edatorFavorito = findViewById(R.id.edtatorFavorito);
        edfilmeSequencia = findViewById(R.id.edtfilmeSequencia);
        edcodCliente = findViewById(R.id.edtcodCliente);
        btnCad = findViewById(R.id.btnCadastrar);
        btnCon = findViewById(R.id.btnConsultar);
        btnEdt = findViewById(R.id.btnEditar);
        btnLim = findViewById(R.id.btnLimpar);
        lstGostosFilmes = findViewById(R.id.lstDados);

        lstGostosFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pessoa = (QuesGostosFilmes) parent.getItemAtPosition(position);
                edfilmeFavorito.setText(pessoa.getFilmeFavorito());
                edgeneroFavorito.setText(pessoa.getGeneroFavorito());
                edfilmeOdiado.setText(pessoa.getFilmeOdiado());
                edgeneroOdiado.setText(pessoa.getGeneroOdiado());
                edatorFavorito.setText(pessoa.getAtorFavorito());
                edfilmeSequencia.setText(pessoa.getFilmeSequencia());
                edcodCliente.setText(pessoa.getCodCliente());
                codigo = pessoa.getcodGostos();
                btnEdt.setEnabled(true);
                btnCad.setEnabled(false);
            }
        });

        preencheLista(caminho + "consultar_json");

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa.setFilmeFavorito(edfilmeFavorito.getText().toString());
                pessoa.setGeneroFavorito(edgeneroFavorito.getText().toString());
                pessoa.setFilmeOdiado(edfilmeOdiado.getText().toString());
                pessoa.setGeneroOdiado(edgeneroOdiado.getText().toString());
                pessoa.setAtorFavorito(edatorFavorito.getText().toString());
                pessoa.setFilmeSequencia(edfilmeSequencia.getText().toString());
                pessoa.setCodCliente(edcodCliente.getText().toString());
                cadastrar(caminho + "cadastrar");
                preencheLista(caminho + "consultar_json");
                limparDados();
            }
        });

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itConsulta = new Intent(questionarioGostosFilmes.this, TelaPesquisa2.class);
                startActivity(itConsulta);
            }
        });

        btnEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa.setFilmeFavorito(edfilmeFavorito.getText().toString());
                pessoa.setGeneroFavorito(edgeneroFavorito.getText().toString());
                pessoa.setFilmeOdiado(edfilmeOdiado.getText().toString());
                pessoa.setGeneroOdiado(edgeneroOdiado.getText().toString());
                pessoa.setAtorFavorito(edatorFavorito.getText().toString());
                pessoa.setFilmeSequencia(edfilmeSequencia.getText().toString());
                pessoa.setCodCliente(edcodCliente.getText().toString());

                alterar(caminho + "atualizar");
                preencheLista(caminho + "consultar_json");
                limparDados();
            }
        });

        btnLim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparDados();
            }
        });

        btnEdt.setEnabled(false);
    }

    private void limparDados() {
        edfilmeFavorito.setText(null);
        edgeneroFavorito.setText(null);
        edfilmeOdiado.setText(null);
        edgeneroOdiado.setText(null);
        edatorFavorito.setText(null);
        edfilmeSequencia.setText(null);
        edcodCliente.setText(null);
        btnEdt.setEnabled(false);
        btnCad.setEnabled(true);
    }
    public void preencheLista(String endereco) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, endereco, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listaQuestionarioGostos.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        QuesGostosFilmes objPessoa = new QuesGostosFilmes();
                        objPessoa.setCodCliente(obj.getString("codCliente"));
                        objPessoa.setFilmeFavorito(obj.getString("filmeFavorito"));
                        objPessoa.setGeneroFavorito(obj.getString("generoFavorito"));
                        objPessoa.setFilmeOdiado(obj.getString("filmeOdiado"));
                        objPessoa.setGeneroOdiado(obj.getString("generoOdiado"));
                        objPessoa.setAtorFavorito(obj.getString("atorFavorito"));
                        objPessoa.setFilmeSequencia(obj.getString("filmeSequencia"));
                        objPessoa.setcodGostos(obj.getString("codGostos"));
                        listaQuestionarioGostos.add(objPessoa);
                    }
                    adapterQuestionarioGostosFilmes = new ArrayAdapter<>(questionarioGostosFilmes.this, android.R.layout.simple_list_item_1, listaQuestionarioGostos);
                    lstGostosFilmes.setAdapter(adapterQuestionarioGostosFilmes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(questionarioGostosFilmes.this, "Não foi possível carregar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonArrayRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(questionarioGostosFilmes.this);
        queue.getCache().clear();
        queue.add(jsonArrayRequest);
    }
    public void cadastrar(String endereco) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, endereco, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(questionarioGostosFilmes.this, ":)"+response, Toast.LENGTH_SHORT).show();
                Log.e("Teste", response);
                if (response.equalsIgnoreCase("ok")) {

                    Toast.makeText(questionarioGostosFilmes.this, "Dados Cadastrados com Sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(questionarioGostosFilmes.this, "Não foi possível Cadastrar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("filmefavorito", pessoa.getFilmeFavorito());
                params.put("generofavorito", pessoa.getGeneroFavorito());
                params.put("filmeodiado", pessoa.getFilmeOdiado());
                params.put("generoodiado", pessoa.getGeneroOdiado());
                params.put("atorfavorito", pessoa.getAtorFavorito());
                params.put("filmesequencia", pessoa.getFilmeSequencia());
                params.put("codCliente", pessoa.getCodCliente());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(questionarioGostosFilmes.this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
    public void alterar(String endereco) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, endereco, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("ok")) {
                    Toast.makeText(questionarioGostosFilmes.this, "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(questionarioGostosFilmes.this, "Erro ao Atualizar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("codigo", String.valueOf(pessoa.getcodGostos()));
                params.put("filmefavorito", pessoa.getFilmeFavorito());
                params.put("generofavorito", pessoa.getGeneroFavorito());
                params.put("filmeodiado", pessoa.getFilmeOdiado());
                params.put("generoodiado", pessoa.getGeneroOdiado());
                params.put("atorfavorito", pessoa.getAtorFavorito());
                params.put("filmesequencia", pessoa.getFilmeSequencia());
                params.put("codCliente", pessoa.getCodCliente());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(questionarioGostosFilmes.this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}