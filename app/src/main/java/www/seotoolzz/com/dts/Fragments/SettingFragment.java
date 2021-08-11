package www.seotoolzz.com.dts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tapadoo.alerter.Alerter;

import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Helpers.SharedPref;
import www.seotoolzz.com.dts.R;


public class SettingFragment extends Fragment {


    public static final String ARG_PAGE = "ARG_PAGE";
    public int mPageNo;

    ArrayAdapter<String> adapter;



    public SettingFragment() {
        // Required empty public constructor
    }


    public static SettingFragment newInstance(int pageNo) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView userName = view.findViewById(R.id.username);
        EditText link = view.findViewById(R.id.ipOfServer);

        CheckBox viewOfficeByShortNames = view.findViewById(R.id.viewOfficeByShortNames);

        Spinner officeSpinner = view.findViewById(R.id.officeSpinner);

        String[] office_names = DB.getInstance(getContext()).officeDao().getOfficeNames();
        String[] office_short_names = DB.getInstance(getContext()).officeDao().getOfficeShortNames();


        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                office_names);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        officeSpinner.setAdapter(adapter);

        officeSpinner.setSelection(SharedPref.getSharedPreferenceInt(getContext(), "SELECT_OFFICE_INDEX", 0));



        viewOfficeByShortNames.setOnClickListener(v -> {
            if (viewOfficeByShortNames.isChecked()) {
                adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item,
                        office_short_names);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                officeSpinner.setAdapter(adapter);
            } else {
                adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item,
                        office_names);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                officeSpinner.setAdapter(adapter);
            }
        });


        String username = SharedPref.getSharedPreferenceString(getContext(), "NAME", "@user");


        userName.setText(username);

        link.setText(SharedPref.getSharedPreferenceString(getContext(), "BASE_URL", getString(R.string.base_url)));

        view.findViewById(R.id.btnSaveSetting).setOnClickListener(v -> {
            int officeIndex = (int) officeSpinner.getSelectedItemId();
            SharedPref.setSharedPreferenceString(getContext(), "BASE_URL", link.getText().toString());
            SharedPref.setSharedPreferenceString(getContext(), "SELECTED_OFFICE", office_short_names[officeIndex]);
            SharedPref.setSharedPreferenceInt(getContext(), "SELECT_OFFICE_INDEX", officeIndex);

            Alerter.create(getActivity())
                    .setTitle("Message")
                    .setBackgroundColorRes(R.color.colorPrimary)
                    .setText("Setting Successfully Saved!")
                    .enableSwipeToDismiss()
                    .show();
        });

        super.onViewCreated(view, savedInstanceState);
    }
}