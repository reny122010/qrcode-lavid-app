package br.com.stchost.lavid.lavid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dados extends AppCompatActivity {
    private TextView id;
    private TextView nome;
    private TextView matricula;
    private TextView curso;
    private TextView instituicao;

    public void PreencheDados(String ResultQR) {
        try {
            JSONObject DadosQR = new JSONObject(ResultQR);
            id.setText("ID :" + DadosQR.getString("_id"));
            nome.setText("NOME: " + DadosQR.getString("name"));
            matricula.setText("MATRÍCULA: " + DadosQR.getString("matricula"));
            curso.setText("CURSO: " + DadosQR.getString("curso"));
            instituicao.setText("INSTITUIÇÃO: " + DadosQR.getString("instituicao"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);

        id = (TextView) findViewById(R.id.txtid);
        nome = (TextView) findViewById(R.id.txtnome);
        matricula = (TextView) findViewById(R.id.txtmatricula);
        curso = (TextView) findViewById(R.id.txtcurso);
        instituicao = (TextView) findViewById(R.id.txtinstituicao);


        String ResultQR;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                ResultQR = "";
            } else {
                ResultQR = extras.getString("QR");
            }
        } else {
            ResultQR = (String) savedInstanceState.getSerializable("QR");
        }


        if (isJSONValid(ResultQR)) {
            PreencheDados(ResultQR);
        } else {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "http://lavid.stchost.com.br/id?id=" +ResultQR.replace("\"", "");

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            PreencheDados(response.toString());
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), url+"Falha ao buscar o aluno.", Toast.LENGTH_SHORT).show();
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
    }


    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}
