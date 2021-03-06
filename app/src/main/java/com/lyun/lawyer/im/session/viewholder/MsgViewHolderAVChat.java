package com.lyun.lawyer.im.session.viewholder;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyun.lawyer.R;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment;

/**
 * Created by zhoujianghua on 2015/8/6.
 */
public class MsgViewHolderAVChat extends MsgViewHolderBase {

    private ImageView typeImage;
    private TextView statusLabel;
    private boolean isReceive;


    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_avchat;
    }

    @Override
    protected void inflateContentView() {
        typeImage = findViewById(R.id.message_item_avchat_type_img);
        statusLabel = findViewById(R.id.message_item_avchat_state);
    }

    @Override
    protected void bindContentView() {
        if (message.getAttachment() == null) {
            return;
        }

        layoutByDirection();

        refreshContent();
    }

    private void layoutByDirection() {
        AVChatAttachment attachment = (AVChatAttachment) message.getAttachment();

        if (isReceivedMessage()) {
            if (attachment.getType() == AVChatType.AUDIO) {
                //typeImage.setImageResource(R.drawable.avchat_left_type_audio);
                typeImage.setImageResource(R.drawable.avchat_right_type_audio);
            } else {
                //typeImage.setImageResource(R.drawable.avchat_left_type_video);
                typeImage.setImageResource(R.drawable.avchat_right_type_video);
            }
            isReceive = false;
            //statusLabel.setTextColor(Color.WHITE);
            statusLabel.setTextColor(context.getResources().getColor(R.color.color_grey_999999));
        } else {
            if (attachment.getType() == AVChatType.AUDIO) {
                typeImage.setImageResource(R.drawable.avchat_right_type_audio);
            } else {
                typeImage.setImageResource(R.drawable.avchat_right_type_video);
            }
            isReceive = true;
            statusLabel.setTextColor(context.getResources().getColor(R.color.color_grey_999999));
        }
    }

    private void refreshContent() {
        AVChatAttachment attachment = (AVChatAttachment) message.getAttachment();

        String textString = "";
        switch (attachment.getState()) {
            case Success: //成功接听
                textString = TimeUtil.secToTime(attachment.getDuration());
                break;
            case Missed: //未接听
                textString = "未接听";
                break;
            case Rejected: //主动拒绝
                if (isReceive)
                    textString = "对方拒绝接听";
                else
                    textString = "您拒绝接听";
                break;
            case Canceled: //未接听
                if (isReceive)
                    textString = "对方取消语音请求";
                else
                    textString = "您取消语音请求";
                break;
        default:
            break;
        }

        statusLabel.setText(textString);
    }
}
