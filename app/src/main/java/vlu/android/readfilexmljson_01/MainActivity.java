package vlu.android.readfilexmljson_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvDSNV;
    ArrayList<NV>arrayListDSNV = new ArrayList<>();
    ArrayList<String> dataLView = new ArrayList<>();
    ArrayAdapter<String>adapter;

    String filename  ="dsnv_01.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //-----------------------------------
            lvDSNV=(ListView) findViewById(R.id.lvDSNV);
        //-------------------------------
        //load dữ liệu lên cho LView
        try {
            arrayListDSNV = readDSNV_Json(filename);
            setDataLView(arrayListDSNV);
            adapter = new ArrayAdapter<>(getApplication(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dataLView);
            lvDSNV.setAdapter(adapter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //------------------------
        //Xu ly sự kiện click item mở activity chi tiết của NV

        addEvent();

    }

    ArrayList<NV> readDSNV_Json(String filename) throws IOException, JSONException {
        ArrayList<NV> arrayListDSNV = new ArrayList<>();
        //Đọc dữ liệu json từ file lên cho chuỗi kq
        String kq="";
        InputStream inputStream = getResources().getAssets().open(filename);

        int size = inputStream.available();
        byte[]data = new byte[size];
        inputStream.read(data);
        inputStream.close();

        kq = new String(data,"UTF-8");
        //Tách các object của chuỗi json trong kq vào arrayList
        JSONObject jsonObjectDSNV = new JSONObject(kq);
        JSONArray jsonArrayDSNV = jsonObjectDSNV.getJSONArray("dsnv");
        for(int i=0;i<jsonArrayDSNV.length(); i++)
        {
            JSONObject jsonObjectNV = jsonArrayDSNV.getJSONObject(i);
            NV nv = new NV();
            nv.setMsnv(jsonObjectNV.getString("msnv"));
            nv.setIdAnh(jsonObjectNV.getString("anh"));
            nv.setHten(jsonObjectNV.getString("tensv"));
            nv.setNgaySinh(jsonObjectNV.getString("ngaysinh"));
            nv.setCvu(jsonObjectNV.getString("cvu"));

            arrayListDSNV.add(nv);
        }
        return arrayListDSNV;
    }

    void setDataLView(ArrayList<NV> arrayListDSNV)
    {
        for (NV nv: arrayListDSNV) {
            String st = "MSNV: " + nv.getMsnv() + "--" + "Họ tên: " + nv.getHten();
            dataLView.add(st);
        }
    }

    void addEvent()
    {
        lvDSNV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NV nv = arrayListDSNV.get(i);
                Intent intent = new Intent(MainActivity.this,NVDetailActivity.class);
                intent.putExtra("nv",nv);

                startActivity(intent);
            }
        });
    }





}