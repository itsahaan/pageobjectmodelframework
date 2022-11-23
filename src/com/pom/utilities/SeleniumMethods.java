package com.pom.utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class SeleniumMethods {

	public StringSelection stringSelection;
	public Clipboard clipboard;
	// Define objects
	protected WebDriver driver;

	// Declare objects
	public SeleniumMethods(WebDriver webdriver) {
		driver = webdriver;
	}

	// Return web driver object
	public WebDriver getWebDriver() {
		return driver;
	}

	// Print message on screen
	public void log(String logMsg) {
		System.out.println(logMsg);
	}

	// Handle locator type
	public By byLocator(String locator) {
		By result = null;

		if (locator.startsWith("//")) {
			result = By.xpath(locator);
		} else if (locator.startsWith("css=")) {
			result = By.cssSelector(locator.replace("css=", ""));
		}

		else if (locator.startsWith("tag")) {
			result = By.tagName(locator.replace("tag", ""));
		}

		else if (locator.startsWith("#")) {
			result = By.id(locator.replace("#", ""));
		} else if (locator.startsWith("link=")) {
			result = By.linkText(locator.replace("link=", ""));
		} else if (locator.startsWith("id=")) {
			result = By.id(locator.replace("id=", ""));
		} else {
			result = By.xpath(locator);
		}
		return result;
	}

	// Assert element present
	public Boolean isElementPresent(String locator) {
		Boolean result = false;
		try {
			getWebDriver().findElement(byLocator(locator));
			result = true;
		} catch (Exception ex) {
		}
		return result;
	}

	// Wait for element present
	public void waitForElementPresent(String locator, int timeout) {
		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Handle click action
	public void clickOn(String locator) {
		this.waitForElementPresent(locator, 30);
		// waitForWorkAroundTime(4000);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		el.click();
	}

	// Handle send keys action
	public void sendKeys(String locator, String str) {
		this.waitForElementPresent(locator, 30);
		// waitForWorkAroundTime(4000);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		// el.sendKeys(Keys.CONTROL + "a");
		// el.sendKeys(Keys.DELETE);
		el.clear();
		el.sendKeys(str);
	}

	// Store text from a locatorl
	public String getText(String locator) {
		waitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		String text = getWebDriver().findElement(byLocator(locator)).getText();
		return text;
	}

	// Get attribute value
	public String getAttribute(String locator, String attribute) {
		waitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		String text = getWebDriver().findElement(byLocator(locator)).getAttribute(attribute);
		return text;
	}

	public Integer getXpathCount(String locator) {
		waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		int a = driver.findElements(By.xpath(locator)).size();
		return a;
	}

	public void waitForWorkAroundTime(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void verifyTitle(String title) {
		waitForWorkAroundTime(4000);
		String actualTitle = getWebDriver().getTitle();
		Assert.assertTrue(actualTitle.contains(title));
	}

	public void mouseHover(String locator) {
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		Actions action = new Actions(getWebDriver());
		action.moveToElement(el).build().perform();
	}

	public void selectByText(String locator, String text) {
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		Select select = new Select(el);
		select.selectByVisibleText(text);
	}

	public void typeKeys(String locator, Keys key) {
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		el.sendKeys(key);
	}

	public void verticalScroll(int val) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,100)");
	}

	public void removeAttribute(String locator, String attribute) {
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		List<WebElement> inputs = driver.findElements(byLocator(locator));

		for (WebElement input : inputs) {
			((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attribute + "')", input);
		}
	}

	public void dropDown(String locator, String str) {
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		Select drp = new Select(driver.findElement(byLocator(locator)));
		drp.selectByVisibleText(str);

	}

	public void rightClick(String locator) {
		Actions actions = new Actions(driver);
		WebElement elementLocator = driver.findElement(byLocator(locator));
		actions.contextClick(elementLocator).perform();
	}

	public void switchToFrame(String frameName) {
		waitForWorkAroundTime(2000);
		driver.switchTo().frame(frameName);
		waitForWorkAroundTime(4000);
	}

	public void uploadFileWithRobotClass(String filePath) {
		waitForWorkAroundTime(4000);
		stringSelection = new StringSelection(filePath);
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(150);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public void startDatePicker(String locator) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		// Integer to String Conversion
		String today = Integer.toString(todayInt);

		// This is from date picker table
		this.waitForElementPresent(locator, 30);
		waitForWorkAroundTime(4000);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		// This are the columns of the from date picker table
		List<WebElement> columns = el.findElements(By.tagName("td"));

		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		for (WebElement cell : columns) {
			/*
			 * //If you want to click 18th Date if (cell.getText().equals("18")) {
			 */
			// Select Today's Date
			if (cell.getText().equals(today)) {
				cell.click();
				break;
			}
		}

		// Wait for 4 Seconds to see Today's date selected.
		waitForWorkAroundTime(4000);

	}

	public void endDatePicker(String locator) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		todayInt = todayInt + 1;
		// Integer to String Conversion
		String today = Integer.toString(todayInt);
		// This is from date picker table
		this.waitForElementPresent(locator, 30);
		waitForWorkAroundTime(10000);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		// This are the columns of the from date picker table
		List<WebElement> columns = el.findElements(By.tagName("td"));

		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		for (WebElement cell : columns) {
			/*
			 * //If you want to click 18th Date if (cell.getText().equals("18")) {
			 */
			// Select Today's Date
			if (cell.getText().equals(today)) {
				cell.click();
				break;
			}
		}

		// Wait for 4 Seconds to see Today's date selected.
		waitForWorkAroundTime(4000);

	}

	public void switchBetweenTabs(int i) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		// below code will switch to new tab
		/*
		 * driver.switchTo().window(tabs.get(1)); //perform whatever actions you want in
		 * new tab then close it driver.close(); //Switch back to your original tab
		 */
		driver.switchTo().window(tabs.get(i));
	}

	public void sendKeysWithActionClass(String locator, String str) {
		this.waitForElementPresent(locator, 30);
		waitForWorkAroundTime(4000);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		Actions actions = new Actions(driver);
		el.clear();
		actions.sendKeys(el, str).perform();
	}

	public void switchToFrameWithWebElement(String locator) {
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		driver.switchTo().frame(el);
		waitForWorkAroundTime(4000);
	}

	public void verifyText(String locator, String str) {
		waitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		String text = getWebDriver().findElement(byLocator(locator)).getText();
		Assert.assertTrue(text.contains(str));
	}

	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	public void changeAttributeValue(String locator, String attribute) {
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement w1 = driver.findElement(byLocator(locator));
		((JavascriptExecutor) driver).executeScript("arguments[0]." + attribute + ".display = 'block';", w1);
	}

	public void navigateURL(String url) {
		driver.navigate().to(url);
	}

	public void verticalScrollUp(int val) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-500)");
	}

}