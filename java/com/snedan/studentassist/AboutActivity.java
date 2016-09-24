package com.snedan.studentassist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class AboutActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        final Typeface font = Typeface.createFromAsset(getAssets(),"fonts/appFont.ttf");

        TextView credits=(TextView)findViewById(R.id.textView8);
        TextView t1=(TextView)findViewById(R.id.textView3);
        TextView t2=(TextView)findViewById(R.id.textView4);
        TextView t3=(TextView)findViewById(R.id.textView5);
        TextView t4=(TextView)findViewById(R.id.textView6);

        t1.setTypeface(font);
        t2.setTypeface(font);
        t3.setTypeface(font);
        t4.setTypeface(font);
        credits.setTypeface(font);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/app/details?id=com.snedan.test"));
                if (!PlayStore(a)) {
                    Toast.makeText(getApplicationContext(), " PLEASE INSTALL PLAY STORE TO RATE OUR APP ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final String text = "\n\t App Concept: Vishal S. Robertson\n" +
                "\t\t\t\t\t\t\t\t\t\tImtiaz Khan\n" +
                "\t\t\t\t\t\t\t\t\t\tKevin Alvares\n" +
                "\t\t\t\t\t\t\t\t\t\tAlan Couto\n" +
                "\t\t\t\t\t\t\t\t\t\tAnuj Shetgaonkar\n" +
                "\t\t\t\t\t\t\t\t\t\tAnish Thali\n" +
                "\t\t\t\t\t\t\t\t\t\tKavish Narvekar\n" +
                "\t\t\t\t\t\t\t\t\t\tDikshay Aldonkar\n" +
                "\t\t\t\t\t\t\t\t\t\tTroy Conceicao\n" +
                "\t\t\t\t\t\t\t\t\t\tAlisha Lotlikar\n\n" +
                "\t App Name: Alrich de Sa\n\n" +
                "\t App Logo: Alan Couto\n\n" +
                "\t App Theme: Alan Couto\n" +
                "\t\t\t\t\t\t\t\t\tStephenie Robertson\n" +
                "\t\t\t\t\t\t\t\t\tKevin Alvares\n\n" +
                "\t About Page: Anuj Shetgaonkar\n\n" +
                "\t Developed By: Vishal S. Robertson\n\n" +
                "\t\t\t\t\tTHANK YOU FOR USING OUR APP";
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AboutActivity.this);
                builder1.setTitle("CREDITS");
                final TextView op = new TextView(AboutActivity.this);
                builder1.setView(op);
                op.setText(text);
                op.setTypeface(font);
                builder1.setIcon(R.drawable.miniic);
                AlertDialog b = builder1.create();
                b.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean PlayStore(Intent a)
    {
        try{
            startActivity(a);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }
}
