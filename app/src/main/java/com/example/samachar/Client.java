package com.example.samachar;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final String BASE_URL = "http://newsapi.org/v2/";


    private static Retrofit retrofit;

    private static Client client;

    private Client()
    {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static synchronized  Client getInstance() {

        if (client == null) {
            client = new Client();
        }
        return client;
    }
    public Interface getApi()
    {

        return  retrofit.create(Interface.class);
    }
}
