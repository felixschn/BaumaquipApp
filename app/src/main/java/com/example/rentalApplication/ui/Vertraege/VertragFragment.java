package com.example.rentalApplication.ui.Vertraege;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.MainPageActivity;
import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.VertragListAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Baumaschine.AddBaumaschinenActivity;
import com.example.rentalApplication.ui.Baumaschine.ModifyBaumaschineViewModel;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VertragFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VertragFragment extends Fragment implements VertragClickListener {

    private final ArrayList<Vertrag> mVertrag = new ArrayList<>();
    private VertragListAdapter vertragListAdapter;
    private RecyclerView recyclerView;
    private VertragViewModel vertragViewModel;
    private TextView emptyRecyclerView;
    private AddStuecklisteneintragViewModel addStuecklisteneintragViewModel;
    private ModifyVertragViewModel modifyVertragViewModel;
    private ModifyBaumaschineViewModel modifyBaumaschineViewModel;
    private Vertrag archivedVertrag;
    private static final String TAG = "VertragFragment.java";
    private Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Required empty public constructor
    public VertragFragment() {
    }


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
        context = this.getContext();
        vertragListAdapter = new VertragListAdapter(this, this);

        emptyRecyclerView = view.findViewById(R.id.emptyVertraegeRecyclerviewTextView);

        recyclerView = view.findViewById(R.id.vertragRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(vertragListAdapter);
        modifyBaumaschineViewModel = new ViewModelProvider(requireActivity()).get(ModifyBaumaschineViewModel.class);
        vertragViewModel = new ViewModelProvider(requireActivity()).get(VertragViewModel.class);
        vertragViewModel.getAllVertrag().observe(getViewLifecycleOwner(), new Observer<List<Vertrag>>() {
            @Override
            public void onChanged(List<Vertrag> vertrags) {
                /* functionality to auto archive a contract after date is expired
                for(int i = 0; i<vertrags.size(); i++){
                    if(vertrags.get(i).getEndeVertrag().isBefore(LocalDate.now())){
                        archiveVertrag(vertrags.get(i).getIdVertrag());
                    }
                 }*/
                vertragListAdapter.setVertrag(vertrags);
                recyclerViewVisibility();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void archiveVertrag(int id) {
        modifyVertragViewModel = new ViewModelProvider(requireActivity()).get(ModifyVertragViewModel.class);
        archivedVertrag = modifyVertragViewModel.loadVertragById(id);
        archivedVertrag.setArchived(true);


        if (!archivedVertrag.getStuecklisteIds().isEmpty()) {
            List<Integer> deleteStuecklisteneintragList = archivedVertrag.getStuecklisteIds();
            addStuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
            for (int i = 0; i < deleteStuecklisteneintragList.size(); i++) {
                Stuecklisteneintrag current = addStuecklisteneintragViewModel.stuecklisteneintragById(deleteStuecklisteneintragList.get(i));
                //stuecklisteneintragViewModel.delete(stuecklisteneintragViewModel.stuecklisteneintragById(deleteStuecklisteneintragList.get(i)));
                current.setArchived(true);

                Log.d(TAG, "Modify Button clicked!");
                Baumaschine modifyBaumaschine = modifyBaumaschineViewModel.getBaumaschineById(current.getIdBaumaschine());
                Log.d("BaumaschinenListAdapter.java", "RowID Baumaschine: " + modifyBaumaschine.getIdBaumaschine());
                if(modifyBaumaschine.getAmount() == 1) {
                    Intent modifyBaumaschineIntent = new Intent(context, AddBaumaschinenActivity.class);
                    modifyBaumaschineIntent.putExtra("baumaschineneRowId", modifyBaumaschine.getIdBaumaschine());
                    modifyBaumaschineIntent.putExtra("Class", "VertragFragment");
                    context.startActivity(modifyBaumaschineIntent);
                }
                addStuecklisteneintragViewModel.update(current);
            }
            archivedVertrag.setStuecklisteIds(new ArrayList<>());
        }
        modifyVertragViewModel.update(archivedVertrag);

    }

    public void recyclerViewVisibility() {
        if (vertragListAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyRecyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyRecyclerView.setVisibility(View.GONE);

        }
    }

    @Override
    public void onPositionClicked(int position) {

    }
}