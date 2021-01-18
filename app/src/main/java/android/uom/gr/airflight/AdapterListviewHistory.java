package android.uom.gr.airflight;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by LamPrOs on 10/1/2017.
 */

public class AdapterListviewHistory  extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    private ArrayList<ItemForHistory> itens;




    private ProgressDialog pd;
    private ProgressBar pb;


    public AdapterListviewHistory(Context context, ArrayList<ItemForHistory> itens) {

        this.itens = itens;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }


    public int getCount() {
        return itens.size();
    }


    public ItemForHistory getItem(int position) {
        return itens.get(position);
    }


    public long getItemId(final int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        final ItemForHistory item = itens.get(position);
        final AdapterListviewHistory.MyViewHolder holder;

        if (view == null) {

            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.history_adapter_list, null);
            holder = new AdapterListviewHistory.MyViewHolder();

            holder.proorismo = (TextView) view.findViewById(R.id.proorismoID);
            holder.imerominia= (TextView) view.findViewById(R.id.imerominiaID);
            holder.from= (TextView) view.findViewById(R.id.fromID);
            holder.delete = (ImageView) view.findViewById(R.id.deleteID);


            view.setTag(holder);

        }else{


            holder = (AdapterListviewHistory.MyViewHolder) view.getTag();

        }

        final ItemForHistory result = getItem(position);

        holder.proorismo.setText(item.getTo());
        holder.imerominia.setText(item.getDepart_day()+"->"+item.getArrival_day());
        holder.from.setText(item.getFrom());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,"delete", Toast.LENGTH_LONG).show();
                MyDBHistorySearch db = new  MyDBHistorySearch(context);
                Log.d("Delete: ", "Deleting ..");
                db.deleteContact(getItem(position));
                Intent intent = new Intent(context, context.getClass());
                context.startActivity(intent);
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context,"view", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Details.class);

                intent.putExtra("from", getItem(position).getFrom());
                intent.putExtra("to", getItem(position).getTo());
                intent.putExtra("adults", getItem(position).getAdults_number());
                intent.putExtra("children", getItem(position).getChildren_number());
                intent.putExtra("infants",getItem(position).getInfants());
                intent.putExtra("way", getItem(position).getWay_is());
                intent.putExtra("depart", getItem(position).getDepart_day());
                intent.putExtra("arival", getItem(position).getArrival_day());
                intent.putExtra("SaveInDB", "dontSave");

                context.startActivity(intent);


            }
        });


        return view;
    }


    private static class MyViewHolder {

        private TextView proorismo ;
        private TextView  imerominia;
        private TextView  from;
        private ImageView delete;


    }



}