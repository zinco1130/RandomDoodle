package org.techtown.doodle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fragment3 extends Fragment {

    Button button;
    TextView textView;

    List<String> textValues, textEmotion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

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

        Button randomButton = rootView.findViewById(R.id.randomButton);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();

                String randomValue = textValues.get(r.nextInt(textValues.size()));
                String randomColor = textEmotion.get(r.nextInt(textEmotion.size()));

                TextView randomTextView = rootView.findViewById(R.id.randomTextView);
                randomTextView.setText("' " + randomValue +  randomColor + "  '");

            }
        });

        return rootView;
    }
}
