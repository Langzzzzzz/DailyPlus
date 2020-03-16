package com.example.menu.ui.language;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.menu.R;

public class LanguageFragment extends Fragment {
    public static int isEnglish = 1;
    private ToggleButton languageButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_language, container, false);
        languageButton = root.findViewById(R.id.language_button);
        languageButton.setChecked(isEnglish == 1);
        languageButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isEnglish = 1;
            } else {
                isEnglish = 0;
            }
        });
        return root;
    }
}
