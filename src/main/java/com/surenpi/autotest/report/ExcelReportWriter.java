package com.surenpi.autotest.report;

import com.surenpi.autotest.report.model.ExcelReport;
import com.surenpi.autotest.report.record.ExceptionRecord;
import com.surenpi.autotest.report.record.NormalRecord;
import com.surenpi.autotest.report.record.ProjectRecord;
import com.surenpi.autotest.report.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;

/**
 * Excel格式报告导出
 * @author suren
 */
@Component
public class ExcelReportWriter implements RecordReportWriter
{
    private ExcelUtils utils;

    @Override
    public void write(ExceptionRecord record)
    {
        NormalRecord normalRecord = record.getNormalRecord();

        ModelMapper mapper = new ModelMapper();
        ExcelReport excelReport = mapper.map(normalRecord, ExcelReport.class);
        excelReport.setDetail(record.getStackTraceText());
        excelReport.setStatus(ReportStatus.EXCEPTION.name());
        excelReport.setBeginTime(DateUtils.getDateText(normalRecord.getBeginTime()));
        excelReport.setEndTime(DateUtils.getDateText(normalRecord.getEndTime()));

        utils.export(excelReport);
    }

    @Override
    public void write(NormalRecord normalRecord)
    {
        ExcelReport excelReport = new ModelMapper().map(normalRecord, ExcelReport.class);
        excelReport.setStatus(ReportStatus.NORMAL.name());
        excelReport.setBeginTime(DateUtils.getDateText(normalRecord.getBeginTime()));
        excelReport.setEndTime(DateUtils.getDateText(normalRecord.getEndTime()));

        utils.export(excelReport);
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
