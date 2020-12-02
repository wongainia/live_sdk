package com.lib.showfield.ui.fragment.live.contribute;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lib.showfield.R;
import com.lib.showfield.aop.CheckNet;
import com.lib.showfield.aop.SingleClick;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.base.action.StatusAction;
import com.lib.showfield.bean.ContributeListParam;
import com.lib.showfield.bean.FocusChatAnchorParam;
import com.lib.showfield.bean.ForbiddenParam;
import com.lib.showfield.bean.SetupManagerParam;
import com.lib.showfield.bean.UserIdRoomNoParam;
import com.lib.showfield.common.Constants;
import com.lib.showfield.databinding.FragmentGeneralListBinding;
import com.lib.showfield.http.exception.ApiException;
import com.lib.showfield.http.glide.GlideApp;
import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.model.Hosts;
import com.lib.showfield.http.model.Result;
import com.lib.showfield.http.respones.live.ContributeList;
import com.lib.showfield.http.respones.live.UserCard;
import com.lib.showfield.interfaces.OnGoUpListClickListener;
import com.lib.showfield.repository.live.LiveDataSource;
import com.lib.showfield.repository.live.remote.RemoteLiveDataSource;
import com.lib.showfield.ui.adapter.ContributeAdapter;
import com.lib.showfield.ui.fragment.LazyLoadFragmentV2;
import com.lib.showfield.ui.view.dialog.MessageDialog;
import com.lib.showfield.ui.view.dialog.PickDialog;
import com.lib.showfield.ui.view.dialog.UserPermissionDialog;
import com.lib.showfield.ui.view.dialog.UserSelfCardDialog;
import com.lib.showfield.ui.view.widget.HintLayout;
import com.lib.showfield.utils.FindDrawableByName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by upingu
 * Date  2020-06-05 10:13
 * <p>
 * Description
 **/
public class GeneralListFragment extends LazyLoadFragmentV2 implements StatusAction,
        View.OnClickListener {

    private FragmentGeneralListBinding generalListBinding;

    private RecyclerView mRvGeneral;
    private ContributeAdapter mContributeAdapter;
    private List<ContributeList.RankListBean> mList;

    private AppCompatImageView mNo1Avatar;
    private AppCompatTextView mNo1Nickname;
    private AppCompatTextView mNo1ContributeNum;
    private AppCompatImageView mNo1Level;

    private AppCompatImageView mNo2Avatar;
    private AppCompatTextView mNo2Nickname;
    private AppCompatTextView mNo2ContributeNum;
    private AppCompatImageView mNo2Level;

    private AppCompatImageView mNo3Avatar;
    private AppCompatTextView mNo3Nickname;
    private AppCompatTextView mNo3ContributeNum;
    private AppCompatImageView mNo3Level;

    private AppCompatImageView mUserAvatar;
    private AppCompatTextView mContributeNum;
    private AppCompatImageView mUserLevel;

    private AppCompatTextView mToGo;

    private LiveDataSource remoteLiveDataSource = RemoteLiveDataSource.getInstance();
    private String mRoomNo;
    private int mAnchorId;

    private int mCurrentClickUserId;
    private String mUserId;
    private int mUserType;
    private boolean isClickSelf;
    private int isFollow;
    private String mClickNickname;
    private int mUser1Id, mUser2Id, mUser3Id;

    private List<ContributeList.RankListBean> mRankList;

    protected OnGoUpListClickListener mOnGoUpListClickListener;
    private boolean isCameraPush;

    public void setOnGoUpListClickListener(OnGoUpListClickListener listener) {
        this.mOnGoUpListClickListener = listener;
    }

    public static GeneralListFragment newInstance(String roomNo, Boolean isLiving) {
        GeneralListFragment fragment = new GeneralListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomNo", roomNo);
        bundle.putBoolean("isLiving", isLiving);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        generalListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_general_list,
                null, false);
        mRvGeneral = generalListBinding.rvContributeGeneral;
        mUserAvatar = generalListBinding.ivGeneralUserAvatar;
        mContributeNum = generalListBinding.ivGeneralUserContribute;
        mUserLevel = generalListBinding.ivGeneralUserLevel;
        mToGo = generalListBinding.ivGeneralUserGo;
        initView();
        initData();

        generalListBinding.rlBottom.setClickable(true);
        return generalListBinding.getRoot();
    }

    protected void initView() {
        mList = new ArrayList<>();
        mRankList = new ArrayList<>();

        assert getArguments() != null;
        Bundle bundle = this.getArguments();
        mRoomNo = bundle.getString("roomNo");

        isCameraPush = getArguments().getBoolean("isLiving");
        generalListBinding.rlBottom.setVisibility(isCameraPush ? View.INVISIBLE : View.VISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvGeneral.setLayoutManager(manager);

        mContributeAdapter = new ContributeAdapter(R.layout.item_contribute, mList);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.include_contribute_head,
                null, false);
        mNo1Avatar = view.findViewById(R.id.iv_contribute_no1_avatar);
        mNo1Nickname = view.findViewById(R.id.tv_contribute_no1_nickname);
        mNo1ContributeNum = view.findViewById(R.id.tv_contribute_no1_num);
        mNo1Level = view.findViewById(R.id.iv_contribute_no1_level);

        mNo2Avatar = view.findViewById(R.id.iv_contribute_no2_avatar);
        mNo2Nickname = view.findViewById(R.id.tv_contribute_no2_nickname);
        mNo2ContributeNum = view.findViewById(R.id.tv_contribute_no2_num);
        mNo2Level = view.findViewById(R.id.iv_contribute_no2_level);

        mNo3Avatar = view.findViewById(R.id.iv_contribute_no3_avatar);
        mNo3Nickname = view.findViewById(R.id.tv_contribute_no3_nickname);
        mNo3ContributeNum = view.findViewById(R.id.tv_contribute_no3_num);
        mNo3Level = view.findViewById(R.id.iv_contribute_no3_level);

        mContributeAdapter.addHeaderView(view);
        mRvGeneral.setAdapter(mContributeAdapter);

        mToGo.setOnClickListener(this);

        mUserId = SPUtils.getInstance("user").getString(Constants.USER_ID);
    }

    protected void initData() {

        reloadList(new ContributeListParam(mRoomNo, 3));
        mContributeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mCurrentClickUserId = mRankList.get(position).getUserId();
                toGetUserCard(mCurrentClickUserId);
            }
        });

        mNo1Avatar.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                mCurrentClickUserId = mUser1Id;
                toGetUserCard(mCurrentClickUserId);
            }
        });

        mNo2Avatar.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                mCurrentClickUserId = mUser2Id;
                toGetUserCard(mCurrentClickUserId);
            }
        });

        mNo3Avatar.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                mCurrentClickUserId = mUser3Id;
                toGetUserCard(mCurrentClickUserId);
            }
        });
    }

    private void toGetUserCard(int currentClickUserId) {
        mUserId = SPUtils.getInstance("user").getString(Constants.USER_ID);
        if (currentClickUserId != 0) {
            if (mUserId.equals(String.valueOf(currentClickUserId))) {
                //点击自己
                isClickSelf = true;
                Log.d("zzy", "------点击了自己--------");
                reloadUserCard(new UserIdRoomNoParam(currentClickUserId,
                        mRoomNo));
            } else {
                isClickSelf = false;
                if (mUserType == 3) {
                    // 是本直播间的主播
                    reloadUserCard(new UserIdRoomNoParam(currentClickUserId,
                            mRoomNo));
                } else {
                    if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                        reloadIsRoomManager(mUserId, mRoomNo);
                    } else {
                        reloadUserCard(new UserIdRoomNoParam(currentClickUserId,
                                mRoomNo));
                    }
                }
            }
        }
    }

    private void reloadList(ContributeListParam param) {
        remoteLiveDataSource.queryContributeListByRoomNo(param, new Result<BaseResponse<ContributeList>>() {

            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(BaseResponse<ContributeList> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData().getRankList())) {
                    showComplete();
                    mRvGeneral.setVisibility(View.VISIBLE);
                    int size = responseData.getData().getRankList().size();
                    if (size > 2) {
                        ContributeList.RankListBean bean1 = responseData.getData().getRankList().get(0);
                        ContributeList.RankListBean bean2 = responseData.getData().getRankList().get(1);
                        ContributeList.RankListBean bean3 = responseData.getData().getRankList().get(2);

                        mNo1Nickname.setText(bean1.getNickname());
                        mNo1ContributeNum.setText(bean1.getContribution());

                        GlideApp.with(getContext())
                                .load(bean1.getAvatar())
                                .circleCrop()
                                .placeholder(R.drawable.icon_avatar)
                                .error(R.drawable.icon_avatar)
                                .into(mNo1Avatar);

                        mNo1Level.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_user_level" + bean1.getLevel()));


                        mNo2Nickname.setText(bean2.getNickname());
                        mNo2ContributeNum.setText(bean2.getContribution());
                        GlideApp.with(getContext())
                                .load(bean2.getAvatar())
                                .circleCrop()
                                .placeholder(R.drawable.icon_avatar)
                                .error(R.drawable.icon_avatar)
                                .into(mNo2Avatar);

                        mNo2Level.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_user_level" + bean2.getLevel()));

                        mNo3Nickname.setText(bean3.getNickname());
                        mNo3ContributeNum.setText(bean3.getContribution());
                        GlideApp.with(getContext())
                                .load(bean3.getAvatar())
                                .circleCrop()
                                .placeholder(R.drawable.icon_avatar)
                                .error(R.drawable.icon_avatar)
                                .into(mNo3Avatar);

                        mNo3Level.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_user_level" + bean3.getLevel()));

                        mUser1Id = bean1.getUserId();
                        mUser2Id = bean2.getUserId();
                        mUser3Id = bean3.getUserId();


                    } else if (size > 1) {
                        ContributeList.RankListBean bean1 = responseData.getData().getRankList().get(0);
                        ContributeList.RankListBean bean2 = responseData.getData().getRankList().get(1);

                        mNo1Nickname.setText(bean1.getNickname());
                        mNo1ContributeNum.setText(bean1.getContribution());
                        GlideApp.with(getContext())
                                .load(bean1.getAvatar())
                                .placeholder(R.drawable.icon_avatar)
                                .error(R.drawable.icon_avatar)
                                .circleCrop()
                                .into(mNo1Avatar);

                        mNo1Level.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_user_level" + bean1.getLevel()));

                        mNo2Nickname.setText(bean2.getNickname());
                        mNo2ContributeNum.setText(bean2.getContribution());
                        GlideApp.with(getContext())
                                .load(bean2.getAvatar())
                                .circleCrop()
                                .placeholder(R.drawable.icon_avatar)
                                .error(R.drawable.icon_avatar)
                                .into(mNo2Avatar);

                        mNo2Level.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_user_level" + bean2.getLevel()));

                        mUser1Id = bean1.getUserId();
                        mUser2Id = bean2.getUserId();

                    } else if (size > 0) {
                        ContributeList.RankListBean bean1 = responseData.getData().getRankList().get(0);

                        mNo1Nickname.setText(bean1.getNickname());
                        mNo1ContributeNum.setText(bean1.getContribution());

                        GlideApp.with(getContext())
                                .load(bean1.getAvatar())
                                .circleCrop()
                                .placeholder(R.drawable.icon_avatar)
                                .error(R.drawable.icon_avatar)
                                .into(mNo1Avatar);

                        mNo1Level.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_user_level" + bean1.getLevel()));

                        mUser1Id = bean1.getUserId();
                    }

                    GlideApp.with(getContext())
                            .load(responseData.getData().getAvatar())
                            .circleCrop()
                            .placeholder(R.drawable.icon_avatar)
                            .error(R.drawable.icon_avatar)
                            .into(mUserAvatar);

                    mContributeNum.setText(responseData.getData().getContribution());
                    mUserLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + responseData.getData().getLevel()));

                    mRankList.clear();
                    for (int i = 3; i < 11; i++) {
                        if (i >= size) {
                            mRankList.add(new ContributeList.RankListBean());
                        } else {
                            mRankList.add(responseData.getData().getRankList().get(i));
                        }
                    }

                    mContributeAdapter.setData$com_github_CymChad_brvah(mRankList);
                    mContributeAdapter.notifyDataSetChanged();
                } else {
                    mRvGeneral.setVisibility(View.GONE);
                    showLayout(R.drawable.icon_empty_comment, R.string.nobang_text, null, false);
                    GlideApp.with(getContext())
                            .load(responseData.getData().getAvatar())
                            .placeholder(R.drawable.icon_avatar)
                            .error(R.drawable.icon_avatar)
                            .circleCrop()
                            .into(mUserAvatar);

                    mUserLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + responseData.getData().getLevel()));

                    mContributeNum.setText(responseData.getData().getContribution());

                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (t instanceof ApiException) {
                    ApiException exception = (ApiException) t;
                    switch (exception.getCode()) {
                        case Hosts.RESP_CODE_ERROR:
                        case Hosts.RESP_CODE_FAIL:
                            mRvGeneral.setVisibility(View.GONE);
                            showError(view -> {
                                reloadList(new ContributeListParam(mRoomNo, 3));
                            });

                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            break;
                        default:
                            break;
                    }
                } else {
                    mRvGeneral.setVisibility(View.GONE);
                    showError(view -> {
                        reloadList(new ContributeListParam(mRoomNo, 3));
                    });

                }
            }
        });
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_general_user_go) {
            if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                if (mOnGoUpListClickListener != null)
                    mOnGoUpListClickListener.onGoUpClick();
            } else {
                //LoginActivity.start(getContext());
            }
        }
    }

    @Override
    public void requestData() {
        if (!ObjectUtils.isEmpty(mRoomNo))
            reloadList(new ContributeListParam(mRoomNo, 3));
    }

    @Override
    public HintLayout getHintLayout() {
        return generalListBinding.hlContributeGeneral;
    }

    /**
     * 用户卡片
     *
     * @param param
     */
    private void reloadUserCard(UserIdRoomNoParam param) {
        remoteLiveDataSource.queryUserCardById(param, new Result<BaseResponse<UserCard>>() {

            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(BaseResponse<UserCard> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    Log.d("zzy", "----用户卡片----: " + responseData.getData().toString());
                    if (!isClickSelf) {
                        showUserCardDialog(responseData.getData());
                    } else {
                        showUserCardSelfDialog(responseData.getData());
                    }
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (t instanceof ApiException) {
                    ApiException exception = (ApiException) t;
                    switch (exception.getCode()) {
                        case Hosts.RESP_CODE_ERROR:
                            break;
                        case Hosts.RESP_CODE_FAIL:
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    private void reloadIsRoomManager(String user, String roomNo) {
        UserIdRoomNoParam param = new UserIdRoomNoParam(Integer.parseInt(user), roomNo);
        if (!ObjectUtils.isEmpty(user)) {
            remoteLiveDataSource.queryIsRoomManagerById(param, new Result<BaseResponse<Boolean>>() {

                @Override
                public void onLoading() {
                }

                @Override
                public void onSuccess(BaseResponse<Boolean> responseData) {
                    if (!ObjectUtils.isEmpty(responseData.getData())) {
                        Log.d("zzy", "----是否为房管----: " + responseData.getData());
                        if (responseData.getData()) {
                            mUserType = 1;
                        } else {
                            mUserType = 0;
                        }

                        reloadUserCard(new UserIdRoomNoParam(mCurrentClickUserId,
                                mRoomNo));
                    }
                }

                @Override
                public void onFailed(Throwable t) {
                    if (t instanceof ApiException) {
                        ApiException exception = (ApiException) t;
                        switch (exception.getCode()) {
                            case Hosts.RESP_CODE_ERROR:
                                break;
                            case Hosts.RESP_CODE_FAIL:
                                break;
                            case Hosts.RESP_CODE_REPEAT:
                                SPUtils.getInstance("token").clear();
                                SPUtils.getInstance("idCard").clear();
                                SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                                break;
                            case Hosts.RESP_CODE_TOKEN_LOST:
                                SPUtils.getInstance("token").put(Constants.TOKEN,
                                        SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        }
    }

    /**
     * 关注
     *
     * @param param
     */
    /*@CheckNet
    private void reloadFocus(FocusChatAnchorParam param) {
        remoteLiveDataSource.queryFocusOnAnchorById(param, new Result<BaseResponse<Object>>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(BaseResponse<Object> responseData) {
                if (isFollow == 0) {
                    com.hjq.toast.ToastUtils.show("关注成功");
                } else {
                    com.hjq.toast.ToastUtils.show("取关成功");
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (t instanceof ApiException) {
                    ApiException exception = (ApiException) t;
                    switch (exception.getCode()) {
                        case Hosts.RESP_CODE_ERROR:
                            com.hjq.toast.ToastUtils.show(exception.getMessage());
                            break;
                        case Hosts.RESP_NO_LOGIN:
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            break;
                        case Hosts.RESP_AUTHOR_FOCUS_SELF:
                            break;
                    }
                }
            }
        });
    }*/
    @CheckNet
    private void reloadSetupManager(SetupManagerParam param) {
        remoteLiveDataSource.querySetupUserManagerById(param, new Result<BaseResponse<Boolean>>() {

            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(BaseResponse<Boolean> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    com.hjq.toast.ToastUtils.show("任命房管成功");
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (t instanceof ApiException) {
                    ApiException exception = (ApiException) t;
                    switch (exception.getCode()) {
                        case Hosts.RESP_CODE_ERROR:
                            break;
                        case Hosts.RESP_CODE_FAIL:
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @CheckNet
    private void reloadUnSetupManager(UserIdRoomNoParam param) {
        remoteLiveDataSource.queryUnSetupUserManagerById(param, new Result<BaseResponse<Boolean>>() {

            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(BaseResponse<Boolean> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    com.hjq.toast.ToastUtils.show("解任房管成功");
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (t instanceof ApiException) {
                    ApiException exception = (ApiException) t;
                    switch (exception.getCode()) {
                        case Hosts.RESP_CODE_ERROR:
                            break;
                        case Hosts.RESP_CODE_FAIL:
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }


    /**
     * 禁言弹窗
     */
    private void showForbiddenDialog(int userId) {
        ArrayList<String> lists = new ArrayList<>();
        lists.add("30分钟");
        lists.add("2小时");
        lists.add("1天");
        lists.add("3天");
        lists.add("7天");
        lists.add("30天");
        lists.add("永久");

        new PickDialog.Builder(getContext())
                .setTitle("禁言")
                .setDataList(lists)
                .setListener(new PickDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int data) {
                        dialog.dismiss();
                        reloadForbidden(new ForbiddenParam(mRoomNo, mAnchorId,
                                userId, data));
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    /**
     * 禁言
     *
     * @param param
     */
    private void reloadForbidden(ForbiddenParam param) {
        remoteLiveDataSource.queryForbiddenUserById(param, new Result<BaseResponse<Boolean>>() {

            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(BaseResponse<Boolean> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    if (responseData.getData()) {
                        ToastUtils.showShort("您成功禁言「" + mClickNickname + "」");
                    }
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (t instanceof ApiException) {
                    ApiException exception = (ApiException) t;
                    switch (exception.getCode()) {
                        case Hosts.RESP_CODE_ERROR:
                            break;
                        case Hosts.RESP_CODE_FAIL:
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    /**
     * 用户卡片弹窗
     */
    private void showUserCardDialog(UserCard userCard) {

        Log.d("zzy", "-----showUserCardDialog-----: " + mUserType);

        new UserPermissionDialog.Builder(getContext())
                .setCanceledOnTouchOutside(true)
                .setData(userCard, mUserType, mAnchorId)
                .setListener(new UserPermissionDialog.OnListener() {
                    @Override
                    public void onFocus(BaseDialog dialog, int opType) {
                        if (opType == 1) {
//                            new FocusDialog.Builder(getContext())
//                                    .setMessage("确定取消关注吗？")
//                                    .setListener(new FocusDialog.OnListener() {
//                                        @Override
//                                        public void onConfirm(BaseDialog dialog) {
//                                            //取关
//                                            isFollow = 1;
//                                            if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
//                                                reloadFocus(new FocusChatAnchorParam(String.valueOf(userCard.getUserId()), 0,
//                                                        1, mRoomNo));
//                                            } else {
//                                                dialog.dismiss();
//                                                LoginActivity.start(getContext());
//                                            }
//
//                                        }
//                                    }).show();
                            dialog.dismiss();
                        } else {
                            isFollow = 0;
                            if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                                //关注
//                                reloadFocus(new FocusChatAnchorParam(String.valueOf(userCard.getUserId()), 1,
//                                        1, mRoomNo));
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                                //LoginActivity.start(getContext());
                            }

                        }
                    }

                    @Override
                    public void onSetupManager(boolean isSetupManager, BaseDialog dialog) {
                        dialog.dismiss();
                        if (isSetupManager) {
                            new MessageDialog.Builder(getContext())
                                    .setMessage("是否确定将「" + userCard.getNickname() + "」任命为房管")
                                    .setListener(new MessageDialog.OnListener() {
                                        @Override
                                        public void onConfirm(BaseDialog dialog) {
                                            if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                                                reloadSetupManager(new SetupManagerParam(mRoomNo,
                                                        userCard.getUserId(), mAnchorId));
                                            } else {
                                                dialog.dismiss();
                                                //LoginActivity.start(getContext());
                                            }

                                        }
                                    }).show();

                        } else {
                            if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                                reloadUnSetupManager(new UserIdRoomNoParam(userCard.getUserId(), mRoomNo));
                            } else {
                                dialog.dismiss();
                                //LoginActivity.start(getContext());
                            }

                        }
                    }

                    @Override
                    public void onForbidden(BaseDialog dialog) {
                        dialog.dismiss();
                        showForbiddenDialog(userCard.getUserId());
                        mClickNickname = userCard.getNickname();
                    }
                })
                .show();
    }

    public void setParams(int anchorId) {
        this.mAnchorId = anchorId;
        mUserId = SPUtils.getInstance("user").getString(Constants.USER_ID);
        if (String.valueOf(anchorId).equals(mUserId)) {
            mUserType = 3;
        }
    }

    /**
     * 用户卡片弹窗(点击自己)
     */
    private void showUserCardSelfDialog(UserCard userCard) {
        new UserSelfCardDialog.Builder(getContext())
                .setData(userCard, mAnchorId)
                .setCanceledOnTouchOutside(true)
                .show();
    }
}
