package www.seotoolzz.com.dts.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import www.seotoolzz.com.dts.Adapters.DocumentAdapter;
import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Database.Models.Document;
import www.seotoolzz.com.dts.R;


public class HomeFragment extends Fragment  implements DocumentAdapter.ItemClickListener  {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    DocumentAdapter adapter;


    public HomeFragment() {
    }

    public static HomeFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rvDocument = view.findViewById(R.id.rvDocument);
        rvDocument.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DocumentAdapter(getActivity(), DB.getInstance(getContext()).documentDao().get());
        adapter.setClickListener(this);
        rvDocument.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onItemClick(View view, int position) {
        Document document = adapter.getItem(position);
        Toast.makeText(getContext(), String.valueOf(document.getReference_no()), Toast.LENGTH_SHORT).show();
    }
}
