package cl.tiocomegfas.ubb.loud.controller;

import java.util.LinkedList;

import cl.tiocomegfas.ubb.loud.backend.model.Person;

public class Manager {

    private static Manager INSTANCE;
    private LinkedList<Person> persons;

    private Manager(){ }

    public static Manager getInstance(){
        if(INSTANCE == null) INSTANCE = new Manager();
        return INSTANCE;
    }

    public Manager setPersons(LinkedList<Person> persons) {
        this.persons = persons;
        return this;
    }

    public LinkedList<Person> getPersons() {
        if(persons == null) persons = new LinkedList<>();
        return persons;
    }
}
