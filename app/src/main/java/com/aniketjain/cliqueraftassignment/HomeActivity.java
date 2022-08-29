package com.aniketjain.cliqueraftassignment;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.aniketjain.cliqueraftassignment.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "main_activity";
    private ActivityHomeBinding binding;
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding setUp
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // listeners setUp
        listeners();

    }

    private void listeners() {
        // calls when user change the edittext data
        binding.textEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setMessageText(binding.textEt.getText().toString());
                binding.textTv.setText(message);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // BUTTON CLICKS

        // Toast Button
        binding.btnLayout.btn1.setOnClickListener(view -> {
            if (checkTextIsEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // Notification Button
        binding.btnLayout.btn2.setOnClickListener(view -> {
            if (checkTextIsEmpty()) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(
                            CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT
                    );
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                addNotification();
            }
        });

        // Modal Pop Up Button
        binding.btnLayout.btn3.setOnClickListener(view -> {
            if (checkTextIsEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Assignment Pop Up")
                        .setMessage(message)
                        .setPositiveButton("Ok", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .setNegativeButton("Cancel", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        });

                // create new dialog box
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //  Change Text Button
        binding.btnLayout.btn4.setOnClickListener(view -> binding.textTv.setText(R.string.completed_text));
    }

    private boolean checkTextIsEmpty() {
        if (binding.textEt.getText().toString().isEmpty()) {
            binding.textEt.setError(
                    Html.fromHtml(
                            "<font color='#EEEEEE'>Please enter some text here</font>"
                    )
            );
            return false;
        } else {
            return true;
        }
    }

    private void setMessageText(String message) {
        this.message = message;
    }

    private void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Notification")
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}