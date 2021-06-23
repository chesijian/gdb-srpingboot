package com.jrsoft.business.modules.statistics.domain;/**
 * Created by chesijian on 2021/4/19.
 */

import com.jrsoft.engine.common.utils.CommonUtil;

import java.util.Map;

/**
 * @ClassName ExportContext
 * @Description TODO
 * @Author chesijian
 * @Date 2021/4/19 11:45
 * @Version 1.0
 */
public class ExportContext {
    public ExportStrategy getInstance(String commandType) {
        ExportStrategy commandStrategy = null;
        Map<String, String> allClazz = ExportEnum.getAllClazz();
        String clazz = allClazz.get(commandType);
        if (!CommonUtil.ifNotEmpty(clazz)) {
            clazz = allClazz.get("comm");
        }
        try {
            try {
                commandStrategy = (ExportStrategy) Class.forName(clazz).newInstance();//调用无参构造器创建实例
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return commandStrategy;
    }

}
