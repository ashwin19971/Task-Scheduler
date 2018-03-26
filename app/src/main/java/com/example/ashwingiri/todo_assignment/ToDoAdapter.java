package com.example.ashwingiri.todo_assignment;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Ashwin Giri on 12/25/2017.
 */

public class ToDoAdapter extends BaseAdapter  {
    Context mContext;
    ArrayList<Detail> task_details;

    public ToDoAdapter(Context context, ArrayList<Detail> task_details) {
        this.mContext = context;
        this.task_details = task_details;
    }

    @Override
    public int getCount() {
        return task_details.size();
    }

    @Override
    public Object getItem(int position) {
        return task_details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.list_details, parent, false);
        }
        ImageButton btDel=convertView.findViewById(R.id.btDel);
        TextView tvTask=convertView.findViewById(R.id.tvTask);
        CheckBox cbToDo=convertView.findViewById(R.id.cbToDo);
        tvTask.setText(task_details.get(i).getTask());
        cbToDo.setChecked(task_details.get(i).isChecked());

        btDel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog d=new Dialog(mContext);
                        d.setTitle("Remove this task?");
                        d.setContentView(R.layout.dialog_box);
                        d.show();
                        final int position= i;
                        Button btY=d.findViewById(R.id.btYes);
                        Button btN=d.findViewById(R.id.btNo);
                        btY.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        task_details.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(mContext,"task"+(position+1)+" deleted successfully",Toast.LENGTH_SHORT).show();
                                        d.cancel();

                                        try{
                                            File sdCard= Environment.getExternalStorageDirectory();
                                            File todo=new File(sdCard,"todo.txt");
                                            FileOutputStream foutStream=new FileOutputStream(todo,true);
                                            foutStream.write(("!"+position+"\n").getBytes());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        );
                        btN.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        d.cancel();
                                    }
                                }
                        );

                    }
                }
        );
        cbToDo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Detail s=task_details.get(i);

                        if(s.isChecked()){
                            Toast.makeText(mContext,
                                    "task"+(i+1)+" unchecked successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }else{
                            Toast.makeText(mContext,
                                    "task"+(i+1)+" checked successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                        task_details.set(i,new Detail(s.getTask(),!s.isChecked()));
                        notifyDataSetChanged();

                        try{
                            File sdCard=Environment.getExternalStorageDirectory();
                            File todo=new File(sdCard,"todo.txt");
                            FileOutputStream foutStream=new FileOutputStream(todo,true);
                            foutStream.write(("#"+i+"\n").getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        convertView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext,"You clicked on task "+(i+1),Toast.LENGTH_SHORT).show();
                    }
                }
        );

        return convertView;
    }

}
//package com.example.ashwingiri.todo_assignment;
//
//        import android.content.Context;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.BaseAdapter;
//        import android.widget.Button;
//        import android.widget.CheckBox;
//        import android.widget.ImageButton;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import java.util.ArrayList;
//
///**
// * Created by Ashwin Giri on 12/25/2017.
// */
//
//public class ToDoAdapter extends BaseAdapter  {
//    Context mContext;
//    ArrayList<Detail> task_details;
//
//    public ToDoAdapter(Context context, ArrayList<Detail> task_details) {
//        this.mContext = context;
//        this.task_details = task_details;
//    }
//
//    @Override
//    public int getCount() {
//        return task_details.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return task_details.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int i, View convertView, ViewGroup parent) {
//
////        View v=View.inflate(mContext,R.layout.list_details,null);
////        ImageButton btDel=v.findViewById(R.id.btDel);
////        btDel.setTag(i);
////        TextView tvTask=v.findViewById(R.id.tvTask);
////        CheckBox cbToDo=v.findViewById(R.id.cbToDo);
////        tvTask.setText(task_details.get(i).getTask());
////        cbToDo.setChecked(task_details.get(i).isChecked());
////        cbToDo.setTag(i);
////        Toast.makeText(mContext,"Your "+i+" task Added",Toast.LENGTH_SHORT).show();
////
////        return v;
//        if (convertView == null) {
//            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = li.inflate(R.layout.list_details, parent, false);
//        }
//        ImageButton btDel=convertView.findViewById(R.id.btDel);
//        TextView tvTask=convertView.findViewById(R.id.tvTask);
//        CheckBox cbToDo=convertView.findViewById(R.id.cbToDo);
//        tvTask.setText(task_details.get(i).getTask());
//        cbToDo.setChecked(task_details.get(i).isChecked());
//        btDel.setTag(i);
//        cbToDo.setTag(i);
//        convertView.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(mContext,"You clicked on task "+(i+1),Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        return convertView;
//    }
//
//}
