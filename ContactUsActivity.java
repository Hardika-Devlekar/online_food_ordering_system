package com.example.sizzlingbites.ui.user.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sizzlingbites.R;

public class ContactUsActivity extends AppCompatActivity {
    TextView con_email, con_website, con_phone, con_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setUpToolbar();

        con_email = findViewById(R.id.contact_email);
        con_website = findViewById(R.id.contact_website);
        con_phone = findViewById(R.id.contact_phone);
        con_location = findViewById(R.id.contact_location);

        con_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open email using client intent
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "sizzlingbites@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send mail"));
            }
        });

        con_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://sizzlingbites.com/");
            }
        });

        con_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open dial pad using client intent
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+917208345436"));
                startActivity(callIntent);
            }
        });

        con_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent that will load a map of Santacruz, Mumbai
                Uri gmmIntentUri = Uri.parse("geo:19.0787, 72.85");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    private void gotoUrl(String s) {
        //Open url using client intent
        Uri websiteUri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, websiteUri));
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.contact_toolbar);
        setSupportActionBar(toolbar);
    }
}