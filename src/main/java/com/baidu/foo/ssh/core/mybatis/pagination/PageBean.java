package com.baidu.foo.ssh.core.mybatis.pagination;

import java.util.List;

/**
 * 
 * @author zhanghao10@baidu.com
 *
 */
public class PageBean<E> {

	private int pageSize = 10;
	private int totalPage = 0;
	private int totalRecord = 0;
	private int currentPage = 1;

	private boolean hasPreviousPage = false;
	private boolean hasNextPage = false;
	private int endPage = 0;
	private int startPage = 0;
	
	private List<E> data;
	
	public PageBean() {}
	
	public PageBean(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void doPaging() {
		
		if ((totalRecord % pageSize) == 0) {
			totalPage = totalRecord / pageSize;
		} else {
			totalPage = totalRecord / pageSize + 1;
		}
		
        if(currentPage > 0) {
        	calculatePage();	
        }
        
	}
	
	private void calculatePage() {
		
		if ((currentPage - 1) > 0) {
			hasPreviousPage = true;
		} else {
			hasPreviousPage = false;
		}

		if (currentPage >= totalPage) {
			hasNextPage = false;
		} else {
			hasNextPage = true;
		}

		if (currentPage * pageSize < totalRecord) {
			endPage = currentPage * pageSize;
			startPage = endPage - pageSize;
		} else {
			endPage = totalRecord;
			startPage = pageSize * (totalPage - 1);
		}
		
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

}
