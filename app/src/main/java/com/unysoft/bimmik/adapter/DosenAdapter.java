package com.unysoft.bimmik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.model.DosenModel;

import java.util.List;

public class DosenAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<DosenModel> listData;
    private Context context;
    public DosenAdapter(Context context, List<DosenModel> listData) {
        this.context = context;
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = listData;
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return (DosenModel)listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DosenAdapter.ViewHolder spinnerHolder;
        if(convertView == null){
            spinnerHolder = new DosenAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_list, parent, false);
            spinnerHolder.id= (TextView)convertView.findViewById(R.id.spinner_list_item);
//            spinnerHolder.nama = (TextView)convertView.findViewById(R.id.spinner_list_item_nama);
            convertView.setTag(spinnerHolder);
        }else{
            spinnerHolder = (DosenAdapter.ViewHolder)convertView.getTag();
        }
        spinnerHolder.id.setText(listData.get(position).getId_dosen());
//        spinnerHolder.nama.setText(listData.get(position).getNama());
        return convertView;
    }
    class ViewHolder{
        TextView id;
    }
}