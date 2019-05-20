package javax.core.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 分页对象
 * @Auther: qingruizhu
 * @Date: 2019-05-16 16:02
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private long start;//当前页的第一条数据在list中的位置，从0开始
    private int pageSize;//每页的记录数
    private long total;//总记录数
    private List<T> rows;//当前页的记录


    public Page() {
        this(0,DEFAULT_PAGE_SIZE,0,new ArrayList<T>());
    }

    public Page(long start, int pageSize, long total, List<T> rows) {
        this.start = start;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = rows;
    }

    /**
     * 获取总页数
     */
    public long getTotalPages() {
        if (total % pageSize == 0) {
            return total / pageSize;
        }
        return total / pageSize + 1;
    }

    /**
     * 获取当前页码
     */
    public long getPageNo() {
        return start / pageSize + 1;
    }

    /**
     * 是否有上一页
     */
    public boolean hasPreviousPage(){
        return this.getPageNo() > 1;
    }

    /**
     * 是否有下一页
     */
    public boolean hasNextpage() {
        return this.getPageNo() < this.getTotalPages();
    }
    /**
     * 获取任一页的第一条数据在list中的位置
     */


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
