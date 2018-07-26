package com.example.lilingzhi.tworecyc.adapter

import android.support.v7.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lilingzhi.tworecyc.R
import com.example.lilingzhi.tworecyc.bean.OneBean

class OneAdapter(var layoutId:Int,var datas:List<OneBean>):BaseQuickAdapter<OneBean,BaseViewHolder>(layoutId,datas){


    var index=-1;


    override fun convert(helper: BaseViewHolder?, item: OneBean?) {

        helper!!.getView<TextView>(R.id.tv).setText(item!!.name)

        if(index==item.id){
            helper.getView<RelativeLayout>(R.id.rl).setBackgroundColor(mContext.resources.getColor(R.color.gray))
        }else{
            helper.getView<RelativeLayout>(R.id.rl).setBackgroundColor(mContext.resources.getColor(R.color.white))
        }
    }




}