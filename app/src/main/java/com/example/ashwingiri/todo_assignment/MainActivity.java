package com.example.ashwingiri.todo_assignment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity  {
    ListView lvDetails;
    ToDoAdapter toDoAdapter;
    ArrayList<Detail> lvTask=new ArrayList<>();
    EditText etTask;
    Button btAdd;
    Button btY;
    Button btN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvDetails = findViewById(R.id.lvToDoList);
        etTask= findViewById(R.id.etAdd);
        toDoAdapter = new ToDoAdapter(this, lvTask);
        lvDetails.setAdapter(toDoAdapter);
        int perm = ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (perm == PackageManager.PERMISSION_GRANTED) {
            readFile();
        } else {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    45
            );
        }

    }

    private void readFile() {
        File sdCard=Environment.getExternalStorageDirectory();
        File todo=new File(sdCard,"todo.txt");
        try {
            FileInputStream finStream=new FileInputStream(todo);
            BufferedReader br=new BufferedReader(new InputStreamReader(finStream));
            String buf=br.readLine();
            while(buf!=null){
                if(buf.charAt(0)!='#'&&buf.charAt(0)!='!'){
                    boolean temp_check=false;
                    StringBuilder temp_task=new StringBuilder();
                    for(int i=2;i<buf.length();i++){
                        if(buf.charAt(i)!=' '){
                            temp_task.append(buf.charAt(i));
                        }else{
                            temp_check = Integer.parseInt(buf.charAt(i + 1) + "") == 1;
                            break;
                        }
                    }
                    lvTask.add(new Detail(temp_task.toString(),temp_check));
                    toDoAdapter.notifyDataSetChanged();
                }else if(buf.charAt(0)=='!'){
                    StringBuilder temp_pos=new StringBuilder();
                    for(int i=1;i<buf.length();i++){
                        temp_pos.append(buf.charAt(i));
                    }
                    lvTask.remove(Integer.parseInt(temp_pos.toString()));
                    toDoAdapter.notifyDataSetChanged();
                }
                else if(buf.charAt(0)=='#'){
                    StringBuilder temp_pos=new StringBuilder();
                    for(int i=1;i<buf.length();i++){
                        temp_pos.append(buf.charAt(i));
                    }
                    int temp_pos1=Integer.parseInt(temp_pos.toString());
                    Detail tt=lvTask.get(temp_pos1);
                    lvTask.set(temp_pos1,new Detail(tt.getTask(),!tt.isChecked()));
                    toDoAdapter.notifyDataSetChanged();
                }
                buf=br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Add(View view) {
        final Dialog d=new Dialog(MainActivity.this);
        d.setTitle("Add the task?");
        d.setContentView(R.layout.dialog_box);
        d.show();
        btY=d.findViewById(R.id.btYes);
        btN=d.findViewById(R.id.btNo);

        if (!etTask.getText().toString().equals("")) {
            btY.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            d.cancel();
                            String temp=etTask.getText().toString();
                            lvTask.add(new Detail(etTask.getText().toString(),false));
                            toDoAdapter.notifyDataSetChanged();

                            int perm = ContextCompat.checkSelfPermission(
                                    MainActivity.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

                            if (perm == PackageManager.PERMISSION_GRANTED) {
                                writeFile_Add();
                            } else {
                                ActivityCompat.requestPermissions(
                                        MainActivity.this,
                                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        44
                                );
                            }
                            etTask.setText("");

                        }
                    }
            );
            btN.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            etTask.setText("");
                            d.cancel();
                        }
                    }
            );
        } else {
            Toast.makeText(MainActivity.this, "Enter the task", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeFile_Add() {
        try {
            File sdCard=Environment.getExternalStorageDirectory();
            File todo=new File(sdCard,"todo.txt");
            FileOutputStream foutStream=new FileOutputStream(todo,true);
            foutStream.write("  ".getBytes());
            foutStream.write(etTask.getText().toString().getBytes());
            foutStream.write(" ".getBytes());
            foutStream.write("0\n".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) { //write request
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                writeFile_Add();
            } else {
                Toast.makeText(this, "Writing file required this permission", Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode == 45) { //read request
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFile();
            } else {
                Toast.makeText(this, "Reading file required this permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


//package com.example.ashwingiri.todo_assignment;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Toast;
//import java.util.ArrayList;
//
//public class MainActivity extends Activity  {
//    ListView lvDetails;
//    ToDoAdapter toDoAdapter;
//    ArrayList<Detail> lvTask=new ArrayList<>();
//    EditText etTask;
//    Button btAdd;
//    Button btY;
//    Button btN;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        lvDetails = findViewById(R.id.lvToDoList);
//        toDoAdapter = new ToDoAdapter(this, lvTask);
////        lvDetails.setOnItemClickListener(this);
//        lvDetails.setAdapter(toDoAdapter);
//
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//
//    }
//
//    public void onRemove(View v) {
////        int position= (int) v.getTag();
////        lvTask.remove(position);
////        toDoAdapter.notifyDataSetChanged();
////        Toast.makeText(MainActivity.this,"task"+(position+1)+" deleted successfully",Toast.LENGTH_SHORT).show();
////
//        final Dialog d=new Dialog(MainActivity.this);
//        d.setTitle("Remove this task?");
//        d.setContentView(R.layout.dialog_box);
//        d.show();
//        final int position= (int) v.getTag();
//        btY=d.findViewById(R.id.btYes);
//        btN=d.findViewById(R.id.btNo);
//        btY.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        lvTask.remove(position);
//                        toDoAdapter.notifyDataSetChanged();
//                        Toast.makeText(MainActivity.this,"task"+(position+1)+" deleted successfully",Toast.LENGTH_SHORT).show();
//                        d.cancel();
//                    }
//                }
//        );
//        btN.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        d.cancel();
//                    }
//                }
//        );
//    }
//
//    public void Check_box(View v) {
//        int position=(int) v.getTag();
//        Detail s=lvTask.get(position);
//        lvTask.set(position,new Detail(s.getTask(),!s.isChecked()));
//        toDoAdapter.notifyDataSetChanged();
//
//        if(s.isChecked()){
//            final Toast toast=Toast.makeText(MainActivity.this,"task"+(position+1)+" unchecked successfully",Toast.LENGTH_SHORT);
//            toast.show();
//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    toast.cancel();
//                }
//            },1000);
//        }else{
//            final Toast toast=Toast.makeText(MainActivity.this,"task"+(position+1)+" checked successfully",Toast.LENGTH_SHORT);
//            toast.show();
//
//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    toast.cancel();
//                }
//            },1000);
//        }
//    }
////
////    @Override
////    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////        Toast.makeText(MainActivity.this,"You clicked on task"+(i+1),Toast.LENGTH_SHORT).show();
////        //  lvTask.add(new Detail("cfggcgf",false));
////        // toDoAdapter.notifyDataSetChanged();
////        //  lvTask.remove(i);
////        //toDoAdapter.notifyDataSetChanged();
////
////
////    }
//
//    public void Add(View view) {
////        if (!etTask.getText().toString().equals("")) {
////            lvTask.add(new Detail(etTask.getText().toString(),false));
////            etTask.setText("");
////        } else {
////            Toast.makeText(MainActivity.this, "Enter the task", Toast.LENGTH_SHORT).show();
////        }
//         final Dialog d=new Dialog(MainActivity.this);
//         d.setTitle("Add the task?");
//         d.setContentView(R.layout.dialog_box);
//         d.show();
//         etTask= findViewById(R.id.etAdd);
//         btY=d.findViewById(R.id.btYes);
//         btN=d.findViewById(R.id.btNo);
//
//        if (!etTask.getText().toString().equals("")) {
//            btY.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            d.cancel();
//                            lvTask.add(new Detail(etTask.getText().toString(),false));
//                            toDoAdapter.notifyDataSetChanged();
//                            etTask.setText("");
//
//                        }
//                    }
//            );
//            btN.setOnClickListener(
//                    new View.OnClickListener(){
//                        @Override
//                        public void onClick(View view) {
//                            etTask.setText("");
//                            d.cancel();
//                        }
//                    }
//            );
//        } else {
//            Toast.makeText(MainActivity.this, "Enter the task", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}
