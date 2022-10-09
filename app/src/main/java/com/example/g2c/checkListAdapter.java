package com.example.g2c;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g2c.databinding.DeleteChecklistDialogBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class checkListAdapter  extends RecyclerView.Adapter<checkListAdapter.viewHolder> {
    Context context;
    private  ArrayList<String> checkList;


    public checkListAdapter( Context context,ArrayList<String> checkList) {
        this.checkList = checkList;
        this.context = context;
    }



    public static class viewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public LinearLayout checkListLL;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkListLL = itemView.findViewById(R.id.checkListLL);
            this.textView = itemView.findViewById(R.id.rv_Item);
        }
    }

    @Override
    public viewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull checkListAdapter.viewHolder holder, int position) {

        holder.textView.setText(checkList.get(position).toString());
        holder.checkListLL.setOnLongClickListener(v -> {
            Dialog dialog = new Dialog(context);
            DeleteChecklistDialogBinding binding = DeleteChecklistDialogBinding.inflate(LayoutInflater.from(context));
            dialog.setContentView(binding.getRoot());
            dialog.setCanceledOnTouchOutside(true);
            binding.dialogDeleteBtn.setOnClickListener(view -> {
                Snackbar.make(holder.itemView,"Deleted " + checkList.get(position).toString()  , 1000).show();
                FirebaseDatabase.getInstance().getReference("Service List")
                        .child(checkList.get(position).toString()).removeValue();
                notifyDataSetChanged();
                dialog.dismiss();
            });
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            return true;
        });
        holder.checkListLL.setOnClickListener(v->{
            Intent intent = new Intent(context,BookingDetailActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return checkList.size();
    }
}
