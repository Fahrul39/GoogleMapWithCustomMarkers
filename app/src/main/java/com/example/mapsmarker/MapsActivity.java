package com.example.mapsmarker;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().setTitle("MapMarkers");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_map);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng home = new LatLng(-0.902470, 119.912112);
        LatLng school = new LatLng(-0.899463867885607, 119.90357703261769);

        mMap.addMarker(new MarkerOptions().position(home).title("Rumah").snippet("Disini Saya")
                .icon(BitmapDescriptorFactory
                        .fromBitmap(createCustomMarker(MapsActivity.this,R.drawable.fotoku2))));
        mMap.addMarker(new MarkerOptions().position(school).title("SMPN 7 Palu").snippet("Ini Adalah Sekolahku")
        .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(MapsActivity.this, R.drawable.sekolah))));

       Polyline polyline = mMap.addPolyline(new PolylineOptions().add(
                school,
               new LatLng(-0.8994607937719127, 119.90331220983198),
               new LatLng(-0.899208185879904, 119.90319839330975),
               new LatLng(-0.8990285129572109, 119.90318232618449),
               new LatLng(-0.8988881811593364, 119.90326577697711),
               new LatLng(-0.8987901542988094, 119.90322714990307),
               new LatLng(-0.898170228516488, 119.90316184115842),
               new LatLng(-0.8981411545148336, 119.90327300107097),
               new LatLng(-0.9004609795519294, 119.90607590948007),
               new LatLng(-0.9010456288816224, 119.90661503349496),
               new LatLng(-0.9017268256644011, 119.90714074646382),
               new LatLng(-0.9018689651268484, 119.90740360298437),
               new LatLng(-0.9017751063876984, 119.90824123893199),
               new LatLng(-0.9015247861230105, 119.90948921251304),
               new LatLng(-0.9021088689152168, 119.90961059427042),
               new LatLng(-0.9020595634194308, 119.90995577788253),
               new LatLng(-0.9023819470607197, 119.91006578132081),
               new LatLng(-0.9026019262164597, 119.91212929164392),
                home
                )
        );
       polyline.setTag("A");

       stylePolyline(polyline);
       polyline.setPattern(PATTERN_POLYLINE_DOTTED);

       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 14.5f));

    }

    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource){
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageResource(resource);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;

    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "A":
                // Use a custom bitmap as the cap at the start of the line.
                polyline.setStartCap(
                        new CustomCap(
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow), 13));
                break;
        }

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }

    private static final int PATTERN_GAP_LENGTH_PX = 5;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);
}