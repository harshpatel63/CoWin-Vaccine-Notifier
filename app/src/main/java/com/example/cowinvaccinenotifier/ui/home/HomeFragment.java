package com.example.cowinvaccinenotifier.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.adapters.HomeAdapter;
import com.example.cowinvaccinenotifier.databinding.FragmentHomeBinding;
import com.example.cowinvaccinenotifier.network.properties.Sessions;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.progressBar.setVisibility(View.VISIBLE);

        HomeAdapter adapter = new HomeAdapter();
        binding.recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewHome.setAdapter(adapter);

        homeViewModel.getSessionsData().observe(getViewLifecycleOwner(), new Observer<List<Sessions>>() {
            @Override
            public void onChanged(List<Sessions> sessions) {
                binding.progressBar.setVisibility(View.GONE);
                Log.i("adgagasdg", "Why the heck is not working");
                adapter.updateSessions(sessions);
            }
        });

        homeViewModel.getNullDataEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                    Toast.makeText(getContext(), "No data available for the current session", Toast.LENGTH_SHORT).show();
            }
        });

        homeViewModel.getPincodeFromDb().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.pincodeHome.setText(s);
            }
        });

        homeViewModel.getDateFromDevice().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.dateHome.setText(s);
            }
        });

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}