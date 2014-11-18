package com.aiteu.dailyreading;

import java.util.ArrayList;
import java.util.List;

import com.aiteu.dailyreading.book.ItemDaily;
import com.aiteu.dailyreading.view.PrefixView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DailyAdapter extends BaseAdapter{
	
	private List<ItemDaily> dailyList = null; 
	private LayoutInflater mInflater = null;
	private Context mContext = null;
	// 颜色
    private int[] colors = { R.color.blue, R.color.green,
                    R.color.light_blue, R.color.ivory, R.color.yellow, R.color.lightgoldenrodyellow};
	
	public DailyAdapter(Context context){
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dailyList = new ArrayList<ItemDaily>();
		mContext = context;
	}
	
	public void setData(List<ItemDaily> daily){
		this.dailyList = daily;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dailyList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dailyList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mHolder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.daily_list_item, null);
			convertView.setBackgroundResource(colors[position%6]);
			mHolder = new ViewHolder();
			mHolder.title = (TextView)convertView.findViewById(R.id.item_title);
			mHolder.abstracts = (TextView)convertView.findViewById(R.id.item_abstract);
			mHolder.scanTimes = (PrefixView)convertView.findViewById(R.id.item_scan);
			convertView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder)convertView.getTag();
		}
		
		final ItemDaily item = dailyList.get(position);
		mHolder.title.setText(item.getTitle());
		mHolder.abstracts.setText(item.getAbstracts());
		mHolder.scanTimes.setText(String.format(mContext.getText(R.string.item_sufix_scan).toString(), item.getScanTimes()));
		return convertView;
	}
	
	class ViewHolder{
		private TextView title;
		private TextView abstracts;
		private PrefixView scanTimes;
	}
	
}
