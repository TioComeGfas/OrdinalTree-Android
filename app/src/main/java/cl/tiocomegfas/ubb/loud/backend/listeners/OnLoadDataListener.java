package cl.tiocomegfas.ubb.loud.backend.listeners;

import java.util.LinkedList;

import cl.tiocomegfas.ubb.loud.backend.model.Person;

public interface OnLoadDataListener {
    void onRunning();
    void onReady(LinkedList<Person> persons);
    void onError(String message);
}
