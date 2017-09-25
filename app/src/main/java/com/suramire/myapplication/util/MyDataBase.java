package com.suramire.myapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.suramire.myapplication.model.Admin;
import com.suramire.myapplication.model.House;
import com.suramire.myapplication.model.RentInfo;
import com.suramire.myapplication.model.Room;

/**
 * Created by Suramire on 2017/5/2.
 * 实现对数据库的相关操作
 */

public class MyDataBase extends SQLiteOpenHelper {
    private static final String TAG = "MyDataBase";
    SQLiteDatabase mydb;
    Context context;


    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        mydb = getWritableDatabase();
    }
    public Cursor selectByName(String name){
        return  mydb.query("admin",null,"name =?",new String[]{name},null,null,null);
    }

    /**
     * 查询登录用户所拥有的房源
     * @param adminId
     * @return
     */
    public Cursor selectAllHouseByAdminId(int adminId){
        return mydb.query("house", null, "adminid=?", new String[]{adminId+""}, null, null, null);
    }

    public Cursor selectAllRoomGotMoney(int adminId,int ispayed){
        return mydb.query("rentinfo",null,"ispayed = ? and  roomid in (select _id from room where houseid in (select _id from house where adminid=?))",
                new String[]{ispayed+"",adminId+""},null,null,null);
    }



    /**
     * 查询某房源下的所有房间
     * @param houseId
     * @return
     */
    public Cursor selectAllRoom(int houseId){
        return  mydb.query("room", null, "houseId=?", new String[]{houseId+""}, null, null, null);
    }

    /**
     * 查询某房源下所有已租房间
     * @param houseId
     * @return
     */
    public Cursor selectAllRoomLend(int houseId){
        return  mydb.query("room", null, "houseId=? and islend=?", new String[]{houseId+"","1"}, null, null, null);
    }
    /**
     * 查询某房源下所有未租房间
     * @param houseId
     * @return
     */
    public Cursor selectAllRoomNotLend(int houseId){
        return  mydb.query("room", null, "houseId=? and islend=?", new String[]{houseId+"","0"}, null, null, null);
    }

    public Cursor selectRenterInfo(int roomId) {
        return mydb.query("rentinfo", null, "roomid=?", new String[]{roomId + ""}, null, null, null);
    }

    public Cursor selectRenterInfoByPayed(int isPayed) {
        return mydb.query("rentinfo", null, "ispayed=?", new String[]{isPayed + ""}, null, null, null);
    }

    public Cursor selectRoomByRoomid(int roomId) {
        return mydb.query("room", null, "_id= ?", new String[]{roomId + ""},null,null,null);
    }

    public int updateRoomInfo(int roomId,Room room){
        ContentValues values = new ContentValues();
        values.put("name", room.getName());
        values.put("price", room.getPrice());
        return mydb.update("room", values, "_id = ?", new String[]{roomId + ""});
    }

    public int updateRoomLend(int roomId){
        ContentValues values = new ContentValues();
        values.put("islend", 1);
        return mydb.update("room", values, "_id = ?", new String[]{roomId + ""});
    }

    public int updateRentInfoPayed(int rentinfoId){
        ContentValues values = new ContentValues();
        values.put("ispayed", 1);
        return mydb.update("rentinfo", values, "_id = ?", new String[]{rentinfoId + ""});
    }


    public long addAdmin(Admin admin) {
        ContentValues values = new ContentValues();
        values.put("name", admin.getName());
        values.put("password", admin.getPassword());
        return mydb.insert("admin", null, values);
    }

    public long addHouse(House house){
        ContentValues values = new ContentValues();
        values.put("address", house.getAddress());
        values.put("area", house.getArea());
        values.put("name", house.getName());
        values.put("floor", house.getFloor());
        values.put("adminid",house.getAdminid());
        return mydb.insert("house", null, values);
    }

    public long addRoom(Room room){
        ContentValues values = new ContentValues();
        values.put("name",room.getName());
        values.put("price",room.getPrice());
        values.put("houseid",room.getHouseid());
        values.put("islend", 0);
        return mydb.insert("room", null, values);
    }

    public long addRentInfo(RentInfo rentInfo){
        ContentValues values = new ContentValues();
        values.put("roomid",rentInfo.getRoomid());
        values.put("rentername", rentInfo.getRenterName());
        values.put("renterphone", rentInfo.getRenterPhone());
        values.put("money", rentInfo.getMoney());
        values.put("margin", rentInfo.getMargin());
        values.put("datebegin", rentInfo.getDate_begin());
        values.put("dateend", rentInfo.getDate_end());
        values.put("ispayed",0);
        return mydb.insert("rentinfo",null,values);
    }


    public int delete(String where,String[] strings){
       return  mydb.delete("admin",where,strings);
    }


    /**
     * 关闭数据库
     */
    public void close() {
        if (mydb.isOpen())
            mydb.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists admin(_id integer primary key autoincrement," +
                "name varchar,password varchar)");
        db.execSQL("create table if not exists house(_id integer primary key autoincrement," +
                "address varchar,area varchar,name varchar,floor integer,adminid integer)");
        db.execSQL("create table if not exists room(_id integer primary key autoincrement," +
                "name varchar,price integer,houseid integer,islend integer)");
        db.execSQL("create table if not exists rentinfo(_id integer primary key autoincrement," +
                "roomid integer,rentername varchar,renterphone varchar,money integer,margin integer,datebegin date,dateend date" +
                ",ispayed integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
