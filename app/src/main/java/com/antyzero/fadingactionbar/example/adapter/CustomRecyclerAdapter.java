package com.antyzero.fadingactionbar.example.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antyzero.fadingactionbar.example.R;

import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<String> stringList;
    private final Typeface typeFace;

    public CustomRecyclerAdapter(Context context, List<String> stringList) {
        this.stringList = stringList;
        layoutInflater = LayoutInflater.from( context );

        typeFace = Typeface.createFromAsset( context.getAssets(), "fonts/RedactedScript-Regular.ttf" );
    }

    @Override
    public CustomRecyclerAdapter.CustomViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType ) {
        return new CustomViewHolder( layoutInflater.inflate( R.layout.adapter_item, viewGroup, false ), typeFace );
    }

    @Override
    public void onBindViewHolder( CustomViewHolder customViewHolder, int position ) {
        customViewHolder.textView.setText( stringList.get( position ) );
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    /**
     * ViewHolder pattern
     */
    protected static final class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public CustomViewHolder( View itemView, Typeface typeface ) {
            super( itemView );

            textView = (TextView) itemView.findViewById( R.id.textView );
            textView.setTypeface( typeface );
        }
    }
}
