package com.example.dynamicbuttons;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_ROWS = 10;
    private static final int NUM_COLS = 10;

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateButtons();
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        for (int row=0; row<NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for(int col=0; col<NUM_COLS;col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setText(""+col+","+row); //给button加个坐标
                button.setPadding(0,0,0,0);//make text not clip on small buttons
                button.setOnClickListener(new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v){
                        gridButtonClicked(FINAL_COL,FINAL_ROW);//cannot use outer variables that is not final in inner classes
                    }
                });
                tableRow.addView(button);
                //将新创建的那个button保存到数组中
                buttons[row][col] = button;
            }
        }
    }
    //button点击后的动作
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void gridButtonClicked(int col, int row) {
        Toast.makeText(this,"Button clicked: ("+col+","+row+")", Toast.LENGTH_SHORT).show();
        //另外一个function没有给button起名，无法直接获取button们，于是需要在外面创建一个二维数组来保存这些button
        Button button = buttons[row][col];

        // Lock button sizes:
        lockButtonSizes();
        //button.setBackgroundResource(R.drawable.image27); //does not scale image

        //Scale image to button:
        //Only works for jellyBean!
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.image27);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth,newHeight,true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource,scaledBitmap));

        // Change text on button:
        button.setText(""+ col);
    }

    //lock button size
    private void lockButtonSizes() {
        for(int row=0; row<NUM_ROWS;row++){
            for(int col=0; col<NUM_COLS;col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
}
