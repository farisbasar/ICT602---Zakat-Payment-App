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

public class ZakatActivity extends AppCompatActivity {
    private EditText etGoldWeight, etGoldValue;
    private RadioButton rbKeep, rbWear;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat);

        // Initialize UI elements
        etGoldWeight = findViewById(R.id.etGoldWeight);
        etGoldValue = findViewById(R.id.etGoldValue);
        rbKeep = findViewById(R.id.rbKeep);
        rbWear = findViewById(R.id.rbWear);
        tvResult = findViewById(R.id.tvResult);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnHome = findViewById(R.id.btnHome);

        btnCalculate.setOnClickListener(v -> calculateZakat());
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ZakatActivity.this, MainActivity.class);
            startActivity(intent);
        });
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

        // Display result in box
        tvResult.setText(String.format(
                "Total Gold Value: RM %.2f\nZakat Payable: RM %.2f\nTotal Zakat: RM %.2f",
                totalValue, zakatPayable, totalZakat));

        Toast.makeText(this, "Calculation completed!", Toast.LENGTH_SHORT).show();
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome Zakat App: [Your App URL]");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
