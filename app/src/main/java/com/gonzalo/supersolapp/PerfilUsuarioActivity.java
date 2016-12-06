package com.gonzalo.supersolapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gonzalo.supersolapp.controllers.CreditoCeldaAdapter;
import com.gonzalo.supersolapp.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.gonzalo.supersolapp.controllers.Constant.CONNECTION_TIMEOUT;
import static com.gonzalo.supersolapp.controllers.Constant.READ_TIMEOUT;
import static com.gonzalo.supersolapp.controllers.Constant._URL_PERFIL;

/**
 * Created by gonzalopro on 12/1/16.
 */

public class PerfilUsuarioActivity extends AppCompatActivity {

    Toolbar toolbar;
    private String idUsuario;
    private TextView ci, nombre, apellidos, usuario, telefono, estado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_toolbar_chevron_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ci = (TextView) findViewById(R.id.tv_perfil_ci);
        nombre = (TextView) findViewById(R.id.tv_perfil_nombre);
        apellidos = (TextView) findViewById(R.id.tv_perfil_apellidos);
        usuario = (TextView) findViewById(R.id.tv_perfil_usuario);
        telefono = (TextView) findViewById(R.id.tv_perfil_telefono);
        estado = (TextView) findViewById(R.id.tv_perfil_estado);

        idUsuario = ((SupersolApp) getApplicationContext()).getIdUsuario();

        new ObtenerPerfil(idUsuario).execute();

    }

    private class ObtenerPerfil extends AsyncTask<Void,Void,Void> {
        private String idUsuario;

        private List<Usuario> usuarios;
        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultPerfil;

        public ObtenerPerfil(String idUsuario) {
            this.idUsuario = idUsuario;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_PERFIL);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(READ_TIMEOUT);
                httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("idUsuario", idUsuario);

                String request = builder.build().getEncodedQuery();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(request);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int response_code = httpURLConnection.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    resultPerfil = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        resultPerfil.append(line);
                        //System.out.println("Categoria: " +resultCreditos);
                    }


                } else {
                    Log.d("Async", "error async");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            usuarios = new ArrayList<>();

            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultPerfil));

                JSONArray jsonArray = group_info.getJSONArray("usuario_info");

                for (int i = 0; i < jsonArray.length() ; i++) {

                    JSONObject jsonGroup = jsonArray.getJSONObject(i);
                    ci.setText(jsonGroup.getString("cedula"));
                    nombre.setText(jsonGroup.getString("nombre"));
                    apellidos.setText(jsonGroup.getString("apellidoPaterno") + " " + jsonGroup.getString("apellidoMaterno"));
                    usuario.setText(jsonGroup.getString("login"));
                    telefono.setText(jsonGroup.getString("telefono"));
                    //estado.setText(jsonGroup.getString("telefono"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
