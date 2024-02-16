package com.example.googlemaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mapa;
    private HashMap<String, String[]> infoMarkers;
//elaboracion de los puntos fijos de informacion dentro de nuestrainterfaz virtual del mapa
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(true);
// se asgunara facultad como cada uno de las varianles a reemplazar
        LatLng facultad1 = new LatLng(-1.012859, -79.469363);
        LatLng facultad2 = new LatLng(-1.012269, -79.470227);
        LatLng facultad3 = new LatLng(-1.012590, -79.470522);
        LatLng facultad4 = new LatLng(-1.012569, -79.471015);
        float colorMarcador = BitmapDescriptorFactory.HUE_RED;

        agregarMarcador(facultad1, "Facultad de Ciencias de la Salud", colorMarcador);
        agregarMarcador(facultad2, "Facultad de Ciencias Empresariales", colorMarcador);
        agregarMarcador(facultad3, "Facultad de Ciencias Sociales, Económicas y Financieras", colorMarcador);
        agregarMarcador(facultad4, "Facultad de Ciencias de la Educación", colorMarcador);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(facultad1, 18);
        mapa.animateCamera(cameraUpdate);
//mediante este fragmento estamos permitiendo que el usuario pueda tener una ventana emergente
        // que le permita ver la informacion de un punto estatico
        mapa.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.facultad, null);
                TextView titleTextView = view.findViewById(R.id.facul);
                TextView infoTextView = view.findViewById(R.id.informacion);
                ImageView imageView = view.findViewById(R.id.ImgFacultad);

                String[] info = infoMarkers.get(marker.getTitle());
                if (info != null) {
                    titleTextView.setText(marker.getTitle());
                    infoTextView.setText(info[0]);
                    imageView.setImageResource(Integer.parseInt(info[1]));
                }

                return view;
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//añadimos todos los datos necesario para dar la informacion de la diferentes facultades
        infoMarkers = new HashMap<>();
        infoMarkers.put("Facultad de Ciencias de la Salud", new String[]{"Crear profesionales de la salud completamente capacitados con fundamentos científicos y " +
                "humanísticos sólidos, que les capaciten para abordar las necesidades de salud ofreciendo  " +
                "Aportan su conocimiento al avance de la investigación, la ciencia y la innovación, mejorando así las condiciones de bienestar de la comunidad y " +
                " asegurando el derecho a la salud..", String.valueOf(R.drawable.portada)});
        infoMarkers.put("Facultad de Ciencias Empresariales", new String[]{"La Facultad de Ciencias Empresariales de la UTEQ aspira a ser pionera en los ámbitos académico,  " +
                "de investigación, extensión y gestión, abarcando los niveles Técnicos, Tecnológicos, Pregrado y Postgrado. Su objetivo es integrar eficazmente recursos  " +
                "humanos al entorno productivo científico, técnico y social, en consonancia con las demandas de una sociedad competitiva.", String.valueOf(R.drawable.empresariales)});
        infoMarkers.put("Facultad de Ciencias Sociales, Económicas y Financieras", new String[]{"La Facultad de Ciencias Sociales Económicas y Financieras forma profesionales " +
                "comprometidos con la comunidad, con suficientes bases teóricas y prácticas sobre el mejor uso de los recursos planteando soluciones a los problemas que impiden " +
                "alcanzar el bienestar económico y financiero de toda la sociedad.", String.valueOf(R.drawable.sociyeco)});
        infoMarkers.put("Facultad de Ciencias de la Educación", new String[]{"Luego de un año de creación la Facultad de Ciencias de la Educación (FCEdu) cuenta con más de 500 " +
                "estudiantes.", String.valueOf(R.drawable.educa)});

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void agregarMarcador(LatLng posicion, String titulo, float color) {
        mapa.addMarker(new MarkerOptions()
                .position(posicion)
                .title(titulo)
                .icon(BitmapDescriptorFactory.defaultMarker(color)));
    }
}