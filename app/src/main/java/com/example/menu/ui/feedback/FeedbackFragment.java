package com.example.menu.ui.feedback;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menu.MainActivity;
import com.example.menu.R;
import com.example.menu.data.Language;
import com.example.menu.data.PieView;
import com.example.menu.data.Statistics;


public class FeedbackFragment extends Fragment {
    private Button send;
    private TextView emailAddress, emailBody;
    private String emailSubject = "User Feedback";
    private String SemailBody;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container,false);
        send = view.findViewById(R.id.FeedbacksendButton);
        emailAddress = view.findViewById(R.id.useremail);
        emailBody = view.findViewById(R.id.userfeedback);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailURI = "mailto:" + emailAddress.getText().toString();
                SemailBody = emailBody.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(emailURI));

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, SemailBody);

                startActivity(Intent.createChooser(emailIntent, "Email Client ..."));
            }
        });

        return view;
    }

}