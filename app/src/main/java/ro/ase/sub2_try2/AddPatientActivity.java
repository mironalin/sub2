package ro.ase.sub2_try2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AddPatientActivity extends AppCompatActivity {
    public static final String SEND_PATIENT_KEY = "patientKey";
    EditText etPatientName, etExaminationCost;
    RadioGroup radioGroup;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        initComponents();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    Intent intent = new Intent();
                    intent.putExtra(SEND_PATIENT_KEY, getObject());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean isValid() {
        if (etPatientName.getText() == null || etPatientName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Patient name invalid!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etExaminationCost.getText() == null || etExaminationCost.getText().toString().trim().isEmpty() || Float.parseFloat(etExaminationCost.getText().toString().trim()) < 0) {
            Toast.makeText(getApplicationContext(), "Examination cost invalid!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private Patient getObject() {
        String patientName = etPatientName.getText().toString().trim();
        float examinationCost = Float.parseFloat(etExaminationCost.getText().toString().trim());
        boolean insurance = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString().equals("Yes") ? true: false;
        return new Patient(patientName, examinationCost, insurance);
    }

    private void initComponents() {
        etPatientName = findViewById(R.id.etPatientName);
        etExaminationCost = findViewById(R.id.etExaminationCost);
        radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton = findViewById(R.id.radioBtnNo);
        radioGroup.check(radioButton.getId());
        btnSave = findViewById(R.id.btnSave);
    }
}