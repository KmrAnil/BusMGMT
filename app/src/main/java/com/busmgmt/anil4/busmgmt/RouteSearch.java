package com.busmgmt.anil4.busmgmt;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RouteSearch extends AppCompatActivity {
    private EditText routeNumber;
    private Button routeBtn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mlayoutManger;
    ArrayList<String> mcontact;
    private  int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_search);
        routeBtn= findViewById(R.id.routeBtn);
        routeNumber=findViewById(R.id.routeNumber);


        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard();
                try{
                    result = Integer.parseInt(routeNumber.getText().toString());
                    if(result<=4){
                        FirebaseFirestore.getInstance().collection("station").document("route_number").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    RecyclerView recyclerView =findViewById(R.id.recycler_view);
                                    recyclerView.setHasFixedSize(true);
                                    mcontact = (ArrayList<String>) document.get(routeNumber.getText().toString());
                                    mlayoutManger=new LinearLayoutManager(RouteSearch.this);
                                    adapter=new MainAdapter(mcontact);
                                    recyclerView.setLayoutManager(mlayoutManger);
                                    recyclerView.setAdapter(adapter);
                                }else {
                                    Toast.makeText(RouteSearch.this, "Not Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(RouteSearch.this, "Route Does not Exist", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(RouteSearch.this, "e", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menur,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeMenu:{
                startActivity(new Intent(RouteSearch.this,MainActivity.class));
                break;
            }
            case R.id.searchBusMenu:{
                startActivity(new Intent(RouteSearch.this,BusSearch.class));
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void dismissKeyboard() {
        View view =this.getCurrentFocus();
        if(view!=null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }
}
