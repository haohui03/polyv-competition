package com.scut.plvlee2.Streamlive;

import static com.plv.thirdpart.litepal.LitePalApplication.getContext;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.beauty.di.PLVBeautyModule;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVCustomGiftBean;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVSpecialTypeTag;
import com.easefun.polyv.livecommon.module.modules.chatroom.holder.PLVChatMessageItemType;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.data.PLVChatroomData;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMode;
import com.easefun.polyv.livecommon.module.modules.document.presenter.PLVDocumentPresenter;
import com.easefun.polyv.livecommon.module.modules.document.view.PLVAbsDocumentView;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager;
import com.easefun.polyv.livecommon.module.modules.socket.PLVAbsOnSocketEventListener;
import com.easefun.polyv.livecommon.module.modules.socket.PLVSocketLoginManager;
import com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter;
import com.easefun.polyv.livecommon.module.modules.streamer.view.PLVAbsStreamerView;
import com.easefun.polyv.livecommon.module.utils.PLVLiveLocalActionHelper;
import com.easefun.polyv.livecommon.module.utils.PLVToast;
import com.easefun.polyv.livecommon.module.utils.result.PLVLaunchResult;
import com.easefun.polyv.livecommon.ui.util.PLVViewUtil;
import com.easefun.polyv.livecommon.ui.widget.PLVConfirmDialog;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.window.PLVBaseActivity;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.custom.PolyvCustomEvent;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebProcessor;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebView;
import com.easefun.polyv.livescenes.document.model.PLVSPPTPaintStatus;
import com.easefun.polyv.livescenes.model.bulletin.PolyvBulletinVO;
import com.easefun.polyv.livestreamer.modules.document.PLVLSDocumentLayout;
import com.easefun.polyv.livestreamer.modules.document.widget.PLVLSDocumentInputWidget;
import com.easefun.polyv.livestreamer.modules.streamer.di.PLVLSStreamerModule;
import com.easefun.polyv.livestreamer.scenes.PLVLSLiveStreamerActivity;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.feature.login.IPLVSceneLoginManager;
import com.plv.livescenes.feature.login.PLVSceneLoginManager;
import com.plv.livescenes.feature.login.model.PLVLoginVO;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import com.plv.socket.event.chat.PLVChatImgEvent;
import com.plv.socket.event.chat.PLVCloseRoomEvent;
import com.plv.socket.event.chat.PLVLikesEvent;
import com.plv.socket.event.login.PLVKickEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.event.login.PLVLoginRefuseEvent;
import com.plv.socket.event.login.PLVLogoutEvent;
import com.plv.socket.event.login.PLVReloginEvent;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import com.scut.plvlee2.R;
import com.scut.plvlee2.mLoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
/** @deprecated */
public class StreamTest extends PLVBaseActivity {
    DocumentLy documentLy;
    //数据管理器
    IPLVLiveRoomDataManager liveRoomDataManager;
    PLVChatroomPresenter chatroomPresenter;
    SurfaceView surfaceView;
    private IPLVSocketLoginManager socketLoginManager;
    private PLVSDocumentWebView plvlsDocumentWebView;

    private IPLVDocumentContract.Presenter documentPresenter;
    private IPLVStreamerContract.IStreamerPresenter streamerPresenter;
    private static final String EXTRA_CHANNEL_ID = "channelId";             // 频道号
    private static final String EXTRA_VIEWER_ID = "viewerId";               // 开播者Id
    private static final String EXTRA_VIEWER_NAME = "viewerName";           // 开播者昵称
    private static final String EXTRA_AVATAR_URL = "avatarUrl";             // 开播者头像url
    private static final String EXTRA_ACTOR = "actor";                      // 开播者头衔
    private static final String EXTRA_USERTYPE = "usertype";                // 开播者角色
    private static final String EXTRA_COLIN_MIC_TYPE = "colinMicType";      // 嘉宾连麦类型
    private static final String EXTRA_IS_OPEN_MIC = "isOpenMic";            // 麦克风开关
    private static final String EXTRA_IS_OPEN_CAMERA = "isOpenCamera";      // 摄像头开关
    private static final String EXTRA_IS_FRONT_CAMERA = "isFrontCamera";    // 摄像头方向



    private void initDocumentPresenter() {

    }

    private void injectDependency() {
        //美颜模块必须加载
        PLVDependManager.getInstance()
                .switchStore(this)
                .addModule(PLVBeautyModule.instance)
                .addModule(PLVLSStreamerModule.instance);
    }
    /**
     * 启动手机开播三分屏页
     *
     * @param activity      上下文Activity
     * @param channelId     频道号
     * @param viewerId      开播者ID
     * @param viewerName    开播者昵称
     * @param avatarUrl     开播者头像url
     * @param actor         开播者头衔
     * @param isOpenMic     是否打开麦克风
     * @param isOpenCamera  是否打开相机
     * @param isFrontCamera 是否使用前置摄像头
     * @return PLVLaunchResult.isSuccess=true表示启动成功，PLVLaunchResult.isSuccess=false表示启动失败
     */
    @NonNull
    public static PLVLaunchResult launchStreamer(@NonNull Activity activity,
                                                 @NonNull String channelId,
                                                 @NonNull String viewerId,
                                                 @NonNull String viewerName,
                                                 @NonNull String avatarUrl,
                                                 @NonNull String actor,
                                                 @NonNull String usertype,
                                                 @NonNull String colinMicType,
                                                 boolean isOpenMic,
                                                 boolean isOpenCamera,
                                                 boolean isFrontCamera) {
        if (activity == null) {
            return PLVLaunchResult.error("activity 为空，启动手机开播三分屏页失败！");
        }
        if (TextUtils.isEmpty(channelId)) {
            return PLVLaunchResult.error("channelId 为空，启动手机开播三分屏页失败！");
        }
        if (TextUtils.isEmpty(viewerId)) {
            return PLVLaunchResult.error("viewerId 为空，启动手机开播三分屏页失败！");
        }
        if (TextUtils.isEmpty(viewerName)) {
            return PLVLaunchResult.error("viewerName 为空，启动手机开播三分屏页失败！");
        }
        if (TextUtils.isEmpty(avatarUrl)) {
            return PLVLaunchResult.error("avatarUrl 为空，启动手机开播三分屏页失败！");
        }
        if (TextUtils.isEmpty(actor)) {
            return PLVLaunchResult.error("actor 为空，启动手机开播三分屏页失败！");
        }
        if (TextUtils.isEmpty(usertype)) {
            return PLVLaunchResult.error("usertype 为空，启动手机开播三分屏页失败！");
        }
        if (TextUtils.isEmpty(colinMicType)) {
            return PLVLaunchResult.error("colinMicType 为空，启动手机开播三分屏页失败！");
        }

        Intent intent = new Intent(activity, PLVLSLiveStreamerActivity.class);
        intent.putExtra(EXTRA_CHANNEL_ID, channelId);
        intent.putExtra(EXTRA_VIEWER_ID, viewerId);
        intent.putExtra(EXTRA_VIEWER_NAME, viewerName);
        intent.putExtra(EXTRA_AVATAR_URL, avatarUrl);
        intent.putExtra(EXTRA_ACTOR, actor);
        intent.putExtra(EXTRA_USERTYPE, usertype);
        intent.putExtra(EXTRA_COLIN_MIC_TYPE, colinMicType);
        intent.putExtra(EXTRA_IS_OPEN_MIC, isOpenMic);
        intent.putExtra(EXTRA_IS_OPEN_CAMERA, isOpenCamera);
        intent.putExtra(EXTRA_IS_FRONT_CAMERA, isFrontCamera);
        activity.startActivity(intent);
        return PLVLaunchResult.success();
    }
    // <editor-fold defaultstate="collapsed" desc="聊天室 - MVP模式的view层实现">
    private PLVAbsChatroomView chatroomView = new PLVAbsChatroomView() {
        @Override
        public int getSpeakEmojiSize() {
            return ConvertUtils.dp2px(12);
        }

        @Override
        public void onImgEvent(@NonNull PLVChatImgEvent chatImgEvent) {
            super.onImgEvent(chatImgEvent);
        }

        @Override
        public void onLikesEvent(@NonNull PLVLikesEvent likesEvent) {
            super.onLikesEvent(likesEvent);
        }

        @Override
        public void onLoginEvent(@NonNull PLVLoginEvent loginEvent) {
            super.onLoginEvent(loginEvent);
        }

        @Override
        public void onLogoutEvent(@NonNull PLVLogoutEvent logoutEvent) {
            super.onLogoutEvent(logoutEvent);
        }

        @Override
        public void onBulletinEvent(@NonNull PolyvBulletinVO bulletinVO) {
            super.onBulletinEvent(bulletinVO);
        }

        @Override
        public void onRemoveBulletinEvent() {
            super.onRemoveBulletinEvent();
        }

        @Override
        public void onCloseRoomEvent(@NonNull final PLVCloseRoomEvent closeRoomEvent) {
            super.onCloseRoomEvent(closeRoomEvent);
        }

        @Override
        public void onRemoveMessageEvent(@Nullable String id, boolean isRemoveAll) {
            super.onRemoveMessageEvent(id, isRemoveAll);
        }

        @Override
        public void onCustomGiftEvent(@NonNull PolyvCustomEvent.UserBean userBean, @NonNull PLVCustomGiftBean customGiftBean) {
            super.onCustomGiftEvent(userBean, customGiftBean);
        }

        @Override
        public void onLocalSpeakMessage(@Nullable PolyvLocalMessage localMessage) {
            super.onLocalSpeakMessage(localMessage);
            if (localMessage == null) {
                return;
            }
            final List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData<>(localMessage, PLVChatMessageItemType.ITEMTYPE_SEND_SPEAK, new PLVSpecialTypeTag(localMessage.getUserId())));
            //添加信息至列表
        }

        @Override
        public void onLoadEmotionMessage(@Nullable PLVChatEmotionEvent emotionEvent) {
            super.onLoadEmotionMessage(emotionEvent);
            if (emotionEvent == null) {
                return;
            }
            final List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData<>(emotionEvent, PLVChatMessageItemType.ITEMTYPE_EMOTION, emotionEvent.isSpecialTypeOrMe() ? new PLVSpecialTypeTag(emotionEvent.getUserId()) : null));
            //添加信息至列表
        }

        @Override
        public void onLocalImageMessage(@Nullable PolyvSendLocalImgEvent localImgEvent) {
            super.onLocalImageMessage(localImgEvent);
            if (localImgEvent == null) {
                return;
            }
            List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData<>(localImgEvent, PLVChatMessageItemType.ITEMTYPE_SEND_IMG, new PLVSpecialTypeTag(localImgEvent.getUserId())));
            //添加信息至列表
        }

        @Override
        public void onSendProhibitedWord(@NonNull final String prohibitedMessage, @NonNull final String hintMsg, @NonNull final String status) {
            super.onSendProhibitedWord(prohibitedMessage, hintMsg, status);
        }

        @Override
        public void onSpeakImgDataList(List<PLVBaseViewData> chatMessageDataList) {
            super.onSpeakImgDataList(chatMessageDataList);
            //添加信息至列表
        }

        @Override
        public void onHistoryDataList(List<PLVBaseViewData<PLVBaseEvent>> chatMessageDataList, int requestSuccessTime, boolean isNoMoreHistory, int viewIndex) {
            super.onHistoryDataList(chatMessageDataList, requestSuccessTime, isNoMoreHistory, viewIndex);

        }

        @Override
        public void onHistoryRequestFailed(String errorMsg, Throwable t, int viewIndex) {
            super.onHistoryRequestFailed(errorMsg, t, viewIndex);
            if (viewIndex == chatroomPresenter.getViewIndex(chatroomView)) {
                PLVToast.Builder.context(getContext())
                        .setText(getContext().getString(com.easefun.polyv.livestreamer.R.string.plv_chat_toast_history_load_failed) + ": " + errorMsg)
                        .build()
                        .show();
            }
        }
    };
    // </editor-fold>
    void mlaunchStreamer(@NonNull Activity activity,
                        @NonNull String channelId,
                        @NonNull String viewerId,
                        @NonNull String viewerName,
                        @NonNull String avatarUrl,
                        @NonNull String actor,
                        @NonNull String role,
                        @NonNull String colinMicType,
                        boolean isOpenMic,
                        boolean isOpenCamera,
                        boolean isFrontCamera){


        // 设置Config数据
        PLVLiveChannelConfigFiller.setupUser(viewerId, viewerName, avatarUrl, role, actor);
        PLVLiveChannelConfigFiller.setupChannelId(channelId);
        PLVLiveChannelConfigFiller.setColinMicType(colinMicType);

        PLVLiveLocalActionHelper.getInstance().enterChannel(channelId);
        liveRoomDataManager = new PLVLiveRoomDataManager(PLVLiveChannelConfigFiller.generateNewChannelConfig());

        // 进行网络请求，获取直播详情数据
        liveRoomDataManager.requestChannelDetail();
        //获取推流？
        streamerPresenter = new PLVStreamerPresenter(liveRoomDataManager);
        streamerPresenter.registerView(streamerView);
        streamerPresenter.init();
        streamerPresenter.enableLocalVideo(true);
        streamerPresenter.enableLocalVideo(true);
        streamerPresenter.setCameraDirection(true);
        streamerPresenter.requestMemberList();

        streamerPresenter.createRenderView(Utils.getApp());
        chatroomPresenter = new PLVChatroomPresenter(liveRoomDataManager);
        chatroomPresenter.registerView(chatroomView);
        chatroomPresenter.init();
        chatroomPresenter.requestChatHistory(1);
        PLVChatroomData plvChatroomData =  chatroomPresenter.getData();

        socketLoginManager = new PLVSocketLoginManager(liveRoomDataManager);
        socketLoginManager.init();
        socketLoginManager.setOnSocketEventListener(onSocketEventListener);
        socketLoginManager.login();

        plvlsDocumentWebView = findViewById(R.id.web_view_test);
        plvlsDocumentWebView.setNeedDarkBackground(true);
        plvlsDocumentWebView.loadWeb();
        documentPresenter = PLVDocumentPresenter.getInstance();

        documentPresenter.init(this, liveRoomDataManager, new PLVSDocumentWebProcessor(plvlsDocumentWebView));

        //documentPresenter.
        Pair<Boolean, Integer> sendResult = chatroomPresenter.sendChatMessage( new PolyvLocalMessage("hello the world"));
    }
    private PLVAbsDocumentView documentMvpView = new PLVAbsDocumentView() {

        @Override
        public void onSwitchShowMode(PLVDocumentMode showMode) {

        }

        @Override
        public void onPptPageChange(int autoId, int pageId) {

        }

        @Override
        public void onPptPaintStatus(@Nullable PLVSPPTPaintStatus pptPaintStatus) {
            //显示编辑框
        }

        @Override
        public void onZoomValueChanged(String zoomValue) {
        }
    };


    private IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener = new PLVAbsOnSocketEventListener() {
        @Override
        public void handleLoginIng(boolean isReconnect) {
            super.handleLoginIng(isReconnect);
            if (isReconnect) {
                PLVToast.Builder.context(getContext())
                        .setText(com.easefun.polyv.livestreamer.R.string.plv_chat_toast_reconnecting)
                        .build()
                        .show();
            }
        }

        @Override
        public void handleLoginSuccess(boolean isReconnect) {
            super.handleLoginSuccess(isReconnect);
            if (isReconnect) {
                PLVToast.Builder.context(getContext())
                        .setText(com.easefun.polyv.livestreamer.R.string.plv_chat_toast_reconnect_success)
                        .build()
                        .show();
            }
            //Pair<Boolean, Integer> sendResult = chatroomPresenter.sendChatMessage( new PolyvLocalMessage("hello the world"));
        }

        @Override
        public void handleLoginFailed(@NonNull Throwable throwable) {
            super.handleLoginFailed(throwable);
            PLVToast.Builder.context(getContext())
                    .setText(getResources().getString(com.easefun.polyv.livestreamer.R.string.plv_chat_toast_login_failed) + ":" + throwable.getMessage())
                    .build()
                    .show();
        }

        @Override
        public void onKickEvent(@NonNull PLVKickEvent kickEvent, boolean isOwn) {
            super.onKickEvent(kickEvent, isOwn);
            if (isOwn) {
                PLVToast.Builder.context(PLVAppUtils.getApp())
                        .shortDuration()
                        .setText(com.easefun.polyv.livestreamer.R.string.plv_chat_toast_kicked_streamer)
                        .build()
                        .show();
                ((Activity) getContext()).finish();
            }
        }

        @Override
        public void onLoginRefuseEvent(@NonNull PLVLoginRefuseEvent loginRefuseEvent) {
            super.onLoginRefuseEvent(loginRefuseEvent);

        }

        @Override
        public void onReloginEvent(@NonNull PLVReloginEvent reloginEvent) {
            super.onReloginEvent(reloginEvent);
        }
    };
    String LinkId ;
    List<PLVLinkMicItemDataBean> mlinkMicList;
    private PLVAbsStreamerView streamerView = new PLVAbsStreamerView(){

        @Override
        public void onStreamerEngineCreatedSuccess(String linkMicUid, List<PLVLinkMicItemDataBean> linkMicList) {
            super.onStreamerEngineCreatedSuccess(linkMicUid, linkMicList);
            LinkId =linkMicUid;
            mlinkMicList = mlinkMicList;
            //surfaceView = streamerPresenter.createRenderView(getApplicationContext());
            streamerPresenter.setupRenderView(surfaceView,LinkId);
            streamerPresenter.startLiveStream();
            documentPresenter.notifyStreamStatus(true);
            PLVDocumentPresenter.getInstance().switchShowMode(PLVDocumentMode.WHITEBOARD);


        }

        @Override
        public void onUserMuteVideo(String uid, boolean mute, int streamerListPos, int memberListPos) {
            super.onUserMuteVideo(uid, mute, streamerListPos, memberListPos);
        }

        @Override
        public void onUserMuteAudio(String uid, boolean mute, int streamerListPos, int memberListPos) {
            super.onUserMuteAudio(uid, mute, streamerListPos, memberListPos);
        }

        @Override
        public void onLocalUserMicVolumeChanged(int volume) {
            super.onLocalUserMicVolumeChanged(volume);
        }

        @Override
        public void onRemoteUserVolumeChanged(List<PLVMemberItemDataBean> linkMicList) {
            super.onRemoteUserVolumeChanged(linkMicList);
        }

        @Override
        public void onUsersJoin(List<PLVLinkMicItemDataBean> dataBeanList) {
            super.onUsersJoin(dataBeanList);
        }

        @Override
        public void onUsersLeave(List<PLVLinkMicItemDataBean> dataBeanList) {
            super.onUsersLeave(dataBeanList);
        }

        @Override
        public void onNetworkQuality(int quality) {
            super.onNetworkQuality(quality);
        }

        @Override
        public void onUpdateStreamerTime(int secondsSinceStartTiming) {
            super.onUpdateStreamerTime(secondsSinceStartTiming);
        }

        @Override
        public void onShowNetBroken() {
            super.onShowNetBroken();
        }

        @Override
        public void onStatesToStreamEnded() {
            super.onStatesToStreamEnded();
        }

        @Override
        public void onStatesToStreamStarted() {
            super.onStatesToStreamStarted();
        }

        @Override
        public void onStreamerError(int errorCode, Throwable throwable) {
            super.onStreamerError(errorCode, throwable);
        }

        @Override
        public void onUpdateMemberListData(List<PLVMemberItemDataBean> dataBeanList) {
            super.onUpdateMemberListData(dataBeanList);
        }

        @Override
        public void onCameraDirection(boolean front, int pos) {
            super.onCameraDirection(front, pos);
        }

        @Override
        public void onUpdateSocketUserData(int pos) {
            super.onUpdateSocketUserData(pos);
        }

        @Override
        public void onAddMemberListData(int pos) {
            super.onAddMemberListData(pos);
        }

        @Override
        public void onRemoveMemberListData(int pos) {
            super.onRemoveMemberListData(pos);
        }

        @Override
        public void onReachTheInteractNumLimit() {
            super.onReachTheInteractNumLimit();
        }

        @Override
        public void onUserRequest(String uid) {
            super.onUserRequest(uid);
        }

        @Override
        public void onStreamLiveStatusChanged(boolean isLive) {
            super.onStreamLiveStatusChanged(isLive);
        }

        @Override
        public void onGuestRTCStatusChanged(int pos) {
            super.onGuestRTCStatusChanged(pos);
        }

        @Override
        public void onGuestMediaStatusChanged(int pos) {
            super.onGuestMediaStatusChanged(pos);
        }

        @Override
        public void onScreenShareChange(int position, boolean isShare, int extra) {
            super.onScreenShareChange(position, isShare, extra);
        }

        @Override
        public void onFirstScreenChange(String linkMicUserId, boolean isFirstScreen) {
            super.onFirstScreenChange(linkMicUserId, isFirstScreen);
        }

        @Override
        public void onDocumentStreamerViewChange(boolean documentInMainScreen) {
            super.onDocumentStreamerViewChange(documentInMainScreen);
        }

        @Override
        public void onSetPermissionChange(String type, boolean isGranted, boolean isCurrentUser, PLVSocketUserBean user) {
            super.onSetPermissionChange(type, isGranted, isCurrentUser, user);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependency();
        setContentView(R.layout.streamer_layout_test);
        surfaceView = findViewById(R.id.sf_test);
        loginStreamer();
    }

    private void initLiveRoomManager() {
        // 使用PLVLiveChannelConfigFiller配置好直播参数后，用其创建直播间数据管理器实例
        liveRoomDataManager = new PLVLiveRoomDataManager(PLVLiveChannelConfigFiller.generateNewChannelConfig());

        // 进行网络请求，获取直播详情数据
        liveRoomDataManager.requestChannelDetail();
    }
    private void requireStreamerPermissionThenRun(
            final boolean camera,
            final boolean audio,
            final Runnable runnable
    ) {
        //要检查的列表
        final List<String> permissions = new ArrayList<>();
        if (camera) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (audio) {
            permissions.add(Manifest.permission.RECORD_AUDIO);
        }
        if (permissions.isEmpty() || PLVFastPermission.hasPermission(this, permissions)) {
            runnable.run();
            return;
        }
        //获取权限弹框
        PLVFastPermission.getInstance().start(this, permissions, new PLVOnPermissionCallback() {
            @Override
            public void onAllGranted() {
                runnable.run();
            }

            @Override
            public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
                final boolean hasCameraPermission = PLVFastPermission.hasPermission(StreamTest.this, Manifest.permission.CAMERA);
                final boolean hasAudioPermission = PLVFastPermission.hasPermission(StreamTest.this, Manifest.permission.RECORD_AUDIO);
                if (hasCameraPermission && hasAudioPermission) {
                    runnable.run();
                    return;
                }
                //ok
                final String notGrantedPermissionDescription;
                if (hasCameraPermission) {
                    notGrantedPermissionDescription = "麦克风";
                } else if (hasAudioPermission) {
                    notGrantedPermissionDescription = "摄像头";
                } else {
                    notGrantedPermissionDescription = "摄像头和麦克风";
                }

            }
        });
    }

    PLVSceneLoginManager loginManager;
    private void loginStreamer() {

        final String channelId ="3513971";
        final String nick ="i am robot ";
        final String password ="scut1234";
        if (loginManager == null) {
            loginManager = new PLVSceneLoginManager();
        }
        //登录方法
        loginManager.loginStreamerNew(channelId, password, new IPLVSceneLoginManager.OnStringCodeLoginListener<PLVLoginVO>() {
            @Override
            public void onLoginSuccess(PLVLoginVO loginVO) {
                //返回一个Login的数据

                //更新开播状态
                PLVLiveChannelConfigFiller.setLiveStreamingWhenLogin(loginVO.isLiveStatus());

                //不填写登录昵称时，使用登录接口返回的后台设置的昵称
                String loginNick = TextUtils.isEmpty(nick) ? loginVO.getTeacherNickname() : nick;

                //根据后台设置获取方法 是否是的哦？
                PLVLiveChannelType liveChannelType = loginVO.getLiveChannelTypeNew();
                if (PLVLiveChannelType.PPT.equals(liveChannelType)) {
                    //进入手机开播三分屏场景
                    final boolean isOpenCamera = "N".equals(loginVO.getIsOnlyAudio());
                    //检查权限啦
                    requireStreamerPermissionThenRun(
                            isOpenCamera,
                            true,
                            new Runnable() {
                                //新的线程
                                @Override
                                public void run() {
                                    mlaunchStreamer(
                                            StreamTest.this,
                                            loginVO.getChannelId(),
                                            loginVO.getInteractUid(),
                                            loginNick,
                                            loginVO.getTeacherAvatar(),
                                            loginVO.getTeacherActor(),
                                            loginVO.getRole(),
                                            loginVO.getColinMicType(),
                                            true,
                                            isOpenCamera,
                                            true
                                    );
                                }
                            }
                    );
                    //todo 什么纯视频
//                } else if (PLVLiveChannelType.ALONE.equals(liveChannelType)) {
//                    //进入手机开播纯视频场景
//                    requireStreamerPermissionThenRun(
//                            true,
//                            true,
//                            new Runnable() {
//                                @Override
//                                public void run() {
//                                    PLVLaunchResult launchResult = PLVSAStreamerAloneActivity.launchStreamer(
//                                            PLVLoginStreamerActivity.this,
//                                            loginVO.getChannelId(),
//                                            loginVO.getInteractUid(),
//                                            loginNick,
//                                            loginVO.getTeacherAvatar(),
//                                            loginVO.getTeacherActor(),
//                                            loginVO.getChannelName(),
//                                            loginVO.getRole(),
//                                            loginVO.getColinMicType()
//                                    );
//                                    if (!launchResult.isSuccess()) {
//                                        onLoginFailed(launchResult.getErrorMessage(), launchResult.getError());
//                                    }
//                                }
//                            }
//                    );
                } else {
                    //String errorMsg = getResources().getString(R.string.plv_scene_login_toast_streamer_no_support_type);
                    String errorMsg = "tmp error ";
                    onLoginFailed(errorMsg, new Throwable(errorMsg));
                }
            }

            @Override
            public void onLoginFailed(String msg, Throwable throwable) {
                onLoginFailed(msg, "", throwable);
            }

            @Override
            public void onLoginFailed(String msg, String code, Throwable throwable) {
                throwable.printStackTrace();

                System.out.println(msg+code+throwable);
            }
        });
    }



}

