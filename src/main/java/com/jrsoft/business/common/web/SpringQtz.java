package com.jrsoft.business.common.web;

import com.jrsoft.business.modules.progress.service.ProgressServiceI;
import com.jrsoft.engine.common.utils.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chesijian on 2019/6/28
 */
public class SpringQtz {
    @Autowired
    private ProgressServiceI progressServiceI;


    protected void execute()  {

        List<Map<String,Object>> remindList = progressServiceI.getRemindList();

        System.out.println("定时任务开始执行==="+CommonUtil.objToJson(remindList));
        String[] assigneeList=new String[remindList.size()];
        for (int i=0;i<remindList.size();i++) {
            assigneeList[i]=String.valueOf(remindList.get(i).get("assignee"));
        }
        //SysNoticeUtil.send("你有个进度任务截止本月完成","点击查看详情", SessionUtil.getBasePath(),"/app/index",null,"提醒",assigneeList);

    }

    private static String getAgentId(Map<String,String> agentMap, String suite_id, String corp_id, String app_id){
        String key = suite_id+"@"+corp_id+"@"+app_id;
        if(agentMap.containsKey(key)){
            return agentMap.get(key);
        }else{
            String sql = "select AGENT_ID_ from PF_WX_APP where SUITE_ID_ = '"+suite_id+"' and CORP_ID_ = '"+corp_id+"' and APP_ID_ = '"+app_id+"' ";
            /*PfSqlServiceI service = Context.getEngine().getEngineConfig().getPfSqlService();
            Map<String,Object> data = service.getMap(sql,null);
            if(data != null && data.size()>0 && CommonUtil.ifNotEmpty(data.get("AGENT_ID_"))){
                agentMap.put(key, String.valueOf(data.get("AGENT_ID_")));
                return String.valueOf(data.get("AGENT_ID_"));
            }*/
        }
        return null;
    }
}
