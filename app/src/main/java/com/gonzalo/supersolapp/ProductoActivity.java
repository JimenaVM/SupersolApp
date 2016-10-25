package com.gonzalo.supersolapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.gonzalo.supersolapp.controllers.ProductoCeldaAdapter;
import com.gonzalo.supersolapp.model.Producto;

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
import static com.gonzalo.supersolapp.controllers.Constant._URL_PRODUCTO;

/**
 * Created by gonzalopro on 10/25/16.
 */

public class ProductoActivity extends AppCompatActivity {

    private String getId;
    private ListView listViewProducto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        listViewProducto = (ListView) findViewById(R.id.lv_producto);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getId = getIntent().getStringExtra("id");
        new ProductoTask(getId).execute();

    }

    public class ProductoTask extends AsyncTask<Void, Void, Void> {

        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultCategorias;
        private List<Producto> productos;
        private String idCategoria;

        public ProductoTask(String idCategoria) {
            this.idCategoria = idCategoria;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_PRODUCTO);
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
                        .appendQueryParameter("idCategoria", idCategoria);

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

                    resultCategorias = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        resultCategorias.append(line);

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

            productos = new ArrayList<>();
            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultCategorias));
                JSONArray jsonArray = group_info.getJSONArray("group_info");

                for (int i = 0; i < jsonArray.length() ; i++) {
                    JSONObject jsonGroup = jsonArray.getJSONObject(i);

                    productos.add(i, new Producto(
                            jsonGroup.getString("idProducto"),
                            jsonGroup.getString("codigo"),
                            jsonGroup.getString("nombre"),
                            jsonGroup.getString("descripcion"),
                            jsonGroup.getString("foto"),
                            jsonGroup.getString("estado"),
                            jsonGroup.getString("idUnidadMedida"),
                            jsonGroup.getString("idCategoria")));

                    listViewProducto.setAdapter(new ProductoCeldaAdapter(ProductoActivity.this, productos));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
