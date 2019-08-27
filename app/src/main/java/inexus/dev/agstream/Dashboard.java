package inexus.dev.agstream;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        myDialog = new Dialog(this);
    }

    public void homeIntent(View view){
        Intent goHome = new Intent(Dashboard.this, MainActivity.class);
        startActivity(goHome);
    }

    public void socialIntent(View view){
        Toast.makeText(this, "Social available soon!", Toast.LENGTH_SHORT).show();
    }

    public void ministriesIntent(View view){
        Toast.makeText(this, "Available in next update", Toast.LENGTH_SHORT).show();
    }

    public void donateIntent(View view){
        Toast.makeText(this, "Available in next update", Toast.LENGTH_SHORT).show();
    }

    public void hqIntent(View view){
        Toast.makeText(this, "See you soon!", Toast.LENGTH_SHORT).show();
    }

    public void credits(View view){
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
}
