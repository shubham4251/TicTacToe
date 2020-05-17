package com.abd.tictactoe;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button [][] buttons=new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Point;
    private int player2Point;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1=findViewById(R.id.text_view_p1);
        textViewPlayer2=findViewById(R.id.text_view_p2);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonId="button_"+i+j;
                int resId=getResources().getIdentifier(buttonId,"id",getPackageName());
                buttons[i][j]=findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset=findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        if(player1Turn){
            ((Button) v).setText("X");
        }
        else{
            ((Button)v).setText("O");
        }
        roundCount++;
        if(checkForWin()){
            if(player1Turn){
                player1Win();
            }
            else{
                player2Win();
            }
        }
        else if (roundCount==9){
            draw();
        }
        else {
            player1Turn=!player1Turn;
        }
    }
    private boolean checkForWin(){
        String [][] field=new String[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]= buttons[i][j].getText().toString();

            }
        }
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])&&
                    field[i][0].equals(field[i][2])&&
                    !field[i][0].equals("")){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i])&&
                    field[0][i].equals(field[2][i])&&
                    !field[0][i].equals("")){
                return true;
            }
        }
        if(field[0][0].equals(field[1][1])&&
                field[0][0].equals(field[2][2])&&
                !field[0][0].equals("")){
            return true;
        }
        if(field[0][2].equals(field[1][1])&&
                field[0][2].equals(field[2][0])&&
                !field[0][2].equals("")){
            return true;
        }
        return false;
    }
    private void player1Win(){
        player1Point++;
        Toast.makeText(this,"player 1 wins!",Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    private void player2Win(){
        player2Point++;
        Toast.makeText(this,"player 2 wins!",Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePoints(){
        String x="Player 1:"+player1Point;
        String y="Player 2:"+player2Point;
        textViewPlayer1.setText(x);
        textViewPlayer2.setText(y);
    }
    private void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundCount=0;
        player1Turn=true;
    }
    private void resetGame(){
        player1Point=0;
        player2Point=0;
        updatePoints();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Point",player1Point);
        outState.putInt("player2Point",player2Point);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount=savedInstanceState.getInt("roundCount");
        player1Point=savedInstanceState.getInt("player1Point");
        player2Point=savedInstanceState.getInt("player2Point");
        player1Turn=savedInstanceState.getBoolean("player1Turn");
    }
}
