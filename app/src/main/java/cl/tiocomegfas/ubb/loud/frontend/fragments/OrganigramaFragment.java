package cl.tiocomegfas.ubb.loud.frontend.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.tiocomegfas.library.frontend.top_bar.DialogTop;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.constants.Sex;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.controller.Manager;
import cl.tiocomegfas.ubb.loud.frontend.adapter.GraphAdapter;
import cl.tiocomegfas.ubb.loud.frontend.listeners.OnGraphAdapterListener;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class OrganigramaFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.graph)
    GraphView graphView;

    private Unbinder unbinder;
    private Graph graph;
    private int loudTree = Manager.LOUD_TREE_1;

    private final OnGraphAdapterListener listenerGraph = (id,x,y) -> getActivity().runOnUiThread(() -> {
        int fChild = (int)Manager.getInstance().getFirstChild(loudTree,id);
        if(fChild == -1){
            DialogTop.show(getActivity(),"No tiene mas hijos",DialogTop.INFORMATION);
            return;
        }

        int lChild = (int)Manager.getInstance().getSibling(loudTree,fChild);
        updateGraph(fChild,lChild,x,y);
    });

    public OrganigramaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organigrama, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        graph = new Graph();

        final Node node1 = new Node("false root");
        final Node node2 = new Node("root");

        int[] ids = new int[] {0,2};
        String[] names = new String[]{Manager.getInstance().getPersonString(loudTree,0), Manager.getInstance().getPersonString(loudTree,2)};

        graph.addEdge(node1, node2);

        GraphAdapter adapter = new GraphAdapter(graph,ids,names, listenerGraph);
        graphView.setAdapter(adapter);

        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(50)
                .setLevelSeparation(50)
                .setSubtreeSeparation(100)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));
    }

    private void updateGraph(int fChild, int lChild, float _x, float _y){
        List<Node> nodos = graph.getNodes();

        for(final Node nodo: nodos){
            float x = nodo.getX();
            float y = nodo.getY();

            if(_x == x && _y == y){
                int size = lChild - fChild;
                int[] ids = new int[size];
                String[] names = new String[size];

                for(int i = 0; i < size; i++){

                    ids[i] = fChild;
                    names[i] = Manager.getInstance().getPersonString(loudTree,fChild);
                    fChild++;

                    final Node aux = new Node("child"+x+","+y+": "+i);
                    graphView.getAdapter().getGraph().addEdge(nodo,aux);
                }

                ((GraphAdapter) graphView.getAdapter()).updateData(ids,names);
                ((GraphAdapter) graphView.getAdapter()).notifyDataSetChanged();

                //actualizar la informacion del adaptador
                return;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}