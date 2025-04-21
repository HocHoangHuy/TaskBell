package com.laptrinhdidong.taskbell;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pomodoro extends AppCompatActivity
{
    private TextView tvTitle, tvTime;
    private CardView cdv;
    private FrameLayout fl;
    private ProgressBar pdb;
    private Button start, setting;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;

    private long timeLeftInMillis;
    private long totalTime;

    private int pomodoroCount = 0;

    private enum Mode { WORK, SHORT_BREAK, LONG_BREAK }
    private Mode currentMode = Mode.WORK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        // Ánh xạ
        tvTitle = findViewById(R.id.titleText);
        tvTime = findViewById(R.id.tvTimeLeft);
        cdv = findViewById(R.id.cardContainer);
        fl = findViewById(R.id.flTimer);
        pdb = findViewById(R.id.pbTimer);
        start = findViewById(R.id.btStart);
        setting = findViewById(R.id.btSetTime);

        // Thiết lập ban đầu là 25 phút làm việc
        totalTime = timeLeftInMillis = 25 * 60 * 1000;
        updateUI();
        updateTimeText();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning) {
                    if (timeLeftInMillis == totalTime) {
                        if (currentMode == Mode.WORK) {
                            startWork();
                        } else if (currentMode == Mode.SHORT_BREAK) {
                            startBreak(5 * 60 * 1000, Mode.SHORT_BREAK);
                        } else {
                            startBreak(15 * 60 * 1000, Mode.LONG_BREAK);
                        }
                    } else {
                        countdown(); // Resume
                    }
                    start.setText("Pause");
                    setting.setText("Reset");
                } else {
                    pause();
                    start.setText("Start");
                }
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setting.getText().equals("Reset")) {
                    pause();
                    if (currentMode == Mode.WORK) {
                        timeLeftInMillis = totalTime = 25 * 60 * 1000;
                    } else if (currentMode == Mode.SHORT_BREAK) {
                        timeLeftInMillis = totalTime = 5 * 60 * 1000;
                    } else {
                        timeLeftInMillis = totalTime = 15 * 60 * 1000;
                    }
                    updateTimeText();
                    pdb.setProgress(100);
                    start.setText("Start");
                    setting.setText("Setting");
                } else {
                    // Bạn có thể mở activity cài đặt tùy chỉnh thời gian ở đây nếu muốn
                }
            }
        });
    }

    private void countdown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimeText();

                int progress = (int) (millisUntilFinished * 100 / totalTime);
                pdb.setProgress(progress);
            }

            public void onFinish() {
                isRunning = false;
                start.setText("Start");
                setting.setText("Setting");

                if (currentMode == Mode.WORK) {
                    pomodoroCount++;
                    if (pomodoroCount % 4 == 0) {
                        startBreak(15 * 60 * 1000, Mode.LONG_BREAK); // nghỉ dài
                    } else {
                        startBreak(5 * 60 * 1000, Mode.SHORT_BREAK); // nghỉ ngắn
                    }
                } else {
                    startWork(); // quay lại làm việc
                }
            }
        }.start();

        isRunning = true;
    }

    private void pause() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    private void updateTimeText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        tvTime.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
    }

    private void updateUI() {
        if (currentMode == Mode.WORK) {
            tvTitle.setText("Pomodoro Focus");
        } else if (currentMode == Mode.SHORT_BREAK) {
            tvTitle.setText("Short Break");
        } else {
            tvTitle.setText("Long Break");
        }
        pdb.setProgress(100);
    }

    private void startWork() {
        currentMode = Mode.WORK;
        totalTime = timeLeftInMillis = 25 * 60 * 1000;
        updateUI();
        updateTimeText();
        countdown();
    }

    private void startBreak(long duration, Mode mode) {
        currentMode = mode;
        totalTime = timeLeftInMillis = duration;
        updateUI();
        updateTimeText();
        countdown();
    }
}