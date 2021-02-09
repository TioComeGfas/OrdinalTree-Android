package cl.tiocomegfas.ubb.loud.controller;

import android.content.Context;
import android.text.TextUtils;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnBuildLoudTreeListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryCadenaMandoListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryColegasListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryJefeListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQuerySubordinadosListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnSearchPersonListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnTableViewGenerateDataListener;
import cl.tiocomegfas.ubb.loud.backend.threads.BuildLoudTreeThread;
import cl.tiocomegfas.ubb.loud.backend.threads.LoadDataThread;
import cl.tiocomegfas.ubb.loud.backend.threads.QueryCadenaMandoThread;
import cl.tiocomegfas.ubb.loud.backend.threads.QueryColegasThread;
import cl.tiocomegfas.ubb.loud.backend.threads.QueryJefeThread;
import cl.tiocomegfas.ubb.loud.backend.threads.QuerySubordinadosThread;
import cl.tiocomegfas.ubb.loud.backend.threads.SearchPersonThread;
import cl.tiocomegfas.ubb.loud.backend.threads.TableViewGenerateDataThread;

public class Pipe {

    private static Pipe INSTANCE;
    private LoadDataThread loadDataThread;
    private SearchPersonThread searchPersonThread;
    private BuildLoudTreeThread buildLoudTreeThread;
    private QueryJefeThread queryJefeThread;
    private QueryCadenaMandoThread queryCadenaMandoThread;
    private QueryColegasThread queryColegasThread;
    private QuerySubordinadosThread querySubordinadosThread;
    private TableViewGenerateDataThread generateDataThread;

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

    public void callSearchPerson(int loudTree, String[] persons, String personSearch, OnSearchPersonListener listener){
        if(persons == null || persons.length == 0) throw new IllegalArgumentException("persons is invalid or empty");
        if(TextUtils.isEmpty(personSearch)) throw new IllegalArgumentException("personSearch is invalid");
        if(listener == null) throw new IllegalArgumentException("El listener es invalido");

        searchPersonThread = SearchPersonThread.getInstance();
        searchPersonThread.
                setTreeSelect(loudTree).
                setPersons(persons).
                setPersonSearch(personSearch).
                setListener(listener).
                start();
    }

    public void callBuildLoud(int loudTree, OnBuildLoudTreeListener listener){
        if(loudTree != Manager.LOUD_TREE_1 && loudTree != Manager.LOUD_TREE_2 && loudTree != Manager.LOUD_TREE_3) throw new IllegalArgumentException("El loudTree es invalido");
        if(listener == null) throw new IllegalArgumentException("El listener es invalido");

        buildLoudTreeThread = BuildLoudTreeThread.getInstance();

        buildLoudTreeThread.
                setLoudTree(loudTree).
                setListener(listener).
                start();
    }

    public void callQueryJefe(int loudTree, int position, OnQueryJefeListener listener){
        queryJefeThread = QueryJefeThread.getInstance();
        queryJefeThread.
                setLoudTree(loudTree).
                setPosition(position).
                setListener(listener).
                start();
    }

    public void callQuerySubordinado(int loudTree, int position, OnQuerySubordinadosListener listener){
        querySubordinadosThread = QuerySubordinadosThread.getInstance();
        querySubordinadosThread.
                setPosition(position).
                setLoudTree(loudTree).
                setListener(listener).
                start();
    }

    public void callQueryCadenaMando(int loudTree, int position, OnQueryCadenaMandoListener listener){
        queryCadenaMandoThread = QueryCadenaMandoThread.getInstance();
        queryCadenaMandoThread.
                setPosition(position).
                setLoudTree(loudTree).
                setListener(listener).
                start();
    }

    public void callQueryColegas(int loudTree, int position, OnQueryColegasListener listener){
        queryColegasThread = QueryColegasThread.getInstance();
        queryColegasThread.
                setPosition(position).
                setLoudTree(loudTree).
                setListener(listener).
                start();
    }

    public void callTableViewGenerateData(OnTableViewGenerateDataListener listener){
        generateDataThread = TableViewGenerateDataThread.getInstance();
        generateDataThread.
                setListener(listener).
                start();
    }



    public void cancelLoadJson(){
        if(loadDataThread == null) throw new IllegalStateException("loadDataThread is invalid");
        loadDataThread.stop();
    }

    public void cancelSearchPerson(){
        if(searchPersonThread == null) throw new IllegalStateException("searchPersonThread is invalid");
        searchPersonThread.stop();
    }

    public void cancelBuildLoudTree(){
        if(buildLoudTreeThread == null) throw new IllegalStateException("buildLoudTreeThread is invalid");
        buildLoudTreeThread.stop();
    }
}
