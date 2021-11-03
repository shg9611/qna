package com.example.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SqliteHelper (context: Context, name:String, version:Int):
    SQLiteOpenHelper(context,name,null,version)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val create="create table memo ("+
                "no integer primary key, "+
                "context text, "+
                "datetime integer" +
                ")"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertMemo(memo:Memo){
        val values = ContentValues()
        values.put("content",memo.content)
        values.put("datetime",memo.datetime)
        Log.d("hhh","${memo.content},hi")
        val wd=writableDatabase
        wd.insert("memo",null,values)
        Log.d("hhh","${values.get("content")},hi")
        wd.close()

    }

    @SuppressLint("Range")
    fun selectMemo():MutableList<Memo>{
        val list=mutableListOf<Memo>()

        val select = "select * from memo"
        val rd= readableDatabase
        val cursor = rd.rawQuery(select,null)

        while (cursor.moveToNext()){
            val no= cursor.getLong(cursor.getColumnIndex("no"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val datetime = cursor.getLong(cursor.getColumnIndex("datetime"))

            list.add(Memo(no,content,datetime))
        }

        cursor.close()
        rd.close()


        return list
    }

    fun updateMemo(memo: Memo){
        val values = ContentValues()
        values.put("content",memo.content)
        values.put("datetime",memo.datetime)

        val wd = writableDatabase
        wd.update("memo",values,"no=${memo.no}",null)
        wd.close()
    }

    fun deleteMemo(memo: Memo){
        val delete="delete from memo where no = ${memo.no}"
        val db=writableDatabase
        db.execSQL(delete)
        db.close()
    }
}

data class Memo(var no:Long?,var content:String, var datetime:Long)