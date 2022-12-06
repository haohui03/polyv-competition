package com.scut.plvlee2.Streamlive;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter;
/** @deprecated */
public class DocumentLy extends FrameLayout {
    PLVChatroomPresenter ChatroomPresenter;

    IPLVLiveRoomDataManager liveRoomDataManager;

    public DocumentLy(@NonNull Context context) {
        super(context);
    }

    public DocumentLy(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DocumentLy(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DocumentLy(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    void init() {

    }
}
