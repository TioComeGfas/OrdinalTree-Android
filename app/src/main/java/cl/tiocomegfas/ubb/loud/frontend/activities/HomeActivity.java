package cl.tiocomegfas.ubb.loud.frontend.activities;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.LinkedList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.frontend.fragments.AboutFragment;
import cl.tiocomegfas.ubb.loud.frontend.fragments.ExperimentFragment;
import cl.tiocomegfas.ubb.loud.frontend.fragments.HomeFragment;
import cl.tiocomegfas.ubb.loud.frontend.fragments.ImplementFragment;
import cl.tiocomegfas.ubb.loud.frontend.fragments.OrganigramaFragment;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_navigation)
    BottomNavigationBar bottomNavigationBar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.contenedor)
    FrameLayout frameLayout;

    private static final int POS_HOME = 0;
    private static final int POS_ORGANIGRAMA = 1;
    private static final int POS_EXPERIMENTO = 2;
    private static final int POS_IMPLEMENTACION = 3;
    private static final int POS_ACERCA_DE = 4;

    private final BottomNavigationBar.OnTabSelectedListener listenerBottomNavigationBar = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            switch (position){
                case POS_HOME:{
                    showHomeFragment();
                    break;
                } case POS_ORGANIGRAMA:{
                    showOrganigramaFragment();
                    break;
                } case POS_EXPERIMENTO:{
                    showExperimentFragment();
                    break;
                } case POS_IMPLEMENTACION:{
                    showImplementationFragment();
                    break;
                } case POS_ACERCA_DE:{
                    showAboutFragment();
                    break;
                }
            }
        }

        @Override
        public void onTabUnselected(int position) { }

        @Override
        public void onTabReselected(int position) { }
    };
    private final OnLoadDataListener listenerLoadData = new OnLoadDataListener() {
        @Override
        public void onRunning() {
            runOnUiThread(() -> {

            });
        }

        @Override
        public void onReady(LinkedList<Person> persons) {
            runOnUiThread(() -> {

            });
        }

        @Override
        public void onError(String message) {
            runOnUiThread(() -> {

            });
        }
    };

    private HomeFragment homeFragment;
    private ExperimentFragment experimentFragment;
    private ImplementFragment implementFragment;
    private OrganigramaFragment organigramaFragment;
    private AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configurateBottomNavigationBar();
        showHomeFragment();
    }

    public void showHomeFragment(){
        if(homeFragment == null) homeFragment = new HomeFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(frameLayout.getId(),homeFragment).commit();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Inicio");
            getSupportActionBar().setSubtitle("Presentación de la APP");

            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.md_blue_500));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
            getWindow().setStatusBarColor(getResources().getColor(R.color.md_blue_500));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.md_blue_500));
        }
    }

    public void showImplementationFragment(){
        if(implementFragment == null) implementFragment = new ImplementFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(frameLayout.getId(),implementFragment).commit();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Implementación");
            getSupportActionBar().setSubtitle("Código fuente de la estructura de datos sucinta LOUDS");

            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.md_green_500));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
            getWindow().setStatusBarColor(getResources().getColor(R.color.md_green_500));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.md_green_500));
        }
    }

    public void showOrganigramaFragment(){
        if(organigramaFragment == null) organigramaFragment = new OrganigramaFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(frameLayout.getId(),organigramaFragment).commit();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Organigrama");
            getSupportActionBar().setSubtitle("El organigrama de Coca Cola Company");

            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.md_purple_500));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
            getWindow().setStatusBarColor(getResources().getColor(R.color.md_purple_500));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.md_purple_500));
        }
    }

    public void showExperimentFragment(){
        if(experimentFragment == null) experimentFragment = new ExperimentFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(frameLayout.getId(),experimentFragment).commit();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Experimentación");
            getSupportActionBar().setSubtitle("Experimentos y resultados obtenidos");

            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.md_orange_500));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
            getWindow().setStatusBarColor(getResources().getColor(R.color.md_orange_500));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.md_orange_500));
        }
    }

    public void showAboutFragment(){
        if(aboutFragment == null) aboutFragment = new AboutFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(frameLayout.getId(),aboutFragment).commit();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Acerca de");
            getSupportActionBar().setSubtitle("Codigo libre utilizado e información de la APP");

            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.md_brown_500));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
            getWindow().setStatusBarColor(getResources().getColor(R.color.md_brown_500));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.md_brown_500));
        }
    }

    private void configurateBottomNavigationBar(){
        BottomNavigationItem bniHome = new BottomNavigationItem(R.drawable.ic_home,"Inicio");
        bniHome.setActiveColorResource(R.color.md_blue_500);

        BottomNavigationItem bniOrganigrama = new BottomNavigationItem(R.drawable.ic_organitation,"Organigrama");
        bniOrganigrama.setActiveColorResource(R.color.md_purple_500);

        BottomNavigationItem bniExperiment = new BottomNavigationItem(R.drawable.ic_experiment,"Experimento");
        bniExperiment.setActiveColorResource(R.color.md_orange_500);

        BottomNavigationItem bniImplementation = new BottomNavigationItem(R.drawable.ic_code,"Código fuente");
        bniImplementation.setActiveColorResource(R.color.md_green_500);

        BottomNavigationItem bniAbout = new BottomNavigationItem(R.drawable.ic_about,"Acerca de");
        bniAbout.setActiveColorResource(R.color.md_brown_500);

        bottomNavigationBar.
                addItem(bniHome).
                addItem(bniOrganigrama).
                addItem(bniExperiment).
                addItem(bniImplementation).
                addItem(bniAbout).

                setFirstSelectedPosition(0).
                initialise();

        bottomNavigationBar.setTabSelectedListener(listenerBottomNavigationBar);
    }
}