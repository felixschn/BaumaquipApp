package com.example.rentalApplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rentalApplication.adapter.BaumaquipAdapter;
import com.example.rentalApplication.persistence.RentDatabase;
import com.example.rentalApplication.ui.Baumaschine.AddBaumaschinenActivity;
import com.example.rentalApplication.ui.Baumaschine.ArchivedBaumaschinenActivity;
import com.example.rentalApplication.ui.Kunde.AddKundenActivity;
import com.example.rentalApplication.ui.Kunde.ArchivedKundenActivity;
import com.example.rentalApplication.ui.Vertraege.AddVertragActivity;
import com.example.rentalApplication.ui.Vertraege.ArchivedVertragActivity;
import com.example.rentalApplication.ui.Vertraege.VertragDetailsActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MainPageActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton fab;
    private ExtendedFloatingActionButton mAddBaumaschineFab, mAddKundeFab, mAddVertragFab;
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

        /*mAddBaumaschinenFabString = findViewById(R.id.addBaumaschineFabString);
        mAddKundeFabString = findViewById(R.id.addKundenFabString);
        mAddVertragFabString = findViewById(R.id.addVertragFabString);*/

        /*mAddBaumaschinenFabString.setText(R.string.baumaschinenAdd);
        mAddKundeFabString.setText(R.string.kundenAdd);
        mAddVertragFabString.setText(R.string.vertraegeAdd);*/

        mAddBaumaschineFab.setVisibility(View.GONE);
        mAddKundeFab.setVisibility(View.GONE);
        mAddVertragFab.setVisibility(View.GONE);
        /*mAddBaumaschinenFabString.setVisibility(View.GONE);
        mAddKundeFabString.setVisibility(View.GONE);
        mAddVertragFabString.setVisibility(View.GONE);*/

        isAllFabsVisible = false;

        fab.shrink();

        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isAllFabsVisible) {
                            mAddBaumaschineFab.show();
                            mAddKundeFab.show();
                            mAddVertragFab.show();
                            /*mAddBaumaschinenFabString.setVisibility(View.VISIBLE);
                            mAddKundeFabString.setVisibility(View.VISIBLE);
                            mAddVertragFabString.setVisibility(View.VISIBLE);*/

                            fab.extend();
                            isAllFabsVisible = true;
                        } else {
                            mAddBaumaschineFab.hide();
                            mAddKundeFab.hide();
                            mAddVertragFab.hide();
                            /*mAddBaumaschinenFabString.setVisibility(View.GONE);
                            mAddKundeFabString.setVisibility(View.GONE);
                            mAddVertragFabString.setVisibility(View.GONE);*/

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
                baumaschinenIntent.putExtra("Class", "MainPageActivity");
                MainPageActivity.this.startActivity(baumaschinenIntent);
            }
        });
        mAddKundeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kundenIntent = new Intent(MainPageActivity.this, AddKundenActivity.class);
                kundenIntent.putExtra("Class", "MainPageActivity");
                MainPageActivity.this.startActivity(kundenIntent);
            }
        });
        mAddVertragFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vertragIntent = new Intent(MainPageActivity.this, AddVertragActivity.class);
                MainPageActivity.this.startActivity(vertragIntent);
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
                switch (position) {
                    case 0:
                        Log.d(TAG, "BaumaschineTab");

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
                switch (position) {
                    case 0:
                        tab.setText(R.string.baumaschinen);
                        break;
                    case 1:
                        tab.setText(R.string.kunden);
                        break;
                    case 2:
                        tab.setText(R.string.vertraege);
                        break;
                }

            }
        });
        tabLayoutMediator.attach();
        try {
            backupDatabase();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.archive_menu, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,

                                           @NonNull String[] permissions,

                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                backupDatabase();

            } else {

                System.out.println("TEST");

            }

        }

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.archivedBaumaschine:
                Intent archivedBaumaschinenIntent = new Intent(MainPageActivity.this, ArchivedBaumaschinenActivity.class);
                archivedBaumaschinenIntent.putExtra("Class", "MainPageActivity");
                MainPageActivity.this.startActivity(archivedBaumaschinenIntent);
                return true;
            case R.id.archivedKunde:
                Intent archivedKundenIntent = new Intent(MainPageActivity.this, ArchivedKundenActivity.class);
                archivedKundenIntent.putExtra("Class", "MainPageActivity");
                MainPageActivity.this.startActivity(archivedKundenIntent);
                return true;
            case R.id.archivedVertrag:
                Intent archivedVertragIntent = new Intent(MainPageActivity.this, ArchivedVertragActivity.class);
                archivedVertragIntent.putExtra("Class","MainPageActivity");
                MainPageActivity.this.startActivity(archivedVertragIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static void copyFile(File src, File dst) throws IOException
    {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
    /*private static void backupDatabase(Context context){
        try {
            File db_source = new File("/data/data/com.example.baumaquip_rental/databases/rent_database");
            File db_source_shm = new File("/data/data/com.example.baumaquip_rental/databases/rent_database-shm");
            File db_source_wal = new File("/data/data/com.example.baumaquip_rental/databases/rent_database-wal");
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)  + "/rental_db_backup");

            *//*if (!dir.exists())
                if(!dir.mkdir()) {
                    Toast.makeText(context, "Path not found", Toast.LENGTH_SHORT).show();
                    return; //TODO: Pfad konnte nicht erstellt werden
                }*//*
           *//*File dst_rent_database = new File(dir, "rent_database");
            File dst_rent_database_shm = new File(dir, "rent_database-shm");
            File dst_rent_database_wal = new File(dir, "rent_database-wal");*//*

            File dst_rent_database = new File("/data/data/com.example.baumaquip_rental/rent_database");
            File dst_rent_database_shm = new File("/data/data/com.example.baumaquip_rental/rent_database-shm");
            File dst_rent_database_wal = new File("/data/data/com.example.baumaquip_rental/rent_database-wal");


            copyFile(db_source, dst_rent_database);
            copyFile(db_source_shm, dst_rent_database_shm);
            copyFile(db_source_wal, dst_rent_database_wal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    private void backupDatabase(){
        try{
            File internalStorage = getFilesDir();
            File data = Environment.getDataDirectory();

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            if(true){
                String currentDBPath = "/data/" + "com.example.baumaquip_rental" + "/databases/" + "rent_database";
                String backupDBPath = "rentDatabaseBackup.db";

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(internalStorage, backupDBPath);

                if(currentDB.exists()){
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();

                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
            //data/data/com.example.baumaquip_rental/databases/rent_database

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void restoreDatabase(Context context){
        try {
            /*File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)  + "/rental_db_backup");
            File db_source = new File(dir, "rent_database");
            File db_source_shm = new File(dir, "rent_database-shm");
            File db_source_wal = new File(dir, "rent_database-wal");*/

            File db_source = new File("/data/data/com.example.baumaquip_rental/rent_database");
            File db_source_shm = new File("/data/data/com.example.baumaquip_rental/rent_database-shm");
            File db_source_wal = new File("/data/data/com.example.baumaquip_rental/rent_database-wal");


            File dir_db = new File("/storage/emulated/0/Documents/rental_db_backup");
            File dst_rent_database = new File("/data/data/com.example.baumaquip_rental/databases/rent_database");
            File dst_rent_database_shm = new File("/data/data/com.example.baumaquip_rental/databases/rent_database-shm");
            File dst_rent_database_wal = new File("/data/data/com.example.baumaquip_rental/databases/rent_database-wal");

            copyFile(db_source, dst_rent_database);
            copyFile(db_source_shm, dst_rent_database_shm);
            copyFile(db_source_wal, dst_rent_database_wal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}