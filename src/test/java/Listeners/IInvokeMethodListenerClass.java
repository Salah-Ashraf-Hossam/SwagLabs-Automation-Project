package Listeners;

import Pages.P02_LandingPage;
import Utilites.LogsUtils;
import Utilites.Utility;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokeMethodListenerClass implements IInvokedMethodListener {


    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

    }


    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        File logFile = Utility.getLatestFile(LogsUtils.LOGS_PATH);
        try {
            assert logFile != null;
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtils.info("Test Case " + testResult.getName() + " failed");
            Utility.takeScreenShot(getDriver(), testResult.getName());
            Utility.takeFullScreenSHOT(getDriver(), new P02_LandingPage(getDriver()).getNumberOfSelectedProductOnCart());
        }
    }
}
