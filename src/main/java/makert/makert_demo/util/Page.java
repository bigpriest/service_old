package makert.makert_demo.util;

public class Page {
    private int currPage; //当前页数
    private int pageSize; //每页显示记录数
    private int totalCount; //总记录数
    private int totalPage; //总页数

    public Page(int currPagePara, int pageSizePara, int totalCountPara) {
        currPage = currPagePara;
        pageSize = pageSizePara;
        totalCount = totalCountPara;
        //求出总页数
        if (totalCount % pageSize == 0){
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize+1;
        }
        //设置首页和尾页
        if (currPage <= 1){
            currPage = 1;
        }else if(currPage >= totalPage+1){
            currPage = totalPage;
        }
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
