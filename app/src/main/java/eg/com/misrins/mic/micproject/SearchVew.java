package eg.com.misrins.mic.micproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchVew extends AppCompatActivity {
    final Context context =this;
    static ArrayList<DTO> allDataList;
    DataBaseHelperClass obj ;
    Integer typeID;
    Integer govID = 1;
    UtilClass utilClass =new UtilClass();
     static ArrayList autoList;
    String typename="";
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vew);

        typename= getIntent().getStringExtra("name");
        String typeid =  getIntent().getStringExtra("typeid") ;
        typeID = Integer.parseInt(typeid);

        typeface=Typeface.createFromAsset(getAssets(), "CairoBold.ttf");
        obj =  new DataBaseHelperClass(this);

        // Settings for App Title
        final ActionBar abar = getSupportActionBar();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        View viewActionBar = getLayoutInflater().inflate(R.layout.header_layout, null);
        final AutoCompleteTextView   autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);


        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        TextView govlabel = (TextView) findViewById(R.id.govlabel);
        TextView namelabel = (TextView) findViewById(R.id.namelabel);
        TextView typelabel = (TextView) findViewById(R.id.typelabel);
        TextView subtext = (TextView) findViewById(R.id.typelabel);

        govlabel.setTypeface(typeface);
        namelabel.setTypeface(typeface);
        typelabel.setTypeface(typeface);
        subtext.setTypeface(typeface);
        textviewTitle.setTypeface(typeface);
        autoCompleteTextView.setTypeface(typeface);

        textviewTitle.setText("الشبكة الطبية - "+typename);

        //check for Doctors and hospitals / centers
        if(typeID==1 ||typeID ==8){

            /*************DropDown List for section Spinner*********************/
            final Spinner typespinner = (Spinner) findViewById(R.id.subspinner);
            ArrayList<String> typespinnerArr = new ArrayList<>();

            if(typeID==1) {
                typespinnerArr = utilClass.getSubDoctorslist();
            }else if(typeID==8) {
                typespinnerArr = utilClass.getsubhostListData();
                subtext.setText("القسم");
            }

            // Create Takhasos Spinner
            final ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,typespinnerArr);

            typeadapter.setDropDownViewResource(R.layout.spinner_item);

            typespinner.setAdapter(typeadapter);
            typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    ((TextView) parent.getChildAt(0)).setTypeface(typeface);
                    }catch (Exception e){

                    }
                    if(!typeadapter.getItem(position).equals("اختر التخصص")&&!typeadapter.getItem(position).equals("اختر القسم") ) {
                        onTypeChange(typeadapter.getItem(position));
                        autoCompleteTextView.setText("");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //typespinner.setSelection(0);
                }
            }); /**/
            //System.out.println("typeID,govID : "+typeID+" : "+govID);
            allDataList =  obj.getSubListType(typeID,govID);// get data filtered by typeID  for 1 cairo
            //System.out.println("allDataList: "+allDataList.size());
            // onupdateList();
        }else{
            TextView tx = (TextView) findViewById(R.id.typelabel);
            Spinner sp = (Spinner) findViewById(R.id.subspinner);
            LinearLayout l = (LinearLayout) findViewById(R.id.subspinnerlayout) ;
            tx.setVisibility(View.GONE);
            sp.setVisibility(View.GONE);
            l.setVisibility(View.GONE);

            tx.setTypeface(typeface);


            allDataList =  obj.getDataListType(typeID,govID);// get data filtered by typeID  for 1 cairo
            //onupdateList();
        }/**/

        /*********DataList********/

        final ListViewAdapter adapterlist = new ListViewAdapter(this,allDataList,"listview");

        ListView list = (ListView) findViewById(R.id.resultlistview);
        list.setAdapter(adapterlist);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView name = (TextView) view.findViewById(R.id.name);
                TextView degree = (TextView) view.findViewById(R.id.degree);
                TextView address = (TextView) view.findViewById(R.id.address);

                redirect(name.getText().toString(),address.getText().toString(),degree.getText().toString());

            }
        });/**/

        /*************DropDown List*********************/
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> spinnerArr= obj.getGovernorate();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,spinnerArr);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTypeface(typeface);
                }catch (Exception e){}
                onGovChange(adapter.getItem(position));
                autoCompleteTextView.setText("");
                onupdateList();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }
        });

        /*AutoComplete*/

        autoList = getStringlist(allDataList);
        final ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,autoList);
        autoCompleteTextView.setAdapter(adapterAuto);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onAutoCompChange(adapterAuto.getItem(position));
            }
        });
       // autoCompleteTextView.on(1);


    }

    public void redirect(String name,String address,String degree ){

        Intent myintent = new Intent(this,Details.class);
        myintent.putExtra("name",name);
        myintent.putExtra("address",address);
        myintent.putExtra("degree",degree);
        myintent.putExtra("typename",typename);

        startActivity(myintent);
    }

    /// get list of Strings for autocomplete to not dublicated
    public ArrayList<String> getStringlist(ArrayList<DTO> listDto){
        ArrayList<String> newList= new ArrayList<String>();
        for(int i=0;i<listDto.size();i++)
        {
            if(!newList.contains(listDto.get(i).getName()))
                newList.add(listDto.get(i).getName());
        }
        return newList;
    }

    // on change governorate dropdownlist
    public void onGovChange(String govname){

        govID = obj.getGovID(govname);
        // System.out.println("govname"+govname+" ,govID "+govID);
        if(typeID==1 ||typeID==8) {
            allDataList = obj.getSubListType(typeID, govID);
            final Spinner spinner = (Spinner) findViewById(R.id.subspinner);
            spinner.setSelection(0);
        }
        else
            allDataList =  obj.getDataListType(typeID,govID);
        AutoCompleteTextView   autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setText("");
        ListViewAdapter adapterlist = new ListViewAdapter(this,allDataList,"listview");

        ListView list = (ListView) findViewById(R.id.resultlistview);
        list.setAdapter(adapterlist);

        /********change aucomplete list*********/
        // AutoCompleteTextView   autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

        autoList = getStringlist(allDataList);
        final ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,autoList);
        autoCompleteTextView.setAdapter(adapterAuto);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onAutoCompChange(adapterAuto.getItem(position));
            }
        });

    }

    // on change governorate dropdownlist
    public void onTypeChange(String Typename){
        System.out.println("You Entered OntypeChange");
        Integer typeInt=0;
        if(typeID==1)
            typeInt = utilClass.getSubDrID(Typename);
        if(typeID==8)
            typeInt = utilClass.getsubhostID(Typename);
       // System.out.println("Typename"+Typename+" ,typeInt "+typeInt);


        allDataList =  obj.getDataListType(typeInt,govID);


        ListViewAdapter adapterlist = new ListViewAdapter(this,allDataList,"listview");

        ListView list = (ListView) findViewById(R.id.resultlistview);
        list.setAdapter(adapterlist);

        /********change aucomplete list*********/
        final AutoCompleteTextView   autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

        autoList = getStringlist(allDataList);
        final ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,autoList);
        autoCompleteTextView.setAdapter(adapterAuto);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               onAutoCompChange(adapterAuto.getItem(position));
            }
        });
    }

    // put put Data as String in arrayList for autocomplete
    public void onupdateList(){
        autoList = getStringlist(allDataList);
    }
    // on change autocomplete get matched names
    public void onAutoCompChange(String name){
        // System.out.println("Entered to onAutoCompChange");
       // AutoCompleteTextView   autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

      /*  if(TextUtils.isEmpty(autoCompleteTextView.getText()))
            Toast.makeText(getApplicationContext(),"EMPTY",Toast.LENGTH_LONG).show();*/
        ArrayList<DTO> newlists = new ArrayList<>();
        // onupdateList();
        for (int i=0 ;i <allDataList.size();i++){
            if(allDataList.get(i).getName().contains(name)){
                newlists.add(allDataList.get(i));
            }
        }
        final ListViewAdapter adapterlist = new ListViewAdapter(this,newlists,"listview");

        ListView list = (ListView) findViewById(R.id.resultlistview);
        list.setAdapter(adapterlist);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        //getMenuInflater().inflate(R.menu.main_menu, menus);
        return true;

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


}
