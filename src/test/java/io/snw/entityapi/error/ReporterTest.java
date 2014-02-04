package io.snw.entityapi.error;

import io.snw.entityapi.utils.PastebinReporter;

public class ReporterTest {

    public PastebinReporter REPORTER = new PastebinReporter("8759cf9327f8593508789ecaa36cf27b");

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void testReport() throws Exception {
        System.out.println(REPORTER.post(new PastebinReporter.Report("## TEST ##").appendLine("1. Line 1"), PastebinReporter.ReportFormat.YAML, PastebinReporter.ExpireDate.TEN_MINUTES));
    }
}
