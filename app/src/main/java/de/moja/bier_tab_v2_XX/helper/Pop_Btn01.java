package de.moja.bier_tab_v2_XX.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import de.moja.bier_tab_v2_XX.main.Protokoll;
import de.moja.bier_tab_v2_XX.R;

/**
 * Created by MPWH on 13.08.2017.
 */

public class Pop_Btn01 extends Activity {
    private Button choice01,choice02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_1_layout);

        choice01 = (Button) findViewById(R.id.btn_popup1_choice1);
        choice02 = (Button) findViewById(R.id.btn_popup1_choice2);

        choice01.setText("neues Rezept erstellen");
        choice02.setText("Rezeptdatenbank");

        choice01.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("Popupbuttn01: ","pressed");
                Log.d("BTN1: ","LOAD_ btn REZEPTE");
                startActivity(new Intent(Pop_Btn01.this,Protokoll.class));
                finish();
            }
        });
        choice02.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("Popupbuttn02: ","pressed");
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width =dm.widthPixels;
        int heigtt = dm.heightPixels;

        getWindow().setLayout(250,250);

    }
}
