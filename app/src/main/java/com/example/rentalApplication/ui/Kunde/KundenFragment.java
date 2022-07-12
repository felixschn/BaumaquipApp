package com.example.rentalApplication.ui.Kunde;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.KundenListAdapter;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;
import com.example.rentalApplication.ui.Vertraege.VertragViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KundenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KundenFragment extends Fragment implements KundenClickListener {

    private RecyclerView recyclerView;
    private KundenViewModel kundenViewModel;
    private ModifyKundenViewModel modifyKundenViewModel;
    private VertragViewModel vertragViewModel;
    private KundenListAdapter kundenListAdapter;
    private TextView emptyRecyclerView;
    private Kunde archivedkunde;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KundenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KundenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KundenFragment newInstance(String param1, String param2) {
        KundenFragment fragment = new KundenFragment();
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
        View view = inflater.inflate(R.layout.fragment_kunden, container, false);

        emptyRecyclerView = view.findViewById(R.id.emptyKundenRecyclerviewTextView);

        recyclerView = view.findViewById(R.id.kundenRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Kunde testKunde = new Kunde("Berholz GmbH","012345678","berghol","08529", "Pausa", "0123456789", "Straßengeschäft", "Klaus Bergholz");
        //mKunde.add(testKunde);
        kundenListAdapter = new KundenListAdapter(this, this);
        recyclerView.setAdapter(kundenListAdapter);
        kundenViewModel = new ViewModelProvider(requireActivity()).get(KundenViewModel.class);


       /* Kunde testKunde = new Kunde("TestKunde", "1", "2", "3", "4", "5", "6", "7", "8");

        try {
            kundenViewModel.insert(testKunde);
        }catch (SQLiteConstraintException exception){
            Toast.makeText(getContext(), "Kunde schon vorhanden!", Toast.LENGTH_SHORT).show();
        }*/

        kundenViewModel.getAllKunden().observe(getViewLifecycleOwner(), new Observer<List<Kunde>>() {
            @Override
            public void onChanged(List<Kunde> kundes) {
                kundenListAdapter.setKunden(kundes);
                recyclerViewVisibility();

            }
        });

        return view;
    }

    public void archiveKunde(int id) {
        vertragViewModel = new ViewModelProvider(this).get(VertragViewModel.class);
        List<Vertrag> getAllExistingVertrag = vertragViewModel.getAllExistingVertrag();
        for(int i = 0; i < getAllExistingVertrag.size(); i++){
            if(id == getAllExistingVertrag.get(i).getIdKunde()){
                Toast.makeText(getActivity(), R.string.not_removable, Toast.LENGTH_LONG).show();
                return;

            }
        }

        modifyKundenViewModel = new ViewModelProvider(requireActivity()).get(ModifyKundenViewModel.class);
        archivedkunde = modifyKundenViewModel.loadKundeById(id);
        archivedkunde.setArchived(true);
        modifyKundenViewModel.update(archivedkunde);

    }

    public void recyclerViewVisibility() {
        if (kundenListAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyRecyclerView.setVisibility(View.VISIBLE);
        } else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyRecyclerView.setVisibility(View.GONE);

        }

    }

    @Override
    public void onPositionClicked(int position) {

    }
}