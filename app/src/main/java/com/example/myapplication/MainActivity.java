package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private EditText un ,pw;
private Button btn;
private ProgressBar pb;
private static String URL_LOGIN="http://192.168.43.237/a/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        un=findViewById(R.id.txtun);
        pw=findViewById(R.id.txtpw);
        btn=findViewById(R.id.btn);
        pb=findViewById(R.id.loading);
pb.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String euname =un.getText().toString().trim();
                String epass =pw.getText().toString().trim();
                if (!euname.isEmpty() || !epass.isEmpty()){
               Login(euname,epass);

                } else {
                    un.setText("Please Enter Username");
                    pw.setText("Please Enter Password");
                }
            }
        });
    }

    private void Login(final String name, final String pass) {
        pb.setVisibility(View.VISIBLE);
        btn.setVisibility(View.GONE);
        StringRequest sr = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                   // JSONArray login = jsonObject.getJSONArray("success");
                    String success = jsonObject.getString("success");

                        if (success.equals("1")) {
                            Toast.makeText(MainActivity.this, "Success login : ", Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.GONE);
                            Intent intent =new Intent (getApplicationContext(),MainUI.class);
                            intent.putExtra("Username",name);
                            startActivity(intent);


                            }
                        else {Toast.makeText(MainActivity.this, "Login Failed : ", Toast.LENGTH_LONG).show();
                        btn.setVisibility(View.VISIBLE);
                            pb.setVisibility(View.GONE);}

                } catch (JSONException e) {
                    e.printStackTrace();
                    pb.setVisibility(View.GONE);
                    btn.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"Error in 66 : "  + e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error){
                    pb.setVisibility(View.GONE);
                    btn.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"Error : "  + error.toString(),Toast.LENGTH_LONG).show();
                }
        })
        {
            @Override
            protected Map<String ,String> getParams() throws AuthFailureError {
                Map<String ,String > params =new HashMap<>();
                params.put("Username",name);
                params.put("Password",pass);

                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
