package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<TextView> _cells;
    TextView[][] _map;
    boolean figure_is_x = false;
    boolean pcMove = false;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        findViewById(R.id.restartButton).setOnClickListener(v -> {restartGame();});
        restartGame();
    }

    private void restartGame() {
        _cells = new ArrayList<>();
        _cells.add(findViewById(R.id.cell11));
        _cells.add(findViewById(R.id.cell12));
        _cells.add(findViewById(R.id.cell13));
        _cells.add(findViewById(R.id.cell21));
        _cells.add(findViewById(R.id.cell22));
        _cells.add(findViewById(R.id.cell23));
        _cells.add(findViewById(R.id.cell31));
        _cells.add(findViewById(R.id.cell32));
        _cells.add(findViewById(R.id.cell33));

        int row = 0;
        int col = 0;
        _map = new TextView[3][3];
        for (TextView cell : _cells) {
            figure_is_x = false;
            cell.setText("");
            Log.d("INDEXING", "onCreate: " + row + " " + col);
            _map[row][col] = cell;
            final int cellRow = row;
            final int cellCol = col;
            cell.setOnClickListener(v -> {
                if (setFigure(cell, cellRow, cellCol))
                    return;
                if (pcMove)
                    return;
                pcMove = true;
                _cells.get(random.nextInt(_cells.size())).callOnClick();
                pcMove = false;
            });
            col++;
            if (col == 3){
                col = 0;
                row++;
            }
        }

        if (random.nextBoolean()){
            pcMove = true;
            _cells.get(random.nextInt(_cells.size())).callOnClick();
            pcMove = false;
        }
    }
    private boolean setFigure(TextView cell, int row, int col){
        String figure = figure_is_x ? "X" : "0";
        cell.setText(figure);
        if (checkWin(row, col, figure)){
            showMessage(
                    (figure_is_x ? "Крестики" : "Нолики") + " победили!",
                    "Игра окончена!");
            return true;
        }
        figure_is_x = !figure_is_x;
        cell.setOnClickListener(null);
        _cells.remove(cell);
        if (_cells.size() == 0){
            showMessage("Ничья..", "Игра окончена!");
            return true;
        }
        return false;
    }

    private boolean checkWin(int checkRow, int checkCol, String figure){

        for (int col = 0; ; col++) {
            if (figure != _map[checkRow][col].getText().toString())
                break;
            if(col == 2)
                return true;
        }

        for (int row = 0; ; row++){
            if (figure != _map[row][checkCol].getText().toString())
                break;
            if(row == 2)
                return true;
        }
        if (checkRow == checkCol){
            for (int i = 0; ; i++){
                if (figure != _map[i][i].getText().toString())
                    break;
                if(i == 2)
                    return true;
            }
        }
        if (checkRow + checkCol == 2){
            for (int i = 0; ; i++){
                if (figure != _map[i][2-i].getText().toString())
                    break;
                if(i == 2)
                    return true;
            }
        }
        return false;
    }

    private void showMessage(String message, String title) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}