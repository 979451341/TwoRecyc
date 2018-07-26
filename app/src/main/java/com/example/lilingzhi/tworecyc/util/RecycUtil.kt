package com.example.lilingzhi.tworecyc.util

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

class RecycUtil{
    companion object {

        //移动到n的位置，并且置顶
        fun moveToPositAndTop(n:Int, layoutM: LinearLayoutManager, recyc: RecyclerView,handler:Handler){
            var first=layoutM.findFirstVisibleItemPosition()
            var last=layoutM.findLastVisibleItemPosition()


            if(n<=first){
                recyc.scrollToPosition(n)
            }else if(n<=last){
                var offSet=recyc.getChildAt(n-first).top
                recyc.smoothScrollBy(0,offSet)
            }else{
                recyc.scrollToPosition(n)

                //这里要延迟一下，因为之前的scrollToPosition的实际功能是开子线程完成的，如果直接调findFirstVisibleItemPosition，获取的数据还是之前的
                handler.postDelayed(Runnable {
                    first=layoutM.findFirstVisibleItemPosition()
                    last=layoutM.findLastVisibleItemPosition()
                    Log.v("zzw"," rightclick:"+n+" first:"+first+" last:"+last)

                    if(recyc.getChildAt(last-first)==null){

                    }else{
                        var offSet=recyc.getChildAt(last-first).top
                        recyc.smoothScrollBy(0,offSet)
                    }

                },50)

            }

        }

        //移动到n的位置，并且居中
        fun moveToPositAndCenter(n:Int, layoutM: LinearLayoutManager, recyc: RecyclerView,handler:Handler){
            var first=layoutM.findFirstVisibleItemPosition()
            var last=layoutM.findLastVisibleItemPosition()


            if(n<=first){
                recyc.scrollToPosition(n)

                handler.postDelayed({
                    first=layoutM.findFirstVisibleItemPosition()
                    last=layoutM.findLastVisibleItemPosition()

                    if(recyc.getChildAt(last-first)==null){

                    }else{
                        var offSet=recyc.getChildAt(last-first).top
                        recyc.smoothScrollBy(0,-offSet/2)
                    }
                },50)
            }else if(n<=last){
                var offSet=recyc.getChildAt(n-first).top
                recyc.smoothScrollBy(0,offSet/2)
            }else{
                recyc.scrollToPosition(n)

                //这里要延迟一下，因为之前的scrollToPosition的实际功能是开子线程完成的，如果直接调findFirstVisibleItemPosition，获取的数据还是之前的
                handler.postDelayed(Runnable {
                    first=layoutM.findFirstVisibleItemPosition()
                    last=layoutM.findLastVisibleItemPosition()

                    if(recyc.getChildAt(last-first)==null){

                    }else{
                        var offSet=recyc.getChildAt(last-first).top
                        recyc.smoothScrollBy(0,offSet/2)
                    }

                },50)

            }

        }

    }
}