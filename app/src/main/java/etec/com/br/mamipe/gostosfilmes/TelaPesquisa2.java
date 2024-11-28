package etec.com.br.mamipe.gostosfilmes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaPesquisa2 extends AppCompatActivity {

    EditText edtDin;
    ListView lstDin;
    String filmeFavorito;
    private List<QuesGostosFilmes> listaQuestionarioGostos = new ArrayList<>();
    private ArrayAdapter<QuesGostosFilmes> adapterQuestionarioGostosFilmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pesquisa2);

        edtDin = findViewById(R.id.edtDinNome2);
        lstDin = findViewById(R.id.lstDin);
        adapterQuestionarioGostosFilmes = new ArrayAdapter<QuesGostosFilmes>(TelaPesquisa2.this, android.R.layout.simple_list_item_1, listaQuestionarioGostos);
        lstDin.setAdapter(adapterQuestionarioGostosFilmes);
        preencheLista(questionarioGostosFilmes.caminho + "consultar_json");
        edtDin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Toast.makeText(TelaDinamica.this, "Valor Digitado " + s, Toast.LENGTH_SHORT).show();
                filmeFavorito = s.toString();
                buscaNome(questionarioGostosFilmes.caminho + "retorna_nome_din");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void preencheLista(String endereco) {
        listaQuestionarioGostos.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, endereco, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Codigo se a execução deu certo
                        // listaAgendaPessoal.clear();
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

                            adapterQuestionarioGostosFilmes.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //se der algo errado
                Toast.makeText(TelaPesquisa2.this, "Não foi possível carregar" + error, Toast.LENGTH_SHORT).show();
            }
        }

        );
        RequestQueue queue = Volley.newRequestQueue(TelaPesquisa2.this);
        queue.getCache().clear();
        queue.add(jsonArrayRequest);
    }


    private void buscaNome(String endereco) {
        listaQuestionarioGostos.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest strRequest = new StringRequest(Request.Method.POST, endereco, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("teste",response);
                            JSONArray objArray = new JSONArray(response);
                            for (int i = 0; i < objArray.length(); i++) {
                                JSONObject obj = new JSONObject();
                                obj = (JSONObject) objArray.get(i);
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
                            adapterQuestionarioGostosFilmes.notifyDataSetChanged();
                        } catch (JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TelaPesquisa2.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("filmeFavorito", filmeFavorito);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(strRequest);
    }
}
