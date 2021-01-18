package android.uom.gr.airflight;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MyDBHistorySearch db;

    AdapterListViewFlightDetails adapterListViewFlightDetails;
    private ArrayList<ItemForFlightDetails> itens = new ArrayList<ItemForFlightDetails>();
    ListView listView;
    ProgressDialog pd;

    String travelClassNavBar="ECONOMY";
    String maxPriceNavBar="10000";
    String changeDepartDayNavBar = "";
    String changeArrivalDayNavBar = "";
    String arriveByNavVar="";
    String returnByNavVar="";
    String currencyNavVar="EUR";
    String numberOfResultsByNavVar="10";

    String from = null;
    String to = null;
    String adults = null;
    String children = null;
    String infants = null;
    String way = null;
    String depart = null;
    String arival = null;
    String SaveInDB = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.drawer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.END);
            }
        });
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        db = new  MyDBHistorySearch(this);


        listView = (ListView) findViewById(R.id.listview);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            from     = extras.getString("from");
            to       = extras.getString("to");
            adults   = extras.getString("adults");
            children = extras.getString("children");
            infants = extras.getString("infants");
            way      = extras.getString("way");
            depart   = extras.getString("depart");
            arival   = extras.getString("arival");
            SaveInDB   = extras.getString("SaveInDB");
            Log.i("from",from);
            Log.i("to",to);
            Log.i("adults",adults);
            Log.i("children",children);
            Log.i("infants",infants);
            Log.i("way",way);
            Log.i("depart",depart);
            Log.i("arival",arival);
        }
        String[] fromAir = from.split(",");
        String[] toAir = to.split(",");

        setTitle(fromAir[0] +"-"+toAir[0]);
        if(way.equals("one way")){
            searchForFlightsOneWay(this,fromAir[0],toAir[0],adults,children,infants,way,depart,arival,SaveInDB);
        }else if(way.equals("round trip")){
            searchForFlights(this,fromAir[0],toAir[0],adults,children,infants,way,depart,arival,SaveInDB);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.changeArrivalDay).setTitle("Arrival day - "+ arival);
            menu.findItem(R.id.changeDepartDay).setTitle("Depart day - "+ depart);
            navigationView.setNavigationItemSelectedListener(Details.this);
        }



        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Details.this, MainActivity.class);
                //intent.putExtra("title", getTitle());
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Details.this);
            builder1.setMessage("Do you want to go back?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            startActivity(new Intent(Details.this, MainActivity.class));
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            //System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.travelClassID) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.travel_class_alert_dialog, (ViewGroup) findViewById(R.id.travelClassDialog));
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setView(layout);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            final RadioButton buissnesClass = (RadioButton) layout.findViewById(R.id.buissnesClassID);
            final RadioButton firstClass = (RadioButton) layout.findViewById(R.id.firstClassID);
            final RadioButton premiumEconomy = (RadioButton) layout.findViewById(R.id.premiumEconomyID);
            final RadioButton economyClass = (RadioButton) layout.findViewById(R.id.economyClassID);

            final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            buissnesClass.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        travelClassNavBar= "BUSINESS";
                        menu.findItem(R.id.travelClassID).setTitle("Max price - Business class");
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    firstClass.setChecked(false);
                    premiumEconomy.setChecked(false);
                    economyClass.setChecked(false);
                }
            });

            firstClass.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.travelClassID).setTitle("Travel class - First class");
                        travelClassNavBar= "FIRST";
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    buissnesClass.setChecked(false);
                    economyClass.setChecked(false);
                    premiumEconomy.setChecked(false);
                }
            });
            premiumEconomy.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.travelClassID).setTitle("Max price - Premium economy");
                        travelClassNavBar= "PREMIUM_ECONOMY";
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    economyClass.setChecked(false);
                    firstClass.setChecked(false);
                    buissnesClass.setChecked(false);
                }
            });

            economyClass.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.travelClassID).setTitle("Max price - Economy class");
                        travelClassNavBar= "ECONOMY";
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    premiumEconomy.setChecked(false);
                    firstClass.setChecked(false);
                    buissnesClass.setChecked(false);
                }
            });

            Button okButton = (Button) layout.findViewById(R.id.okTravelClassID);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });


        } else if (id == R.id.MaxPriceID) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.seekbar, (ViewGroup) findViewById(R.id.AlertSeekBarDialog));
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setView(layout);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            SeekBar sb = (SeekBar) layout.findViewById(R.id.seekBarMax);
            final TextView priceSelected = (TextView) layout.findViewById(R.id.priceFromSeekID);
            sb.setMax(1000);
//      seekBar.setIndeterminate(true);

            ShapeDrawable thumb = new ShapeDrawable(new OvalShape());

            thumb.setIntrinsicHeight(15);
            thumb.setIntrinsicWidth(15);
            sb.setThumb(thumb);
            sb.setProgress(0);
            sb.setVisibility(View.VISIBLE);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //Do something here with new value
                    priceSelected.setText(String.valueOf(progress));
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.MaxPriceID).setTitle("Max price - " + String.valueOf(progress));
                        if(progress==1000)
                            progress=10000;
                        maxPriceNavBar = String.valueOf(progress);
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            Button okButton = (Button) layout.findViewById(R.id.OkID);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

        } else if (id == R.id.changeDepartDay) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.calendar_depart, (ViewGroup) findViewById(R.id.departCalendar));
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setView(layout);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            MaterialCalendarView calendarView = (MaterialCalendarView) layout.findViewById(R.id.calendarDepart);

            calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull final CalendarDay date, boolean selected) {
                    //String.valueOf(date.getMonth())  String.valueOf(date.getDay())
                    int month= date.getMonth() +1;
                    String theMonth="";
                    String theDay="";
                    if(month>=1 && month<=9){
                        theMonth = String.format("%02d", month);
                    }
                    else
                        theMonth = String.valueOf(month);

                    if(date.getDay()>=1 && date.getDay()<=9){
                        theDay = String.format("%02d",date.getDay());
                    }
                    else
                        theDay = String.valueOf(date.getDay());

                    String dateSelect = String.valueOf(date.getYear()+"-"+theMonth+"-"+theDay);

                    changeDepartDayNavBar = dateSelect;
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.changeDepartDay).setTitle("Depart day - "+ dateSelect);
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }


                    Log.i("Date",String.valueOf(date.getYear()+"-"+String.valueOf(date.getMonth())+"-"+ String.valueOf(date.getDay())));
                    widget.toString();

                }
            });


            Button okButton = (Button) layout.findViewById(R.id.okID);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //changeDepartDayNavBar =
                    alertDialog.dismiss();
                }
            });

            Button cancel = (Button) layout.findViewById(R.id.cancelID);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeDepartDayNavBar = depart;
                    alertDialog.dismiss();
                }
            });

        } else if (id == R.id.changeArrivalDay){
            if(way.equals("one way")){

            }else if(way.equals("round trip")){
                LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.calendar_arrival, (ViewGroup) findViewById(R.id.arrivalCalendar));
                final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setView(layout);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                MaterialCalendarView calendarView = (MaterialCalendarView) layout.findViewById(R.id.calendarArrival);
                Button cancel = (Button) layout.findViewById(R.id.cancelArrivalID);
                Button ok = (Button) layout.findViewById(R.id.okArrivalID);
                calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull final CalendarDay date, boolean selected) {
                        //String.valueOf(date.getMonth())  String.valueOf(date.getDay())
                        int month= date.getMonth() +1;
                        String theMonth="";
                        String theDay="";
                        if(month>=1 && month<=9){
                            theMonth = String.format("%02d", month);
                        }
                        else
                            theMonth = String.valueOf(month);

                        if(date.getDay()>=1 && date.getDay()<=9){
                            theDay = String.format("%02d",date.getDay());
                        }
                        else
                            theDay = String.valueOf(date.getDay());

                        String dateSelect = String.valueOf(date.getYear()+"-"+theMonth+"-"+theDay);

                        changeArrivalDayNavBar = dateSelect;


                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        if (navigationView != null) {
                            Menu menu = navigationView.getMenu();
                            menu.findItem(R.id.changeArrivalDay).setTitle("Arrival day - "+ dateSelect);
                            navigationView.setNavigationItemSelectedListener(Details.this);
                        }



                        Log.i("Date",String.valueOf(date.getYear()+"-"+String.valueOf(date.getMonth())+"-"+ String.valueOf(date.getDay())));
                        widget.toString();

                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeArrivalDayNavBar = arival;
                        alertDialog.dismiss();
                    }
                });
            }


        } else if (id == R.id.nav_search) {

            String[] fromAir = from.split(",");
            String[] toAir = to.split(",");

            if(way.equals("one way")){
//                if(!changeDepartDayNavBar.equals(""))
//                    setTitle(fromAir[0] +"-"+toAir[0]+","+changeDepartDayNavBar);
                searchForFlightsOneWay(this,fromAir[0],toAir[0],adults,children,infants,way,depart,arival,SaveInDB);
            }else if(way.equals("round trip")){
//                if(!changeDepartDayNavBar.equals("")&& !changeArrivalDayNavBar.equals(""))
//                    setTitle(fromAir[0] +"-"+toAir[0]+","+changeDepartDayNavBar+"-"+changeArrivalDayNavBar);
//                else if(!changeDepartDayNavBar.equals("") && changeArrivalDayNavBar.equals(""))
//                    setTitle(fromAir[0] +"-"+toAir[0]+","+changeDepartDayNavBar+"-"+arival);
//                else if(changeDepartDayNavBar.equals("") && !changeArrivalDayNavBar.equals(""))
//                    setTitle(fromAir[0] +"-"+toAir[0]+","+depart+"-"+changeArrivalDayNavBar);
                searchForFlights(this,fromAir[0],toAir[0],adults,children,infants,way,depart,arival,SaveInDB);
            }


//            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.non_stop, (ViewGroup) findViewById(R.id.nonStopLayoutID));
//            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
//                    .setView(layout);
//            final AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//
//            final RadioButton yes = (RadioButton) layout.findViewById(R.id.yesID);
//            final RadioButton no = (RadioButton) layout.findViewById(R.id.NoID);
//
//
//            final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//
//            yes.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    if (navigationView != null) {
//                        Menu menu = navigationView.getMenu();
//                        menu.findItem(R.id.NonstopID).setTitle("Non stop - Yes");
//                        navigationView.setNavigationItemSelectedListener(Details.this);
//                    }
//                    no.setChecked(false);
//                }
//            });
//
//            no.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if (navigationView != null) {
//                        Menu menu = navigationView.getMenu();
//                        menu.findItem(R.id.NonstopID).setTitle("Nonstop - No");
//                        navigationView.setNavigationItemSelectedListener(Details.this);
//                    }
//                    yes.setChecked(false);
//                }
//            });
//
//
//            Button okButton = (Button) layout.findViewById(R.id.okID);
//            okButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    alertDialog.dismiss();
//                }
//            });

        }
        else if (id == R.id.Number_of_resultsID) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.number_of_results_dialog, (ViewGroup) findViewById(R.id.resultsLayoutID));
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setView(layout);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            SeekBar sb = (SeekBar)layout.findViewById(R.id.resultsSeekID);
            final TextView priceSelected = (TextView) layout.findViewById(R.id.resultsTextID);
            sb.setMax(250);
//      seekBar.setIndeterminate(true);

            ShapeDrawable thumb = new ShapeDrawable(new OvalShape());

            thumb.setIntrinsicHeight(20);
            thumb.setIntrinsicWidth(20);
            sb.setThumb(thumb);
            sb.setProgress(10);
            sb.setVisibility(View.VISIBLE);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                    //Do something here with new value
                    if(progress==0){
                        progress=1;
                    }
                    priceSelected.setText(String.valueOf(progress));
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();

                        menu.findItem(R.id.Number_of_resultsID).setTitle("Number of results - "+String.valueOf(progress));
                        numberOfResultsByNavVar = String.valueOf(progress);
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            Button okButton = (Button) layout.findViewById(R.id.resultsButtonID);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

        } else if (id == R.id.arrive_byID) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.arrive_by, (ViewGroup) findViewById(R.id.arrive_by_layoutID));
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setView(layout);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            SeekBar sb = (SeekBar)layout.findViewById(R.id.seekBar);
            final TextView timeSelected = (TextView) layout.findViewById(R.id.timeID);

//      seekBar.setIndeterminate(true);




            sb.setVisibility(View.VISIBLE);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                    //Do something here with new value
                    String time = "";
                    if(progress==0){time="00:00";} if(progress==1){time="01:00";}if(progress==2){time="02:00";}if(progress==3){time="03:00";}
                    if(progress==4){time="04:00";}if(progress==5){time="05:00";}if(progress==6){time="06:00";}if(progress==7){time="07:00";}
                    if(progress==8){time="08:00";}if(progress==9){time="09:00";}if(progress==10){time="10:00";}if(progress==11){time="11:00";}
                    if(progress==12){time="12:00";}if(progress==13){time="13:00";}if(progress==14){time="14:00";}if(progress==15){time="15:00";}
                    if(progress==16){time="16:00";}if(progress==17){time="17:00";}if(progress==18){time="18:00";}if(progress==19){time="19:00";}
                    if(progress==20){time="20:00";}if(progress==21){time="21:00";}if(progress==22){time="22:00";}if(progress==23){time="23:00";}

                    timeSelected.setText(time);
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        arriveByNavVar = time;
                        //2017-02-28T17%3A00
                        menu.findItem(R.id.arrive_byID).setTitle("Arrive by - "+time);
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            Button okButton = (Button) layout.findViewById(R.id.okID);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            Button defaultButton = (Button) layout.findViewById(R.id.DefaultID);
            defaultButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        arriveByNavVar = "";
                        menu.findItem(R.id.arrive_byID).setTitle("Arrive by - Default");
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    alertDialog.dismiss();
                }
            });

        } else if (id == R.id.currencyID) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.currency, (ViewGroup) findViewById(R.id.currencyLayoutID));
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setView(layout);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            final RadioButton eur = (RadioButton) layout.findViewById(R.id.EURID);
            final RadioButton gbp = (RadioButton) layout.findViewById(R.id.GBPID);
            final RadioButton usd = (RadioButton) layout.findViewById(R.id.USDID);
            final RadioButton chf = (RadioButton) layout.findViewById(R.id.CHFID);

            final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            eur.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.currencyID).setTitle("Currency - EUR");
                        currencyNavVar = "EUR";
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    gbp.setChecked(false);
                    usd.setChecked(false);
                    chf.setChecked(false);
                }
            });

            gbp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.currencyID).setTitle("Currency - GBP");
                        currencyNavVar = "GBP";
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    eur.setChecked(false);
                    usd.setChecked(false);
                    chf.setChecked(false);
                }
            });
            usd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.currencyID).setTitle("Currency - USD");
                        currencyNavVar = "USD";
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    gbp.setChecked(false);
                    eur.setChecked(false);
                    chf.setChecked(false);
                }
            });

            chf.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (navigationView != null) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.currencyID).setTitle("Currency - CHF");
                        currencyNavVar = "CHF";
                        navigationView.setNavigationItemSelectedListener(Details.this);
                    }
                    gbp.setChecked(false);
                    eur.setChecked(false);
                    usd.setChecked(false);
                }
            });

            Button okButton = (Button) layout.findViewById(R.id.okID);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

        } else if (id == R.id.returnID) {
            if(way.equals("one way")){

            }else if(way.equals("round trip")){
                LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.arrive_by, (ViewGroup) findViewById(R.id.arrive_by_layoutID));
                final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setView(layout);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                SeekBar sb = (SeekBar)layout.findViewById(R.id.seekBar);
                final TextView timeSelected = (TextView) layout.findViewById(R.id.timeID);
                TextView returnDay = (TextView) layout.findViewById(R.id.textView14);
                returnDay.setText("Return by");
//      seekBar.setIndeterminate(true);

                sb.setVisibility(View.VISIBLE);
                sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                        //Do something here with new value
                        String time = "";
                        if(progress==0){time="00:00";} if(progress==1){time="01:00";}if(progress==2){time="02:00";}if(progress==3){time="03:00";}
                        if(progress==4){time="04:00";}if(progress==5){time="05:00";}if(progress==6){time="06:00";}if(progress==7){time="07:00";}
                        if(progress==8){time="08:00";}if(progress==9){time="09:00";}if(progress==10){time="10:00";}if(progress==11){time="11:00";}
                        if(progress==12){time="12:00";}if(progress==13){time="13:00";}if(progress==14){time="14:00";}if(progress==15){time="15:00";}
                        if(progress==16){time="16:00";}if(progress==17){time="17:00";}if(progress==18){time="18:00";}if(progress==19){time="19:00";}
                        if(progress==20){time="20:00";}if(progress==21){time="21:00";}if(progress==22){time="22:00";}if(progress==23){time="23:00";}

                        timeSelected.setText(String.valueOf(progress));
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        if (navigationView != null) {
                            Menu menu = navigationView.getMenu();
                            returnByNavVar = time;
                            menu.findItem(R.id.returnID).setTitle("Return by - "+time);
                            navigationView.setNavigationItemSelectedListener(Details.this);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                Button okButton = (Button) layout.findViewById(R.id.okID);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                Button defaultButton = (Button) layout.findViewById(R.id.DefaultID);
                defaultButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        if (navigationView != null) {
                            Menu menu = navigationView.getMenu();
                            returnByNavVar = "";
                            menu.findItem(R.id.returnID).setTitle("Number of results -");
                            navigationView.setNavigationItemSelectedListener(Details.this);
                        }
                        alertDialog.dismiss();
                    }
                });
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }


    public void searchForFlights(final Context context, final String from, final String to , final String adults , final String children, final String infants , final String way , final String depart , final String arival, final String saveDb){
        pd = new ProgressDialog(this);
        pd.setMessage("Παρακαλώ περιμένετε!");
        pd.show();
        String url;
        //2017-02-28T17%3A00
        if(changeDepartDayNavBar.equals(""))
            changeDepartDayNavBar = depart;
        if(changeArrivalDayNavBar.equals(""))
            changeArrivalDayNavBar = arival;
        if(arriveByNavVar.equals("")&&returnByNavVar.equals("")){
            url = "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin="+from+"&destination="+to+"&departure_date="+changeDepartDayNavBar+"&return_date="+changeArrivalDayNavBar+"&adults="+adults+"&children="+children+"&infants="+infants+"&max_price="+maxPriceNavBar+"&currency="+currencyNavVar+"&travel_class="+travelClassNavBar+"&number_of_results="+numberOfResultsByNavVar;
        }else if(arriveByNavVar.equals("") && !returnByNavVar.equals("")){
            String[] splitTimeRet = returnByNavVar.split(":");
            returnByNavVar = arival+"T"+splitTimeRet[0]+"%3A"+splitTimeRet[1];
            url = "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin="+from+"&destination="+to+"&departure_date="+changeDepartDayNavBar+"&return_date="+changeArrivalDayNavBar+"&return_by="+returnByNavVar+"&adults="+adults+"&children="+children+"&infants="+infants+"&max_price="+maxPriceNavBar+"&currency="+currencyNavVar+"&travel_class="+travelClassNavBar+"&number_of_results="+numberOfResultsByNavVar;
            // url = "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin=ATH&destination=SKG&departure_date=2017-02-25&return_date=2017-02-28&return_by=2017-02-25T16%3A00&adults=1&children=1&infants=1&max_price=10000&currency=EUR&travel_class=ECONOMY&number_of_results=5";
        }
        else if(returnByNavVar.equals("") && !arriveByNavVar.equals("")){
            String[] splitTimeArr =arriveByNavVar.split(":");
            arriveByNavVar = depart+"T"+splitTimeArr[0]+"%3A"+splitTimeArr[1];
            url = "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin="+from+"&destination="+to+"&departure_date="+changeDepartDayNavBar+"&return_date="+changeArrivalDayNavBar+"&arrive_by="+arriveByNavVar+"&adults="+adults+"&children="+children+"&infants="+infants+"&max_price="+maxPriceNavBar+"&currency="+currencyNavVar+"&travel_class="+travelClassNavBar+"&number_of_results="+numberOfResultsByNavVar;
            // url =  "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin=ATH&destination=SKG&departure_date=2017-02-25&return_date=2017-02-28&arrive_by=2017-02-25T16%3A00&adults=1&children=1&infants=1&max_price=10000&currency=EUR&travel_class=ECONOMY&number_of_results="+numberOfResultsByNavVar;
        }
        else{
            String[] splitTimeArr =arriveByNavVar.split(":");
            arriveByNavVar = depart+"T"+splitTimeArr[0]+"%3A"+splitTimeArr[1];
            String[] splitTimeRet = returnByNavVar.split(":");
            returnByNavVar = arival+"T"+splitTimeRet[0]+"%3A"+splitTimeRet[1];
            url = "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin="+from+"&destination="+to+"&departure_date="+changeDepartDayNavBar+"&return_date="+changeArrivalDayNavBar+"&arrive_by="+arriveByNavVar+"&return_by="+returnByNavVar+"&adults="+adults+"&children="+children+"&infants="+infants+"&max_price="+maxPriceNavBar+"&currency="+currencyNavVar+"&travel_class="+travelClassNavBar+"&number_of_results="+numberOfResultsByNavVar;
        }
        Log.i("url sandbox",url+"sd");
        Ion.with(this)
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
                            Log.i("response: ", json.toString());
                            Log.i("status: ", String.valueOf(result.getHeaders().code()));

                            if(result.getHeaders().code()==200) {
                                try {
                                    String total_price = "";
                                    String marketing_airline_Out = "";
                                    String operating_airline_Out = "";
                                    String flight_number_Out = "";
                                    String aircraft_Out = "";
                                    String marketing_airline_In = "";
                                    String operating_airline_In = "";
                                    String flight_number_In = "";
                                    String aircraft_In = "";
                                    String departs_at_Out = "";
                                    String departs_at_In = "";
                                    String arrives_at_Out = "";
                                    String arrives_at_In = "";
                                    String travel_class_Out= "" ;
                                    String booking_code_Out="";
                                    String  seats_remaining_Out="";
                                    String travel_class_In = "";
                                    String booking_code_In = "";
                                    String  seats_remaining_In = "";
                                    String origin_airport_Out= "";
                                    String destination_airport_Out="";
                                    String origin_airport_In = "";
                                    String destination_airport_In= "";

                                    JSONObject o = new JSONObject(json.toString());
                                    JSONArray results = o.getJSONArray("results");

                                    for (int i = 0; i < results.length(); i++) {
                                        JSONObject resultObj = results.getJSONObject(i);
                                        JSONArray itineraries = resultObj.getJSONArray("itineraries");
                                        JSONObject fare = resultObj.getJSONObject("fare");
                                        total_price = fare.getString("total_price");
                                        Log.i("total_price", total_price);


                                        for (int z = 0; z < itineraries.length(); z++) {
                                            JSONObject itinerariesResults = itineraries.getJSONObject(z);
                                            JSONObject outboundObj = itinerariesResults.getJSONObject("outbound");
                                            JSONArray outboundArray = outboundObj.getJSONArray("flights");

                                            JSONObject inboundObj = itinerariesResults.getJSONObject("inbound");
                                            JSONArray inboundArray = inboundObj.getJSONArray("flights");

                                            //                                        "departs_at": "2017-01-21T07:05",
                                            //                                        "arrives_at": "2017-01-21T08:05",

                                            for (int j = 0; j < outboundArray.length(); j++) {
                                                JSONObject outboundResults = outboundArray.getJSONObject(j);
                                                departs_at_Out = outboundResults.getString("departs_at");
                                                Log.i("departs_at Out", departs_at_Out);
                                                arrives_at_Out = outboundResults.getString("arrives_at");
                                                Log.i("arrives_at Out", arrives_at_Out);

                                                JSONObject origin_info = outboundResults.getJSONObject("origin");
                                                origin_airport_Out= origin_info.getString("airport");
                                                Log.i("airport_Out",origin_airport_Out);

                                                JSONObject destination_info = outboundResults.getJSONObject("destination");
                                                destination_airport_Out= destination_info.getString("airport");
                                                Log.i("airport_Out",destination_airport_Out);

                                                marketing_airline_Out = outboundResults.getString("marketing_airline");
                                                Log.i("marketing_air Out", marketing_airline_Out);
                                                operating_airline_Out = outboundResults.optString("operating_airline");
                                                Log.i("operating_air Out", operating_airline_Out);
                                                flight_number_Out = outboundResults.optString("flight_number");
                                                Log.i("flight_number Out", flight_number_Out);
                                                aircraft_Out = outboundResults.optString("aircraft");
                                                Log.i("aircraft Out", aircraft_Out);
                                                JSONObject booking_info = outboundResults.getJSONObject("booking_info");
                                                travel_class_Out = booking_info.getString("travel_class");
                                                booking_code_Out = booking_info.getString("booking_code");
                                                seats_remaining_Out = booking_info.getString("seats_remaining");
                                                Log.i("booking_info_Lam",travel_class_Out+","+booking_code_Out+","+seats_remaining_Out);

                                            }

                                            for (int k = 0; k < inboundArray.length(); k++) {
                                                JSONObject inboundResults = inboundArray.getJSONObject(k);
                                                departs_at_In = inboundResults.getString("departs_at");
                                                Log.i("departs_at In", departs_at_In);
                                                arrives_at_In = inboundResults.getString("arrives_at");
                                                Log.i("arrives_at Out", arrives_at_In);

                                                JSONObject origin_info = inboundResults.getJSONObject("origin");
                                                origin_airport_In= origin_info.getString("airport");
                                                Log.i("airport_IN",origin_airport_In);

                                                JSONObject destination_info = inboundResults.getJSONObject("destination");
                                                destination_airport_In= destination_info.getString("airport");
                                                Log.i("airport_IN",destination_airport_In);

                                                marketing_airline_In = inboundResults.getString("marketing_airline");
                                                Log.i("marketing_air In", marketing_airline_In);
                                                operating_airline_In = inboundResults.optString("operating_airline");
                                                Log.i("operating_air IN", operating_airline_In);
                                                flight_number_In = inboundResults.optString("flight_number");
                                                Log.i("flight_number IN", flight_number_In);
                                                aircraft_In = inboundResults.optString("aircraft");
                                                Log.i("aircraft IN", aircraft_In);
                                                JSONObject booking_info = inboundResults.getJSONObject("booking_info");
                                                travel_class_In = booking_info.getString("travel_class");
                                                booking_code_In = booking_info.getString("booking_code");
                                                seats_remaining_In = booking_info.getString("seats_remaining");
                                                Log.i("booking_info_Lam",travel_class_In+","+booking_code_In+","+seats_remaining_In);

                                            }

                                        }

                                        String[] timeDepartGo = departs_at_Out.split("T");
                                        String[] timeArivalGo = arrives_at_Out.split("T");
                                        String[] timeDepartEnd = departs_at_In.split("T");
                                        String[] timeArivalEnd = arrives_at_In.split("T");
                                        ItemForFlightDetails item = new ItemForFlightDetails(timeDepartGo[1], /*origin_airport_Out*/from, timeArivalGo[1], destination_airport_Out, depart+"->"+timeDepartGo[0], total_price, "E",operating_airline_Out,travel_class_Out, booking_code_Out,seats_remaining_Out, timeDepartEnd[1],/*origin_airport_In*/to, timeArivalEnd[1], destination_airport_In, arival+"->"+timeArivalEnd[0], total_price, "E",operating_airline_In,travel_class_In, booking_code_In,seats_remaining_In);
                                        itens.add(item);
                                        adapterListViewFlightDetails = new AdapterListViewFlightDetails(context, itens);
                                        listView.setAdapter(adapterListViewFlightDetails);


                                    }


                                } catch (Exception exe) {
                                    exe.printStackTrace();
                                }

                                if(saveDb.equals("Save")) {

                                    List<ItemForHistory> history = db.getAllContacts();

                                    Log.d("Insert: ", "Inserting ..");
                                    db.addContact(new ItemForHistory(history.size() + 1, from, to, adults, children, infants, way, depart, arival));
                                }


                            }else{
                                String [] message = json.toString().split("\"");
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Details.this);
                                builder1.setMessage(message[5]);
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                                dialog.cancel();
                                                startActivity(new Intent(Details.this, MainActivity.class));
                                            }
                                        });

//                                builder1.setNegativeButton(
//                                        "Όχι",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            }
                        } else {
                            e.printStackTrace();
                        }
                        pd.cancel();
                    }
                });
    }


    public void searchForFlightsOneWay(final Context context, final String from, final String to , final String adults , final String children, final String infants , final String way , final String depart , final String arival, final String saveDb){
        pd = new ProgressDialog(this);
        pd.setMessage("Παρακαλώ περιμένετε!");
        pd.show();
        String url;
        if(changeDepartDayNavBar.equals(""))
            changeDepartDayNavBar = depart;
        if(arriveByNavVar.equals("")){
            url = "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin="+from+"&destination="+to+"&departure_date="+changeDepartDayNavBar+"&adults="+adults+"&children="+children+"&infants="+infants+"&max_price="+maxPriceNavBar+"&currency="+currencyNavVar+"&travel_class="+travelClassNavBar+"&number_of_results="+numberOfResultsByNavVar;
        }else{
            String[] splitTimeArr =arriveByNavVar.split(":");
            arriveByNavVar = depart+"T"+splitTimeArr[0]+"%3A"+splitTimeArr[1];
            url = "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin="+from+"&destination="+to+"&departure_date="+changeDepartDayNavBar+"&arrive_by="+arriveByNavVar+"&adults="+adults+"&children="+children+"&infants="+infants+"&max_price="+maxPriceNavBar+"&currency="+currencyNavVar+"&travel_class="+travelClassNavBar+"&number_of_results="+numberOfResultsByNavVar;
        }
        //url = "https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&origin="+from+"&destination="+to+"&departure_date="+depart+"&adults="+adults+"&children="+children+"&infants="+infants+"&max_price="+maxPriceNavBar+"&currency="+currencyNavVar+"&travel_class="+travelClassNavBar+"&number_of_results="+numberOfResultsByNavVar;
        Log.i("url oneWay",url+"DS");
        Ion.with(this)
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
                            Log.i("response: ", json.toString());
                            Log.i("status: ", String.valueOf(result.getHeaders().code()));

                            if(result.getHeaders().code()==200) {
                                try {
                                    String total_price = "";
                                    String marketing_airline_Out = "";
                                    String operating_airline_Out = "";
                                    String flight_number_Out = "";
                                    String aircraft_Out = "";
                                    String marketing_airline_In = "";
                                    String operating_airline_In = "";
                                    String flight_number_In = "";
                                    String aircraft_In = "";
                                    String departs_at_Out = "";
                                    String departs_at_In = "";
                                    String arrives_at_Out = "";
                                    String arrives_at_In = "";
                                    String travel_class_Out= "" ;
                                    String booking_code_Out="";
                                    String  seats_remaining_Out="";
                                    String travel_class_In = "";
                                    String booking_code_In = "";
                                    String  seats_remaining_In = "";
                                    String origin_airport_Out= "";
                                    String destination_airport_Out="";
                                    String origin_airport_In = "";
                                    String destination_airport_In= "";

                                    JSONObject o = new JSONObject(json.toString());
                                    JSONArray results = o.getJSONArray("results");

                                    for (int i = 0; i < results.length(); i++) {
                                        JSONObject resultObj = results.getJSONObject(i);
                                        JSONArray itineraries = resultObj.getJSONArray("itineraries");
                                        JSONObject fare = resultObj.getJSONObject("fare");
                                        total_price = fare.getString("total_price");
                                        Log.i("total_price", total_price);


                                        for (int z = 0; z < itineraries.length(); z++) {
                                            JSONObject itinerariesResults = itineraries.getJSONObject(z);
                                            JSONObject outboundObj = itinerariesResults.getJSONObject("outbound");
                                            JSONArray outboundArray = outboundObj.getJSONArray("flights");

//                                            JSONObject inboundObj = itinerariesResults.getJSONObject("inbound");
//                                            JSONArray inboundArray = inboundObj.getJSONArray("flights");

                                            //                                        "departs_at": "2017-01-21T07:05",
                                            //                                        "arrives_at": "2017-01-21T08:05",

                                            for (int j = 0; j < outboundArray.length(); j++) {
                                                JSONObject outboundResults = outboundArray.getJSONObject(j);
                                                departs_at_Out = outboundResults.getString("departs_at");
                                                Log.i("departs_at Out", departs_at_Out);
                                                arrives_at_Out = outboundResults.getString("arrives_at");
                                                Log.i("arrives_at Out", arrives_at_Out);

                                                JSONObject origin_info = outboundResults.getJSONObject("origin");
                                                origin_airport_Out= origin_info.getString("airport");
                                                Log.i("airport_Out",origin_airport_Out);

                                                JSONObject destination_info = outboundResults.getJSONObject("destination");
                                                destination_airport_Out= destination_info.getString("airport");
                                                Log.i("airport_Out",destination_airport_Out);

                                                marketing_airline_Out = outboundResults.getString("marketing_airline");
                                                Log.i("marketing_air Out", marketing_airline_Out);
                                                operating_airline_Out = outboundResults.optString("operating_airline");
                                                Log.i("operating_air Out", operating_airline_Out);
                                                flight_number_Out = outboundResults.optString("flight_number");
                                                Log.i("flight_number Out", flight_number_Out);
                                                aircraft_Out = outboundResults.optString("aircraft");
                                                Log.i("aircraft Out", aircraft_Out);
                                                JSONObject booking_info = outboundResults.getJSONObject("booking_info");
                                                travel_class_Out = booking_info.getString("travel_class");
                                                booking_code_Out = booking_info.getString("booking_code");
                                                seats_remaining_Out = booking_info.getString("seats_remaining");
                                                Log.i("booking_info_Lam",travel_class_Out+","+booking_code_Out+","+seats_remaining_Out);

                                            }

//                                            for (int k = 0; k < inboundArray.length(); k++) {
//                                                JSONObject inboundResults = inboundArray.getJSONObject(k);
//                                                departs_at_In = inboundResults.getString("departs_at");
//                                                Log.i("departs_at In", departs_at_In);
//                                                arrives_at_In = inboundResults.getString("arrives_at");
//                                                Log.i("arrives_at Out", arrives_at_In);
//
//                                                JSONObject origin_info = inboundResults.getJSONObject("origin");
//                                                origin_airport_In= origin_info.getString("airport");
//                                                Log.i("airport_IN",origin_airport_In);
//
//                                                JSONObject destination_info = inboundResults.getJSONObject("destination");
//                                                destination_airport_In= destination_info.getString("airport");
//                                                Log.i("airport_IN",destination_airport_In);
//
//                                                marketing_airline_In = inboundResults.getString("marketing_airline");
//                                                Log.i("marketing_air In", marketing_airline_In);
//                                                operating_airline_In = inboundResults.optString("operating_airline");
//                                                Log.i("operating_air IN", operating_airline_In);
//                                                flight_number_In = inboundResults.optString("flight_number");
//                                                Log.i("flight_number IN", flight_number_In);
//                                                aircraft_In = inboundResults.optString("aircraft");
//                                                Log.i("aircraft IN", aircraft_In);
//                                                JSONObject booking_info = inboundResults.getJSONObject("booking_info");
//                                                travel_class_In = booking_info.getString("travel_class");
//                                                booking_code_In = booking_info.getString("booking_code");
//                                                seats_remaining_In = booking_info.getString("seats_remaining");
//                                                Log.i("booking_info_Lam",travel_class_In+","+booking_code_In+","+seats_remaining_In);
//
//                                            }

                                        }

                                        String[] timeDepartGo = departs_at_Out.split("T");
                                        String[] timeArivalGo = arrives_at_Out.split("T");
//                                        String[] timeDepartEnd = departs_at_In.split("T");
//                                        String[] timeArivalEnd = arrives_at_In.split("T");
                                        ItemForFlightDetails item = new ItemForFlightDetails(timeDepartGo[1],  /*origin_airport_Out*/from, timeArivalGo[1], destination_airport_Out, depart+"->"+timeArivalGo[0], total_price, "E",operating_airline_Out,travel_class_Out, booking_code_Out,seats_remaining_Out, "","", "", "", "5:00", "", "E","","", "","");
                                        itens.add(item);
                                        adapterListViewFlightDetails = new AdapterListViewFlightDetails(context, itens);
                                        listView.setAdapter(adapterListViewFlightDetails);


                                    }


                                } catch (Exception exe) {
                                    exe.printStackTrace();
                                }

                                if(saveDb.equals("Save")) {

                                    List<ItemForHistory> history = db.getAllContacts();

                                    Log.d("Insert: ", "Inserting ..");
                                    db.addContact(new ItemForHistory(history.size() + 1, from, to, adults, children, infants, way, depart, arival));
                                }


                            }else{
                                String [] message = json.toString().split("\"");
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Details.this);
                                builder1.setMessage(message[5]);
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                                dialog.cancel();
                                                startActivity(new Intent(Details.this, MainActivity.class));
                                            }
                                        });

//                                builder1.setNegativeButton(
//                                        "Όχι",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            }
                        } else {
                            e.printStackTrace();
                        }
                        pd.cancel();
                    }
                });
    }


}
