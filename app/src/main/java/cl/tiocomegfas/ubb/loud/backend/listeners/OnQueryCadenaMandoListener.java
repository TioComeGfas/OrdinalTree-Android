package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnQueryCadenaMandoListener extends OnErrorBaseListener, OnRunningBaseListener{
    void onReady(int[] ids, String[] persons, double time);
}
