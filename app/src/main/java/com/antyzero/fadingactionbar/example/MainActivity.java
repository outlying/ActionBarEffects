package com.antyzero.fadingactionbar.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.antyzero.fadingactionbar.FadingActionBar;
import com.antyzero.fadingactionbar.example.adapter.CustomRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final RandomTextGenerator textGenerator = new RandomTextGenerator();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        List<String> data = generateRandomData();

        CustomRecyclerAdapter recyclerAdapter = new CustomRecyclerAdapter( this, data );

        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.recyclerView );

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setAdapter( recyclerAdapter );

        recyclerView.setOnScrollListener( FadingActionBar.create( this, R.color.primary ) );
    }

    /**
     * Random data set for adapter.
     *
     * @return List of strings
     */
    private List<String> generateRandomData() {

        List<String> stringList = new ArrayList<>();

        for ( int i = 0; i < 200; i++ ) {
            stringList.add( textGenerator.generate() );
        }

        return stringList;
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected( item );
    }
}
