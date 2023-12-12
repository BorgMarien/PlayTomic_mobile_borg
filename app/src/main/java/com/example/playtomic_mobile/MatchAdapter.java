package com.example.playtomic_mobile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.protobuf.Any;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchAdapter extends ArrayAdapter<Match> {
    public MatchAdapter(Context context , int resource, List<Match> MatchList){
        super(context,resource,MatchList);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent)
    {
        Match match = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.matchcell, parent, false);
        }
        TextView matchname = (TextView) convertView.findViewById(R.id.matchname);
        matchname.setText(match.getName());

        TextView clubName = (TextView) convertView.findViewById(R.id.matchclubname);
        clubName.setText(match.getFieldName());

        TextView date = (TextView) convertView.findViewById(R.id.matchDateTime);
        date.setText(match.getDate() + "-" + match.getTime());

        TextView comp = (TextView) convertView.findViewById(R.id.iscompetitive);
        if(match.isFriendly()){
            comp.setText("");
        }else{
            comp.setText("Competitive");
        }


        TextView player1 = (TextView) convertView.findViewById(R.id.player1name);
        player1.setText(match.getCreatorName());
        TextView player2 = (TextView) convertView.findViewById(R.id.player2name);
        player2.setText(match.getPlayer2());
        TextView player3 = (TextView) convertView.findViewById(R.id.player3name);
        player3.setText(match.getPlayer3());
        TextView player4 = (TextView) convertView.findViewById(R.id.player4name);
        player4.setText(match.getPlayer4());

        ImageView player1img = (ImageView) convertView.findViewById(R.id.player1img);
        Picasso.get().load(match.getCreatorimg()).into(player1img);

        if(match.getPlayer2img() != ""){
            ImageView player2img = (ImageView) convertView.findViewById(R.id.player2img);
            Picasso.get().load(match.getPlayer2img()).into(player2img);
        }

        if(match.getPlayer3img() != ""){
            ImageView player3img = (ImageView) convertView.findViewById(R.id.player3img);
            Picasso.get().load(match.getPlayer3img()).into(player3img);
        }

        if(match.getPlayer4img() != ""){
            ImageView player4img = (ImageView) convertView.findViewById(R.id.player4img);
            Picasso.get().load(match.getPlayer4img()).into(player4img);
        }


        return convertView;
    }


}
