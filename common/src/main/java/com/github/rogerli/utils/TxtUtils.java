package com.github.rogerli.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author roger.li
 * @since 2019/6/24
 */
@Slf4j
@Component
public class TxtUtils {

    public final static String SUFFIX = ".txt | .csv";

    /**
     * * 读取数据文件
     *
     * @param fileName
     *
     * @return
     *
     * @throws Exception
     */
    public Map txtToMap(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis, "GBK"))) {
            return txtToMap(br);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Maps.newHashMap();
    }

    /**
     * * 读取数据文件
     *
     * @param br
     *
     * @return
     *
     * @throws Exception
     */
    public Map txtToMap(BufferedReader br) throws IOException {
        // 数据结果
        Map data = new HashMap();

        String line;
        int rowNum = 1;
        Map head = new HashMap();
        List<Map> body = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (rowNum == 1) {
                // 读取头部
                String[] headArray = line.split(",");
                for (int i = 0; i < headArray.length; i++) {
                    String zh = headArray[i];
                    head.put(i, zh);
                }
            } else {
                // 读取数据
                Map bodyData = new LinkedHashMap();
                String[] bodyArray = line.split(",");
                for (int i = 0; i < bodyArray.length; i++) {
                    String bd = bodyArray[i];
                    bodyData.put(head.get(i), bd);
                }
                body.add(bodyData);
            }
            rowNum++;
        }
        data.put("head", head.values().stream().collect(Collectors.toList()));
        data.put("body", body);
        return data;
    }

    public static void main(String[] args) throws Exception {
        String path = "D:\\迅雷下载\\异常停车调账20190124093803.csv";
        TxtUtils utils = new TxtUtils();
        Map list2 = utils.txtToMap(path);
        System.out.println(list2);
    }

}
