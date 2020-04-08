package com.example.menu.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.menu.MainActivity;
import com.example.menu.R;
import com.example.menu.data.Language;
import com.example.menu.loadingPage;
import com.example.menu.ui.language.LanguageFragment;

public class CategoriesFragment extends Fragment implements View.OnClickListener {
    private Button addCategory;
    private Button deleteCategory;
    private EditText categoryEnglish;
    private EditText categoryChinese;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        addCategory = root.findViewById(R.id.add_category);
        deleteCategory = root.findViewById(R.id.delete_category);
        addCategory.setText(Language.add());
        deleteCategory.setText(Language.delete());
        addCategory.setOnClickListener(this);
        deleteCategory.setOnClickListener(this);
        categoryEnglish = root.findViewById(R.id.category_input_e);
        categoryChinese = root.findViewById(R.id.category_input_c);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_category) {
            if (categoryEnglish.getText().toString().equals("") || categoryChinese.getText().toString().equals("")) {
                Toast.makeText(getActivity(), Language.bothEmptyHint(), Toast.LENGTH_SHORT).show();
            } else {
                loadingPage.db.insertNewCategory(categoryEnglish.getText().toString(), categoryChinese.getText().toString());
                categoryEnglish.setText("");
                categoryChinese.setText("");
            }
        } else if (v.getId() == R.id.delete_category) {
            if (categoryEnglish.getText().toString().equals("")) {
                Toast.makeText(getActivity(), Language.emptyHint(), Toast.LENGTH_SHORT).show();
            } else {
                loadingPage.db.deleteCategory(categoryEnglish.getText().toString());
                categoryEnglish.setText("");
            }
        }
    }
}
