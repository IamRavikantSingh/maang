package com.ravikantsingh.maang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ravikantsingh.maang.Adapters.SuggestionAdapter;
import com.ravikantsingh.maang.ModalClass.ModalClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravikant Singh on 27,February,2019
 */
public class MyPost extends Fragment {

    private RecyclerView mRecyclerView;
    private SuggestionAdapter mAdapter;
    private List<ModalClass> modalClassList = new ArrayList<>();
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_post,container,false);
        mRecyclerView = v.findViewById(R.id.suggestionRV);

        mAdapter = new SuggestionAdapter(modalClassList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo add post dialog/fragment
            }
        });

        try {

            DatabaseReference mRefrence = FirebaseDatabase.getInstance().getReference().child("posts");
            mRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    modalClassList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        modalClassList.add(new ModalClass(String.valueOf(ds.child("related-sector").getValue()),
                                String.valueOf(ds.child("related-schemes").getValue()),
                                String.valueOf(ds.child("likes").getValue()),
                                String.valueOf(ds.child("comments").getValue()),
                                String.valueOf(ds.child("imglink").getValue()),
                                String.valueOf(ds.child("pdflink").getValue()),
                                String.valueOf(ds.child("description").getValue()),
                                String.valueOf(ds.child("timestamp").getValue()),
                                String.valueOf(ds.child("uid").getValue())));
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){}

        return  v;
    }

}