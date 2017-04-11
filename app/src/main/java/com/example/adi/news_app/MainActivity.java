package com.example.adi.news_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import static com.example.adi.news_app.R.id.email;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    private static final String url_one="https://adityasoni104.000webhostapp.com/news_api_one.php";
    private static final String url_two="https://adityasoni104.000webhostapp.com/news_api_three.php";
    private Button login;
    private TextView sign_up,forgot_pass;
    private TextInputEditText user_email,user_password;
    private TextInputLayout email_wrapper,password_wrapper;
    private String u_email="",u_password="NONE";

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
        getSupportActionBar().setTitle(null);
        //Initializing the components
        login=(Button)findViewById(R.id.login_submit);
        sign_up=(TextView)findViewById(R.id.sign_up);
        forgot_pass=(TextView)findViewById(R.id.forgot_pass);
        user_email=(TextInputEditText) findViewById(email);
        user_password=(TextInputEditText) findViewById(R.id.password);
        email_wrapper=(TextInputLayout)findViewById(R.id.email_wrapper);
        password_wrapper=(TextInputLayout)findViewById(R.id.password_wrapper);
        //Implementing Onclick Listeners
        login.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        forgot_pass.setOnClickListener(this);

    }
    private void makeCustomHttpCalls(final String url,final String email,final String password) {
        final String url1=url;
        //making Http Calls and Requests
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
                if(url.equals(url_one)){
                    setLoginResponse(response);
                }
                else if(url.equals(url_two)){
                    setForgotPasswordResponse(response);
                }
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
                parameters.put("password",password);
                return parameters;
            }
        };
        //Adding to Request Queue
        CustomVolleyApp.getInstance().addToRequestQueue(stringRequest);
    }

    private void setForgotPasswordResponse(String res) {
        JSONObject jsonObejct=null;
        try {
            jsonObejct=new JSONObject(res);
        } catch (JSONException e) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.view_pallet), "Internet Connection Error,try Again Later", Snackbar.LENGTH_LONG);
            snackbar.show();
            e.printStackTrace();
        }
        try {
            String success=jsonObejct.getString("success");
            /*if(success.equals("1")){
                Intent i=new Intent(this,HomeActivity.class);
                startActivity(i);
            }
            else if(success.equals("0")){
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.view_pallet), "Entered Email is incorrect", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else{
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.view_pallet), "Internet Connection Error,try Again Later", Snackbar.LENGTH_LONG);
                snackbar.show();
            }*/
            System.out.println(success);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ///Setting Up what happens to the login response
    public void setLoginResponse(String res){
        JSONObject jsonObejct=null;
        try {
            jsonObejct=new JSONObject(res);
        } catch (JSONException e) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.view_pallet), "Internet Connection Error,try Again Later", Snackbar.LENGTH_LONG);
            snackbar.show();
            e.printStackTrace();
        }
        try {
            String success=jsonObejct.getString("success");
            if(success.equals("1")){
                Intent i=new Intent(this,HomeActivity.class);
                startActivity(i);
            }
            else if(success.equals("0")){
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.view_pallet), "Eighter Email or Password is incorrect", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else{
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.view_pallet), "Internet Connection Error,try Again Later", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        int id;
        id=view.getId();
        switch (id){
            case R.id.sign_up:
                Intent i=new Intent(this,SignUpActivity.class);
                startActivity(i);
            case R.id.forgot_pass:
                Snackbar snackbar = Snackbar
                        .make(view, "Want to Restore Password ?", Snackbar.LENGTH_LONG)
                        .setAction("RESTORE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                u_email=email_wrapper.getEditText().getText().toString();
                                u_password=password_wrapper.getEditText().getText().toString();
                                if(u_email.equals("")){
                                    Snackbar snackbar1 = Snackbar.make(view, "Please Enter your Email !!", Snackbar.LENGTH_LONG);
                                    snackbar1.show();
                                }
                                else{
                                    makeCustomHttpCalls(url_two,u_email,u_password);
                                    Snackbar snackbar1 = Snackbar.make(view, "Check Your Email !!", Snackbar.LENGTH_LONG);
                                    snackbar1.show();
                                }
                            }
                        });
                snackbar.show();
            case R.id.login_submit:
                ///Getting Content
                u_email=email_wrapper.getEditText().getText().toString();
                u_password=password_wrapper.getEditText().getText().toString();
                makeCustomHttpCalls(url_one,u_email,u_password);
                //Toast.makeText(getApplicationContext(),"Email: "+u_email+", Password: "+u_password,Toast.LENGTH_LONG).show();
        }
    }
}
