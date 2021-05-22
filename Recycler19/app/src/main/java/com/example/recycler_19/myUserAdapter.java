package com.example.recycler_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;


import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myUserAdapter extends FirebaseRecyclerAdapter<Deets, myUserAdapter.myviewholder> {
    final FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
    final String cur_email = user1.getEmail();

    public myUserAdapter(@NonNull FirebaseRecyclerOptions<Deets> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final Deets user) {
        holder.name.setText(user.getUserName());
        holder.phone.setText(user.getPhone());
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userrow,parent,false);
        return new myviewholder(view);
    }

    static class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name, phone;
        RelativeLayout rel_id;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.userNameText);
            phone=(TextView) itemView.findViewById(R.id.userPhoneText);
            rel_id=(RelativeLayout)itemView.findViewById(R.id.user_rel_id);
        }
    }
}