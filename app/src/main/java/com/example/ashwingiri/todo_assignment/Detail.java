package com.example.ashwingiri.todo_assignment;

import android.content.Context;

/**
 * Created by Ashwin Giri on 12/25/2017.
 */

public class Detail {
    String task;
    boolean checked;

    public Detail(String task, boolean checked) {

        this.task = task;
        this.checked = checked;
    }

    public String getTask() {
        return task;
    }


    public boolean isChecked() {
        return checked;
    }


}
