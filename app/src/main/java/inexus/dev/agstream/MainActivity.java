package inexus.dev.agstream;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import inexus.dev.agstream.Interface.ItemClickListener;
import inexus.dev.agstream.Model.MainData;
import inexus.dev.agstream.ViewHolder.DataViewHolder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference maindata;

    FirebaseRecyclerAdapter<MainData, DataViewHolder> adapter;

    Dialog myDialog;

    RecyclerView recycler_data;
    LinearLayoutManager layoutManager;
//    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("AG Stream");
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);


        //INITIALIZE FIREBASE
        database = FirebaseDatabase.getInstance();
        maindata = database.getReference("MainData");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //SHOW PROGRESS DIALOG WHILE LOGGING IN
        final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
//        mDialog.setTitle("Populating View");
        mDialog.setMessage("Streaming content...");
        mDialog.show();


        //LOAD DATA
        recycler_data = (RecyclerView) findViewById(R.id.recycler_data);
        recycler_data.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recycler_data.setLayoutManager(layoutManager);

        loadMenu();

        //PROGRESS_BAR DELAY FOR 10 SECONDS
        //TO ALLOW CONTENT LOAD
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
            }
        }, 12000);


    }


    private void loadMenu() {

         adapter = new FirebaseRecyclerAdapter<MainData, DataViewHolder>(MainData.class, R.layout.data_item, DataViewHolder.class, maindata) {
            @Override
            public void populateViewHolder(DataViewHolder viewHolder, MainData model, int position) {
                viewHolder.ribbonLayout.setHeaderText(model.getTag());
                if (model.getTag().contentEquals("News")) {
                    viewHolder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#2B323A"));
                } else if (model.getTag().contentEquals("Upcoming Event")) {
                    viewHolder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#FF4081"));
                } else {
                    viewHolder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#34495E"));
                }
                viewHolder.ribbonLayout.setBottomText(model.getTitle());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final MainData clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(MainActivity.this, "" + clickItem.getTitle(), Toast.LENGTH_SHORT).show();

                        //START NEW ACTIVITY
                        Intent streamDetail = new Intent(MainActivity.this, StreamDetail.class);
                        streamDetail.putExtra("Title", adapter.getRef(position).getKey());
                        startActivity(streamDetail);
                    }
                });
            }
        };

        recycler_data.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //DASHBOARD NAVIGATION
        if (id == R.id.nav_Home) {
            //GO TO THE HOME/DASHBOARD SCREEN
            Intent homeIntent = new Intent(this, Dashboard.class);
            startActivity(homeIntent);
        }
        //DONATE ACTIVITY NAVIGATION
        else if (id == R.id.nav_Donate) {
            Toast.makeText(this, "Available Soon", Toast.LENGTH_SHORT).show();

        }
        //STREAM ACTIVITY NAVIGATION
        else if (id == R.id.nav_Stream){
            Intent streamIntent = new Intent(this, MainActivity.class);
            startActivity(streamIntent);
        }
        //CREDITS LAYOUT NAVIGATION
        else if (id == R.id.nav_credits) {

            TextView txtClose;

            myDialog.setContentView(R.layout.creditspopup);
            txtClose = myDialog.findViewById(R.id.txtClose);

                txtClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
        }
        //EXIT TO CLOSE APP OPTION
        else if (id == R.id.nav_exit) {
           Toast.makeText(this, "Available Soon", Toast.LENGTH_SHORT).show();

        }
        //APP INFORMATION NAVIGATION
        else if (id == R.id.nav_info) {
            Toast.makeText(this, "Available Soon", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
