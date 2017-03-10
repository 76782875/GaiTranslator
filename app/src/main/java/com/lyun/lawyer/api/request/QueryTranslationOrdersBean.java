package com.lyun.lawyer.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by ZHAOWEIWEI on 2017/2/21.
 */

public class QueryTranslationOrdersBean extends BaseRequest {

    public QueryTranslationOrdersBean(String pageid, String pagesize, String cardNo) {
        this.pageid = pageid;
        this.pagesize = pagesize;
        this.cardNo = cardNo;
    }

    private String pageid;
    private String pagesize;
    private String cardNo;

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
