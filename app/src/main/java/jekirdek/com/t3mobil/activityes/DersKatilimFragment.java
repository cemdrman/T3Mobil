package jekirdek.com.t3mobil.activityes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.List;

import jekirdek.com.t3mobil.R;
import jekirdek.com.t3mobil.model.Attendence;
import jekirdek.com.t3mobil.model.User;
import jekirdek.com.t3mobil.utility.JsonParse;
import jekirdek.com.t3mobil.utility.RequestURL;

/**
 * Created by cem
 */
public class DersKatilimFragment extends Fragment {

    private Spinner cmbDers;
    private EditText txtAdSoyadSorgulama;
    private Button btnYoklamaListele;
    private ListView lstViewAttendece;
    private JsonParse jsonParse = new JsonParse();
    private User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ders_katilim, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        btnYoklamaListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adSoyad = txtAdSoyadSorgulama.getText().toString();
                String secilenDers = cmbDers.getSelectedItem().toString();
                if (adSoyad.equals("") || secilenDers.equals("")) {
                    Toast.makeText(getActivity(),"Öğrenci İsmi veya Ders İsmi Eksik",Toast.LENGTH_SHORT).show();
                }else{
                    String[] isimSoyisim = adSoyad.split(" ");
                    getStudent(isimSoyisim[0],isimSoyisim[1]);

                    //----ÖNEMLİ ---\\
                    /*split metodunda tek boşluğa göre ayırma olduğu için eğer kullanıcı birden fazla boşluk girerse
                    bu boşluk kontrol edilmeli fazla boşluk silinmelidir
                    */

                    if (user == null) {
                        //verilen öğrenci yok ise mesaj
                        //Toast.makeText(getActivity(),"Verilen bilgilere göre öğrenci bulunamadı",Toast.LENGTH_SHORT).show();
                        System.out.println("user null");
                    }else{

                        getOgrenciYoklamaListesi(user.getId(),cmbDers.getSelectedItemPosition());
                    }
                }
            }
        });
    }

    private void getStudent(String name, String surname){
        String ogrenciAramaUrl = RequestURL.getOgrenciAramaUrl(name,surname);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ogrenciAramaUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);

                if (response.equals("[]")) {
                    Toast.makeText(getActivity(),"Verilen bilgilere göre öğrenci bulunamadı",Toast.LENGTH_SHORT).show();
                }else {
                    user = jsonParse.userJsonParser(response);
                    System.out.println("user bulundu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HATA OLUŞTU");
                //öğrenci bulunamadı hatası verilecek
            }
        });
        System.out.println("request:" + stringRequest.getUrl());
        requestQueue.add(stringRequest);


    }

    /**
     * Parametrelere göre bulduğu Yoklama listesini listview'a yazar
     *
     * @param studentId isminden ogrenciId bulunur ve bu methoda geçilir
     * @param lessonId ders combobaxından seçilen ders adına göre dersId bulunur
     */
    private void getOgrenciYoklamaListesi(int studentId,int lessonId){
        String ogrenciYoklamaListesiUrl = RequestURL.getOgrenciYoklamaListesiUrl(studentId,lessonId) ;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ogrenciYoklamaListesiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);

                //dolu ise listView boşaltılmalı!
                if (jsonParse.getAttendenceList(response).length > 0) {
                    Attendence[] attendences = jsonParse.getAttendenceList(response);
                    String[] yoklamaTarihList = new String[attendences.length];
                    for (int i = 0;i < attendences.length; i++ ) {
                        yoklamaTarihList[i] = attendences[i].getLessonDate();
                    }
                    ArrayAdapter ogrenciYoklamaAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,yoklamaTarihList);
                    lstViewAttendece.setAdapter(ogrenciYoklamaAdapter);
                }else{
                    System.out.println("öğrencinin devamsızlığı yoktur");
                }



                //öğrencinin bütün yoklama listesi elimizde
                //yoklama listesi parse edilecek

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

    private void init(View view){
        cmbDers = (Spinner)view.findViewById(R.id.cmbDers);
        txtAdSoyadSorgulama = (EditText)view.findViewById(R.id.txtAdSoyadSorgulama);
        btnYoklamaListele = (Button)view.findViewById(R.id.btnYoklamaListele);
        lstViewAttendece = (ListView) view.findViewById(R.id.ogrenciYoklamaList);
        getDersListesi();
    }

    private void getDersListesi(){

        String loginRequestUrl = RequestURL.baseUrl.concat(RequestURL.dersListesiUrl);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);

                List<String> lessons = jsonParse.getLessonList(response);

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lessons);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                cmbDers.setAdapter(dataAdapter);

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

}
