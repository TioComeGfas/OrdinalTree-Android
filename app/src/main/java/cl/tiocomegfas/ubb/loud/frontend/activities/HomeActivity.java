package cl.tiocomegfas.ubb.loud.frontend.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.frontend.fragments.ExperimentFragment;
import cl.tiocomegfas.ubb.loud.frontend.fragments.HomeFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        showHomeFragment();
    }

    public void showHomeFragment(){
        if(homeFragment == null) homeFragment = new HomeFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(frameLayout.getId(),homeFragment);
    }

    public void showImplementationFragment(){

    }

    public void showOrganigramaFragment(){

    }

    public void showExperimentFragment(){
        if(experimentFragment == null) experimentFragment = new ExperimentFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(frameLayout.getId(),experimentFragment);
    }

    public void showAboutFragment(){

    }

    private void configurateBottomNavigationBar(){
        BottomNavigationItem bniHome = new BottomNavigationItem(0,"Inicio");
        BottomNavigationItem bniOrganigrama = new BottomNavigationItem(0,"Organigrama");
        BottomNavigationItem bniExperiment = new BottomNavigationItem(0,"Experimento");
        BottomNavigationItem bniImplementation = new BottomNavigationItem(0,"Implementation");
        BottomNavigationItem bniAbout = new BottomNavigationItem(0,"Acerca de");

        bottomNavigationBar.
                addItem(bniHome).
                addItem(bniImplementation).
                addItem(bniOrganigrama).
                addItem(bniExperiment).
                addItem(bniAbout).
                setFirstSelectedPosition(0).
                initialise();

        bottomNavigationBar.setTabSelectedListener(listenerBottomNavigationBar);
    }
}