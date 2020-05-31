package com.example.miura.a006_myjanken

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.os.Handler
import android.util.Log
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var timer: Timer       //タイマー用
    lateinit var handler: Handler   //遅延処理用

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonPaa.setOnClickListener(this)
        buttonGuu.setOnClickListener(this)
        buttonTyoki.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        var f = 0

        //インスタンス生成
        handler = Handler()
        timer = Timer()

        //period:300ms毎にじゃんけん画像を切り替える
        //delay:2000ms後に処理を開始する
        timer.schedule(2000 ,300,{ runOnUiThread{
            f++
            if (f == 1) {
                imageView.setImageResource(R.drawable.guu)
            }else if(f == 2){
                imageView.setImageResource(R.drawable.tyoki)
            }else{
                imageView.setImageResource(R.drawable.paa)
            }
            if (f == 3){
                f = 0
            }
        }})
    }

    //画面が落ちた場合、タイマーを終了する
    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    //ボタンをタップした時の処理
    override fun onClick(v: View?) {
        //ボタンの無効化
        buttonGuu.isEnabled = false
        buttonTyoki.isEnabled = false
        buttonPaa.isEnabled = false

        //乱数を使用し、相手のじゃんけんの手を決める
        val n = Random().nextInt(3)
        if (n==0){
            imageViewResult.setImageResource(R.drawable.guu)
        }else if (n==1){
            imageViewResult.setImageResource(R.drawable.tyoki)
        }else{
            imageViewResult.setImageResource(R.drawable.paa)
        }

        //タップしたじゃんけんの手をテキスト表示
        textViewResult.text = getString(R.string.pon)
        //タップしたじゃんけんの手の画像を赤色に変更
        when(v?.id) {
            R.id.buttonGuu
            -> buttonGuu.setBackgroundResource(R.drawable.guu2)

            R.id.buttonTyoki
            -> buttonTyoki.setBackgroundResource(R.drawable.tyoki2)

            R.id.buttonPaa
            -> buttonPaa.setBackgroundResource(R.drawable.paa2)
        }

        //相手のじゃんけんの手を表示する
        imageViewResult.visibility = View.VISIBLE
        imageView.visibility = View.INVISIBLE

        //じゃんけんの勝敗判定
        handler.postDelayed(Runnable {
            when(v?.id){
                //ユーザーがグーを出した時の処理
                R.id.buttonGuu
                -> {
                    buttonGuu.setBackgroundResource(R.drawable.guu)
                    if (n == 0) {
                        textViewResult.text = getString(R.string.draw)
                    } else if (n == 1) {
                        textViewResult.text = getString(R.string.win)
                    } else {
                        textViewResult.text = getString(R.string.loose)
                    }
                }

                //ユーザーがチョキを出した時の処理
                R.id.buttonTyoki
                -> {
                    buttonTyoki.setBackgroundResource(R.drawable.tyoki)
                    if (n==0){
                        textViewResult.text = getString(R.string.loose)
                    }else if (n==1){
                        textViewResult.text = getString(R.string.draw)
                    }else {
                        textViewResult.text = getString(R.string.win)
                    }
                }

                //ユーザーがパーを出した時の処理
                R.id.buttonPaa
                -> {
                    buttonPaa.setBackgroundResource(R.drawable.paa)
                    if (n == 0) {
                        textViewResult.text = getString(R.string.win)
                    } else if (n == 1) {
                        textViewResult.text = getString(R.string.loose)
                    } else {
                        textViewResult.text = getString(R.string.draw)
                    }
                }

            }
        },1000)

        handler.postDelayed(Runnable {
            //ボタンの有効化
            buttonGuu.isEnabled = true
            buttonTyoki.isEnabled = true
            buttonPaa.isEnabled = true

            //初期状態に戻す
            textViewResult.text = getString(R.string.jannkenn)
            imageViewResult.visibility = View.INVISIBLE
            imageView.visibility = View.VISIBLE
        },2000)
    }

}
