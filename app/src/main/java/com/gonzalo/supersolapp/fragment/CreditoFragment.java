package com.gonzalo.supersolapp.fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.SupersolApp;
import com.gonzalo.supersolapp.controllers.CreditoCeldaAdapter;
import com.gonzalo.supersolapp.model.Credito;

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
import static com.gonzalo.supersolapp.controllers.Constant._URL_CREDITO;

/**
 * Created by gonzalopro on 10/24/16.
 */
public class CreditoFragment extends android.support.v4.app.Fragment {

    private ListView listView;
    private String idUsuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_credito,container,false);

        listView = (ListView) root.findViewById(R.id.lv_fragment_credit);
        idUsuario = ((SupersolApp) getActivity().getApplicationContext()).getIdUsuario();
        new RecuperarCreditos(listView,idUsuario).execute();
        return root;
    }

    private class RecuperarCreditos extends AsyncTask<Void,Void,Void> {

        private ListView listView;
        private String idUsuario;

        private List<Credito> creditos;
        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultCreditos;

        public RecuperarCreditos(ListView listView, String idUsuario) {
            this.listView = listView;
            this.idUsuario = idUsuario;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_CREDITO);
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

                    resultCreditos = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        resultCreditos.append(line);
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

            creditos = new ArrayList<>();
            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultCreditos));

                JSONArray jsonArray = group_info.getJSONArray("creditos_info");
                for (int i = 0; i < jsonArray.length() ; i++) {

                    JSONObject jsonGroup = jsonArray.getJSONObject(i);
                    creditos.add(i, new Credito(jsonGroup.getString("idVenta"),jsonGroup.getString("fecha"),jsonGroup.getString("total"),jsonGroup.getString("codigoControl")));

                    listView.setAdapter(new CreditoCeldaAdapter(getContext(), creditos));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
