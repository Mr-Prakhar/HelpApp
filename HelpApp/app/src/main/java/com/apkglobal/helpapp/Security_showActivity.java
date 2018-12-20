package com.apkglobal.helpapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class Security_showActivity extends AppCompatActivity {
    private RecyclerView complaintlist;
    private DatabaseReference compref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_show);

        complaintlist=(RecyclerView)findViewById(R.id.recyclerview);
        compref=FirebaseDatabase.getInstance().getReference().child("Complaints");
        complaintlist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        complaintlist.setLayoutManager(linearLayoutManager);

        Displaycompalints();




    }

    public void Displaycompalints()
    {
        FirebaseRecyclerOptions<complaint> options=
                new FirebaseRecyclerOptions.Builder<complaint>().setQuery(compref,complaint.class).build();
        FirebaseRecyclerAdapter<complaint,Proctorial_ShowActivity.Complaintsviewholder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<complaint, Proctorial_ShowActivity.Complaintsviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Proctorial_ShowActivity.Complaintsviewholder holder, int position, @NonNull complaint model)
            {
                holder.setName(model.getName());
                holder.setPlace(model.getPlace());
                holder.setMobile(model.getMobile());
                holder.setMessege(model.getMessege());
                holder.setDate(model.getDate());
                holder.setImageurl(getApplicationContext(),model.getImageurl());

            }

            @NonNull
            @Override
            public Proctorial_ShowActivity.Complaintsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_items,parent,false);
                return new Proctorial_ShowActivity.Complaintsviewholder(view);
            }
        };
        complaintlist.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class Complaintsviewholder extends RecyclerView.ViewHolder
    {

        TextView username,userplace,usermobile,usermessege,userdate;
        ImageView userimage;
        public Complaintsviewholder(View itemView) {
            super(itemView);






        }
        public void setDate(String date)
        {
            userdate=(TextView)itemView.findViewById(R.id.date1);
            userdate.setText(date);

        }
        public void setMessege(String messege)
        {
            usermessege=(TextView)itemView.findViewById(R.id.message1);
            usermessege.setText(messege);
        }
        public void setMobile(String mobile)
        {
            usermobile=(TextView)itemView.findViewById(R.id.mobile1);
            usermobile.setText(mobile);
        }
        public void setPlace(String place)
        {
            userplace=(TextView)itemView.findViewById(R.id.place1);
            userplace.setText(place);
        }
        public void setName(String name)
        {
            username=(TextView)itemView.findViewById(R.id.name1);
            username.setText(name);

        }
        public void setImageurl(Context context, String imageurl)
        {
            userimage=(ImageView)itemView.findViewById(R.id.image);
            //context = itemView.getContext();

            Picasso.with(context).load(imageurl).into(userimage);
            // Glide.with(context).load(imageurl).into(userimage);


        }

    }


}
