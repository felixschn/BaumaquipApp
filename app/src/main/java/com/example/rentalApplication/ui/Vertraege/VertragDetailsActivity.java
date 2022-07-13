package com.example.rentalApplication.ui.Vertraege;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.VertragDetailsListAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Baumaschine.ModifyBaumaschineViewModel;
import com.example.rentalApplication.ui.Kunde.ModifyKundenViewModel;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class VertragDetailsActivity extends AppCompatActivity implements VertragDetailsClickListener {
    private static final String TAG = "VertragDetailsActivity.java";
    private RecyclerView recyclerView;
    private boolean hideButton;
    private Intent intent;
    private Vertrag vertrag;
    private Kunde kunde;
    private List<Baumaschine> baumaschineVertragDetailsList;
    private List<Integer> baumaschineContractAmount;
    private TextView vertragDetailsTextView, vertragDetailsIdTextView, vertragDetailsKundeTextView, vertragDetailsKundeNameTextView, vertragDetailsStartDateTextTextView, vertragDetailsStartDateTextView, vertragDetailsEndDateTextTextView, vertragDetailsEndDateTextView, vertragDetailsSumTextView, vertragDetailsSum, vertragDetailsDiscountText, vertragDetailsDiscount;
    private ModifyVertragViewModel modifyVertragViewModel;
    private ModifyKundenViewModel modifyKundenViewModel;
    private ModifyBaumaschineViewModel modifyBaumaschineViewModel;
    private AddStuecklisteneintragViewModel addStuecklisteneintragViewModel;
    private List<Stuecklisteneintrag> stuecklisteneintragListFromVertrag = new ArrayList<>();
    private List<Stuecklisteneintrag> archivedStuecklisteneintragListFromVertrag = new ArrayList<>();

    ImageButton generatePDFbtn;

    // declaring width and height for our PDF file.
    private int pageHeight = mmToPoints(297);
    private int pagewidth = mmToPoints(210);
    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertrag_details);
        vertragDetailsTextView = findViewById(R.id.vertragDetails);
        vertragDetailsIdTextView = findViewById(R.id.vertragDetailsId);
        vertragDetailsKundeTextView = findViewById(R.id.vertragDetailsKunde);
        vertragDetailsKundeNameTextView = findViewById(R.id.vertragDetailsKundeId);
        vertragDetailsStartDateTextTextView = findViewById(R.id.vertragDetailsStartDateText);
        vertragDetailsStartDateTextView = findViewById(R.id.vertragDetailsStartDate);
        vertragDetailsEndDateTextTextView = findViewById(R.id.vertragDetailsEndDateText);
        vertragDetailsEndDateTextView = findViewById(R.id.vertragDetailsEndDate);
        vertragDetailsSumTextView = findViewById(R.id.vertragDetailsSumText);
        vertragDetailsSum = findViewById(R.id.vertragDetailsSum);
        vertragDetailsDiscountText = findViewById(R.id.vertragDetailsDiscountText);
        vertragDetailsDiscount = findViewById(R.id.vertragDetailsDiscount);
        generatePDFbtn = findViewById(R.id.vertragDetailsPrint);
        if (checkPermission()) {
            Toast.makeText(this, "Permission 'Write externalStorage' granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF();
            }
        });

        recyclerView = findViewById(R.id.vertragDetailsRecyclerview);
        recyclerView.hasFixedSize();    //TODO
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final VertragDetailsListAdapter vertragDetailsListAdapter = new VertragDetailsListAdapter(this, this);
        recyclerView.setAdapter(vertragDetailsListAdapter);


        intent = this.getIntent();
        modifyVertragViewModel = new ViewModelProvider(this).get(ModifyVertragViewModel.class);

        modifyKundenViewModel = new ViewModelProvider(this).get(ModifyKundenViewModel.class);
        addStuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
        modifyBaumaschineViewModel = new ViewModelProvider(this).get(ModifyBaumaschineViewModel.class);
        baumaschineVertragDetailsList = new ArrayList<>();
        baumaschineContractAmount = new ArrayList<>();
        if (intent == null) {
            //TODO: catch if intent is null, e.g. close the activity
            return;
        }

        vertrag = modifyVertragViewModel.loadVertragById(intent.getIntExtra("vertragRowId", 0));
        kunde = modifyKundenViewModel.loadKundeById(vertrag.getIdKunde());
        for (int i = 0; i < vertrag.getStuecklisteIds().size(); i++) {
            int stuecklisteneintragId = vertrag.getStuecklisteIds().get(i);
            Stuecklisteneintrag current = addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId);
            try {
                baumaschineContractAmount.add(addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId).getAmount());
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                return;
            }
            baumaschineVertragDetailsList.add(modifyBaumaschineViewModel.getBaumaschineById(addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId).getIdBaumaschine()));
            //TODO somethings wrong with stuecklisteneintraege which are archived but shown like they are not
            if (current.isArchived()) {
                archivedStuecklisteneintragListFromVertrag.add(addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId));
                vertragDetailsListAdapter.setBaumaschineVertragDetailsList(archivedStuecklisteneintragListFromVertrag, baumaschineVertragDetailsList, baumaschineContractAmount);

            } else {
                stuecklisteneintragListFromVertrag.add(addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId));


                vertragDetailsListAdapter.setBaumaschineVertragDetailsList(stuecklisteneintragListFromVertrag, baumaschineVertragDetailsList, baumaschineContractAmount);
            }


        }
        String activityString = intent.getStringExtra("Class");
        if (activityString.equals("ArchivedVertragListAdapter")) {
            hideButton = true;
        }

        Log.d(TAG, "Vertrag Id: " + vertrag.getIdVertrag());
        vertragDetailsIdTextView.setText(String.valueOf(vertrag.getIdVertrag()));
        vertragDetailsKundeNameTextView.setText(kunde.getName());
        vertragDetailsStartDateTextView.setText(vertrag.getBeginnVertrag().toString());
        vertragDetailsEndDateTextView.setText(vertrag.getEndeVertrag().toString());
        vertragDetailsSum.setText(String.format("%s€", vertrag.getSumOfRent()));
        vertragDetailsDiscount.setText(String.format("%s€", vertrag.getDiscountOfRent()));


    }

    public Boolean hideButtonStatus() {
        return hideButton;
    }

    public void archiveStuecklisteneintragFromVertrag(int id) {
        Stuecklisteneintrag current = addStuecklisteneintragViewModel.stuecklisteneintragById(id);
        current.setArchived(true);
        addStuecklisteneintragViewModel.update(current);
    }

    public Baumaschine getBaumaschineFromStuecklisteneintrag(int id) {
        return modifyBaumaschineViewModel.getBaumaschineById(id);
    }

    @Override
    public void onPositionClicked(int position) {

    }

    private void generatePDF() {
        // creating an object variable for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint
        // "paint" is used for drawing shapes
        // "title" for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();
        Paint description = new Paint();
        Paint content = new Paint();

        // we are adding page info to our PDF file in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is our bitmap
        // second parameter is position from left
        // third parameter is position from top and
        // last one is our variable for paint.
        //canvas.drawBitmap(scaledbmp, 56, 40, paint);

        // below line is used for adding typeface for our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        description.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        content.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is sued for setting color of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.baumaquip_main_color));
        description.setColor(ContextCompat.getColor(this, R.color.black));
        content.setColor(ContextCompat.getColor(this, R.color.black));
        description.setTextSize(8);
        content.setTextSize(8);

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        title.setTextSize(18);
        canvas.drawText("MIETVERTRAG", mmToPoints(90), mmToPoints(50), title);
        title.setTextSize(14);

        // block 1 = details costumer
        int sb1 =   60;  // start block 1
        int b1lh =   7;  // line height
        int b1c1 =  20;  // description start (distance from left border)
        int b1c2 =  60;  // value start (distance from left border)
        int b1c3 = 105;
        int b1c4 = 130;
        canvas.drawText("Angaben Mieter", mmToPoints(b1c1), mmToPoints(sb1), title);
        canvas.drawText("Name / Firma:", mmToPoints(b1c1), mmToPoints(sb1+1*b1lh), description);
        canvas.drawText(kunde.getName(), mmToPoints(b1c2), mmToPoints(sb1+1*b1lh), content);
        canvas.drawText("Straße, Nr.:", mmToPoints(b1c1), mmToPoints(sb1+2*b1lh), description);
        canvas.drawText(kunde.getStreetName().concat(" ").concat(kunde.getStreetNumber()), mmToPoints(b1c2), mmToPoints(sb1+2*b1lh), content);
        canvas.drawText("PLZ, Ort", mmToPoints(b1c3), mmToPoints(sb1+2*b1lh), description);
        canvas.drawText(kunde.getZip().concat(" ").concat(kunde.getLocation()), mmToPoints(b1c4), mmToPoints(sb1+2*b1lh), content);
        canvas.drawText("Baustelle / Kostenstelle:", mmToPoints(b1c1), mmToPoints(sb1+3*b1lh), description);
        canvas.drawText(kunde.getConstructionSide(), mmToPoints(b1c2), mmToPoints(sb1+3*b1lh), content);        //TODO: Ist ConstructionSide eine Eigenschaft des Kunden oder des Vertrags?
        canvas.drawText("Abholung von:", mmToPoints(b1c3), mmToPoints(sb1+3*b1lh), description);
        canvas.drawText("LoremIpsumLoremIpsum", mmToPoints(b1c4), mmToPoints(sb1+3*b1lh), content);      //TODO: value

        // block 2 = details items
        int sb2  =  93;  // start block 2
        int b2lh =   7;  // line height
        int b2c1 =  20;  // description start (distance from left border)
        int b2c2 =  b2c1 + 70;  // value start (distance from left border)
        int b2c3 = b2c2 + 10;
        int b2c4 = b2c3 + 20;
        int b2c5 = b2c4 + 20;
        int b2c6 = b2c5 + 30;
        canvas.drawText("Mietgegenstand", mmToPoints(b2c1), mmToPoints(sb2), title);
        //Table borders
        for (int i = 0; i<11; i++) {
            canvas.drawLine(mmToPoints(b2c1), mmToPoints(sb2 +2 +i*b2lh), mmToPoints(210 - b2c1), mmToPoints(sb2 +2 +i*b2lh), content);
        }
        canvas.drawLine(mmToPoints(b2c1), mmToPoints(sb2 +2), mmToPoints(b2c1), mmToPoints(sb2 +2 +10*b2lh), content);
        canvas.drawLine(mmToPoints(b2c2), mmToPoints(sb2 +2), mmToPoints(b2c2), mmToPoints(sb2 +2 +10*b2lh), content);
        canvas.drawLine(mmToPoints(b2c3), mmToPoints(sb2 +2), mmToPoints(b2c3), mmToPoints(sb2 +2 +10*b2lh), content);
        canvas.drawLine(mmToPoints(b2c4), mmToPoints(sb2 +2), mmToPoints(b2c4), mmToPoints(sb2 +2 +10*b2lh), content);
        canvas.drawLine(mmToPoints(b2c5), mmToPoints(sb2 +2), mmToPoints(b2c5), mmToPoints(sb2 +2 +10*b2lh), content);
        canvas.drawLine(mmToPoints(b2c6), mmToPoints(sb2 +2), mmToPoints(b2c6), mmToPoints(sb2 +2 +10*b2lh), content);
        canvas.drawLine(mmToPoints(210-b2c1), mmToPoints(sb2 +2), mmToPoints(210-b2c1), mmToPoints(sb2 +2 +10*b2lh), content);

        //Table Header
        canvas.drawText("Bezeichnung/Seriennummer", mmToPoints(b2c1+1), mmToPoints(sb2 +b2lh), content);
        canvas.drawText("Menge", mmToPoints(b2c2+1), mmToPoints(sb2 +b2lh), content);
        canvas.drawText("Betriebsstd", mmToPoints(b2c3+1), mmToPoints(sb2 +b2lh-2), content);
        canvas.drawText("Übergabe", mmToPoints(b2c3+1), mmToPoints(sb2 +b2lh+1), content);
        canvas.drawText("Betriebsstd", mmToPoints(b2c4+1), mmToPoints(sb2 +b2lh-2), content);
        canvas.drawText("Rückgabe", mmToPoints(b2c4+1), mmToPoints(sb2 +b2lh+1), content);
        canvas.drawText("Verschleiß", mmToPoints(b2c5+1), mmToPoints(sb2 +b2lh-2), content);
        canvas.drawText("/ Dia", mmToPoints(b2c5+1), mmToPoints(sb2 +b2lh+1), content);
        canvas.drawText("Versicherung", mmToPoints(b2c6+1), mmToPoints(sb2 +b2lh+1), content);
        // for (int i = 2; i<11; i++) {
        //     canvas.drawText("    ja / nein", mmToPoints(b2c6+1), mmToPoints(sb2 +i*b2lh), content);
        // }

        //Table data
        for (int i = 0; i < stuecklisteneintragListFromVertrag.size(); i++){
            String Maschinenname = modifyBaumaschineViewModel.getBaumaschineById(stuecklisteneintragListFromVertrag.get(i).getIdBaumaschine()).getMachineName(); //TODO: einfacher?
            canvas.drawText(Maschinenname, mmToPoints(b2c1+1), mmToPoints(sb2 +(i+2)*b2lh), content);
            canvas.drawText(stuecklisteneintragListFromVertrag.get(i).getAmount().toString(), mmToPoints(b2c2+1), mmToPoints(sb2 +(i+2)*b2lh), content);
            //canvas.drawText(stuecklisteneintragListFromVertrag.get(i).getOperatingHours_begin().toString(), mmToPoints(b2c3+1), mmToPoints(sb2 +(i+2)*b2lh), content); //TODO: NullPointerException wenn nicht vorhanden -> per hand?
            String insurance = (stuecklisteneintragListFromVertrag.get(i).getInsurance()) ? "ja" : "nein";
            canvas.drawText(insurance, mmToPoints(b2c6+1), mmToPoints(sb2 +(i+2)*b2lh), content);
        }


        //block 3
        int sb3  = 175;  // start block 3
        int b3lh =   7;  // line height
        int b3c1 =  20;  // description start (distance from left border)
        canvas.drawText("folgende Kosten werden in Rechnung gestellt:", mmToPoints(b3c1), mmToPoints(sb3), description);
        canvas.drawText("☐ Mietpreis   ☐ Reinigung bei Rückgabe   ☐ Betankung bei Rückgabe   ☐ Transport", mmToPoints(b3c1), mmToPoints(sb3+b3lh), description);
        canvas.drawText("Es wird eine unverzinsliche Kaution in Höhe von _______________ vereinbart.", mmToPoints(b3c1), mmToPoints(sb3+2*b3lh), description);







        // block 5 = Special agreements
        int sb5  = 200;  // start block 3
        int b5lh =   7;  // line height
        int b5c1 =  20;  // description start (distance from left border)
        canvas.drawText("Besondere Vereinbarung", mmToPoints(b5c1), mmToPoints(sb5), title);
        canvas.drawText("Eine Einweisung zur Maschine hat der Abholer erhalten. Ihm wurde angeboten die Bedienungsanleitung mitzunehmen.", mmToPoints(b5c1), mmToPoints(sb5+b5lh), description);
        canvas.drawLine(mmToPoints(b5c1), mmToPoints(sb5+2*b5lh), mmToPoints(175), mmToPoints(sb5+2*b5lh), content);

        // block 6 = handover
        int sb6  = 235;  // start block 6
        int b6lh =   5;  // line height
        int b6c1 =  20;  // description start (distance from left border)
        canvas.drawText("Übergabe (siehe Übergabeprotokoll)", mmToPoints(b6c1), mmToPoints(sb6), title);
        canvas.drawLine(mmToPoints( 35), mmToPoints(sb6+2*b6lh), mmToPoints( 75), mmToPoints(sb6+2*b6lh), content);
        canvas.drawText("Ort, Datum", mmToPoints( 35), mmToPoints(sb6+3*b6lh), content);
        canvas.drawLine(mmToPoints( 85), mmToPoints(sb6+2*b6lh), mmToPoints(125), mmToPoints(sb6+2*b6lh), content);
        canvas.drawText("Unterschrift Vermieter", mmToPoints( 85), mmToPoints(sb6+3*b6lh), content);
        canvas.drawLine(mmToPoints(135), mmToPoints(sb6+2*b6lh), mmToPoints(175), mmToPoints(sb6+2*b6lh), content);
        canvas.drawText("Unterschrift Mieter", mmToPoints(135), mmToPoints(sb6+3*b6lh), content);

        // block 7 = return
        int sb7  = 256;  // start block 7
        int b7lh =   5;  // line height
        int b7c1 =  20;  // description start (distance from left border)
        canvas.drawText("Rückgabe (siehe Übergabeprotokoll)", mmToPoints(b7c1), mmToPoints(sb7), title);
        canvas.drawLine(mmToPoints( 35), mmToPoints(sb7+2*b6lh), mmToPoints( 75), mmToPoints(sb7+2*b7lh), content);
        canvas.drawText("Ort, Datum", mmToPoints( 35), mmToPoints(sb7+3*b7lh), content);
        canvas.drawLine(mmToPoints( 85), mmToPoints(sb7+2*b7lh), mmToPoints(125), mmToPoints(sb7+2*b7lh), content);
        canvas.drawText("Unterschrift Vermieter", mmToPoints( 85), mmToPoints(sb7+3*b7lh), content);
        canvas.drawLine(mmToPoints(135), mmToPoints(sb7+2*b7lh), mmToPoints(175), mmToPoints(sb7+2*b7lh), content);
        canvas.drawText("Unterschrift Mieter", mmToPoints(135), mmToPoints(sb7+3*b7lh), content);

        // borders  TODO: remove this block
        canvas.drawLine(mmToPoints(0), mmToPoints(42), mmToPoints(210), mmToPoints(42), title);
        canvas.drawText("HEADER", mmToPoints(22), mmToPoints(40), title);
        canvas.drawLine(mmToPoints(0), mmToPoints(271), mmToPoints(210), mmToPoints(271), title);
        canvas.drawText("FOOTER", mmToPoints(22), mmToPoints(280), title);

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of our PDF file and its path.
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)  + "/contracts");
        if (!dir.exists())
            if(!dir.mkdir()) {
                Toast.makeText(VertragDetailsActivity.this, "Path not found - PDF file NOT generated.", Toast.LENGTH_SHORT).show();
                return; //TODO: Pfad konnte nicht erstellt werden
            }
        File file = new File(dir, "contract" + System.currentTimeMillis() / 1000L + ".pdf");
        if (!file.exists()) { //sollte nicht mehr vorkommen, da contract mit Zeitstempel
            try {
                file.createNewFile();
                Log.e(TAG, "savePdfFileToStorage: " + "file created" + file.getName() + "path: " + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // after creating a file name we will write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            Toast.makeText(VertragDetailsActivity.this, "PDF file generated successfully.", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(VertragDetailsActivity.this, "PDF file NOT generated.", Toast.LENGTH_SHORT).show();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission 'writeExternalStorage' Granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission 'writeExternalStorage' Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    private int mmToPoints(int mm){
        BigDecimal mmb = new BigDecimal(mm);
        return mmb.divide(new BigDecimal("25.4"), 6, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(72))
                    .setScale(0, RoundingMode.HALF_UP)
                    .intValue();

    }
}
