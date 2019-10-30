package eg.com.misrins.mic.micproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar abar = getSupportActionBar();
        //abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.header_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("الشبكة الطبية");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
       // abar.setDisplayHomeAsUpEnabled(true);
       // abar.setIcon(R.color.transparent);
        abar.setHomeButtonEnabled(true);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "CairoBold.ttf");
        textviewTitle.setTypeface(typeface);
/*
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.header_layout);
        LayoutInflater linflater  =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;

        View view = linflater.inflate(R.layout.header_layout, null);

        TextView title = (TextView) view.findViewById(R.id.mytext);
        title.setText("TEESSS");*/
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.logomic));

        final ArrayList<DTO> mylist = new ArrayList<>();

        //  DataBaseHelperClass obj = new DataBaseHelperClass(this);
        //  mylist = obj.fetchData(this);
        mylist.add(new DTO(11, "بحث باقرب مركز", R.drawable.place));
        mylist.add(new DTO(1, "أطباء", R.drawable.doctor));
        mylist.add(new DTO(2, "مستشفيات", R.drawable.hospital));
        mylist.add(new DTO(3, "مراكز أشعة", R.drawable.xrays));
        mylist.add(new DTO(4, "معامل تحاليل", R.drawable.blood));
        mylist.add(new DTO(5, "صيدليات", R.drawable.pharmacy));
        mylist.add(new DTO(6, "عيادات تخصصية", R.drawable.clinic));//
        mylist.add(new DTO(7, "علاج طبيعي وتأهيل", R.drawable.physiotherapist));//
        mylist.add(new DTO(8, "مستشفيات ومراكز متخصصة", R.drawable.centers));//
        mylist.add(new DTO(9, "مراكز بصريات", R.drawable.glass));
        mylist.add(new DTO(10, "شركات اجهزة تعويضية وسمعية", R.drawable.wheelchair));
        //System.out.println("sizeeeeeeeeee: "+mylist.size());
        final ListViewAdapter adapter = new ListViewAdapter(this, mylist, "maingridview");

        final ListView list = (ListView) findViewById(R.id.main_gridview);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* TextView name= (TextView) view.findViewById(R.id.name);
                TextView desc= (TextView) view.findViewById(R.id.desc);*/
              /*  Toast.makeText(getApplicationContext(),name.getText(),Toast.LENGTH_LONG).show();
                mylist.add(new DTO("Yahya","Good Superman :)"));
                mylist.add(new DTO("Asmaa","Good Superman :)"));*/
                // adapter.notifyDataSetChanged();
                //list.setAdapter(adapter);

                TextView name = (TextView) view.findViewById(R.id.home_title);
                TextView typid = (TextView) view.findViewById(R.id.typeids);
                redirect(name.getText().toString(), typid.getText().toString());

            }
        });


    }

    public void redirect(String name, String typeid) {


        if(String.valueOf(typeid).equals("11")) {
            Intent myintent = new Intent(this, NearPlace.class);
            startActivity(myintent);
        }else {
            Intent myintent = new Intent(this, SearchVew.class);
            myintent.putExtra("name", name);
            myintent.putExtra("typeid", String.valueOf(typeid));
            startActivity(myintent);
        }
    }

    public void alert(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        //getMenuInflater().inflate(R.menu.main_menu, menus);
        return true;

    }


}


