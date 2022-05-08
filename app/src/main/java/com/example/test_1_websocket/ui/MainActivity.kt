package com.example.test_1_websocket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.test_1_websocket.R
import com.example.test_1_websocket.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel: MainViewModel

    private var connect = false
    private var response = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mViewModel.connect.observe(this) {
            connect = it
            if (it) {
//                output_tv.text = ""
//                start_btn.text = "connect"
            } else {
//                output_tv.text = ""
//                start_btn.text = "disconnect"
            }
        }
        mViewModel.mResponseSocket.observe(this) {
            response = it
//            output_tv.text = it
        }

//        start_btn.setOnClickListener {
//            mViewModel.start()
//        }
    }
}