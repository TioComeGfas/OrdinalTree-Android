package cl.tiocomegfas.ubb.loud.backend.tableutil.model;

import androidx.annotation.Nullable;

public class Cell {

    @Nullable
    private final Object data;

    public Cell(@Nullable Object data) {
        this.data = data;
    }

    @Nullable
    public Object getData() {
        return data;
    }
}
