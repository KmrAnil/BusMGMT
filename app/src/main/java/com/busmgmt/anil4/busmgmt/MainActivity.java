package com.busmgmt.anil4.busmgmt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button searchBus,searchRoute;
    private long backPressedTime;
    private  Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBus=findViewById(R.id.searchBus);
        searchRoute=findViewById(R.id.searchRoute);


        searchRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RouteSearch.class));
            }
        });

        searchBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BusSearch.class));
            }
        });



    }

    @Override
    public void onBackPressed() {

        if(backPressedTime+2000> System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
        }else{
            backToast=Toast.makeText(getBaseContext(), "Enter Back again to Exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime =System.currentTimeMillis();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.searchRouteMenu:{
                startActivity(new Intent(MainActivity.this,RouteSearch.class));
                break;
            }
            case R.id.searchBusMenu:{
                startActivity(new Intent(MainActivity.this,BusSearch.class));
                break;
            }
            case R.id.shareMenu:{
                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody="Your Body here";
                String shareSub="Download this Bus Management App for free";
                intent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                intent.putExtra(Intent.EXTRA_TEXT,shareSub);
                startActivity(Intent.createChooser(intent,"Share Using"));
                break;

            }

        }
        return super.onOptionsItemSelected(item);
    }

}
