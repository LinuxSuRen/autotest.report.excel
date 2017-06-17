package com.surenpi.autotest.report;

import org.springframework.stereotype.Component;
import org.suren.autotest.web.framework.report.RecordReportWriter;
import org.suren.autotest.web.framework.report.record.ExceptionRecord;
import org.suren.autotest.web.framework.report.record.NormalRecord;
import org.suren.autotest.web.framework.report.record.ProjectRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author suren
 */
@Component
public class ExcelReport implements RecordReportWriter
{
    private ExcelUtils utils = new ExcelUtils();

    @Override
    public void write(ExceptionRecord record) {
    }

    @Override
    public void write(NormalRecord normalRecord)
    {
        List<Object> list = new ArrayList<Object>();
        list.add(normalRecord);
        utils.export(list, new File(normalRecord.getModuleName() + normalRecord.getMethodName() + ".xls"));
    }

    @Override
    public void write(ProjectRecord projectRecord) {

    }
}
