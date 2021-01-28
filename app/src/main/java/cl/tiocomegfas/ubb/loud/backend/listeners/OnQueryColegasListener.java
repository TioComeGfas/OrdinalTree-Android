package cl.tiocomegfas.ubb.loud.backend.listeners;

import java.util.LinkedList;

public interface OnQueryColegasListener extends OnErrorBaseListener, OnRunningBaseListener{
    void onReady(double time, LinkedList<Integer> padres, LinkedList<Integer[]> hijos);
}
