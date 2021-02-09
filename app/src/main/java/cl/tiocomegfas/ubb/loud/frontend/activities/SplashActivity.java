package cl.tiocomegfas.ubb.loud.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

import cl.tiocomegfas.library.frontend.top_bar.DialogTop;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnBuildLoudTreeListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.controller.Manager;
import cl.tiocomegfas.ubb.loud.controller.Pipe;

public class SplashActivity extends AppCompatActivity {

    private boolean isCreateloudTree1 = false;
    private boolean isCreateloudTree2 = false;
    private boolean isCreateloudTree3 = false;

    private final OnBuildLoudTreeListener listenerBuildLoudTree = new OnBuildLoudTreeListener() {
        @Override
        public void onRunning() {

        }

        @Override
        public void onReady(int loudTree) {
            runOnUiThread(() -> {
                if(loudTree == Manager.LOUD_TREE_1) isCreateloudTree1 = true;
                else if(loudTree == Manager.LOUD_TREE_2) isCreateloudTree2 = true;
                else isCreateloudTree3 = true;

                if(isCreateloudTree1 && isCreateloudTree2 && isCreateloudTree3){
                    // llamar una vez cargado
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);

                    finish();
                }
            });
        }

        @Override
        public void onError(String message) {
            runOnUiThread(() -> DialogTop.show(SplashActivity.this,"Ocurrio un problema",message, DialogTop.ERROR));
        }
    };
    private final OnLoadDataListener listenerLoadData = new OnLoadDataListener() {
        @Override
        public void onRunning() {
            //activity.runOnUiThread(() -> DialogTop.show(activity,"Generando información", DialogTop.SUCCESS));
        }

        @Override
        public void onReady(LinkedList<Person> persons) {
            runOnUiThread(() -> {
                Log.e("TAG",persons.size()+"");
                int size = persons.size();
                if(size == 1000){
                    //Almacenamiento de las personas generadas
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_1,persons);
                    Pipe.getInstance().callLoadJson(SplashActivity.this,10000,listenerLoadData);

                    //Creacion del arbol con las personas cargadas
                    Pipe.getInstance().callBuildLoud(Manager.LOUD_TREE_1,listenerBuildLoudTree);
                    return;
                }else if(size == 10000){
                    //Almacenamiento de las personas generadas
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_2,persons);
                    Pipe.getInstance().callLoadJson(SplashActivity.this,100000,listenerLoadData);


                    //Creacion del arbol con las personas cargadas
                    Pipe.getInstance().callBuildLoud(Manager.LOUD_TREE_2,listenerBuildLoudTree);
                    return;
                }else if(size == 100000){
                    //Almacenamiento de las personas generadas
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_3,persons);

                    //Creacion del arbol con las personas cargadas
                    Pipe.getInstance().callBuildLoud(Manager.LOUD_TREE_3,listenerBuildLoudTree);
                }
            });
        }

        @Override
        public void onError(String message) {
            runOnUiThread(() -> DialogTop.show(SplashActivity.this,"Ocurrio un problema",message, DialogTop.ERROR));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Pipe.getInstance().callLoadJson(this,1000,listenerLoadData);
    }

}
