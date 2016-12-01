package com.gonzalo.supersolapp.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.controllers.PromocionCeldaAdapter;
import com.gonzalo.supersolapp.model.Promocion;

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
import static com.gonzalo.supersolapp.controllers.Constant._URL_PROMOCION;

/**
 * Created by gonzalopro on 10/24/16.
 */
public class PromocionFragment extends android.support.v4.app.Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_promocion,container,false);

        listView = (ListView) root.findViewById(R.id.lv_fragment_promotion);
        new RecuperarPromocion(listView).execute();
        return root;
    }

    private class RecuperarPromocion extends AsyncTask<Void,Void,Void>{

        private ListView listView;

        private List<Promocion> promotions;
        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultPromocion;

        public RecuperarPromocion(ListView listView) {
            this.listView = listView;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_PROMOCION);
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
                    resultPromocion = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        resultPromocion.append(line);
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
            promotions = new ArrayList<>();

            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultPromocion));

                JSONArray jsonArray = group_info.getJSONArray("promocion_info");

                for (int i = 0; i < jsonArray.length() ; i++) {
                    JSONObject jsonGroup = jsonArray.getJSONObject(i);
                    promotions.add(i, new Promocion(jsonGroup.getString("idPromocion"),jsonGroup.getString("fechaInicio"),jsonGroup.getString("fechaFin"),jsonGroup.getString("evento"),jsonGroup.getString("descripcion"),jsonGroup.getString("precioPromocion"),jsonGroup.getString("imagen")));

                    listView.setAdapter(new PromocionCeldaAdapter(getContext(), promotions));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
