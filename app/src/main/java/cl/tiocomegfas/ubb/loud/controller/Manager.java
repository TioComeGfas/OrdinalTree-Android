package cl.tiocomegfas.ubb.loud.controller;

import android.content.Context;

import java.util.LinkedList;

import cl.tiocomegfas.bitarray.LoudsTree;
import cl.tiocomegfas.chronometer.Chronometer;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryCadenaMandoListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryColegasListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryJefeListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQuerySubordinadosListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;

public class Manager {
    public static final int LOUD_TREE_1 = 1;
    public static final int LOUD_TREE_2 = 2;
    public static final int LOUD_TREE_3 = 3;

    private static Manager INSTANCE;
    private LinkedList<Person> persons1;
    private LinkedList<Person> persons2;
    private LinkedList<Person> persons3;
    private Chronometer chronometerTree1;
    private Chronometer chronometerTree2;
    private Chronometer chronometerTree3;
    private LoudsTree loudTree1;
    private LoudsTree loudTree2;
    private LoudsTree loudTree3;

    private Manager(){

    }

    public static Manager getInstance(){
        if(INSTANCE == null) INSTANCE = new Manager();
        return INSTANCE;
    }

    public Manager setPersons(int loudTree, LinkedList<Person> persons) {
        if(loudTree < 1 || loudTree > 3) throw new IllegalArgumentException("loudTree < 1 | loudTree > 3");

        if(loudTree == 1) this.persons1 = persons;
        else if(loudTree == 2) this.persons2 = persons;
        else this.persons3 = persons;
        return this;
    }

    public LinkedList<Person> getPersons(int loudTree) {
        if(loudTree < 1 || loudTree > 3) throw new IllegalArgumentException("loudTree < 1 | loudTree > 3");

        if(loudTree == 1) return this.persons1;
        else if(loudTree == 2) return this.persons2;
        else return this.persons3;
    }

    public String getPersonString(int loudTree, int index){
        if(loudTree < 1 || loudTree > 3) throw new IllegalArgumentException("loudTree < 1 | loudTree > 3");

        if(loudTree == 1) return this.persons1.get(index).toString();
        else if(loudTree == 2) return this.persons2.get(index).toString();
        else return this.persons3.get(index).toString();
    }

    public Person[] getPersons(int loudTree, int ... values){
        final Person[] personas = new Person[values.length];

        for(int i = 0; i < values.length; i++){
            personas[i] = getPerson(loudTree,values[i]);
        }

        return personas;
    }

    public Person getPerson(int loudTree, int id){
        if(loudTree == LOUD_TREE_1){
            final int size = persons1.size();
            for(int i = 0; i < size; i++){
                Person person = persons1.get(i);
                if(person.getId() == id){
                    return person;
                }
            }
        }else if(loudTree == LOUD_TREE_2){
            final int size = persons2.size();
            for(int i = 0; i < size; i++){
                Person person = persons2.get(i);
                if(person.getId() == id){
                    return person;
                }
            }
        }else if(loudTree == LOUD_TREE_3){
            final int size = persons3.size();
            for(int i = 0; i < size; i++){
                Person person = persons3.get(i);
                if(person.getId() == id){
                    return person;
                }
            }
        }

        throw new IllegalStateException("Not found person");
    }

    public void startChronometer(int loudTree){
        if(loudTree == LOUD_TREE_1){
            if(chronometerTree1 == null) chronometerTree1 = Chronometer.getInstance();
            Chronometer.startClock(chronometerTree1);
        }else if(loudTree == LOUD_TREE_2){
            if(chronometerTree2 == null) chronometerTree2 = Chronometer.getInstance();
            Chronometer.startClock(chronometerTree2);
        }else if(loudTree == LOUD_TREE_3){
            if(chronometerTree3 == null) chronometerTree3 = Chronometer.getInstance();
            Chronometer.startClock(chronometerTree3);
        }else throw new IllegalStateException("loudTree invalido");
    }

    public double stopChronometer(int loudTree){
        if(loudTree == LOUD_TREE_1){
            if(chronometerTree1 == null) throw new IllegalStateException("Chronometer is invalid");
            double request = chronometerTree1.stopClock(chronometerTree1);
            chronometerTree1 = null;
            return request;
        }else if(loudTree == LOUD_TREE_2){
            if(chronometerTree2 == null) throw new IllegalStateException("Chronometer is invalid");
            double request = chronometerTree2.stopClock(chronometerTree2);
            chronometerTree2 = null;
            return request;
        }else if(loudTree == LOUD_TREE_3){
            if(chronometerTree3 == null) throw new IllegalStateException("Chronometer is invalid");
            double request = chronometerTree3.stopClock(chronometerTree3);
            chronometerTree3 = null;
            return request;
        }

        throw new IllegalStateException("loudTree invalido");
    }

    public void buildLoudTree(int loudTree, int countNodes){
        switch (loudTree){
            case LOUD_TREE_1:{
                loudTree1 = new LoudsTree(countNodes);
                break;
            } case LOUD_TREE_2:{
                loudTree2 = new LoudsTree(countNodes);
                break;
            } case LOUD_TREE_3:{
                loudTree3 = new LoudsTree(countNodes);
                break;
            } default:{
                throw new IllegalStateException("loudTree invalido");
            }
        }
    }

    public long getParent(int loudTree, int position){
        LoudsTree loud = getTree(loudTree);
        return loud.parent(position);
    }

    public long getFirstChild(int loudTree, int position){
        LoudsTree loud = getTree(loudTree);
        return loud.firstChild(position);
    }

    public long getSibling(int loudTree, int position){
        LoudsTree loud = getTree(loudTree);
        return loud.nextSibling(position);
    }

    /**
     * Retorna el nombre de los subornidados del nodo position
     * @param loudTree
     * @param position
     * @return
     */
    public void getSubordinados(int loudTree, int position, OnQuerySubordinadosListener listener){
        Pipe.getInstance().callQuerySubordinado(loudTree,position,listener);
    }

    /**
     * Retorna el nombre del jefe del nodo position
     * @param loudTree
     * @param position
     * @return
     */
    public void getJefe(int loudTree, int position, OnQueryJefeListener listener){
        Pipe.getInstance().callQueryJefe(loudTree,position,listener);
    }

    /**
     * Retorna el nombre de todos los colegas del nodo position
     * @param loudTree
     * @param position
     * @return
     */
    public void getColegas(int loudTree, int position, OnQueryColegasListener listener){
        Pipe.getInstance().callQueryColegas(loudTree,position,listener);
    }

    /**
     * Retorna el nombre de los jefes de forma ascendente hasta la raiz
     * @param loudTree
     * @param position
     * @return
     */
    public void getCadenaDeMando(int loudTree, int position, OnQueryCadenaMandoListener listener){
        Pipe.getInstance().callQueryCadenaMando(loudTree, position, listener);
    }

    /**
     * Retorna el nodo que contiene el nombre de la persona buscada
     * el nombre es unico en _todo el arbol
     * INVOCARE UN HILO PARA REALIZAR ESTA TAREA, POR LA POSIBILIDAD DE
     * QUE SE DEMORE UN TIEMPO MAYOR A 3 SEGUNDO Y EVITAR QUE ANDROID MATE EL PROCESO.
     * @param loudTree
     * @return
     */
    public int searchNodo(int loudTree, String name, String lastname){
        return 0;
    }

    /**
     * Retorna el arbol completo para ser mostrado por pantalla
     * @param loudTree
     * @return
     */
    public String printTree(int loudTree){
        LoudsTree loud = getTree(loudTree);
        return loud.printAll();
    }

    private LoudsTree getTree(int loudTree){
        switch (loudTree){
            case LOUD_TREE_1:{
                return loudTree1;
            } case LOUD_TREE_2:{
                return loudTree2;
            } case LOUD_TREE_3:{
                return loudTree3;
            } default:{
                throw new IllegalStateException("loudTree invalido");
            }
        }
    }

}