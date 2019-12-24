package com.example.lenovo.androidmyadmin.ManageUsers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.androidmyadmin.R;
import com.example.lenovo.androidmyadmin.model.User;

import java.util.ArrayList;

public class RecordListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<User> recordList;

    public RecordListAdapter(Context context, int layout, ArrayList<User>recordList){
        this.context=context;
        this.layout=layout;
        this.recordList=recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{

        TextView txtEmail,txtPass,txtType,txtId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder holder=new ViewHolder();
        if(row==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.txtId= (TextView)row.findViewById(R.id.txtId);
            holder.txtEmail= (TextView)row.findViewById(R.id.txtEmail);
            holder.txtPass= (TextView)row.findViewById(R.id.txtPass);

            holder.txtType= (TextView)row.findViewById(R.id.txtType);
            row.setTag(holder);

        }
        else
        {
            holder = (ViewHolder)(row.getTag());
        }
        User model = recordList.get(position);
        holder.txtEmail.setText(model.getEmail());
        holder.txtPass.setText(model.getPassword());
        holder.txtType.setText(model.getType());
        holder.txtId.setText((( String.valueOf(model.getId()))));
        //row.setTag(holder.txtId);
        return row;
    }

}
