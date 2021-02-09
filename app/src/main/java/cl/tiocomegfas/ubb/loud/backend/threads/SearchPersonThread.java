package cl.tiocomegfas.ubb.loud.backend.threads;

import android.net.MacAddress;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnSearchPersonListener;
import cl.tiocomegfas.ubb.loud.controller.Manager;

public class SearchPersonThread implements Runnable {

    private static final SearchPersonThread INSTANCE = new SearchPersonThread();
    private Thread thread;
    private OnSearchPersonListener listener;
    private String[] persons;
    private String personSearch;
    private int treeSelect;

    public static SearchPersonThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {
        listener.onRunning();

        Manager.getInstance().startChronometer(treeSelect);
        int find = -1;
        int size = persons.length;
        for(int i = 0; i < size; i++){
            if(persons[i].equalsIgnoreCase(personSearch)) {
                find = i;
                break;
            }
        }

        double time = Manager.getInstance().stopChronometer(treeSelect);

        if(find == -1){
            listener.onError("No encontrado");
            return;
        }

        listener.onReady(find,personSearch, time);
    }

    public SearchPersonThread setListener(OnSearchPersonListener listener) {
        this.listener = listener;
        return this;
    }

    public SearchPersonThread setPersons(String[] persons) {
        this.persons = persons;
        return this;
    }

    public SearchPersonThread setTreeSelect(int treeSelect) {
        this.treeSelect = treeSelect;
        return this;
    }

    public SearchPersonThread setPersonSearch(String personSearch) {
        this.personSearch = personSearch;
        return this;
    }

    public void start(){
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void stop(){
        if(thread == null) return;
        thread.interrupt();
    }
}
