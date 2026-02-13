package com.termux.app.terminal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.termux.app.TermuxActivity;
import com.termux.app.R;
import com.termux.view.TerminalView;
import com.termux.view.TerminalViewClient;

public class TerminalFragment extends Fragment {

    private TerminalView mTerminalView;
    private TerminalViewClient mTerminalViewClient;
    private TermuxActivity mTermuxActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_termux, container, false);
        mTerminalView = rootView.findViewById(R.id.terminal_view);
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
