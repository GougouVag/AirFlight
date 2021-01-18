package android.uom.gr.airflight;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.koushikdutta.ion.Response;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    //ArrayList<String> cities = new ArrayList<String>();




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        final int[] ICONS = new int[]{
                R.drawable.history,
                R.drawable.search,
                R.drawable.notification2
        };

        //Get reference to your Tablayout
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }
    public static class FragmentFirst extends Fragment {

        MyDBHistorySearch db;

        public static FragmentFirst newInstance(int sectionNumber) {
            FragmentFirst fragment = new FragmentFirst();
            return fragment;
        }

        public FragmentFirst() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.history, container, false);

            db = new  MyDBHistorySearch(getContext());

            AdapterListviewHistory adapterListviewHistory;
            ArrayList<ItemForHistory> itens = new ArrayList<ItemForHistory>();

            ListView listView =(ListView) rootView.findViewById(R.id.listview);

            Log.d("Reading: ", "Reading all contacts..");
            List<ItemForHistory> history = db.getAllContacts();

            for (ItemForHistory his : history) {
                String log = "Id: "+his.getId()+" ,From: " + his.getFrom() + " ,To: " + his.getTo()+" ,Adults: " + his.getAdults_number() + " ,Children: " + his.getChildren_number()+ " ,Infants: " + his.getInfants()+" ,WayIs: " + his.getWay_is() + " ,Depart: " + his.getDepart_day()+ " ,Arrival: " + his.getArrival_day();

                ItemForHistory item = new ItemForHistory(his.getId(), his.getFrom(), his.getTo(), his.getAdults_number(), his.getChildren_number(), his.getInfants(), his.getWay_is(),his.getDepart_day(),his.getArrival_day());
                itens.add(item);

                adapterListviewHistory = new AdapterListviewHistory(getContext(), itens);
                listView.setAdapter(adapterListviewHistory);

                // Writing Contacts to log
                Log.d("Flight SQL ", log);
            }


            return rootView;
        }
    }

    public static class FragmentSecond extends Fragment implements TextWatcher {
        ArrayList<String> citiesFrom = new ArrayList<String>();
        ArrayAdapter<String> adapterFrom;
        ArrayList<String> citiesTo = new ArrayList<String>();
        ArrayAdapter<String> adapterTo;
        ProgressDialog pdFrom;
        AutoCompleteTextView  from;
        AutoCompleteTextView  to;
        int whereIs = 0;

        CalendarDay depart;
        CalendarDay arival;

        TextView departDate;
        TextView arivalDate;
        TextView arivalTextView;

        String fromText="";
        String toText="";


        public static FragmentSecond newInstance(int sectionNumber) {
            FragmentSecond fragment = new FragmentSecond();
            return fragment;
        }

        public FragmentSecond() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.search_fragment, container, false);


            //AutoCompleteTextView
            from = (AutoCompleteTextView)rootView.findViewById(R.id.FromID);
            from.addTextChangedListener(this);

            to = (AutoCompleteTextView)rootView.findViewById(R.id.ToID);
            to.addTextChangedListener(this);

            from.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View arg0, MotionEvent arg1)
                {
                    whereIs = 1;
                    return false;
                }
            });

            to.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View arg0, MotionEvent arg1)
                {
                    whereIs = 2;
                    return false;
                }
            });

            //AutoCompleteTextView

            //Spinner
            final Spinner adultsSpinner = (Spinner) rootView.findViewById(R.id.AdultsSpinnerID);
            Integer[] adultsItems = new Integer[]{0,1,2,3,4,5,6,7,8};
            ArrayAdapter<Integer> adapterAdults = new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_item, adultsItems);
            adultsSpinner.setAdapter(adapterAdults);

            final Spinner childrenSpinner = (Spinner) rootView.findViewById(R.id.ChildrenSpinnerID);
            Integer[] childrenItems = new Integer[]{0,1,2,3,4,5,6,7,8};
            ArrayAdapter<Integer> adapterChildren = new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_item, childrenItems);
            childrenSpinner.setAdapter(adapterChildren);

            final Spinner infantsSpinner = (Spinner) rootView.findViewById(R.id.infantsSpinnerID);
            Integer[] infantsItems = new Integer[]{0,1,2,3,4,5,6,7,8};
            ArrayAdapter<Integer> adapterInfants = new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_item, infantsItems);
            infantsSpinner.setAdapter(adapterInfants);
            //Spinner


            departDate = (TextView) rootView.findViewById(R.id.dateDepartID);
            arivalDate = (TextView) rootView.findViewById(R.id.dateArrivalID);
            arivalTextView = (TextView) rootView.findViewById(R.id.ArivalID);


            //radiaButton

            final RadioButton oneWay = (RadioButton) rootView.findViewById(R.id.radioButtonOneWayID);
            final RadioButton roundTrip = (RadioButton) rootView.findViewById(R.id.radioButtonRoundTripID);

            oneWay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    roundTrip.setChecked(false);
                    arivalDate.setVisibility(View.INVISIBLE);
                    arivalTextView.setVisibility(View.INVISIBLE);
                }
            });

            roundTrip.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    oneWay.setChecked(false);
                    arivalDate.setVisibility(View.VISIBLE);
                    arivalTextView.setVisibility(View.VISIBLE);
                }
            });

            //radiaButton

            //calendar

            departDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.calendar_depart, (ViewGroup) rootView.findViewById(R.id.departCalendar));
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setView(layout);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    MaterialCalendarView calendarView = (MaterialCalendarView) layout.findViewById(R.id.calendarDepart);

                    Button cancel = (Button) layout.findViewById(R.id.cancelID);
                    Button ok = (Button) layout.findViewById(R.id.okID);

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
                        departDate.setText(dateSelect);
                        depart = date;

                        Log.i("Date",String.valueOf(date.getYear()+"-"+String.valueOf(date.getMonth())+"-"+ String.valueOf(date.getDay())));
                        widget.toString();

                    }});

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            departDate.setText("");
                            alertDialog.dismiss();
                        }
                    });


                }
            });

            arivalDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.calendar_arrival, (ViewGroup) rootView.findViewById(R.id.arrivalCalendar));
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
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

                                arivalDate.setText(dateSelect);
                                arival = date;


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
                                arivalDate.setText("");
                                alertDialog.dismiss();
                            }
                        });
                }
            });


            //calendar


            //button

            final Button searchFlights = (Button) rootView.findViewById(R.id.searchFlightsID);

            searchFlights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  if(fromText.length()==0){
                      AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                      builder1.setMessage("Please insert city or airport!");
                      builder1.setCancelable(true);

                      builder1.setPositiveButton(
                              "Ok",
                              new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int id) {

                                      dialog.cancel();
                                  }
                              });

                      AlertDialog alert11 = builder1.create();
                      alert11.show();
                  }else if(toText.length()==0){
                      AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                      builder1.setMessage("Please insert city or airport!");
                      builder1.setCancelable(true);

                      builder1.setPositiveButton(
                              "Ok",
                              new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int id) {

                                      dialog.cancel();
                                  }
                              });

                      AlertDialog alert11 = builder1.create();
                      alert11.show();
                  } else {
                      if (oneWay.isChecked()) {
                          searchForFlight(fromText, toText, adultsSpinner.getSelectedItem().toString(), childrenSpinner.getSelectedItem().toString(),infantsSpinner.getSelectedItem().toString(), "one way", departDate.getText().toString(), "");
                      } else if (roundTrip.isChecked()) {
                          searchForFlight(fromText, toText, adultsSpinner.getSelectedItem().toString(), childrenSpinner.getSelectedItem().toString(),infantsSpinner.getSelectedItem().toString(), "round trip", departDate.getText().toString(), arivalDate.getText().toString());
                      }
                  }

                }
            });

            //
            return rootView;
        }


        private ArrayList<Date> getHolidays(){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
            String dateInString = "2017-01-15";
            Date date = null;
            try {
                date = sdf.parse(dateInString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ArrayList<Date> holidays = new ArrayList<>();
            holidays.add(date);
            return holidays;
        }


        //stelnei ta dedomena
        public void searchForFlight(String from, String to , String adults , String children,String infants , String way ,String depart , String arival){

            Intent intent = new Intent(getContext(), Details.class);

            intent.putExtra("from", from);
            intent.putExtra("to", to);
            intent.putExtra("adults", adults);
            intent.putExtra("children", children);
            intent.putExtra("infants",infants);
            intent.putExtra("way", way);
            intent.putExtra("depart", depart);
            intent.putExtra("arival", arival);
            intent.putExtra("SaveInDB", "Save");

            startActivity(intent);
            //

        }

        public boolean compareDate(CalendarDay dateFirst,CalendarDay dateSecond){

            //elegxos gia tis meres//

            return true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           // Log.i("lam3" , "lam3");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           // Log.i("lam1" , "lam1");
            Log.i("TEXT CHANGED TO: " , s.toString());
            if(!TextUtils.isEmpty(s.toString())) {
                Log.i("TEXT CHANGED TO: 2" , s.toString());
                if(s.toString().length()>=3&& !s.toString().contains("[")){
                    //getNewText(s.toString());

                    pdFrom = new ProgressDialog(getContext());
                    pdFrom.setMessage("Παρακαλώ περιμένετε!");
                    pdFrom.show();
                    String url = "https://api.sandbox.amadeus.com/v1.2/airports/autocomplete?apikey=lnHOL4YI5vaCi5pxxcJrbfrHqqZ7glEY&term="+s.toString();
                    url = url.replaceAll(" ", "%20");
                    if(url.contains("[")) {
                        url = url.replaceAll(" \\[ ", "");
                        url = url.replaceAll(" \\] ", "");
                    }
                    Log.i("Url", url);
                    Ion.with(this)
                            .load(url)
                            .setLogging("Parsvid", Log.DEBUG)
                            .asJsonArray()
                            .setCallback(new FutureCallback<JsonArray>() {

                                @Override
                                public void onCompleted(Exception e, JsonArray result) {
                                    citiesFrom.clear();
                                    for (int i = 0; i < result.size(); i++) {
                                        //result.get(i).getAsJsonObject();

                                        Log.i("obj", result.get(i).getAsJsonObject().toString());
                                        String splitObj[] = result.get(i).getAsJsonObject().toString().split("\"");
                                        String value = splitObj[3];
                                        String label = splitObj[7];
                                        Log.i("values", value + "," + label);
                                        String valueNew = value + "," + label;
                                        valueNew = valueNew.replaceAll("\\\\", "");

                                        citiesFrom.add(valueNew);

                                    }
                                    pdFrom.cancel();
//                                    for(int i=0; i<citiesFrom.size(); i++)
//                                        Log.i("cities",citiesFrom.get(i).toString());
                                    if(whereIs==1){
                                        Log.i("Lams","mpike1");
                                        adapterFrom= new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,citiesFrom);
                                        from.setAdapter(adapterFrom);
                                        from.showDropDown();
                                    }else if(whereIs ==2){
                                        Log.i("Lams","mpike2");
                                        adapterTo= new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,citiesFrom);
                                        to.setAdapter(adapterTo);
                                        to.showDropDown();
                                    }

//                                    My_arr_adapter.notifyDataSetChanged();

                                }

                            });

                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //Toast.makeText(getContext(), s.toString(), Toast.LENGTH_SHORT).show();
            if(whereIs==1){
               fromText = s.toString();
            }else if(whereIs ==2){
               toText = s.toString();
            }
        }

    }



    public static class FragmentThird extends Fragment {

        MyDBNotifications db;

        public static FragmentThird newInstance(int sectionNumber) {
            FragmentThird fragment = new FragmentThird();
            return fragment;
        }

        public FragmentThird() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            db = new  MyDBNotifications(getContext());

            AdapterListViewNotification adapterListViewNotification;
            ArrayList<ItemForNotification> itens = new ArrayList<ItemForNotification>();

            ListView listView =(ListView) rootView.findViewById(R.id.listview);

            Log.d("Reading: ", "Reading all contacts..");
            List<ItemForNotification> notification = db.getAllContacts();

            for (ItemForNotification noti : notification) {
                String log = "Id: "+noti.getID()+",Date: "+noti.getDate()+", Text: "+noti.getNotificationText();
                ItemForNotification item = new ItemForNotification(+noti.getID(),noti.getDate(),noti.getNotificationText());
                itens.add(item);

                adapterListViewNotification = new AdapterListViewNotification(getContext(), itens);
                listView.setAdapter(adapterListViewNotification);

                // Writing Contacts to log
                Log.d("Flight SQL ", log);
            }

            return rootView;
        }


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    return FragmentFirst.newInstance(0);
                case 1:
                    return FragmentSecond.newInstance(1);
                case 2:
                    return FragmentThird.newInstance(2);
                default:
                    //assume you only have 3
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "HISTORY";
                case 1:
                    return "SEARCH";
                case 2:
                    return "NOTIFICATION";
            }
            return null;
        }
    }

}