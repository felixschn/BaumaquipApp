package com.example.rentalApplication.ui.Baumaschine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.BaumaschinenListAdapter;
import com.example.rentalApplication.models.Baumaschine;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaumaschinenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaumaschinenFragment extends Fragment implements BaumaschinenClickListener {

    private ArrayList<Baumaschine> mBaumaschine = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private BaumaschinenViewModel baumaschinenViewModel;
    private Baumaschine archiveBaumaschine;
    private static final String TAG = "BaumaschinenFragment.java";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BaumaschinenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaumaschinenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaumaschinenFragment newInstance(String param1, String param2) {
        BaumaschinenFragment fragment = new BaumaschinenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_baumaschinen, container, false);

        recyclerView = view.findViewById(R.id.baumaschineRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Baumaschine baumaschine = new Baumaschine("test",1,10.00, 25.00,100.00, null,null, null);
        //mBaumaschine.add(baumaschine);
        final BaumaschinenListAdapter baumaschinenListAdapter = new BaumaschinenListAdapter(this, this);
        recyclerView.setAdapter(baumaschinenListAdapter);

        baumaschinenViewModel = new ViewModelProvider(requireActivity()).get(BaumaschinenViewModel.class);
     /*   baumaschinenViewModel.getAllBaumaschinen().observe(getViewLifecycleOwner(), allBaumaschinen -> {

        });*/
        baumaschinenViewModel.getAllBaumaschinen().observe(getViewLifecycleOwner(), new Observer<List<Baumaschine>>() {
            @Override
            public void onChanged(List<Baumaschine> baumaschines) {
                baumaschinenListAdapter.setBaumaschinen(baumaschines);


            }
        });


        return view;
    }

    public void archiveBaumaschine(int id){
        Log.d(TAG,"id Wert: " + id);
        ModifyBaumaschineViewModel modifyBaumaschineViewModel = new ViewModelProvider(requireActivity()).get(ModifyBaumaschineViewModel.class);
        archiveBaumaschine = modifyBaumaschineViewModel.getBaumaschineById(id);
        Log.d(TAG,"ROW_ID in Fragment: " + archiveBaumaschine.getIdBaumaschine());
        archiveBaumaschine.setArchived(true);
        modifyBaumaschineViewModel.update(archiveBaumaschine);

    }

    @Override
    public void onPositionClicked(int position) {

    }
}