package com.example.medicaredoctor;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomNotification extends ArrayAdapter<String> {
    String []pname;
    String []paddress;
    String []pcontact;
    String []pdate;
    String []ptime;
     Activity context;

    public CustomNotification(Activity context, String[] Pname, String[] Paddress,String[] Pcontact,String[] Pdate,String[] Ptime ) {
        super(context,R.layout.notification_design,Pname);
        this.context =context;
        this.pname=Pname;
        this.paddress = Paddress;
        this.pcontact = Pcontact;
        this.pdate=Pdate;
        this.ptime=Ptime;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.notification_design,null,true);

        TextView txtname=(TextView)listViewItem.findViewById(R.id.txtuname);
        TextView txtuaddress=(TextView)listViewItem.findViewById(R.id.txtaddress);
        TextView txtucontact=(TextView)listViewItem.findViewById(R.id.txtcontact);
        TextView txtudate=(TextView)listViewItem.findViewById(R.id.txtdate);
        TextView txtutime=(TextView)listViewItem.findViewById(R.id.txttime);


        txtname.setText(pname[position]);
        txtuaddress.setText(paddress[position]);
        txtucontact.setText(pcontact[position]);
        txtudate.setText(pdate[position]);
        txtutime.setText(ptime[position]);


        return listViewItem;
        //  return super.getView(position, convertView, parent);
    }

}
