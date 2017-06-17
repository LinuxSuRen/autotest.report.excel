package com.surenpi.autotest.report;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author suren
 */
public class ExcelUtils
{
    private static final String HEAD_MODULE_NAME = "模块名称";
    private static final String HEAD_CLAZZ = "类名";
    private static final String HEAD_METHOD = "方法名";
    private static final String HEAD_STATUS = "状态";
    private static final String HEAD_BEGINE_TIME = "开始时间";
    private static final String HEAD_END_TIME = "结束时间";
    private static final String HEAD_DETAIL = "详细信息";
    private static final String HEAD_MODULE_DESC = "模块描述";

    private static final TreeMap<String, String> headerMap = new TreeMap<String, String>();

    private File targetFile;

    private FileOutputStream fos = null;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;

    private int currentRow;

    public ExcelUtils(File targetFile)
    {
        this.targetFile = targetFile;
    }

    public void init()
    {
        headerMap.put(HEAD_MODULE_DESC, "moduleDescription");
        headerMap.put(HEAD_DETAIL, "project");
        headerMap.put(HEAD_END_TIME, "endTime");
        headerMap.put(HEAD_BEGINE_TIME, "beginTime");
        headerMap.put(HEAD_METHOD, "methodName");
        headerMap.put(HEAD_CLAZZ, "clazzName");
        headerMap.put(HEAD_MODULE_NAME, "moduleName");
        headerMap.put(HEAD_STATUS, "status");

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("测试报告明细");
    }

    /**
     * 将objList中的对象列表到导出到文件中
     *
     * @param objList 对象列表
     * @return 导出成功返回true
     */
    public boolean export(List<Object> objList) {
        try {
            if(fos == null)
            {
                fos = new FileOutputStream(targetFile);
            }

            return exportStream(objList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 导出一条记录
     * @param targetObj
     * @return
     */
    public boolean export(Object targetObj)
    {
        List<Object> list = new ArrayList<Object>(1);
        list.add(targetObj);

        return export(list);
    }

    /**
     * 将objList中的对象列表到导出到输出流中
     *
     * @param objList 对象列表
     * @return 导出成功返回true
     */
    public boolean exportStream(List<Object> objList) {
        createTitle(sheet);

        if(objList != null)
        {
            int size = objList.size();
            int incr = size + currentRow;
            for(int i = currentRow; i < incr; i++, currentRow++) {
                fillContent(sheet, i, objList.get(i - currentRow));
            }
        }

        return false;
    }

    /**
     * 设置表头
     *
     * @param sheet
     */
    private void createTitle(HSSFSheet sheet) {
        if(currentRow > 0)
        {
            return;
        }

        HSSFRow row = sheet.createRow(currentRow++);
        int column = 0;

        for(String key : headerMap.keySet()) {
            HSSFCell cell = row.createCell(column++);
            cell.setCellValue(key);
        }
    }

    /**
     * 根据一个对象填充一行数据
     *
     * @param sheet
     * @param rowNum 行号
     * @param data 数据对象
     */
    private void fillContent(HSSFSheet sheet, int rowNum, Object data) {
        if(sheet == null || data == null) {
            return;
        }

        HSSFRow row = sheet.createRow(rowNum);
        int column = 0;

        Class<? extends Object> dataCls = data.getClass();
        for(String key : headerMap.keySet()) {
            String name = headerMap.get(key);

            try {
                Method method = dataCls.getMethod(String.format("get%s%s",
                        name.substring(0, 1).toUpperCase(), name.substring(1)));
                Object value = method.invoke(data);
                if(value != null) {
                    HSSFCell cell = row.createCell(column);
                    cell.setCellValue(value.toString());
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                column++;
            }
        }
    }

    public void save()
    {
        if(fos != null)
        {
            try {
                workbook.write(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
