package com.zk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zk.entity.ProduceStatus;
import com.zk.weldmonitor.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 * 生产任务情况的RecyclerView适配器
 */
public class ProduceStatusAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<ProduceStatus> mDatas ;
    private Context mContext ;
    private LayoutInflater mInflater ;
    public ProduceStatusAdapter(Context context, List<ProduceStatus> datas ) {
        this.mContext =  context ;
        this.mDatas = datas ;
        mInflater = LayoutInflater.from(context) ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.produce_item, parent, false) ;
        MyViewHolder viewHolder = new MyViewHolder(view) ;
        return viewHolder;
    }

    //通过viewholder修改数据
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_produceId.setText(mDatas.get(position).getId());
        holder.tv_produceName.setText(mDatas.get(position).getName());
        holder.tv_produceStatus.setText(mDatas.get(position).getStatus());
        holder.tv_produceTime.setText(mDatas.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}

   /**
   	 * viewholder复用
   	 * @author Yan jiepeng
   	 * @time 2016/7/4 17:23
   	 */
class MyViewHolder extends RecyclerView.ViewHolder {

     TextView tv_produceId;
     TextView tv_produceName;
     TextView tv_produceTime;
     TextView tv_produceStatus;

    public MyViewHolder(View itemView) {
        super(itemView);
        tv_produceId = (TextView) itemView.findViewById(R.id.tv_produceID);
        tv_produceName = (TextView) itemView.findViewById(R.id.tv_produceName);
        tv_produceTime = (TextView) itemView.findViewById(R.id.tv_produceTime);
        tv_produceStatus = (TextView) itemView.findViewById(R.id.tv_produceStatus);
    }
}