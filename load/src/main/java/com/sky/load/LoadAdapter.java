package com.sky.load;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sky.load.http.DownloadService;
import com.sky.load.model.FileEntity;

import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/29 上午10:11
 */
public class LoadAdapter extends BaseAdapter {

    private List<FileEntity> files;

    public LoadAdapter() {
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.isEmpty() ? 0 : files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
            holder.name = (TextView) convertView.findViewById(R.id.tvFileName);
            holder.bar = (ProgressBar) convertView.findViewById(R.id.pbProgress);
            holder.start = (Button) convertView.findViewById(R.id.btStart);
            holder.stop = (Button) convertView.findViewById(R.id.btStop);
            holder.name.setText(files.get(position).getFileName());
            holder.bar.setMax(100);
            holder.start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent service = new Intent(context, DownloadService.class);
                    service.setAction(DownloadService.LOAD_START);
                    service.putExtra("file", files.get(position));
                    context.startService(service);
                }
            });
            holder.stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent service = new Intent(context, DownloadService.class);
                    service.setAction(DownloadService.LOAD_STOP);
                    service.putExtra("file", files.get(position));
                    context.startService(service);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bar.setProgress(files.get(position).getFinished());

        return convertView;
    }

    public void setProgress(int finished, String url) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getUrl().equals(url)) {
                files.get(i).setFinished(finished);
                notifyDataSetChanged();
            }
        }
    }

    static class ViewHolder {
        TextView name;
        ProgressBar bar;
        Button start;
        Button stop;

    }

}

