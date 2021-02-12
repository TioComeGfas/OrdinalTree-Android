package cl.tiocomegfas.ubb.loud.frontend.bottomsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.controller.Manager;
import cl.tiocomegfas.ubb.loud.frontend.activities.HomeActivity;
import cl.tiocomegfas.ubb.loud.frontend.adapter.FilesAdapter;
import cl.tiocomegfas.ubb.loud.frontend.adapter.TreeAdapter;
import cl.tiocomegfas.ubb.loud.frontend.listeners.OnTreeAdapterListener;

public class TreeSelectBottomFragment extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_trees)
    RecyclerView rvTrees;

    private HomeActivity activity;
    private Context context;
    private Unbinder unbinder;
    private String[] trees = new String[]{"Árbol 1", "Árbol 2", "Árbol 3"};

    private final OnTreeAdapterListener listener = new OnTreeAdapterListener() {
        @Override
        public void onClick(int position) {
            activity.runOnUiThread(() -> {
                if(position == 0) activity.setTree(Manager.LOUD_TREE_1);
                else if(position == 1) activity.setTree(Manager.LOUD_TREE_2);
                else activity.setTree(Manager.LOUD_TREE_3);
            });
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trees_bottom, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();
        this.activity = (HomeActivity) getActivity();

        GridLayoutManager layoutManager = new GridLayoutManager(context,3);
        rvTrees.setLayoutManager(layoutManager);

        TreeAdapter treeAdapter = new TreeAdapter(trees, listener);
        rvTrees.setAdapter(treeAdapter);

        rvTrees.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
