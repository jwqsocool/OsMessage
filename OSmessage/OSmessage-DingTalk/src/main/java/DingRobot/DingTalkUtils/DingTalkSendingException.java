package DingRobot.DingTalkUtils;

import DingRobot.Config.GroupConfig;
import DingRobot.Param.DingTalkMsg;
import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DingTalkSendingException {


    public static void dingTalkSendingExceptionByMarkdown(Exception e, GroupConfig group){
        StackTraceElement stackTrace = e.getStackTrace()[0];
        String s = "**异常信息：**" + e.getMessage() +
                "  \n  " + "  **所在文件：**  " + stackTrace.getFileName() +
                "  \n  " + "  **报错行号：**  " + stackTrace.getLineNumber() +
                "  \n  " + "  **相关方法：**  " + stackTrace.getMethodName() +
                "  \n  " + "  **异常class：**  " + stackTrace.getClass() +
                "  \n  " + "  **异常className：**  " + stackTrace.getClassName();
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(DingTalkMsg.getMsgTypeMarkdown());
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("异常告警通知！");
        markdown.setText(s);
        request.setMarkdown(markdown);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);

        OapiRobotSendResponse response = new OapiRobotSendResponse();
        try {
            response = new DefaultDingTalkClient(group.toString()).execute(request);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("钉钉机器人异常通知返回信息：" + JSON.toJSONString(response));
    }

    public static void dingTalkSendingExceptionByActioncard(Exception e, GroupConfig group, String actionUrl){
        StackTraceElement stackTrace = e.getStackTrace()[0];
        String s = "**异常信息：**" + e.getMessage() +
                "  \n  " + "  **所在文件：**  " + stackTrace.getFileName() +
                "  \n  " + "  **报错行号：**  " + stackTrace.getLineNumber() +
                "  \n  " + "  **相关方法：**  " + stackTrace.getMethodName() +
                "  \n  " + "  **异常class：**  " + stackTrace.getClass() +
                "  \n  " + "  **异常className：**  " + stackTrace.getClassName();
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(DingTalkMsg.getMsgTypeActionCard());
        OapiRobotSendRequest.Actioncard actioncard = new OapiRobotSendRequest.Actioncard();
        actioncard.setTitle("异常告警通知！");
        actioncard.setText(s);
        OapiRobotSendRequest.Btns btn = new OapiRobotSendRequest.Btns();
        btn.setTitle("查看");
        btn.setActionURL(actionUrl);
        request.setActionCard(actioncard);

        OapiRobotSendResponse response = new OapiRobotSendResponse();
        try {
            response = new DefaultDingTalkClient(group.toString()).execute(request);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("钉钉机器人异常通知返回信息：" + JSON.toJSONString(response));
    }
}
