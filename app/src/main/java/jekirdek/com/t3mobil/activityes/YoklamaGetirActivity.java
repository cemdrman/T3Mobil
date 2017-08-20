package jekirdek.com.t3mobil.activityes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import jekirdek.com.t3mobil.R;

/**
 * Created by cem on 20.08.2017.
 */
public class YoklamaGetirActivity extends Activity {

    private Spinner cmbDeneyap;
    private Spinner cmbDersler;
    private DatePicker datePicker;
    private Button btnYoklamaListesiGetir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnYoklamaListesiGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(YoklamaGetirActivity.this,MainActivity.class);
                //bilgiler alınacak
                startActivity(ıntent);

            }
        });
    }

    private void init(){
        cmbDeneyap = (Spinner) findViewById(R.id.cmbDeneyap);
        cmbDersler = (Spinner) findViewById(R.id.cmbDers1);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        btnYoklamaListesiGetir = (Button) findViewById(R.id.btnYoklamaListesiGetir);
    }

    /**
     * format must be yyyy-mm-dd
     * @return
     */
    private String selectedDate(){
        int day = datePicker.getDayOfMonth();
        int mounth = datePicker.getMonth();
        int year = datePicker.getYear();
        String date = year + "-" + mounth + "-" + day;
        return date;
    }
    

}
