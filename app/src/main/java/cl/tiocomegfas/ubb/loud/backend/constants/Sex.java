package cl.tiocomegfas.ubb.loud.backend.constants;

public enum Sex {
    FEMALE(1),
    MALE(2);

    private int id;

    private Sex(int id){
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}
