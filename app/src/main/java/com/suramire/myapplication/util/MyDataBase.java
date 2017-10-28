package com.suramire.myapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.suramire.myapplication.model.Admin;
import com.suramire.myapplication.model.Ammeter;
import com.suramire.myapplication.model.House;
import com.suramire.myapplication.model.Notification;
import com.suramire.myapplication.model.RentInfo;
import com.suramire.myapplication.model.Room;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Suramire on 2017/5/2.
 * 实现对数据库的相关操作
 */

public class MyDataBase extends SQLiteOpenHelper {
    SQLiteDatabase mydb;
    Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        mydb = getWritableDatabase();
    }
    public Cursor selectByName(String name){
        return  mydb.query("admin",null,"name =?",new String[]{name},null,null,null);
    }

    public Cursor selectAdminById(int adminid){
        return mydb.query("admin", null,"_id = ?",new String[]{adminid+""},null,null,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        用户表
        db.execSQL("create table if not exists admin(_id integer primary key autoincrement," +
                "name varchar,password varchar,nickname varchar)");
//        房源表
        db.execSQL("create table if not exists house(_id integer primary key autoincrement," +
                "address varchar,area varchar,name varchar,floor integer,adminid integer)");
//        房间表
        db.execSQL("create table if not exists room(_id integer primary key autoincrement," +
                "name varchar,price integer,houseid integer,islend integer)");
//        出租表
        db.execSQL("create table if not exists rentinfo(_id integer primary key autoincrement," +
                "roomid integer,rentername varchar,renterphone varchar,money integer,margin integer,datebegin date,dateend date" +
                ",ispayed integer)");
//        工作提醒表
        db.execSQL("create table if not exists notification(_id integer primary key autoincrement," +
                "ncontent varchar,adminid integer,ndate date)");
//        电读数表
        db.execSQL("create table if not exists ammeter(_id integer primary key autoincrement," +
                "roomid integer,lastcount integer,count integer,time date,lasttime date,sort integer)");
//        历史读数表
        db.execSQL("create table if not exists ammeterhistory(_id integer primary key autoincrement," +
                "roomid integer,count integer,time date)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}




    /**
     * 查询登录用户所拥有的房源
     * @param adminId
     * @return
     */
    public Cursor selectAllHouseByAdminId(int adminId){
        return mydb.query("house", null, "adminid=?", new String[]{adminId+""}, null, null, null);
    }


    /**
     *
     * @param adminId
     * @param ispayed
     * @return
     */
    public Cursor selectAllRoomGotMoney(int adminId,int ispayed){
        return mydb.query("rentinfo",null,"ispayed = ? and  roomid in (select _id from room where houseid in (select _id from house where adminid=?))",
                new String[]{ispayed+"",adminId+""},null,null,null);
    }


    /**
     * 查询某房间的电表读数
     * @param roomId
     * @return
     */
    public Cursor selectAmmeter(int roomId){
        return mydb.query("ammeter", null, "roomid=?", new String[]{roomId + ""}, null, null, "sort desc");
    }



    /**
     * 查询当前登录的房东的所有房间
     * @param adminId
     * @return
     */
    public Cursor selectAllRoom(int adminId){
        return  mydb.query("room", null, "houseid = (select _id from house where adminid=?) ", new String[]{adminId+""}, null, null, null);
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


    /**
     * 查询当前用户的所有工作提醒
     * @param adminid
     * @return
     */
    public Cursor selectNotification(int adminid){
        return mydb.query("notification", null, "adminid=?", new String[]{adminid+""}, null, null, null);
    }


    /**
     * 根据是否支付查询房间
     * @param isPayed 1=已支付租金 0=未支付租金
     * @return
     */
    public Cursor selectRenterInfoByPayed(int isPayed) {
        return mydb.query("rentinfo", null, "ispayed=?", new String[]{isPayed + ""}, null, null, null);
    }


    /**
     * 根据id查找某房间的所有信息
     * @param roomId
     * @return
     */
    public Cursor selectRoomByRoomid(int roomId) {
        return mydb.query("room", null, "_id= ?", new String[]{roomId + ""},null,null,null);
    }


    /**
     * 查询某房间的所有历史读数
     * @param roomId
     * @return
     */
    public Cursor selectAmHistory(int roomId){
        return mydb.query("ammeterhistory", null, "roomid= ?", new String[]{roomId + ""},null,null,null);
    }


    /**
     * 修改房间信息
     * @param roomId
     * @param room
     * @return
     */
    public int updateRoomInfo(int roomId,Room room){
        ContentValues values = new ContentValues();
        values.put("name", room.getName());
        values.put("price", room.getPrice());
        return mydb.update("room", values, "_id = ?", new String[]{roomId + ""});
    }


    /**
     * 更新房间状态 未出租→已出租
     * @param roomId
     * @return
     */
    public int updateRoomLend(int roomId){
        ContentValues values = new ContentValues();
        values.put("islend", 1);
        return mydb.update("room", values, "_id = ?", new String[]{roomId + ""});
    }


    /**
     * 更新房间租金的支付状态 未支付→已支付
     * @param rentinfoId
     * @return
     */

    public int updateRentInfoPayed(int rentinfoId){
        ContentValues values = new ContentValues();
        values.put("ispayed", 1);
        return mydb.update("rentinfo", values, "_id = ?", new String[]{rentinfoId + ""});
    }


    /**
     * 更新读表
     * @param ammeter
     * @return
     */
    public int updateAmmeter(Ammeter ammeter){
        ContentValues values = new ContentValues();
        values.put("count", ammeter.getCount());
        values.put("lastcount",ammeter.getLastcount());
        values.put("sort",ammeter.getSort());
        return mydb.update("ammeter",values,"_id=?",new String[]{ammeter.getId()+""});
    }


    /**
     * 添加管理员（注册）
     * @param admin
     * @return
     */
    public long addAdmin(Admin admin) {
        ContentValues values = new ContentValues();
        values.put("name", admin.getName());
        values.put("password", admin.getPassword());
        values.put("nickname", admin.getNickname());
        return mydb.insert("admin", null, values);
    }


    /**
     * 添加房源
     * @param house
     * @return
     */
    public long addHouse(House house){
        ContentValues values = new ContentValues();
        values.put("address", house.getAddress());
        values.put("area", house.getArea());
        values.put("name", house.getName());
        values.put("floor", house.getFloor());
        values.put("adminid",house.getAdminid());
        return mydb.insert("house", null, values);
    }


    /**
     * 添加一个房间
     * @param room
     * @return
     */
    public long addRoom(Room room){
        ContentValues values = new ContentValues();
        values.put("name",room.getName());
        values.put("price",room.getPrice());
        values.put("houseid",room.getHouseid());
        values.put("islend", 0);
        return mydb.insert("room", null, values);
    }


    /**
     * 新建一条出租记录
     * @param rentInfo
     * @return
     */
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


    /**
     * 未新房间添加读表
     * @param ammeter
     * @return
     */
    public long addAmmeter(Ammeter ammeter){
        ContentValues values = new ContentValues();
        values.put("roomid", ammeter.getRoomid());
        values.put("count", ammeter.getCount());
        return mydb.insert("ammeter",null,values);
    }

    /**
     * 添加一条历史读表记录
     * @param ammeter
     * @return
     */
    public long addAmHistory(Ammeter ammeter){
        ContentValues values = new ContentValues();
        values.put("roomid", ammeter.getRoomid());
        values.put("count", ammeter.getCount());
        String format = simpleDateFormat.format(new Date());
        values.put("time",format);
        return mydb.insert("ammeterhistory",null,values);
    }


    /**
     * 新建一条工作提醒
     * @param notification
     * @return
     */
    public long addNotification(Notification notification){
        ContentValues values = new ContentValues();
        values.put("ncontent", notification.getContent());
        values.put("ndate", notification.getDate());
        values.put("adminid", notification.getAdminid());
        return mydb.insert("notification",null,values);
    }

    /**
     * 删除历史读表数据
     * @param id 历史读表的id
     * @return
     */
    public int deleteAmHistory(int id){
        return  mydb.delete("ammeterhistory","_id=?",new String[]{id+""});
    }


    /**
     * 关闭数据库
     */
    public void close() {
        if (mydb.isOpen())
            mydb.close();
    }
}
