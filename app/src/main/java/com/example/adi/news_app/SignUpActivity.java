package com.example.adi.news_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.adi.news_app.supporting_dir.CustomVolleyApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String url_two="https://adityasoni104.000webhostapp.com/news_api_two.php";
    private Toolbar toolbar;
    private TextInputEditText email;
    private TextInputLayout email_wrapper;
    private Button send_password;
    private String u_email="";
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //loading Activity Transition animation
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //Initializing Variables
        toolbar=(Toolbar)findViewById(R.id.main_toolbar);
        //Setting Up the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        //Initializing Components
        email_wrapper=(TextInputLayout)findViewById(R.id.email_wrapper);
        send_password=(Button)findViewById(R.id.submit);
        ///Setting up onclick listener
        send_password.setOnClickListener(this);
        ///Initializing progress bar
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
    }
    private void makeCustomHttpCalls(final String url,final String email) {
        pDialog.show();
        //making Http Calls and Requests
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println(response.toString());
                setResponse(response);
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
                parameters.put("email",email);
                return parameters;
            }
        };
        //Adding to Request Queue
        CustomVolleyApp.getInstance().addToRequestQueue(stringRequest);
    }

    private void setResponse(String response) {
        JSONObject jsonObejct=null;
        try {
            jsonObejct=new JSONObject(response);
        } catch (JSONException e) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.view_pallet), "Internet Connection Error,try Again Later", Snackbar.LENGTH_LONG);
            snackbar.show();
            e.printStackTrace();
        }
        try {
            String success=jsonObejct.getString("success");
            if(success.equals("1")){
                Intent i=new Intent(this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else{
                pDialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.view_pallet), "Oops,Enter correct Details and try Again !", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        u_email=email_wrapper.getEditText().getText().toString();
        //Toast.makeText(getApplicationContext(),"Hello "+u_email,Toast.LENGTH_LONG).show();
        makeCustomHttpCalls(url_two,u_email);
    }
}
