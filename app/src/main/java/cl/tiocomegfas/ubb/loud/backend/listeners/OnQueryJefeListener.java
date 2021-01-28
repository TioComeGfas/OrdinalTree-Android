package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnQueryJefeListener extends OnErrorBaseListener, OnRunningBaseListener{
    void onReady(String person, int ID);

}
