package sg.edu.rp.c346.id22024044.mymovies;

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

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    TextView tvID;
    EditText etID;
    TextView tvTitle;
    EditText etTitle;

    TextView tvGenre;
    EditText etGenre;

    TextView tvYear;
    EditText etYear;

    Spinner spnRating;

    Button btnUpdate, btnDelete, btnCancel;
    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tvID = findViewById(R.id.tvMovieID);
        etID = findViewById(R.id.etMovieID);

        tvTitle = findViewById(R.id.tvTitle);
        etTitle = findViewById(R.id.etTitle);

        tvGenre = findViewById(R.id.tvGenre);
        etGenre = findViewById(R.id.etGenre);

        tvYear = findViewById(R.id.tvYear);
        etYear = findViewById(R.id.etYear);

        spnRating = findViewById(R.id.spnRating);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        etID.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(String.valueOf(data.getYear()));

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

        // Get the rating value from the data object
        String rating = data.getRating();

        // Find the index of the rating in the array of movie ratings
        int ratingIndex = ratingAdapter.getPosition(rating);

        // Set the selected item in the Spinner based on the rating index
        spnRating.setSelection(ratingIndex);

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ThirdActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));

                // Get the selected rating from the Spinner
                String selectedRating = spnRating.getSelectedItem().toString();
                data.setRating(selectedRating);

                db.updateMovie(data);
                db.close();

                Toast.makeText(ThirdActivity.this, "Updated " + data.getTitle(),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ThirdActivity.this);
                db.deleteMovie(data.getId());

                Toast.makeText(ThirdActivity.this, "Removed " + data.getTitle(),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
