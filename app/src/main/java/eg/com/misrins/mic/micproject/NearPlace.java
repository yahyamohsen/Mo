package eg.com.misrins.mic.micproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NearPlace extends AppCompatActivity {
    Typeface typeface;

    UtilClass util = new UtilClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_place);

        typeface=Typeface.createFromAsset(getAssets(), "CairoBold.ttf");
        final ActionBar abar = getSupportActionBar();
        //abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));//line under the action bar
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        View viewActionBar = getLayoutInflater().inflate(R.layout.header_layout, null);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        // Typeface typeface=Typeface.createFromAsset(getAssets(), "CairoBold.ttf");
        textviewTitle.setTypeface(typeface);

        textviewTitle.setText("الشبكة الطبية");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        // abar.setIcon(R.color.transparent);
        abar.setHomeButtonEnabled(true);

        DataBaseHelperClass obj = new DataBaseHelperClass(this);

        TextView maintypeText = (TextView) findViewById(R.id.maintype);
        TextView maintitleText = (TextView) findViewById(R.id.maintitle);
        Button b = (Button) findViewById(R.id.search);

        maintypeText.setTypeface(typeface);
        maintitleText.setTypeface(typeface);
        b.setTypeface(typeface);
        /*************Governorate DropDown List********************
        final Spinner Governoratespinner = (Spinner) findViewById(R.id.governorate);
        List<String> GovernoratespinnerArr= obj.getGovernorate();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,GovernoratespinnerArr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Governoratespinner.setAdapter(adapter);*/



        /************* Types DropDown List*********************/



        final Spinner typespinner = (Spinner) findViewById(R.id.type);
        final ArrayAdapter<String> typesadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,UtilClass.types);
        typesadapter.setDropDownViewResource(R.layout.spinner_item);
        typespinner.setAdapter(typesadapter);
       // textviewTitle.setTypeface(typeface);




      typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //txt.setText("Index = "+position+",Text = "+spinnerArr.get(position));
                //Integer ids = obj.getTypeID( name);
               // System.out.println("adapter.getItem(position):"+typesadapter.getItem(position));

                //typesadapter.getItem(position).
                try {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    ((TextView) parent.getChildAt(0)).setTypeface(typeface);
                    onTypechange(typesadapter.getItem(position));
                }catch (Exception e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typespinner.setSelection(0);
            }
        });


        }

        void onTypechange(String val){
           final Typeface typeface=Typeface.createFromAsset(getAssets(), "CairoBold.ttf");

            String[] subtypes1 ={"مراكز جهاز هضمي متخصصة","مراكز قلب متخصصة","مراكز ومستشفيات العيون"};
            final Spinner subtypespinner = (Spinner) findViewById(R.id.subtype);
            TextView subtypeText = (TextView) findViewById(R.id.subtypetext);
            LinearLayout linearLayout = (LinearLayout)  findViewById(R.id.subspinnerLayer);
            subtypeText.setTypeface(typeface);

            //final TextView subtext = (TextView) findViewById(R.id.subtype);

            if(val.equals("أطباء")){
                ArrayList<String>var = UtilClass.getSubDoctorslist();
               /* subtypespinner.setVisibility(View.VISIBLE);
                subtypeText.setVisibility(View.VISIBLE);*/
                linearLayout.setVisibility(View.VISIBLE);
                final ArrayAdapter<String> subtypesadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,var);
                subtypesadapter.setDropDownViewResource(R.layout.spinner_item);
                subtypespinner.setAdapter(subtypesadapter);
                subtypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                        ((TextView) parent.getChildAt(0)).setTypeface(typeface);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        subtypespinner.setSelection(0);
                    }
                });/**/
            }
            else if(val.equals("شركات اجهزة تعويضية وسمعية")){
                //subtypespinner.setVisibility(View.VISIBLE);
                //subtypeText.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                final ArrayAdapter<String> subtypesadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,subtypes1);
                subtypesadapter.setDropDownViewResource(R.layout.spinner_item);
                subtypespinner.setAdapter(subtypesadapter);
                subtypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                        ((TextView) parent.getChildAt(0)).setTypeface(typeface);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        subtypespinner.setSelection(0);
                    }
                });
            }else{
                linearLayout.setVisibility(View.GONE);
             //   subtypespinner.setVisibility(View.GONE);
             //   subtypeText.setVisibility(View.GONE);
            }

        }


    public void searchfun(View view) {

        Spinner typespinner = (Spinner) findViewById(R.id.type);
        Spinner subtypespinner = (Spinner) findViewById(R.id.subtype);

        String typest = typespinner.getSelectedItem().toString();
        String subtypest = "";
        Integer id = 0;
        // check if has subtype or not and get id for search near places to this types
        //Toast.makeText(getApplicationContext(),typest+" - "+id+" - "+subtypest,Toast.LENGTH_LONG).show();
        if(typest.equals("أطباء") ) {
            subtypest = subtypespinner.getSelectedItem().toString();
           id =  UtilClass.getSubDrID(subtypest);
        }else if( typest.equals("شركات اجهزة تعويضية وسمعية")){
            subtypest = subtypespinner.getSelectedItem().toString();
           id = UtilClass.getsubhostID(subtypest);

        }else if( typest.equals("اختار")) {
            id = -1;
        }
        else if( typest.equals("اختر التخصص")) {
            id = -2;
        }
        else if( typest.equals("اختر القسم")) {
            id = -3;
        }
        else{
            id = UtilClass.getTypesId(typest); /***other types **/
        }



        if(id == -1) {
            Toast.makeText(getApplicationContext(),"قم باختيار التخصص",Toast.LENGTH_LONG).show();
        }
        else if(id == -2) {
            Toast.makeText(getApplicationContext(),"قم باختيار التخصص الفرعي",Toast.LENGTH_LONG).show();
        }
        else if(id == -3) {
            Toast.makeText(getApplicationContext(),"قم باختيار القسم",Toast.LENGTH_LONG).show();
        }
        else{
            //if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //Intent back = new Intent(this,NearPlace.class);
               // startActivity(back);

               /* Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(in);
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
               // return;
            }else {
                Intent myintent = new Intent(this, MapsActivity.class);

                myintent.putExtra("typeid", String.valueOf(id));
                startActivity(myintent);
            }*/
            LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch(Exception ex) {}

            final Context context=this;
            //check for GPS
            if(!gps_enabled && !network_enabled) {
                Toast.makeText(getApplicationContext(),"يجب تشغيل خاصية GPS",Toast.LENGTH_LONG).show();
                Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(in);
            }else{
                //check for internet connection

                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network

                    Intent myintent = new Intent(this, MapsActivity.class);
                    myintent.putExtra("typeid", String.valueOf(id));
                    startActivity(myintent);
                }
                else
                    Toast.makeText(getApplicationContext(),"يجب الاتصال بالانترنت",Toast.LENGTH_LONG).show();


            }
        }
        //  Toast.makeText(getApplicationContext(),typest+"   -   "+id+"   -   "+subtypest,Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //startActivity(new Intent(this,MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

