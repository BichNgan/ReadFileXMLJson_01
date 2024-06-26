package vlu.android.readfilexmljson_01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;

public class NVDetailActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nvdetail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //---------------
        Intent intent=getIntent();
        NV nv = (NV) intent.getExtras().getSerializable("nv");

        String hten = nv.getHten();
        //---------------------
        addControl();
        int idAnh = getResources().getIdentifier(nv.getIdAnh(),"drawable",getPackageName());
        imageView.setImageResource(idAnh);

    }
    void addControl()
    {
        imageView=(ImageView) findViewById(R.id.imageView);
    }
}