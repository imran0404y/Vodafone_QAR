package Libraries;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class Keyword_OSM extends Driver {
	Common CO = new Common();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: OSM_Login
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the OSM application
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 17-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String OSM_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Login Event Details------");
		try {

			if (!(getdata("Browser").equals(""))) {
				browser.set(getdata("Browser"));
			} else {
				browser.set("ie");
			}

			if (!(getdata("URL").equals(""))) {
				URL.set(getdata("URL"));
			} else {
				URL.set("ie");
			}

			Browser.OpenBrowser(browser.get(), "http://10.162.53.102:7001/OrderManagement/Login.jsp");

			// Result.fUpdateLog("Browser Opened Successfully");
			Result.takescreenshot("Opening Browser and navigating to the URL");
			/*
			 * Browser.WebLink.waittillvisible("OSM_Link");
			 * Browser.WebLink.click("OSM_Link");
			 */
			Browser.WebEdit.click("OSM_Login_User");
			Browser.WebEdit.Set("OSM_Login_User", getdata("OSM_Login_User"));
			Browser.WebEdit.click("OSM_Login_Pswd");
			Browser.WebEdit.Set("OSM_Login_Pswd", getdata("OSM_Login_Pswd"));
			Browser.WebLink.click("OSM_Submit");
			// Browser.WebLink.waittillvisible("OSM_Submit");
			// Browser.WebLink.click("OSM_Submit");

			CO.ToWait();

			if (Continue.get()) {
				Test_OutPut += "Successfully Login with : " + getdata("OSM_Login_User") + ",";
				Result.takescreenshot("Login Successfully with user " + getdata("OSM_Login_User"));
				Result.fUpdateLog("Login Successfully with user " + getdata("OSM_Login_User"));
				Status = "PASS";
			} else {
				Test_OutPut += "Login Failed" + ",";
				Result.takescreenshot("Login Failed");
				Result.fUpdateLog("Login Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------OSM Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String OSM_SearchFL() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------OSM_SearchFL Event Details------");
		int Col, Col_S, Row_Count, Wait = 0;

		String Order_No = null, New_Order = null;
		try {

			String Sales_Od = SalesOrder_No.get();
			Browser.WebButton.click("OSM_Query");
			CO.waitforload();
			Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
			Result.fUpdateLog("Searching with Sales OrderNo " + Sales_Od);
			CO.scroll("OSM_OrderNo_entry", "WebEdit");
			Browser.WebButton.click("OSM_Query_search");
			CO.waitforload();
			Col = CO.Actual_OSM_tabval("OSM_QueryRes", "State");
			Col_S = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");

			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");

			String LData = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col);
			if (LData.equalsIgnoreCase("Received")) {
				Order_No = Browser.WebTable.getCellData("OSM_QueryRes", 2, Col_S);
			} else {
				Continue.set(false);
			}

			if (Continue.get()) {
				CO.waitforload();
				Browser.WebButton.click("OSM_Worklist");
				CO.waitforload();
				Browser.WebEdit.Set("OSM_OrderId", Order_No);
				Result.fUpdateLog("Searching with Order_No " + Order_No);
				CO.waitforload();
				Browser.WebButton.click("OSM_Refresh");
				Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");
				if (Row_Count >= 2) {
					CO.waitforload();
					Browser.WebButton.click("OSM_Search");
					CO.scroll("OSM_CPE", "WebEdit");
					Browser.WebEdit.Set("OSM_CPE", getdata("OSM_CPE"));
					CO.scroll("OSM_OUI", "WebEdit");
					Browser.WebEdit.Set("OSM_OUI", getdata("OSM_OUI"));
					CO.scroll("OSM_CPEP", "WebEdit");
					Browser.WebEdit.Set("OSM_CPEP", getdata("OSM_CPEP"));
					CO.scroll("OSM_SerialN", "WebEdit");
					Browser.WebEdit.Set("OSM_SerialN", getdata("OSM_SerialN"));
					CO.scroll("OSM_CardID", "WebEdit");
					Browser.WebEdit.Set("OSM_CardID", getdata("OSM_CardID"));
					CO.scroll("OSM_PortID", "WebEdit");
					Browser.WebEdit.Set("OSM_PortID", getdata("OSM_PortID"));
					Select dropdown = new Select(cDriver.get().findElement(
							By.xpath("//form[@name=\"orderEditorMenu\"]//select[@id=\"completionStatusList\"]")));
					dropdown.selectByVisibleText("Finish");
					Browser.WebButton.click("OSM_Update");
					Browser.WebButton.click("OSM_Query");
					CO.scroll("OSM_OrderNo_entry", "WebEdit");
					CO.waitforload();
					Browser.WebEdit.Set("OSM_OrderNo_entry", Sales_Od);
					Browser.WebButton.click("OSM_Query_search");
					CO.waitforload();
					Col = CO.Actual_OSM_tabval("OSM_QueryRes", "Order State");
					int ColOD = CO.Actual_OSM_tabval("OSM_QueryRes", "Order ID");

					Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");
					for (int i = 2; i <= Row_Count; i++) {

						String ODid = Browser.WebTable.getCellData("OSM_QueryRes", i, ColOD);
						if (ODid.equals(Order_No)) {
							Browser.WebButton.click("OSM_Worklist");
							Browser.WebEdit.Set("OSM_OrderId", ODid);
							Browser.WebButton.click("OSM_Refresh");

							do {
								LData = Browser.WebTable.getCellData("OSM_QueryRes", i, Col);
								System.out.println("OSM Status" + " " + LData);
								if (LData.equalsIgnoreCase("Completed")) {
									Result.fUpdateLog("OSM Status has Sucesfully Updated ");
									Wait = 101;
									break;

								} else if (Wait == 105) {
									Result.fUpdateLog("OSM Status updation has failed ");
									Continue.set(false);
									break;

								}
								Browser.WebButton.click("OSM_Refresh");
								Wait = Wait + 5;
								CO.waitforload();
							} while (Wait < 100);

						} else {
							New_Order = ODid;
						}

					}
					if (Continue.get()) {
						Browser.WebButton.click("OSM_Worklist");
						Browser.WebEdit.Set("OSM_OrderId", New_Order);
						Browser.WebButton.click("OSM_Refresh");

						Col = CO.Actual_OSM_tabval("OSM_QueryRes", "Order State");
						Row_Count = Browser.WebTable.getRowCount("OSM_QueryRes");
						for (int i = 2; i <= Row_Count; i++) {

							do {
								LData = Browser.WebTable.getCellData("OSM_QueryRes", i, Col);
								System.out.println("OSM Status" + " " + LData);
								if (LData.equalsIgnoreCase("Completed")) {
									Result.fUpdateLog("OSM New_Order Status has Sucesfully Updated ");
									Wait = 101;
									break;

								} else if (Wait == 105) {
									Result.fUpdateLog("OSM New_Order Status updation has failed ");
									break;
								}
								Browser.WebButton.click("OSM_Refresh");
								Wait = Wait + 5;
								CO.waitforload();
							} while (Wait < 100);
						}

					} else {
						Continue.set(false);
					}
					CO.scroll("OSM_Logout", "WebButton");
					Browser.WebButton.click("OSM_Logout");
					Result.fUpdateLog("OSM Logout ");
				}
			}

			CO.ToWait();

			if (Continue.get()) {
				Utlities.StoreValue("Seibel Order No :", Order_No);
				Test_OutPut += "Seibel Order No : " + Order_No + ",";
				Utlities.StoreValue("New_Order:", New_Order);
				Test_OutPut += "New_Order: " + New_Order + ",";
				Result.takescreenshot("Login Successfully with user " + getdata("OSM_Login_User"));
				Result.fUpdateLog("Login Successfully with user " + getdata("OSM_Login_User"));
				Status = "PASS";
			} else {
				Test_OutPut += "OSM Updation Failed" + ",";
				Result.takescreenshot("OSM Updation Failed");
				Result.fUpdateLog("OSM Updation Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------OSM Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

}
