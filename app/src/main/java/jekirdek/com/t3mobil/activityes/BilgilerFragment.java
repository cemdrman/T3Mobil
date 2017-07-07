package jekirdek.com.t3mobil.activityes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import jekirdek.com.t3mobil.R;
import jekirdek.com.t3mobil.database.BilgilerDB;
import jekirdek.com.t3mobil.model.User;

/**
 * Created by cem
 */
public class BilgilerFragment extends Fragment {

    private EditText txtAdSoyad;
    private EditText txtTelNo;
    private EditText txtEmail;
    private EditText txtSifre;
    private EditText txtTcNo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bilgilerim, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        BilgilerDB bilgilerDB = new BilgilerDB(getContext());
        User user = bilgilerDB.getUser();
        System.out.println("db den dönenen user : " + user.toString());
        txtAdSoyad.setText(user.getName() + " " + user.getSurname());
        txtEmail.setText(user.getEmail());
        txtSifre.setText(user.getPassword());
        txtTelNo.setText(user.getPhoneNumber());
        txtTcNo.setText(user.getCitizenId());
    }

    private void init(View view){
        txtAdSoyad = (EditText)view.findViewById(R.id.txtAdSoyad);
        txtTelNo = (EditText)view.findViewById(R.id.txtTelNo);
        txtEmail = (EditText)view.findViewById(R.id.txtEmail);
        txtSifre = (EditText)view.findViewById(R.id.txtSifre);
        txtTcNo = (EditText)view.findViewById(R.id.txtTcNo);
    }
}
