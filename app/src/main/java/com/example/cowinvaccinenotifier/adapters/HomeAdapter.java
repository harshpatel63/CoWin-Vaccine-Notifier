package com.example.cowinvaccinenotifier.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.network.properties.Sessions;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

        private final ArrayList<Sessions> dataSet = new ArrayList<Sessions>();


    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private final TextView centerName;
        private final TextView centerAddress;
        private final TextView dose1;
        private final TextView dose2;
        private final ImageView ageTag;
        private final ImageView priceTag;
        private final ImageView vaccineTag;


        public HomeViewHolder(View view)
        {

            super(view);
            centerName = (TextView) view.findViewById(R.id.centerName);
            centerAddress = view.findViewById(R.id.centerAddress);
            dose1 = view.findViewById(R.id.dose1);
            dose2 = view.findViewById(R.id.dose2);
            ageTag = view.findViewById(R.id.age_tag);
            priceTag = view.findViewById(R.id.price_tag);
            vaccineTag = view.findViewById(R.id.vaccine_tag);

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

        switch (currentSessions.getVaccine())
        {
            case "COVISHIELD":
                holder.vaccineTag.setImageResource(R.drawable.covishield_tag);
                break;
            case "COVAXIN":
                holder.vaccineTag.setImageResource(R.drawable.covaxin_tag);
                break;
            case "MODERNA":
                holder.vaccineTag.setImageResource(R.drawable.moderna_tag);
                break;
            case "SPUTNIKV":
                holder.vaccineTag.setImageResource(R.drawable.sputnik_tag);
                break;
        }
        switch(currentSessions.getMinAgeLimit())
        {
            case 18:
                holder.ageTag.setImageResource(R.drawable.eighteen_tag);
                break;
            case 45:
                holder.ageTag.setImageResource(R.drawable.fortyfive_tag);
                break;
        }
        switch (currentSessions.getFeeType())
        {
            case "Paid":
                holder.priceTag.setImageResource(R.drawable.paid_tag);
                break;
            case "Free":
                holder.priceTag.setImageResource(R.drawable.free_tag);
                break;
        }

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
