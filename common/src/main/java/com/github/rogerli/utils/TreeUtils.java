package com.github.rogerli.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author roger.li
 * @since 2019/6/18
 */
public class TreeUtils {

    /**
     * list to tree root
     *
     * @param orderList
     * @param keyName
     * @param parentName
     *
     * @return
     */
    public static List listToTreeRoot(List orderList, String keyName, String parentName, String listName) {
        Assert.notEmpty(orderList, MessageUtils.get("list.notEmpty"));
        List root = new ArrayList();

        orderList.stream().forEach(o -> {
            BeanWrapper wrapper = new BeanWrapperImpl(o);
            if (wrapper.getPropertyValue(parentName) == null || "".equals(wrapper.getPropertyValue(parentName))) { // parentName 为空的属于根项目
                root.add(o);
            } else { // parentName 不为空迭代处理
                listToTree(o, orderList, keyName, parentName, listName);
            }
        });

        return root;
    }

    /**
     * list to tree
     *
     * @param obj
     * @param orderList
     * @param keyName
     * @param parentName
     * @param listName
     *
     * @return
     */
    public static void listToTree(Object obj, List orderList, String keyName, String parentName, String listName) {
        Assert.notEmpty(orderList, MessageUtils.get("list.notEmpty"));

        orderList.stream().forEach(o -> {
            BeanWrapper wrapper = new BeanWrapperImpl(o);
            BeanWrapper objWrapper = new BeanWrapperImpl(obj);
            if (wrapper.getPropertyValue(keyName).equals(objWrapper.getPropertyValue(parentName))) {
                if (wrapper.getPropertyValue(listName) == null) {
                    List tree = new ArrayList();
                    wrapper.setPropertyValue(listName, tree);
                }
                ((List) wrapper.getPropertyValue(listName)).add(obj);
            } else {
                return;
            }
        });
    }

//    public static void main(String[] args) throws JsonProcessingException {
//        List<Test> list = Lists.newArrayList();
//        list.add(new Test().setId("1"));
//        list.add(new Test().setId("2"));
//        list.add(new Test().setId("3").setParentId("1"));
//        list.add(new Test().setId("4").setParentId("1"));
//        list.add(new Test().setId("5").setParentId("2"));
//        list.add(new Test().setId("6"));
//        list.add(new Test().setId("7"));
//        list.add(new Test().setId("8").setParentId("7"));
//        list.add(new Test().setId("9").setParentId("7"));
//        list.add(new Test().setId("10").setParentId("3"));
//        list.add(new Test().setId("11").setParentId("5"));
//        System.out.println(new ObjectMapper().writeValueAsString(TreeUtils.listToTreeRoot(list, "id", "parentId", "sons")));
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Accessors(chain = true)
//    public static class Test {
//        private String id;
//        private String parentId;
//        private List<Test> sons;
//    }

}
