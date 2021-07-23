package com.example.cowinvaccinenotifier.ui.settings;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.databinding.FragmentSettingsBinding;
import com.example.cowinvaccinenotifier.databinding.ItemBottomSheetContainerBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.ArrayList;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;
    ArrayList<String> ageList = new ArrayList<>();
    ArrayList<String> feeList = new ArrayList<>();
    ArrayList<String> vaccineList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetParent.getRoot());
        bottomSheetBehavior.setPeekHeight(200,true);
        ItemBottomSheetContainerBinding bs = binding.bottomSheetParent;

        LinearLayout layoutMisc = binding.bottomSheetParent.layoutMisc;

        layoutMisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                else if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

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

        setChecksInCheckbox(bs, settingsViewModel);


        binding.buttonApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pincode = 0;

                String pincodeStr = Objects.requireNonNull(binding.editPincode.getText()).toString();

                try{
                    pincode = Integer.parseInt(pincodeStr);
                } catch (NumberFormatException e)
                {
                    Log.i("SettingsFragment", "exeception caught");
                }

                String nameStr = Objects.requireNonNull(binding.editName.getText()).toString();

                if(bs.checkboxEighteen.isChecked())
                    ageList.add("18");
                if(bs.checkboxFortyFive.isChecked())
                    ageList.add("45");
                if(bs.checkboxFree.isChecked())
                    feeList.add("Free");
                if(bs.checkboxPaid.isChecked())
                    feeList.add("Paid");
                if(bs.checkboxCovishield.isChecked())
                    vaccineList.add(getString(R.string.covishield));
                if(bs.checkboxCovaxin.isChecked())
                    vaccineList.add(getString(R.string.covaxin));
                if(bs.checkboxModerna.isChecked())
                    vaccineList.add(getString(R.string.moderna));
                if(bs.checkboxSputnikV.isChecked())
                    vaccineList.add(getString(R.string.sputnik_v));


                if(settingsViewModel.checkPincode(pincode) && settingsViewModel.checkLists(vaccineList, ageList, feeList))
                {
                    settingsViewModel.changeData(pincode, nameStr, vaccineList, feeList, ageList);
                    NavController navController = Navigation.findNavController(
                            getActivity(), R.id.nav_host_fragment_activity_main
                    );
                    Toast.makeText(getContext(), "Data stored successfully", Toast.LENGTH_SHORT).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            navController.navigate(R.id.action_navigation_settings_to_navigation_home);
                        }
                    }, 500);
                }
                else if(!settingsViewModel.checkPincode(pincode))
                {
                    Toast.makeText(getActivity(), "Invalid Pincode.", Toast.LENGTH_LONG).show();
                    binding.editPincode.setError("Invalid Pincode");
                }
                else
                {
                    Toast.makeText(getActivity(), "Please select proper filter options.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    private void setChecksInCheckbox(ItemBottomSheetContainerBinding bs, SettingsViewModel viewModel) {
        ArrayList<String> vaccineList = viewModel.getVaccineListDb().getValue();
        ArrayList<String> ageList = viewModel.getAgeListDb().getValue();
        ArrayList<String> feeList = viewModel.getFeeListDb().getValue();

        if(vaccineList == null && ageList == null && feeList == null)
        {
            //First time login in the app
            bs.checkboxEighteen.setChecked(true);
            bs.checkboxFortyFive.setChecked(true);
            bs.checkboxFree.setChecked(true);
            bs.checkboxPaid.setChecked(true);
            bs.checkboxCovishield.setChecked(true);
            bs.checkboxCovaxin.setChecked(true);
            bs.checkboxModerna.setChecked(true);
            bs.checkboxSputnikV.setChecked(true);

        }
        else {
            bs.checkboxEighteen.setChecked(ageList.contains("18"));
            bs.checkboxFortyFive.setChecked(ageList.contains("45"));
            bs.checkboxFree.setChecked(feeList.contains("Free"));
            bs.checkboxPaid.setChecked(feeList.contains("Paid"));
            bs.checkboxCovishield.setChecked(vaccineList.contains(getString(R.string.covishield)));
            bs.checkboxCovaxin.setChecked(vaccineList.contains(getString(R.string.covaxin)));
            bs.checkboxModerna.setChecked(vaccineList.contains(getString(R.string.moderna)));
            bs.checkboxSputnikV.setChecked(vaccineList.contains(getString(R.string.sputnik_v)));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}