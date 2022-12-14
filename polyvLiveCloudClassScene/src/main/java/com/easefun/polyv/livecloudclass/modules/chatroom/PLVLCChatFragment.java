package com.easefun.polyv.livecloudclass.modules.chatroom;

import static com.plv.foundationsdk.utils.PLVAppUtils.postToMainThread;
import static com.plv.foundationsdk.utils.PLVSugarUtil.firstNotNull;
import static com.plv.foundationsdk.utils.PLVSugarUtil.format;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmotionPersonalListAdapter;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCMessageAdapter;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatFunctionListener;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatMoreLayout;
import com.easefun.polyv.livecloudclass.modules.chatroom.utils.PLVChatroomUtils;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCBulletinTextView;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVSpecialTypeTag;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.holder.PLVChatMessageItemType;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectQueue;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVRewardSVGAHelper;
import com.easefun.polyv.livecommon.module.utils.PLVToast;
import com.easefun.polyv.livecommon.module.utils.PLVUriPathHelper;
import com.easefun.polyv.livecommon.ui.widget.PLVImagePreviewPopupWindow;
import com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.window.PLVInputFragment;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendChatImageHelper;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.easefun.polyv.livescenes.model.PLVEmotionImageVO;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.utils.PLVSDCardUtils;
import com.plv.livescenes.model.interact.PLVChatFunctionVO;
import com.plv.livescenes.model.interact.PLVWebviewUpdateAppStatusVO;
import com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener;
import com.plv.livescenes.playback.chat.IPLVChatPlaybackManager;
import com.plv.livescenes.playback.chat.PLVChatPlaybackCallDataExListener;
import com.plv.livescenes.playback.chat.PLVChatPlaybackData;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import com.plv.socket.event.chat.PLVCloseRoomEvent;
import com.plv.socket.event.chat.PLVFocusModeEvent;
import com.plv.socket.event.chat.PLVLikesEvent;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.socket.event.chat.PLVSpeakEvent;
import com.plv.socket.event.interact.PLVNewsPushStartEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.StringUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ????????????tab???
 */
public class PLVLCChatFragment extends PLVInputFragment implements View.OnClickListener {
    // <editor-fold defaultstate="collapsed" desc="??????">
    private String TAG = getClass().getSimpleName();

    private static final int REQUEST_SELECT_IMG = 0x01;//????????????????????????
    private static final int REQUEST_OPEN_CAMERA = 0x02;//????????????????????????
    //??????????????????
    private PLVLCChatCommonMessageList chatCommonMessageList;
    //??????????????????view
    private TextView unreadMsgTv;

    //???????????????
    private EditText inputEt;
    //?????????????????????????????????
    private Editable recordInputMessage;

    //??????????????????
    private ImageView toggleEmojiIv;
    //??????????????????
    private ImageView toggleMoreIv;

    //??????????????????????????????
    private SwipeRefreshLayout swipeLoadView;

    private PLVLCChatMoreLayout chatMoreLayout;
    //????????????????????????
    private boolean isSelectOnlyTeacher;

    //????????????
    private ViewGroup emojiLy;
    private TextView sendMsgTv;
    private ImageView deleteMsgIv;
    //emoji????????????
    private RecyclerView emojiRv;
    //emoji??????tab
    private ImageView tabEmojiIv;
    private RecyclerView emojiPersonalRv;
    //???????????????tab
    private ImageView tabPersonalIv;
    //???????????????????????????
    private PLVImagePreviewPopupWindow emotionPreviewWindow;

    //????????????
    private ViewGroup likesLy;
    private PLVLCLikeIconView likesView;
    private TextView likesCountTv;
    private long likesCount;

    //????????????
    @Nullable
    private ImageView rewardIv;
    //????????????????????????
    private IPLVPointRewardEventProducer pointRewardEventProducer;
    //??????????????????item
    private PLVPointRewardEffectWidget polyvPointRewardEffectWidget;
    //????????????svg??????
    private SVGAImageView rewardSvgImage;
    private SVGAParser svgaParser;
    private PLVRewardSVGAHelper svgaHelper;
    //????????????????????????
    private boolean isSelectCloseEffect = false;

    //????????????
    private ImageView cardEnterView;
    private TextView cardEnterCdTv;
    private PLVTriangleIndicateTextView cardEnterTipsView;
    private PLVCardPushManager cardPushManager;

    //?????????
    private PLVLCGreetingTextView greetingTv;
    private boolean isShowGreeting;//?????????????????????

    //??????(???????????????)
    private PLVLCBulletinTextView bulletinTv;

    //?????????presenter
    private IPLVChatroomContract.IChatroomPresenter chatroomPresenter;

    //???????????????????????????
    private File takePictureFilePath;
    private Uri takePictureUri;

    //?????????????????????
    private boolean isLiveType;
    //??????????????????????????????
    private boolean isOpenPointReward = false;

    //?????????????????????
    private IPLVChatPlaybackManager chatPlaybackManager;
    private Runnable playbackTipsRunnable;

    //??????????????????
    private List<PolyvChatFunctionSwitchVO.DataBean> functionSwitchData;
    //??????????????????
    private List<PLVEmotionImageVO.EmotionImage> emotionImages;

    //????????????tipsView
    private TextView chatPlaybackTipsTv;
    //????????????????????????(????????????????????????????????????????????????????????????????????????????????????????????????)
    private boolean isChatPlaybackLayout;

    //?????????????????????
    private boolean isCloseRoomStatus;
    //??????????????????
    private boolean isFocusModeStatus;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.plvlc_chatroom_chat_portrait_fragment, null);
        initView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMG && resultCode == Activity.RESULT_OK) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                String picturePath = PLVUriPathHelper.getPrivatePath(getContext(), selectedUri);
                sendImg(picturePath);
            } else {
                ToastUtils.showShort("cannot retrieve selected image");
            }
        } else if (requestCode == REQUEST_OPEN_CAMERA && resultCode == Activity.RESULT_OK) {//data->null
            if (Build.VERSION.SDK_INT >= 29) {
                String picturePath = PLVUriPathHelper.getPrivatePath(getContext(), takePictureUri);
                sendImg(picturePath);
            } else {
                sendImg(takePictureFilePath.getAbsolutePath());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isOpenPointReward) {
            destroyPointRewardEffectQueue();
        }
        if (chatPlaybackTipsTv != null) {
            chatPlaybackTipsTv.removeCallbacks(playbackTipsRunnable);
        }
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="???????????????">
    public void init(PLVLCChatCommonMessageList chatCommonMessageList) {
        this.chatCommonMessageList = chatCommonMessageList;
    }

    public void setIsChatPlaybackLayout(boolean isChatPlaybackLayout) {
        this.isChatPlaybackLayout = isChatPlaybackLayout;
    }

    public void setCardPushManager(PLVCardPushManager cardPushManager) {
        this.cardPushManager = cardPushManager;
    }

    //????????????????????????????????????????????????????????????????????????(??????????????????)??????
    public void setIsLiveType(boolean isLiveType) {
        this.isLiveType = isLiveType;
    }

    /**
     * ????????????????????????????????????
     */
    public void setOpenPointReward(boolean open) {
        isOpenPointReward = open;
        if (rewardIv != null) {
            rewardIv.setVisibility(open ? View.VISIBLE : View.GONE);
        }
        updateRewardEffectBtnVisibility(isOpenPointReward);
        initPointRewardEffectQueue();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????view">
    private void initView() {
        //????????????view
        unreadMsgTv = findViewById(R.id.unread_msg_tv);
        if (chatCommonMessageList != null) {
            chatCommonMessageList.addUnreadView(unreadMsgTv);
            chatCommonMessageList.addOnUnreadCountChangeListener(new PLVMessageRecyclerView.OnUnreadCountChangeListener() {
                @Override
                public void onChange(int currentUnreadCount) {
                    unreadMsgTv.setText("???" + currentUnreadCount + "???????????????????????????");
                }
            });
        }

        //???????????????
        inputEt = findViewById(R.id.input_et);
        inputEt.addTextChangedListener(inputTextWatcher);
        if (isChatPlaybackLayout) {
            inputEt.setHint("?????????????????????");
            inputEt.setEnabled(false);
        }

        //?????????????????????
        toggleEmojiIv = findViewById(R.id.toggle_emoji_iv);
        toggleEmojiIv.setOnClickListener(this);
        toggleMoreIv = findViewById(R.id.toggle_more_iv);
        if (isChatPlaybackLayout) {
            toggleMoreIv.setVisibility(View.GONE);
            toggleEmojiIv.setEnabled(false);
            toggleEmojiIv.setAlpha(0.5f);
        } else {
            toggleMoreIv.setVisibility(View.VISIBLE);
            toggleEmojiIv.setEnabled(true);
            toggleEmojiIv.setAlpha(1f);
        }
        toggleMoreIv.setOnClickListener(this);

        //????????????
        swipeLoadView = findViewById(R.id.swipe_load_view);
        swipeLoadView.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeLoadView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isChatPlaybackLayout) {
                    if (chatPlaybackManager != null) {
                        chatPlaybackManager.loadPrevious();
                    }
                } else if (chatroomPresenter != null) {
                    chatroomPresenter.requestChatHistory(chatroomPresenter.getViewIndex(chatroomView));
                }
            }
        });

        if (chatCommonMessageList != null) {
            //?????????????????????????????????????????????
            boolean result = chatCommonMessageList.attachToParent(swipeLoadView, false);
            if (result) {
                if (chatroomPresenter != null) {
                    //???????????????????????????chatroomPresenter.registerView?????????
                    chatCommonMessageList.setMsgIndex(chatroomPresenter.getViewIndex(chatroomView));
                    if (!isChatPlaybackLayout) {
                        //????????????????????????????????????
                        if (chatroomPresenter.getChatHistoryTime() == 0) {
                            chatroomPresenter.requestChatHistory(chatroomPresenter.getViewIndex(chatroomView));//????????????????????????
                        }
                    }
                }
            }
        }

        initChatMoreLayout();

        //????????????
        emojiLy = findViewById(R.id.emoji_ly);
        sendMsgTv = findViewById(R.id.send_msg_tv);
        sendMsgTv.setOnClickListener(this);
        deleteMsgIv = findViewById(R.id.delete_msg_iv);
        deleteMsgIv.setOnClickListener(this);
        //emoji????????????
        emojiRv = findViewById(R.id.emoji_rv);
        PLVChatroomUtils.initEmojiList(emojiRv, inputEt);
        //??????????????????
        emojiPersonalRv = findViewById(R.id.emoji_personal_rv);
        //??????tab
        tabEmojiIv = findViewById(R.id.plvlc_emoji_tab_emoji_iv);
        tabEmojiIv.setSelected(true);
        tabEmojiIv.setOnClickListener(this);
        tabPersonalIv = findViewById(R.id.plvlc_emoji_tab_personal_iv);
        tabPersonalIv.setVisibility(View.VISIBLE);
        tabPersonalIv.setOnClickListener(this);
        //??????????????????
        emotionPreviewWindow = new PLVImagePreviewPopupWindow(getContext());

        //????????????
        likesLy = findViewById(R.id.likes_ly);
        likesView = findViewById(R.id.likes_view);
        likesView.setOnButtonClickListener(this);
        likesCountTv = findViewById(R.id.likes_count_tv);
        if (likesCount != 0) {
            String likesString = StringUtils.toWString(likesCount);
            likesCountTv.setText(likesString);
        }

        //??????
        rewardIv = findViewById(R.id.plvlc_iv_show_point_reward);
        rewardIv.setOnClickListener(this);
        rewardIv.setVisibility(isOpenPointReward ? View.VISIBLE : View.GONE);
        //??????svga??????
        rewardSvgImage = findViewById(R.id.plvlc_reward_svg);
        svgaParser = new SVGAParser(getContext());
        svgaHelper = new PLVRewardSVGAHelper();
        svgaHelper.init(rewardSvgImage, svgaParser);

        //????????????????????????
        polyvPointRewardEffectWidget = findViewById(R.id.plvlc_point_reward_effect);
        polyvPointRewardEffectWidget.setEventProducer(pointRewardEventProducer);

        //?????????
        greetingTv = findViewById(R.id.greeting_tv);

        //??????(???????????????)
        bulletinTv = findViewById(R.id.bulletin_tv);

        //????????????tipsView
        chatPlaybackTipsTv = findViewById(R.id.plvlc_chat_playback_tips_tv);
        if (isChatPlaybackLayout) {
            chatPlaybackTipsTv.setVisibility(View.VISIBLE);
            chatPlaybackTipsTv.postDelayed(playbackTipsRunnable = new Runnable() {
                @Override
                public void run() {
                    chatPlaybackTipsTv.setVisibility(View.GONE);
                }
            }, 5000);
        }

        //????????????
        cardEnterView = findViewById(R.id.card_enter_view);
        cardEnterCdTv = findViewById(R.id.card_enter_cd_tv);
        cardEnterTipsView = findViewById(R.id.card_enter_tips_view);
        if (cardPushManager != null) {
            cardPushManager.registerView(cardEnterView, cardEnterCdTv, cardEnterTipsView);
        }

        addPopupButton(toggleEmojiIv);
        addPopupLayout(emojiLy);

        addPopupButton(toggleMoreIv);
        addPopupLayout(chatMoreLayout);

        acceptFunctionSwitchData(functionSwitchData);
        acceptEmotionImageData(emotionImages);
    }

    private void initChatMoreLayout() {
        chatMoreLayout = findViewById(R.id.plvlc_chat_more_layout);
        if (!isLiveType) {
            chatMoreLayout.updateFunctionShow(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_BULLETIN, false);
        }
        chatMoreLayout.setFunctionListener(new PLVLCChatFunctionListener() {
            @Override
            public void onFunctionCallback(String type, String data) {

                switch (type) {
                    case PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_ONLY_TEACHER:
                        if (isFocusModeStatus) {
                            ToastUtils.showShort("???????????????????????????");
                            return;
                        }
                        isSelectOnlyTeacher = !isSelectOnlyTeacher;
                        PLVChatFunctionVO onlyTeacherFunction = chatMoreLayout.getFunctionByType(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_ONLY_TEACHER);
                        if (onlyTeacherFunction != null) {
                            onlyTeacherFunction.setSelected(isSelectOnlyTeacher);
                            onlyTeacherFunction.setName(isSelectOnlyTeacher ? getString(R.string.plv_chat_view_all_message) : getString(R.string.plv_chat_view_special_message));
                            chatMoreLayout.updateFunctionStatus(onlyTeacherFunction);
                        }
                        if (chatCommonMessageList != null) {
                            chatCommonMessageList.changeDisplayType(isSelectOnlyTeacher ? PLVLCMessageAdapter.DISPLAY_DATA_TYPE_SPECIAL : PLVLCMessageAdapter.DISPLAY_DATA_TYPE_FULL);
                        }
                        break;
                    case PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_SEND_IMAGE:
                        requestSelectImg();
                        break;
                    case PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_OPEN_CAMERA:
                        requestOpenCamera();
                        break;
                    case PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_BULLETIN:
                        hideSoftInputAndPopupLayout();
                        if (onViewActionListener != null) {
                            onViewActionListener.onShowBulletinAction();
                        }
                        break;
                    default:
                        hideSoftInputAndPopupLayout();
                        if (onViewActionListener != null) {
                            onViewActionListener.onClickDynamicFunction(data);
                        }
                        break;
                    case PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_EFFECT:
                        hideSoftInputAndPopupLayout();
                        isSelectCloseEffect = !isSelectCloseEffect;
                        PLVChatFunctionVO effectFunction = chatMoreLayout.getFunctionByType(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_EFFECT);
                        if (effectFunction != null) {
                            effectFunction.setSelected(isSelectCloseEffect);
                            effectFunction.setName(isSelectCloseEffect ? getString(R.string.plv_chat_view_show_effect) : getString(R.string.plv_chat_view_close_effect));
                            chatMoreLayout.updateFunctionStatus(effectFunction);
                        }
                        if (isSelectCloseEffect) {
                            polyvPointRewardEffectWidget.hideAndReleaseEffect();
                            svgaHelper.clear();
                            rewardSvgImage.setVisibility(View.INVISIBLE);
                        } else {
                            polyvPointRewardEffectWidget.showAndPrepareEffect();
                            rewardSvgImage.setVisibility(View.VISIBLE);
                        }
                        if (onViewActionListener != null) {
                            onViewActionListener.onShowEffectAction(!isSelectCloseEffect);
                        }
                        break;
                }
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????API - ??????PLVInputFragment???????????????">
    @Override
    public int inputLayoutId() {
        return R.id.bottom_input_ly;
    }

    @Override
    public int inputViewId() {
        return R.id.input_et;
    }

    @Override
    public boolean onSendMsg(String message) {
        return sendChatMessage(message);
    }

    @Override
    public int attachContainerViewId() {
        return R.id.plvlc_chatroom_input_layout_container;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    private void acceptNewsPushStartMessage(final PLVNewsPushStartEvent newsPushStartEvent) {
        if (cardPushManager != null) {
            cardPushManager.acceptNewsPushStartMessage(chatroomPresenter, newsPushStartEvent);
        }
    }

    private void acceptNewsPushCancelMessage() {
        if (cardPushManager != null) {
            cardPushManager.acceptNewsPushCancelMessage();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    private IPLVChatPlaybackCallDataListener chatPlaybackDataListener = new PLVChatPlaybackCallDataExListener() {

        @Override
        public void onLoadPreviousEnabled(boolean enabled, boolean isByClearData) {
            if (swipeLoadView != null) {
                swipeLoadView.setEnabled(enabled);
            }
            if (!enabled && !isByClearData) {
                ToastUtils.showShort(R.string.plv_chat_toast_history_all_loaded);
            }
        }

        @Override
        public void onHasNotAddedData() {
            if (unreadMsgTv != null) {
                if (unreadMsgTv.getVisibility() != View.VISIBLE) {
                    unreadMsgTv.setText("???????????????????????????");
                    unreadMsgTv.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onLoadPreviousFinish() {
            if (swipeLoadView != null) {
                swipeLoadView.setRefreshing(false);
            }
        }

        @Override
        public void onDataInserted(int startPosition, int count, List<PLVChatPlaybackData> insertDataList, boolean inHead, int time) {
            List<PLVBaseViewData> dataList = new ArrayList<>();
            for (PLVChatPlaybackData chatPlaybackData : insertDataList) {
                boolean isSpecialTypeOrMe = PLVEventHelper.isSpecialType(chatPlaybackData.getUserType())
                        || PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(chatPlaybackData.getUserId());
                int itemType = chatPlaybackData.isImgMsg() ? PLVChatMessageItemType.ITEMTYPE_RECEIVE_IMG : PLVChatMessageItemType.ITEMTYPE_RECEIVE_SPEAK;
                // ?????????userType???????????????????????????
                dataList.add(new PLVBaseViewData<>(chatPlaybackData, itemType, isSpecialTypeOrMe ? new PLVSpecialTypeTag(chatPlaybackData.getUserId()) : null));
            }
            if (inHead) {
                addChatMessageToListHead(dataList);
            } else {
                boolean isScrollEnd = chatCommonMessageList != null && chatCommonMessageList.getItemCount() == 0;
                addChatMessageToList(dataList, isScrollEnd);
            }
        }

        @Override
        public void onDataRemoved(int startPosition, int count, List<PLVChatPlaybackData> removeDataList, boolean inHead) {
            removeChatMessageToList(startPosition, count);
        }

        @Override
        public void onDataCleared() {
            removeChatMessageToList(null, true);
        }

        @Override
        public void onData(List<PLVChatPlaybackData> dataList) {
        }

        @Override
        public void onManager(IPLVChatPlaybackManager manager) {
            chatPlaybackManager = manager;
        }
    };

    public IPLVChatPlaybackCallDataListener getChatPlaybackDataListener() {
        return chatPlaybackDataListener;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    private void acceptFocusModeEvent(final PLVFocusModeEvent focusModeEvent) {
        isFocusModeStatus = focusModeEvent.isOpen();
        postToMainThread(new Runnable() {
            @Override
            public void run() {
                updateViewByRoomStatusChanged(isFocusModeStatus);
                if (chatCommonMessageList != null) {
                    if (isFocusModeStatus) {
                        chatCommonMessageList.changeDisplayType(PLVLCMessageAdapter.DISPLAY_DATA_TYPE_FOCUS_MODE);
                    } else {
                        chatCommonMessageList.changeDisplayType(isSelectOnlyTeacher ? PLVLCMessageAdapter.DISPLAY_DATA_TYPE_SPECIAL : PLVLCMessageAdapter.DISPLAY_DATA_TYPE_FULL);
                    }
                }
                if (chatCommonMessageList != null && chatCommonMessageList.isLandscapeLayout()) {
                    return;
                }
                ToastUtils.showLong(isFocusModeStatus ? R.string.plv_chat_toast_focus_mode_open : R.string.plv_chat_toast_focus_mode_close);
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    private void acceptCloseRoomEvent(PLVCloseRoomEvent closeRoomEvent) {
        isCloseRoomStatus = closeRoomEvent.getValue().isClosed();
        postToMainThread(new Runnable() {
            @Override
            public void run() {
                updateViewByRoomStatusChanged(isCloseRoomStatus);
                if (chatCommonMessageList != null && chatCommonMessageList.isLandscapeLayout()) {
                    return;
                }
                ToastUtils.showLong(isCloseRoomStatus ? R.string.plv_chat_toast_chatroom_close : R.string.plv_chat_toast_chatroom_open);
            }
        });
    }

    private void updateViewByRoomStatusChanged(boolean isDisabled) {
        boolean isEnabled = !isCloseRoomStatus && !isFocusModeStatus;
        if (isDisabled) {
            hideSoftInputAndPopupLayout();
            if (recordInputMessage == null) {
                recordInputMessage = inputEt.getText();
            }
            inputEt.setText("");
        } else {
            if (recordInputMessage != null && isEnabled) {
                inputEt.setText(recordInputMessage);
                recordInputMessage = null;
            }
        }
        inputEt.setHint(isCloseRoomStatus ? "??????????????????" : (isFocusModeStatus ? "????????????????????????????????????" : "??????????????????"));
        inputEt.setEnabled(isEnabled);
        toggleEmojiIv.setEnabled(isEnabled);
        toggleEmojiIv.setAlpha(isEnabled ? 1f : 0.5f);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - MVP?????????view?????????">
    private IPLVChatroomContract.IChatroomView chatroomView = new PLVAbsChatroomView() {
        @Override
        public void setPresenter(@NonNull IPLVChatroomContract.IChatroomPresenter presenter) {
            super.setPresenter(presenter);
            chatroomPresenter = presenter;
        }

        @Override
        public void onSpeakEvent(@NonNull PLVSpeakEvent speakEvent) {
            super.onSpeakEvent(speakEvent);
            if (isChatPlaybackLayout) {
                return;
            }
            acceptSpeakEvent(speakEvent);
        }

        @Override
        public int getSpeakEmojiSize() {
            return ConvertUtils.dp2px(16);
        }

        @Override
        public void onLikesEvent(@NonNull PLVLikesEvent likesEvent) {
            super.onLikesEvent(likesEvent);
            acceptLikesMessage(likesEvent.getCount());
        }

        @Override
        public void onLoginEvent(@NonNull PLVLoginEvent loginEvent) {
            super.onLoginEvent(loginEvent);
            acceptLoginEvent(loginEvent);
        }

        @Override
        public void onLoginError(@Nullable PLVLoginEvent loginEvent, final String msg, final int errorCode) {
            super.onLoginError(loginEvent, msg, errorCode);
            postToMainThread(new Runnable() {
                @Override
                public void run() {
                    PLVToast.Builder.create()
                            .setText(format("{}({})", msg, errorCode))
                            .show();
                    final Activity activity = firstNotNull(getActivity(), ActivityUtils.getTopActivity());
                    if (activity != null) {
                        activity.finish();
                    }
                }
            });
        }

        @Override
        public void onRewardEvent(@NonNull PLVRewardEvent rewardEvent) {
            super.onRewardEvent(rewardEvent);
            acceptPointRewardMessage(rewardEvent);
        }

        @Override
        public void onCloseRoomEvent(@NonNull final PLVCloseRoomEvent closeRoomEvent) {
            super.onCloseRoomEvent(closeRoomEvent);
            if (isChatPlaybackLayout) {
                return;
            }
            acceptCloseRoomEvent(closeRoomEvent);
        }

        @Override
        public void onFocusModeEvent(@NonNull PLVFocusModeEvent focusModeEvent) {
            super.onFocusModeEvent(focusModeEvent);
            if (isChatPlaybackLayout) {
                return;
            }
            acceptFocusModeEvent(focusModeEvent);
        }

        @Override
        public void onRemoveMessageEvent(@Nullable String id, boolean isRemoveAll) {
            super.onRemoveMessageEvent(id, isRemoveAll);
            if (isChatPlaybackLayout) {
                return;
            }
            removeChatMessageToList(id, isRemoveAll);
        }

        @Override
        public void onLocalSpeakMessage(@Nullable PolyvLocalMessage localMessage) {
            super.onLocalSpeakMessage(localMessage);
            if (isChatPlaybackLayout) {
                return;
            }
            if (localMessage == null) {
                return;
            }
            final List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData<>(localMessage, PLVChatMessageItemType.ITEMTYPE_SEND_SPEAK, new PLVSpecialTypeTag(localMessage.getUserId())));
            if (!isShowKeyBoard(new OnceHideKeyBoardListener() {
                @Override
                public void call() {
                    //?????????????????????
                    addChatMessageToList(dataList, true);//?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                }
            })) {
                //?????????????????????
                addChatMessageToList(dataList, true);
            }
        }

        @Override
        public void onLoadEmotionMessage(@Nullable PLVChatEmotionEvent emotionEvent) {
            super.onLoadEmotionMessage(emotionEvent);
            if (isChatPlaybackLayout) {
                return;
            }
            if (emotionEvent == null) {
                return;
            }
            final List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData(emotionEvent, PLVChatMessageItemType.ITEMTYPE_EMOTION, emotionEvent.isSpecialTypeOrMe() ? new PLVSpecialTypeTag(emotionEvent.getUserId()) : null));
                //?????????????????????
            addChatMessageToList(dataList, emotionEvent.isLocal());
        }

        @Override
        public void onNewsPushStartMessage(@NonNull PLVNewsPushStartEvent newsPushStartEvent) {
            super.onNewsPushStartMessage(newsPushStartEvent);
            acceptNewsPushStartMessage(newsPushStartEvent);
        }

        @Override
        public void onNewsPushCancelMessage() {
            super.onNewsPushCancelMessage();
            acceptNewsPushCancelMessage();
        }

        @Override
        public void onLocalImageMessage(@Nullable PolyvSendLocalImgEvent localImgEvent) {
            super.onLocalImageMessage(localImgEvent);
            if (isChatPlaybackLayout) {
                return;
            }
            List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData<>(localImgEvent, PLVChatMessageItemType.ITEMTYPE_SEND_IMG, new PLVSpecialTypeTag(localImgEvent.getUserId())));
            //?????????????????????
            addChatMessageToList(dataList, true);
        }

        @Override
        public void onSpeakImgDataList(List<PLVBaseViewData> chatMessageDataList) {
            super.onSpeakImgDataList(chatMessageDataList);
            if (isChatPlaybackLayout) {
                return;
            }
            //?????????????????????
            addChatMessageToList(chatMessageDataList, false);
        }

        @Override
        public void onHistoryDataList(List<PLVBaseViewData<PLVBaseEvent>> chatMessageDataList, int requestSuccessTime, boolean isNoMoreHistory, int viewIndex) {
            super.onHistoryDataList(chatMessageDataList, requestSuccessTime, isNoMoreHistory, viewIndex);
            if (isChatPlaybackLayout) {
                return;
            }
            if (swipeLoadView != null) {
                swipeLoadView.setRefreshing(false);
                swipeLoadView.setEnabled(true);
            }
            if (!chatMessageDataList.isEmpty()) {
                addChatHistoryToList(chatMessageDataList, requestSuccessTime == 1);
            }
            if (isNoMoreHistory) {
                ToastUtils.showShort(R.string.plv_chat_toast_history_all_loaded);
                if (swipeLoadView != null) {
                    swipeLoadView.setEnabled(false);
                }
            }
        }

        @Override
        public void onHistoryRequestFailed(String errorMsg, Throwable t, int viewIndex) {
            super.onHistoryRequestFailed(errorMsg, t, viewIndex);
            if (isChatPlaybackLayout) {
                return;
            }
            if (swipeLoadView != null) {
                swipeLoadView.setRefreshing(false);
                swipeLoadView.setEnabled(true);
            }
            if (chatroomPresenter != null && viewIndex == chatroomPresenter.getViewIndex(chatroomView)) {
                ToastUtils.showShort(getString(R.string.plv_chat_toast_history_load_failed) + ": " + errorMsg);
            }
        }
    };

    public IPLVChatroomContract.IChatroomView getChatroomView() {
        return chatroomView;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????">
    private void acceptLoginEvent(final PLVLoginEvent loginEvent) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //???????????????
                if (greetingTv != null && isShowGreeting) {
                    greetingTv.acceptLoginEvent(loginEvent);
                }
            }
        });
    }

    private void acceptSpeakEvent(PLVSpeakEvent speakEvent) {
        //??????????????????????????????
        if (PLVSocketUserConstant.USERTYPE_MANAGER.equals(speakEvent.getUser().getUserType())) {
            //?????????????????????(???????????????)
            if (bulletinTv != null) {
                bulletinTv.startMarquee((CharSequence) speakEvent.getObjects()[0]);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????">

    /**
     * ?????????????????????????????????item
     */
    private void initPointRewardEffectQueue() {
        if (pointRewardEventProducer == null) {
            pointRewardEventProducer = new PLVPointRewardEffectQueue();
            if (polyvPointRewardEffectWidget != null) {
                polyvPointRewardEffectWidget.setEventProducer(pointRewardEventProducer);
            }
        }
    }

    /**
     * ??????????????????????????????
     */
    private void destroyPointRewardEffectQueue() {
        if (pointRewardEventProducer != null) {
            pointRewardEventProducer.destroy();
        }
    }

    private void acceptPointRewardMessage(final PLVRewardEvent rewardEvent) {
        if (pointRewardEventProducer != null) {
            //?????? ??? ????????????  ???????????????????????????
            if (ScreenUtils.isPortrait() && !isSelectCloseEffect) {
                //?????????????????????????????????????????????
//                pointRewardEventProducer.addEvent(rewardEvent);
                //?????????svga
//                svgaHelper.addEvent(rewardEvent);
            }
        }
    }
    // </editor-fold >

    // <editor-fold defaultstate="collapsed" desc="????????? - ??????????????????">
    private void addChatMessageToList(final List<PLVBaseViewData> chatMessageDataList, final boolean isScrollEnd) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (chatCommonMessageList != null) {
                    chatCommonMessageList.addChatMessageToList(chatMessageDataList, isScrollEnd, false);
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }

    private void addChatHistoryToList(final List<PLVBaseViewData<PLVBaseEvent>> chatMessageDataList, final boolean isScrollEnd) {
        if (chatCommonMessageList != null) {
            chatCommonMessageList.addChatHistoryToList(chatMessageDataList, isScrollEnd, false);
        }
    }

    private void addChatMessageToListHead(final List<PLVBaseViewData> chatMessageDataList) {
        if (chatCommonMessageList != null) {
            chatCommonMessageList.addChatMessageToListHead(chatMessageDataList, false, false);
        }
    }

    private void removeChatMessageToList(int startPosition, int count) {
        if (chatCommonMessageList != null) {
            chatCommonMessageList.removeChatMessage(startPosition, count, false);
            if (!chatCommonMessageList.canScrollVertically(1)) {
                chatCommonMessageList.scrollToPosition(chatCommonMessageList.getItemCount() - 1);
            }
        }
    }

    private void removeChatMessageToList(final String id, final boolean isRemoveAll) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (chatCommonMessageList == null) {
                    return;
                }
                if (isRemoveAll) {
                    chatCommonMessageList.removeAllChatMessage(false);
                } else {
                    chatCommonMessageList.removeChatMessage(id, false);
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ??????????????????">
    private boolean sendChatMessage(String message) {
        if (message.trim().length() == 0) {
            ToastUtils.showLong(R.string.plv_chat_toast_send_text_empty);
            return false;
        } else {
            PolyvLocalMessage localMessage = new PolyvLocalMessage(message);
            if (isChatPlaybackLayout) {
                return false;
            }
            if (chatroomPresenter == null) {
                return false;
            }
            Pair<Boolean, Integer> sendResult = chatroomPresenter.sendChatMessage(localMessage);
            if (sendResult.first) {
                //????????????????????????????????????/????????????????????????
                inputEt.setText("");
                hideSoftInputAndPopupLayout();
                return true;
            } else {
                //????????????
                ToastUtils.showShort(getString(R.string.plv_chat_toast_send_msg_failed) + ": " + sendResult.second);
                return false;
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????????????????">
    private boolean checkCanSendImg() {
        if (isCloseRoomStatus) {
            ToastUtils.showShort("???????????????????????????????????????");
            return false;
        } else if (isFocusModeStatus) {
            ToastUtils.showShort("???????????????????????????");
            return false;
        }
        return true;
    }

    private void requestSelectImg() {
        if (!checkCanSendImg()) {
            return;
        }
        ArrayList<String> permissions = new ArrayList<>(1);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PLVFastPermission.getInstance()
                .start((Activity) getContext(), permissions, new PLVOnPermissionCallback() {
                    @Override
                    public void onAllGranted() {
                        selectImg();
                    }

                    @Override
                    public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
                        if (!deniedForeverP.isEmpty()) {
                            showRequestPermissionDialog("???????????????????????????????????????????????????????????????????????????????????????");
                        } else {
                            ToastUtils.showShort("???????????????????????????????????????");
                        }
                    }
                });
    }

    private void selectImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "????????????"), REQUEST_SELECT_IMG);
    }

    private void requestOpenCamera() {
        if (!checkCanSendImg()) {
            return;
        }
        ArrayList<String> permissions = new ArrayList<>(2);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
        PLVFastPermission.getInstance()
                .start((Activity) getContext(), permissions, new PLVOnPermissionCallback() {
                    @Override
                    public void onAllGranted() {
                        openCamera();
                    }

                    @Override
                    public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
                        if (!deniedForeverP.isEmpty()) {
                            showRequestPermissionDialog("??????????????????????????????????????????????????????????????????????????????????????????");
                        } else {
                            ToastUtils.showShort("??????????????????????????????????????????");
                        }
                    }
                });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String picName = System.currentTimeMillis() + ".jpg";//???????????????
        if (Build.VERSION.SDK_INT >= 29) {
            Environment.getExternalStorageState();
            // ???????????????SD???,????????????SD?????????,?????????SD????????????????????????
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, picName);
            takePictureUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            if (getContext() == null || getContext().getApplicationContext() == null) {
                return;
            }
            String savePath = PLVSDCardUtils.createPath(getContext(), "PLVChatImg");
            takePictureFilePath = new File(savePath, picName);
            takePictureUri = FileProvider.getUriForFile(
                    getContext(),
                    getContext().getApplicationContext().getPackageName() + ".plvfileprovider",
                    takePictureFilePath);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, takePictureUri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_OPEN_CAMERA);
    }

    private void showRequestPermissionDialog(String message) {
        new AlertDialog.Builder(getContext()).setTitle("??????")
                .setMessage(message)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PLVFastPermission.getInstance().jump2Settings(getContext());
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setCancelable(false).show();
    }

    private void sendImg(String picturePath) {
        PolyvSendLocalImgEvent sendLocalImgEvent = new PolyvSendLocalImgEvent();
        sendLocalImgEvent.setImageFilePath(picturePath);
        int[] pictureWh = PolyvSendChatImageHelper.getPictureWh(picturePath);
        sendLocalImgEvent.setWidth(pictureWh[0]);
        sendLocalImgEvent.setHeight(pictureWh[1]);

        if (chatroomPresenter != null) {
            chatroomPresenter.sendChatImage(sendLocalImgEvent);
        }
        hideSoftInputAndPopupLayout();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ???????????????????????????">
    public boolean isDisplaySpecialType() {
        return isSelectOnlyTeacher;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ?????? - ??????????????????">

    /**
     * ????????????????????????
     */
    public void updateInteractStatus(boolean isShow, boolean hasNew) {
        chatMoreLayout.updateFunctionNew(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_MESSAGE, isShow, hasNew);
    }

    public void updateChatMoreFunction(PLVWebviewUpdateAppStatusVO functionsVO) {
        if (chatMoreLayout != null) {
            chatMoreLayout.updateFunctionView(functionsVO);
        }
    }


    /**
     * ??????????????????
     */
    public void updateRewardEffectBtnVisibility(boolean isShow) {
//        chatMoreLayout.updateFunctionShow(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_EFFECT, isShow);
    }
    // </editor-fold >

    // <editor-fold defaultstate="collapsed" desc="????????? - ??????????????????">
    public void acceptFunctionSwitchData(List<PolyvChatFunctionSwitchVO.DataBean> dataBeans) {
        this.functionSwitchData = dataBeans;
        if (view != null && dataBeans != null) {
            for (PolyvChatFunctionSwitchVO.DataBean dataBean : dataBeans) {
                boolean isSwitchEnabled = dataBean.isEnabled();
                switch (dataBean.getType()) {
                    //????????????????????????
                    case PolyvChatFunctionSwitchVO.TYPE_VIEWER_SEND_IMG_ENABLED:
                        chatMoreLayout.updateFunctionShow(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_SEND_IMAGE, isSwitchEnabled);
                        chatMoreLayout.updateFunctionShow(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_OPEN_CAMERA, isSwitchEnabled);
                        break;
                    //???????????????
                    case PolyvChatFunctionSwitchVO.TYPE_WELCOME:
                        isShowGreeting = isSwitchEnabled;
                        break;
                    //??????/????????????
                    case PolyvChatFunctionSwitchVO.TYPE_SEND_FLOWERS_ENABLED:
                        likesLy.setVisibility(isSwitchEnabled ? View.VISIBLE : View.GONE);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ??????????????????">
    public void acceptEmotionImageData(List<PLVEmotionImageVO.EmotionImage> emotionImages) {
        this.emotionImages = emotionImages;
        //?????????????????????????????????
        if (view != null && emotionImages != null && !emotionImages.isEmpty()) {
            PLVChatroomUtils.initEmojiPersonalList(emojiPersonalRv, 5, emotionImages, new PLVLCEmotionPersonalListAdapter.OnViewActionListener() {
                @Override
                public void onEmotionViewClick(PLVEmotionImageVO.EmotionImage emotionImage) {
                    if (isChatPlaybackLayout) {
                        return;
                    }
                    if (chatroomPresenter != null) {
                        Pair<Boolean, Integer> sendResult = chatroomPresenter.sendChatEmotionImage(new PLVChatEmotionEvent(emotionImage.getId()));

                        if (!sendResult.first) {
                            //????????????
                            ToastUtils.showShort(getString(R.string.plv_chat_toast_send_msg_failed) + ": " + sendResult.second);
                        } else {
                            //????????????
                            hideSoftInputAndPopupLayout();
                        }
                    }
                }

                @Override
                public void onEmotionViewLongClick(PLVEmotionImageVO.EmotionImage emotionImage, View view) {
                    emotionPreviewWindow.showInTopCenter(emotionImage.getUrl(), view);
                }
            });
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ?????????????????????">
    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s != null && s.length() > 0) {
                sendMsgTv.setEnabled(true);
                sendMsgTv.setSelected(true);
            } else {
                sendMsgTv.setSelected(false);
                sendMsgTv.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????? - ?????????????????????">
    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
        String likesString = StringUtils.toWString(likesCount);
        if (likesCountTv != null) {
            likesCountTv.setText(likesString);
        }
    }

    private void acceptLikesMessage(final int likesCount) {
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                startAddLoveIconTask(200, Math.min(5, likesCount));
            }
        });
    }

    private void startAddLoveIconTask(final long ts, final int count) {
        if (count >= 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (likesView != null) {
                        likesView.addLoveIcon(1);
                    }
                    startAddLoveIconTask(ts, count - 1);
                }
            }, ts);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????TAB">
    private void changeEmojiTab(boolean isEmoji) {
        tabEmojiIv.setSelected(isEmoji);
        tabPersonalIv.setSelected(isEmoji);
        int selectColor = Color.parseColor("#FF2B2C35");
        int unSelectColor = Color.parseColor("#FF202127");
        tabEmojiIv.setBackgroundColor(isEmoji ? selectColor : unSelectColor);
        tabPersonalIv.setBackgroundColor(isEmoji ? unSelectColor : selectColor);
        //??????rv????????????
        if (isEmoji) {
            //??????emoji?????????
            emojiRv.setVisibility(View.VISIBLE);
            sendMsgTv.setVisibility(View.VISIBLE);
            deleteMsgIv.setVisibility(View.VISIBLE);
            emojiPersonalRv.setVisibility(View.INVISIBLE);
        } else {
            //?????????????????????
            emojiRv.setVisibility(View.INVISIBLE);
            sendMsgTv.setVisibility(View.INVISIBLE);
            deleteMsgIv.setVisibility(View.INVISIBLE);
            emojiPersonalRv.setVisibility(View.VISIBLE);
        }
    }
    // </editor-fold >

    // <editor-fold defaultstate="collapsed" desc="????????????">
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (chatCommonMessageList == null) {
                return;
            }
            boolean result = chatCommonMessageList.attachToParent(swipeLoadView, false);
            if (result && chatroomPresenter != null) {
                chatCommonMessageList.setMsgIndex(chatroomPresenter.getViewIndex(chatroomView));
                if (!isChatPlaybackLayout) {
                    //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    if (chatroomPresenter.getChatHistoryTime() == 0) {
                        chatroomPresenter.requestChatHistory(chatroomPresenter.getViewIndex(chatroomView));
                    }
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.toggle_emoji_iv) {
            togglePopupLayout(toggleEmojiIv, emojiLy);
        } else if (id == R.id.toggle_more_iv) {
            togglePopupLayout(toggleMoreIv, chatMoreLayout);
        } else if (id == R.id.delete_msg_iv) {
            PLVChatroomUtils.deleteEmoText(inputEt);
        } else if (id == R.id.send_msg_tv) {
            sendChatMessage(inputEt.getText().toString());
        } else if (id == R.id.likes_view) {
            if (chatroomPresenter != null) {
                chatroomPresenter.sendLikeMessage();
            }
            acceptLikesMessage(1);
        } else if (id == R.id.plvlc_emoji_tab_emoji_iv) {
            changeEmojiTab(true);
        } else if (id == R.id.plvlc_emoji_tab_personal_iv) {
            changeEmojiTab(false);
        } else if (id == R.id.plvlc_iv_show_point_reward) {
            if (onViewActionListener != null) {
                onViewActionListener.onShowRewardAction();
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - view???????????????">
    private OnViewActionListener onViewActionListener;

    public void setOnViewActionListener(OnViewActionListener listener) {
        this.onViewActionListener = listener;
    }

    public interface OnViewActionListener {
        /**
         * ????????????
         */
        void onShowBulletinAction();

        /**
         * ????????????????????????
         */
        void onShowRewardAction();

        /**
         * ????????????
         */
        void onShowEffectAction(boolean isShow);

        /**
         * ???????????????????????????
         *
         * @param event ???????????????event data
         */
        void onClickDynamicFunction(String event);
    }
    // </editor-fold>
}
