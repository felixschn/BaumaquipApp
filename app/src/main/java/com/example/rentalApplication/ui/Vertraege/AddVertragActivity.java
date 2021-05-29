package com.example.rentalApplication.ui.Vertraege;

import android.os.Bundle;
import android.util.Log;
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

public class AddVertragActivity extends AppCompatActivity implements AddVertragBaumaschineListAdapter.IGetBaumaschinenFromAdapter{

    private BaumaschinenViewModel bViewModel;
    private List<String> baumaschineList = new ArrayList<String>();
    private AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter;
    private RecyclerView recyclerViewBaumaschine;
    private AddVertragViewModel addVertragViewModel;
    private static final String sendedList = "receiving list";


    @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vertrag);
        recyclerViewBaumaschine = findViewById(R.id.addVertragBaumaschineRecyclerView);
        //recyclerViewBaumaschine.hasFixedSize();
        recyclerViewBaumaschine.addItemDecoration(new SimpleItemDecoration(this));
        recyclerViewBaumaschine.setLayoutManager(new LinearLayoutManager(this));
        final AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter = new AddVertragBaumaschineListAdapter(this);
        recyclerViewBaumaschine.setAdapter(addVertragBaumaschineListAdapter);
        bViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        bViewModel.getAllBaumaschinen().observe(this, new Observer<List<Baumaschine>>() {
            @Override
            public void onChanged(List<Baumaschine> baumaschines) {
                addVertragBaumaschineListAdapter.setAddVertragBaumaschinen(baumaschines);
            }
        });
        Log.d(sendedList, "" + addVertragBaumaschineListAdapter.sendBaumaschineList().size());
    }


    //created interface to intercept data from the recyclerview adapter
    @Override
    public void getBaumaschinenFromAdapter(List<Baumaschine> baumaschineList) {
        Log.d(sendedList, "Activity " + baumaschineList.size());

    }
}
