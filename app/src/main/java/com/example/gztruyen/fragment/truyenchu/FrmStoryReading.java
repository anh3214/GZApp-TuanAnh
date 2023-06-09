package com.example.gztruyen.fragment.truyenchu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gztruyen.R;

public class FrmStoryReading extends Fragment {

    private static FrmStoryReading frm;

    public static FrmStoryReading getInstance() {
        if (frm == null)
            frm = new FrmStoryReading();
        return frm;
    }

    private TextView tvContent;


    private Context context;


    public FrmStoryReading() {
    }
    private static FrmStoryReading instance;
    public static FrmStoryReading getInstances(){
        if(instance == null)
            instance = new FrmStoryReading();
        return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frm_story_reading, container, false);
    }

   private String nameChap;
    private String nameTitle;
    String type;
    String name;
    String nameChapter;
    String contentChap;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        bindingAction(view);
        Bundle b = instance.getArguments();
        nameChap = b.getString("name");
        nameTitle = b.getString("nameTitle");
        contentChap = b.getString("contentChap");


        tvContent.setMovementMethod(new ScrollingMovementMethod());
        tvContent.setText(contentChap);

    }


    private void bindingAction(View view) {

    }
    private void bindingView(View view) {

        tvContent = view.findViewById(R.id.tvContent);
        context = view.getContext();

    }

}