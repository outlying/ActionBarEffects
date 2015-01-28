package com.antyzero.fadingactionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import java.lang.IllegalStateException;

/**
 *
 */
public class FadingActionBar extends RecyclerView.OnScrollListener {

    public static final int ALPHA_OPAQUE = 255;
    public static final int ALPHA_TRANSPARENT = 0;

    public static final int FADE_DISTANCE_DEFAULT = 300;

    private final RecyclerView.OnScrollListener onScrollListener;

    private final Drawable actionBarDrawable;

    private final int fadeDistance;

    // Tracks vertical scrolling
    private int verticalScroll = 0;

    /**
     * ...
     */
    public FadingActionBar( Builder builder ) {

        this.actionBarDrawable = builder.actionBarDrawable;
        this.fadeDistance = builder.fadeDistance;
        this.onScrollListener = builder.onScrollListener;

        // Assign values

        actionBarDrawable.setAlpha( builder.alphaInitial );
    }

    /**
     * Fast way to build your ScrollListener
     *
     * @param activity
     * @param colorResourceId
     * @return
     */
    public static FadingActionBar create( Activity activity, int colorResourceId ){
        return new Builder( activity, colorResourceId ).build();
    }

    /**
     * Do alpha calculation for scroll amount, based on given fade distance. Also pass call
     * to another listener.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public void onScrolled( RecyclerView recyclerView, int dx, int dy ) {
        super.onScrolled( recyclerView, dx, dy );

        if ( onScrollListener != null ) {
            onScrollListener.onScrolled( recyclerView, dx, dy );
        }

        verticalScroll = verticalScroll + dy;

        int amount = Math.min( fadeDistance, Math.max( verticalScroll, 0 ) );

        float ratio = ( ( (float) amount ) / ( (float) fadeDistance ) );

        actionBarDrawable.setAlpha( (int) ( ratio * ALPHA_OPAQUE ) );
    }

    /**
     * Pass call to another listener if it is not null.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public void onScrollStateChanged( RecyclerView recyclerView, int newState ) {
        super.onScrollStateChanged( recyclerView, newState );

        if ( onScrollListener != null ) {
            onScrollListener.onScrollStateChanged( recyclerView, newState );
        }
    }

    /**
     * Assing given drawable to Activity, check if ActionBar is available (support or native)
     *
     * @param activity
     * @param actionBarDrawable
     * @throws IllegalStateException if Activity does not have ActionBar
     */
    private static void assignDrawableToActionBar( Activity activity, Drawable actionBarDrawable ) throws IllegalStateException {

        ActionBar actionBar = activity.getActionBar();

        android.support.v7.app.ActionBar supportActionBar = null;

        if ( activity instanceof ActionBarActivity ) {

            ActionBarActivity actionBarActivity = (ActionBarActivity) activity;

            supportActionBar = actionBarActivity.getSupportActionBar();
        }

        if ( actionBar == null && supportActionBar == null ) {
            throw new IllegalStateException( "Activity requires ActionBar" );
        }

        if ( actionBar != null ) {
            actionBar.setBackgroundDrawable( actionBarDrawable );
        } else {
            supportActionBar.setBackgroundDrawable( actionBarDrawable );
        }
    }

    /**
     * ...
     */
    public static final class Builder {

        private final Drawable actionBarDrawable;
        private int fadeDistance;

        private int alphaInitial = ALPHA_TRANSPARENT;

        private RecyclerView.OnScrollListener onScrollListener;

        /**
         * Minimal constructor for scroll listener.
         *
         * @param activity          access to various resources
         * @param actionBarDrawable for alpha animation
         */
        public Builder( @NonNull Activity activity, @NonNull Drawable actionBarDrawable ) {

            assignDrawableToActionBar( activity, actionBarDrawable );

            this.actionBarDrawable = actionBarDrawable;

            this.fadeDistance = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, FADE_DISTANCE_DEFAULT, activity.getResources().getDisplayMetrics() );
        }

        /**
         * New builder based on given resource color, eg. material primary color.
         *
         * @param activity        access to various resources
         * @param colorResourceId for ActionBar color
         */
        public Builder( @NonNull Activity activity, int colorResourceId ) {
            this( activity, new ColorDrawable( activity.getResources().getColor( colorResourceId ) ) );
        }

        /**
         * Sets fade distance, after scrolling for given distance ActionBar should be fully opaque
         * again.
         * <p/>
         * It is not required to set this value but it is highly recommended. If not set builder
         * will use default value of
         * {@value com.antyzero.fadingactionbar.FadingActionBar#FADE_DISTANCE_DEFAULT} dp units.
         *
         * @param value for alpha calculation, should be positive
         * @return Builder object
         */
        public Builder setFadeDistance( int value ) {

            if ( value < 0 ) {
                throw new IllegalArgumentException( "Fade distance can not have negative value" );
            }

            this.fadeDistance = value;
            return this;
        }

        /**
         * Pass events to another listener.
         *
         * @param onScrollListener listener, null value allowed
         * @return Builder object
         */
        public Builder setOnScrollListener( RecyclerView.OnScrollListener onScrollListener ) {
            this.onScrollListener = onScrollListener;
            return this;
        }

        /**
         * Sets drawable alpha value for given value after building this listener. It might be
         * helpful in some situations.
         * <p/>
         * Recommended to not use it, alpha of drawable normally will be set to transparent after
         * building this listener.
         *
         * @param alpha initial value, in range [0..255]
         * @return Builder object
         */
        public Builder setAlphaInitial( int alpha ) {

            if ( alpha < ALPHA_TRANSPARENT || alpha > ALPHA_OPAQUE ) {
                throw new IllegalArgumentException( "Alpha value should be in range [0..255]" );
            }

            this.alphaInitial = alpha;
            return this;
        }
        /**
         * Creates new listener.
         *
         * @return RecyclerView.OnScrollListener object
         */
        public FadingActionBar build() {
            return new FadingActionBar( this );
        }

    }
}
