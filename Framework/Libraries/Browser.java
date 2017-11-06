package Libraries;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.net.MalformedURLException;

public class Browser extends Driver {

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebEdit 
	 * Use 					: Subclass of browser class represents the WebEdit in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 16-April-2017
	--------------------------------------------------------------------------------------------------------*/
	public static class WebEdit {
		public static void Set(String objname, String objvalue) throws IOException {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			Method.setTD(objprop, objvalue);

			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static String gettext(String objname) {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.getval(objprop);
		}

		public static void click(String objname) throws IOException {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static void clear(String objname) throws IOException {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static void Doubleclick(String objname) throws IOException {
			String[] objprop = Utlities.FindObject(objname, "WebEdit");
			String Xpath = objprop[0];
			org.openqa.selenium.WebElement element = cDriver.get().findElement(By.xpath(Xpath));
			((JavascriptExecutor) cDriver.get()).executeScript("arguments[0].scrollIntoView();", element);
			Actions action = new Actions(cDriver.get()).doubleClick(element);
			action.build().perform();
		}

		public static boolean exist(String objname) {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static void waittillvisible(String objname) throws InterruptedException {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);

		}

		public static boolean waitTillEnabled(String objname) throws InterruptedException {
			String objtype = "WebEdit";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebButton 
	 * Use 					: Subclass of browser class represents the WebButton in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebButton {
		public static void click(String objname) throws IOException {
			String objtype = "WebButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static void waittillvisible(String objname) throws InterruptedException {
			String objtype = "WebButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
		}

		public static boolean exist(String objname) {
			String objtype = "WebButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static boolean waitTillEnabled(String objname) throws InterruptedException {
			String objtype = "WebButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebLink 
	 * Use 					: Subclass of browser class represents the WebLink in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebLink {
		public static void click(String objname) throws IOException {
			String objtype = "WebLink";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static void clickL(String objname, int R) throws IOException {
			String[] objprop = Utlities.FindObject(objname, "WebLink");
			String cellXpath = objprop[0] + "//div[" + R + "]//div[1]/a";
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static boolean exist(String objname) {
			String objtype = "WebLink";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static void waittillvisible(String objname) throws InterruptedException, IOException {
			String objtype = "WebLink";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: ListBox 
	 * Use 					: Subclass of browser class represents the ListBox in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class ListBox {
		public static void setdropvalue(String objname, String objvalue) throws IOException {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.setdropvalue(objprop, objvalue);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static void select(String objname, String objvalue) throws IOException {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			Method.selectTD(objprop, objvalue);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static void click(String objname) throws IOException {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static void clear(String objname) throws IOException {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clearTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static String gettext(String objname) {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.getval(objprop);
		}

		public static boolean exist(String objname) {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static boolean waitTillEnabled(String objname) throws InterruptedException {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}

		public static void waittillvisible(String objname) throws InterruptedException {
			String objtype = "ListBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebElement 
	 * Use 					: Subclass of browser class represents the WebElement in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebElement {
		public static void click(String objname) throws IOException {
			String objtype = "WebElement";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static void waittillvisible(String objname) throws InterruptedException, IOException {
			String objtype = "WebElement";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static boolean exist(String objname) {
			String objtype = "WebElement";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}

		public static boolean waitTillEnabled(String objname) throws InterruptedException {
			String objtype = "WebElement";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}

		public static void select(String objname, String objvalue) throws IOException {
			String objtype = "WebElement";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.selectTD(objprop, objvalue);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebRadioButton 
	 * Use 					: Subclass of browser class represents the WebRadioButton in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebRadioButton {
		public static void click(String objname) throws IOException {
			String objtype = "RadioButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}

		public static boolean exist(String objname) {
			String objtype = "RadioButton";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebCheckBox 
	 * Use 					: Subclass of browser class represents the WebRadioButton in the application and 
	 * 						  contains functions for all the operations performed on web edit   
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebCheckBox {
		public static void click(String objname) throws IOException {
			String objtype = "CheckBox";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.clickTD(objprop);
			if (Continue.get() == false) {
				Result.fUpdateLog("Failed at : " + objname);
			}
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: WebTable 
	 * Use 					: Subclass of browser class represents the WebTabel in the application and 
	 * 						  contains functions for all the operations performed on Web Table 
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class WebTable {
		/*------------------------------------------------------------------------------------------------------
		* Function Name: getRowCount
		* Use :	returns the total number of rows in the webtable
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		@SuppressWarnings("null")
		public static int getRowCount(String objname) {
			try {

				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr";
				List<org.openqa.selenium.WebElement> rows = cDriver.get().findElements(By.xpath(cellXpath));
				int rowcount = rows.size();
				return rowcount;
			} catch (Exception e) {
				return (Integer) null;
			}
		}

		public static boolean exist(String objname) {
			String objtype = "WebTable";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.existobj(objprop);
		}
		
		public static boolean waitTillEnabled(String objname) throws InterruptedException {
			String objtype = "WebTable";
			String[] objprop = Utlities.FindObject(objname, objtype);
			return Method.Methodwaittillenabled(objprop);
		}
		
		/*------------------------------------------------------------------------------------------------------
		* Function Name: getCellData
		* Use :	returns the value in the given row and column of the web table
		* Designed By: AG
		* Modified By: Vinodhini
		* Last Modified Date : 13-Feb-2017
		--------------------------------------------------------------------------------------------------------*/
		public static String getCellData(String objname, int rownum, int columnnum) {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");

				String cellXpath = objprop[0] + "//tr[" + rownum + "]" + "//td[" + columnnum + "]";
				String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText();

				return celldata;
			} catch (Exception e) {
				return "";
			}
		}
		
		public static String getCellData_title(String objname, int rownum, int columnnum) {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");

				String cellXpath = objprop[0] + "//tr[" + rownum + "]" + "//td[" + columnnum + "]";
				String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("title");

				return celldata;
			} catch (Exception e) {
				return "";
			}
		}

		public static String CellData(String objname, int rownum)
		{
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String celldata = "";
				int a = Browser.WebTable.getColCount(objname);
				System.out.println(a);
				for (int columnnum = 1; columnnum >= a; columnnum++) {
					String cellXpath = objprop[0] + "//tr[" + rownum + "]" + "//td[" + columnnum + "]";
					celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText();
					System.out.println(celldata);
				}
				return celldata;
			} catch (Exception e) {
				return "";
			}
		}

		public static boolean DataSearch(String objname, String Data) {
			boolean status = true;
			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpath = objprop[0] + "/tr/td";
			System.out.println(cellXpath);
			List<org.openqa.selenium.WebElement> celldata = cDriver.get().findElements(By.xpath(cellXpath));
			for (org.openqa.selenium.WebElement data : celldata) {
				System.out.println(data.getText());
				if (data.getText().toString().equalsIgnoreCase(Data)) {

					status = true;

					System.out.println("True");
				}

				else {

					status = false;

				}

			}
			return status;

		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: click
		* Use :	Clicks the given row and column of the webtable
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static void click(String objname, int rownum, int columnnum) {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
			} catch (Exception e) {

			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: clickL
		* Use :	Clicks the given row and column of the webtable
		* Designed By: Vinodhini
		* Last Modified Date : 07-March-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void clickL(String objname, int rownum, int columnnum) {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + (columnnum+1) + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//a";
				cDriver.get().findElement(By.xpath(cellXpath1)).click();
			} catch (Exception e) {

			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: getColCount
		* Use : get the column count of the given web table
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		@SuppressWarnings("null")
		public static int getColCount(String objname) {
			try {

				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[1]//td";
				List<org.openqa.selenium.WebElement> cols = cDriver.get().findElements(By.xpath(cellXpath));
				int colcount = cols.size();
				return colcount;
			} catch (Exception e) {
				return ((Integer) null);
			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: getColumnname
		* Use : get the column Name for the row and column
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static String getColumnname(String objname, int rownum, int columnnum) {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//th[" + columnnum + "]";
				String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText();
				return celldata;
			} catch (Exception e) {
				return null;
			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: waittillvisible
		* Use : Waits till the web table is visible
		* Designed By: AG
		* Last Modified Date : 15-June-2016
		--------------------------------------------------------------------------------------------------------*/
		public static void waittillvisible(String objname) throws InterruptedException {
			String objtype = "WebTable";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);
		}

		public static void SetTableData(String objname) throws InterruptedException {
			String objtype = "WebTable";
			String[] objprop = Utlities.FindObject(objname, objtype);
			Method.waittillobjvisible(objprop);

		}

		public static void clickT(String objname) {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				cDriver.get().findElement(By.xpath(objprop[0])).click();
				// cDriver.get().findElement(By.xpath(objprop[0])).click();
			} catch (Exception e) {

			}
		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: SetDataE
		* Use : Sets the Specified value to the cell
		* Designed By: Vinodhini
		* Last Modified Date : 13-January-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void SetDataE(String objname, int rownum, int columnnum, String obj, String Val) {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//input[@name='" + obj
						+ "']";
				cDriver.get().findElement(By.xpath(cellXpath1)).clear();
				String vis = "false";
				int countval = 1;
				while (vis == "false" || countval < 10000)
					if (cDriver.get().findElement(By.xpath(cellXpath1)).isDisplayed()) {
						cDriver.get().findElement(By.xpath(cellXpath1)).sendKeys(Val);
						vis = "true";
						countval = 10000;
					} else {
						countval++;
						Thread.sleep(10);
					}
			} catch (Exception e) {
				System.out.println("Object Not Found");
			}

		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: SetData
		* Use : Sets the Specified value to the cell
		* Designed By: Vinodhini
		* Last Modified Date : 13-January-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void SetData(String objname, int rownum, int columnnum, String obj, String Val) {
			try {
				String[] objprop = Utlities.FindObject(objname, "WebTable");
				String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
				cDriver.get().findElement(By.xpath(cellXpath)).click();
				String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//input[@name='" + obj
						+ "']";
				cDriver.get().findElement(By.xpath(cellXpath1)).clear();
				String vis = "false";
				int countval = 1;
				while (vis == "false" || countval < 10000)
					if (cDriver.get().findElement(By.xpath(cellXpath1)).isDisplayed()) {
						cDriver.get().findElement(By.xpath(cellXpath1)).sendKeys(Val);
						cDriver.get().findElement(By.xpath(cellXpath1)).sendKeys(Keys.ENTER);
						vis = "true";
						countval = 10000;
					} else {
						countval++;
						Thread.sleep(10);
					}
			} catch (Exception e) {
				System.out.println("Object Not Found");
			}

		}

		/*------------------------------------------------------------------------------------------------------
		* Function Name: Check
		* Use : Sets the Specified value to the cell
		* Designed By: Vinodhini
		* Last Modified Date : 7-March-2017
		--------------------------------------------------------------------------------------------------------*/
		public static void Check(String objname, int rownum, int columnnum, String val) {

			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			String cellXpath1 = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//option[@value='" + val
					+ "']";
			cDriver.get().findElement(By.xpath(cellXpath1)).click();

		}

		public static void Link(String objname, int rownum, int columnnum) {

			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]/a";
			cDriver.get().findElement(By.xpath(cellXpath)).click();

			// cDriver.get().findElement(By.xpath(cellXpath)).

		}

		public static void Expand(String objname, int rownum, int columnnum) {

			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
			cDriver.get().findElement(By.xpath(cellXpathX)).click();
			String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]/div/div";
			cDriver.get().findElement(By.xpath(cellXpath)).click();

		}

		public static void Popup(String objname, int rownum, int columnnum) {

			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
			cDriver.get().findElement(By.xpath(cellXpathX)).click();
			String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//span";
			cDriver.get().findElement(By.xpath(cellXpath)).click();

		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Class Name			: alert 
	 * Use 					:  class represents the alert in the application and 
	 * 						  contains functions for all the operations performed on alert 
	 * Designed By			: AG
	 * Last Modified Date 	: 25-Apr-2016
	--------------------------------------------------------------------------------------------------------*/
	public static class alert {
		public static void accept() {
			String alertpresent = "false";
			while (alertpresent == "false") {
				try {
					Alert simpleAlert = ((WebDriver) cDriver.get()).switchTo().alert();
					simpleAlert.accept();
					alertpresent = "true";
					break;
				} catch (Exception e) {
					alertpresent = "false";
				}

			}
		}
	}

	/*------------------------------------------------------------------------------------------------------
	* Function Name: OpenBrowser
	* Use : Opens a new browser and resizes it according the number of parallel instances
	* Designed By: AG
	* Last Modified Date : 15-June-2016
	--------------------------------------------------------------------------------------------------------*/
	public static void OpenBrowser(String BrowserName, String URL) throws MalformedURLException, InterruptedException {

		switch (BrowserName.toLowerCase()) {
		case "mobile":
			cDriver.set(new AndroidMobBrow().getNewDriver());
			cDriver.get().get(URL);
			System.out.println("Title " + cDriver.get().getTitle());
			break;
		case "firefox":
			cDriver.set(new DesktopWebBrow().getNewDriver());
			cDriver.get().get(URL);
			Thread.sleep(2000);
			cDriver.get().findElement(By.xpath("//*[@id='advancedButton']")).click();
			cDriver.get().findElement(By.xpath("//*[@id='exceptionDialogButton']")).click();
			Thread.sleep(5000);
			maximize();
			System.out.println("Title " + cDriver.get().getTitle());
			// position();
			break;
		case "chrome":
			cDriver.set(new DesktopWebBrow().getNewDriver());
			cDriver.get().get(URL);
			Thread.sleep(5000);
			maximize();
			System.out.println("Title " + cDriver.get().getTitle());
			// position();
			break;
		case "ie":
			cDriver.set(new DesktopWebBrow().getNewDriver());
			cDriver.get().get(URL);
			Thread.sleep(3000);
			cDriver.get().get("javascript:document.getElementById('overridelink').click();");
			Thread.sleep(3000);
			maximize();
			System.out.println("Title " + cDriver.get().getTitle());
			// position();
			break;

		}

	}

	public static void maximize() {
		cDriver.get().manage().window().maximize();
	}

	public static Boolean Readystate() {
		((JavascriptExecutor) cDriver.get()).executeScript("return document.readyState");
		return true;
	}
	
}