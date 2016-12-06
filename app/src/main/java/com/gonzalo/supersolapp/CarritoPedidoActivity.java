package com.gonzalo.supersolapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.gonzalo.supersolapp.controllers.PedidoCeldaAdapter;
import com.gonzalo.supersolapp.model.Pedido;

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
import java.util.List;

import static com.gonzalo.supersolapp.controllers.Constant.CONNECTION_TIMEOUT;
import static com.gonzalo.supersolapp.controllers.Constant.READ_TIMEOUT;
import static com.gonzalo.supersolapp.controllers.Constant._URL_PEDIDO;

/**
 * Created by gonzalopro on 12/6/16.
 */

public class CarritoPedidoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private ImageView done, cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_pedido);
        Log.d("Shop", "List pedidos:" + ((SupersolApp)getApplicationContext()).getPedidos());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_toolbar_chevron_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        listView = (ListView) findViewById(R.id.lv_carrito_pedido);
        listView.setAdapter(new PedidoCeldaAdapter(this,((SupersolApp) getApplicationContext()).getPedidos()));
        done = (ImageView) findViewById(R.id.iv_celda_pedido_done);
        cancel = (ImageView) findViewById(R.id.iv_celda_pedido_cancel);
    }

    @Override
    protected void onStart() {
        super.onStart();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ((SupersolApp) getApplicationContext()).getIdUsuario();
                List<Pedido> pedidos = ((SupersolApp) getApplicationContext()).getPedidos();
                new EnviarPedido(id, pedidos).execute();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private class EnviarPedido extends AsyncTask<Void,Void,Void> {

        private String id;
        private List<Pedido> pedidos;
        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder result;

        public EnviarPedido(String id, List<Pedido> pedidos) {
            this.id = id;
            this.pedidos = pedidos;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_PEDIDO);
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
                //httpURLConnection.setFixedLengthStreamingMode(jsonSendPedido.getBytes().length);

                Uri.Builder builder = new Uri.Builder();
                builder.appendQueryParameter("idUsuario", String.valueOf(id));
                //builder.appendQueryParameter("items[]", pedidos.toString());

                Log.d("Enviar Pedido", pedidos.toString());

                for (Pedido sendPedido: pedidos) {

                    builder.appendQueryParameter("idProducto[]", sendPedido.getId());
                    builder.appendQueryParameter("cantidad[]", sendPedido.getCantidad());
                    builder.appendQueryParameter("precio[]", sendPedido.getPrecio());
                }

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

                    result = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                        System.out.println("result: " + result);
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
            try {
                JSONObject group_info = new JSONObject(String.valueOf(result));

                JSONArray codigo_respuesta = group_info.getJSONArray("code");
                Log.d("Response", "ID: " + codigo_respuesta);
                for (int i = 0; i < codigo_respuesta.length() ; i++) {
                    int response = codigo_respuesta.getInt(i);

                    switch (response) {
                        case 0:
                            // NO tiene acceso
                            Toast.makeText(CarritoPedidoActivity.this, "Error al insertar", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            // Iniciar nueva actividad
                            Toast.makeText(CarritoPedidoActivity.this, "Insertado Correctamente", Toast.LENGTH_SHORT).show();
                            break;

                    }

                    Log.d("Response", "value: " + response);
                    //names.add(i, jsonGroup.getString("nombre"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
