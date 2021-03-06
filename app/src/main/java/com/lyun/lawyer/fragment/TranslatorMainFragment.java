package com.lyun.lawyer.fragment;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lyun.lawyer.Account;
import com.lyun.lawyer.AppApplication;
import com.lyun.lawyer.R;
import com.lyun.lawyer.api.response.TranslationOrderResponse;
import com.lyun.lawyer.databinding.FragmentTranslatorGrabLayoutBinding;
import com.lyun.lawyer.im.avchat.AVChatProfile;
import com.lyun.lawyer.model.TranslationOrderModel;
import com.lyun.lawyer.service.TranslationOrderService;
import com.lyun.lawyer.viewmodel.TranslatorMainViewModel;
import com.lyun.lawyer.viewmodel.watchdog.ITranslatorMainViewModelCallbacks;
import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.library.mvvm.viewmodel.ProgressBarDialogViewModel;
import com.lyun.utils.L;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatEventType;
import com.netease.nimlib.sdk.avchat.constant.AVChatTimeOutEvent;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatCalleeAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNotifyOption;
import com.netease.nimlib.sdk.avchat.model.AVChatOptionalConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.netease.nimlib.sdk.avchat.constant.AVChatTimeOutEvent.INCOMING_TIMEOUT;
import static com.netease.nimlib.sdk.avchat.constant.AVChatTimeOutEvent.OUTGOING_TIMEOUT;

/**
 * @author Gordon
 * @since 2017/1/20
 * do(翻译主页抢单页面)
 */

public class TranslatorMainFragment extends MvvmFragment<FragmentTranslatorGrabLayoutBinding, TranslatorMainViewModel>
        implements ITranslatorMainViewModelCallbacks {

    private TranslationOrderResponse mGrabOrderInfo;

    private ProgressBarDialogViewModel mProgressDialog;

    public TranslatorMainFragment() {
    }

//    public static TranslatorMainFragment newInstance() {
//        TranslatorMainFragment fragment = new TranslatorMainFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @NonNull
    @Override
    protected TranslatorMainViewModel createViewModel() {
        return new TranslatorMainViewModel().setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_translator_grab_layout;
    }

    @Override
    public void onGrabOrderStart(ObservableField observableField, int fieldId) {
        showProgress(null);
    }

    @Override
    public void onGrabOrderSuccess(ObservableField<TranslationOrderResponse> observableField, int fieldId) {
        mGrabOrderInfo = observableField.get();

        L.i(getClass().getSimpleName(), "抢单成功：" + new Gson().toJson(mGrabOrderInfo));

        if ("图文".equals(mGrabOrderInfo.getOrdertype())) {
            L.i(getClass().getSimpleName(), "开启图文服务:" + new Gson().toJson(mGrabOrderInfo));
            startTranslationService();
            return;
        }
        AVChatManager.getInstance().call(observableField.get().getUsername(), AVChatType.AUDIO, new AVChatOptionalConfig(), new AVChatNotifyOption(), new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData avChatData) {
                L.i("AVChat", "语音请求发起成功，等待对方接听");
                showProgress("正在为您接通");
            }

            @Override
            public void onFailed(int code) {
                L.e("AVChat", "语音请求发起失败 code:" + code);
            }

            @Override
            public void onException(Throwable exception) {
                L.e("AVChat", "语音请求发起失败", exception);
            }
        });
    }

    @Override
    public void onGrabOrderFail(ObservableField<String> observableField, int fieldId) {
        dismissProgress();
        Toast.makeText(getContext(), observableField.get(), Toast.LENGTH_LONG).show();
    }

    protected void startTranslationService() {

        if (mGrabOrderInfo == null) {
            return;
        }

        AVChatManager.getInstance().observeCalleeAckNotification(mAVChatCallAckObserver, false);

        TranslationOrderModel.OrderType orderType = TranslationOrderModel.OrderType.AUDIO;
        if ("图文".equals(mGrabOrderInfo.getOrdertype())) {
            orderType = TranslationOrderModel.OrderType.MESSAGE;
        }

        TranslationOrderService.start(getActivity(), mGrabOrderInfo.getUserorderid(), mGrabOrderInfo.getDomain(), orderType, Account.preference().getCardNo(), mGrabOrderInfo.getUsername());

        dismissProgressAfter1S();

        mGrabOrderInfo = null;
    }

    /**
     * 监听被叫方回应（主叫方）
     * 主叫方在发起呼叫成功后需要监听被叫方的回应，
     * 监听接口 observeCalleeAckNotification，
     * 回调返回 AVChatCalleeAckEvent，其中包含被叫方的回应结果：
     * <p>
     * 对方拒绝接听;
     * 对方已经有来电了，此时会返回忙;
     * 对方同意接听，此时 SDK 会自动开启音视频设备，建立通话连接，然后双方就可以进行语音视频通话了。
     */
    Observer<AVChatCalleeAckEvent> mAVChatCallAckObserver = (Observer<AVChatCalleeAckEvent>) ackInfo -> {
        if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_BUSY) {
            // 对方正在忙
            L.e("AVChat", "对方正在忙");
        } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_REJECT) {
            // 对方拒绝接听
            L.e("AVChat", "对方拒绝接听");
        } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_AGREE) {
            // 对方同意接听
            L.i("AVChat", "对方同意接听");
            // 设备初始化成功，开始通话
            L.i("AVChat", "设备初始化成功，开始通话");
            AVChatProfile.getInstance().setAVChatting(true);
            AVChatManager.getInstance().muteRemoteAudio(ackInfo.getAccount(), false);
            AVChatManager.getInstance().muteLocalAudio(false);
            // 切换到语音聊天界面
            L.i(getClass().getSimpleName(), "开启语音服务:" + new Gson().toJson(mGrabOrderInfo));
            startTranslationService();
        }
        dismissProgressAfter1S();
    };

    /**
     * 监听呼叫或接听超时通知
     * 主叫方在拨打网络通话时，超过 45 秒被叫方还未接听来电，则自动挂断。被叫方超过 45 秒未接听来听，也会自动挂断，在通话过程中网络超时 30 秒自动挂断。
     */
    Observer<AVChatTimeOutEvent> mAVChatCallTimeoutObserver = (Observer<AVChatTimeOutEvent>) event -> {
        // 超时类型
        L.i("AVChat", "语音聊天中断：event -> " + event);
        if (event == OUTGOING_TIMEOUT) {
            dismissProgress();
            Toast.makeText(AppApplication.getInstance(), "订单已过期", Toast.LENGTH_SHORT).show();
        } else if (event == INCOMING_TIMEOUT) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressBarDialogViewModel(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        // 监听被叫方回应（主叫方）
        AVChatManager.getInstance().observeCalleeAckNotification(mAVChatCallAckObserver, true);
        getFragmentViewModel().startAutoRefresh();
        // 监听呼叫或接听超时通知
        AVChatManager.getInstance().observeTimeoutNotification(mAVChatCallTimeoutObserver, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentViewModel().stopAutoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AVChatManager.getInstance().observeCalleeAckNotification(mAVChatCallAckObserver, false);
        AVChatManager.getInstance().observeTimeoutNotification(mAVChatCallTimeoutObserver, false);
        dismissProgress();
        mProgressDialog = null;
    }

    protected void dismissProgressAfter1S() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> dismissProgress());
    }

    protected void showProgress(String message) {
        if (!getActivity().isFinishing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
