package com.example.cowinvaccinenotifier.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.adapters.HomeAdapter;
import com.example.cowinvaccinenotifier.databinding.FragmentHomeBinding;
import com.example.cowinvaccinenotifier.network.properties.Sessions;
import com.example.cowinvaccinenotifier.service.TrackingService;
import java.util.List;
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

        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected)
            binding.progressBar.setVisibility(View.VISIBLE);
        else
        {
            new NoInternetDialogFragment().show(
                    getChildFragmentManager(),
                    "NoInternetDialogFragment"
            );
        }

        HomeAdapter adapter = new HomeAdapter();
        binding.recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewHome.setAdapter(adapter);

        homeViewModel.getSessionsData().observe(getViewLifecycleOwner(), new Observer<List<Sessions>>() {
            @Override
            public void onChanged(List<Sessions> sessions) {
                binding.progressBar.setVisibility(View.GONE);
                Log.i("HomeFragment", ""+sessions.size());
                if(sessions.size()>0){
                    binding.textNoSlots.setVisibility(View.GONE);
                    binding.recyclerViewHome.setVisibility(View.VISIBLE);
                    adapter.updateSessions(sessions);
                } else {
                    binding.textNoSlots.setVisibility(View.VISIBLE);
                    binding.recyclerViewHome.setVisibility(View.GONE);
                }
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

        homeViewModel.getTrackingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == null || !aBoolean)
                    binding.buttonService.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                else
                    binding.buttonService.setImageResource(R.drawable.ic_baseline_stop_24);
            }
        });

        binding.buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TrackingService.isTracking.getValue() == null || !TrackingService.isTracking.getValue())
                {
                    Intent service = new Intent(getActivity(), TrackingService.class);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        startForegroundService(getActivity(), service);
                    else
                        getActivity().startService(service);
                }
                else
                {
                        getActivity().stopService(new Intent(getActivity(), TrackingService.class));
                }


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