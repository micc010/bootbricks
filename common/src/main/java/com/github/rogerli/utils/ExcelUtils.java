package com.github.rogerli.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author roger.li
 * @since 2019/6/24
 */
@Slf4j
@Component
public class ExcelUtils {

    //2003- 版本的excel
    private final static String excel2003L = ".xls";
    //2007+ 版本的excel        
    private final static String excel2007U = ".xlsx";

    public final static String SUFFIX = excel2003L + " | " + excel2007U;

    /**
     * * 读取excel文件
     *
     * @param fileName
     *
     * @return
     *
     * @throws Exception
     */
    public Map excelToMap(String fileName) {
        return excelToMap(fileName, 0, 1);
    }

    /**
     * * 读取excel文件
     *
     * @param fileName
     *
     * @return
     *
     * @throws Exception
     */
    public Map excelToMap(String fileName, int headrow, int datarow) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(new File(fileName));
            return excelToMap(fis, fileName, headrow, datarow);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error(MessageUtils.get("file.is.closeFailed"));
                }
            }
        }
        return Maps.newHashMap();
    }

    /**
     * * 读取excel文件
     *
     * @param is
     * @param fileName
     *
     * @return
     *
     * @throws Exception
     */
    public Map excelToMap(InputStream is, String fileName) throws Exception {
        return excelToMap(is, fileName, 0, 1);
    }

    /**
     * * 读取excel文件
     *
     * @param is
     * @param fileName
     *
     * @return
     *
     * @throws Exception
     */
    public Map excelToMap(InputStream is, String fileName, int headrow, int datarow) throws Exception {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        Workbook work;
        try {
            if (excel2003L.equals(fileType)) {
                work = new HSSFWorkbook(is); //2003-
            } else if (excel2007U.equals(fileType)) {
                work = new XSSFWorkbook(is); //2007+
            } else {
                throw new Exception(MessageUtils.get("file.notExcel"));
            }
            if (null == work) {
                throw new Exception(MessageUtils.get("file.excel.isNull"));
            }
            // 数据结果
            Map data = new HashMap();

            // 读取头部
            Sheet sheet = work.getSheetAt(0);
            Row row = sheet.getRow(headrow);
            Map head = new HashMap();
            for (int i = 0; i < row.getLastCellNum(); i++) {
                String zh = row.getCell(i).getStringCellValue();
                head.put(i, zh);
            }
            data.put("head", head.values().stream().collect(Collectors.toList()));

            // 读取数据
            List<Map> body = new ArrayList<>();
            for (int i = datarow; i <= sheet.getLastRowNum(); i++) {
                Row bodyRow = sheet.getRow(i);
                Map bodyData = new LinkedHashMap();
                for (int j = 0; j < bodyRow.getLastCellNum(); j++) {
                    bodyData.put(head.get(j), getCellValueByCell(bodyRow.getCell(j)));
                }
                body.add(bodyData);
            }
            data.put("body", body);

            work.close();

            return data;
        } catch (FileNotFoundException e) {
            log.error(MessageUtils.get("file.notFound"), fileName);
        } catch (IOException e) {
            log.error(MessageUtils.get("file.excel.initFailed"), fileName);
        }
        return Maps.newHashMap();
    }

    /**
     * 获取cell字符串
     *
     * @param cell
     * @return
     */
    private String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        DataFormatter dataFormatter = new DataFormatter();

        String data = null;

        if (cell.getCellType() == CellType.FORMULA.ordinal()) {
            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            data = dataFormatter.formatCellValue(cell, evaluator);
        } else {
            data = dataFormatter.formatCellValue(cell);
        }
        String dataFormatString = cell.getCellStyle().getDataFormatString();
        if (StringUtils.hasText(dataFormatString)) {
            if (dataFormatString.matches("@\".+\"")) {
                Pattern p = Pattern.compile("@\"(.+)\"");
                Matcher m = p.matcher(dataFormatString);
                m.find();
                data = data + m.group(1);
            }
        }

        return data;

    }

}
