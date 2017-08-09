package de.moja.bier_v06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Observable;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;

import android.view.View;
import android.util.Log;

import android.os.Bundle;
import android.widget.TextView;
//import android.widget.TextView;

import com.punchthrough.bean.sdk.message.ScratchBank;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.Bean;


public class MainActivity extends AppCompatActivity implements BeanDiscoveryListener, BeanListener, Observer {
    //Objekte
    final String TAG = "Bier";
    //UI
    public Button myButton,myButton2;
    public ImageButton btn_addm,btn_addh,btn_addr;
    public ImageButton btn_subm_01,btn_subm_02,btn_subm_03,btn_subm_04,btn_subm_05,btn_subm_06;
    public ImageButton btn_subh_01,btn_subh_02,btn_subh_03,btn_subh_04;
    public ImageButton btn_subr_01,btn_subr_02,btn_subr_03,btn_subr_04,btn_subr_05;
    public LinearLayout malt01,malt02,malt03,malt04,malt05,malt06;
    public LinearLayout hop01,hop02,hop03,hop04;
    public LinearLayout rast01,rast02,rast03,rast04,rast05;
    public AutoCompleteTextView actf_malt01,actf_malt02,actf_malt03,actf_malt04,actf_malt05,actf_malt06;
    public EditText et_EBCm1min,et_EBCm2min,et_EBCm3min,et_EBCm4min,et_EBCm5min,et_EBCm6min;
    public EditText et_EBCm1max,et_EBCm2max,et_EBCm3max,et_EBCm4max,et_EBCm5max,et_EBCm6max;
    public EditText et_malt01g,et_malt02g,et_malt03g,et_malt04g,et_malt05g,et_malt06g;
    public AutoCompleteTextView actf_hop01,actf_hop02,actf_hop03,actf_hop04;
    public EditText et_hop01a,et_hop02a,et_hop03a,et_hop04a;
    public AutoCompleteTextView actf_hefe01;
    public EditText et_rast01,et_rast02,et_rast03,et_rast04,et_rast05;
    public TextView lbl_r1,lbl_r2,lbl_r3,lbl_r4,lbl_r5;
    //Var
    private int malt = 1,hop = 1,rast =1;
    public String filename_malt = "malt.db";
    public String filename_hop  = "hop.db";
    public String filename_hefe = "hefe.db";
    public List<String> maltlist = new ArrayList<String>();
    public List<String> maltlistEBCmin = new ArrayList<String>();
    public List<String> maltlistEBCmax = new ArrayList<String>();
    public List<String> hoplist = new ArrayList<String>();
    public List<String> hefelist = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protokol);

        //Test
        myButton = (Button) findViewById(R.id.btn_test);
        myButton2= (Button) findViewById(R.id.btn_test2);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetfile(filename_malt,baseMALT,maltlist);
                resetfile(filename_hefe,baseHEFE,hefelist);

                //Log.d(TAG,"TESTBUTTON 1 (save)");
                //writeFile();
                //Log.d(TAG,"TESTBUTTON 1 (read list)" + maltlist.get(3));
                //add_maltlist("hallo");
            }
        });
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"load (list)");
                for(int n=0;n<maltlist.size();n++){
                    Log.d(TAG,"list: (" + n + ") "+ maltlist.get(n));
                }

            }
        });

        //region UI
        btn_addm = (ImageButton) findViewById(R.id.prot_btn_addmalt);
        btn_subm_01 = (ImageButton) findViewById(R.id.prot_submalt_01);
        btn_subm_02 = (ImageButton) findViewById(R.id.prot_submalt_02);
        btn_subm_03 = (ImageButton) findViewById(R.id.prot_submalt_03);
        btn_subm_04 = (ImageButton) findViewById(R.id.prot_submalt_04);
        btn_subm_05 = (ImageButton) findViewById(R.id.prot_submalt_05);
        btn_subm_06 = (ImageButton) findViewById(R.id.prot_submalt_06);

        btn_addh = (ImageButton) findViewById(R.id.prot_btn_addhop);
        btn_subh_01 = (ImageButton) findViewById(R.id.prot_subhop_01);
        btn_subh_02 = (ImageButton) findViewById(R.id.prot_subhop_02);
        btn_subh_03 = (ImageButton) findViewById(R.id.prot_subhop_03);
        btn_subh_04 = (ImageButton) findViewById(R.id.prot_subhop_04);

        btn_addr = (ImageButton)findViewById(R.id.prot_btn_addrast);
        btn_subr_01 = (ImageButton) findViewById(R.id.prot_subrast_01);
        btn_subr_02 = (ImageButton) findViewById(R.id.prot_subrast_02);
        btn_subr_03 = (ImageButton) findViewById(R.id.prot_subrast_03);
        btn_subr_04 = (ImageButton) findViewById(R.id.prot_subrast_04);
        btn_subr_05 = (ImageButton) findViewById(R.id.prot_subrast_05);


        actf_malt01 = (AutoCompleteTextView) findViewById(R.id.prot_actf_malt01);
        actf_malt02 = (AutoCompleteTextView) findViewById(R.id.prot_actf_malt02);
        actf_malt03 = (AutoCompleteTextView) findViewById(R.id.prot_actf_malt03);
        actf_malt04 = (AutoCompleteTextView) findViewById(R.id.prot_actf_malt04);
        actf_malt05 = (AutoCompleteTextView) findViewById(R.id.prot_actf_malt05);
        actf_malt06 = (AutoCompleteTextView) findViewById(R.id.prot_actf_malt06);

        et_EBCm1min = (EditText) findViewById(R.id.prot_et_malt01EBCmin);
        et_EBCm1max = (EditText) findViewById(R.id.prot_et_malt01EBCmax);
        et_EBCm2min = (EditText) findViewById(R.id.prot_et_malt02EBCmin);
        et_EBCm2max = (EditText) findViewById(R.id.prot_et_malt02EBCmax);
        et_EBCm3min = (EditText) findViewById(R.id.prot_et_malt03EBCmin);
        et_EBCm3max = (EditText) findViewById(R.id.prot_et_malt03EBCmax);
        et_EBCm4min = (EditText) findViewById(R.id.prot_et_malt04EBCmin);
        et_EBCm4max = (EditText) findViewById(R.id.prot_et_malt04EBCmax);
        et_EBCm5min = (EditText) findViewById(R.id.prot_et_malt05EBCmin);
        et_EBCm5max = (EditText) findViewById(R.id.prot_et_malt05EBCmax);
        et_EBCm6min = (EditText) findViewById(R.id.prot_et_malt06EBCmin);
        et_EBCm6max = (EditText) findViewById(R.id.prot_et_malt06EBCmax);

        et_malt01g = (EditText) findViewById(R.id.prot_et_malt01g);
        et_malt02g = (EditText) findViewById(R.id.prot_et_malt02g);
        et_malt03g = (EditText) findViewById(R.id.prot_et_malt03g);
        et_malt04g = (EditText) findViewById(R.id.prot_et_malt04g);
        et_malt05g = (EditText) findViewById(R.id.prot_et_malt05g);
        et_malt06g = (EditText) findViewById(R.id.prot_et_malt06g);

        actf_hop01 = (AutoCompleteTextView) findViewById(R.id.prot_actf_hop1);
        actf_hop02 = (AutoCompleteTextView) findViewById(R.id.prot_actf_hop2);
        actf_hop03 = (AutoCompleteTextView) findViewById(R.id.prot_actf_hop3);
        actf_hop04 = (AutoCompleteTextView) findViewById(R.id.prot_actf_hop4);

        et_hop01a = (EditText) findViewById(R.id.prot_et_hop01iso);
        et_hop02a = (EditText) findViewById(R.id.prot_et_hop02iso);
        et_hop03a = (EditText) findViewById(R.id.prot_et_hop03iso);
        et_hop04a = (EditText) findViewById(R.id.prot_et_hop04iso);

        actf_hefe01 = (AutoCompleteTextView) findViewById(R.id.prot_actf_hefe01);

        et_rast01 = (EditText) findViewById(R.id.prot_et_rast1);
        et_rast02 = (EditText) findViewById(R.id.prot_et_rast2);
        et_rast03 = (EditText) findViewById(R.id.prot_et_rast3);
        et_rast04 = (EditText) findViewById(R.id.prot_et_rast4);
        et_rast05 = (EditText) findViewById(R.id.prot_et_rast5);

        lbl_r1 = (TextView) findViewById(R.id.prot_lbl_r1);
        lbl_r2 = (TextView) findViewById(R.id.prot_lbl_r2);
        lbl_r3 = (TextView) findViewById(R.id.prot_lbl_r3);
        lbl_r4 = (TextView) findViewById(R.id.prot_lbl_r4);
        lbl_r5 = (TextView) findViewById(R.id.prot_lbl_r5);


        //malt01 = (LinearLayout) findViewById(R.id.malt01);
        malt02 = (LinearLayout) findViewById(R.id.malt02);
        malt03 = (LinearLayout) findViewById(R.id.malt03);
        malt04 = (LinearLayout) findViewById(R.id.malt04);
        malt05 = (LinearLayout) findViewById(R.id.malt05);
        malt06 = (LinearLayout) findViewById(R.id.malt06);

        //hop01 = (LinearLayout) findViewById(R.id.hop01);
        hop02 = (LinearLayout) findViewById(R.id.hop02);
        hop03 = (LinearLayout) findViewById(R.id.hop03);
        hop04 = (LinearLayout) findViewById(R.id.hop04);

        //rast01 = (LinearLayout) findViewById(R.id.rast01);
        rast02 = (LinearLayout) findViewById(R.id.rast02);
        rast03 = (LinearLayout) findViewById(R.id.rast03);
        rast04 = (LinearLayout) findViewById(R.id.rast04);
        rast05 = (LinearLayout) findViewById(R.id.rast05);

        //endregion

        //region Autocomplete
        String path = getFilesDir().toString();
        File f = new File(path);

        //Init malzliste
        File file = getBaseContext().getFileStreamPath(filename_malt);
        if (file.exists()) {
            try {
                FileInputStream fIn = openFileInput(filename_malt);
                InputStreamReader isr = new InputStreamReader(fIn);
                BufferedReader buffreader = new BufferedReader(isr);
                String readString = buffreader.readLine();
                String tmpsplit;
                while(readString!= null){
                    maltlist.add(readString.substring(0,readString.indexOf(";")));
                    Log.d(TAG,"init Malz:" + readString.substring(0,readString.indexOf(";")));

                    tmpsplit = readString.substring(readString.indexOf(";")+1,readString.length());
                    maltlistEBCmin.add(tmpsplit.substring(0,tmpsplit.indexOf(";")));
                    Log.d(TAG,"init Malz min EBC:" + tmpsplit.substring(0,tmpsplit.indexOf(";")));

                    tmpsplit = tmpsplit.substring(tmpsplit.indexOf(";")+1,tmpsplit.length());
                    maltlistEBCmax.add(tmpsplit.substring(0,tmpsplit.indexOf(";")));
                    Log.d(TAG,"init Malz max EBC:" + tmpsplit.substring(0,tmpsplit.indexOf(";")));

                    readString = buffreader.readLine();
                }
                isr.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        ArrayAdapter<String> maltadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, maltlist);

        actf_malt01.setAdapter(maltadapter);
        actf_malt02.setAdapter(maltadapter);
        actf_malt03.setAdapter(maltadapter);
        actf_malt04.setAdapter(maltadapter);
        actf_malt05.setAdapter(maltadapter);
        actf_malt06.setAdapter(maltadapter);

        //Init hopfenliste
        file = getBaseContext().getFileStreamPath(filename_hop);
        if (file.exists()) {
            try {
                FileInputStream fIn = openFileInput(filename_hop);
                InputStreamReader isr = new InputStreamReader(fIn);
                BufferedReader buffreader = new BufferedReader(isr);
                String readString = buffreader.readLine();
                while(readString!= null){
                    hoplist.add(readString);
                    Log.d(TAG,"init Hopfen: " + readString);
                    readString = buffreader.readLine();
                }
                isr.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        ArrayAdapter<String> hopadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, hoplist);

        actf_hop01.setAdapter(hopadapter);
        actf_hop02.setAdapter(hopadapter);
        actf_hop03.setAdapter(hopadapter);
        actf_hop04.setAdapter(hopadapter);

        //Init Hefeliste
        file = getBaseContext().getFileStreamPath(filename_hefe);
        if (file.exists()) {
            try {
                FileInputStream fIn = openFileInput(filename_hefe);
                InputStreamReader isr = new InputStreamReader(fIn);
                BufferedReader buffreader = new BufferedReader(isr);
                String readString = buffreader.readLine();
                while(readString!= null){
                    hefelist.add(readString);
                    Log.d(TAG,"init Hefe: " + readString);
                    readString = buffreader.readLine();
                }
                isr.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        ArrayAdapter<String> hefeadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, hefelist);

        actf_hefe01.setAdapter(hefeadapter);

        //endregion

        //region INIT

        malt02.setVisibility(View.GONE);
        malt03.setVisibility(View.GONE);
        malt04.setVisibility(View.GONE);
        malt05.setVisibility(View.GONE);
        malt06.setVisibility(View.GONE);
        btn_subm_01.setVisibility(View.GONE);
        btn_subm_02.setVisibility(View.GONE);
        btn_subm_03.setVisibility(View.GONE);
        btn_subm_04.setVisibility(View.GONE);
        btn_subm_05.setVisibility(View.GONE);
        btn_subm_06.setVisibility(View.GONE);

        hop02.setVisibility(View.GONE);
        hop03.setVisibility(View.GONE);
        hop04.setVisibility(View.GONE);
        btn_subh_01.setVisibility(View.GONE);

        rast02.setVisibility(View.GONE);
        rast03.setVisibility(View.GONE);
        rast04.setVisibility(View.GONE);
        rast05.setVisibility(View.GONE);
        btn_subr_01.setVisibility(View.GONE);

        //endregion /
        print();

         /* Set Text Watcher listener */
        et_rast01.addTextChangedListener(watcher);
        et_rast02.addTextChangedListener(watcher);
        et_rast03.addTextChangedListener(watcher);
        et_rast04.addTextChangedListener(watcher);
        et_rast05.addTextChangedListener(watcher);

        actf_malt01.addTextChangedListener(EBCwatcher);
        actf_malt02.addTextChangedListener(EBCwatcher);
        actf_malt03.addTextChangedListener(EBCwatcher);
        actf_malt04.addTextChangedListener(EBCwatcher);
        actf_malt05.addTextChangedListener(EBCwatcher);
        actf_malt06.addTextChangedListener(EBCwatcher);

        /*
        actf_malt01.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG,"listener");
                String tmpcheck = actf_malt01.getText().toString();
                for(int n=0;n<maltlist.size();n++){
                    if(tmpcheck.equals(maltlist.get(n))){
                        Log.d(TAG,"TREFFER");
                    }
                }
                //add_maltlist(actf_malt01.getText().toString());
            }
        });
*/


        //region add buttons (3x)
        btn_addm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_malt();
            }
        });
        btn_addh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_hop();
            }
        });
        btn_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_rast();
            }
        });//endregion

        //region btn sub buttons (
        //malt
        btn_subm_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_malt(1);
            }
        });
        btn_subm_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_malt(2);
            }
        });
        btn_subm_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_malt(3);
            }
        });
        btn_subm_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_malt(4);
            }
        });
        btn_subm_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_malt(5);
            }
        });
        btn_subm_06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_malt(6);
            }
        });
        //hop
        btn_subh_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_hop(1);
            }
        });
        btn_subh_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_hop(2);
            }
        });
        btn_subh_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_hop(3);
            }
        });
        btn_subh_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_hop(4);
            }
        });
        //rast
        btn_subr_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_rast(1);
            }
        });
        btn_subr_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_rast(2);
            }
        });
        btn_subr_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_rast(3);
            }
        });
        btn_subr_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_rast(4);
            }
        });
        btn_subr_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_rast(5);
            }
        });//endregion


    }

    //Datenbank
    private static final String[] baseMALT = new String[] {//region Malz
            "Pilsener Malz;2;5;",
            "Pale Ale Malz;5,5;7,5;",
            "Wiener Malz;7;9;",
            "Münchner Malz;12;25;",
            "BREWFERM Special-B;350;350;",
            "BREWFERM Cara-crystal;120;120;",
            "Weizenmalz hell;3;5;",
            "Weizenmalz dunkel;14;18;",
            "Dinkelmalz;0;0;",
            "Roggenmalz;3;8;",
            "Emmermalz;3;5;",
            "Einkornmalz;3;5;",
            "Hafermalz;3;8;",
            "Schwarzhafermalz;3;8;",
            "Triticalemalz;3;5;",
            "Hirsemalz;3;8;",
            "Caramalz extra-hell;3;5;",
            "CaraPils;3;5;",
            "CaraFoam;3;5;",
            "Caramalz hell;20;30;",
            "CARAHELL;20;30;",
            "Carared;40;50;",
            "Caramalz dunkel;80;100;",
            "CARAMÜNCH I;80;100;",
            "CARAMÜNCH II;110;130;",
            "CARAMÜNCH III;140;160;",
            "CARAAROMA;320;320;",
            "Weizen-Caramalz;100;130;",
            "Roggen-Caramelmalz;150;200;",
            "CARAFA I;800;1000;",
            "CARAFA II;1100;1200;",
            "CARAFA III;1300;1500;",
            "Weizenröstmalz;800;1200;",
            "Roggenröstmalz;500;800;",
            "Dinkelröstmalz;450;650;",
            "Röstgerste;800;800;",
            "Black Barley;1300;1300;",
            "Sauermalz;3;7;",
            "Spitzmalz;2;5;",
            "Melanoidinmalz;60;80;",
            "Diastasemalz;3;6;",
            "Weizendiastasemalz;3;6;",
            "Rauchmalz;3;6;",
            "Grünmalz;0;0;",
            "Roggenrotmalz;250;250;",
            "Golden Promise Pale Ale;4;4;",
            "Best Red X;28;32;",
            "Reisflocken;0;0;"
            //endregion
    };
    private static final String[] baseHOP = new String[] {//region Hopfen
            "Admiral",
            "Agnus",
            "Ahtanum",
            "Amarillo",
            "Amethyst",
            "Apollo",
            "Aramis",
            "Archer",
            "Atlas",
            "Aurora",
            "Azacca™",
            "Beata",
            "Belma",
            "Boadicea",
            "Bramling Cross",
            "Bravo",
            "Brewers Gold",
            "Bullion",
            "Calypso",
            "Cascade",
            "Cashmere",
            "Centennial",
            "Challenger",
            "Chelan",
            "Chinook",
            "Citra",
            "Cluster",
            "Columbus",
            "Comet",
            "Crystal",
            "Delta",
            "Dr. Rudi",
            "East Kent Goldings",
            "El Dorado",
            "Ella",
            "Endeavourv",
            "Equinox",
            "Extra Styrian Dana",
            "Falconers Flight",
            "Fantasia",
            "First Gold",
            "Fuggles",
            "Fusion",
            "Galaxy",
            "Galena",
            "Glacier",
            "Goldings",
            "Green Bullet",
            "Hallertau Blanc",
            "Hallertauer Comet",
            "Hallertauer Magnum",
            "Hallertauer Mittelfrüher",
            "Hallertauer Tradition",
            "Harmonie",
            "Herkules",
            "Hersbrucker Pure",
            "Hersbrucker Spät",
            "Huell Melon",
            "Jarrylo",
            "Junga",
            "Kazbek",
            "Kohatu",
            "Lemondrop",
            "Liberty",
            "Lubelski",
            "Magnum Slowenien",
            "Magnum US",
            "Malling",
            "Mandarina Bavaria",
            "Marynka",
            "Merkur",
            "Millenium",
            "Mosaic",
            "Motueka",
            "Mount Hood",
            "Nelson Sauvin",
            "Newport",
            "Northdown",
            "Northern Brewer",
            "Nugget",
            "Opal",
            "Pacific Gem",
            "Pacific Jade",
            "Pacifica (Pacific Hallertau)",
            "Palisade",
            "Perle",
            "Phoenix",
            "Pilgrim",
            "Polaris",
            "Premiant",
            "Progress",
            "Rakau",
            "Riwaka",
            "Saazer",
            "Santiam",
            "Saphir",
            "Savinjski Golding",
            "Simcoe",
            "Sladek",
            "Smaragd",
            "Sorachi Ace",
            "Southern Cross",
            "Sovereign",
            "Spalter",
            "Spalter Select",
            "Sterling",
            "Sticklebract",
            "Strisselspalt",
            "Styrian Goldings",
            "Styrian Golding Celeia",
            "Summer",
            "Summit",
            "Super Galena",
            "Super Pride",
            "Sybilla",
            "Target",
            "Taurus",
            "Tettnanger",
            "Topaz",
            "Triskel",
            "Vanguard",
            "Vic Secret",
            "Vital",
            "Wai-Iti",
            "Waimea",
            "Wakatu",
            "Warrior",
            "Willamette",
            "Zeus",
            "Zythos"
            //endregion
    };
    private static final String[] baseHEFE = new String[] {//region Hefe
            "Nottingham Ale",
            "S-85"
            //endregion
    };


    //OWN
    public void add_maltlist(String maltin){
        boolean addI = true;

        for(int n = 0;n<maltlist.size();n++){
            if(maltlist.get(n).equals(maltin)){
                addI = false;
                Log.d(TAG,"false");
            }else{
                Log.d(TAG,"true: " + n + " " + maltlist.get(n) + " - " + maltin);
            }
        }
        if(addI){
            maltlist.add(maltin);
            Log.d(TAG,"add: " + addI);
            writeFile(filename_malt,maltlist);
        }
    }

    public void add_malt(){
        if(malt<=5){malt += 1;}
        btn_subm_01.setVisibility(View.VISIBLE);

        if(malt == 2){malt02.setVisibility(View.VISIBLE);btn_subm_02.setVisibility(View.VISIBLE);}
        if(malt == 3){malt03.setVisibility(View.VISIBLE);btn_subm_03.setVisibility(View.VISIBLE);}
        if(malt == 4){malt04.setVisibility(View.VISIBLE);btn_subm_04.setVisibility(View.VISIBLE);}
        if(malt == 5){malt05.setVisibility(View.VISIBLE);btn_subm_05.setVisibility(View.VISIBLE);}
        if(malt == 6){malt06.setVisibility(View.VISIBLE);btn_subm_06.setVisibility(View.VISIBLE);btn_addm.setVisibility(View.GONE);}
    }
    public void add_hop(){
        if(hop<=3){hop += 1;}
        btn_subh_01.setVisibility(View.VISIBLE);

        if(hop == 2){hop02.setVisibility(View.VISIBLE);}
        if(hop == 3){hop03.setVisibility(View.VISIBLE);}
        if(hop == 4){hop04.setVisibility(View.VISIBLE);btn_addh.setVisibility(View.GONE);}
    }
    public void add_rast(){
        if(rast<=4){rast += 1;}
        btn_subr_01.setVisibility(View.VISIBLE);

        if(rast == 2){rast02.setVisibility(View.VISIBLE);}
        if(rast == 3){rast03.setVisibility(View.VISIBLE);}
        if(rast == 4){rast04.setVisibility(View.VISIBLE);}
        if(rast == 5){rast05.setVisibility(View.VISIBLE);btn_addr.setVisibility(View.GONE);}
    }

    public void sub_malt(int maltno){
        btn_addm.setVisibility(View.VISIBLE);
        switch (maltno){
            case 1:
                actf_malt01.setText(actf_malt02.getText());et_malt01g.setText(et_malt02g.getText());
                actf_malt02.setText(actf_malt03.getText());et_malt02g.setText(et_malt03g.getText());
                actf_malt03.setText(actf_malt04.getText());et_malt03g.setText(et_malt04g.getText());
                actf_malt04.setText(actf_malt05.getText());et_malt04g.setText(et_malt05g.getText());
                actf_malt05.setText(actf_malt06.getText());et_malt05g.setText(et_malt06g.getText());
                if(malt == 2){actf_malt02.setText("");malt02.setVisibility(View.GONE);btn_subm_02.setVisibility(View.GONE);}
                if(malt == 3){actf_malt03.setText("");malt03.setVisibility(View.GONE);btn_subm_03.setVisibility(View.GONE);}
                if(malt == 4){actf_malt04.setText("");malt04.setVisibility(View.GONE);btn_subm_04.setVisibility(View.GONE);}
                if(malt == 5){actf_malt05.setText("");malt05.setVisibility(View.GONE);btn_subm_05.setVisibility(View.GONE);}
                if(malt == 6){actf_malt06.setText("");malt06.setVisibility(View.GONE);btn_subm_06.setVisibility(View.GONE);}
                malt -= 1;
                break;
            case 2:
                actf_malt02.setText(actf_malt03.getText());et_malt02g.setText(et_malt03g.getText());
                actf_malt03.setText(actf_malt04.getText());et_malt03g.setText(et_malt04g.getText());
                actf_malt04.setText(actf_malt05.getText());et_malt04g.setText(et_malt05g.getText());
                actf_malt05.setText(actf_malt06.getText());et_malt05g.setText(et_malt06g.getText());
                if(malt == 2){actf_malt02.setText("");malt02.setVisibility(View.GONE);btn_subm_02.setVisibility(View.GONE);}
                if(malt == 3){actf_malt03.setText("");malt03.setVisibility(View.GONE);btn_subm_03.setVisibility(View.GONE);}
                if(malt == 4){actf_malt04.setText("");malt04.setVisibility(View.GONE);btn_subm_04.setVisibility(View.GONE);}
                if(malt == 5){actf_malt05.setText("");malt05.setVisibility(View.GONE);btn_subm_05.setVisibility(View.GONE);}
                if(malt == 6){actf_malt06.setText("");malt06.setVisibility(View.GONE);btn_subm_06.setVisibility(View.GONE);}
                malt -= 1;
                break;
            case 3:
                actf_malt03.setText(actf_malt04.getText());et_malt03g.setText(et_malt04g.getText());
                actf_malt04.setText(actf_malt05.getText());et_malt04g.setText(et_malt05g.getText());
                actf_malt05.setText(actf_malt06.getText());et_malt05g.setText(et_malt06g.getText());
                if(malt == 3){actf_malt03.setText("");malt03.setVisibility(View.GONE);btn_subm_03.setVisibility(View.GONE);}
                if(malt == 4){actf_malt04.setText("");malt04.setVisibility(View.GONE);btn_subm_04.setVisibility(View.GONE);}
                if(malt == 5){actf_malt05.setText("");malt05.setVisibility(View.GONE);btn_subm_05.setVisibility(View.GONE);}
                if(malt == 6){actf_malt06.setText("");malt06.setVisibility(View.GONE);btn_subm_06.setVisibility(View.GONE);}
                malt -= 1;
                break;
            case 4:
                actf_malt04.setText(actf_malt05.getText());et_malt04g.setText(et_malt05g.getText());
                actf_malt05.setText(actf_malt06.getText());et_malt05g.setText(et_malt06g.getText());
                if(malt == 4){actf_malt04.setText("");malt04.setVisibility(View.GONE);btn_subm_04.setVisibility(View.GONE);}
                if(malt == 5){actf_malt05.setText("");malt05.setVisibility(View.GONE);btn_subm_05.setVisibility(View.GONE);}
                if(malt == 6){actf_malt06.setText("");malt06.setVisibility(View.GONE);btn_subm_06.setVisibility(View.GONE);}
                malt -= 1;
                break;
            case 5:
                actf_malt05.setText(actf_malt06.getText());et_malt05g.setText(et_malt06g.getText());
                if(malt == 5){actf_malt05.setText("");malt05.setVisibility(View.GONE);btn_subm_05.setVisibility(View.GONE);}
                if(malt == 6){actf_malt06.setText("");malt06.setVisibility(View.GONE);btn_subm_06.setVisibility(View.GONE);}
                malt -= 1;
                break;
            case 6:
                malt06.setVisibility(View.GONE);btn_subm_06.setVisibility(View.GONE);
                break;
        }
        if(malt==1){btn_subm_01.setVisibility(View.GONE);}
    }
    public void sub_hop(int hopno){
        btn_addh.setVisibility(View.VISIBLE);
        switch (hopno){
            case 1:
                actf_hop01.setText(actf_hop02.getText());et_hop01a.setText(et_hop02a.getText());
                actf_hop02.setText(actf_hop03.getText());et_hop02a.setText(et_hop03a.getText());
                actf_hop03.setText(actf_hop04.getText());et_hop03a.setText(et_hop04a.getText());
                if(hop == 2){actf_hop02.setText("");hop02.setVisibility(View.GONE);}
                if(hop == 3){actf_hop03.setText("");hop03.setVisibility(View.GONE);}
                if(hop == 4){actf_hop04.setText("");hop04.setVisibility(View.GONE);}
                hop -= 1;
                break;
            case 2:
                actf_hop02.setText(actf_hop03.getText());et_hop02a.setText(et_hop03a.getText());
                actf_hop03.setText(actf_hop04.getText());et_hop03a.setText(et_hop04a.getText());
                if(hop == 2){actf_hop02.setText("");hop02.setVisibility(View.GONE);}
                if(hop == 3){actf_hop03.setText("");hop03.setVisibility(View.GONE);}
                if(hop == 4){actf_hop04.setText("");hop04.setVisibility(View.GONE);}
                hop -= 1;
                break;
            case 3:
                actf_hop03.setText(actf_hop04.getText());et_hop03a.setText(et_hop04a.getText());
                if(hop == 3){actf_malt05.setText("");hop03.setVisibility(View.GONE);}
                if(hop == 4){actf_malt06.setText("");hop04.setVisibility(View.GONE);}
                hop -= 1;
                break;
            case 4:
                hop04.setVisibility(View.GONE);
                break;
        }
        if(hop==1){btn_subh_01.setVisibility(View.GONE);}
    }
    public void sub_rast(int rastno){
        btn_addr.setVisibility(View.VISIBLE);
        switch (rastno){
            case 1:
                et_rast01.setText(et_rast02.getText());
                et_rast02.setText(et_rast03.getText());
                et_rast03.setText(et_rast04.getText());
                et_rast04.setText(et_rast05.getText());
                if(rast == 2){et_rast02.setText("");rast02.setVisibility(View.GONE);}
                if(rast == 3){et_rast03.setText("");rast03.setVisibility(View.GONE);}
                if(rast == 4){et_rast04.setText("");rast04.setVisibility(View.GONE);}
                if(rast == 5){et_rast05.setText("");rast05.setVisibility(View.GONE);}
                rast -= 1;
                break;
            case 2:
                et_rast02.setText(et_rast03.getText());
                et_rast03.setText(et_rast04.getText());
                et_rast04.setText(et_rast05.getText());
                if(rast == 2){et_rast02.setText("");rast02.setVisibility(View.GONE);}
                if(rast == 3){et_rast03.setText("");rast03.setVisibility(View.GONE);}
                if(rast == 4){et_rast04.setText("");rast04.setVisibility(View.GONE);}
                if(rast == 5){et_rast05.setText("");rast05.setVisibility(View.GONE);}
                rast -= 1;
                break;
            case 3:
                et_rast03.setText(et_rast04.getText());
                et_rast04.setText(et_rast05.getText());
                if(rast == 3){et_rast03.setText("");rast03.setVisibility(View.GONE);}
                if(rast == 4){et_rast04.setText("");rast04.setVisibility(View.GONE);}
                if(rast == 5){et_rast05.setText("");rast05.setVisibility(View.GONE);}
                rast -= 1;
                break;
            case 4:
                et_rast04.setText(et_rast05.getText());
                if(rast == 4){et_rast04.setText("");rast04.setVisibility(View.GONE);}
                if(rast == 5){et_rast05.setText("");rast05.setVisibility(View.GONE);}
                rast -= 1;
                break;
            case 5:
                rast05.setVisibility(View.GONE);
                break;
        }
        if(rast==1){btn_subr_01.setVisibility(View.GONE);}
    }

    private final TextWatcher watcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            print();}
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher EBCwatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //print();
            Log.d(TAG,"EBCwatcher");

            et_EBCm1min.setText("");et_EBCm1max.setText("");
            et_EBCm2min.setText("");et_EBCm2max.setText("");
            et_EBCm3min.setText("");et_EBCm3max.setText("");
            et_EBCm4min.setText("");et_EBCm4max.setText("");
            et_EBCm5min.setText("");et_EBCm5max.setText("");
            et_EBCm6min.setText("");et_EBCm6max.setText("");

            String M1check = actf_malt01.getText().toString();
            String M2check = actf_malt02.getText().toString();
            String M3check = actf_malt03.getText().toString();
            String M4check = actf_malt04.getText().toString();
            String M5check = actf_malt05.getText().toString();
            String M6check = actf_malt06.getText().toString();

            for(int n=0;n<maltlist.size();n++){
                if(M1check.equals(maltlist.get(n))){
                    et_EBCm1min.setText(maltlistEBCmin.get(n).toString());
                    et_EBCm1max.setText(maltlistEBCmax.get(n).toString());
                }
                if(M2check.equals(maltlist.get(n))){
                    et_EBCm2min.setText(maltlistEBCmin.get(n).toString());
                    et_EBCm2max.setText(maltlistEBCmax.get(n).toString());
                }
                if(M3check.equals(maltlist.get(n))){
                    et_EBCm3min.setText(maltlistEBCmin.get(n).toString());
                    et_EBCm3max.setText(maltlistEBCmax.get(n).toString());
                }
                if(M4check.equals(maltlist.get(n))){
                    et_EBCm4min.setText(maltlistEBCmin.get(n).toString());
                    et_EBCm4max.setText(maltlistEBCmax.get(n).toString());
                }
                if(M5check.equals(maltlist.get(n))){
                    et_EBCm5min.setText(maltlistEBCmin.get(n).toString());
                    et_EBCm5max.setText(maltlistEBCmax.get(n).toString());
                }
                if(M6check.equals(maltlist.get(n))){
                    et_EBCm6min.setText(maltlistEBCmin.get(n).toString());
                    et_EBCm6max.setText(maltlistEBCmax.get(n).toString());
                }
            }
            }
        public void afterTextChanged(Editable s) {}
    };

    public String rastlbl(EditText etIN, String base){
        int temp = 0;
        if(etIN.getText().length()>0){
            temp = Integer.valueOf(etIN.getText().toString());
        }

        if(temp > 52 && temp < 58){
            return "Eiweißrast";
        }if(temp > 60 && temp < 68){
            return "Maltoserast";
        }if(temp >= 68 && temp < 78){
            return "Verzuckerungs-\nrast";
        }if(temp > 78){
            return "ZU HEISS!!!";
        }else{
            return base;
        }
    }

    public void writeFile(String fname, List<String> mylist){
        try {
            //FileOutputStream myfile = openFileOutput(filename_malt, Context.MODE_APPEND);
            FileOutputStream myfile = openFileOutput(fname, Context.MODE_PRIVATE);//neue Datei
            OutputStreamWriter mywriter = new OutputStreamWriter(myfile);
            try {
                for(int n = 0;n<mylist.size();n++){
                    mywriter.write(mylist.get(n));
                    mywriter.write("\n");
                }
                mywriter.flush();
                mywriter.close();
            } catch (IOException e) {e.printStackTrace();}
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }
    public void resetfile(String fname,String[] myArray,List<String> mylist){

        mylist.clear();
        try {
            FileOutputStream myfile = openFileOutput(fname, Context.MODE_PRIVATE);//neue Datei
            OutputStreamWriter mywriter = new OutputStreamWriter(myfile);
            try {
                for(int n = 0;n<myArray.length;n++){
                    mywriter.write(myArray[n]);
                    mylist.add(myArray[n]);
                    mywriter.write("\n");
                }
                mywriter.flush();
                mywriter.close();
            } catch (IOException e) {e.printStackTrace();}
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }

    //Hier alle Felder aktualisieren
    public void print(){
        lbl_r1.setText(rastlbl(et_rast01,"Einmaischen"));
        lbl_r2.setText(rastlbl(et_rast02,"2. Rast\n"));
        lbl_r3.setText(rastlbl(et_rast03,"\n3. Rast"));
        lbl_r4.setText(rastlbl(et_rast03,"\n4. Rast"));
        lbl_r5.setText(rastlbl(et_rast03,"\n5. Rast"));
    }


    //region NOTUSED
       /*
    public void status(String statusmeldung, boolean onDisplay){
        Log.d(TAG,statusmeldung);

    }
    public String byteArrayToString(byte[] in) {
        //convert hashcode to hexcode
        char out[] = new char[in.length * 2];
        for (int i = 0; i < in.length; i++) {
            out[i * 2] = "0123456789ABCDEF".charAt((in[i] >> 4) & 15);
            out[i * 2 + 1] = "0123456789ABCDEF".charAt(in[i] & 15);
        }


        //convert hexcode to text
        // ALLE Hexcodes, die mit C beginnen löschen. (C2 C3)
        int newlength = out.length;
        String hexstring = new String(out);

        for (int i = 0; i < out.length / 2; i++) {
            if (out[i * 2] == 'C') {
                for (int j = 0; j < out.length - i * 2 - 2; j++) {
                    out[i * 2 + j] = out[i * 2 + j + 2];
                }
                out[out.length - 2] = '2';
                out[out.length - 1] = '0';
                //newlength -= 1;
            }
        }
        //ersetzte Hex durch zeichen aus tabelle
        char out2[] = new char[newlength / 2];
        for (int i = 0; i < newlength / 2; i++) {
            out2[i] = "                                 ! #$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[ ]^_'abcdefghijklmnopqrstuvwxyz{|}~     Ä                 Ö     Ü  ß    ä           °     ö     ü                                                                  X".charAt(Integer.parseInt(hexstring.substring(i * 2, i * 2 + 2), 16));
            // 0                                                                                                   1                                                                                                   2                                                      2
            // 0         1         2         3         4         5         6         7         8         9         0         1         2         3         4         5         6         7         8         9         0         1         2         3         4         5    5
            // 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345
            // 0               1               2               3               4               5               6               7               8               9               A               B               C               D               E               F
            // 0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF
        }
        return new String(out2);
    }

    public float Tconv(byte[] data) {
        try {
            return ((float) (Integer.parseInt(byteArrayToString(data)))) / 100;
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            return 0;
        }
    }
    public String TimeToString(float Sin){
        //HH:MM:SS
        String time = "";
        int secin = (int)Sin;
        int h = (secin - (secin % (60*60)))/(60*60);
        secin -= h*60*60;
        int m = (secin-(secin % 60))/60;
        secin -= m*60;
        int s = secin;

        if(h<10){time = time + "0";}
        time = time + String.valueOf(h)+":";
        if(m<10){time = time + "0";}
        time = time + String.valueOf(m)+":";
        if(s<10){time = time + "0";}
        time = time + String.valueOf(s);

        return time;
    }*/
       //endregion


    @Override
    public void onBeanDiscovered(Bean bean, int rssi) {}
    @Override
    public void onDiscoveryComplete() {}
    @Override
    public void onConnected() {}
    @Override
    public void onConnectionFailed() {}
    @Override
    public void onDisconnected() {}
    @Override
    public void onSerialMessageReceived(byte[] data) {}
    @Override
    public void onScratchValueChanged(ScratchBank bank, byte[] value) {}
    @Override
    public void onError(BeanError error) {}
    @Override
    public void onReadRemoteRssi(int rssi) {}
    @Override
    public void update(Observable o, Object arg) {}
}
