package com.lib.showfield.http.respones.live;

import java.util.List;

/**
 * Created by upingu
 * Date  2020-05-07 19:10
 * <p>
 * Description
 **/
public class PullUrl {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 流畅
         * ratio : 320P
         * pullUrl : rtmp://pull.live.upingudata.cn/live/872ef594b2bc40f0922909231bf8809a
         */

        private String name;
        private String ratio;
        private String pullUrl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getPullUrl() {
            return pullUrl;
        }

        public void setPullUrl(String pullUrl) {
            this.pullUrl = pullUrl;
        }
    }
}
