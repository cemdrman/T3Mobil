package jekirdek.com.t3mobil.activityes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jekirdek.com.t3mobil.R;
import jekirdek.com.t3mobil.adapter.DatePickerFragment;
import jekirdek.com.t3mobil.model.DeneYap;
import jekirdek.com.t3mobil.utility.JsonParse;
import jekirdek.com.t3mobil.utility.RequestURL;


/**
 * Created by cem
 */
public class YoklamaAyarFragment extends Fragment {

    private Spinner cmbDeneyap;
    private Spinner cmbDersler;
    private DatePicker datePicker;
    private Button btnYoklamaListesiGetir;
    private Button btnOpenCalander;
    private TextView dateOfCalendar;
    private JsonParse jsonParse = new JsonParse();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yoklama_ayar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getDeneyapListesi();
        getDersListesi();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        setDateToTextView(dateFormat.format(date));
        btnYoklamaListesiGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYoklamaListesiGetir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent ıntent = new Intent(getActivity(),YoklamaGetirActivity.class);
                        ıntent.putExtra("deneyap",getSelectedDeneyap());
                        ıntent.putExtra("ders",getSelectedDers());
                        ıntent.putExtra("tarih",getSelectedDate());
                        startActivity(ıntent);
                    }
                });
            }
        });

        btnOpenCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void setDateToTextView(String date){
        dateOfCalendar.setText("");
        dateOfCalendar.setText(date);
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH) + 1);
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getActivity().getFragmentManager(),"Date");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            setDateToTextView(year + "-" + monthOfYear + "-" + dayOfMonth);
            Toast.makeText(getContext(), String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth),
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void init(View view){
        cmbDeneyap = (Spinner) view.findViewById(R.id.cmbDeneyap);
        cmbDersler = (Spinner) view.findViewById(R.id.cmbDers1);
        btnYoklamaListesiGetir = (Button) view.findViewById(R.id.btnYoklamaListesiGetir);
        datePicker= new DatePicker(getContext());
        btnOpenCalander = (Button) view.findViewById(R.id.btnOpenCalendar);
        dateOfCalendar = (TextView) view.findViewById(R.id.dateOfCalendar);
    }

    /**
     * format must be yyyy-mm-dd
     * @return
     */
    private String getSelectedDate(){
        int day = datePicker.getDayOfMonth();
        int mounth = datePicker.getMonth();
        int year = datePicker.getYear();
        String date = year + "-" + mounth + "-" + day;
        return date;
    }

    private String getSelectedDeneyap(){
        String selectedDeneyap = (String) cmbDeneyap.getSelectedItem();
        return selectedDeneyap;
    }

    private String getSelectedDers(){
        String selectedDers = (String) cmbDersler.getSelectedItem();
        return selectedDers;
    }

    private void getDersListesi(){

        String loginRequestUrl = RequestURL.baseUrl.concat(RequestURL.dersListesiUrl);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);

                List<String> lessons = jsonParse.getLessonList(response);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, lessons);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cmbDersler.setAdapter(dataAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HATA OLUŞTU");
            }
        });
        System.out.println("request:" + stringRequest.getUrl());
        requestQueue.add(stringRequest);
    }

    private void getDeneyapListesi(){
        String deneyapUrl = RequestURL.getDeneyapUrl();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request =  new StringRequest(Request.Method.GET, deneyapUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DeneYap[] deneyapListesi = jsonParse.getDeneyapList(response);
                String[] deneyaplar = new String[deneyapListesi.length];
                for (int i=0; i < deneyaplar.length;i++ ) {
                    deneyaplar[i] = deneyapListesi[i].getName();
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, deneyaplar);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cmbDeneyap.setAdapter(dataAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        System.out.println("request:" + request.getUrl());
        requestQueue.add(request);
    }

}
