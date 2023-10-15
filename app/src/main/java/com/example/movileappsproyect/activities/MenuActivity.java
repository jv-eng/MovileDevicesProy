package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Space;
import android.widget.Toast;

import com.example.movileappsproyect.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setBtnLogic();
    }

    private void setBtnLogic() {
        Button btnSpaceStationList = findViewById(R.id.menu_list_space_station);
        btnSpaceStationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MenuActivity.this, SpaceStationListActivity.class);
                startActivity(i);
            }
        });


        Button btnDayPicture = findViewById(R.id.menu_day_pict);
        btnDayPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.internetFlag) {
                    Intent i  = new Intent(MenuActivity.this, PictureDayActivity.class);
                    startActivity(i);
                } else { Toast.makeText(MenuActivity.this, R.string.dayPictNoInternet, Toast.LENGTH_SHORT).show(); }
            }
        });

        Button btnISSDistance = findViewById(R.id.menu_distance);
        btnISSDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this, "No implementado", Toast.LENGTH_SHORT).show();
                Intent i  = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}