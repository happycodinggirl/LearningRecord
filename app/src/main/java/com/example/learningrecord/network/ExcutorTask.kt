package com.example.learningrecord.network

import java.util.concurrent.Executors



  private  val IO_EXCUTOR = Executors.newSingleThreadExecutor()

    fun ioThread(f:()->Unit){
        IO_EXCUTOR.execute(f)
    }


