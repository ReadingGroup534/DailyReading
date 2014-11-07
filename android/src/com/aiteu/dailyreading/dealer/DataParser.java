package com.aiteu.dailyreading.dealer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aiteu.dailyreading.book.ItemDaily;

public class DataParser {

	public static final int parseDailyCount(JSONObject json) {
		int count = 0;
		try{
			if(json.has("count")){
				count = json.getInt("count");
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return count;
	}
	
	public static final List<ItemDaily> parseDailyData(JSONObject json) {
		List<ItemDaily> data = new ArrayList<ItemDaily>();
		try {
			if (!json.has("list") || json.getString("list").equals("null")) {
				return data;
			}
			JSONArray listJson = json.getJSONArray("list");
			for(int i=0;i < listJson.length();i ++){
				JSONObject listItem = listJson.getJSONObject(i);
				ItemDaily dailyItem = new ItemDaily();
				dailyItem.setTitle(listItem.getString("title"));
				dailyItem.setAuthor(listItem.getString("author"));
				dailyItem.setAbstracts(listItem.getString("abstracts"));
				dailyItem.setArticleType(listItem.getString("articleType"));
				dailyItem.setRecommendStar(listItem.getInt("recommendStar"));
				dailyItem.setPraiseTimes(listItem.getInt("praiseTimes"));
				dailyItem.setShareTimes(listItem.getInt("shareTimes"));
				dailyItem.setScanTimes(listItem.getInt("scanTimes"));
				dailyItem.setDetailUrl(listItem.getString("detailUrl"));
				data.add(dailyItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}
}
