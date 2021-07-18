package com.example.cowinvaccinenotifier.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cowinvaccinenotifier.MainActivity;
import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.adapters.HomeAdapter;
import com.example.cowinvaccinenotifier.databinding.FragmentHomeBinding;
import com.example.cowinvaccinenotifier.network.properties.Sessions;
import com.example.cowinvaccinenotifier.service.TrackingService;

import java.util.List;
import java.util.Objects;

import static androidx.core.content.ContextCompat.startForegroundService;

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
                if(aBoolean)
                Toast.makeText(getContext(), "No data available for the current session", Toast.LENGTH_SHORT).show();
            }
        });

        homeViewModel.getPincodeFromDb().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!="")
                    binding.pincodeHome.setText(s);
                else
                {
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.action_navigation_home_to_navigation_settings);
                }
            }
        });

        homeViewModel.getDateFromDevice().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.dateHome.setText(s);
            }
        });

        binding.buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "I clicked");
                Intent service = new Intent(getActivity(), TrackingService.class);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    startForegroundService(getActivity(), service);
                else
                    getActivity().startService(service);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}