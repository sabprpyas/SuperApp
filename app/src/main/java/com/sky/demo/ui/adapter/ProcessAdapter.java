package com.sky.demo.ui.adapter;

import android.view.View;

import com.sky.demo.R;
import com.sky.demo.model.ProcessEntity;
import com.sky.demo.ui.RecyclerAdapter;
import com.sky.demo.ui.RecycleHolder;

/**
 * @author sky QQ:1136096189
 * @Description: 进程适配器
 * @date 15/12/27 下午4:22
 */
public class ProcessAdapter extends RecyclerAdapter<ProcessEntity, RecycleHolder> {
    public ProcessAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected RecycleHolder onCreateBodyHolder(View view) {
        return new RecycleHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecycleHolder holder, int position) {

        holder.setText(R.id.tv_appName,"程序名称:" + datas.get(position).getAppName());
        holder.setText(R.id.tv_uid,"进程UID:" + datas.get(position).getUid());
        holder.setText(R.id.tv_pid,"进程PID:" + datas.get(position).getPid());
        holder.setText(R.id.tv_memSize,"进程大小:" + datas.get(position).getMemSize() + "KB");
        holder.setText(R.id.tv_processName,"进程名:" + datas.get(position).getProcessName());
    }
}
