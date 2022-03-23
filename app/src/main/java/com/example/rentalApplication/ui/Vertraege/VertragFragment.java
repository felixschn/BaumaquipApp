package com.example.rentalApplication.ui.Vertraege;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.VertragListAdapter;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VertragFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VertragFragment extends Fragment implements VertragClickListener {

    private ArrayList<Vertrag> mVertrag = new ArrayList<>();
    private VertragListAdapter vertragListAdapter;
    private RecyclerView recyclerView;
    private VertragViewModel vertragViewModel;
    private AddStuecklisteneintragViewModel stuecklisteneintragViewModel;
    private ModifyVertragViewModel modifyVertragViewModel;
    private Vertrag archivedVertrag;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VertragFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VertraegeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VertragFragment newInstance(String param1, String param2) {
        VertragFragment fragment = new VertragFragment();
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

        View view = inflater.inflate(R.layout.fragment_vertraege, container, false);

        final VertragListAdapter vertragListAdapter = new VertragListAdapter(this, this);

        recyclerView = view.findViewById(R.id.vertragRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(vertragListAdapter);
        vertragViewModel = new ViewModelProvider(requireActivity()).get(VertragViewModel.class);
        vertragViewModel.getAllVertrag().observe(getViewLifecycleOwner(), new Observer<List<Vertrag>>() {
            @Override
            public void onChanged(List<Vertrag> vertrags) {
                for(int i = 0; i<vertrags.size(); i++){
                    //TODO: test if this is a good way to automatically archieve expired contracts
                    if(vertrags.get(i).getEndeVertrag().isBefore(LocalDate.now())){
                        archiveVertrag(vertrags.get(i).getIdVertrag());
                    }
                }
                vertragListAdapter.setVertrag(vertrags);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void archiveVertrag(int id){
        modifyVertragViewModel = new ViewModelProvider(requireActivity()).get(ModifyVertragViewModel.class);
        archivedVertrag = modifyVertragViewModel.loadVertragById(id);
        archivedVertrag.setArchived(true);

        if(!archivedVertrag.getStuecklisteIds().isEmpty()) {
            List<Integer> deleteStuecklisteneintragList = archivedVertrag.getStuecklisteIds();
            stuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
            for (int i = 0; i < deleteStuecklisteneintragList.size(); i++) {
                stuecklisteneintragViewModel.delete(stuecklisteneintragViewModel.stuecklisteneintragById(deleteStuecklisteneintragList.get(i)));
            }
            //TODO: if contract is archived and therefore Stuecklisteneinträge in database deleted, scrutinize if also the list in the contract object has to be remove or if it necessarry at all because I disabled the function of restoring an archived contract
            archivedVertrag.setStuecklisteIds(new ArrayList<>());
        }
        modifyVertragViewModel.update(archivedVertrag);

    }

    @Override
    public void onPositionClicked(int position) {

    }
}