package com.example.zakatpaymentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;

public class ZakatActivity extends AppCompatActivity {
    private EditText etGoldWeight, etGoldValue;
    private RadioButton rbKeep, rbWear;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat);


        etGoldWeight = findViewById(R.id.etGoldWeight);
        etGoldValue = findViewById(R.id.etGoldValue);
        rbKeep = findViewById(R.id.rbKeep);
        rbWear = findViewById(R.id.rbWear);
        tvResult = findViewById(R.id.tvResult);


        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnHome = findViewById(R.id.btnHome);

        btnCalculate.setOnClickListener(v -> {
            if (validateInput()) {
                showConfirmationDialog(); // Show confirmation dialog before calculating
            }
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ZakatActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInput() {

        if (etGoldWeight.getText().toString().isEmpty()) {
            Snackbar.make(etGoldWeight, "Please enter the gold weight!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (etGoldValue.getText().toString().isEmpty()) {
            Snackbar.make(etGoldValue, "Please enter the gold value!", Snackbar.LENGTH_LONG).show();
            return false;
        }


        if (!rbKeep.isChecked() && !rbWear.isChecked()) {
            Snackbar.make(rbKeep, "Please select Keep or Wear!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Calculation")
                .setMessage("Are you sure you want to calculate Zakat?")
                .setPositiveButton("Yes", (dialog, which) -> calculateZakat())
                .setNegativeButton("Cancel", null)
                .show();
    }



    private void calculateZakat() {
        double weight, value, threshold;
        try {
            weight = Double.parseDouble(etGoldWeight.getText().toString());
            value = Double.parseDouble(etGoldValue.getText().toString());
        } catch (NumberFormatException e) {
            tvResult.setText("Please enter valid numbers.");
            return;
        }

        threshold = rbKeep.isChecked() ? 85 : 200;
        double payableWeight = Math.max(weight - threshold, 0);
        double totalValue = weight * value;
        double zakatPayable = payableWeight * value;
        double totalZakat = zakatPayable * 0.025;

        tvResult.setText(String.format(
                "Total Gold Value: RM %.2f\nZakat Payable: RM %.2f\nTotal Zakat: RM %.2f",
                totalValue, zakatPayable, totalZakat));

        Toast.makeText(this, "Calculation completed!", Toast.LENGTH_SHORT).show();
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How to Use")
                .setMessage("1. Select 'Keep' or 'Wear' as the Zakat type.\n" +
                        "2. Enter the Gold Weight and Current Gold Value per Gram.\n" +
                        "3. Press 'Calculate Zakat' to view the results.\n" +
                        "4. Use the Home button to navigate back to the main screen.")
                .setPositiveButton("Got it", (dialog, which) -> dialog.dismiss())
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuShare) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome Zakat App: https://github.com/farisbasar/ICT602---Zakat-Payment-App");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        } else if (item.getItemId() == R.id.menuHelp) {
            showHelpDialog();
            return true;

        }
        return false;
    }
}
