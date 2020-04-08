package com.example.menu.ui.language;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.menu.MainActivity;
import com.example.menu.R;
import com.example.menu.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class LanguageFragment extends Fragment {
    public static int isEnglish = 1;
    private ToggleButton languageButton;
    private NavigationView navigationView;
    private MenuItem home;
    private MenuItem bill;
    private MenuItem chart;
    private MenuItem categories;
    private MenuItem password;
    private MenuItem language;
    private MenuItem feedback;
    private MenuItem communicate;
    private MenuItem send;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.nav_view);
        home = navigationView.getMenu().findItem(R.id.nav_home);
        bill = navigationView.getMenu().findItem(R.id.nav_bill);
        chart = navigationView.getMenu().findItem(R.id.nav_chart);
        categories = navigationView.getMenu().findItem(R.id.nav_categories);
        password = navigationView.getMenu().findItem(R.id.nav_password);
        language = navigationView.getMenu().findItem(R.id.nav_language);
        feedback = navigationView.getMenu().findItem(R.id.nav_feedback);
        communicate = navigationView.getMenu().findItem(R.id.communicate);
        send = navigationView.getMenu().findItem(R.id.nav_send);
        View root = inflater.inflate(R.layout.fragment_language, container, false);



        languageButton = root.findViewById(R.id.language_button);

        languageButton.setChecked(isEnglish == 1);
        languageButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isEnglish = 1;
                setEnglish();
            }
            else {
                isEnglish = 0;
                setChinese();
            }
        });
        return root;
    }
    public void setChinese(){
        communicate.setTitle("互动");
        bill.setTitle("账单");
        home.setTitle("主页");
        chart.setTitle("图表");
        categories.setTitle("种类");
        password.setTitle("密码");
        send.setTitle("发送");
        feedback.setTitle("联系我们");
        language.setTitle("语言");
        

    }
    public void setEnglish(){
        communicate.setTitle("Communicate");
        bill.setTitle("Bill");
        home.setTitle("Home");
        chart.setTitle("Generate Chart");
        categories.setTitle("Categories");
        password.setTitle("Password");
        send.setTitle("Send");
        feedback.setTitle("Contact Us");
        language.setTitle("Language");
    }
}
