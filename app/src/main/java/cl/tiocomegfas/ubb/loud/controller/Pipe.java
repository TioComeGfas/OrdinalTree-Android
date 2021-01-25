package cl.tiocomegfas.ubb.loud.controller;

import android.content.Context;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.threads.LoadDataThread;

public class Pipe {

    private static Pipe INSTANCE;
    private LoadDataThread loadDataThread;

    private Pipe(){ }

    public static Pipe getInstance(){
        if(INSTANCE == null) INSTANCE = new Pipe();
        return INSTANCE;
    }

    public void callLoadJson(Context context, int size, OnLoadDataListener listener){
        if(context == null) throw new IllegalArgumentException("El contexto es invalido");
        if(size <= 0) throw new IllegalArgumentException("size <= 0");
        if(listener == null) throw new IllegalArgumentException("El listener es invalido");

        loadDataThread = LoadDataThread.getInstance();

        loadDataThread.
                setContext(context).
                setSize(size).
                setListener(listener).
                start();
    }

    public void cancellLoadJson(){
        if(loadDataThread == null) return;
        loadDataThread.stop();
    }
}
