package jekirdek.com.t3mobil.activityes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import jekirdek.com.t3mobil.R;
import jekirdek.com.t3mobil.database.BilgilerDB;
import jekirdek.com.t3mobil.model.User;
import jekirdek.com.t3mobil.utility.JsonParse;


public class LoginActivity extends Activity {


    private JsonParse jsonParse = new JsonParse();
    private EditText txtEmail;
    private EditText txtSifre;
    private Button btnGiris;
    //private TokenDB tokenDB;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String sifre = txtSifre.getText().toString();
                if (email.equals("") || sifre.equals("")) {
                    Toast.makeText(getApplicationContext(), "Lütfen Bilgilerizi Tam Giriniz!", Toast.LENGTH_LONG).show();
                }else{
                    //sorun yok demektir login metodu çalışır
                    login();

                }

            }
        });


     /*   if (tokenDB.getRowCount() > 0) {
            System.out.println();
            Intent ıntent = new Intent(LoginActivity.this,MainActivity.class );
            System.out.println("token:   " + tokenDB.getToken());
            System.out.println("staffID: " + tokenDB.getID());
            ıntent.putExtra("token",tokenDB.getToken());
            ıntent.putExtra("staffID",tokenDB.getID());
            startActivity(ıntent);
        }else{
            btnGiris.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = txtEmail.getText().toString();
                    String sifre = txtSifre.getText().toString();
                    if (email.equals("") || sifre.equals("")) {
                        Toast.makeText(getApplicationContext(), "Lütfen Bilgilerizi Tam Giriniz!", Toast.LENGTH_LONG).show();
                    }else{
                        //sorun yok demektir login metodu çalışır
                        login();
                    }
                }
            });
        }*/
    }

    private void login(){
        String loginRequestUrl = "http://www.jekirdek.com/t3mobil/mobileservlet.php?servletMethod=loginControl&email="+txtEmail.getText()+"&password="+txtSifre.getText();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);
                User user = jsonParse.userJsonParser(response);
                BilgilerDB bilgilerDB = new BilgilerDB(getApplicationContext());
                bilgilerDB.saveUser(user);
                Intent ıntent = new Intent(LoginActivity.this,MainActivity.class );
                startActivity(ıntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HATA OLUŞTU");
            }
        });
        System.out.println("request:" + stringRequest.getOriginUrl());
        requestQueue.add(stringRequest);
    }

    private void init(){
        txtEmail = (EditText) findViewById(R.id.email);
        txtSifre = (EditText) findViewById(R.id.password);
        btnGiris = (Button)findViewById(R.id.btnGiris);
        //tokenDB = new TokenDB(getApplicationContext());
    }
}
