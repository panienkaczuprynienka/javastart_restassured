package pl.javastart.restassured.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyListener implements ITestListener {
  @Override
  public void onTestStart(ITestResult result) {
    System.out.println("MyListener: Starting test " + result.getMethod().getMethodName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    System.out.println("MyListener: Finished successfully test: " + result.getMethod().getMethodName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    System.out.println("MyListener: Test: " + result.getMethod().getMethodName() + " has failed.");
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    System.out.println("MyListener: Skipping test " + result.getMethod().getMethodName());
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
  }

  @Override
  public void onTestFailedWithTimeout(ITestResult result) {
    System.out.println("MyListener: Test: " + result.getMethod().getMethodName() + " has failed because of timeout.");
  }

  @Override
  public void onStart(ITestContext context) {
    System.out.println("MyListener: Starting: " + context.getName());
  }

  @Override
  public void onFinish(ITestContext context) {
    System.out.println("MyListener: Finishing: " + context.getName());
  }
}
