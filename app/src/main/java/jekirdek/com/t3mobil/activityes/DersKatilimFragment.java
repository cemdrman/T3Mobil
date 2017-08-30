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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private EditText txtAdSorgulama;
    private EditText txtSoyadSorgulama;
    private EditText txtTcSorgulama;
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
        getDersListesi();
        btnYoklamaListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = txtAdSorgulama.getText().toString();
                String soyad = txtSoyadSorgulama.getText().toString();
                String tc = txtTcSorgulama.getText().toString();

                if (!tc.isEmpty() || !ad.isEmpty()) {
                    String url = urlControl(ad,soyad,tc);
                    if (url != null) {
                        findStudent(url);
                    }
                }else{
                    Toast.makeText(getActivity(),"Öğrenci Tc, İsim veya Ders İsmi Eksik!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String urlControl(String name, String surname, String citizenId){

        String ogrenciAramaUrl = null;

        if (citizenId.isEmpty()) {
            if (surname.isEmpty()) {
                ogrenciAramaUrl  = RequestURL.getOgrenciAramaUrl(name);
                System.out.println("isim ile arama çağrıldı");
            }else{
                ogrenciAramaUrl  = RequestURL.getOgrenciAramaUrl(name,surname);
            }
        }else{
            if (citizenId.length() == 11) {
                ogrenciAramaUrl = RequestURL.getOgrenciAramaUrl(name,surname,citizenId);
                System.out.println("tc ile arama çağrıldı");
            }else{
                Toast.makeText(getActivity(),"Eksik TC No!",Toast.LENGTH_SHORT).show();
            }
        }
        return ogrenciAramaUrl;
    }

    private void init(View view){
        cmbDers = (Spinner)view.findViewById(R.id.cmbDers);
        txtAdSorgulama = (EditText)view.findViewById(R.id.txtAdSorgulama);
        txtSoyadSorgulama = (EditText)view.findViewById(R.id.txtSoyadSorgulama);
        txtTcSorgulama = (EditText) view.findViewById(R.id.txtTcSorgulama);
        btnYoklamaListele = (Button)view.findViewById(R.id.btnYoklamaListele);
        lstViewAttendece = (ListView) view.findViewById(R.id.ogrenciYoklamaList);
    }

    private void findStudent(String ogrenciAramaUrl){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ogrenciAramaUrl , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);

                if (response.equals("[]")) {
                    Toast.makeText(getActivity(),"Verilen bilgilere göre öğrenci bulunamadı",Toast.LENGTH_SHORT).show();
                }else {
                    user = jsonParse.userJsonParser(response);
                    System.out.println("user bulundu");
                    if (user == null) {
                        Toast.makeText(getActivity(),"Verilen bilgilere göre öğrenci bulunamadı",Toast.LENGTH_SHORT).show();
                    }else{
                        user.toString();
                        getStudentAttenceList(user.getId(),cmbDers.getSelectedItemPosition());
                    }
                }
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

    /**
     * Parametrelere göre bulduğu Yoklama listesini listview'a yazar
     *
     * @param studentId isminden ogrenciId bulunur ve bu methoda geçilir
     * @param lessonId ders combobaxından seçilen ders adına göre dersId bulunur(+1 eklenir)
     */
    private void getStudentAttenceList(int studentId,int lessonId){
        String ogrenciYoklamaListesiUrl = RequestURL.getOgrenciYoklamaListesiUrl(studentId,lessonId + 1);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ogrenciYoklamaListesiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);

                /**
                 * farklı aramalar için liste boşaltılıyor
                 */
                ArrayAdapter<String> listViewBosalt = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, new String[]{});
                lstViewAttendece.setAdapter(listViewBosalt);

                if (jsonParse.getAttendenceList(response).length > 0) {
                    Attendence[] attendences = jsonParse.getAttendenceList(response);

                    ArrayList<String> yoklamaTarihList = new ArrayList<>();
                    for (int i = 0; i < attendences.length; i++ ) {

                        if (attendences[i].getPresence().equals("0")) {
                            String dt = attendences[i].getLessonDate();
                            int dayOfWeek = 0;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try{
                                Date dateFrom=format.parse(dt);
                                Calendar c = Calendar.getInstance();
                                c.setTime(dateFrom);
                                dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            switch (dayOfWeek){
                                case Calendar.MONDAY :
                                    yoklamaTarihList.add( attendences[i].getLessonDate() +" Pazartesi" + " Gelmedi" );
                                    break;
                                case Calendar.TUESDAY :
                                    yoklamaTarihList.add( attendences[i].getLessonDate() +" Salı" + " Gelmedi" );
                                    break;
                                case Calendar.WEDNESDAY :
                                    yoklamaTarihList.add( attendences[i].getLessonDate() +" Çarşamba" + " Gelmedi");
                                    break;
                                case Calendar.THURSDAY :
                                    yoklamaTarihList.add( attendences[i].getLessonDate() +" Perşembe" + " Gelmedi");
                                    break;
                                case Calendar.FRIDAY :
                                    yoklamaTarihList.add( attendences[i].getLessonDate() +" Cuma" + " Gelmedi");
                                    break;
                                case Calendar.SATURDAY :
                                    yoklamaTarihList.add( attendences[i].getLessonDate() +" Cumartesi" + " Gelmedi");
                                    break;
                                case Calendar.SUNDAY :
                                    yoklamaTarihList.add( attendences[i].getLessonDate() +" Pazar" + " Gelmedi");
                                    break;
                            }

                        }
                    }
                    String[] devamsizlikList = new String[yoklamaTarihList.size()];
                    for (int i = 0; i < yoklamaTarihList.size(); i++) {
                        System.out.println(yoklamaTarihList.get(i));
                        devamsizlikList[i] = yoklamaTarihList.get(i);
                    }

                    ArrayAdapter<String> ogrenciYoklamaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, devamsizlikList);
                    lstViewAttendece.setAdapter(ogrenciYoklamaAdapter);
                }else{
                    Toast.makeText(getActivity(),"Öğrencinin Devamsızlığı Yoktur",Toast.LENGTH_SHORT).show();
                    System.out.println("öğrencinin devamsızlığı yoktur");
                }
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

    private void getDersListesi(){

        String loginRequestUrl = RequestURL.baseUrl.concat(RequestURL.dersListesiUrl);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);

                List<String> lessons = jsonParse.getLessonList(response);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lessons);

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
