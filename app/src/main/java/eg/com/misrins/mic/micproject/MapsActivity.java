package eg.com.misrins.mic.micproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
     Double latitude = 0.0;
     Double longitude = 0.0;
    DataBaseHelperClass obj ;
    ArrayList<DTO> resultList;
    ArrayList<DTO> nearplacestList;
    LocationManager locationManager;
    String country ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //get Typeid from NearPlace Activity
        Integer typeid = Integer.parseInt( getIntent().getStringExtra("typeid")) ;
        //Log.i("typeid : ",typeid.toString());

        //Get Data where typeid
        obj = new DataBaseHelperClass(this);
        resultList = obj.getDataListType(typeid,-1);
        Integer i = resultList.size();
        //Log.i("------resultList : " , i.toString());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
           /* Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(in);*/

            //ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                try {


                Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                }catch (Exception e){}
            }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                try {
                    Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }catch (Exception e){}
            }else{
                Toast.makeText(getApplicationContext(),"يجب بسماحية الوصل لمكانك أولاً" ,Toast.LENGTH_LONG).show();
               // Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //startActivity(in);/**/
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

               // Intent back = new Intent(this,NearPlace.class);
                //startActivity(back);
            }
        }
       if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                     latitude = location.getLatitude();
                     longitude= location.getLongitude();
                    //setval(latitude,longitude);
                   // Toast.makeText(getApplicationContext(),"NETWORK_PROVIDER" ,Toast.LENGTH_LONG).show();
                    LatLng latLng = new LatLng(latitude,longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
                        country =addressList.get(0).getLocality()+ " , "+addressList.get(0).getCountryName();

                        mMap.clear();
                        onMapReady(mMap);
                        mMap.addMarker(new MarkerOptions().position(latLng).title(country)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.youhere));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
            });
        } else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 0, new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                     latitude = location.getLatitude();
                     longitude= location.getLongitude();
                   // setval(latitude,longitude);
                    LatLng latLng = new LatLng(latitude,longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    //Toast.makeText(getApplicationContext(),"GPS_PROVIDER" ,Toast.LENGTH_LONG).show();

                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
                        country =addressList.get(0).getLocality()+ " , "+addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(country)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.youhere));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

        //Toast.makeText(getApplicationContext(),latitude+" -onCreate- "+longitude ,Toast.LENGTH_LONG).show();
/* */
    }
   /* public void setval(Double la,Double lo){
        this.latitude = la;
        this.longitude = lo;
        Toast.makeText(getApplicationContext(),latitude+" -setval- "+longitude ,Toast.LENGTH_LONG).show();

    }*/

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        // System.out.println("Latitude :"+Latitude);
        // System.out.println("Longitude :"+Longitude);

        /*if(longitude== 0.0){
            Intent back = new Intent(this,NearPlace.class);
            startActivity(back);
        }*/
        Location current = new  Location("");
        current.setLongitude(longitude);
        current.setLatitude(latitude);

        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));

        //current Location
        LatLng location = new LatLng(latitude,longitude );
        mMap.addMarker(new MarkerOptions().position(location).title(country)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.youhere));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
       // Toast.makeText(getApplicationContext(),latitude+" -onMapReady- "+longitude ,Toast.LENGTH_LONG).show();
        /************work with nearest places to current location******************/

        String googleCode = "";
        String[]location1;
        Location tempLocation;
        for(int count = 0 ;  count<resultList.size();count++) {
            // System.out.println("*************** title********* : "+resultList.get(count).getName());
            googleCode = resultList.get(count).getGoogle_cd() ;

            if (googleCode != null ) {

                String title ="";
                /*if(resultList.get(count).getName() !=null) {
                    title = resultList.get(count).getName();// dto.getName();
                    //System.out.println("*************** title********* : " + title);
                }*/
                resultList.get(count).setName(resultList.get(count).getName());
                location1 = googleCode.split(",");

                tempLocation = new Location("");
                tempLocation.setLatitude(Double.parseDouble(location1[0]));
                tempLocation.setLongitude(Double.parseDouble(location1[1]));
                Float  distance = current.distanceTo(tempLocation);
                resultList.get(count).setDistance(distance);
                //System.out.println("*************** distance********* : "+distance);
                resultList.get(count).setLocation(new LatLng(Double.parseDouble(location1[0]), Double.parseDouble(location1[1])));
                //mMap.addMarker(new MarkerOptions().position(resultList.get(count).getLocation()).title(title));

            }else{
                resultList.get(count).setDistance(0.0F);
                // System.out.println("/////////////////// Doesn't have  location : "+resultList.get(count).getName());
            }
        }
        //*** sort resultList by Distance
        Collections.sort(resultList, new MyComparator());

        int places = 0;
        // print the Nearest Places on the Map
        for(int count =0;resultList.size()>count;count++) {
            System.out.println("Distance : "+resultList.get(count).getDistance());
            if(resultList.get(count).getDistance()!=0 &&resultList.get(count).getDistance() <= 5000.0)
            {

                mMap.addMarker(new MarkerOptions().position(resultList.get(count).getLocation()).title(resultList.get(count).getName()+" - "+ resultList.get(count).getPhone()));
                places++;
            }else{
               // System.out.println("skipped Distance:  "+resultList.get(count).getDistance());
               // Sy000stem.out.println("skipped Name       : "+resultList.get(count).getName());
            }

        }
       /* for (DTO dto : resultList) {
            System.out.println(dto.getDistance() + ": " + dto.getName() + " points");
        }*/

    }


}
class MyComparator implements Comparator<DTO> {
    @Override
    public int compare(DTO o1, DTO o2) {
       /* if(o1.getDistance()!=null || o2.getDistance()!=null)
            return 0;*/
        if (o1.getDistance() > o2.getDistance()) {
            return 1;
        } else if (o1.getDistance() < o2.getDistance()) {
            return -1;
        }
        return 0;
    }}