package jekirdek.com.t3mobil.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import jekirdek.com.t3mobil.R;
import jekirdek.com.t3mobil.model.AttendeceListModel;

/**
 * Created by cem on 6.08.2017.
 */
public class CustomAdapter extends ArrayAdapter {

    private ArrayList<AttendeceListModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        CheckBox checkBox;
    }

    public CustomAdapter(ArrayList<AttendeceListModel> data, Context context) {
        super(context, R.layout.ogrenci_bilgi_satir, data);
        this.dataSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public AttendeceListModel getItem(int position) {
        return dataSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ogrenci_bilgi_satir, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        AttendeceListModel item = getItem(position);

        viewHolder.txtName.setText(item.name);
        viewHolder.checkBox.setChecked(item.checked);

        return result;
    }
}

