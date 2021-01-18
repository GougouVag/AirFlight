package android.uom.gr.airflight;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by LamPrOs on 10/1/2017.
 */

public class AdapterListViewFlightDetails extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    private ArrayList<ItemForFlightDetails> itens;




    private ProgressDialog pd;
    private ProgressBar pb;


    public AdapterListViewFlightDetails(Context context, ArrayList<ItemForFlightDetails> itens) {

        this.itens = itens;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }


    public int getCount() {
        return itens.size();
    }


    public ItemForFlightDetails getItem(int position) {
        return itens.get(position);
    }


    public long getItemId(final int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        final ItemForFlightDetails item = itens.get(position);
        final MyViewHolder holder;

        if (view == null) {
//
//            private String timeDepartGo ;
//            private String  airportDepartGo;
//            private String  timeArivalGo;
//            private String  airportArivalGo;
//            private String  flightTimeGo;
//            private String  priceToGo;
//            private String  curToGo;
//
//            private String timeDepartEnd ;
//            private String  airportDepartEnd;
//            private String  timeArivalEnd;
//            private String  airportArivalEnd;
//            private String  flightTimeEnd;
//            private String  priceToEnd;
//            private String  curToEnd;

            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.depart_arival_list_adapter, null);
            holder = new MyViewHolder();
            holder.timeDepartGo = (TextView) view.findViewById(R.id.timeDepartGoID);
            holder.airportDepartGo= (TextView) view.findViewById(R.id.airportDepartGoID);
            holder.timeArivalGo= (TextView) view.findViewById(R.id.timeArivalGoID);
            holder.airportArivalGo = (TextView) view.findViewById(R.id.airportArivalGoID);
            holder.flightTimeGo= (TextView) view.findViewById(R.id.flightTimeGoID);
            holder.priceToGo= (TextView) view.findViewById(R.id.priceToGoID);
            holder.curToGo = (TextView) view.findViewById(R.id.curToGoID);
            holder.airlineGo = (TextView) view.findViewById(R.id.airlinesGoID);



            holder.timeDepartEnd= (TextView) view.findViewById(R.id.timeDepartEndID);
            holder.airportDepartEnd= (TextView) view.findViewById(R.id.airportDepartEndID);
            holder.timeArivalEnd= (TextView) view.findViewById(R.id.timeArivalEndID);
            holder.airportArivalEnd= (TextView) view.findViewById(R.id.airportArivalEndID);
            holder.flightTimeEnd= (TextView) view.findViewById(R.id.flightTimeEndID);
            holder.priceToEnd= (TextView) view.findViewById(R.id.priceToEndID);
            holder.curToEnd= (TextView) view.findViewById(R.id.curToEndID);
            holder.airlineEnd = (TextView) view.findViewById(R.id.AirlinesEndID);

            holder.returnFlightLayout = (RelativeLayout) view.findViewById(R.id.returnFlightLayoutID);

            //holder.downloadFile= (ImageView) view.findViewById(R.id.downloadImageID);

            view.setTag(holder);

        }else{


            holder = (MyViewHolder) view.getTag();

        }

        final ItemForFlightDetails result = getItem(position);

        holder.timeDepartGo.setText(item.getTimeDepartGo());
        holder.airportDepartGo.setText(item.getAirportDepartGo());
        holder.timeArivalGo.setText(item.getTimeArivalGo());
        holder.airportArivalGo.setText(item.getAirportArivalGo());
        holder.flightTimeGo.setText(item.getFlightTimeGo());
        holder.priceToGo.setText(item.getPriceToGo());
        holder.curToGo.setText(item.getCurToGo());
        holder.airlineGo.setText(item.getAirlineGo());


        holder.timeDepartEnd.setText(item.getTimeDepartEnd());
        holder.airportDepartEnd.setText(item.getAirportDepartEnd());
        holder.timeArivalEnd.setText(item.getTimeArivalEnd());
        holder.airportArivalEnd.setText(item.getAirportArivalEnd());
        holder.flightTimeEnd.setText(item.getFlightTimeEnd());
        holder.priceToEnd.setText(item.getPriceToEnd());
        holder.curToEnd.setText(item.getCurToEnd());
        holder.airlineEnd.setText(item.getAirlineEnd());



        if(holder.timeDepartEnd.getText().equals("")&&holder.airportArivalEnd.getText().equals("")){
            holder.returnFlightLayout.setVisibility(View.INVISIBLE);
            holder.returnFlightLayout.setVisibility(View.GONE);
            holder.priceToGo.setVisibility(View.VISIBLE);
            holder.curToGo.setVisibility(View.VISIBLE);
        }

        String url = "https://iatacodes.org/api/v6/airlines?api_key=6f348d34-54ba-472f-9c76-5eb6e1b85d38&code="+getItem(position).getAirlineGo();
        Log.i("url:",url);
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    public static final String TAG = "";

                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        if (e == null && result != null) {
                            JsonObject json = result.getResult();
                            Log.d(TAG, "response: " + json);
                            Log.d(TAG, "status: " + result.getHeaders().code());

                            try {
                                String finalJson = json.toString();
                                JSONObject parentObject = new JSONObject(finalJson);
                                JSONArray parentArray = parentObject.getJSONArray("response");

                                StringBuffer finalBufferedData = new StringBuffer();
                                for (int i = 0; i < parentArray.length(); i++) {
                                    JSONObject finalObject = parentArray.getJSONObject(i);
                                    Log.i("mpike2 ", "mpike2233");
                                    String code = finalObject.getString("code");
                                    String name = finalObject.getString("name");
                                    holder.airlineGo.setText(name);
                                    finalBufferedData.append(name + "\n");
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        } else {
                            e.printStackTrace();
                        }
                    }
                });

        if(!holder.timeDepartEnd.getText().equals("")&&!holder.airportArivalEnd.getText().equals("")) {
            String url1 = "https://iatacodes.org/api/v6/airlines?api_key=6f348d34-54ba-472f-9c76-5eb6e1b85d38&code=" + getItem(position).getAirlineEnd();
            Ion.with(context)
                    .load(url1)
                    .asJsonObject()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<JsonObject>>() {
                        public static final String TAG = "";

                        @Override
                        public void onCompleted(Exception e, Response<JsonObject> result) {
                            if (e == null && result != null) {
                                JsonObject json = result.getResult();
                                Log.d(TAG, "response: " + json);
                                Log.d(TAG, "status: " + result.getHeaders().code());
                                Log.i("response: ", json.toString());
                                try {
                                    String finalJson = json.toString();
                                    JSONObject parentObject = new JSONObject(finalJson);
                                    JSONArray parentArray = parentObject.getJSONArray("response");

                                    StringBuffer finalBufferedData = new StringBuffer();
                                    for (int i = 0; i < parentArray.length(); i++) {
                                        JSONObject finalObject = parentArray.getJSONObject(i);
                                        Log.i("mpike2 ", "mpike2233");
                                        String code = finalObject.getString("code");
                                        String name = finalObject.getString("name");
                                        holder.airlineEnd.setText(name);
                                        finalBufferedData.append(name + "\n");
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
        }



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,result.getPriceToGo(), Toast.LENGTH_LONG).show();


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.flight_more_details, (ViewGroup) ((Activity) context).findViewById(R.id.flifhtMoreDetailsID));
                final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setView(layout);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView originAirportGo = (TextView) layout.findViewById(R.id.originAirportGoID);
                TextView destinationAirportGo = (TextView) layout.findViewById(R.id.destinationAirportGoID);
                TextView operatingAirlineGo = (TextView) layout.findViewById(R.id.operatingAirlineGoID);
                TextView FlightNumberGo = (TextView) layout.findViewById(R.id.FlightNumberGoId);
                TextView aircraftGo = (TextView) layout.findViewById(R.id.aircraftGoID);
                TextView economyGO = (TextView) layout.findViewById(R.id.ECONOMYGOID);
                TextView seatsRemainingGo = (TextView) layout.findViewById(R.id.seatsRemainingGoID);
                TextView bookingCodeGo = (TextView) layout.findViewById(R.id.bookingCodeGoId);

                TextView originAirportEnd = (TextView) layout.findViewById(R.id.originAirportEndID);
                TextView destinationAirportEnd= (TextView) layout.findViewById(R.id.destinationAirportEndID);
                TextView operatingAirlineEnd= (TextView) layout.findViewById(R.id.operatingAirlineEndID);
                TextView FlightNumberEnd = (TextView) layout.findViewById(R.id.FlightNumberEndId);
                TextView aircraftEnd = (TextView) layout.findViewById(R.id.aircraftEndID);
                TextView economyEND = (TextView) layout.findViewById(R.id.ECONOMYENDID);
                TextView seatsRemainingEnd = (TextView) layout.findViewById(R.id.seatsRemainingEndID);
                TextView bookingCodeEnd = (TextView) layout.findViewById(R.id.bookingCodeEndId);

                TextView totalPrice = (TextView) layout.findViewById(R.id.priceID);
                TextView cur = (TextView) layout.findViewById(R.id.curID);

                RelativeLayout inboundLayout = (RelativeLayout) layout.findViewById(R.id.inboundLayoutID);

                Button cancel = (Button) layout.findViewById(R.id.cancelID);
                Button checkOut = (Button) layout.findViewById(R.id.checkOutID);






                originAirportGo.setText(item.getAirportDepartGo());
                destinationAirportGo.setText(item.getAirportArivalGo());
                operatingAirlineGo.setText(item.getAirlineGo());
                //FlightNumberGo.setText(ite);
                //aircraftGo
                economyGO.setText(item.getTravel_classGo());
                seatsRemainingGo.setText(item.getSeats_remainingGo());
                bookingCodeGo.setText(item.getBooking_codeGo());

                if(holder.timeDepartEnd.getText().equals("")&&holder.airportArivalEnd.getText().equals("")) {
                    inboundLayout.setVisibility(View.INVISIBLE);
                    inboundLayout.setVisibility(View.GONE);
                }else{
                    originAirportEnd.setText(item.getAirportDepartEnd());
                    destinationAirportEnd.setText(item.getAirportArivalEnd());
                    operatingAirlineEnd.setText(item.getAirlineEnd());
                    //FlightNumberEnd.setText(ite);
                    //aircraftEnd
                    economyEND.setText(item.getTravel_classEnd());
                    seatsRemainingEnd.setText(item.getSeats_remainingEnd());
                    bookingCodeEnd.setText(item.getBooking_codeEnd());
                }





                totalPrice.setText(item.getPriceToGo());
                cur.setText(item.getCurToGo());

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                checkOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });


            }
        });


        return view;
    }


    private static class MyViewHolder {

        private TextView timeDepartGo ;
        private TextView  airportDepartGo;
        private TextView  timeArivalGo;
        private TextView  airportArivalGo;
        private TextView  flightTimeGo;
        private TextView  priceToGo;
        private TextView  curToGo;
        private TextView airlineGo;

        private TextView timeDepartEnd ;
        private TextView  airportDepartEnd;
        private TextView  timeArivalEnd;
        private TextView  airportArivalEnd;
        private TextView  flightTimeEnd;
        private TextView  priceToEnd;
        private TextView  curToEnd;
        private TextView airlineEnd;

        private RelativeLayout returnFlightLayout;
    }



}

