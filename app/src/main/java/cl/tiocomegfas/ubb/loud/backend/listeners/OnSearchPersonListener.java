package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnSearchPersonListener {
    void onRunning();
    void onReady(int position, String name, double time);
    void onError(String message);
}
