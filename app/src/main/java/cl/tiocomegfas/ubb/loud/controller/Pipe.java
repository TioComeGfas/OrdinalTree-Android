package cl.tiocomegfas.ubb.loud.controller;

import android.content.Context;
import android.text.TextUtils;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnSearchPersonListener;
import cl.tiocomegfas.ubb.loud.backend.threads.LoadDataThread;
import cl.tiocomegfas.ubb.loud.backend.threads.SearchPersonThread;

public class Pipe {

    private static Pipe INSTANCE;
    private LoadDataThread loadDataThread;
    private SearchPersonThread searchPersonThread;

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

    public void callSearchPerson(Context context, String[] persons, String personSearch, OnSearchPersonListener listener){
        if(context == null) throw new IllegalArgumentException("El contexto es invalido");
        if(persons == null || persons.length == 0) throw new IllegalArgumentException("persons is invalid or empty");
        if(TextUtils.isEmpty(personSearch)) throw new IllegalArgumentException("personSearch is invalid");
        if(listener == null) throw new IllegalArgumentException("El listener es invalido");

        searchPersonThread = SearchPersonThread.getInstance();
        searchPersonThread.
                setPersons(persons).
                setPersonSearch(personSearch).
                setListener(listener).
                start();
    }


    public void cancellLoadJson(){
        if(loadDataThread == null) throw new IllegalStateException("loadDataThread is invalid");
        loadDataThread.stop();
    }

    public void cancelSearchPerson(){
        if(searchPersonThread == null) throw new IllegalStateException("searchPersonThread is invalid");
        searchPersonThread.stop();
    }
}
