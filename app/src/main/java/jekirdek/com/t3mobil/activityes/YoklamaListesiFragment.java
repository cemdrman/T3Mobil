package jekirdek.com.t3mobil.activityes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import jekirdek.com.t3mobil.R;
import jekirdek.com.t3mobil.model.Attendence;
import jekirdek.com.t3mobil.utility.JsonParse;
import jekirdek.com.t3mobil.utility.RequestURL;


/**
 * Created by cem
 */
public class YoklamaListesiFragment extends Fragment {

    private ListView tumOgrenciListView;
    private Button btnKaydet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yoklama_listesi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getTumSinifListe();
    }

    private void getTumSinifListe(){
        String tumOgrenciListesi = RequestURL.getTumOgrenciListesi(595,currentDate());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, tumOgrenciListesi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);
                JsonParse jsonParse = new JsonParse();
                Attendence[] attendences = jsonParse.getAttendenceList(response);
                if (jsonParse.getAttendenceList(response).length > 0) {

                    String[] ogrenciList = new String[attendences.length];
                    for (int i = 0;i < attendences.length; i++ ) {
                        ogrenciList[i] = attendences[i].getStudentNameSurname();
                        System.out.println("öğrenci isim: " + ogrenciList[i].toString());
                    }
                    System.out.println("öğrenci listesi uzunluk " + ogrenciList.length);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, ogrenciList);
                    tumOgrenciListView.setAdapter(adapter);
                }else{
                    Toast.makeText(getContext(),"Öğrenci Liste Yok",Toast.LENGTH_SHORT).show();
                    System.out.println("Öğrenci Listesi Yok");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HATA OLUŞTU");
            }
        });
        System.out.println("request:" + request.getUrl());
        requestQueue.add(request);

    }

    /**
     * System date
     * @return current date format must be yyyy-mm-dd
     */
    private String currentDate(){
        return "2017-07-10";
    }

    private void init(View view){
        tumOgrenciListView = (ListView) view.findViewById(R.id.list);
        btnKaydet = (Button) view.findViewById(R.id.btnYoklamaKaydet);
    }

}
