package com.github.admin.edu.assembly.common.util;

import com.github.admin.edu.assembly.common.entity.*;
import com.github.admin.edu.assembly.string.util.StringUtils;

import java.util.*;

/**
 * 树结构生成方法
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-3-25
 */
public class TreeUtil {


    /**
     * 获取树结构json字符串
     *
     * @param list           数据
     * @param idFieldCode    节点编码字段名称
     * @param pidFieldCode   父节点编码字段名称
     * @param titleFieldCode 标题编码字段名称
     * @param <T>
     * @return
     */
    public static <T> String getJsonTree(List<T> list, String idFieldCode, String pidFieldCode,
                                         String titleFieldCode) {
        return getJsonTree(list, idFieldCode, pidFieldCode, titleFieldCode, null);
    }

    /**
     * 获取树结构json字符串
     *
     * @param list           数据
     * @param idFieldCode    节点编码字段名称
     * @param pidFieldCode   父节点编码字段名称
     * @param titleFieldCode 标题编码字段名称
     * @param iconClass      样式
     * @param <T>
     * @return
     */
    public static <T> String getJsonTree(List<T> list, String idFieldCode, String pidFieldCode,
                                         String titleFieldCode, String iconClass) {
        List<TreeEntity> entityList = getTreeList(list, idFieldCode, pidFieldCode, titleFieldCode, iconClass);
        JsonTree<TreeEntity> jsonTree = new JsonTree<>();
        jsonTree.setData(entityList);
        return JsonUtils.toJson(jsonTree);
    }

    /**
     * 获取ECharts树结构
     *
     * @param list           数据对象
     * @param idFieldCode    id 字段名称
     * @param pidFieldCode   pid 节点字段名称
     * @param titleFieldCode 标题字段名称
     * @param <T>
     * @return
     */
    public static <T> String getEChartsTreeJsonList(List<T> list, String idFieldCode, String pidFieldCode,
                                                    String titleFieldCode) {
        List<EChartsTreeEntity> eChartsTreeList = getEChartsTreeList(list, idFieldCode, pidFieldCode, titleFieldCode);
        return JsonUtils.toJson(eChartsTreeList);
    }

    public static <T> String getEChartsTreeEntity(List<T> list, String idFieldCode, String pidFieldCode,
                                                  String titleFieldCode) {
        List<EChartsTreeEntity> eChartsTreeList = getEChartsTreeList(list, idFieldCode, pidFieldCode, titleFieldCode);
        if (null != eChartsTreeList && eChartsTreeList.size() > 0) {
            EChartsTreeEntity treeEntity = eChartsTreeList.get(0);
            return JsonUtils.toJson(treeEntity);
        }
        return "";
    }

    /**
     * 获取树结构json字符串
     *
     * @param list           数据
     * @param idFieldCode    节点编码字段名称
     * @param pidFieldCode   父节点编码字段名称
     * @param titleFieldCode 标题编码字段名称
     * @param iconClass      样式
     * @param <T>
     * @return
     */
    public static <T> String getJsonSelectTree(String[] selectCode, List<T> list,
                                               String idFieldCode,
                                               String pidFieldCode,
                                               String titleFieldCode,
                                               String iconClass) {
        List<TreeEntity> entityList = getTreeSelectList(selectCode, list, idFieldCode, pidFieldCode, titleFieldCode, iconClass);
        JsonTree<TreeEntity> jsonTree = new JsonTree<>();
        jsonTree.setData(entityList);
        return JsonUtils.toJson(jsonTree);
    }

    /**
     * 菜单数据结构
     * @param list
     * @param idFieldCode
     * @param pidFieldCode
     * @param titleFieldCode
     * @param iconClass
     * @param url
     * @param <T>
     * @return
     */
    public static <T> String getJsonMenuTree(List<T> list, String idFieldCode,
                                             String pidFieldCode,
                                             String titleFieldCode,
                                             String iconClass,
                                             String url) {
        List<TMenuEntity> menuEntityList = getMenuTreeList(list,idFieldCode,pidFieldCode,titleFieldCode,iconClass,url);
        if (null != menuEntityList && menuEntityList.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 0);
            map.put("msg", "");
            map.put("data", menuEntityList);
            return JsonUtils.toJson(map);
        }
        return "";
    }

    /**
     * ECharts 数据结构
     *
     * @param idFieldCode
     * @param pidFieldCode
     * @param titleFieldCode
     * @param <T>
     * @return
     */
    private static <T> List<TMenuEntity> getMenuTreeList(List<T> list, String idFieldCode,
                                                            String pidFieldCode,
                                                            String titleFieldCode,
                                                            String iconClass,
                                                            String url) {
        Map<String, List<TMenuEntity>> map = new HashMap<String, List<TMenuEntity>>();
        List<String> treeList = new ArrayList<>();//记录节点的key值
        if (null != list && list.size() > 0) {
            list.forEach(t -> {
                TMenuEntity tree = new TMenuEntity();
                tree.setTitle(String.valueOf(ObjectUtil.getValueByKey(t, titleFieldCode)));
                tree.setId(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)));
                tree.setPid(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                tree.setIcon(String.valueOf(ObjectUtil.getValueByKey(t,iconClass)));
                tree.setUrl(String.valueOf(ObjectUtil.getValueByKey(t,url)));
                List<TMenuEntity> treeLists = map.get(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                if (null == treeLists) {
                    treeLists = new ArrayList<>();
                    treeList.add(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                }
                treeLists.add(tree);
                map.put(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)), treeLists);
            });
        }
        List<TMenuEntity> newList = new ArrayList<>();
        if (treeList != null && treeList.size() > 0) {
            treeList.forEach(tree -> {
                list.forEach(t -> {
                    if ((String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)).equals(tree) && !String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)).equals("0")) ||
                            (String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)).equals("0") && tree.equals("0"))) {
                        TMenuEntity sysTree = new TMenuEntity();
                        sysTree.setTitle(String.valueOf(ObjectUtil.getValueByKey(t, titleFieldCode)));
                        sysTree.setId(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)));
                        sysTree.setPid(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                        sysTree.setIcon(String.valueOf(ObjectUtil.getValueByKey(t,iconClass)));
                        sysTree.setUrl(String.valueOf(ObjectUtil.getValueByKey(t,url)));
                        sysTree.setList(map.get(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode))));
                        newList.add(sysTree);
                    }
                });
            });
        }
        if (null != newList && newList.size() > 0) {
            newList.forEach(tSysTree -> {
                List<TMenuEntity> list1 = tSysTree.getList();
                if (null != list1 && list1.size() > 0) {
                    list1.forEach(tSysTree1 -> {
                        tSysTree1.setList(map.get(tSysTree1.getId()));
                    });
                }
            });
        }
        List<TMenuEntity> trees = new ArrayList<>();
        if (null != newList && newList.size() > 0) {
            newList.forEach(tSysTree -> {
                if ("0".equals(tSysTree.getPid())) {
                    trees.add(tSysTree);
                }
            });
        }
        return trees;
    }

    /**
     * ECharts 数据结构
     *
     * @param idFieldCode
     * @param pidFieldCode
     * @param titleFieldCode
     * @param <T>
     * @return
     */
    private static <T> List<EChartsTreeEntity> getEChartsTreeList(List<T> list, String idFieldCode,
                                                                  String pidFieldCode,
                                                                  String titleFieldCode) {
        Map<String, List<EChartsTreeEntity>> map = new HashMap<String, List<EChartsTreeEntity>>();
        List<String> treeList = new ArrayList<>();//记录节点的key值
        if (null != list && list.size() > 0) {
            list.forEach(t -> {
                EChartsTreeEntity tree = new EChartsTreeEntity();
                tree.setName(String.valueOf(ObjectUtil.getValueByKey(t, titleFieldCode)));
                tree.setId(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)));
                tree.setPid(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                List<EChartsTreeEntity> treeLists = map.get(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                if (null == treeLists) {
                    treeLists = new ArrayList<>();
                    treeList.add(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                }
                treeLists.add(tree);
                map.put(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)), treeLists);
            });
        }
        List<EChartsTreeEntity> newList = new ArrayList<>();
        if (treeList != null && treeList.size() > 0) {
            treeList.forEach(tree -> {
                list.forEach(t -> {
                    if ((String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)).equals(tree) && !String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)).equals("0")) ||
                            (String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)).equals("0") && tree.equals("0"))) {
                        EChartsTreeEntity sysTree = new EChartsTreeEntity();
                        sysTree.setName(String.valueOf(ObjectUtil.getValueByKey(t, titleFieldCode)));
                        sysTree.setId(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)));
                        sysTree.setPid(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                        sysTree.setChildren(map.get(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode))));
                        newList.add(sysTree);
                    }
                });
            });
        }
        if (null != newList && newList.size() > 0) {
            newList.forEach(tSysTree -> {
                List<EChartsTreeEntity> list1 = tSysTree.getChildren();
                if (null != list1 && list1.size() > 0) {
                    list1.forEach(tSysTree1 -> {
                        tSysTree1.setChildren(map.get(tSysTree1.getId()));
                    });
                }
            });
        }
        List<EChartsTreeEntity> trees = new ArrayList<>();
        if (null != newList && newList.size() > 0) {
            newList.forEach(tSysTree -> {
                if ("0".equals(tSysTree.getPid())) {
                    trees.add(tSysTree);
                }
            });
        }
        return trees;
    }

    private static <T> List<TreeEntity> getTreeSelectList(String[] selectCode, List<T> list,
                                                          String idFieldCode,
                                                          String pidFieldCode,
                                                          String titleFieldCode,
                                                          String iconClass) {
        Map<String, List<TreeEntity>> map = new HashMap<String, List<TreeEntity>>();
        List<String> treeList = new ArrayList<>();//记录节点的key值
        if (null != list && list.size() > 0) {
            list.forEach(t -> {
                TreeEntity tree = new TreeEntity();
                tree.setTitle(String.valueOf(ObjectUtil.getValueByKey(t, titleFieldCode)));
                tree.setId(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)));
                tree.setParentId(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                if (null != selectCode && Arrays.<String>asList(selectCode).contains(tree.getId())) {
                    tree.setSelected("selected");
                }
                List<TreeEntity> treeLists = map.get(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                if (null == treeLists) {
                    treeLists = new ArrayList<>();
                    treeList.add(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                }
                treeLists.add(tree);
                map.put(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)), treeLists);
            });
        }
        List<TreeEntity> newList = new ArrayList<>();
        if (treeList != null && treeList.size() > 0) {
            treeList.forEach(tree -> {
                list.forEach(t -> {
                    if ((String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)).equals(tree) && !String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)).equals("0")) ||
                            (String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)).equals("0") && tree.equals("0"))) {
                        TreeEntity sysTree = new TreeEntity();
                        sysTree.setTitle(String.valueOf(ObjectUtil.getValueByKey(t, titleFieldCode)));
                        sysTree.setId(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode)));
                        sysTree.setParentId(String.valueOf(ObjectUtil.getValueByKey(t, pidFieldCode)));
                        sysTree.setChildren(map.get(String.valueOf(ObjectUtil.getValueByKey(t, idFieldCode))));
                        if (null != selectCode && Arrays.<String>asList(selectCode).contains(sysTree.getId())) {
                            sysTree.setSelected("selected");
                        }
                        newList.add(sysTree);
                    }
                });
            });
        }
        if (null != newList && newList.size() > 0) {
            newList.forEach(tSysTree -> {
                List<TreeEntity> list1 = tSysTree.getChildren();
                if (null != list1 && list1.size() > 0) {
                    list1.forEach(tSysTree1 -> {
                        tSysTree1.setChildren(map.get(tSysTree1.getId()));
                    });
                }
            });
        }
        List<TreeEntity> trees = new ArrayList<>();
        if (null != newList && newList.size() > 0) {
            newList.forEach(tSysTree -> {
                if ("0".equals(tSysTree.getParentId())) {
                    trees.add(tSysTree);
                }
                if (StringUtils.isNotBlank(iconClass) && !"0".equals(tSysTree.getParentId())) {
                    tSysTree.setIconClass(iconClass);
                }
            });
        }
        return trees;
    }

    private static <T> List<TreeEntity> getTreeList(List<T> list, String idFieldCode, String pidFieldCode,
                                                    String titleFieldCode, String iconClass) {

        return getTreeSelectList(null, list, idFieldCode, pidFieldCode, titleFieldCode, iconClass);
    }


}
