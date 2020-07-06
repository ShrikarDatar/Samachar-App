package com.example.samachar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.samachar.parameter.Articles;
import com.example.samachar.parameter.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private Adapter adapter;
final String API_KEY= "ff5a22b4b3464fa6ad5fb4fb848578e4";
Button refreshButton;
List<Articles>articles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=  findViewById(R.id.recycler);
        refreshButton = findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(MainActivity.this,articles);
        recyclerView.setAdapter(adapter);
        final String country = getCountry();

        fetchJSON(country,API_KEY);

    }
    private void fetchJSON(String country, final String api_key)
    {
        Call<Headlines> call = Client.getInstance().getApi().getHeadlines(country,API_KEY);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null)
                {
                    articles.clear();
                    articles=response.body().getArticles();
                    adapter = new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check your Internet", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private String getCountry()
    {
        Locale locale=Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}