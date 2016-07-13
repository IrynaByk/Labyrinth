package com.example.labyrinth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ParametersActivity extends AppCompatActivity {
    private EditText xbegin, xend, ybegin, yend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);
        xbegin = (EditText) findViewById(R.id.editText);
        xend = (EditText) findViewById(R.id.editText3);
        ybegin = (EditText) findViewById(R.id.editText2);
        yend = (EditText) findViewById(R.id.editText4);
        final Button button = (Button) findViewById(R.id.button);
        final int[] param = new int[4];
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xbegin.getText().toString().isEmpty() || xend.getText().toString().isEmpty() ||
                        ybegin.getText().toString().isEmpty() || yend.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Не все значения введены", Toast.LENGTH_SHORT).show();
                }
                else {
                    param[0] = Integer.parseInt(xbegin.getText().toString());
                    param[1] = Integer.parseInt(ybegin.getText().toString());
                    param[2] = Integer.parseInt(xend.getText().toString());
                    param[3] = Integer.parseInt(yend.getText().toString());
                    LabyrinthClass lab = new LabyrinthClass();
                    ArrayList<ArrayList<Integer>> mainLabyrinth = new ArrayList<>();
                    lab.readData(mainLabyrinth, "lab.txt", getApplicationContext());
                    LinearLayout v = (LinearLayout) findViewById(R.id.results_layout);
                    if (!(param[0] < mainLabyrinth.size() & param[2] < mainLabyrinth.size() & param[1] < mainLabyrinth.get(0).size() &
                            param[3] < mainLabyrinth.get(0).size())) {
                        Toast.makeText(getApplicationContext(), "Неверные координаты", Toast.LENGTH_SHORT).show();

                    }
                    else if (mainLabyrinth.get(param[2]).get(param[3]) == 1 || mainLabyrinth.get(param[0]).get(param[1]) == 1) {
                        Toast.makeText(getApplicationContext(), "Там стены", Toast.LENGTH_SHORT).show();
                    }
                    else if (lab.isPathNotFound()) {
                        Toast.makeText(getApplicationContext(), "Маршрут не найден", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        button.setClickable(false);
                        lab.Route(getApplicationContext(), param[0], param[2], param[1], param[3]);
                        v.setVisibility(View.VISIBLE);
                        TextView tv = (TextView) findViewById(R.id.route);
                        tv.setText(lab.getS());
                        tv = (TextView) findViewById(R.id.time);
                        tv.setText(String.valueOf(lab.getTime()));
                        tv = (TextView) findViewById(R.id.rnumber);
                        tv.setText(String.valueOf(lab.getR()));
                        tv = (TextView) findViewById(R.id.array);
                        String labText = "";
                        for (int x = 0; x < mainLabyrinth.size(); ++x) {
                            for (int y = 0; y < mainLabyrinth.get(0).size(); ++y) {
                                if (lab.getPathX().contains(x)) {
                                    if (lab.getPathY().get(lab.getPathX().indexOf(x)) == y) {
                                        lab.getPathY().remove(lab.getPathX().indexOf(x));
                                        lab.getPathX().remove(lab.getPathX().indexOf(x));
                                        labText += "<font color=#999999>" + mainLabyrinth.get(x).get(y) + "</font>";
                                    }
                                    else {
                                        labText += "<font color=#ffffff>" + mainLabyrinth.get(x).get(y) + "</font>";
                                    }
                                }
                                else {
                                    labText += "<font color=#ffffff>" + mainLabyrinth.get(x).get(y) + "</font>";
                                }
                            }
                            labText += "<br>";
                        }
                        tv.setText(Html.fromHtml(labText));
                        button.setClickable(true);
                    }
                }
            }
        });
    }
}