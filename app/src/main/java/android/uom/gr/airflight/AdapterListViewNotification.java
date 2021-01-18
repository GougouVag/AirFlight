package android.uom.gr.airflight;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by LamPrOs on 10/1/2017.
 */

public class AdapterListViewNotification extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    private ArrayList<ItemForNotification> itens;




    private ProgressDialog pd;
    private ProgressBar pb;


    public AdapterListViewNotification(Context context, ArrayList<ItemForNotification> itens) {

        this.itens = itens;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }


    public int getCount() {
        return itens.size();
    }


    public ItemForNotification getItem(int position) {
        return itens.get(position);
    }


    public long getItemId(final int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        final ItemForNotification item = itens.get(position);
        final AdapterListViewNotification.MyViewHolder holder;

        if (view == null) {

            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.notification_adapter, null);
            holder = new AdapterListViewNotification.MyViewHolder();

            holder.date = (TextView) view.findViewById(R.id.dateID);
            holder.notificationText= (TextView) view.findViewById(R.id.notificationTextID);



            view.setTag(holder);

        }else{


            holder = (AdapterListViewNotification.MyViewHolder) view.getTag();

        }

        final ItemForNotification result = getItem(position);

        holder.date.setText(item.getDate());
        holder.notificationText.setText(item.getNotificationText());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"view", Toast.LENGTH_LONG).show();

            }
        });


        return view;
    }


    private static class MyViewHolder {

        private TextView date;
        private TextView  notificationText;



    }




}
