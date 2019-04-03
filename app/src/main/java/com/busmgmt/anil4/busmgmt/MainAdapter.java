package com.busmgmt.anil4.busmgmt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<String> mcontact;
    public MainAdapter(ArrayList<String> contact){
        mcontact=contact;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.route_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.roadName.setText(mcontact.get(position));

    }


    @Override
    public int getItemCount() {
        return mcontact.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView roadName;
        public ViewHolder(View itemView){
            super(itemView);
            roadName=itemView.findViewById(R.id.name);
        }
    }
}
