package com.termux.app.terminal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.termux.view.TerminalView;
import com.termux.view.TerminalViewClient;
import com.termux.app.TermuxActivity;

public class TerminalFragment extends Fragment {

    private TerminalView mTerminalView;
    private TerminalViewClient mTerminalViewClient;
    private TermuxActivity mTermuxActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.termux.R.layout.activity_termux, container, false);
        mTerminalView = rootView.findViewById(com.termux.R.id.terminal_view);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof TermuxActivity) {
            mTermuxActivity = (TermuxActivity) getActivity();
        }
    }

    public TerminalView getTerminalView() {
        return mTerminalView;
    }

    public void setTerminalViewClient(TerminalViewClient client) {
        mTerminalViewClient = client;
        if (mTerminalView != null) {
            mTerminalView.setTerminalViewClient(client);
        }
    }
}
