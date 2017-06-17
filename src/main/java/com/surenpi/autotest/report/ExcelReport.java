package com.surenpi.autotest.report;

import org.springframework.stereotype.Component;
import org.suren.autotest.web.framework.report.RecordReportWriter;
import org.suren.autotest.web.framework.report.record.ExceptionRecord;
import org.suren.autotest.web.framework.report.record.NormalRecord;
import org.suren.autotest.web.framework.report.record.ProjectRecord;

import javax.annotation.PreDestroy;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
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
    }

    @PreDestroy
    public void saveFile()
    {
        utils.save();
    }
}
