package www.seotoolzz.com.dts;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.seotoolzz.com.dts.Helpers.SharedPref;


public class ProfileFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public int mPageNo;



    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(int pageNo) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btnSignOut).setOnClickListener(v -> {
            // End the user session.
            SharedPref.setSharedPreferenceBoolean(getContext(), "IS_LOGGED_IN", false);

            // Redirect to login page.
            Intent loggedInIntent = new Intent(getActivity(), LoginActivity.class);
            loggedInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loggedInIntent);
        });
        super.onViewCreated(view, savedInstanceState);
    }
}