package com.example.recycler_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class myAdapter extends FirebaseRecyclerAdapter<Organizations, myAdapter.myviewholder> {
    final FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
    final String cur_email = user1.getEmail();
    String recType;
    public myAdapter(@NonNull FirebaseRecyclerOptions<Organizations> options, String recType) {
        super(options);
        this.recType = recType;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final Organizations Org) {
        holder.name.setText(Org.getName());
        holder.type.setText(Org.getOrgRecycleType());
        String currec=Org.getOrgRecycleType();
        if(!currec.equals(recType))
            holder.rel_id.setVisibility(View.GONE);
        if (Org.getCovidFlag().equals("Yes")) {
            holder.covidImg.setVisibility(View.VISIBLE);
        }
        if (Org.getDeliveryFlag().equals("Yes")) {
            holder.deliveryImg.setVisibility(View.VISIBLE);
        }
        holder.rel_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrganization(v, Org);
            }
        });
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    static class myviewholder extends RecyclerView.ViewHolder
    {
        int cFlag = 0;
        int dFlag = 0;
        TextView name,type;
        ImageView covidImg, deliveryImg;
        RelativeLayout rel_id;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.orgNameText);
            type=(TextView)itemView.findViewById(R.id.recycleTypeText);
            covidImg=(ImageView)itemView.findViewById(R.id.covidCheck);
            deliveryImg=(ImageView)itemView.findViewById(R.id.deliveryCheck);
            rel_id=(RelativeLayout)itemView.findViewById(R.id.rel_id);
        }
    }

    public void openOrganization(View view, Organizations Org) {
        Intent open = new Intent(view.getContext(), organizationPage.class);
        open.putExtra("Name",Org.orgName);
        open.putExtra("About", Org.orgAbout);
        open.putExtra("Type", Org.orgRecycleType);
        open.putExtra("Contact", Org.orgPhone);
        open.putExtra("Location", Org.orgLocation);
        open.putExtra("isCovid", Org.covidFlag);
        open.putExtra("isDelivery", Org.deliveryFlag);
        view.getContext().startActivity(open);
    }



}
