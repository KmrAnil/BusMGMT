package com.busmgmt.anil4.busmgmt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BusSearch extends AppCompatActivity {
    private Spinner source_spinner,destination_spinner;
    private Button search;
    private TextView routeTextView;
    ArrayList<String> route_list;
    private String srcSelectedItem,destSelectionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_search);
        source_spinner=findViewById(R.id.source_spinner);
        search=findViewById(R.id.search);



        routeTextView =findViewById(R.id.routeTextbox);
        destination_spinner=findViewById(R.id.destination_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.station_array, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        source_spinner.setAdapter(adapter);
        destination_spinner.setAdapter(adapter);

        source_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                srcSelectedItem = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(BusSearch.this, "No item selected", Toast.LENGTH_SHORT).show();

            }
        });
        destination_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                destSelectionItem = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(BusSearch.this, "No item selected", Toast.LENGTH_SHORT).show();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("station").document("route_number").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            int srcroot=0,dstroot=0;
                            for(int t=0;t<4;t++) {
                                route_list = (ArrayList<String>) document.get(String.valueOf(t + 1));
                                for (int i = 0; i < route_list.size(); i++) {
                                    if (route_list.get(i).equalsIgnoreCase(srcSelectedItem)) {
                                        srcroot = t+1;
                                    }
                                 }

                                for (int i = 0; i < route_list.size(); i++) {
                                    if (route_list.get(i).equalsIgnoreCase(destSelectionItem)) {
                                        dstroot = t+1;
                                    }
                                }


                                    if (srcroot != 0 && dstroot != 0) {
                                        if (srcSelectedItem != destSelectionItem) {
                                        if (srcroot == dstroot){
                                            routeTextView.setText("Direct Route No " + srcroot);
                                            break;
                                        } else {
                                            routeTextView.setText("No Direct Route Found");
                                        }
                                        }else{
                                            Toast.makeText(BusSearch.this, "Source and Destination must be Different", Toast.LENGTH_SHORT).show();

                                        }
                                }
                            }
                        }else {
                            Toast.makeText(BusSearch.this, "Not Found", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menub,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeMenu:{
                startActivity(new Intent(BusSearch.this,MainActivity.class));
                break;
            }
            case R.id.searchRouteMenu:{
                startActivity(new Intent(BusSearch.this,RouteSearch.class));
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
