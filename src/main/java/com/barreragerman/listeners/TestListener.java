package com.barreragerman.listeners;


import com.barreragerman.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
/**
 *✔ Se ejecuta automáticamente al fallar un test
 * ✔ Centralizado
 * ✔ No ensucia los tests
*/
public class TestListener implements ITestListener {

    private static final Logger logger =
            LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.error("Test failed: {}", testName);

        ScreenshotUtil.takeScreenshot(testName);
    }
}
