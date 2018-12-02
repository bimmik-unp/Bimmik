package com.unysoft.bimmik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.model.MatkulItem;

import java.util.List;

public class MatkulAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<MatkulItem> listData;
    private Context context;
    public MatkulAdapter(Context context, List<MatkulItem> listData) {
        this.context = context;
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = listData;
    }
    @Override
    public int getCount() {
        int a ;
        if(listData != null && !listData.isEmpty()) {
            a = listData.size();
        }
        else {
            a = 0;
        }
        return a;
    }
    @Override
    public Object getItem(int position) {
        MatkulItem data1 = listData.get(position);
        return data1.getId_matkul();
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder spinnerHolder;
        if(convertView == null){
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_list_matkul, parent, false);
            spinnerHolder.id_matkul = (TextView)convertView.findViewById(R.id.spinner_list_item_id);
            spinnerHolder.nama_matkul = (TextView)convertView.findViewById(R.id.spinner_list_item_nama);
            convertView.setTag(spinnerHolder);
        }else{
            spinnerHolder = (ViewHolder)convertView.getTag();
        }
        spinnerHolder.id_matkul.setText(listData.get(position).getId_matkul());
        spinnerHolder.nama_matkul.setText(listData.get(position).getNama());
        return convertView;
    }
    class ViewHolder{
        TextView id_matkul, nama_matkul;
    }
}