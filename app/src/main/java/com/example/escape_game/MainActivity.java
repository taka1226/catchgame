package com.example.escape_game;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android .widget.FrameLayout;

import android.os.Bundle;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

import android.view.WindowManager;
import android.view.Display;
import android.graphics.Point;

public class MainActivity extends AppCompatActivity {
    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;

    //位置
    private float boxX, boxY;
    private float orangeX, orangeY;
    private float pinkX, pinkY;
    private float blackX, blackY;

    //status
    private boolean action_flg = false;

    //start flag
    private boolean start_flg = false;

    //サイズ
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);

        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);

        //screen size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.x;
        screenWidth = size.y;

        System.out.println(Math.random());
        startLabel.setVisibility(View.INVISIBLE);

        System.out.println(scoreLabel.getText());


    }

    public void changePos(){
        if (action_flg){
            boxY -= 20;
        }else{
            boxY += 20;
        }
        box.setY(boxY);

        if (boxY < 0) boxY = 0;
        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;
    }

    //orange
    public void changeOrange(){
        orangeX -= 20;
        orange.setX(orangeX);

        if (orangeX < -50){
            orangeX = screenWidth;
            orangeY = frameHeight * (float)Math.random();
            System.out.println(orangeY);
            orange.setY(orangeY);

        }
    }

    //black
    public void changeBlack(){
        blackX -= 30;
        black.setX(blackX);

        if (blackX < -50){
            blackX = screenWidth;
            blackY = frameHeight * (float)Math.random();

            black.setY(blackY);

        }
    }

    //pink
    public void changePink(){
        pinkX -= 40;
        pink.setX(pinkX);

        if (pinkX < -50){
            pinkX = screenWidth;
            pinkY = frameHeight * (float)Math.random();

            pink.setY(pinkY);

        }
    }

//    //衝突判定
//    public void crush(float objectX, float objectY){
//
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!start_flg){
            start_flg = true;

            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();
            boxSize = box.getHeight();

            boxY = (float)frameHeight / 2;
            System.out.println(boxY);



            startLabel.setVisibility(View.GONE);
            timer.schedule(new TimerTask(){
                @Override
                public void run(){
                    handler.post(new Runnable(){
                        @Override
                        public void run(){
                            changePos();
                            changeOrange();
                            changeBlack();
                            changePink();
                        }
                    });
                }
            }, 0, 20);

        }else{

            if (event.getAction() == MotionEvent.ACTION_DOWN){
                action_flg = true;
            }else if (event.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }


        System.out.println();
        return true;
    }
}