package com.example.cowinvaccinenotifier.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.databinding.FragmentSettingsBinding;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        settingsViewModel.getPincodeDb().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.editPincode.setText(s);
            }
        });

        settingsViewModel.getUsernameDb().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.editName.setText(s);
            }
        });


        binding.buttonApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pincode = 0;

                String pincodeStr = Objects.requireNonNull(binding.editPincode.getText()).toString();

                try{
                    pincode = Integer.parseInt(pincodeStr);
                } catch (NumberFormatException e)
                {
                    Log.i("pincode", "exeception caught");
                }

                String nameStr = Objects.requireNonNull(binding.editName.getText()).toString();
                if(settingsViewModel.checkPincode(pincode))
                {
                    settingsViewModel.changeData(pincode, nameStr);
                    NavController navController = Navigation.findNavController(
                            getActivity(), R.id.nav_host_fragment_activity_main
                    );
                    Toast.makeText(getContext(), "Data stored successfully", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_navigation_settings_to_navigation_home);
                }
                else
                {
                    Toast.makeText(getActivity(), "Invalid Pincode.", Toast.LENGTH_SHORT).show();
                    binding.editPincode.setError("Invalid Pincode");
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