package com.example.adi.news_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.adi.news_app.supporting_dir.CustomVolleyApp;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static final String url="https://adityasoni104.000webhostapp.com/news_api_one.php";
    String JSON_RESPONSE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loading Activity Transition animation
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //Initializing Variables
        toolbar=(Toolbar)findViewById(R.id.main_toolbar);
        //Setting Up the toolbar
        setSupportActionBar(toolbar);

        //making Http Calls and Requests
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());

                  /*  JSON_RESPONSE=response.toString();
                    JSON_RESPONSE.toCharArray();
                    Toast.makeText(getApplicationContext(),JSON_RESPONSE,Toast.LENGTH_LONG).show();*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Something Went Wrong !");
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters=new HashMap<String, String>();
                parameters.put("email","adityasoni5859@gmail.com");
                parameters.put("password","12345");
                return parameters;
            }
        };

        //Adding to Request Queue
        CustomVolleyApp.getInstance().addToRequestQueue(stringRequest);


    }
}
