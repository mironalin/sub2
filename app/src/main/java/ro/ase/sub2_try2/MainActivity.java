package ro.ase.sub2_try2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> launcher;
    private List<Patient> patients;
    private ArrayAdapter<Patient> adapter;
    ListView lvPatients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_option_add) {
            launcher.launch(new Intent(getApplicationContext(), AddPatientActivity.class));
        }
        if (item.getItemId() == R.id.menu_option_details) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            Future<String> futureString = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String link = "https://pastebin.com/raw/VqZmyPrU";
                    StringBuilder sb = new StringBuilder();

                    HttpURLConnection urlConnection = (HttpURLConnection) new URL(link).openConnection();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    }

                    urlConnection.disconnect();
                    return sb.toString();
                }
            });
            executorService.shutdown();
            try {
                Toast.makeText(getApplicationContext(), futureString.get() + getString(R.string.student_group), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private void initComponents() {
        patients = new ArrayList<>();
        lvPatients = findViewById(R.id.lvPatients);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, patients);
        lvPatients.setAdapter(adapter);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            patients.add((Patient) result.getData().getSerializableExtra(AddPatientActivity.SEND_PATIENT_KEY));
                            adapter.notifyDataSetChanged();
                            adapter.sort((p1, p2) -> Float.compare(((Patient) p1).getExaminationCost(), ((Patient) p2).getExaminationCost()));
                        }
                    }
                });
    }
}