package com.example.loginbro;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText edtFullName, edtUsername, edtEmail, edtPassword, edtConfirmPassword, edtAddress, edtPhoneNumber, edtDob;
    private Spinner spnGender;
    private Button btnSubmit;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtFullName = findViewById(R.id.edt_fullname);
        edtUsername = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        edtAddress = findViewById(R.id.edt_address);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        edtDob = findViewById(R.id.edt_dob);
        spnGender = findViewById(R.id.spn_gender);
        btnSubmit = findViewById(R.id.btn_submit);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter);

        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void validateAndSubmit() {
        Map<EditText, String> validations = new HashMap<>();
        validations.put(edtFullName, TextUtils.isEmpty(edtFullName.getText().toString().trim()) ? "Nama lengkap harus diisi" : null);
        validations.put(edtUsername, TextUtils.isEmpty(edtUsername.getText().toString().trim()) ? "Username harus diisi" : null);
        validations.put(edtEmail, !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString().trim()).matches() ? "Email tidak valid" : null);
        validations.put(edtPassword, TextUtils.isEmpty(edtPassword.getText().toString().trim()) ? "Password harus diisi" : null);
        validations.put(edtConfirmPassword, !edtPassword.getText().toString().trim().equals(edtConfirmPassword.getText().toString().trim()) ? "Konfirmasi password tidak cocok" : null);
        validations.put(edtAddress, TextUtils.isEmpty(edtAddress.getText().toString().trim()) ? "Alamat harus diisi" : null);
        validations.put(edtPhoneNumber, TextUtils.isEmpty(edtPhoneNumber.getText().toString().trim()) ? "Nomor HP harus diisi" : null);
        validations.put(edtDob, TextUtils.isEmpty(edtDob.getText().toString().trim()) ? "Tanggal lahir harus diisi" : null);

        for (Map.Entry<EditText, String> entry : validations.entrySet()) {
            if (entry.getValue() != null) {
                entry.getKey().setError(entry.getValue());
                entry.getKey().requestFocus();
                return;
            }
        }

        String message = "Nama Lengkap: " + edtFullName.getText().toString().trim() +
                "\nEmail: " + edtEmail.getText().toString().trim() +
                "\nTanggal Lahir: \n" +  edtDob.getText().toString().trim();
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
