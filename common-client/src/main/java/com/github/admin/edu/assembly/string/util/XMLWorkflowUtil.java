package com.github.admin.edu.assembly.string.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-6-20
 */
@Slf4j
public class XMLWorkflowUtil {

    /**
     * 这里值正对流程workflow
     * @param filePath 流程表单xml地址
     * @param stepId 步骤id值
     * @return
     */
    public static String getWorkflowAttributeValue(String filePath,String stepId){
        try{
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(new File(filePath));
            //获取根节点元素对象
            Element root = document.getRootElement();
            //遍历
            return listNodes(root,"step",stepId);
        }catch (DocumentException e) {
            log.error("xml文件读取失败！"+e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    //遍历当前节点下的所有节点
    private static String listNodes(Element node, String name, String title){
        String value="";
        if(name.equals(node.getName())){//节点名称
            //首先获取当前节点的所有属性节点
            Attribute attribute_is=node.attribute("id");
            Attribute attribute_name=node.attribute("name");
            if(null!=attribute_is&&null!=attribute_is){
                if(attribute_is.getValue().equals(title)){
                    return attribute_name.getValue();
                }
            }
        }
        //同时迭代当前节点下面的所有子节点
        //使用递归
        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            Element e = iterator.next();
            value=listNodes(e,name,title);
            if(""!=value){
                return value;
            }
        }
        return "";
    }
}
