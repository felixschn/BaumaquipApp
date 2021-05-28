package com.example.rentalApplication.ui.Vertraege;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.AddVertragBaumaschineListAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddVertragActivity extends AppCompatActivity {

    private BaumaschinenViewModel bViewModel;
    private List<String> baumaschineList = new ArrayList<String>();
    private AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter;
    private RecyclerView recyclerViewBaumaschine;
    private AddVertragViewModel addVertragViewModel;


    @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vertrag);
        recyclerViewBaumaschine = findViewById(R.id.addVertragBaumaschineRecyclerView);
        //recyclerViewBaumaschine.hasFixedSize();
        recyclerViewBaumaschine.setLayoutManager(new LinearLayoutManager(this));
        final AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter = new AddVertragBaumaschineListAdapter();
        recyclerViewBaumaschine.setAdapter(addVertragBaumaschineListAdapter);

        bViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        bViewModel.getAllBaumaschinen().observe(this, new Observer<List<Baumaschine>>() {
            @Override
            public void onChanged(List<Baumaschine> baumaschines) {
                addVertragBaumaschineListAdapter.setAddVertragBaumaschinen(baumaschines);
            }
        });
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_vertrag, container, false);
        recyclerViewBaumaschine = view.findViewById(R.id.addVertragBaumaschineRecyclerView);
        recyclerViewBaumaschine.hasFixedSize();
        recyclerViewBaumaschine.setLayoutManager(new LinearLayoutManager(this));
        final AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter = new AddVertragBaumaschineListAdapter();
        recyclerViewBaumaschine.setAdapter(addVertragBaumaschineListAdapter);
        bViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        bViewModel.getAllBaumaschinen().observe(this, baumaschines -> {
            for (int i = 0; i < baumaschines.size(); i++) {
                baumaschineList.add(baumaschines.get(i).getMachineName());
                System.out.println(baumaschines.get(i).getRowid());
            }
        });
        return inflater.inflate(R.layout.activity_add_vertrag, container,false);
    }



}
