package com.example.semesterproject.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.semesterproject.R;
import com.example.semesterproject.lib.Utils;
import com.example.semesterproject.models.Equation;
import com.example.semesterproject.models.FlashCards;
import com.example.semesterproject.models.ProblemType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean includeAddition;
    private boolean includeSubtraction;
    private boolean includeMultiplication;
    private boolean includeDivision;

    private String keyIncludeAddition;
    private String keyIncludeSubtraction;
    private String keyIncludeMultiplication;
    private String keyIncludeDivision;

    private TextView firstNumTextView;
    private TextView secondNumTextView;
    private TextView numFuncTextView;
    private TextView correctTextView;
    private EditText numInputTextView;
    private Menu menu;

    private Button submitButton;

    private int equationSolution;
    private int tempFirstNum;
    private int tempSecondNum;
    private int tempSolutionInput;
    private ProblemType pt;

    private FlashCards fc;

    private List<ProblemType> numberFunctionList = new ArrayList<ProblemType>();

    public void setupList() {
        numberFunctionList.clear();
        numberFunctionList.addAll(Arrays.asList(ProblemType.values()));
    }

    // Name of Preference file on device
    private final String keyPrefsName = "PREFS";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // call the super-class's method to save fields, etc.
        super.onSaveInstanceState(outState);

        // save each checkbox's status so that we can check off those boxes after restore
        outState.putBoolean(keyIncludeAddition, includeAddition);
        outState.putBoolean(keyIncludeSubtraction, includeSubtraction);
        outState.putBoolean(keyIncludeMultiplication, includeMultiplication);
        outState.putBoolean(keyIncludeDivision, includeDivision);
    }

    @Override
    protected void onStop() {
        saveToSharedPref();
        super.onStop();
    }

    private void saveToSharedPref() {
        // Create a SP reference to the prefs file on the device whose name matches keyPrefsName
        // If the file on the device does not yet exist, then it will be created
        SharedPreferences preferences = getSharedPreferences(keyPrefsName, MODE_PRIVATE);

        // Create an Editor object to write changes to the preferences object above
        SharedPreferences.Editor editor = preferences.edit();

        // clear whatever was set last time
        editor.clear();

        // save the settings
        saveSettingsToSharedPrefs(editor);

        // apply the changes to the XML file in the device's storage
        editor.apply();
    }

    private void saveSettingsToSharedPrefs(SharedPreferences.Editor editor) {
        // save all preferences
        editor.putBoolean(keyIncludeAddition, includeAddition);
        editor.putBoolean(keyIncludeSubtraction, includeSubtraction);
        editor.putBoolean(keyIncludeMultiplication, includeMultiplication);
        editor.putBoolean(keyIncludeDivision, includeDivision);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fc = new FlashCards();

        firstNumTextView = findViewById(R.id.txt_first_number);
        secondNumTextView = findViewById(R.id.txt_second_number);
        numFuncTextView = findViewById(R.id.txt_selector);
        numInputTextView = findViewById(R.id.txt_input_answer);
        correctTextView = findViewById(R.id.txt_right_wrong);
        submitButton = findViewById(R.id.submit_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ProblemType pt: numberFunctionList) {
                    System.out.println(pt.toString());
                }

                if (numInputTextView.getText().toString().isEmpty()) {
                    Snackbar.make(view, "MUST INPUT AN ANSWER!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    tempSolutionInput = Integer.parseInt(numInputTextView.getText().toString());
                    equationSolution = Integer.parseInt(equationSolution + "");

                    if (tempSolutionInput == equationSolution) {
                        correctTextView.setText(R.string.correct);
                    }
                    else {
                        correctTextView.setText(R.string.wrong);
                    }
                    setEquation(fc.pickNewProblem(numberFunctionList));
                }
            }
        });

        setupList();
        setFieldReferencesToResFileValues();
        setupToolbar();
        setupFab();
        restoreAppSettingsFromPrefs();
        setEquation(fc.pickNewProblem(numberFunctionList));

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setFields(view);
            }
        });
    }

    private void setEquation(Equation eq) {
        numInputTextView.setText("");
        tempFirstNum = eq.getFirstNum();
        tempSecondNum = eq.getSecondNum();
        pt = eq.getNumFunc();

        firstNumTextView.setText(tempFirstNum + "");
        secondNumTextView.setText(tempSecondNum + "");

        if (pt == ProblemType.ADD) {
            numFuncTextView.setText("+");
            equationSolution = tempFirstNum + tempSecondNum;
        } else if (pt == ProblemType.SUBTRACT) {
            numFuncTextView.setText("-");
            equationSolution = tempFirstNum - tempSecondNum;
        } else if (pt == ProblemType.MULTIPLY) {
            numFuncTextView.setText("x");
            equationSolution = tempFirstNum * tempSecondNum;
        } else if (pt == ProblemType.DIVIDE) {
            numFuncTextView.setText("/");
            equationSolution = tempFirstNum / tempSecondNum;
        }
    }

    private void restoreAppSettingsFromPrefs() {
        // Since this is for reading only, no editor is needed unlike in saveRestoreState
        SharedPreferences preferences = getSharedPreferences(keyPrefsName, MODE_PRIVATE);

        // restore AutoSave preference value
        includeAddition = preferences.getBoolean(keyIncludeAddition, true);
        includeSubtraction = preferences.getBoolean(keyIncludeSubtraction, true);
        includeMultiplication = preferences.getBoolean(keyIncludeMultiplication, true);
        includeDivision = preferences.getBoolean(keyIncludeDivision, true);
    }

    private void setFieldReferencesToResFileValues() {
        // These values are the same strings used in the prefs xml as keys for each pref there
        keyIncludeAddition = getString(R.string.key_include_addition);
        keyIncludeSubtraction = getString(R.string.key_include_subtraction);
        keyIncludeMultiplication = getString(R.string.key_include_multiplication);
        keyIncludeDivision = getString(R.string.key_include_division);
    }

    private void toggleMenuItem(MenuItem item) {
        item.setChecked(!item.isChecked());
    }

    private void checkIfEmptyList() {
        if(numberFunctionList.size() < 2) {
            includeAddition = true;
            onPrepareOptionsMenu(menu);
            numberFunctionList.add(ProblemType.ADD);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.check_addition).setChecked(includeAddition);
        menu.findItem(R.id.check_subtraction).setChecked(includeSubtraction);
        menu.findItem(R.id.check_multiplication).setChecked(includeMultiplication);
        menu.findItem(R.id.check_division).setChecked(includeDivision);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_settings) {
            return true;
        } else if (item.getItemId() == R.id.action_about) {
            Utils.showInfoDialog(MainActivity.this, R.string.about, R.string.about_text);
            return true;
        } else if (item.getItemId() == R.id.check_addition) {
            toggleMenuItem(item);
            if(item.isChecked()) {
                numberFunctionList.add(ProblemType.ADD);
            }
            else {
                numberFunctionList.remove(ProblemType.ADD);
            }
            includeAddition = item.isChecked();
            checkIfEmptyList();
            setEquation(fc.pickNewProblem(numberFunctionList));
            return true;
        } else if (item.getItemId() == R.id.check_subtraction) {
            toggleMenuItem(item);
            if(item.isChecked()) {
                numberFunctionList.add(ProblemType.SUBTRACT);
            }
            else {
                numberFunctionList.remove(ProblemType.SUBTRACT);
            }
            includeSubtraction = item.isChecked();
            checkIfEmptyList();
            setEquation(fc.pickNewProblem(numberFunctionList));
            return true;
        } else if (item.getItemId() == R.id.check_multiplication) {
            toggleMenuItem(item);
            if(item.isChecked()) {
                numberFunctionList.add(ProblemType.MULTIPLY);
            }
            else {
                numberFunctionList.remove(ProblemType.MULTIPLY);
            }
            includeMultiplication = item.isChecked();
            checkIfEmptyList();
            setEquation(fc.pickNewProblem(numberFunctionList));
            return true;
        } else if (item.getItemId() == R.id.check_division) {
            toggleMenuItem(item);
            if(item.isChecked()) {
                numberFunctionList.add(ProblemType.DIVIDE);
            }
            else {
                numberFunctionList.remove(ProblemType.DIVIDE);
            }
            includeDivision = item.isChecked();
            checkIfEmptyList();
            setEquation(fc.pickNewProblem(numberFunctionList));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}