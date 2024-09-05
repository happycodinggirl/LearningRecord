package com.example.learningrecord

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learningrecord.ui.theme.LearningRecordTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearningRecordTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "测试协程的不同启动方式" ,
            modifier = modifier.clickable {
                start()
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "测试协程在不同作用域下面的执行顺序",modifier=Modifier.clickable {
                jobOrder()
        })

    }

}

fun jobOrder(){
    GlobalScope.launch(Dispatchers.IO) {//此处，如果在Dispatcher.Main 作用域运行，则是1-10协程是同步进行，1-10是按顺序打印的，若作用域在Dispatcher.IO，则是异步执行的
        for (index in 1 until 10){
            launch {
                Log.v("ff","启动协程${index}")
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun start(){

    GlobalScope.launch {

        val runBlockingJob=runBlocking {
            Log.d("ff","启动一个协程")
            41
        }
        Log.v("ff","------runBlockingJob is $runBlockingJob")
        val job=launch {
            Log.v("ff","启动协程")
        }
        Log.v("ff","-----GlobalScope job is $job")
        val asyncJob=async {
            Log.v("ff","启动协程")
            "我是返回值"
        }
        Log.v("ff","-------asyncJob is ${asyncJob.await()}")
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LearningRecordTheme {
        Greeting("Android")
    }
}