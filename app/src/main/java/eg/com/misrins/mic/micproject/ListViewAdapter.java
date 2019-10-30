package eg.com.misrins.mic.micproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MIC on 27/04/2017.
 */


class ListViewAdapter extends BaseAdapter {
    ArrayList<DTO> items = new ArrayList<DTO>();
    Activity context;
    String method="";

    ListViewAdapter(Activity context, ArrayList<DTO> items,String method) {
        this.items= items;
        this.context = context;
        this.method = method;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "CairoBold.ttf");

        LayoutInflater linflater  =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        View view =null;

        if(method.equals("maingridview")) {
            view = linflater.inflate(R.layout.gridview_layout, null);

            TextView title = (TextView) view.findViewById(R.id.home_title);
            TextView typidsss = (TextView) view.findViewById(R.id.typeids);
            ImageView img = (ImageView) view.findViewById(R.id.home_img);
            title.setTypeface(typeface);


            title.setText(items.get(position).getName());
            //System.out.println("items.get(position).getType_id() :"+items.get(position).getType_id());
            typidsss.setText(String.valueOf(items.get(position).getType_id()));
            img.setImageResource(items.get(position).getImage());

        }else if(method.equals("listview")) {
            view = linflater.inflate(R.layout.listview_layout, null);

            TextView name = (TextView) view.findViewById(R.id.name);
            TextView degree = (TextView) view.findViewById(R.id.degree);
            TextView address = (TextView) view.findViewById(R.id.address);
            TextView phone = (TextView) view.findViewById(R.id.phone);
            TextView typid = (TextView) view.findViewById(R.id.typeids);

            name.setTypeface(typeface);
            degree.setTypeface(typeface);
            address.setTypeface(typeface);
            phone.setTypeface(typeface);
            // TextView govid = (TextView) view.findViewById(R.id.govid);
            //TextView googlecd = (TextView) view.findViewById(R.id.googlecd);
           // ImageView img = (ImageView) view.findViewById(R.id.home_img);

            name.setText(items.get(position).getName());
            degree.setText(items.get(position).getDegree());
            address.setText(items.get(position).getAddress());
            phone.setText(UtilClass.phoneString(items.get(position).getPhone()));
            typid.setText(String.valueOf(items.get(position).getType_id()));
           // govid.setText(String.valueOf(items.get(position).getGov_id()));
            //googlecd.setText((items.get(position).getGoogle_cd()));
            //img.setImageResource(items.get(position).getImage());
        }

        return view;
    }
}

