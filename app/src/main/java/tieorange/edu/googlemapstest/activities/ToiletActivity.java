package tieorange.edu.googlemapstest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class ToiletActivity extends AppCompatActivity {


    private TextView mUiTextViewName;
    private TextView mUiTextViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toilet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUiTextViewName = (TextView) findViewById(R.id.toilet_name);
        mUiTextViewDescription = (TextView) findViewById(R.id.toilet_description);

        setupFab();

        //intent
        Intent intent = getIntent();
        MyMarker myMarker = (MyMarker) intent.getSerializableExtra("name");

        mUiTextViewName.setText(myMarker.getLabel());
        mUiTextViewDescription.setText("Opened: 8:00 - 23:00");

    }

    private void setupFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
