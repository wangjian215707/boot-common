package com.github.edu.security.login.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
 */
@Slf4j
public class LdapService {
    private static LdapContext context=null;

    private static void connectLDAP(String ldapUrl,String ldapAccount,String ldapPwd){
        // 连接Ldap需要的信息
        String ldapFactory = "com.sun.jndi.ldap.LdapCtxFactory";
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, ldapFactory);
        // LDAP server
        env.put(Context.PROVIDER_URL, ldapUrl);

        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        env.put(Context.SECURITY_PRINCIPAL, ldapAccount);

        env.put(Context.SECURITY_CREDENTIALS, ldapPwd);

        env.put("java.naming.referral", "follow");
        LdapContext ctxTDS = null;
        try {
            ctxTDS = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            log.error("ldap服务器连接失败！："+e.getMessage());
            e.printStackTrace();
        }
        context=ctxTDS;
    }

    /**
     * 检测用户是否存在，并且更新用户密码
     * @param ldapUrl
     * @param ldapAccount
     * @param ldapPwd
     * @param uid
     * @return
     */
    public static boolean updateUserPassword(String ldapUrl,String ldapAccount,String ldapPwd,String uid,String newPwd){
        connectLDAP(ldapUrl,ldapAccount,ldapPwd);
        if(null!=context){
            if(searchUser(context,uid)){
                String userDN=getUserDN(context,uid);
                if(!StringUtils.isEmpty(userDN)){
                    return testModify(context,userDN,newPwd);
                }
            }
            log.error("用户不存在！：user not find");
        }
        return false;
    }

    /**
     * 检测用户是否存在
     * @param ctx
     * @throws Exception
     */
    private static boolean searchUser(LdapContext ctx,String uid){
        // 设置过滤条件
        String filter = "(&(objectClass=person)(uid=" + uid + "))";
        // 限制要查询的字段内容
        String[] attrPersonArray = {"uid"};
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 设置将被返回的Attribute
        searchControls.setReturningAttributes(attrPersonArray);
        // 三个参数分别为：
        // 上下文；
        // 要搜索的属性，如果为空或 null，则返回目标上下文中的所有对象；
        // 控制搜索的搜索控件，如果为 null，则使用默认的搜索控件
        NamingEnumeration<SearchResult> answer = null;
        try {
            answer = ctx.search("", filter.toString(), searchControls);
            // 输出查到的数据
            while (answer.hasMore()) {
                SearchResult result = answer.next();
                NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                while (attrs.hasMore()) {
                    return true;
                }
            }
        } catch (NamingException e) {
            log.error("用户检测失败！："+e.getExplanation());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取用户DN信息
     * @param ctx
     * @param uid
     * @return
     * @throws NamingException
     */
    private static String getUserDN(LdapContext ctx,String uid){
        String userDN = "";
        try{
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration en = ctx.search("","uid="+uid, constraints);
            while (en != null && en.hasMoreElements()){
                Object obj = en.nextElement();
                if(obj instanceof SearchResult){
                    SearchResult si = (SearchResult) obj;
                    userDN += si.getName();
                }
                else{
                    System.out.println(obj);
                }
                System.out.println();
            }
        }catch(Exception e){
            log.error("获取用户DN信息失败："+e.getLocalizedMessage());
            e.printStackTrace();
        }

        return userDN;
    }

    /**
     * 修改用户密码
     * @return
     * @throws Exception
     */
    private static boolean testModify(LdapContext ctx,String userDN,String newPdw) {
        Attributes attrs = new BasicAttributes(true);
        attrs.put("userPassword",newPdw);
        try {
            ctx.modifyAttributes(userDN, DirContext.REPLACE_ATTRIBUTE, attrs);
            ctx.close();
            return true;
        } catch (NamingException e) {
            log.error("修改用户密码失败！："+e.getExplanation());
            e.printStackTrace();
        }
        return false;
    }
}
