package com.example.menu.ui.password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menu.MainActivity;
import com.example.menu.R;
import com.example.menu.data.Language;
import com.example.menu.loadingPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PasswordFragment extends Fragment implements View.OnClickListener {
    private Button modify;
    private EditText originalPassword;
    private EditText newPassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_password, container, false);
        modify = root.findViewById(R.id.modify);
        originalPassword = root.findViewById(R.id.original_password);
        newPassword = root.findViewById(R.id.new_password);
        modify.setOnClickListener(this);
        modify.setText(Language.modify());
        originalPassword.setHint(Language.original());
        newPassword.setHint(Language.newPassword());
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.modify) {
            if (originalPassword.getText().toString().equals(loadingPage.password)) {
                loadingPage.db.modifyPassword(originalPassword.getText().toString(), newPassword.getText().toString());
                originalPassword.setText("");
                newPassword.setText("");
            } else {
                Toast.makeText(getActivity(), "Incorrect original password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
