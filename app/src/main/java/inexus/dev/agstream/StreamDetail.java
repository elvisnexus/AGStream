package inexus.dev.agstream;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import inexus.dev.agstream.Model.MainData;

public class StreamDetail extends AppCompatActivity {

    TextView streamTitle, streamText;
    ImageView streamImage;
    CollapsingToolbarLayout collapsingToolbarLayout;

//    Toolbar streamToolbar;

    String title;

    FirebaseDatabase database;
    DatabaseReference mainData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_detail);

        //INITIALIZE VIEW
        streamTitle = (TextView)findViewById(R.id.txtTitle);
        streamText = (TextView)findViewById(R.id.txtStream);
        streamImage = (ImageView)findViewById(R.id.img_stream);
//        streamToolbar = (Toolbar)findViewById(R.id.toolbarStream);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingAppBar);


        //INITIALIZE FIREBASE
        database = FirebaseDatabase.getInstance();
        mainData = database.getReference("MainData");

        // GET TITLE FROM INTENT
        if (getIntent() != null)
            title = getIntent().getStringExtra("Title");
        if(!title.isEmpty()){
            getDetailStream(title);
        }
    }

    private void getDetailStream(String title) {
        mainData.child(title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MainData mainData = dataSnapshot.getValue(MainData.class);

                //GET IMAGE
                assert mainData != null; //REMOVE THIS CODE IF THERE IS A PROBLEM LATER
                Picasso.with(getBaseContext()).load(mainData.getImage())
                        .into(streamImage);

                collapsingToolbarLayout.setTitle(getTitle());
//                streamToolbar.setTitle(getTitle());

                streamText.setText(mainData.getBody());

                streamTitle.setText(mainData.getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
