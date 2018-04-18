package com.suramire.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suramire.myapplication.base.BaseActivity;
import com.suramire.myapplication.model.Room;
import com.suramire.myapplication.util.MyDataBase;

/**
 * 编辑房间信息
 */

public class EditRoomActivity extends BaseActivity {

    private MyDataBase myDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editroom);
        setTitle("房间信息");
        final EditText room_name = (EditText) findViewById(R.id.room_name);
        final EditText room_price = (EditText) findViewById(R.id.room_price);
        Button button = (Button) findViewById(R.id.btn_editroom);

        //接收上个页面传送过来的数据
        Bundle extras = getIntent().getExtras();
        final Room room = (Room) extras.getSerializable("room");

//        显示房间名
        room_name.setText(room.getName());
//        显示房间价格
        room_price.setText(room.getPrice()+"");
//"修改"按钮点击监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = room_name.getText().toString().trim();
                String price = room_price.getText().toString().trim();
                if(name.equals(room.getName())&& price.equals(room.getPrice()+"")){

                }else if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(price)){
                    Toast.makeText(EditRoomActivity.this, "请将房间信息补充完整", Toast.LENGTH_SHORT).show();
                }else{
                    myDataBase = new MyDataBase(EditRoomActivity.this, "test.db", null, 1);
                    room.setName(name);
                    room.setPrice(Float.parseFloat(price));
//                    更新房间信息
                    int i = myDataBase.updateRoomInfo(room.getId(), room);
                    if(i!=0){
                        Toast.makeText(EditRoomActivity.this, "房间信息修改成功", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(EditRoomActivity.this, "房间信息修改失败", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myDataBase !=null)
            myDataBase.close();
    }
}
