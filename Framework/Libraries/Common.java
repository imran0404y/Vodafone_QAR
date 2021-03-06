package Libraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.CharMatcher;

public class Common extends Driver {

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: waitforload
	 * Use 					: It waits for the page to Load
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public void waitforload() {
		try {
			cDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(2000);
		} catch (Exception e) {
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Order_ID
	 * Use 					: To fetch order value
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 28-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Order_ID() {
		String[] objprop = Utlities.FindObject("Order_id", "WebTable");
		String cellXpath = objprop[0];
		String OD = cDriver.get().findElement(By.xpath(cellXpath)).getText();
		return OD;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: waitmoreforload
	 * Use 					: It waits for the page to Load
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public void waitmoreforload() {
		try {
			cDriver.get().manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
			Thread.sleep(20000);

		} catch (Exception e) {
		}
	}

	public void ToWait() {
		try {
			int i = 0;
			cDriver.get().manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
			i = i * 60 * 1000;
			Thread.sleep(i);
		} catch (Exception e) {
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: waitforobj
	 * Use 					: It waits for the obj to be loaded
	 * Arguments			: Object for which script waits
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public void waitforobj(String obj, String obj1) {
		try {
			int time = 1;
			if (obj1.equals("WebButton"))
				while (Browser.WebButton.exist(obj) == false) {
					Thread.sleep(2000);
					time++;
					if (Browser.WebButton.exist(obj) == true)
						break;
					if (time > 40)
						break;
				}
			if (obj1.equals("WebLink"))
				while (Browser.WebLink.exist(obj) == false) {
					Thread.sleep(2000);
					time++;
					if (Browser.WebLink.exist(obj) == true)
						break;
					if (time > 40)
						break;
				}
			if (obj1.equals("WebEdit"))
				while (Browser.WebEdit.exist(obj) == false) {
					Thread.sleep(2000);
					time++;
					if (Browser.WebEdit.exist(obj) == true)
						break;
					if (time > 40)
						break;
				}
			if (time > 40)
				Continue.set(false);
		} catch (Exception e) {
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: scroll
	 * Arguments			: Object to where the script has to scroll
	 * Use 					: To scroll into  specific object
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public void scroll(String objname, String ObjTyp) {
		try {
			String[] objprop = Utlities.FindObject(objname, ObjTyp);
			WebElement scr1 = cDriver.get().findElement(By.xpath(objprop[0]));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			Thread.sleep(1500);
		} catch (Exception E) {
			Continue.set(false);
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Select_Cell
	 * Use 					: To get a Particular Column Value with the Column Name
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Select_Cell(String objname, String objTyp) {
		int Col = 1;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = "//table//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElements(By.xpath(cellXpath)).get(0).getText();
			if (celldata.toLowerCase().contains(Expected.toLowerCase()))
				break;
		}
		return Col;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: isAlertExist
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 24-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	public boolean isAlertExist() {
		try {
			WebDriverWait wait = new WebDriverWait(cDriver.get(), 15);
			if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
				String popup = cDriver.get().switchTo().alert().getText();
				Result.fUpdateLog(popup);
				if (Validatedata("SmartLimit").equalsIgnoreCase("yes")) {
					String theDigits = CharMatcher.DIGIT.retainFrom(popup);
					Def_Smart_limit.set(theDigits);
				}
			}
			Browser.alert.accept();
			Browser.Readystate();
			return true;
		} catch (Exception e) {
			Result.fUpdateLog("No Alert Exist");
			e.getMessage();
			return false;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Col_Val
	 * Use 					: To get the specific Col
	 * Arguments			: Object for which script waits
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 19-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Get_Col(String objname, int rownum, String Expected) {
		int Col = 0, Col_Length = Browser.WebTable.getColCount(objname);
		for (int i = 1; i <= Col_Length; i++)
			if (Browser.WebTable.getCellData(objname, rownum, i).equalsIgnoreCase(Expected)) {
				Col = i;
				break;
			}

		return Col;

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Link_Select
	 * Use 					: To select a Link containing Specific text
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 12-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Link_Select(String Text) {
		String[] objprop = Utlities.FindObject("Link", "WebTable");
		String cellXpath = objprop[0] + Text + "']";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		cDriver.get().findElement(By.xpath(cellXpath)).click();
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Text_Select
	 * Use 					: To select a Link containing Specific text
	 * Designed By			: Sravani
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Text_Select(String Tag, String Text) {
		String cellXpath = "//" + Tag + "[text()='" + Text + "']";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		waitforload();
		cDriver.get().findElement(By.xpath(cellXpath)).click();
	}// div option button span

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Tag_Select
	 * Use 					: To scroll to the particular tag item
	 * Designed By			: Sravani
	 * Last Modified Date 	: 18-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Tag_Select(String Tag, String Text) {
		String cellXpath = "//" + Tag + "[text()='" + Text + "']";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Radio_Select
	 * Use 					: To select a spectific Radio Button or check box
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 12-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Radio_Select(String Text) {

		String cellXpath = "//input[@value='" + Text + "']";

		if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()) {
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			// break;}

		} else
			Continue.set(false);
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Radio_None
	 * Use 					: To select a spectific None Radio Button or to uncheck the check box
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 12-March-2017
	 *--------------------------------------------------------------------------------------------------------*/
	public void Radio_None(String Text) {
		List<WebElement> elements = cDriver.get().findElements(
				By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[@class='siebui-ecfg-feature-group']"));
		int Size = elements.size();
		System.out.println(Size);
		boolean flag = false;
		waitforload();
		for (int i = 1; i <= Size; i++) {
			List<WebElement> cellXpath = cDriver.get()
					.findElements(By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[" + i
							+ "]//div[1]//table//div[1]//div[1]//input"));
			waitforload();
			for (int t = 1; t < cellXpath.size(); t++) {
				if (cellXpath.get(t).getAttribute("value").equals(Text)) {
					if (cellXpath.get(t).getAttribute("type").equals("radio")) {
						// Radio Button
						((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
								cellXpath.get(0));
						waitforload();
						cellXpath.get(0).click();
						flag = true;
						break;
					} else {
						// Check box
						((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
								cellXpath.get(t));
						waitforload();
						cellXpath.get(t).click();
						flag = true;
						break;
					}
				}
			}

			if (flag) {
				break;
			}

		}

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AddOnSelection
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 9-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public void AddOnSelection(String Product, String Status) {
		try {
			int Length;
			String Product_Tabs[] = Product.split("<>");
			for (int i = 0; i < Product_Tabs.length; i++) {
				String Prod_array[] = Product_Tabs[i].split(",");
				Length = Prod_array.length;
				System.out.println(Length);
				if (Length > 1) {
					Thread.sleep(3000);
					Link_Select(Prod_array[0]);

					if (Status.equals("Delete")) {
						Result.fUpdateLog("------Modify Remove Addon Event Details------");
						for (int j = 1; j < Prod_array.length; j++)
							Radio_None(Prod_array[j]);
						Result.takescreenshot("Deletion of Addon");
					} else {
						Result.fUpdateLog("------Modify Add Addon Event Details------");
						for (int j = 1; j < Prod_array.length; j++)
							Radio_Select(Prod_array[j]);
						Result.takescreenshot("Addition of Addon");
					}

				}
			}
		} catch (Exception e) {

		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Col_Data
	 * Use 					: To GET DATA of specific Coloumn Header
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 12-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Col_Data(int i) {
		String cellXpath = "//table//th[" + i + "]";
		String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText().trim();
		return celldata;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Actual_Cell
	 * Use 					: To get a Particular Column Value with the Column Name
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 19-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Actual_Cell(String objname, String objTyp) {
		int Col = 1, f = 0;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		waitforload();
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = "//table//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText().trim();
			if (celldata.equalsIgnoreCase(Expected))
				f = f + 1;
			if (f == 1)
				break;

		}
		return Col;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: ParentAssertSearch
	 * Use 					: Searching based on Input to decide whether to move on Account Search or Asert Search
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 29-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void ParentAssertSearch(String Reference, String Status, String Prod) {
		try {
			if (Reference.substring(0, 3).equals("974")) {
				Assert_Search(Reference, Status);
				waitforload();
				Text_Select("a", Prod);
			} else
				Account_Search(Reference);

		} catch (Exception e) {
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Account Search
	 * Use 					: To send combination of keys 
	 * Args					: Account
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 29-October-2017
	--------------------------------------------------------------------------------------------------------*/

	public void Account_Search(String AccountNo) {
		try {
			int Row;
			Browser.WebLink.click("VQ_Account");
			Link_Select("All Accounts");
			waitforload();
			Browser.WebButton.click("Account_Query");
			Webtable_Value("Account #", AccountNo);
			/*
			 * Col = Select_Cell("Account", "Account #"); Row =
			 * Browser.WebTable.getRowCount("Account"); if (Row == 2)
			 * Browser.WebTable.SetData("Account", 2, Col, "Account_Number", AccountNo);
			 * else Continue.set(false);
			 */
			Browser.WebButton.click("Account_Go");
			waitforload();
			Row = Browser.WebTable.getRowCount("Account");
			if (Row == 2) {
				Browser.WebButton.click("Account360");
				waitforload();
				Browser.WebLink.waittillvisible("Acc_Portal");
				waitforload();
				Browser.WebLink.click("Acc_Portal");
			} else
				Continue.set(false);

		} catch (Exception e) {
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Assert_Search
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 7-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Assert_Search(String MSISDN, String Status) {
		try {
			Result.fUpdateLog("MSISDN : " + MSISDN);
			waitforload();
			int Row = 2, Col;
			Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			Browser.WebLink.waittillvisible("Assert_Search");
			waitforload();
			scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			waitforload();

			// Installed_Assert
			Col = Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			waitforload();
			Col = Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);
			Browser.WebLink.waittillvisible("Acc_Portal");
			waitforload();
			Browser.WebLink.click("Acc_Portal");
			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
			Result.takescreenshot("");
			// Browser.WebLink.click("Inst_Assert_ShowMore");
			waitforload();
			InstalledAssertChange("New Query                   [Alt+Q]");
			Col = Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			waitforload();
			// Browser.WebTable.Expand("Installed_Assert", i, 1);
			Result.takescreenshot("");
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ResumeDate(String objname, int rownum, int columnnum) {
		try {
			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpath = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]";
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			String cellXpath1 = objprop[0] + "//tr[" + rownum + "]/td[" + columnnum + "]//span";
			cDriver.get().findElement(By.xpath(cellXpath1)).click();
			Text_Select("button", "Now");
			Text_Select("button", "Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: InstalledAssertChange
	 * Use 					: Customizing the specific Plan is done
	 * Designed By			: Vinodhini
	 * Last Modified Date 	: 16-Feb-2017
	--------------------------------------------------------------------------------------------------------*/
	public void InstalledAssertChange(String Text) {
		try {
			if (Browser.WebButton.exist("Installed_Assert_Menu")) {
				scroll("Installed_Assert_Menu", "WebButton");
				Browser.WebButton.click("Installed_Assert_Menu");
			} else {
				scroll("Prod_Serv_Menu", "WebButton");
				Browser.WebButton.click("Prod_Serv_Menu");
			}

			String[] objprop = Utlities.FindObject("Menu_Selection", "WebButton");
			String cellXpath = objprop[0] + Text + "']";
			if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()) {
				WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
				((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
				cDriver.get().findElement(By.xpath(cellXpath)).click();
			} else
				Continue.set(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Category_Select
	 * Use 					: To select the plan from catlog view
	 * Designed By			: Sravani
	 * Last Modified Date 	: 18-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Category_Select(String text, String text1, String text2) {
		try {
			String cellXpath, cellXpath1, cellXpath2 = null, cellXpath3, TxtVal, TxtVal1;
			String[] objprop = Utlities.FindObject("Category_Plan", "WebTable");
			int rowcount = 7;// object need to add to get row count
			for (int li_N = 1; li_N <= rowcount; li_N++) {
				cellXpath = objprop[0] + "[" + li_N + "]//a";
				TxtVal = cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("text");
				if (TxtVal.contains(text)) {
					cellXpath1 = objprop[0] + "[" + li_N + "]//i[1]";
					cDriver.get().findElement(By.xpath(cellXpath1)).click();
					if (text1 != "") {
						int rowcount1 = 7;
						for (int li_N1 = 1; li_N1 <= rowcount1; li_N1++) {
							cellXpath3 = objprop[0] + "[" + li_N + "]//ul//li[" + li_N1 + "]//a";
							TxtVal1 = cDriver.get().findElement(By.xpath(cellXpath3)).getAttribute("text");
							if (TxtVal1.contains(text1)) {
								cellXpath2 = objprop[0] + "[" + li_N + "]//ul//li[" + li_N1 + "]//i[1]";
								cDriver.get().findElement(By.xpath(cellXpath2)).click();
								Select_plan(text2, TxtVal1);
								break;
							}

						}

					} else {

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Category_Select(String text, String text1) {
		try {
			String cellXpath, cellXpath1, cellXpathD, TxtVal, Txt = "";
			String[] objprop = Utlities.FindObject("Category_Plan", "WebTable");
			Thread.sleep(4000);
			for (int li_N = 1; li_N <= 6; li_N++) {
				cellXpath = objprop[0] + "[" + li_N + "]//a";// text;+"']";
				TxtVal = cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("text");
				if (TxtVal.contains(text)) {
					cellXpath1 = objprop[0] + "[" + li_N + "]//i[1]";// text;+"']";
					Utlities.cDriver.get().findElement(By.xpath(cellXpath1)).click();
					cellXpathD = objprop[0] + "[" + li_N + "]//ul//li[2]//a";// text;+"']";
					Txt = cDriver.get().findElement(By.xpath(cellXpathD)).getAttribute("text");
					if (Txt.contains(text1))
						cDriver.get().findElement(By.xpath(cellXpathD)).click();
					break;
				}

			}
		}

		catch (Exception e) {
		}
	}

	public void Plan_Select(String obj, String Val) {

		String[] objprop = Utlities.FindObject(obj, "WebEdit");
		cDriver.get().findElement(By.xpath(objprop[0])).sendKeys(Val);
		cDriver.get().findElement(By.xpath(objprop[0])).sendKeys(Keys.ENTER);

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Select_plan
	 * Use 					: To select the plan from catlog view
	 * Designed By			: Sravani
	 * Last Modified Date 	: 18-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Select_plan(String Text, String Plan) {
		String cellXpath, cellXpath1, cellXpath2, TxtVal, tct1;
		String[] objprop = Utlities.FindObject("VQ_Plan", "WebLink");
		int x;
		try {
			waitforload();
			x = Integer.parseInt(Plan.substring(Plan.indexOf("(") + 1, Plan.indexOf(")")).trim());
			for (int li_N = 1; li_N <= x; li_N++) {
				cellXpath = objprop[0] + "[" + li_N + "]/div[1]/div[1]//a";
				TxtVal = cDriver.get().findElement(By.xpath(cellXpath)).getAttribute("text");
				Tag_Select("a", TxtVal);

				if (TxtVal.contains(Text)) {

					cellXpath1 = "//a[text()='" + Text
							+ "']/../../../../div[3]//button[@title='Category Products:Add Item']";
					cDriver.get().findElement(By.xpath(cellXpath1)).click();

				} else {
					if ((li_N % 8) == 0) {
						int li = li_N;
						Tag_Select("a", TxtVal);
						Browser.WebButton.click("VQ_Roll_Down");
						cellXpath2 = objprop[0] + "[" + (li + 1) + "]/div[1]/div[1]//a";// text;+"']";
						tct1 = cDriver.get().findElement(By.xpath(cellXpath2)).getAttribute("text");
						Tag_Select("a", tct1);

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Action_Update
	 * Use 					: To check the action status in line items
	 * Designed By			: Sravani
	 * Last Modified Date 	: 18-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Action_Update(String Text, String MSISDN) {
		int Row_Count1, Col, Col_P, Col1;
		LineItemData.clear();
		try {
			Col = Select_Cell("Line_Items", "Product");
			Col_P = Actual_Cell("Line_Items", "Action");
			Col1 = Select_Cell("Line_Items", "Service Id");
			/*
			 * Field = Col_Data(Col1); if (Field.equalsIgnoreCase("previous service id"))
			 * Col_SID = Actual_Cell("Line_Items", "Service Id");
			 */
			int k = 0;
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				String Msd = Browser.WebTable.getCellData("Line_Items", i, Col1);
				if (Msd.equals(MSISDN) || LData.equals("SIM Card")) {
					if (Action.equals(Text)) {
						LineItemData.put(Integer.toString(k), LData);
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
						k++;
					} else {
						Result.fUpdateLog(LData + ": Failed at fetching data from Action " + Action);
						Continue.set(false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LineItems_Data() {
		int Row_Count1, Col, Col_P;
		LineItemData.clear();
		try {
			Col = Select_Cell("Line_Items", "Product");
			Col_P = Actual_Cell("Line_Items", "Action");
			int k = 0;
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");

			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				if (Action.equals("Add") || Action.equals("Update")) {
					LineItemData.put(Integer.toString(k), LData);
					Result.fUpdateLog("Action Update : " + LData + ":" + Action);
					k++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: RTBScreen
	 * Use 					: To check the Unbilled usage in Billing profile
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 3-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void RTBScreen(String MSISDN, String Status) {
		try {
			waitforload();
			int Row = 2, Col;
			/*
			 * if (Browser.WebButton.exist("VFQ_LeftScroll")) {
			 * Browser.WebButton.click("VFQ_LeftScroll"); }
			 */
			Title_Select("a", "Home");
			waitforload();
			Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			waitforload();
			Col = Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			waitforload();
			Col = Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);
			Browser.WebLink.waittillvisible("Acc_Portal");
			waitforload();
			Browser.WebLink.click("Acc_Portal");
			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
			InstalledAssertChange("New Query                   [Alt+Q]");
			Col = Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");

			int Col1 = Select_Cell("Installed_Assert", "Billing Profile");
			String BP = Browser.WebTable.getCellData("Installed_Assert", 2, Col1);
			waitforload();

			scroll("Profile_Tab", "WebButton");
			Browser.WebButton.click("Profile_Tab");
			waitforload();
			int Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			int Col_Val = Select_Cell("Bill_Prof", "Name");
			for (int i = 2; i <= Row_Count; i++) {
				String BillPro = Browser.WebTable.getCellData("Bill_Prof", i, Col_Val);
				if (BillPro.equals(BP)) {
					Browser.WebTable.click("Bill_Prof", i, Col_Val);
					do {
						waitforload();
					} while (!Browser.WebButton.waitTillEnabled("Bill_Valid_Name"));

					break;
				}
			}
			// Text_Select("a", "Unbilled Usage");
			waitforload();
			do {
				TabNavigator("Real Time Balance");
				waitforload();
			} while (!Browser.WebButton.waitTillEnabled("RTB_Valid_Name"));
			Browser.WebButton.waittillvisible("RTB_Valid_Name");
			scroll("RTB_Valid_Name", "WebButton");
			Result.takescreenshot("Real Time Balance");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AssertSearch(String MSISDN, String Status) {
		try {
			waitforload();
			int Row = 2, Col;
			Title_Select("a", "Home");
			waitforload();
			Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			waitforload();
			Col = Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", Status);
			Col = Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			waitforload();
			Result.takescreenshot("Account Status : " + Status);
			Col = Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);
			Browser.WebLink.waittillvisible("Acc_Portal");
			waitforload();
			Browser.WebLink.click("Acc_Portal");
			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Webtable_Value
	 * Use 					: To fetch the value from a table having SPAN and Input tag
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 3-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Webtable_Value(String Text, String Val) {

		String cellXpath = "//span[text()='" + Text + "']/../../following-sibling::td[1]//input";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		cDriver.get().findElement(By.xpath(cellXpath)).clear();
		cDriver.get().findElement(By.xpath(cellXpath)).sendKeys(Val);

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Status
	 * Use 					: To get the status After modifying the plan
	 * Designed By			: Sravani
	 * Last Modified Date 	: 02-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Status(String Addon) {
		int Col, Col_P, Row_Count, Length;

		try {
			Col = Select_Cell("Line_Items", "Product");
			Col_P = Actual_Cell("Line_Items", "Action");
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			String Product_Tabs[] = Addon.split("<>");
			for (int j = 0; j < Product_Tabs.length; j++) {
				String Prod_array[] = Product_Tabs[j].split(",");
				Length = Prod_array.length;
				System.out.println(Length);
				int k = 0;
				if (Length > 1) {
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						for (int a = 1; a < Prod_array.length; a++) {
							String Sdata = Prod_array[a];
							if (Sdata.equals(LData)) {
								String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
								if (Action.equals("Add")) {
									LineItemData.put(Integer.toString(k), LData);
									Result.fUpdateLog("Status  updated sucuessfully");
									Result.fUpdateLog("Add Event is Successfull");
									k = k + 1;
								} else if (Action.equals("Delete")) {
									Result.fUpdateLog("Status  updated sucuessfully");
									Result.fUpdateLog("Deletion Event is Successfull");
								} else {
									Continue.set(false);
									Result.fUpdateLog("Event is UnSuccessfull");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: readSoapMessage
	 * Use 					: To read the soap response
	 * Designed By			: Lavannya
	 * Last Modified Date 	: 13-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public SOAPMessage readSoapMessage(String filename, String SOAPAction) throws SOAPException, FileNotFoundException {
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		soapPart.setContent(new StreamSource(new FileInputStream(filename)));
		MimeHeaders headers = message.getMimeHeaders();
		headers.addHeader("SOAPAction", SOAPAction);
		message.saveChanges();
		return message;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Setvalue
	 * Use 					: To read the soap response
	 * Designed By			: Lavannya
	 * Last Modified Date 	: 13-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public Document Setvalue(Document doc, String TagName, String val) {
		NodeList nList = doc.getElementsByTagName(TagName);
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				nNode.setTextContent(val);
			}
		}
		return doc;
	}

	public Document SametagSetvalue(Document doc, String Key, String val) {
		NodeList nList = doc.getElementsByTagName("cli:item");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nodes = nList.item(temp);
			NodeList list = nodes.getChildNodes();
			for (int i = 0; i != list.getLength(); i++) {
				Node child = list.item(i);
				if (child.getTextContent().contentEquals(Key)) {
					child = list.item(i + 2);
					if (child.getNodeName().equals("cli:value")) {
						child.getFirstChild().setTextContent(val);
					}
				}
			}
		}
		return doc;
	}

	public Document Setvalue(Document doc, String NodeName, String TagName, String Value) {
		if (NodeName != null) {
			String TagArray[] = TagName.split("&&");
			if (TagArray.length == 2) {
				String ValueArray[] = Value.split("&&");
				NodeList nList = doc.getElementsByTagName(TagArray[0]);
				System.out.println("----------------------------");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						eElement.getElementsByTagName(TagArray[1]).item(0).setTextContent(ValueArray[temp]);
					}
				}
			} else {
				NodeList nList = doc.getElementsByTagName(TagName);
				if (nList.getLength() > 0) {
					nList.item(0).setTextContent(Value);
				}
			}
		}
		return doc;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: getvalue
	 * Use 					: To read the soap response
	 * Designed By			: Lavannya
	 * Last Modified Date 	: 13-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String getvalue(Document doc, String NodeName, String TagName, int i) {
		String ReturnValue = "";
		if (NodeName != null) {
			String TagArray[] = TagName.split("&&");
			if (TagArray.length == 2) {
				NodeList nList = doc.getElementsByTagName(TagArray[0]);
				System.out.println("----------------------------");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						ReturnValue = ReturnValue + eElement.getElementsByTagName(TagArray[1]).item(i).getTextContent()
								+ "||";
					}
				}
			} else {
				NodeList nList = doc.getElementsByTagName(TagName);
				if (nList.getLength() > 0) {
					ReturnValue = nList.item(i).getTextContent();
				}
			}
		}
		return ReturnValue;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: XML_Request
	 * Use 					: Establish SOAP Connection and send request to End Point URL
	 * Designed By			: Imran
	 * Last Modified Date 	: 15-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public SOAPMessage XML_Request(SOAPMessage message, String URL) {
		SOAPMessage soapResponse = null;
		try {
			// Establish SOAP Connection and send request to End Point URL
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			System.out.println("Connection Established");
			soapResponse = soapConnection.call(message, URL);

			// CF.printSOAPResponse(soapResponse);
			soapConnection.close();

		} catch (Exception e) {
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		return soapResponse;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: FindBillingCycle
	 * Use 					: Find the billing cycle based on the activation date
	 * Argument				: OrderSubmissionDate
	 * Designed By			: Vinodhini Raviprasad
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 17-Oct-2017  
	--------------------------------------------------------------------------------------------------------*/
	public String FindBillingCycle() {
		try {
			String billingcycle;
			DateFormat Date_Format = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar cals = Calendar.getInstance();
			int Order_Day, Order_Year, Add_Year;
			String Submission_Date = OrderDate.get();
			// String Submission_Date = "13-11-2017";
			cals.set(Calendar.YEAR, Integer.parseInt(Submission_Date.split("-")[2]));
			cals.set(Calendar.MONTH, Integer.parseInt(Submission_Date.split("-")[1]) - 1);
			cals.set(Calendar.DATE, Integer.parseInt(Submission_Date.split("-")[0]));
			String Order_Month, Add_Month;
			DateFormat Month = new SimpleDateFormat("MMM");
			DateFormat Year = new SimpleDateFormat("yyyy");
			Order_Month = Month.format(cals.getTime());
			Order_Year = Integer.parseInt(Year.format(cals.getTime()));
			Order_Day = Integer.parseInt(Submission_Date.split("-")[0]);
			cals.add(Calendar.MONTH, 1);
			Add_Month = Month.format(cals.getTime());
			Add_Year = Integer.parseInt(Year.format(cals.getTime()));
			cals.add(Calendar.MONTH, -1);
			String dt = billDate.get();
			// String dt = "1";
			if (dt != null) {
				int GetDate = Integer.parseInt(dt);
				if (Order_Day < GetDate)
					billingcycle = GetDate + "-" + Order_Month + "-" + Order_Year;
				else if (Order_Day == GetDate)
					billingcycle = GetDate + "-" + Order_Month + "-" + Order_Year;
				else
					billingcycle = GetDate + "-" + Add_Month + "-" + Add_Year;
			} else {
				Date DD_3 = new Date();
				cals.add(Calendar.DATE, 3);
				DD_3 = Date_Format.parse(Date_Format.format(cals.getTime()));

				Date Date_15 = Date_Format.parse(("15-" + Order_Month + "-" + Order_Year));
				Date Date_1 = Date_Format.parse(("1-" + Add_Month + "-" + Order_Year));

				if (TestCaseN.get().contains("black")) {
					if (Order_Day < 4)
						billingcycle = "07-" + Order_Month + "-" + Order_Year;
					else
						billingcycle = "07-" + Add_Month + "-" + Add_Year;
				} else {
					if (Order_Day < 15)
						if (DD_3.equals(Date_15) || DD_3.after(Date_15))
							billingcycle = "01-" + Add_Month + "-" + Add_Year;
						else
							billingcycle = "15-" + Order_Month + "-" + Order_Year;
					else if (DD_3.equals(Date_1) || DD_3.after(Date_1))
						billingcycle = "15-" + Add_Month + "-" + Add_Year;
					else
						billingcycle = "01-" + Add_Month + "-" + Add_Year;
				}
			}
			return billingcycle;
		} catch (Exception e) {
			return "";
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Prorated
	 * Use 					: To Prorate the Benifit with specified values and conversion units as expected
	 * Arguments			: TotalDays- total days in month, Rmaingdays - balance day after activation, Benifit
	 * Designed By			: Vinodhini Raviprasad
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 17-Oct-2017  
	--------------------------------------------------------------------------------------------------------*/
	public String Prorated(int TotalDays, int Remaingdays, String Benifit) {

		String Units[] = Benifit.split("_"), Unit_VAL, Unit;
		Unit = Units[0];
		double ben = Double.parseDouble(Unit);

		if (Units.length > 1) {
			Unit_VAL = Units[1].toLowerCase();
			switch (Unit_VAL) {
			case "gb":
				ben = ben * 1024 * 1024;
				break;
			case "mb":
				ben = ben * 1024;
				break;
			case "tb":
				ben = ben * 1024 * 1024 * 1024;
				break;
			}
		}

		double Prorateq = (ben * Remaingdays / TotalDays);
		Prorateq = Math.ceil(Prorateq);
		int i = (int) Prorateq;
		return i + "";
	}

	public String Prorated(int TotalDays, int Remaingdays, String Benifit, String BucketValue, String BucketUsageType) {
		double ben = 0;
		String pro = null;
		switch (BucketUsageType) {
		case "Cost":
			ben = Double.parseDouble(BucketValue);
			if (!Benifit.equals("DUMMY")) {
				ben = ben / 100;
			}
			break;
		case "GU":
			ben = Double.parseDouble(BucketValue);
			ben = ben / 600;
			break;
		case "Item":
			ben = Double.parseDouble(BucketValue);
			break;
		case "Time":
			ben = Double.parseDouble(BucketValue);
			ben = ben / 60;
			break;
		case "Volume":
			ben = Double.parseDouble(BucketValue);
			break;
		}
		double Prorateq = (ben * Remaingdays / TotalDays);
		if (Benifit.contains("_Flex")) {
			String x = Benifit.split("_")[0];
			if (!x.equals("0")) {
				Double toBeTruncated = new Double(Prorateq);
				Double truncatedDouble = new BigDecimal(toBeTruncated).setScale(1, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				pro = String.format("%.2f", truncatedDouble);
				System.out.println(String.format("%.2f", truncatedDouble));
			} else {
				int i = (int) Math.ceil(Prorateq);
				pro = i + "";
			}
		} else {
			int i = (int) Math.ceil(Prorateq);
			pro = i + "";
		}
		return pro;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Date1
	 * Arguments			: None
	 * Use 					:To fetch the date from home Browser
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 17-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public void GetSiebelDate() {
		try {
			// Browser.WebButton.waittillvisible("VFQ_LeftScroll");
			// Browser.WebButton.click("VFQ_LeftScroll");

			waitforload();
			Title_Select("a", "Home");
			// Browser.WebLink.click("VQ_Home");
			waitforload();
			String Date = cDriver.get().findElement(By.xpath("//p[@class='vfqa-salutation-date']"))
					.getAttribute("innerHTML");
			String Mon = null;
			String month[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
					"October", "November", "December" };
			String Datefor[] = Date.split(",");
			String Dateform[] = Datefor[1].trim().split(" ");
			String Dateforma[] = Datefor[2].trim().split("\\.");
			for (int i = 1; i <= 12; i++) {
				if (month[i - 1].equals(Dateform[0])) {
					Mon = Integer.toString(i);
					break;
				}
			}
			OrderDate.set(Dateform[1] + "-" + Mon + "-" + Dateforma[0]);

		} catch (Exception e) {
			Utlities.StoreValue("Order_Creation_Date", OrderDate.get());

			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Title_Select
	 * Use 					: To select a Link containing Specific text
	 * Designed By			: Sravani
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Title_Select(String Tag, String Text) {
		String cellXpath = "//" + Tag + "[@title='" + Text + "']";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		waitforload();
		cDriver.get().findElement(By.xpath(cellXpath)).click();
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PopupHeader
	 * Use 					: To get a Particular Column Value with the Column Name
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 31-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public int PopupHeader(String objname, String objTyp) {
		int Col = 1;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = "//div[@class='AppletStylePopup']//table[@class='ui-jqgrid-htable']//th[" + i + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			String celldata = cDriver.get().findElements(By.xpath(cellXpath)).get(0).getText().trim();
			if (celldata.equalsIgnoreCase(Expected))
				break;
		}
		return Col;
	}

	public void Popup_Data(String objname, int rownum, int columnnum, String Variable, String val) {
		try {
			String[] objprop = Utlities.FindObject(objname, "WebTable");
			String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
			WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpathX));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
			cDriver.get().findElement(By.xpath(cellXpathX)).click();
			String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//span";
			cDriver.get().findElement(By.xpath(cellXpath)).click();
			Browser.ListBox.select("PopupQuery_List", Variable);
			waitforload();
			Browser.WebEdit.Set("PopupQuery_Search", val);
			waitforload();
			Browser.WebButton.click("Popup_Go");
			waitforload();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void Popup_Click(String objname, int rownum, int columnnum) {

		String[] objprop = Utlities.FindObject(objname, "WebTable");
		String cellXpath1X = objprop[0] + "//tr[" + rownum + "]//td[" + (columnnum + 1) + "]";
		WebElement scr2 = cDriver.get().findElement(By.xpath(cellXpath1X));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr2);

		String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpathX));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		cDriver.get().findElement(By.xpath(cellXpathX)).click();
		String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//span";
		WebElement scr = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr);
		cDriver.get().findElement(By.xpath(cellXpath)).click();

	}

	public void Popup_Click1(String objname, int rownum, int columnnum) {

		String[] objprop = Utlities.FindObject(objname, "WebTable");
		String cellXpath1X = objprop[0] + "//tr[" + rownum + "]//td[" + (columnnum + 1) + "]";
		WebElement scr2 = cDriver.get().findElement(By.xpath(cellXpath1X));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr2);

		String cellXpathX = objprop[0] + "//tr[" + rownum + "]//td[" + (columnnum - 1) + "]";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpathX));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		cDriver.get().findElement(By.xpath(cellXpathX)).click();

		Actions action = new Actions(cDriver.get());
		action.sendKeys(scr1, Keys.TAB).build().perform();

		String cellXpath = objprop[0] + "//tr[" + rownum + "]//td[" + columnnum + "]//span";
		WebElement scr = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr);
		cDriver.get().findElement(By.xpath(cellXpath)).click();

	}

	public void Popup_Selection(String objname, String Name, String MSISDN) {
		try {
			waitforload();
			int Row_Count = Browser.WebTable.getRowCount(objname);
			int Col = PopupHeader(objname, Name);
			Browser.WebButton.click("PopupQuery");
			waitforload();
			if ((Row_Count > 1) & (Browser.WebTable.getRowCount(objname) == 2)) {
				Browser.WebTable.SetData(objname, 2, Col, Name, MSISDN);
				Row_Count = Browser.WebTable.getRowCount(objname);
				if (Row_Count > 1) {
					scroll("Popup_OK", "WebButton");
					Browser.WebButton.click("Popup_OK");
				} else
					Driver.Continue.set(false);
			} else
				Driver.Continue.set(false);
		} catch (Exception e) {
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: NumberRangeProducts
	 * Use 					: To Add a Specific No of Item and customising From and To Range with Token if applicable
	 * args					: Option, Quantity ,From , To , Token
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 11-October-2017
	--------------------------------------------------------------------------------------------------------*/

	public void NumberRangeProducts(String Option, String Qantity, String From, String To, String Token) {
		try {
			int Operation_Done;
			String CCODE = "974";
			Link_Select("Number Range Products");
			ToWait();
			Operation_Done = 0;
			List<WebElement> Option_Search = cDriver.get()
					.findElements(By.xpath("//div[@class='NotSelected siebui-ecfg-module']//table//select"));
			List<WebElement> input_Search = cDriver.get().findElements(By.xpath(
					"//div[@class='NotSelected siebui-ecfg-module']//table//input[@class='siebui-ctrl-input ']"));
			List<WebElement> button_Search = cDriver.get().findElements(By
					.xpath("//div[@class='NotSelected siebui-ecfg-module']//table//button[@class='siebui-ctrl-btn ']"));
			List<WebElement> Label_Search = cDriver.get().findElements(
					By.xpath("//div[@class='siebui-ecfg-header-title']//div[@class='siebui-ecfg-header-label']"));
			System.out.println(Label_Search.size() + "  " + Option_Search.size() + " " + input_Search.size() + " "
					+ button_Search.size());
			for (int i = 0; i < Label_Search.size(); i++) {
				if (Label_Search.get(i).getText().contains(Option)) {
					((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
							Option_Search.get(i));
					ToWait();
					Option_Search.get(i).click();
					Option_Search.get(i).findElement(By.xpath("//option[text()='" + Option + "']")).click();
					ToWait();
					input_Search.get(i).sendKeys(Qantity);
					button_Search.get(i).click();
					ToWait();
					Result.takescreenshot(Option + " with " + Qantity + " Qantity is added");
					Link_Select(Option);
					ToWait();
					Operation_Done = 1;
					break;

				} else
					((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
							Option_Search.get(i));
				ToWait();
			}
			if (Operation_Done == 1) {
				waitforload();
				List<WebElement> Field_Name = cDriver.get()
						.findElements(By.xpath("//div[@id='MainPage']//div[@class='siebui-ecfg-header-label']"));
				List<WebElement> Field_Input = cDriver.get()
						.findElements(By.xpath("//div[@id='MainPage']//input[@class='siebui-ctrl-input ']"));
				for (int Index = 0; Index < Field_Name.size(); Index++) {
					((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
							Field_Input.get(Index));
					ToWait();
					if (Field_Name.get(Index).getText().contains("From"))
						Field_Input.get(Index).sendKeys(CCODE + From);
					if (Field_Name.get(Index).getText().contains("To"))
						Field_Input.get(Index).sendKeys(CCODE + To);
					if (Field_Name.get(Index).getText().contains("Reservation Token"))
						Field_Input.get(Index).sendKeys(Token);
					Result.takescreenshot("Providing From and To Values " + From + " " + To);
				}
			} else
				Continue.set(false);
		} catch (Exception e) {
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TabNavigator
	 * Use 					: To click on the tab
	 * args					: Option, Quantity ,From , To , Token
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 11-October-2017
	--------------------------------------------------------------------------------------------------------*/
	public void TabNavigator(String value) {
		cDriver.get().manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		Boolean finder = false;
		try {
			Thread.sleep(2);
			List<WebElement> options1 = (List<WebElement>) cDriver.get()
					.findElements(By.xpath("//div[@class='siebui-nav-tab siebui-subview-navs']/div/ul/li/a"));

			for (WebElement option1 : options1) {
				if ((option1.getText().equalsIgnoreCase(value))) {
					option1.click();
					finder = true;
					break;
				}
			}

			if (finder == false) {
				List<WebElement> options = cDriver.get()
						.findElements(By.xpath("//div[@class='siebui-nav-tab siebui-subview-navs']//option"));
				for (WebElement option : options) {
					if ((option.getText().equalsIgnoreCase(value))) {
						option.click();
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Plan_selection(String GetData, String MSISDN) {
		int Col, Col_S, Row_Count;
		String msd = null, LData;
		Col = Actual_Cell("Acc_Installed_Assert", "Product");
		Col_S = Actual_Cell("Acc_Installed_Assert", "Service ID");
		Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
		for (int i = 2; i <= Row_Count; i++) {
			LData = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col);
			msd = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_S);
			if ((LData.equalsIgnoreCase(GetData)) && (MSISDN.equalsIgnoreCase(msd))) {
				if ((i % 2) == 0) {
					Browser.WebTable.click("Acc_Installed_Assert", (i + 1), Col_S);
					InstalledAssertChange("Upgrade Promotion");
					waitforload();
					break;
				} else {
					Browser.WebTable.click("Acc_Installed_Assert", (i - 1), Col_S);
					InstalledAssertChange("Upgrade Promotion");
					waitforload();
					break;
				}
			}

		}

	}

	public void Addon_Settings(String Text) {
		// String cellXpath = "//" + Tag + "[@title='" + Text + "']";
		String cellXpath = "//input[@value='" + Text + "']/../..//i[@class='siebui-icon-settings']";
		WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
		((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
		waitforload();
		cDriver.get().findElement(By.xpath(cellXpath)).click();
	}

	public void Discounts(String Disc_Addon, String Discount) {

		List<WebElement> elements = cDriver.get().findElements(
				By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[@class='siebui-ecfg-feature-group']"));
		int Size = elements.size();
		System.out.println(Size);
		boolean flag = false;
		waitforload();
		for (int i = 1; i <= Size; i++) {
			List<WebElement> Addon = cDriver.get()
					.findElements(By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[" + i
							+ "]//div[1]//table//div[1]//div[1]//input"));

			for (int t = 1; t < Addon.size(); t++) {
				if (Addon.get(t).getAttribute("value").equals(Disc_Addon)) {
					if (Addon.get(t).getAttribute("type").equals("radio")) {
						// Radio Button
						Result.takescreenshot("Addon or Plan Selection");
						((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)",
								Addon.get(t));
						Addon.get(t).click();
						waitmoreforload();
						cDriver.get()
								.findElement(By.xpath("//div[@class='siebui-ecfg-products']//div[1]//div[" + i
										+ "]//div[1]//table//div[1]//div[1]//i[@class='siebui-icon-settings']"))
								.click();

						String cellXpath = "//input[@value='" + Discount + "']";

						if (cDriver.get().findElement(By.xpath(cellXpath)).isDisplayed()) {
							WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
							((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);
							cDriver.get().findElement(By.xpath(cellXpath)).click();

						} else
							Continue.set(false);

						flag = true;
						break;
					}
				}
			}

			if (flag) {
				break;
			}

		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Upload
	 * Use 					: To Upload a specific File
	 * args					: Text, File 
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 11-October-2017
	--------------------------------------------------------------------------------------------------------*/
	public void Upload(String Text, String File) {
		try {
			String path = Templete_FLD.get() + "/Guided_Journey/" + File;
			String[] objprop = Utlities.FindObject(Text, "WebButton");
			cDriver.get().findElement(By.xpath(objprop[0])).sendKeys(path);
			waitmoreforload();
			// Result.takescreenshot("File Uploaded");

		} catch (Exception e) {
			Driver.Continue.set(false);
			System.out.println("Failed to Upload");
		}

	}
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: FL_AddonSelection
	 * Use 					: Customizing the specific Plan for FL
	 * Designed By			: SravaniReddy
	 * Last Modified Date 	: 13-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public void FL_AddonSelection(String Product) {
		try {
			int Length;
			String Product_Tabs[] = Product.split("<>");
			for (int i = 0; i < Product_Tabs.length; i++) {
				String Prod_array[] = Product_Tabs[i].split(",");
				Length = Prod_array.length;
				System.out.println(Length);
				
					Thread.sleep(3);
						for (int j = 0; j < Prod_array.length; j++)
						{
							Radio_Select(Prod_array[j]);
							Thread.sleep(3000);
							
						}
							
					
				
			}
		} catch (Exception e) {

		}
	}

	// Plan_selection using existing plane name

	/*
	 * public void Plan_selection(String GetData, String MSISDN, String
	 * Existing_Plan) { int Col, Row_Count; String msd = null; Col =
	 * Actual_Cell("Acc_Installed_Assert", "Product"); Row_Count =
	 * Browser.WebTable.getRowCount("Acc_Installed_Assert"); for (int i = 1; i <=
	 * Row_Count; i++) { String LData =
	 * Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col); if
	 * (LData.equalsIgnoreCase(Existing_Plan)) { int Col_S =
	 * Actual_Cell("Acc_Installed_Assert", "Service ID"); for (int j = i - 1; j <=
	 * (i + 1); j = j + 2) { LData =
	 * Browser.WebTable.getCellData("Acc_Installed_Assert", j, Col); msd =
	 * Browser.WebTable.getCellData("Acc_Installed_Assert", j, Col_S); if
	 * ((LData.equalsIgnoreCase(GetData)) && (MSISDN.equalsIgnoreCase(msd))) {
	 * Browser.WebTable.click("Acc_Installed_Assert", i, (Col + 1));
	 * InstalledAssertChange("Upgrade Promotion"); waitforload(); break; } } } if
	 * ((LData.equalsIgnoreCase(GetData)) && (MSISDN.equalsIgnoreCase(msd))) {
	 * break; } } }
	 */

	/*
	 * public static void main(String[] args) {
	 * 
	 * }
	 */
	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Actual_OSM_tabval
	 * Use 					: To Select particular value in Query Search Screen in OSM
	 * Designed By			: SravaniReedy
	 * Last Modified Date 	: 13-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public int Actual_OSM_tabval(String objname, String objTyp) {
		int Col = 1, f = 0;
		String Expected = objTyp;
		String[] obj = objTyp.split("_");
		if (obj.length > 1)
			Expected = objTyp.replace('_', ' ');
		int Col_Count = Browser.WebTable.getColCount(objname);
		String[] objprop = Utlities.FindObject(objname, "WebTable");
	
		waitforload();
		for (int i = 1; i < Col_Count; i++) {
			Col = i;
			String cellXpath = objprop[0]+"//table//td[" + i + "]";
			/*WebElement scr1 = cDriver.get().findElement(By.xpath(cellXpath));
			((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", scr1);*/
			String celldata = cDriver.get().findElement(By.xpath(cellXpath)).getText();
			if (celldata.equalsIgnoreCase(Expected))
				f = f + 1;
			if (f == 1)
				break;

		}
		return Col;
	}

}