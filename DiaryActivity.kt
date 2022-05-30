package com.example.basic_ch3_secretdiary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity:AppCompatActivity() {
    /*private val diaryEditText: EditText by lazy {
            findViewById<EditText>(R.id.diaryEditText)
        }*/ // onCreate안에서 선언해줄수도 있어서 그냥 그렇게 선언함
    private val handler = Handler(Looper.getMainLooper())//main thread에 연결된 handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)
        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))//detail이라는 키로 저장된 텍스트를 가져오거나 없을 경우 그냥 빈 string

        //내용이 수정이 될 때마다(글이 변경이 될때마다) 저장을 하는 기능 - save버튼을 누르지 않았을 때 내용이 날라가는 것을 방지
        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit{
                putString("detail", diaryEditText.text.toString())
            }
            // Log.d("DiaryActivity", "SAVE!! ${diaryEditText.text.toString()}")
        }
        diaryEditText.addTextChangedListener{
            // Log.d("DiaryActivity", "Text changed: $it")
            handler.removeCallbacks(runnable)//아직 실행되지 않고 0.5초 이내에 pending되어 있는 runnable을 지움
            handler.postDelayed(runnable, 500)//0.5초마다 실행
        }

    }
}