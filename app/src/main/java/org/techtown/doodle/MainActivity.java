package org.techtown.doodle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener {
    private static final String TAG = "MainActivity";

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;

    BottomNavigationView bottomNavigation;

    public static NoteDatabase mDatabase = null;

    Button button;
    TextView textView;

    List<String> textValues, textEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.randomButton);
        textView = findViewById(R.id.randomTextView);

        textValues = new ArrayList<>();
        textEmotion = new ArrayList<>();

        textValues.add("어떤 꿈을 꾸었나요");
        textValues.add("무엇을 할 계획인가요");
        textValues.add("해야할 일은 무엇인가요");
        textValues.add("기분은 어떤가요");
        textValues.add("하고 싶은 일이 떠올랐나요");
        textValues.add("나의 가치관은");
        textValues.add("나는 누구인가");
        textValues.add("사람들에게 고마운 일은");
        textValues.add("오늘의 목표를 달성했나요");
        textValues.add("가장 행복했던 순간은");
        textValues.add("스트레스는 없나요");
        textValues.add("가장 행복했던 순간은");
        textValues.add("인생");
        textValues.add("실수");
        textValues.add("돈");
        textValues.add("성공");
        textValues.add("과거");
        textValues.add("꿈");
        textValues.add("삶");
        textValues.add("자신감");
        textValues.add("아픔");
        textValues.add("도전");
        textValues.add("음악");
        textValues.add("취미");
        textValues.add("여행");
        textValues.add("친구");
        textValues.add("사랑");
        textValues.add("가족");

        textEmotion.add("..");
        textEmotion.add("");
        textEmotion.add(",,");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();

                String randomValue = textValues.get(r.nextInt(textValues.size()));
                String randomColor = textEmotion.get(r.nextInt(textEmotion.size()));

                textView.setText("' " + randomValue +  randomColor + "  '");

            }
        });

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        notice.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "노트", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment1).commit();
                        return true;
                    case R.id.tab2:
                        notice.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "글쓰기", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment2).commit();
                        return true;
                    case R.id.tab3:
                        notice.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "랜덤질문", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment3).commit();
                        return true;
                    case R.id.tab4:
                        notice.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "설정", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment4).commit();
                        return true;
                }

                return false;
            }
        });

        setPicturePath();

        AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("허용된 권한 갯수 : " + permissions.size());
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("거부된 권한 갯수 : " + permissions.size());
                    }
                })
                .start();

        openDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    public void openDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = NoteDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Note database is open.");
        } else {
            Log.d(TAG, "Note database is not open.");
        }
    }

    public void setPicturePath() {
        String folderPath = getFilesDir().getAbsolutePath();
        AppConstants.FOLDER_PHOTO = folderPath + File.separator + "photo";

        File photoFolder = new File(AppConstants.FOLDER_PHOTO);
        if (!photoFolder.exists()) {
            photoFolder.mkdirs();
        }
    }
    public void onTabSelected(int position) {
        if (position == 0) {
            bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if (position == 1) {
            fragment2 = new Fragment2();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment2).commit();
        } else if (position == 2) {
            bottomNavigation.setSelectedItemId(R.id.tab3);
        }
    }

    public void showFragment2(Note item) {

        fragment2 = new Fragment2();
        fragment2.setItem(item);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment2).commit();

    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}