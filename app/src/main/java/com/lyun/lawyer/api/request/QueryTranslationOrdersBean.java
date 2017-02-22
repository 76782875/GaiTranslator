package com.lyun.lawyer.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by ZHAOWEIWEI on 2017/2/21.
 */

public class QueryTranslationOrdersBean extends BaseRequest {

    public QueryTranslationOrdersBean(String pageid, String pagesize) {
        this.pageid = pageid;
        this.pagesize = pagesize;
    }

    private String pageid;
    private String pagesize;

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
}
