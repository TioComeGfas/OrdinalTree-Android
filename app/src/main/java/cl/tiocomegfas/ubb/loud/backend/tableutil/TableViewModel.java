package cl.tiocomegfas.ubb.loud.backend.tableutil;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cl.tiocomegfas.ubb.loud.backend.tableutil.model.Cell;
import cl.tiocomegfas.ubb.loud.backend.tableutil.model.ColumnHeader;
import cl.tiocomegfas.ubb.loud.backend.tableutil.model.RowHeader;

public class TableViewModel {

    public static final int COLUMN_SIZE = 3;
    public static final int ROW_SIZE = 4;

    public TableViewModel() { }

    @NonNull
    public List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        RowHeader header;

        //segunda fila
        header = new RowHeader("0", "first-child(x)");
        list.add(header);

        //tercera fila
        header = new RowHeader("1", "next-sibling(x)");
        list.add(header);

        //cuarta fila
        header = new RowHeader("2", "parent(x)");
        list.add(header);

        //quinta fila
        header = new RowHeader("3", "data(x)");
        list.add(header);

        return list;
    }

    @NonNull
    public List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        ColumnHeader header;

        header = new ColumnHeader("0", "Tiempo árbol 1");
        list.add(header);

        header = new ColumnHeader("1", "Tiempo árbol 2");
        list.add(header);

        header = new ColumnHeader("2", "Tiempo árbol 3");
        list.add(header);

        return list;
    }

    @NonNull
    public List<List<Cell>> getCellList(double[][] times){
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                Object text = times[i][j];
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

}
