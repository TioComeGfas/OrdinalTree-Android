package cl.tiocomegfas.ubb.loud.frontend.activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.tiocomegfas.library.frontend.top_bar.DialogTop;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.constants.Codes;
import cl.tiocomegfas.ubb.loud.controller.Manager;
import cl.tiocomegfas.ubb.loud.frontend.bottomsheet.ArchivosBottomFragment;
import cl.tiocomegfas.ubb.loud.frontend.bottomsheet.RequestOperationBottomFragment;
import cl.tiocomegfas.ubb.loud.frontend.bottomsheet.TreeSelectBottomFragment;
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
    @BindView(R.id.ib_action)
    ImageButton ibAction;

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
        public void onTabUnselected(int position) {
        }

        @Override
        public void onTabReselected(int position) {
        }
    };

    private HomeFragment homeFragment;
    private ExperimentFragment experimentFragment;
    private ImplementFragment implementFragment;
    private OrganigramaFragment organigramaFragment;
    private AboutFragment aboutFragment;
    private Fragment activeFragment;
    private BottomSheetDialogFragment bottomSheetFragment;

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configurateBottomNavigationBar();

        homeFragment = new HomeFragment();
        experimentFragment = new ExperimentFragment();
        implementFragment = new ImplementFragment();
        organigramaFragment = new OrganigramaFragment();
        aboutFragment = new AboutFragment();

        fragmentManager.beginTransaction().
                add(R.id.contenedor,homeFragment, "home").
                add(R.id.contenedor,experimentFragment, "experiment").hide(experimentFragment).
                add(R.id.contenedor,implementFragment, "implement").hide(implementFragment).
                add(R.id.contenedor,organigramaFragment, "organigrama").hide(organigramaFragment).
                add(R.id.contenedor,aboutFragment,"about").hide(aboutFragment).
                commit();

        activeFragment = homeFragment;
        showHomeFragment();
    }

    public void showHomeFragment(){
        if(homeFragment == null) homeFragment = new HomeFragment();

        getSupportFragmentManager().
                beginTransaction().
                hide(activeFragment).
                show(homeFragment).
                commit();

        activeFragment = homeFragment;

        if(getSupportActionBar() != null){
            ibAction.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Inicio");

            ColorDrawable colorDrawable = new ColorDrawable(getColor(R.color.md_blue_500));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
            getWindow().setStatusBarColor(getColor(R.color.md_blue_500));
            getWindow().setNavigationBarColor(getColor(R.color.md_blue_500));
        }
    }

    public void showImplementationFragment(){
        if(implementFragment == null) implementFragment = new ImplementFragment();

        getSupportFragmentManager().
                beginTransaction().
                hide(activeFragment).
                show(implementFragment)
                .commit();

        activeFragment = implementFragment;

        if(getSupportActionBar() != null){
            ibAction.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Implementación");

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
                hide(activeFragment).
                show(organigramaFragment)
                .commit();

        activeFragment = organigramaFragment;

        if(getSupportActionBar() != null){
            ibAction.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Organigrama");

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
                hide(activeFragment).
                show(experimentFragment)
                .commit();

        activeFragment = experimentFragment;

        if(getSupportActionBar() != null){
            ibAction.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Experimentación");

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
                hide(activeFragment).
                show(aboutFragment)
                .commit();

        activeFragment = aboutFragment;

        if(getSupportActionBar() != null){
            ibAction.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Acerca de");

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

    public void setCode(String code, String lenguaje){
        hideBottomSheetFragment();
        implementFragment.setCode(code,lenguaje);
    }

    public void setTree(int tree){
        hideBottomSheetFragment();
        organigramaFragment.setTree(tree);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.ib_action)
    void onClickAction(){
        if(activeFragment instanceof ImplementFragment){
            ArchivosBottomFragment fragment = new ArchivosBottomFragment();
            showBottomSheetFragment(fragment);
            return;
        }

        if(activeFragment instanceof OrganigramaFragment){
            TreeSelectBottomFragment fragment = new TreeSelectBottomFragment();
            showBottomSheetFragment(fragment);
        }
    }

    private void showBottomSheetFragment(BottomSheetDialogFragment fragment){
        this.bottomSheetFragment = fragment;
        this.bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
        this.bottomSheetFragment.show(getSupportFragmentManager(),fragment.getClass().getSimpleName());
    }

    private void hideBottomSheetFragment(){
        this.bottomSheetFragment.dismiss();
    }

}