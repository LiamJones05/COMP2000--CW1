package com.example.comp2000;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EmployeeHomeScreen extends AppCompatActivity {

    private Button submitButton;
    private Button requestHolidayButton;
    private TextView startDateInput;
    private TextView endDateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_home_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Switch toggleSwitch = findViewById(R.id.NotificationSwitch);
        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(EmployeeHomeScreen.this, "Notifications enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EmployeeHomeScreen.this, "Notifications disabled", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        String[] employeeInfo = {
                "",                  // Name
                "Software Engineer", // Job Title
                "",                  // Address
                "",                  // Email Address
                "",                   // Telephone Number
                "18"
        };

        employeeInfo[0] = intent.getStringExtra("NEW_NAME");
        employeeInfo[2] = intent.getStringExtra("NEW_ADDRESS");
        employeeInfo[3] = intent.getStringExtra("NEW_EMAIL");
        employeeInfo[4] = intent.getStringExtra("NEW_TELEPHONE");

        // Set employee information or default values
        if (employeeInfo[0] != null && !employeeInfo[0].isEmpty()) {
            ((TextView) findViewById(R.id.employeeName)).setText(employeeInfo[0]);
        } else {
            ((TextView) findViewById(R.id.employeeName)).setText("John Doe");
        }
        if (employeeInfo[2] != null && !employeeInfo[2].isEmpty()) {
            ((TextView) findViewById(R.id.address)).setText(employeeInfo[2]);
        } else {
            ((TextView) findViewById(R.id.address)).setText("123 Main St, City, County");
        }
        if (employeeInfo[3] != null && !employeeInfo[3].isEmpty()) {
            ((TextView) findViewById(R.id.emailAddress)).setText(employeeInfo[3]);
        } else {
            ((TextView) findViewById(R.id.emailAddress)).setText("johndoe@example.com");
        }
        if (employeeInfo[4] != null && !employeeInfo[4].isEmpty()) {
            ((TextView) findViewById(R.id.telephoneNumber)).setText(employeeInfo[4]);
        } else {
            ((TextView) findViewById(R.id.telephoneNumber)).setText("(+44)01111111111");
        }
        ((TextView) findViewById(R.id.jobTitle)).setText(employeeInfo[1]);
        ((TextView) findViewById(R.id.holidaysRemaining)).setText("Holidays Remaining: " + employeeInfo[5]);

        // Initialize buttons and inputs
        submitButton = findViewById(R.id.EditDetailButton);
        requestHolidayButton = findViewById(R.id.requestHolidayButton);
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);

        submitButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(EmployeeHomeScreen.this, EditDetailPopup.class);
            startActivityForResult(editIntent, 1); // Use a request code to identify the result
        });

        requestHolidayButton.setOnClickListener(v -> {
            handleHolidayRequest();
        });

        // Set click listeners for date inputs
        startDateInput.setOnClickListener(v -> showDatePickerDialog(true));
        endDateInput.setOnClickListener(v -> showDatePickerDialog(false));
    }

    private void showDatePickerDialog(boolean isStartDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String formattedDate = formatDate(selectedYear, selectedMonth, selectedDay);
            if (isStartDate) {
                startDateInput.setText(formattedDate);
            } else {
                endDateInput.setText(formattedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private String formatDate(int year, int month, int day) {
        // Adjust month to be zero-indexed for formatting
        month += 1;
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
    }

    private void handleHolidayRequest() {
        String startDate = startDateInput.getText().toString();
        String endDate = endDateInput.getText().toString();

        // Validate the input
        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill in both dates.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the end date is after the start date
        if (!isEndDateAfterStartDate(startDate, endDate)) {
            Toast.makeText(this, "End date must be after start date.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Process holiday request (this could be saving to a database, sending to a server, etc.)
        Toast.makeText(this, "Holiday request submitted from " + startDate + " to " + endDate, Toast.LENGTH_LONG).show();

        // Clear input fields after submission
        startDateInput.setText("");
        endDateInput.setText("");
    }

    private boolean isEndDateAfterStartDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            // Parse both dates
            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            startCal.setTime(sdf.parse(startDate));
            endCal.setTime(sdf.parse(endDate));

            // Compare the dates
            return endCal.after(startCal);
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // In case of parsing error, return false
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newName = data.getStringExtra("NEW_NAME");
            String newEmail = data.getStringExtra("NEW_EMAIL");
            String newAddress = data.getStringExtra("NEW_ADDRESS");
            String newTelephone = data.getStringExtra("NEW_TELEPHONE");

            // Update UI with new data
            if (newName != null && !newName.isEmpty()) {
                ((TextView) findViewById(R.id.employeeName)).setText(newName);
            }
            if (newAddress != null && !newAddress.isEmpty()) {
                ((TextView) findViewById(R.id.address)).setText(newAddress);
            }
            if (newEmail != null && !newEmail.isEmpty()) {
                ((TextView) findViewById(R.id.emailAddress)).setText(newEmail);
            }
            if (newTelephone != null && !newTelephone.isEmpty()) {
                ((TextView) findViewById(R.id.telephoneNumber)).setText(newTelephone);
            }
        }
    }

    public void onSignOutClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
