package com.aiteu.reading.base;

import java.util.List;
import java.util.Map;

/**
 * 用于保存数据库操作后结果和状态值
 * 
 * @author liwei
 * 
 */
public class ResultsContainer {
	public static final int CODE_OK = 0x01;
	public static final int CODE_ERROR = -0x01;
	public static final int CODE_EMPTY = 0x02;

	private int code;
	private boolean paging;
	private Map<String, Object> hashResult;
	private List<Map<String, Object>> arrayResults;
	private SplitPage splitPage;

	public ResultsContainer() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public Map<String, Object> getHashResult() {
		return hashResult;
	}

	public void setHashResult(Map<String, Object> hashResult) {
		this.hashResult = hashResult;
	}

	public List<Map<String, Object>> getArrayResults() {
		return arrayResults;
	}

	public void setArrayResults(List<Map<String, Object>> arrayResults) {
		this.arrayResults = arrayResults;
	}

	public SplitPage getSplitPage() {
		return splitPage;
	}

	public void setSplitPage(SplitPage splitPage) {
		this.splitPage = splitPage;
	}
}
