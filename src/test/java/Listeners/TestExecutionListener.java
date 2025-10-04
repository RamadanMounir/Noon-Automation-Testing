package Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestExecutionListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(">>> STARTING TEST: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(">>> PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(">>> FAILED: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println(">>> FINISHED TEST EXECUTION: " + context.getName());
    }
}
