package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy{ActivityMainBinding.inflate(layoutInflater)}
    val helper=SqliteHelper(this,"memo",1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter=RecyclerAdapter()

        adapter.listData.addAll(helper.selectMemo())
        binding.recyclerMemo.adapter=adapter
        binding.recyclerMemo.layoutManager= LinearLayoutManager(this)

        binding.buttonSave.setOnClickListener{
            if (binding.editMemo.text.toString().isNotEmpty()){


                val memo=Memo(null,binding.editMemo.text.toString(),System.currentTimeMillis())
                Log.d("hhh","${memo.content}")
                helper.insertMemo(memo)

                adapter.listData.clear()
                adapter.listData.addAll(helper.selectMemo())
                Log.d("hhh","${adapter.listData.isEmpty()}")
                adapter.notifyDataSetChanged()

                binding.editMemo.setText("")
                Log.d("hhh","ddd")
            }
        }

    }
}