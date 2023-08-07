package sg.edu.rp.c346.id22024044.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle;
    EditText etTitle;

    TextView tvGenre;
    EditText etGenre;

    TextView tvYear;
    EditText etYear;

    Spinner spnRating;

    Button btnInsert;
    Button btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        etTitle = findViewById(R.id.etTitle);

        tvGenre = findViewById(R.id.tvGenre);
        etGenre = findViewById(R.id.etGenre);

        tvYear = findViewById(R.id.tvYear);
        etYear = findViewById(R.id.etYear);

        spnRating = findViewById(R.id.spnRating);

        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        // Create an ArrayAdapter with movie ratings options directly in Java code
        ArrayAdapter<CharSequence> ratingAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        ratingAdapter.add("G");
        ratingAdapter.add("PG");
        ratingAdapter.add("PG-13");
        ratingAdapter.add("NC-16");
        ratingAdapter.add("M-18");
        ratingAdapter.add("R-21");

        // Set the layout resource for the dropdown menu
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach the adapter to the Spinner
        spnRating.setAdapter(ratingAdapter);

        // Set an item selected listener for the Spinner
        spnRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRating = parent.getItemAtPosition(position).toString();
                // You can use the selectedRating here or store it in a variable for later use.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearString = etYear.getText().toString();

                if (!yearString.matches("\\d+")) {
                    Toast.makeText(MainActivity.this, "Please enter a valid year.", Toast.LENGTH_SHORT).show();
                    return; // Exit the click listener early
                }

                int year = Integer.parseInt(yearString);

                String rating = spnRating.getSelectedItem().toString();

                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                db.insertMovie(title, genre, year, rating);

                // Toast Message: To confirm Insertion of movie
                Toast.makeText(MainActivity.this, "Movie Inserted \nMovie Title: " + title + "\nGenre: " + genre +
                        "\nYear: " + year + "\nMovie Rating: " + rating, Toast.LENGTH_LONG).show();

                // Clear the Fields
                etTitle.setText("");
                etGenre.setText("");
                etYear.setText("");
                spnRating.setSelection(0);
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}