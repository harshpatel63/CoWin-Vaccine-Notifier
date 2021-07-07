package com.example.cowinvaccinenotifier.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.network.properties.Sessions;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

        private final ArrayList<Sessions> dataSet = new ArrayList<Sessions>();


    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private final TextView centerName;
        private final TextView centerAddress;
        private final TextView dose1;
        private final TextView dose2;

        public HomeViewHolder(View view)
        {

            super(view);
            centerName = (TextView) view.findViewById(R.id.centerName);
            centerAddress = view.findViewById(R.id.centerAddress);
            dose1 = view.findViewById(R.id.dose1);
            dose2 = view.findViewById(R.id.dose2);

        }

        public TextView getCenterName() { return centerName; }
        public TextView getCenterAddress() { return centerAddress; }
        public TextView getDose1() { return dose1; }
        public TextView getDose2() { return dose2; }
    }




    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        HomeViewHolder viewHolder = new HomeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Sessions currentSessions = dataSet.get(position);
        holder.centerName.setText(currentSessions.getName());
        holder.centerAddress.setText(currentSessions.getAddress());
        holder.dose1.setText(currentSessions.getAvailableCapacityDose1().toString());
        holder.dose2.setText(currentSessions.getAvailableCapacityDose2().toString());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateSessions(List<Sessions> newList)
    {

        Log.i("nullPointerException", "This is null");

            dataSet.clear();
            dataSet.addAll(newList);
            notifyDataSetChanged();

            Log.i("nullPointerException", "This is null");
    }



}
