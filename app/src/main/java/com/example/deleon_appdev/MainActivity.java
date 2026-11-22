package com.example.deleon_appdev;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deleon_appdev.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LabCheckLifecycle";
    private static final String KEY_LOG = "key_lifecycle_log";
    private ActivityMainBinding binding;
    private final StringBuilder traceBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Enhancement 1: applicationId + lab room in header
        binding.tvAppTitle.setText("LabCheck — CompLab 1\n" + getPackageName());

        if (savedInstanceState != null) {
            traceBuilder.append(savedInstanceState.getString(KEY_LOG, ""));
        }
        recordEvent("onCreate");

        binding.btnSimulateTick.setOnClickListener(v ->
                recordEvent("Manual check-in probe (pkg=" + getPackageName() + ")"));

        // Enhancement 2: Clear Log (survives rotation via onSaveInstanceState)
        binding.btnClearLog.setOnClickListener(v -> {
            traceBuilder.setLength(0);
            binding.tvLifecycleLog.setText("Log cleared.");
            Log.d(TAG, "Log cleared by user");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recordEvent("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        recordEvent("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        recordEvent("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        recordEvent("onStop");
    }

    @Override
    protected void onDestroy() {
        recordEvent("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_LOG, traceBuilder.toString());
    }

    private void recordEvent(String eventName) {
        String ts = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String line = "[" + ts + "] " + eventName + "\n";
        traceBuilder.append(line);
        Log.d(TAG, line.trim());
        if (binding != null) {
            binding.tvLifecycleLog.setText(traceBuilder.toString());
        }
    }
}