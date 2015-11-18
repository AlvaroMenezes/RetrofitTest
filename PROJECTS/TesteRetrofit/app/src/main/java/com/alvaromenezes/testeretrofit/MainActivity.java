package com.alvaromenezes.testeretrofit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

import android.util.Log;

public class MainActivity extends AppCompatActivity {


    //http://jsonplaceholder.typicode.com/posts/1    photos

String url = "http://jsonplaceholder.typicode.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        new BackgroundTask().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public interface JsonPlaceHolder {

        @GET("/posts")
        Call<List<Post>> getPosts();



    }



     public class Post{
         public int getUserId() {
             return userId;
         }

         public void setUserId(int userId) {
             this.userId = userId;
         }

         public int getId() {
             return id;
         }

         public void setId(int id) {
             this.id = id;
         }

         public String getTitle() {
             return title;
         }

         public void setTitle(String title) {
             this.title = title;
         }

         public String getBody() {
             return body;
         }

         public void setBody(String body) {
             this.body = body;
         }

         int userId;
        int id;
        String title;
        String  body;


    }





    private class BackgroundTask extends AsyncTask<Void, Void,
                Void> {
       // RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
           // restAdapter = new RestAdapter.Builder()
             //       .setEndpoint(API_URL)
               //     .build();
        }

        @Override
        protected Void doInBackground(Void... params) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory (GsonConverterFactory.create())
                    .build();


            JsonPlaceHolder  api = retrofit.create(JsonPlaceHolder.class);





            Call<List<Post>> call = api.getPosts();
            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Response<List<Post>> response, Retrofit retrofit) {


                   int i =  response.code();
                    if (response.isSuccess()) {
                        // tasks available

                        Log.e("TAG", i +"");


                        for(Post P :response.body()) {
                            Log.e("TAG", P.getId() +"<>" +P.getBody());
                        }





                    } else {
                        // error response, no access to resource?
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    // something went completely south (like no internet connection)
                   // Log.d("Error", t.getMessage());
                }
            });



            return null;
        }

        @Override
        protected void onPostExecute(Void oid) {




        }
    }




}




