package com.example.deleon_appdev;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deleon_appdev.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_STATION_CODE = "extra_station_code";
    public static final String EXTRA_STATUS = "extra_status";
    public static final String EXTRA_ISSUE_NOTE = "extra_issue_note";

    // Replace with your lab's real maintenance number if you have one
    private static final String SUPPORT_NUMBER = "+639171234567";

    private ActivityMainBinding binding;

    // Enhancement 1: three sample stations
    private final String[] stationCodes = {"PC-07", "PC-08", "PC-09"};
    private final String[] stationStatuses = {"Needs Check", "Ready", "Ready"};
    private final String[] stationNotes = {"Emulator slow to boot", "No issues reported", "No issues reported"};
    private int stationIndex = 0;

    private String currentCode = "PC-07";
    private String currentStatus = "Needs Check";
    private String currentNote = "Emulator slow to boot";

    private final ActivityResultLauncher<Intent> detailLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    currentStatus = result.getData().getStringExtra(EXTRA_STATUS);
                    currentNote = result.getData().getStringExtra(EXTRA_ISSUE_NOTE);
                    stationStatuses[stationIndex] = currentStatus;
                    stationNotes[stationIndex] = currentNote;
                    showStation();
                    Toast.makeText(this, "Updated " + currentCode + " -> " + currentStatus,
                            Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Explicit Intent: open StationDetailActivity
        binding.btnInspectStation.setOnClickListener(v -> {
            Intent intent = new Intent(this, StationDetailActivity.class);
            intent.putExtra(EXTRA_STATION_CODE, currentCode);
            intent.putExtra(EXTRA_STATUS, currentStatus);
            intent.putExtra(EXTRA_ISSUE_NOTE, currentNote);
            detailLauncher.launch(intent);
        });

        // Implicit Intent: share a text summary
        binding.btnShareReport.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "LabCheck Report: " + currentCode);
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Station: " + currentCode + "\nStatus: " + currentStatus + "\nNote: " + currentNote);
            startActivity(Intent.createChooser(shareIntent, "Share Station Status"));
        });

        // Enhancement 1: switch between PC-07, PC-08, PC-09
        binding.btnSwitchStation.setOnClickListener(v -> {
            stationIndex = (stationIndex + 1) % stationCodes.length;
            currentCode = stationCodes[stationIndex];
            currentStatus = stationStatuses[stationIndex];
            currentNote = stationNotes[stationIndex];
            showStation();
            Toast.makeText(this, "Selected " + currentCode, Toast.LENGTH_SHORT).show();
        });

        // Enhancement 2: implicit Intent (ACTION_DIAL) to call lab maintenance
        binding.btnCallSupport.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + SUPPORT_NUMBER));
            try {
                startActivity(dialIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No dialer app found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showStation() {
        binding.tvStationCode.setText(currentCode + " • Android Studio SDK");
        binding.tvStationStatus.setText(currentStatus);
        binding.tvStationSpec.setText("Note: " + currentNote);
    }
}
