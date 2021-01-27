package cl.tiocomegfas.ubb.loud.controller;

import java.util.LinkedList;

import cl.tiocomegfas.ubb.cronometer.CronometerAPI;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loudapi.LoudAPI;

public class Manager {

    public static final int LOUD_TREE_1 = 1;
    public static final int LOUD_TREE_2 = 2;
    public static final int LOUD_TREE_3 = 3;

    private static Manager INSTANCE;
    private LinkedList<Person> persons1;
    private LinkedList<Person> persons2;
    private LinkedList<Person> persons3;
    private CronometerAPI chronometerTree1;
    private CronometerAPI chronometerTree2;
    private CronometerAPI chronometerTree3;
    private LoudAPI loudTree1;
    private LoudAPI loudTree2;
    private LoudAPI loudTree3;

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

    public String getPerson(int loudTree, int index){
        if(loudTree < 1 || loudTree > 3) throw new IllegalArgumentException("loudTree < 1 | loudTree > 3");

        if(loudTree == 1) return this.persons1.get(index).toString();
        else if(loudTree == 2) return this.persons2.get(index).toString();
        else return this.persons3.get(index).toString();
    }

    public void startChronometerTree1(){
        if(chronometerTree1 == null) chronometerTree1 = CronometerAPI.getInstance();
        chronometerTree1.startClock(chronometerTree1);
    }

    public void startChronometerTree2(){
        if(chronometerTree2 == null) chronometerTree2 = CronometerAPI.getInstance();
        chronometerTree2.startClock(chronometerTree2);
    }

    public void startChronometerTree3(){
        if(chronometerTree3 == null) chronometerTree3 = CronometerAPI.getInstance();
        chronometerTree3.startClock(chronometerTree3);
    }

    public double stopChronometerTree1(){
        if(chronometerTree1 == null) throw new IllegalStateException("Chronometer is invalid");
        double request = chronometerTree1.stopClock(chronometerTree1);
        chronometerTree1 = null;
        return request;

    }

    public double stopChronometerTree2(){
        if(chronometerTree2 == null) throw new IllegalStateException("Chronometer is invalid");
        double request = chronometerTree2.stopClock(chronometerTree2);
        chronometerTree2 = null;
        return request;
    }

    public double stopChronometerTree3(){
        if(chronometerTree3 == null) throw new IllegalStateException("Chronometer is invalid");
        double request = chronometerTree3.stopClock(chronometerTree3);
        chronometerTree3 = null;
        return request;
    }

    public void buildLoudTree1(int countNodes){
        if(loudTree1 == null) loudTree1 = new LoudAPI(countNodes);
        loudTree1.init(countNodes);
    }

    public void buildLoudTree2(int countNodes){
        if(loudTree2 == null) loudTree2 = new LoudAPI(countNodes);
        loudTree2.init(countNodes);
    }

    public void buildLoudTree3(int countNodes){
        if(loudTree3 == null) loudTree3 = new LoudAPI(countNodes);
        loudTree3.init(countNodes);
    }

    /**
     * Retorna el nombre de los subornidados del nodo position
     * @param loudTree
     * @param position
     * @return
     */
    public String[] getSubordinados(int loudTree, int position){

        return null;
    }

    /**
     * Retorna el nombre del jefe del nodo position
     * @param loudTree
     * @param position
     * @return
     */
    public String getJefe(int loudTree, int position){
        return null;
    }

    /**
     * Retorna el nombre de todos los colegas del nodo position
     * @param loudTree
     * @param position
     * @return
     */
    public String getColegas(int loudTree, int position){
        return null;
    }

    /**
     * Retorna el nombre de los jefes de forma ascendente hasta la raiz
     * @param loudTree
     * @param position
     * @return
     */
    public String getCadenaDeMando(int loudTree, int position){
        return null;
    }

    /**
     * Retorna el nodo que contiene el nombre de la persona buscada
     * el nombre es unico en _todo el arbol
     * INVOCARE UN HILO PARA REALIZAR ESTA TAREA, POR LA POSIBILIDAD DE
     * QUE SE DEMORE UN TIEMPO MAYOR A 3 SEGUNDO Y EVITAR QUE ANDROID MATE EL PROCESO.
     * @param loudTree
     * @param name
     * @param lastName
     * @return
     */
    public int searchNodo(int loudTree, String name, String lastName){
        return 0;
    }

    /**
     * Retorna el arbol completo para ser mostrado por pantalla
     * @param loudTree
     * @return
     */
    public String printTree(int loudTree){
        return null;
    }

    public String getFirstChild(int loudTree, int position){
        if(loudTree <= 0 || loudTree > 3) throw new IllegalArgumentException("loudTree > 0 && loudTree < 4");

        if(loudTree == LOUD_TREE_1){
            int pos = loudTree1.firstChild(loudTree1.getBitArray(), (int)loudTree1.getLength(),position);
            return loudTree1.data(pos);
        }else if(loudTree == LOUD_TREE_2){
            int pos = loudTree2.firstChild(loudTree1.getBitArray(), (int)loudTree2.getLength(),position);
            return loudTree2.data(pos);
        }else{
            int pos = loudTree3.firstChild(loudTree3.getBitArray(), (int)loudTree3.getLength(),position);
            return loudTree3.data(pos);
        }
    }

    public String getNextSibling(int loudTree, int position){
        if(loudTree <= 0 || loudTree > 3) throw new IllegalArgumentException("loudTree > 0 && loudTree < 4");

        if(loudTree == LOUD_TREE_1){
            int pos = loudTree1.nextSibling(loudTree1.getBitArray(), (int)loudTree1.getLength(),position);
            return loudTree1.data(pos);
        }else if(loudTree == LOUD_TREE_2){
            int pos = loudTree2.nextSibling(loudTree1.getBitArray(), (int)loudTree2.getLength(),position);
            return loudTree2.data(pos);
        }else{
            int pos = loudTree3.nextSibling(loudTree3.getBitArray(), (int)loudTree3.getLength(),position);
            return loudTree3.data(pos);
        }
    }

    public String getParent(int loudTree, int position){
        if(loudTree <= 0 || loudTree > 3) throw new IllegalArgumentException("loudTree > 0 && loudTree < 4");

        if(loudTree == LOUD_TREE_1){
            int pos = loudTree1.parent(loudTree1.getBitArray(), (int)loudTree1.getLength(),position);
            return loudTree1.data(pos);
        }else if(loudTree == LOUD_TREE_2){
            int pos = loudTree2.parent(loudTree1.getBitArray(), (int)loudTree2.getLength(),position);
            return loudTree2.data(pos);
        }else{
            int pos = loudTree3.parent(loudTree3.getBitArray(), (int)loudTree3.getLength(),position);
            return loudTree3.data(pos);
        }
    }

    public String getChild(int loudTree, int position, int indexChild){
        if(loudTree <= 0 || loudTree > 3) throw new IllegalArgumentException("loudTree > 0 && loudTree < 4");

        if(loudTree == LOUD_TREE_1){
            int pos = loudTree1.child(loudTree1.getBitArray(), (int)loudTree1.getLength(),position, indexChild);
            return loudTree1.data(pos);
        }else if(loudTree == LOUD_TREE_2){
            int pos = loudTree2.child(loudTree1.getBitArray(), (int)loudTree2.getLength(),position, indexChild);
            return loudTree2.data(pos);
        }else{
            int pos = loudTree3.child(loudTree3.getBitArray(), (int)loudTree3.getLength(),position, indexChild);
            return loudTree3.data(pos);
        }
    }

}