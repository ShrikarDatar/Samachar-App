package com.example.samachar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.samachar.parameter.Articles;
import com.example.samachar.parameter.Headlines;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    final  String API_KEY="ff5a22b4b3464fa6ad5fb4fb848578e4";
    Button button;
    List<Articles> articles=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        button=findViewById(R.id.refreshButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country=getCountry();

        fetchJSON(country,API_KEY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchJSON(country,API_KEY);
            }
        });
    }
    public  void fetchJSON(String country,String apiKey)
    {
        Call<Headlines> call=Client.getInstance().getApi().getHeadlines(country,apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles()!=null){
                    articles.clear();
                    articles=response.body().getArticles();

                    adapter =new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {

                Toast.makeText(MainActivity.this,"There is An Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getCountry()
    {
        Locale locale=Locale.getDefault();
        String country=locale.getCountry();
        return country.toLowerCase();
    }
}
