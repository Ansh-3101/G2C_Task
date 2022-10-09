package com.example.g2c;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.g2c.databinding.ActivityChecklistBinding;
import com.example.g2c.databinding.AddChecklistDialogBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CheckListActivity extends AppCompatActivity {
    ActivityChecklistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChecklistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        final ArrayList<String> list = new ArrayList<>();
        binding.checkListRV.setLayoutManager(new LinearLayoutManager(this));
        final checkListAdapter adapter = new checkListAdapter(this,list);

        binding.checkListRV.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Service List");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren() ){
                    list.add(Objects.requireNonNull(snapshot.getValue()).toString());
                    binding.checkListRV.setAdapter(new checkListAdapter(CheckListActivity.this,list));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(binding.getRoot(),error.getMessage(),1000).show();
            }
        });


        binding.addCheckListFab.setOnClickListener(v->{
            Dialog addDialog = new Dialog(this);
            AddChecklistDialogBinding binding1 = AddChecklistDialogBinding.inflate(getLayoutInflater());
            addDialog.setContentView(binding1.getRoot());
            addDialog.setCanceledOnTouchOutside(true);
            binding1.dialogAddBtn.setOnClickListener(view->{
                if (binding1.addCheckListTitle.getText().toString().isEmpty() ){
                    Snackbar.make(binding.getRoot(),"Item name cannot be empty",1000).show();
                    addDialog.dismiss();

                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Service List")
                            .child(binding1.addCheckListTitle.getText().toString()).setValue(binding1.addCheckListTitle.getText().toString());
                    Snackbar.make(binding.getRoot(),
                            binding1.addCheckListTitle.getText().toString() + " added",1000).show();
                    addDialog.dismiss();
                    Window window = addDialog.getWindow();
                }
            });
            addDialog.show();
            Window window = addDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        });
    }
}
