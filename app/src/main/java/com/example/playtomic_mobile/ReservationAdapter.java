package com.example.playtomic_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ReservationAdapter extends ArrayAdapter<Reservation> {
    public  ReservationAdapter (Context context , int resource, List<Reservation> resarvationsList){
        super(context,resource,resarvationsList);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent)
    {
        Reservation reservation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservationcell, parent, false);
        }

        TextView clubName = (TextView) convertView.findViewById(R.id.clubName);
        clubName.setText(reservation.getFieldName());
        TextView clubAddress = (TextView) convertView.findViewById(R.id.clubaddress);
        clubAddress.setText(reservation.getFieldAdress());
        TextView date = (TextView) convertView.findViewById(R.id.resDate);
        date.setText(reservation.getDate());
        TextView time = (TextView) convertView.findViewById(R.id.resTime);
        time.setText(reservation.getTime());



        return convertView;
    }
}
