package cl.tiocomegfas.ubb.loud.frontend.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;

public class GraphAdapter extends de.blox.graphview.GraphAdapter<GraphView.ViewHolder> {

    private int[] ids;
    private String[] names;

    public GraphAdapter(@NotNull Graph graph, int[] ids, String[] names) {
        super(graph);
        this.ids = ids;
        this.names = names;
    }

    @Override
    public int getCount() {
        return super.getGraph().getNodeCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getGraph().getNodeAtPosition(position);
    }

    @Override
    public boolean isEmpty() {
        return super.getGraph().hasNodes();
    }

    @NonNull
    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.node_graph, viewGroup, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GraphView.ViewHolder viewHolder, @NotNull Object o, int i) {
        ((SimpleViewHolder) viewHolder).bind(ids[i], names[i]);
    }

    static class SimpleViewHolder extends GraphView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_id)
        TextView tvNumber;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_name)
        TextView tvName;

        public SimpleViewHolder(@NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(int number, String nameLastname){
            tvName.setText(nameLastname);
            tvNumber.setText(String.valueOf(number));
        }
    }

}