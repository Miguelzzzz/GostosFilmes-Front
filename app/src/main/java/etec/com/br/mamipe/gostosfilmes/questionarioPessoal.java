package etec.com.br.mamipe.gostosfilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class questionarioPessoal extends AppCompatActivity {

    Button btnCad, btnExc, btnEdt, btnCon, btnLim;
    EditText edtNome, edtEmail, edtCidade, edtTelefone, edtCineFreq, edtPreIngr;
    ListView lstPessoal;

    QuesPessoal pessoa = new QuesPessoal();
    private List<QuesPessoal> listaQuestionarioPessoal = new ArrayList<>();
    private ArrayAdapter<QuesPessoal> adapterQuestionarioPessoal;
    private static String ip = "192.168.1.31";
    private int codigo;
    public static String caminho = "http://"+ ip +"/API-GostosFilmes/controller/questionarioPessoal.php?acao=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_pessoal);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtCidade = findViewById(R.id.edtCidade);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtCineFreq = findViewById(R.id.edtCineFrequent);
        edtPreIngr = findViewById(R.id.edtPrecoAcess);
        btnCad = findViewById(R.id.btnCadastrar);
        btnCon = findViewById(R.id.btnConsultar);
        btnEdt = findViewById(R.id.btnEditar);
        btnExc = findViewById(R.id.btnExcluir);
        btnLim = findViewById(R.id.btnLimpar);
        lstPessoal = findViewById(R.id.lstDados);

        lstPessoal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pessoa = (QuesPessoal) parent.getItemAtPosition(position);
                edtNome.setText(pessoa.getNomeCliente());
                edtEmail.setText(pessoa.getEmail());
                edtCidade.setText(pessoa.getCidade());
                edtTelefone.setText(pessoa.getTelefone());
                edtCineFreq.setText(pessoa.getCinemaFrequentado());
                edtPreIngr.setText(pessoa.getPrecoIngresso());
                codigo = pessoa.getCodCliente();
                btnExc.setEnabled(true);
                btnEdt.setEnabled(true);
                btnCad.setEnabled(false);
            }
        });

        preencheLista(caminho + "consultar_json");

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa.setNomeCliente(edtNome.getText().toString());
                pessoa.setEmail(edtEmail.getText().toString());
                pessoa.setCidade(edtCidade.getText().toString());
                pessoa.setTelefone(edtTelefone.getText().toString());
                pessoa.setCinemaFrequentado(edtCineFreq.getText().toString());
                pessoa.setPrecoIngresso(edtPreIngr.getText().toString());
                cadastrar(caminho + "cadastrar");
                preencheLista(caminho + "consultar_json");
                limparDados();
            }
        });

//        btnCon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent itConsulta = new Intent(questionarioPessoal.this, TelaPesquisa.class);
//                startActivity(itConsulta);
//            }
//        });

        btnEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa.setNomeCliente(edtNome.getText().toString());
                pessoa.setEmail(edtEmail.getText().toString());
                pessoa.setCidade(edtCidade.getText().toString());
                pessoa.setTelefone(edtTelefone.getText().toString());
                pessoa.setCinemaFrequentado(edtCineFreq.getText().toString());
                pessoa.setPrecoIngresso(edtPreIngr.getText().toString());
                alterar(caminho + "atualizar");
                preencheLista(caminho + "consultar_json");
                limparDados();
            }
        });

        btnExc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir(caminho + "excluir");
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

        btnExc.setEnabled(false);
        btnEdt.setEnabled(false);
    }

    private void limparDados() {
        edtNome.setText(null);
        edtTelefone.setText(null);
        edtEmail.setText(null);
        edtCidade.setText(null);
        edtCineFreq.setText(null);
        edtPreIngr.setText(null);
        btnExc.setEnabled(false);
        btnEdt.setEnabled(false);
        btnCad.setEnabled(true);
    }

    private void excluir(String endereco) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, endereco, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("teste",response);
                if (response.equalsIgnoreCase("ok")) {
                    Toast.makeText(questionarioPessoal.this, "Excluído com Sucesso", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(questionarioPessoal.this, "Erro ao Excluir: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("codigo", String.valueOf(codigo));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(questionarioPessoal.this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
    public void preencheLista(String endereco) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, endereco, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listaQuestionarioPessoal.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        QuesPessoal objPessoa = new QuesPessoal();
                        objPessoa.setCodCliente(obj.getInt("codCliente"));
                        objPessoa.setNomeCliente(obj.getString("nomeCliente"));
                        objPessoa.setEmail(obj.getString("email"));
                        objPessoa.setCidade(obj.getString("cidade"));
                        objPessoa.setTelefone(obj.getString("telefone"));
                        objPessoa.setCinemaFrequentado(obj.getString("cinemaFrequentado"));
                        objPessoa.setPrecoIngresso(obj.getString("precoIngresso"));
                        listaQuestionarioPessoal.add(objPessoa);
                    }
                    adapterQuestionarioPessoal = new ArrayAdapter<>(questionarioPessoal.this, android.R.layout.simple_list_item_1, listaQuestionarioPessoal);
                    lstPessoal.setAdapter(adapterQuestionarioPessoal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(questionarioPessoal.this, "Não foi possível carregar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonArrayRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(questionarioPessoal.this);
        queue.getCache().clear();
        queue.add(jsonArrayRequest);
    }
    public void cadastrar(String endereco) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, endereco, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(questionarioPessoal.this, ":)"+response, Toast.LENGTH_SHORT).show();
                if (response.equalsIgnoreCase("ok")) {
                    Log.e("Teste", response);
                    Toast.makeText(questionarioPessoal.this, "Dados Cadastrados com Sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(questionarioPessoal.this, "Não foi possível Cadastrar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome", pessoa.getNomeCliente());
                params.put("email", pessoa.getEmail());
                params.put("cidade", pessoa.getCidade());
                params.put("telefone", pessoa.getTelefone());
                params.put("cinemafrequentado", pessoa.getCinemaFrequentado());
                params.put("precoingresso", pessoa.getPrecoIngresso());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(questionarioPessoal.this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
    public void alterar(String endereco) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, endereco, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("ok")) {
                    Toast.makeText(questionarioPessoal.this, "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(questionarioPessoal.this, "Erro ao Atualizar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("codigo", String.valueOf(codigo));
                params.put("nome", pessoa.getNomeCliente());
                params.put("email", pessoa.getEmail());
                params.put("cidade", pessoa.getCidade());
                params.put("telefone", pessoa.getTelefone());
                params.put("cinemafrequentado", pessoa.getCinemaFrequentado());
                params.put("precoingresso", pessoa.getPrecoIngresso());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(questionarioPessoal.this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
}
