package com.gonzalo.supersolapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gonzalo.supersolapp.CarritoPedidoActivity;
import com.gonzalo.supersolapp.DetallePedidoActivity;
import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.SupersolApp;
import com.gonzalo.supersolapp.controllers.PedidoUsuarioCeldaAdapter;
import com.gonzalo.supersolapp.model.PedidoUsuario;

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
import static com.gonzalo.supersolapp.controllers.Constant._URL_PEDIDO_USUARIO;

/**
 * Created by gonzalopro on 10/24/16.
 */
public class PedidoFragment extends android.support.v4.app.Fragment {

    private ListView listView;
    private String idUsuario;
    private List<PedidoUsuario> pedidos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pedido,container,false);

        listView = (ListView) root.findViewById(R.id.lv_pedido);
        idUsuario = ((SupersolApp) getContext().getApplicationContext()).getIdUsuario();

        new ObtenerPedidoUsuario(listView, idUsuario).execute();

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_shop);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CarritoPedidoActivity.class));
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                PedidoUsuario pedido  = (PedidoUsuario) o;
                String idPedido = pedido.getIdPedido();
                Log.i("DTPA", "id: " + idPedido);
                startActivity(new Intent(getActivity(), DetallePedidoActivity.class).putExtra("idPedido",idPedido));
            }
        });

        return root;
    }

    private class ObtenerPedidoUsuario extends AsyncTask<Void,Void,Void> {

        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultPedido;

        private ListView listView;
        private String idUsuario;

        public ObtenerPedidoUsuario(ListView listView, String idUsuario) {
            this.listView = listView;
            this.idUsuario = idUsuario;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_PEDIDO_USUARIO);
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

                    resultPedido = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        resultPedido.append(line);

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
            pedidos = new ArrayList<>();
            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultPedido));

                JSONArray jsonArray = group_info.getJSONArray("pedido_info");
                for (int i = 0; i < jsonArray.length() ; i++) {

                    JSONObject jsonGroup = jsonArray.getJSONObject(i);
                    pedidos.add(i, new PedidoUsuario(jsonGroup.getString("idPedido"),jsonGroup.getString("fechaPedido"),jsonGroup.getString("estado")));

                    listView.setAdapter(new PedidoUsuarioCeldaAdapter(getActivity(), pedidos));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
