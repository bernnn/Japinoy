package com.it.rmmg.japinoy;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  Response.Listener<JSONObject>,
        Response.ErrorListener{

    TextView etJap;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TranslationAdapter translationAdapter;
    private CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        etJap = (TextView)findViewById(R.id.etJap);recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        findViewById(R.id.btnTranslate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etJap.getText().toString().isEmpty()){
                    etJap.setError("Please enter japanese word");

                }else{
                    etJap.setError(null);
                    requestContent(etJap.getText().toString());
                }



            }
        });

    }


    private void requestContent(String japword){


        progressBar.setVisibility(View.VISIBLE);
        japword = japword.replaceAll(" ","_");


        String url = "www.yourdomain.com/translate.php?japword="+japword;

        Log.e("url", url);

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);


        AppController.getInstance().addToRequestQueue(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Weak internet connection", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(etJap.getText().toString().isEmpty()){
                            etJap.setError("Please enter japanese word");

                        }else{
                            etJap.setError(null);
                            requestContent(etJap.getText().toString());
                        }

                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();


    }

    @Override
    public void onResponse(JSONObject response) {

        progressBar.setVisibility(View.GONE);

        if(response != null){

            try{

                String success = response.getString("success");

                List<TranslationModel> data = new ArrayList<>();

                if(success.equals("1")){

                    recyclerView.setVisibility(View.VISIBLE);

                    JSONArray resultArray = response.getJSONArray("result");

                    for (int j = 0; j <resultArray.length(); j++) {

                        TranslationModel translationModel = new TranslationModel();
                        translationModel.tag_word     = resultArray.getJSONObject(j).getString("tag_word");
                        translationModel.tag_audio     = resultArray.getJSONObject(j).getString("tag_audio");

                        data.add(translationModel);


                    }

                    translationAdapter = new TranslationAdapter(this, data);
                    recyclerView.setAdapter(translationAdapter);
                    translationAdapter.notifyDataSetChanged();

                }else{

                    Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();


                }

            }catch(JSONException e){
                e.printStackTrace();
            }




        }

    }
}
