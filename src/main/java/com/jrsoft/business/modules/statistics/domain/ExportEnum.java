package com.jrsoft.business.modules.statistics.domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by chesijian on 2021/4/19.
 */
public enum ExportEnum {

    SELECT("select", "com.jxz.java.demo.command.SelectCommand"), ADD("add", "com.jxz.java.demo.command.AddCommand"), ABORTED("abort", "com.jxz.java.demo.command.AbortCommand");

    private String command;
    private String clazz;

    public static Map<String, String> map = new HashMap<>(8);

    static {

        Set<String> set = null;
        try {
            set = ClassResourcePatternResolver.findAllClassPathResources();
            Iterator<String> it = set.iterator();
            while (it.hasNext()){
                String fullName = it.next();
                String command = fullName.substring(fullName.lastIndexOf(".")+1).replace("Export","").trim();
                String title = (new StringBuilder()).append(Character.toLowerCase(command.charAt(0))).append(command.substring(1)).toString();
                System.out.println(fullName+".....fullName...title..."+title);
                map.put(title,fullName);
                /*DynamicEnumUtil.addEnum(ExportEnum.class,command,new Class<?>[] {String.class,String.class},new Object[] {command,fullName});
                for (ExportEnum value:ExportEnum.values()){
                    String cmd = value.getCommand();
                    String claz = value.getClazz();
                    map.put(cmd,claz);
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getAllClazz() {
        /*Set<String> set = ClassResourcePatternResolver.findAllClassPathResources();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String fullName = it.next();
            String command = fullName.substring(fullName.lastIndexOf(".")+1).replace("export","").toLowerCase().trim();
            DynamicEnumUtil.addEnum(ExportEnum.class,command,new Class<?>[] {String.class,String.class},new Object[] {command,fullName});
        }
        Map<String, String> map = new HashMap<>(8);
        for (ExportEnum commandEnum : ExportEnum.values()) {
            map.put(commandEnum.getCommand(), commandEnum.getClazz());
        }*/
        return map;
    }

    public String getCommand() {
        return command;
    }

    ExportEnum(String command, String clazz) {
        this.command = command;
        this.clazz = clazz;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}
