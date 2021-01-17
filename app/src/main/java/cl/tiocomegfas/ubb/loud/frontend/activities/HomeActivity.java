package cl.tiocomegfas.ubb.loud.frontend.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.contenedor)
    FrameLayout frameLayout;

    private OnLoadDataListener listenerLoadData = new OnLoadDataListener() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    public void showHomeFragment(){

    }

    public void show1kFragment(){

    }

    public void show10kFragment(){

    }

    public void show100kFragment(){

    }

}