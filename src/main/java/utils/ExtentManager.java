package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Putanja gde će se čuvati izveštaj
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            spark.config().setReportName("Elevation Finder Automation Results");
            spark.config().setDocumentTitle("QA Regression Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}