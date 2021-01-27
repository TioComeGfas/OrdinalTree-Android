package cl.tiocomegfas.ubb.loud.backend.threads;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnSearchPersonListener;

public class SearchPersonThread implements Runnable {

    private static final SearchPersonThread INSTANCE = new SearchPersonThread();
    private Thread thread;
    private OnSearchPersonListener listener;
    private String[] persons;
    private String personSearch;

    public static SearchPersonThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {
        listener.onRunning();

        int find = -1;
        int size = persons.length;
        for(int i = 0; i < size; i++){
            if(persons[i].equalsIgnoreCase(personSearch)) {
                find = i;
                break;
            }
        }

        if(find == -1){
            listener.onError("No encontrado");
            return;
        }

        listener.onReady(find);
    }

    public SearchPersonThread setListener(OnSearchPersonListener listener) {
        this.listener = listener;
        return this;
    }

    public SearchPersonThread setPersons(String[] persons) {
        this.persons = persons;
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
