package com.ramonem.spotysearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Artist> list = new ArrayList<>();

    ListView lv;

    View v;

    List<Pair<String, String>> params;

    String TAG = "Prueba";

    ArtistAdapter adapter;

    SearchView searchView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = (SearchView) findViewById(R.id.searchView);

        lv = (ListView) findViewById(R.id.listArtist);
        v = findViewById(R.id.view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                Log.d(TAG, "onCreate: " + s);
                params = new ArrayList<Pair<String, String>>() {{
                    add(new Pair<String, String>("type", "artist"));
                    add(new Pair<String, String>("q", s.trim()));
                }};

                //Show ProgressBar
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Searching..");
                progressDialog.setMessage(s.trim() + " search");
                progressDialog.show();

                getData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void getData() {

        //Get Data from Spotify

        Fuel.get("https://api.spotify.com/v1/search", params).responseString(new Handler<String>() {

            @Override
            public void failure(Request request, Response response, FuelError error) {
                Log.d(TAG, "failure: " + error);
                progressDialog.dismiss();
            }

            @Override
            public void success(Request request, Response response, String data) {
                //do something when it is successful
                Log.d(TAG, "success: " + data);
                progressDialog.dismiss();
                /*try {
                    ActivityContenedor.evaluaciones = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */
                parseSearch(data, getApplicationContext());
            }
        });
    }

    private void parseSearch(String object, Context context){
        try{
            list.clear();
            JSONObject data = new JSONObject(object);
            JSONObject artists = new JSONObject(String.valueOf(data.getJSONObject("artists")));
            JSONArray items = artists.getJSONArray("items");
            /*JSONArray items = new JSONArray(artists.getJSONArray("items"));*/
            /*JSONObject items = new JSONObject(String.valueOf(data.getJSONObject("items")));*/
            Log.d(TAG, "parseSearch: " + items);

            if (items.length() > 0){

                for (int i = 0; i < items.length(); i++){

                    JSONObject c = items.getJSONObject(i);
                    JSONObject urlArr = c.getJSONObject("external_urls");
                    JSONObject followersArr = c.getJSONObject("followers");

                    String name = c.getString("name");
                    String type = c.getString("type");
                    String url = urlArr.getString("spotify");
                    int followers = followersArr.getInt("total");

                    list.add(new Artist(name, followers, type, url));


                    Log.d(TAG, "parseSearch: " + name + " " + type + " " + url + " " + followers);
                }

                adapter = new ArtistAdapter(list, context);

                lv.setAdapter(adapter);
            }
            else {
                Snackbar.make(v , "No artist found..", Snackbar.LENGTH_LONG).show();
            }

            if (progressDialog.isShowing())
                progressDialog.dismiss();

        } catch (JSONException e) {
            Snackbar.make(v , "Error al obtener los datos..", Snackbar.LENGTH_LONG).show();
            //exception();
            progressDialog.dismiss();
            e.printStackTrace();
        }


    }
}
