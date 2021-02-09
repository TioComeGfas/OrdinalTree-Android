package cl.tiocomegfas.ubb.loud.backend.tableutil.popup;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.evrencoskun.tableview.TableView;

import cl.tiocomegfas.ubb.loud.R;

public class RowHeaderLongPressPopup extends PopupMenu implements PopupMenu.OnMenuItemClickListener {

    // Menu Item constants
    private static final int SCROLL_COLUMN = 1;
    private static final int SHOWHIDE_COLUMN = 2;
    private static final int REMOVE_ROW = 3;

    @NonNull
    private final TableView mTableView;
    private final int mRowPosition;

    public RowHeaderLongPressPopup(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull TableView tableView) {
        super(viewHolder.itemView.getContext(), viewHolder.itemView);

        this.mTableView = tableView;
        this.mRowPosition = viewHolder.getAdapterPosition();

        initialize();
    }

    private void initialize() {
        createMenuItem();

        this.setOnMenuItemClickListener(this);
    }

    private void createMenuItem() {
        Context context = mTableView.getContext();
        this.getMenu().add(Menu.NONE, SCROLL_COLUMN, 0, context.getString(R.string.scroll_to_column_position));
        this.getMenu().add(Menu.NONE, SHOWHIDE_COLUMN, 1, context.getString(R.string.show_hide_the_column));
        this.getMenu().add(Menu.NONE, REMOVE_ROW, 2, "Remove " + mRowPosition + " position");
        // add new one ...

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        // Note: item id is index of menu item..

        switch (menuItem.getItemId()) {
            case SCROLL_COLUMN:
                mTableView.scrollToColumnPosition(15);
                break;
            case SHOWHIDE_COLUMN:
                int column = 1;
                if (mTableView.isColumnVisible(column)) {
                    mTableView.hideColumn(column);
                } else {
                    mTableView.showColumn(column);
                }

                break;
            case REMOVE_ROW:
                mTableView.getAdapter().removeRow(mRowPosition);
                break;
        }
        return true;
    }

}

