package com.example.lilingzhi.tworecyc.act

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.lilingzhi.tworecyc.R
import com.example.lilingzhi.tworecyc.util.RecycUtil
import com.example.lilingzhi.tworecyc.adapter.OneAdapter
import com.example.lilingzhi.tworecyc.adapter.TwoAdapter
import com.example.lilingzhi.tworecyc.bean.OneBean
import com.example.lilingzhi.tworecyc.bean.TwoBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var oneLayoutM:LinearLayoutManager
    lateinit var twoLayoutM:LinearLayoutManager

    lateinit var oneAdapter:OneAdapter
    lateinit var twoAdapter: TwoAdapter

    lateinit var oneitemD:DividerItemDecoration
    lateinit var twoitemD:DividerItemDecoration

    var oneData:MutableList<OneBean> = mutableListOf()
    var twoData:MutableList<TwoBean> = mutableListOf()

    var oneIndex=-1

    lateinit var handler:Handler
    var rightClick:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        initView()



    }

    fun initData(){


        handler= Handler()

        var i=0
        var j=0
        while (i<20){
            oneData.add(OneBean(i,"标题："+i))
            j=0
            twoData.add(TwoBean(i,"标题："+i,true))
            while (j<5){
                twoData.add(TwoBean(i,"内容:"+j,false))
                j++
            }
            i++

        }
    }


    fun initView(){
        oneLayoutM=LinearLayoutManager(this)
        oneLayoutM.orientation=LinearLayoutManager.VERTICAL

        twoLayoutM=LinearLayoutManager(this)
        oneLayoutM.orientation=LinearLayoutManager.VERTICAL

        recyc_one.layoutManager=oneLayoutM
        recyc_two.layoutManager=twoLayoutM

        oneitemD= DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        twoitemD= DividerItemDecoration(this,DividerItemDecoration.VERTICAL)

        recyc_one.addItemDecoration(oneitemD)
        recyc_two.addItemDecoration(twoitemD)

        oneAdapter=OneAdapter(R.layout.item_one,oneData)
        twoAdapter=TwoAdapter(R.layout.item_two,twoData)
        recyc_one.adapter=oneAdapter
        recyc_two.adapter=twoAdapter


        oneAdapter.setOnItemClickListener { adapter, view, position ->

            rightClick=true
            //点击变色
            select(position)

            //点击右侧滚动
            //判断右侧滚动到哪里
            var twoI=0
            while (twoI<twoData.size){
                if(twoData.get(twoI).id==oneData.get(position).id){
                    break;
                }
                twoI++
            }

            RecycUtil.moveToPositAndTop(twoI,twoLayoutM,recyc_two,handler)
        }


       recyc_two.addOnScrollListener(object :RecyclerView.OnScrollListener(){
           override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
               //切断子母列表循环联调
               if(rightClick==false&& newState == RecyclerView.SCROLL_STATE_IDLE){
                   //判断当前子列表显示哪个id的内容
                   var now=0
                   var first= twoLayoutM.findFirstVisibleItemPosition()
                   if(twoData.get(first).isTitle){
                       now=twoData.get(first).id
                   }else{
                       if(twoData.get(first).id+1>oneData.get(oneData.size-1).id){
                           now=twoData.get(first).id
                       }else{
                           now=twoData.get(first).id+1
                       }
                   }
                   //滚动主列表

                   RecycUtil.moveToPositAndCenter(now,oneLayoutM,recyc_one,handler)
                   select(now)

               }else if(rightClick==true&& newState == RecyclerView.SCROLL_STATE_IDLE){
                   rightClick=false
               }else if(rightClick==true&&newState == RecyclerView.SCROLL_STATE_DRAGGING){
                   rightClick=false
               }
           }

           override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
               super.onScrolled(recyclerView, dx, dy)


           }
       })
    }




    fun select(position:Int){
        var i=oneAdapter.index
        oneAdapter.index=position
        if(i>=0){
            oneAdapter.notifyItemChanged(i)
        }
        oneAdapter.notifyItemChanged(position)
        Log.v("zzww"," i:"+i+" position:"+position)
    }



}
