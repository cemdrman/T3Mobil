package jekirdek.com.t3mobil.actitiyes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import jekirdek.com.t3mobil.R;


public class LoginActivity extends Activity {

    private EditText txtEmail;
    private EditText txtSifre;
    private Button btnGiris;
    //private TokenDB tokenDB;
    private String token;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnGiris = (Button)findViewById(R.id.btnGiris);
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(LoginActivity.this,MainActivity.class );
                startActivity(ıntent);
            }
        });

        init();
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

    }

    private void init(){
        txtEmail = (EditText) findViewById(R.id.email);
        txtSifre = (EditText) findViewById(R.id.password);

        //tokenDB = new TokenDB(getApplicationContext());
    }
}
