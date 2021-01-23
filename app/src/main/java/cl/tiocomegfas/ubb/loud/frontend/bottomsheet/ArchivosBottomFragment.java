package cl.tiocomegfas.ubb.loud.frontend.bottomsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.frontend.adapter.FilesAdapter;
import cl.tiocomegfas.ubb.loud.frontend.listeners.OnFilesAdapterListener;

public class ArchivosBottomFragment extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_archivos)
    RecyclerView rvArchivos;

    private Context context;
    private Unbinder unbinder;
    private final String[] filesArray = {
            "BitArray.h",
            "BitArray.cpp",
            "RankSelect.h",
            "RankSelect.cpp",
            "IndexOutOfBoundsException.h",
            "OrdinalTree.h",
            "OrdinalTree.cpp",
            "loud-lib.cpp",
            "Cronometer.h",
            "Cronometer.cpp",
            "Cronometer-lib.cpp"
    };

    private final OnFilesAdapterListener listener = position -> {
        //retorna la posicion que el usuario seleccion de la lista
    };

    public ArchivosBottomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archivos_bottom, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvArchivos.setLayoutManager(linearLayoutManager);

        FilesAdapter filesAdapter = new FilesAdapter(filesArray,listener);
        rvArchivos.setAdapter(filesAdapter);

        rvArchivos.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}