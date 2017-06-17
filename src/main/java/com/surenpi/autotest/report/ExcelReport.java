package com.surenpi.autotest.report;

import com.surenpi.autotest.report.record.ExceptionRecord;
import com.surenpi.autotest.report.record.NormalRecord;
import com.surenpi.autotest.report.record.ProjectRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;

/**
 * Excel格式报告导出
 * @author suren
 */
@Component
public class ExcelReport implements RecordReportWriter
{
    private ExcelUtils utils;

    @Override
    public void write(ExceptionRecord record)
    {
        utils.export(record);
    }

    @Override
    public void write(NormalRecord normalRecord)
    {
        utils.export(normalRecord);
    }

    @Override
    public void write(ProjectRecord projectRecord)
    {
        utils = new ExcelUtils(
                new File(projectRecord.getName() + "" + System.currentTimeMillis() + ".xls"));
        utils.init();
    }

    @PreDestroy
    public void saveFile()
    {
        utils.save();
    }
}
