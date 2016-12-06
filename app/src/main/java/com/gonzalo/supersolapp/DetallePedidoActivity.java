package com.gonzalo.supersolapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.gonzalo.supersolapp.controllers.PedidoCeldaAdapter;
import com.gonzalo.supersolapp.controllers.PedidoDetalleCeldaAdapter;
import com.gonzalo.supersolapp.model.DetallePedido;

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
import static com.gonzalo.supersolapp.controllers.Constant._URL_PEDIDO_DETALLE;

/**
 * Created by gonzalopro on 12/6/16.
 */
public class DetallePedidoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String idPedido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_toolbar_chevron_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        idPedido = getIntent().getStringExtra("idPedido");
        listView = (ListView) findViewById(R.id.lv_detalle_pedido);

        new ObtenerDetallePedido(listView,idPedido).execute();

    }

    private class ObtenerDetallePedido extends AsyncTask<Void,Void,Void> {

        private ListView listView;
        private String idPedido;

        private List<DetallePedido> detallePedidos;
        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultDetallePedido;

        public ObtenerDetallePedido(ListView listView, String idPedido) {
            this.listView = listView;
            this.idPedido = idPedido;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_PEDIDO_DETALLE);
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
                        .appendQueryParameter("idPedido", idPedido);

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

                    resultDetallePedido = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        resultDetallePedido.append(line);

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
            detallePedidos = new ArrayList<>();
            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultDetallePedido));

                JSONArray jsonArray = group_info.getJSONArray("group_pedido");
                for (int i = 0; i < jsonArray.length() ; i++) {

                    JSONObject jsonGroup = jsonArray.getJSONObject(i);
                    detallePedidos.add(i, new DetallePedido(jsonGroup.getString("foto"),jsonGroup.getString("nombre"),jsonGroup.getString("cantidad"),jsonGroup.getString("preciobase")));

                    listView.setAdapter(new PedidoDetalleCeldaAdapter(DetallePedidoActivity.this, detallePedidos));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
