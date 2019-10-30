package eg.com.misrins.mic.micproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Details extends AppCompatActivity implements OnMapReadyCallback {
    DataBaseHelperClass obj;
    MapView mapView;
    GoogleMap map;
    DTO dto = new DTO();
    Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final ActionBar abar = getSupportActionBar();
        //abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));//line under the action bar
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        View viewActionBar = getLayoutInflater().inflate(R.layout.header_layout, null);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
         typeface=Typeface.createFromAsset(getAssets(), "CairoBold.ttf");
        textviewTitle.setTypeface(typeface);


        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        // abar.setIcon(R.color.transparent);
        abar.setHomeButtonEnabled(true);

        obj = new DataBaseHelperClass(this);
        final String getname = getIntent().getStringExtra("name");
        final String getaddress = getIntent().getStringExtra("address");
        final String getdegree = getIntent().getStringExtra("degree");

         dto = obj.getDetails(getname, getaddress);

        //System.out.println("dto.getName() : " + dto.getName());

        ImageView image = (ImageView) findViewById(R.id.image_details);
        TextView name = (TextView) findViewById(R.id.name);
        TextView degree = (TextView) findViewById(R.id.degree);
        TextView address = (TextView) findViewById(R.id.address);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView tkhsos = (TextView) findViewById(R.id.tkhsos);
        Button b = (Button) findViewById(R.id.googlemaplink) ;
        //Typeface typeface=Typeface.createFromAsset(getAssets(), "CairoBold.ttf");
        name.setTypeface(typeface);
        degree.setTypeface(typeface);
        address.setTypeface(typeface);
        phone.setTypeface(typeface);
        tkhsos.setTypeface(typeface);
        b.setTypeface(typeface);

        TextView reportlink = (TextView) findViewById(R.id.report);
        reportlink.setTypeface(typeface);

        // TextView google = (TextView) findViewById(R.id.google);
        String googleCode = dto.getGoogle_cd();
        String[]location;
        if(googleCode !=null) {
            location = googleCode.split(",");
            latitude = Double.parseDouble(location[0]);

            longitude= Double.parseDouble(location[1]);
            title = dto.getName();
        }
        //name.setText(dto.get());
        name.setText(dto.getName());
        degree.setText(getdegree);
        address.setText(dto.getAddress());
        tkhsos.setText(UtilClass.getTkhsosName( dto.getType_id()));
        final String typename= getIntent().getStringExtra("typename");
        if(UtilClass.getTkhsosName( dto.getType_id()).equals(""))
            textviewTitle.setText(typename);
        else
            textviewTitle.setText(typename +" - "+UtilClass.getTkhsosName( dto.getType_id()));
        //UtilClass utilClass = new UtilClass();
        phone.setText(UtilClass.phoneString( dto.getPhone()));
        //google.setText(dto.getName());

        switch (dto.getType_id()) {
            case 1:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
                image.setImageResource(R.drawable.doctor);
                break;
            case 2:
                image.setImageResource(R.drawable.hospital);
                break;
            case 3:
                image.setImageResource(R.drawable.xrays);
                break;
            case 4:
                image.setImageResource(R.drawable.blood);
                break;
            case 5:
                image.setImageResource(R.drawable.pharmacy);//عيادات تخصصية
                break;
            case 6:
                image.setImageResource(R.drawable.clinic);
                break;
            case 7:
                image.setImageResource(R.drawable.physiotherapist);
                break;
            case 8:
            case 81:
            case 82:
            case 83:
                image.setImageResource(R.drawable.centers);
                break;
            case 9:
                image.setImageResource(R.drawable.glass);
                break;
            case 10:
                image.setImageResource(R.drawable.wheelchair);
                break;/**/

        }

    }
    private Double latitude = null,  longitude=null;
    private GoogleMap mMap;
    String title="";
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if(latitude !=null){
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
        }
    }

    public void alert(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
     //   getMenuInflater().inflate(R.menu.main_menu, menus);
        return true;

    }


    /******Share Information******/
    public void sharedata(View view){
        String gen ="";
        //String uri = "http://maps.google.com/maps?saddr=" +latitude+","+longitude;
        String uri = "http://maps.google.com/?q=" +latitude+","+longitude;
        System.out.println("uri************* :"+uri);
        gen += dto.getName() +" - "+ dto.getAddress() +" - "+UtilClass.phoneString( dto.getPhone()) +" - العنوان على خريطة جوجل "+uri;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,gen);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }


    public void goGoogleMap(View view) {
        /*String uri = String.format( "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        this.startActivity(intent);*/
        String uri = "http://maps.google.com/?q=" +latitude+","+longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //startActivity(new Intent(this,SearchVew.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void report(View view) {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Details.this);
        final View mView = getLayoutInflater().inflate(R.layout.report_layout,null);

        final Spinner spinner = (Spinner) mView.findViewById(R.id.reportlist);

        TextView label = (TextView) mView.findViewById(R.id.chooseproblem);
        label.setTypeface(typeface);
        TextView report = (TextView) mView.findViewById(R.id.report);
        report.setTypeface(typeface);



        final List<String> spinnerArr = new ArrayList<String>();
        spinnerArr.add("إلغاء التعاقد");
        spinnerArr.add("المحافظة");
        spinnerArr.add("التخصص");
        spinnerArr.add("رقم الهاتف");
        spinnerArr.add("العنوان");
        spinnerArr.add("خريطة جوجل");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerArr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setReportMessage(spinnerArr.get(position));

                try {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    ((TextView) parent.getChildAt(0)).setTypeface(typeface);
                   // onTypechange(typesadapter.getItem(position));
                }catch (Exception e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }
        });

                mBuilder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener(){ // define the 'Cancel' button
                    public void onClick(DialogInterface dialog, int which) {
                         dialog.cancel();
                    }
                });
                mBuilder.setNeutralButton("إرسال", new DialogInterface.OnClickListener() { // define the 'Cancel' button
                    public void onClick(DialogInterface dialog, int which) {
                        //Either of the following two lines should work.
                        sendmail( );

                        dialog.cancel();
                        //dialog.dismiss();
                    }
                });

                mBuilder.setView(mView).show();


    }


    /*************************************/
    String selectedReport  ;
    public void setReportMessage(String msg){

        selectedReport = msg;
    }

    public void sendmail (){

        String msg =  "خطأ في : "+ selectedReport + ""
                + " - " + dto.getName();
                if(!UtilClass.getTkhsosName( dto.getType_id()).equals("") && UtilClass.getTkhsosName( dto.getType_id()) != null )
                    msg +=   " - " +UtilClass.getTkhsosName( dto.getType_id());
        if( dto.getDegree() != null )
            msg +=  " - " +dto.getDegree();
        if(   dto.getAddress() != null )
            msg +=  " - " + dto.getAddress();
        if(   dto.getPhone() != null )
            msg +=  " - " +UtilClass.phoneString(dto.getPhone());

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, "خطأ في بيانات الشبكة الطبية بطبيق المحمول");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setData(Uri.parse("mailto:shemy1@misrins.com.eg")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);


    }

}


