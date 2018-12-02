package com.unysoft.bimmik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.model.SmtModel;

import java.util.List;

public class SemesterAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<SmtModel> listData;
    private Context context;
    public SemesterAdapter(Context context, List<SmtModel> listData) {
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
        return (SmtModel)listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SemesterAdapter.ViewHolder spinnerHolder;
        if(convertView == null){
            spinnerHolder = new SemesterAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_list, parent, false);
            spinnerHolder.spinnerItemList = (TextView)convertView.findViewById(R.id.spinner_list_item);
            convertView.setTag(spinnerHolder);
        }else{
            spinnerHolder = (SemesterAdapter.ViewHolder)convertView.getTag();
        }
        spinnerHolder.spinnerItemList.setText(listData.get(position).getSmt());
        return convertView;
    }
    class ViewHolder{
        TextView spinnerItemList;
    }
}