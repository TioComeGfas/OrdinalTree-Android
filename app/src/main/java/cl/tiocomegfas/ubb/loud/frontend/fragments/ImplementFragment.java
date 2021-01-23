package cl.tiocomegfas.ubb.loud.frontend.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.tiocomegfas.ubb.loud.R;
import io.github.kbiakov.codeview.CodeView;

public class ImplementFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.code_view)
    CodeView codeView;

    private Unbinder unbinder;

    public ImplementFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_implement, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        codeView.setCode(getString(R.string.code_c_bitarray_h),"C++");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}