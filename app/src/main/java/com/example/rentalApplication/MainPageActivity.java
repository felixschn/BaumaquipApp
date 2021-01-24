package com.example.rentalApplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rentalApplication.adapter.BaumaquipAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.RentDatabase;
import com.example.rentalApplication.ui.main.AddBaumaschinenActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainPageActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton fab;
    private FloatingActionButton mAddBaumaschineFab, mAddKundeFab, mAddVertragFab;
    private TextView mAddBaumaschinenFabString, mAddKundeFabString, mAddVertragFabString;
    private Boolean isAllFabsVisible;
    private static final String TAG = "Main_Activity";
    public static int ADD_BAUMASCHINEN_ACTIVITY_REQUEST_CODE = 1;
    public static RentDatabase rentDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        fab = findViewById(R.id.fabViewPager);
        mAddBaumaschineFab = findViewById(R.id.addBaumaschine);
        mAddKundeFab = findViewById(R.id.addKunde);
        mAddVertragFab = findViewById(R.id.addVertrag);

        mAddBaumaschinenFabString = findViewById(R.id.addBaumaschineFabString);
        mAddKundeFabString = findViewById(R.id.addKundenFabString);
        mAddVertragFabString = findViewById(R.id.addVertragFabString);

        mAddBaumaschinenFabString.setText("Baumaschine hinzufügen");
        mAddKundeFabString.setText("Kunde hinzufügen");
        mAddVertragFabString.setText("Vertrag hinzufügen");

        mAddBaumaschineFab.setVisibility(View.GONE);
        mAddKundeFab.setVisibility(View.GONE);
        mAddVertragFab.setVisibility(View.GONE);
        mAddBaumaschinenFabString.setVisibility(View.GONE);
        mAddKundeFabString.setVisibility(View.GONE);
        mAddVertragFabString.setVisibility(View.GONE);

        isAllFabsVisible = false;

        fab.shrink();

        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isAllFabsVisible){
                            mAddBaumaschineFab.show();
                            mAddKundeFab.show();
                            mAddVertragFab.show();
                            mAddBaumaschinenFabString.setVisibility(View.VISIBLE);
                            mAddKundeFabString.setVisibility(View.VISIBLE);
                            mAddVertragFabString.setVisibility(View.VISIBLE);

                            fab.extend();
                            isAllFabsVisible = true;
                        }
                        else{
                            mAddBaumaschineFab.hide();
                            mAddKundeFab.hide();
                            mAddVertragFab.hide();
                            mAddBaumaschinenFabString.setVisibility(View.GONE);
                            mAddKundeFabString.setVisibility(View.GONE);
                            mAddVertragFabString.setVisibility(View.GONE);

                            fab.shrink();

                            isAllFabsVisible = false;
                        }
                    }
                }
        );
        mAddBaumaschineFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent baumaschinenIntent = new Intent(MainPageActivity.this, AddBaumaschinenActivity.class);
                MainPageActivity.this.startActivity(baumaschinenIntent);
            }
        });

        ViewPager2 viewPager2 = findViewById(R.id.viewpager);
        viewPager2.setAdapter(new BaumaquipAdapter(this));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:

                        /*fab.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "FAB Baumaschinen gedrückt");
                                startActivity(new Intent(MainPageActivity.this, AddBaumaschinenActivity.class));
                            }
                        });*/
                        break;
                    case 1:
                        /*fab.show();
                        fab.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "FAB Kunden gedrückt");
                            }
                        });*/
                        break;
                    case 2:
                        /*fab.show();
                        fab.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "FAB Verträge gedrückt");
                            }
                        });*/
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Baumaschinen");
                        break;
                    case 1:
                        tab.setText("Kunden");
                        break;
                    case 2:
                        tab.setText("Verträge");
                        break;
                }

            }
        });
        tabLayoutMediator.attach();
    }

}