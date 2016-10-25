package com.gonzalo.supersolapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gonzalo.supersolapp.ProductoActivity;
import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.controllers.CategoriaCeldaAdapter;
import com.gonzalo.supersolapp.model.Categoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.gonzalo.supersolapp.controllers.Constant.CONNECTION_TIMEOUT;
import static com.gonzalo.supersolapp.controllers.Constant.READ_TIMEOUT;
import static com.gonzalo.supersolapp.controllers.Constant._URL_CATEGORIA;

/**
 * Created by gonzalopro on 10/24/16.
 */
public class ProductoFragment extends android.support.v4.app.Fragment {

    public static final String TAG = ProductoFragment.class.getSimpleName();
    private ListView listViewCategoria;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_producto,container,false);
        listViewCategoria = (ListView) root.findViewById(R.id.lv_producto_categoria);
        new CategoriaTask().execute();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        listViewCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                Categoria categoria  = (Categoria) o;
                String idCategoria = categoria.getIdCategoria();
                Log.i(TAG, "id: " + idCategoria);
                startActivity(new Intent(getActivity(), ProductoActivity.class).putExtra("id",idCategoria));
            }
        });
    }

    public class CategoriaTask extends AsyncTask<Void, Void, Void> {

        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultCategorias;
        private List<Categoria> categorias;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_CATEGORIA);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(READ_TIMEOUT);
                httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                int response_code = httpURLConnection.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    resultCategorias = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
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

            categorias = new ArrayList<>();
            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultCategorias));
                JSONArray jsonArray = group_info.getJSONArray("group_info");

                for (int i = 0; i < jsonArray.length() ; i++) {
                    JSONObject jsonGroup = jsonArray.getJSONObject(i);
                    categorias.add(i, new Categoria(jsonGroup.getString("idCategoria"),jsonGroup.getString("descripcion"),jsonGroup.getString("Impuesto_idImpuesto")));
                    listViewCategoria.setAdapter(new CategoriaCeldaAdapter(getContext(), categorias));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
