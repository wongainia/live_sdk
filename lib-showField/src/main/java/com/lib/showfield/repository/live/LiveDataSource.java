package com.lib.showfield.repository.live;


import com.lib.showfield.bean.ContributeListParam;
import com.lib.showfield.bean.FocusChatAnchorParam;
import com.lib.showfield.bean.ForbiddenParam;
import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.bean.NotifySecurityParam;
import com.lib.showfield.bean.ReportParam;
import com.lib.showfield.bean.RoomNoItemIdParam;
import com.lib.showfield.bean.RoomNoMatchIdParam;
import com.lib.showfield.bean.RoomNoParam;
import com.lib.showfield.bean.SendMsgBean;
import com.lib.showfield.bean.SetupManagerParam;
import com.lib.showfield.bean.UserIdRoomNoParam;
import com.lib.showfield.bean.gift.bean.GiftListParam;
import com.lib.showfield.bean.gift.bean.GiftSendParam;
import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.model.Result;
import com.lib.showfield.http.respones.Config;
import com.lib.showfield.http.respones.live.AnchorStreamUrl;
import com.lib.showfield.http.respones.live.BanTalkResponse;
import com.lib.showfield.http.respones.live.ChatResponse;
import com.lib.showfield.http.respones.live.ContributeList;
import com.lib.showfield.http.respones.live.DictDetailBean;
import com.lib.showfield.http.respones.live.DynamicMatchBean;
import com.lib.showfield.http.respones.live.FocusRecommendLives;
import com.lib.showfield.http.respones.live.GiftResponse;
import com.lib.showfield.http.respones.live.LivePlayerInfo;
import com.lib.showfield.http.respones.live.LiveRoomTypeInfo;
import com.lib.showfield.http.respones.live.PullUrl;
import com.lib.showfield.http.respones.live.UserCard;
import com.lib.showfield.http.respones.live.UserCoins;
import com.lib.showfield.http.respones.live.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by JoeyChow
 * Date  2020-04-14 10:06
 * <p>
 * Description
 **/
public interface LiveDataSource {

    //获取子频道直播列表
    void queryMatchDataByTabId(Map param, Result<BaseResponse<List<DynamicMatchBean>>> result);

    //根据房间号获取直播模板
    void queryLiveTypeInfoByRoomNo(RoomNoParam param, Result<BaseResponse<LiveRoomTypeInfo>> result);

    //推荐直播列表
    void queryIndexFocusRecommend(NotifySecurityParam param, Result<BaseResponse<FocusRecommendLives>> result);

    //礼物列表
    void queryGiftListByRoomNo(GiftListParam param, Result<BaseResponse<List<GiftResponse>>> result);

    //用户余额
    //void queryUserCoinsByToken(Result<BaseResponse<UserCoins>> result);

    //贡献榜
    void queryContributeListByRoomNo(ContributeListParam param, Result<BaseResponse<ContributeList>> result);

    //用户卡片
    void queryUserCardById(UserIdRoomNoParam param, Result<BaseResponse<UserCard>> result);

    //是否房管
    void queryIsRoomManagerById(UserIdRoomNoParam param, Result<BaseResponse<Boolean>> result);

    //禁言
    void queryForbiddenUserById(ForbiddenParam param, Result<BaseResponse<Boolean>> result);

    //任命房管
    void querySetupUserManagerById(SetupManagerParam param, Result<BaseResponse<Boolean>> result);

    //解任房管
    void queryUnSetupUserManagerById(UserIdRoomNoParam param, Result<BaseResponse<Boolean>> result);

    //在线用户列表弹窗
    void queryLiveAudienctList(Map map, Result<BaseResponse<LiveAudienceBean>> result);

    //用户信息
    void queryUserByToken(Result<BaseResponse<UserInfo>> result);

    //发消息
    void querySendMsgByRoomNo(SendMsgBean param, Result<BaseResponse<Object>> result);

    //举报
    void queryLiveReportByToken(ReportParam param, Result<BaseResponse<Boolean>> result);

    //发送礼物
    void queryGiftSendById(GiftSendParam param, Result<BaseResponse<Object>> result);

    //举报类型
    void queryDictDetail(Result<BaseResponse<List<DictDetailBean>>> result);

    //连击礼物
    void queryHitGiftSendByGiftId(GiftSendParam param, Result<BaseResponse<Object>> result);

    //历史消息
    void queryHistoryChatMsgByRoomNo(RoomNoItemIdParam param, Result<BaseResponse<List<ChatResponse>>> result);

    //进入房间
    void queryEnterRoomCmdByRoomNo(RoomNoParam param, Result<BaseResponse<Object>> result);

    //清流主播拉流
    void queryAnchorStreamUrlByRoomNo(RoomNoMatchIdParam param, Result<BaseResponse<AnchorStreamUrl>> result);

    //用户信息
    void queryLivePlayerInfoById(RoomNoParam param, Result<BaseResponse<LivePlayerInfo>> result);

    //是否被禁言
    void queryUserIsBanTalkById(UserIdRoomNoParam param, Result<BaseResponse<BanTalkResponse>> result);

    //拉流
    void queryPullUrlsByPullId(RoomNoParam param, Result<BaseResponse<PullUrl>> result);

    //通用配置
    void queryAppCommonResources(Result<BaseResponse<Config>> result);
}
