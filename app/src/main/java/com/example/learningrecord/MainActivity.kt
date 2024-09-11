package com.example.learningrecord

import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.learningrecord.ui.theme.LearningRecordTheme
import com.example.learningrecord.viewmodel.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okio.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearningRecordTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   /* Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )*/
                    Surface(modifier = Modifier.padding(innerPadding.calculateTopPadding())) {
                        PagingList()
                    }

                }
            }
        }
    }
}

@Composable
fun PagingList() {
    val mainViewModel: MainViewModel = viewModel()
    val data = mainViewModel.getData().collectAsLazyPagingItems()
    Column {
        LazyColumn {
            Log.v("tt","-----itemCount is ${data.itemCount}")
            items(data.itemCount){index->
                var itemContent=data[index]
                Log.v("item","------itemContent is $itemContent")
                Text(text = itemContent?.author.toString())
            }
            Log.v("tt","-----loadState is ${data.loadState.refresh}")
        }


    }

}

@Composable
fun StatusItem(loadState:LoadState){
    if (loadState is LoadState.Loading){
        Log.v("loading","---正在加载----")
        Text(text = "loading", color = Color.Green)
    }else if (loadState is LoadState.Error){
        when((loadState as LoadState.Error).error){
            is IOException->{
                Log.v("tt","-----网络未连接-----")
                Text(text = "网络未连接", color = Color.Red)
            } else->{
            Log.v("tt","---网络未连接，其他异常---")
            Text(text = "其他异常", color = Color.Red)

        }

        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(text = "测试协程的不同启动方式", modifier = modifier.clickable {
            start()
        })
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "测试协程在不同作用域下面的执行顺序", modifier = Modifier.clickable {
            jobOrder()
        })

        /*Text(text = "测试paging3使用",modifier=Modifier.padding(20.dp).clickable {
            var intent=Intent(LocalContext.current,)
        })
*/

    }

}

fun jobOrder() {
    GlobalScope.launch(Dispatchers.IO) {//此处，如果在Dispatcher.Main 作用域运行，则是1-10协程是同步进行，1-10是按顺序打印的，若作用域在Dispatcher.IO，则是异步执行的
        for (index in 1 until 10) {
            launch {
                Log.v("ff", "启动协程${index}")
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun start() {

    GlobalScope.launch {

        val runBlockingJob = runBlocking {
            Log.d("ff", "启动一个协程")
            41
        }
        Log.v("ff", "------runBlockingJob is $runBlockingJob")
        val job = launch {
            Log.v("ff", "启动协程")
        }
        Log.v("ff", "-----GlobalScope job is $job")
        val asyncJob = async {
            Log.v("ff", "启动协程")
            "我是返回值"
        }
        Log.v("ff", "-------asyncJob is ${asyncJob.await()}")
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LearningRecordTheme {
        Greeting("Android")
    }
}