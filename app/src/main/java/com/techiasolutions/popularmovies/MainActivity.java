package com.techiasolutions.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MoviesAdaptor.ItemClickListener {

    MoviesAdaptor adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        new FetchMovieTask(MovieType.POPULAR).execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.popular:
                new FetchMovieTask(MovieType.POPULAR).execute();
                return true;
            case R.id.rated:
                new FetchMovieTask(MovieType.RATED).execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public enum MovieType {
        POPULAR, RATED
    }

    private class FetchMovieTask extends AsyncTask<String, Void, MoviesResponse> {

        MovieType mType;
        Call<MoviesResponse> call;

        public FetchMovieTask(MovieType movieType) {
            super();
            mType = movieType;
        }

        @Override
        protected MoviesResponse doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.moviedb_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MoviesService service = retrofit.create(MoviesService.class);

            if (mType == MovieType.POPULAR) {
                call = service.getPopularMovies(getResources().getString(R.string.moviedb_api_key));
            } else {
                call = service.getTopRatedMovies(getResources().getString(R.string.moviedb_api_key));
            }

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                    MoviesResponse jsonResponse = response.body();
                    assert jsonResponse != null;
                    List<MoviesResult> data = jsonResponse.getMoviesResults();
                    adapter = new MoviesAdaptor(data);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                }

            });

            return null;
        }
    }

}
