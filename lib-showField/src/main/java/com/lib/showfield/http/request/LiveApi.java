package com.lib.showfield.http.request;

import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.http.model.BaseResponse;
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

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by JoeyChow
 * Date  2020-04-14 09:59
 * <p>
 * Description
 **/
public interface LiveApi {

    //首页赛事by tab-/app/channel/matches
    @POST("/app/channel/matche/channel")
    Observable<BaseResponse<List<DynamicMatchBean>>> queryMatchDataByTabId(@Body RequestBody body);

    //举报
    @POST("/app/report/add")
    Observable<BaseResponse<Boolean>> queryLiveReportByToken(@Body RequestBody body);

    //获取举报原因
    @POST("/app/dict/dictDetail/REPORT_TYPE")
    Observable<BaseResponse<List<DictDetailBean>>> queryLiveDictDetail();

    //主播信息
    @POST("/app/live/anchor/info")
    Observable<BaseResponse<LivePlayerInfo>> queryLivePlayerInfoById(@Body RequestBody body);

    //关注或取消关注
//    @POST("/app/follow/user/op")
//    Observable<BaseResponse<Object>> queryFocusOnAnchorById(@Body RequestBody body);

    //获取拉流地址
    @POST("/app/live/url/pull")
    Observable<BaseResponse<PullUrl>> queryPullUrlsByPullId(@Body RequestBody body);

    //热力值
    @POST("/app/live/hot/calculate")
    Observable<BaseResponse<Object>> queryHotValueByPullId(@Body RequestBody body);

    //礼物列表
    @POST("/app/room/gift/list")
    Observable<BaseResponse<List<GiftResponse>>> queryGiftListByRoomNo(@Body RequestBody body);

    //发送聊天信息
    @POST("/app/chat/message")
    Observable<BaseResponse<Object>> querySendMsgByRoomNo(@Body RequestBody body);

    //礼物送出
    @POST("/app/room/gift/send")
    Observable<BaseResponse<Object>> queryGiftSendByGiftId(@Body RequestBody body);

    //贡献榜
    @POST("/app/room/gift/rank")
    Observable<BaseResponse<ContributeList>> queryContributeByRoomNo(@Body RequestBody body);

    //用户卡片
    @POST("/app/room/manager/user/card")
    Observable<BaseResponse<UserCard>> queryUserCardById(@Body RequestBody body);

    //当前用户是否为房管
    @POST("/app/room/manager/check")
    Observable<BaseResponse<Boolean>> queryIsRoomManagerById(@Body RequestBody body);

    //禁言
    @POST("/app/room/manager/ban")
    Observable<BaseResponse<Boolean>> queryForbiddenUserById(@Body RequestBody body);

    //任命房管
    @POST("/app/room/manager/add")
    Observable<BaseResponse<Boolean>> querySetupUserManagerById(@Body RequestBody body);

    //解除房管
    @POST("/app/room/manager/remove")
    Observable<BaseResponse<Boolean>> queryUnSetupUserManagerById(@Body RequestBody body);

//    //余额
//    @POST("/app/user/remainingSum")
//    Observable<BaseResponse<UserCoins>> queryUserCoinsByToken();

    //获取聊天区历史消息
    @POST("/app/chat/pullMessage")
    Observable<BaseResponse<List<ChatResponse>>> queryHistoryChatMsgByRoomNo(@Body RequestBody body);

    //查看是否禁言
    @POST("/app/room/manager/ban/check")
    Observable<BaseResponse<BanTalkResponse>> queryUserIsBanTalkById(@Body RequestBody body);

    //礼物送出
    @POST("/app/room/gift/doubleHit")
    Observable<BaseResponse<Object>> queryHitGiftSendByGiftId(@Body RequestBody body);

    //进入房间
    @POST("/app/live/anchor/sendEnterRoomCmd")
    Observable<BaseResponse<Object>> queryEnterRoomCmdByRoomNo(@Body RequestBody body);

    //获取通用资源配置
    @POST("/app/commonConfig/android")
    Observable<BaseResponse<Config>> queryAppCommonResources();

    //直播-判断直播间模板类型
    @POST("/app/live/info")
    Observable<BaseResponse<LiveRoomTypeInfo>> queryLiveTypeInfoByRoomNo(@Body RequestBody body);

    //获取秀场推荐列表
    @POST("/app/index/follow/recommends")
    Observable<BaseResponse<FocusRecommendLives>> queryIndexFocusRecommend(@Body RequestBody body);

    //在线用户列表弹窗
    @POST("/app/live/getOnlineUsers")
    Observable<BaseResponse<LiveAudienceBean>> queryLiveAudienctList(@Body RequestBody body);

    //用户信息
    @POST("/app/user/profile")
    Observable<BaseResponse<UserInfo>> queryUserByToken();

    //带主播的清流直播流
    @POST("/app/match/anchorStreamUrl")
    Observable<BaseResponse<AnchorStreamUrl>> queryAnchorStreamUrlByRoomNo(@Body RequestBody body);
}
