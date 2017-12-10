package Libraries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.CharMatcher;

public class Keyword_CRM extends Driver {
	static String Billprofile_No;
	Common CO = new Common();
	Random R = new Random();
	public static int COL_FUL_STATUS;

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Open browser
	 * Arguments			: None
	 * Use 					: Opens a New Browser and logins to the Siebel CRM application
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Siebel_Login() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Login Event Details------");
		try {

			if (!(getdata("Browser").equals(""))) {
				browser.set(getdata("Browser"));
			} else {
				browser.set("Chrome");
			}

			URL.set(getdata("URL/HOST"));

			Browser.OpenBrowser(browser.get(), URL.get());

			Result.fUpdateLog("Browser Opened Successfully");
			Result.takescreenshot("Opening Browser and navigating to the URL");
			Browser.WebEdit.waittillvisible("VQ_Login_User");
			Browser.WebEdit.Set("VQ_Login_User", getdata("VQ_Login_User"));
			Browser.WebEdit.Set("VQ_Login_Pswd", getdata("VQ_Login_Pswd"));
			Browser.WebButton.waittillvisible("VQ_Login");
			Browser.WebButton.click("VQ_Login");
			CO.waitforload();
			Browser.WebButton.waittillvisible("VF_Search_Identify");

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Successfully Login with : " + getdata("VQ_Login_User") + ",";
				Result.takescreenshot("Login Successfully with user " + getdata("VQ_Login_User"));
				Result.fUpdateLog("Login Successfully with user " + getdata("VQ_Login_User"));
				Status = "PASS";
			} else {
				Test_OutPut += "Login Failed" + ",";
				Result.takescreenshot("Login Failed");
				Result.fUpdateLog("Login Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: SiebLogout
	 * Arguments			: None
	 * Use 					: Log Out Siebel browser and close the browser window
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Siebel_Logout() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Siebel Logout Event Details------");
		try {

			CO.waitforobj("VQ_Acc_Logo", "WebButton");
			CO.scroll("VQ_Acc_Logo", "WebButton");
			Browser.WebButton.waittillvisible("VQ_Acc_Logo");
			Browser.WebButton.click("VQ_Acc_Logo");
			Result.takescreenshot("Siebel Application Logged out");

			CO.waitforobj("VQ_Logout", "WebButton");
			CO.scroll("VQ_Logout", "WebButton");
			Browser.WebButton.waittillvisible("VQ_Logout");
			Browser.WebButton.click("VQ_Logout");

			cDriver.get().close();

			CO.ToWait();

			if (Continue.get()) {
				Test_OutPut += "Siebel Logout Successful";
				Result.fUpdateLog("Siebel Logout Successful");
				Status = "PASS";
			} else {
				Test_OutPut += "Logout Failed";
				Result.fUpdateLog("Logout Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Test_OutPut += "Exception occurred";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Siebel Logout Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: ContactCreation
	 * Arguments			: None
	 * Use 					: Creates a new contact with the specific data in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String ContactCreation() {
		String Test_OutPut = "", Status = "";
		String Last_Name = null;
		Result.fUpdateLog("------Contact Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				String IDType = "", IDNumber = "";
				CO.waitforobj("VQ_Contact", "WebLink");
				CO.waitforload();
				Browser.WebLink.click("VQ_Contact");
				Browser.WebLink.waittillvisible("My_Contacts");

				CO.waitforobj("My_Contacts", "WebLink");
				Browser.WebLink.click("My_Contacts");
				Browser.WebButton.waittillvisible("New_Contact");

				Browser.WebButton.click("New_Contact");

				if (!(getdata("LastName").equals(""))) {
					Last_Name = getdata("LastName");
				} else if (!(pulldata("LastName").equals(""))) {
					Last_Name = pulldata("LastName") + R.nextInt(1000);
				}
				CO.scroll("LastName", "WebEdit");
				Browser.WebEdit.Set("LastName", Last_Name);
				Result.fUpdateLog("LastName : " + Last_Name);

				if (!(getdata("FirstName").equals(""))) {
					Browser.WebEdit.Set("FirstName", getdata("FirstName"));
				} else if (!(pulldata("FirstName").equals(""))) {
					Browser.WebEdit.Set("FirstName", pulldata("FirstName"));
				}

				if (!(getdata("Mr/Ms").equals(""))) {
					Browser.ListBox.select("Mr/Ms", getdata("Mr/Ms"));
				} else {
					Browser.ListBox.select("Mr/Ms", pulldata("Mr/Ms"));
				}

				if (!(getdata("PrefLanguage").equals(""))) {
					Browser.ListBox.select("PrefLanguage", getdata("PrefLanguage"));
				} else {
					Browser.ListBox.select("PrefLanguage", pulldata("PrefLanguage"));
				}

				if (!(getdata("DOB").equals(""))) {
					Browser.WebEdit.Set("DOB", getdata("DOB"));
				} else {
					Browser.WebEdit.Set("DOB", pulldata("DOB"));
				}

				if (!(getdata("Gender").equals(""))) {
					Browser.ListBox.select("Gender", getdata("Gender"));
				} else {
					Browser.ListBox.select("Gender", pulldata("Gender"));
				}

				if (!(getdata("Email").equals(""))) {
					Browser.WebEdit.Set("Email", getdata("Email"));
				} else {
					Browser.WebEdit.Set("Email", pulldata("Email"));
				}

				// CO.scroll("ID_Type", "ListBox");
				if (!(getdata("ID_Type").equals(""))) {
					IDType = getdata("ID_Type");
				} else {
					IDType = pulldata("ID_Type");
				}
				Browser.ListBox.select("ID_Type", IDType);

				// CO.scroll("ID_Number", "WebEdit");
				if (!(getdata("ID_Number").equals(""))) {
					IDNumber = getdata("ID_Number");
				} else {
					IDNumber = pulldata("ID_Number") + R.nextInt(100000);
				}
				Browser.WebEdit.Set("ID_Number", IDNumber);
				Result.fUpdateLog("Customer ID : " + IDNumber);
				Test_OutPut += "Customer ID : " + IDNumber + ",";

				// CO.scroll("ID_ExpDate", "WebEdit");
				if (!(getdata("ID_ExpDate").equals(""))) {
					Browser.WebEdit.Set("ID_ExpDate", getdata("ID_ExpDate"));
				} else {
					Browser.WebEdit.Set("ID_ExpDate", pulldata("ID_ExpDate"));
				}

				// CO.scroll("Nationality", "ListBox");
				if (!(getdata("Nationality").equals(""))) {
					Browser.ListBox.select("Nationality", getdata("Nationality"));
				} else {
					Browser.ListBox.select("Nationality", pulldata("Nationality"));
				}

				// CO.scroll("Phone", "WebEdit");
				if (!(getdata("Phone").equals(""))) {
					Browser.WebEdit.Set("Phone", getdata("Phone"));
				} else {
					Browser.WebEdit.Set("Phone", pulldata("Phone"));
				}

				Browser.WebLink.waittillvisible("Con_Link");
				Browser.WebLink.click("Con_Link");

				// Handles Alerts
				if (CO.isAlertExist())
					CO.waitforload();

				String Address;
				if (!(getdata("Address").equals(""))) {
					Address = getdata("Address");
				} else if (!(getdata("Kahramaa_ID").equals(""))) {
					Address = "Kar#" + getdata("Kahramaa_ID");
				} else if (!(pulldata("Kahramaa_ID").equals(""))) {
					Address = "Kar#" + pulldata("Kahramaa_ID");
				} else {
					Address = pulldata("Address");
				}
				Result.takescreenshot("Customer Creation with Customer ID : " + IDNumber);

				if (!(Address.equals(""))) {

					CO.waitforobj("Add_Address", "WebButton");
					Browser.WebButton.click("Add_Address");

					// Search for Specific Address
					CO.waitforobj("Popup_Go", "WebButton");
					CO.scroll("Popup_Go", "WebButton");

					if (Address.contains("Kar#")) {
						Browser.ListBox.select("PopupQuery_List", "Kahramaa ID");
						Address = Address.split("#")[1];
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					} else {
						Browser.ListBox.select("PopupQuery_List", "Address Line 1");
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					}
					CO.waitforload();
					Browser.WebButton.click("Popup_Go");

					CO.scroll("Add_OK", "WebButton");
					Browser.WebButton.click("Add_OK");
					/*
					 * do { Result.fUpdateLog("Page Loading....."); } while
					 * (Browser.WebButton.waitTillEnabled("Add_OK"));
					 */

					CO.waitmoreforload();
					Browser.WebButton.waittillvisible("Create_A/c");
					Result.takescreenshot("Address Selected : " + Address);
					Result.fUpdateLog("Contact created with given Existing Address : " + Address);
				} else {
					String[] stat_add = AddressCreation().split("@@");
					Status = stat_add[0];
					Address = stat_add[1].split(",")[0];
					Result.takescreenshot("Address Created : " + Address);
					Result.fUpdateLog("Created new Address : " + Address);
				}

				CO.ToWait();
				if (Continue.get()) {
					Utlities.StoreValue("LastName", Last_Name);
					Utlities.StoreValue("Address", Address);
					Status = "PASS";
				} else {
					Result.takescreenshot("Create_A/c button not exist");
					Test_OutPut += "Create_A/c button not exist" + ",";
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Contact Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AccountCreation
	 * Arguments			: None
	 * Use 					: Creates Account for the Contact created Earlier in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String AccountCreation() {
		String Test_OutPut = "", Status = "";
		String Account_No = null;
		Result.fUpdateLog("------Account Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				int Row_Count = Browser.WebTable.getRowCount("Address");
				if (Row_Count > 1) {
					Browser.WebButton.waittillvisible("Create_A/c");
					Browser.WebButton.waitTillEnabled("Create_A/c");
					CO.waitforobj("Create_A/c", "WebButton");
					CO.scroll("Create_A/c", "WebButton");
					CO.Text_Select("span", "Create A/c");

					Browser.ListBox.waittillvisible("CR_Type");
					String CR = "12" + R.nextInt(100000);
					if (!(getdata("CR_Type").equals(""))) {
						Browser.ListBox.select("CR_Type", getdata("CR_Type"));
						Browser.WebEdit.Set("CR_Number", getdata("CR_Number"));
					} else if (!(pulldata("CR_Type").equals(""))) {
						Browser.ListBox.select("CR_Type", pulldata("CR_Type"));
						Browser.WebEdit.Set("CR_Number", CR);
					}

					if (!(getdata("SpecialManagement").equals(""))) {
						Browser.ListBox.select("Spcl_Mang", getdata("SpecialManagement"));
						Result.fUpdateLog("SpecialManagement : " + getdata("SpecialManagement"));
					} else {
						Browser.ListBox.select("Spcl_Mang", pulldata("SpecialManagement"));
						Result.fUpdateLog("SpecialManagement : " + pulldata("SpecialManagement"));
					}

					Account_No = Browser.WebEdit.gettext("Account_No");
					Utlities.StoreValue("Account_No", Account_No);
					Test_OutPut += "Account_No : " + Account_No + ",";
				} else {
					Continue.set(false);
					Result.fUpdateLog("No records Founded - Create a address for the customer");
					System.exit(0);
				}

				CO.ToWait();
				if (Continue.get()) {
					Status = "PASS";
					Result.takescreenshot("Account Created Account_No : " + Account_No);
				} else {
					Test_OutPut += "Account Creation is Failed" + ",";
					Result.takescreenshot("Account Creation is Failed");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();

		}
		Result.fUpdateLog("------Account Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: AddressCreation
	 * Arguments			: None
	 * Use 					: Creates Account for the Contact created Earlier in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String AddressCreation() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Address Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				// Browser.WebLink.waittillvisible("Acc_address");
				CO.waitforload();
				if (Browser.WebLink.exist("Acc_address")) {
					Result.fUpdateLog("Proceeding Consumer Address Creation");
					Browser.WebButton.click("Add_Address");
					CO.waitforload();
				} else if (Browser.WebButton.exist("Address_Tab")) {
					Result.fUpdateLog("Proceeding Enterprise Address Creation");
					Browser.WebButton.click("Add_Address");
					CO.waitmoreforload();
				}

				CO.waitforload();
				int Row = 2, Col;
				CO.scroll("Acc_Add_New", "WebButton");
				Browser.WebButton.click("Acc_Add_New");

				String Add1 = null, Add2 = null;
				Col = CO.Select_Cell("Address", "Address Line 1");
				if (!(getdata("Add_AddressLine1").equals(""))) {
					Add1 = getdata("Add_AddressLine1");
				} else if (!(pulldata("Add_AddressLine1").equals(""))
						&& !(pulldata("Add_AddressLine1").equalsIgnoreCase("Autogenerated"))) {
					Add1 = pulldata("Add_AddressLine1");
				} else if (pulldata("Add_AddressLine1").equalsIgnoreCase("Autogenerated")) {
					Add1 = Utlities.randname();
				}
				CO.waitforload();
				Browser.WebTable.SetDataE("Address", Row, Col, "Street_Address", Add1);
				Utlities.StoreValue("Address line1", Add1);

				Col = CO.Select_Cell("Address", "Address Line 2");
				if (!(getdata("Add_AddressLine2").equals(""))) {
					Add2 = getdata("Add_AddressLine2");
				} else if (!(pulldata("Add_AddressLine2").equals(""))
						&& !(pulldata("Add_AddressLine2").equalsIgnoreCase("Autogenerated"))) {
					Add2 = pulldata("Add_AddressLine2");
				} else if (pulldata("Add_AddressLine2").equalsIgnoreCase("Autogenerated")) {
					Add2 = Utlities.randname();
				}
				Browser.WebTable.SetDataE("Address", Row, Col, "Street_Address_2", Add2);
				Utlities.StoreValue("Address line2", Add2);

				Col = CO.Select_Cell("Address", "PO Box");
				if (!(getdata("Add_POBox").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_PO_Box", getdata("Add_POBox"));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_PO_Box", pulldata("Add_POBox"));
				}

				Col = CO.Select_Cell("Address", "Postal Code");
				if (!(getdata("Add_Zip").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "Postal_Code", getdata("Add_PostalCode"));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "Postal_Code", pulldata("Add_PostalCode"));
				}

				Col = CO.Select_Cell("Address", "Kahramaa ID");
				if (!(getdata("Add_Kahramaa_ID").equals(""))) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", getdata("Add_Kahramaa_ID"));
				} else if (pulldata("Add_Kahramaa_ID").equalsIgnoreCase("Autogenerated")) {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", "1" + R.nextInt(10000000));
				} else {
					Browser.WebTable.SetDataE("Address", Row, Col, "VFQA_Kahramaa_ID", pulldata("Add_Kahramaa_ID"));
				}

				CO.waitforload();
				int Row_Count = Browser.WebTable.getRowCount("Address");
				// Browser.WebLink.waittillvisible("Acc_Contacts");
				CO.waitforload();

				if (Continue.get() && Row_Count > 1) {
					Test_OutPut += Add1 + ",";
					Status = "PASS";
				} else {
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Address Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BillingProfileCreation
	 * Arguments			: None
	 * Use 					: Creates a Billing Profile in the existing Account for Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String BillingProfileCreation() {
		String Test_OutPut = "", Status = "";
		String Bill_No = null;
		int Col_Nam, Row_va = 0;
		Result.fUpdateLog("------Billing Profile Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (!Exi.equals("")) {
				CO.Account_Search(Exi);
				Utlities.StoreValue("Account_No", Exi);
				Test_OutPut += "Account_No : " + Exi + ",";
				CO.waitforload();
			}
			if (Continue.get()) {
				CO.scroll("Profile_Tab", "WebButton");
				do {
					Browser.WebButton.click("Profile_Tab");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("BP_Valid_Name")) { j = 0; break; }
					 */

				} while (!Browser.WebEdit.waitTillEnabled("BP_Valid_Name"));
				Browser.WebEdit.waittillvisible("BP_Valid_Name");

				CO.waitforload();
				int Row = 2, Col_Val = 0, Row_Count;

				String Payment_Type = null;
				if (!(getdata("Bill_PayType").equals(""))) {
					Payment_Type = getdata("Bill_PayType");
				} else if (!(pulldata("Bill_PayType").equals(""))) {
					Payment_Type = pulldata("Bill_PayType");
				}

				String Bill_NewProfile = "No";
				if (!(getdata("Bill_NewProfile").equals(""))) {
					Bill_NewProfile = getdata("Bill_NewProfile");
				} else if (!(pulldata("Bill_NewProfile").equals(""))) {
					Bill_NewProfile = pulldata("Bill_NewProfile");
				}

				CO.waitforobj("Bill_Add", "WebButton");
				Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
				if (Row_Count >= Row) {
					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");
					Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
						if (Payment_Type.equalsIgnoreCase(LData)) {
							Bill_No = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
							break;
						}
						Row_va = i;
					}
				}

				if ((Row_Count < Row) || Bill_NewProfile.equals("Yes") || Row_Count == Row_va) {
					Browser.WebButton.waittillvisible("Bill_Add");
					CO.scroll("Bill_Add", "WebButton");
					int Row_Ct = Browser.WebTable.getRowCount("Bill_Prof");
					Browser.WebButton.click("Bill_Add");
					do {
						int Row_C = Browser.WebTable.getRowCount("Bill_Prof");
						if (Row_C > Row_Ct) {
							break;
						}
					} while (true);

					CO.waitforload();

					Browser.WebTable.waittillvisible("Bill_Prof");
					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");
					if (!(getdata("Bill_PayType").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Type", getdata("Bill_PayType"));
					} else if (!(pulldata("Bill_PayType").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Type", pulldata("Bill_PayType"));
					}

					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Method");
					if (!(getdata("Bill_PayMethod").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method",
								getdata("Bill_PayMethod"));
					} else if (!(pulldata("Bill_PayMethod").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method",
								pulldata("Bill_PayMethod"));
					}
					CO.isAlertExist();

					if (Payment_Type.equalsIgnoreCase("Postpaid")) {
						Col_Val = CO.Select_Cell("Bill_Prof", "Bill Media");
						if (!(getdata("Bill_Media").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", getdata("Bill_Media"));
						} else if (!(pulldata("Bill_Media").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", pulldata("Bill_Media"));
						}

						Col_Val = CO.Select_Cell("Bill_Prof", "Bill Type");
						if (!(getdata("Bill_Type").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", getdata("Bill_Type"));
						} else if (!(pulldata("Bill_Type").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", pulldata("Bill_Type"));
						}

					}

					Col_Val = CO.Select_Cell("Bill_Prof", "Language");
					if (!(getdata("Bill_Lang").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code", getdata("Bill_Lang"));
					} else if (!(pulldata("Bill_Lang").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code",
								pulldata("Bill_Lang"));
					}

					int Col_v;
					Col_v = CO.Actual_Cell("Bill_Prof", "Name");
					Bill_No = Browser.WebTable.getCellData("Bill_Prof", Row, Col_v);
				}

				Billprofile_No = Bill_No;
				Utlities.StoreValue("Billing_NO", Bill_No);
				Test_OutPut += "Billing_NO : " + Bill_No + ",";

				Browser.WebButton.waittillvisible("Orders_Tab");
			}
			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("Billing Profile Created Billing_NO : " + Bill_No);
			} else {
				Result.takescreenshot("Orders Tab not exist");
				Test_OutPut += "Orders Tab not exist" + ",";
				Result.fUpdateLog("Orders Tab not exist");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		Result.fUpdateLog("------Billing Profile Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: SalesOrder
	 * Arguments			: None
	 * Use 					: Creates a Sales Order in the existing Account for Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String SalesOrder() {
		String Test_OutPut = "", Status = "";
		String Order_No = null;
		Result.fUpdateLog("------Sales Order Event Details------");
		try {

			// int j = 1;
			do {
				Browser.WebButton.click("Orders_Tab");
				CO.waitforload();
				if (CO.isAlertExist())
					Browser.WebButton.click("Orders_Tab");
				/*
				 * if (Browser.WebEdit.waitTillEnabled("Order_Valid_Name")) { j = 0; break; }
				 */

			} while (!Browser.WebTable.waitTillEnabled("Order_Table"));
			Browser.WebTable.waittillvisible("Order_Table");

			int Row = 2, Col, Col_new;
			Browser.WebButton.waitTillEnabled("Order_New");
			CO.scroll("Order_New", "WebButton");
			Browser.WebButton.click("Order_New");

			CO.waitforload();
			Col_new = CO.Actual_Cell("Order_Table", "Status");
			boolean flag = true;
			do {
				String orderstatus = Browser.WebTable.getCellData_title("Order_Table", 2, Col_new);
				if (orderstatus.equalsIgnoreCase("Pending")) {
					flag = false;
					break;
				}
			} while (flag);

			Col = CO.Get_Col("Order_Table", Row, "Sales Order");
			Browser.WebTable.click("Order_Table", Row, Col);
			Order_No = Browser.WebTable.getCellData("Order_Table", 2, (Col - 1));

			String OD_Date;
			Col_new = CO.Actual_Cell("Order_Table", "Order Date");
			Browser.WebTable.click("Order_Table", Row, Col_new);
			OD_Date = Browser.WebTable.getCellData_title("Order_Table", 2, Col_new);
			String[] Date = OD_Date.split(" ")[0].split("/");
			OrderDate.set((Date[1] + "-" + Date[0] + "-" + Date[2]));

			Browser.WebTable.click("Order_Table", Row, (Col - 1));
			do {
				CO.waitforload();
			} while (!Browser.WebLink.waitTillEnabled("Line_Items"));
			Browser.WebLink.waittillvisible("Line_Items");
			Browser.WebLink.click("Line_Items");
			CO.waitforload();
			Result.fUpdateLog(Billprofile_No);
			if (Billprofile_No != null) {
				CO.Webtable_Value("Billing Profile", Billprofile_No);
			}

			Browser.WebButton.waittillvisible("LI_New");

			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Utlities.StoreValue("Sales_OrderNO", Order_No);
				Test_OutPut += "Order_No : " + Order_No + ",";
				Utlities.StoreValue("Order_Creation_Date", OrderDate.get());
				Result.takescreenshot("Sales Order Created Order_No : " + Order_No);
			} else {
				Status = "FAIL";
				Test_OutPut += "Sales Order Creation Failed" + ",";
				Result.takescreenshot("Sales Order Creation Failed");
				Result.fUpdateLog("Sales Order Creation Failed");
			}

		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		Result.fUpdateLog("------Sales Order Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public String PlanSelection() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_pri, Col_cat;
			String Reserve, Category, GetData, Add_Addon, Remove_Addon, ReservationToken, StarNumber = null, SIM,
					MSISDN = null, SData = "SIM Card";
			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}
			Planname.set(PlanName);
			Test_OutPut += "PlanName : " + PlanName + ",";

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			// To use Catalog view uncomment the below lines
			// Browser.WebLink.click("VQ_Catalog");
			// CO.Category_Select(pulldata("PlanName"),pulldata("PlanName"),PlanName);
			// Browser.WebButton.click("LI_New");
			// CO.waitforload();

			// To use the catalog view comment the below line till '----'
			CO.scroll("LI_New", "WebButton");
			Browser.WebButton.click("LI_New");
			int Row = 2, Col;
			Col = CO.Select_Cell("Line_Items", "Product");
			Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", PlanName);
			Browser.WebTable.click("Line_Items", Row, Col + 1);
			CO.waitforload();
			// -----------------------

			int Row_Count = Browser.WebTable.getRowCount("Line_Items");

			Col_S = CO.Select_Cell("Line_Items", "Service Id");

			// To select the Mobile Bundle
			Col_V = Col + 2;

			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_V);

			if (!(getdata("Add_Addon").equals(""))) {
				Add_Addon = getdata("Add_Addon");
			} else {
				Add_Addon = pulldata("Add_Addon");
			}

			if (!(getdata("Remove_Addon").equals(""))) {
				Remove_Addon = getdata("Remove_Addon");
			} else {
				Remove_Addon = pulldata("Remove_Addon");
			}

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			Result.fUpdateLog("MSISDN : " + MSISDN);
			Test_OutPut += "MSISDN : " + MSISDN + ",";

			if (!(getdata("SIM").equals(""))) {
				SIM = getdata("SIM");
			} else {
				SIM = pulldata("SIM");
			}
			Result.fUpdateLog("SIM_NO : " + SIM);
			Test_OutPut += "SIM_NO : " + SIM + ",";

			if (!(getdata("StarNumber").equals(""))) {
				StarNumber = getdata("StarNumber");
			} else if (!(pulldata("StarNumber").equals(""))) {
				StarNumber = pulldata("StarNumber");
			}

			if (!(getdata("ReservationToken").equals(""))) {
				ReservationToken = getdata("ReservationToken");
			} else {
				ReservationToken = pulldata("ReservationToken");
			}

			if (Add_Addon != "" || Remove_Addon != "" || ReservationToken != "") {
				Browser.WebButton.click("Customize");
				if (ReservationToken != "") {
					Browser.WebEdit.waittillvisible("NumberReservationToken");
					Browser.WebEdit.Set("NumberReservationToken", ReservationToken);
					Result.takescreenshot("Providing Number Reservation Token");
				}
				CO.AddOnSelection(Add_Addon, "Add");
				CO.AddOnSelection(Remove_Addon, "Delete");
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist())
					Continue.set(false);
			}

			if (ReservationToken.equals("")) {
				CO.scroll("Numbers", "WebLink");
				Browser.WebLink.click("Numbers");
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Numbers");
				if (Row_Count == 1)
					Browser.WebButton.click("Number_Query");
				Browser.WebLink.click("Num_Manage");
				CO.waitforload();
				Browser.WebButton.waitTillEnabled("Reserve");
				Browser.WebButton.waittillvisible("Reserve");
				COl_STyp = CO.Select_Cell("Numbers", "Service Type");
				Col_Res = CO.Select_Cell("Numbers", "(Start) Number");
				Col_cat = CO.Select_Cell("Numbers", "Category");
				Col_pri = CO.Select_Cell("Numbers", "Price From");
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "Mobile");

				if (!MSISDN.equals("")) {

					Reserve = MSISDN.substring(3, MSISDN.length());
					Browser.WebTable.SetData("Numbers", Row, Col_Res, "Service_Id", Reserve);
					// Browser.WebButton.click("Number_Go");
					CO.waitmoreforload();
				} else {
					Browser.WebButton.click("Number_Go");
					CO.waitmoreforload();
					CO.waitforload();
					Browser.WebTable.click("Numbers", (Row + 1), Col);
					MSISDN = Browser.WebTable.getCellData("Numbers", (Row + 1), Col_Res);

				}

				Category = Browser.WebTable.getCellData("Numbers", Row, Col_cat);
				if (StarNumber == null) {
					StarNumber = Browser.WebTable.getCellData("Numbers", Row, Col_pri);
					StarNumber = StarNumber.substring(2, StarNumber.length());
				}

				Result.fUpdateLog("Category " + Category);
				Browser.WebButton.click("Reserve");
				CO.waitmoreforload();
				if (CO.isAlertExist()) {
					Result.takescreenshot("Number Reseved");
					Result.fUpdateLog("Alert Handled");
				}

				Browser.WebLink.waittillvisible("Line_Items");
				Browser.WebLink.click("Line_Items");
				CO.waitforload();
				// Browser.WebLink.click("LI_Totals");
				CO.waitforload();
				Col = CO.Actual_Cell("Line_Items", "Product");
				Row_Count = Browser.WebTable.getRowCount("Line_Items");

				if (Category.contains("STAR")) {

					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						if (GetData.equalsIgnoreCase(LData)) {
							Row_Val = i;
							break;
						}
					}
					Browser.WebTable.click("Line_Items", Row_Val, Col_V);
					CO.Text_Select("span", "Customize");
					CO.Link_Select("Others");
					CO.scroll("Star_Number_purch", "WebEdit");
					CO.waitforload();
					CO.Text_Select("option", "Default");
					CO.Text_Select("option", "For Testing Only");
					CO.waitforload();
					CO.scroll("Star_Number_purch", "WebEdit");
					Browser.WebEdit.Set("Star_Number_purch", StarNumber);

					CO.waitforload();
					CO.Text_Select("button", "Verify");
					CO.isAlertExist();
					CO.waitforload();
					CO.Text_Select("button", "Done");
					if (CO.isAlertExist()) {
						Continue.set(false);
						System.exit(0);
					}

				}
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				Col = CO.Actual_Cell("Line_Items", "Product");
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData)) {
						Row_Val = i;
					}
				}
				CO.waitforload();
				CO.waitforload();
				CO.Popup_Click("Line_Items", Row_Val, Col_S);
				CO.waitforload();
				Reserve = MSISDN.substring(3, MSISDN.length());
				CO.Popup_Selection("Number_Selection", "Number", Reserve);
				/*
				 * CO.waitforload();
				 * 
				 * Browser.WebButton.waittillvisible("Reserved_Ok");
				 * Browser.WebButton.waitTillEnabled("Reserved_Ok"); Row_Count =
				 * Browser.WebTable.getRowCount("Number_Selection"); if (Row_Count > 1)
				 * Browser.WebButton.click("Reserved_Ok"); else Continue.set(false);
				 */
			} else if (!ReservationToken.equals("")) {
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData))
						Row_Val = i;
				}
				Browser.WebTable.click("Line_Items", Row_Val, Col_S);
				Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", MSISDN);

			}
			// To Provide SIM No
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.waitforload();
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equalsIgnoreCase(LData))
					Row_Val = i;
			}

			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", SIM);
			Result.takescreenshot("Plan Selection is Successful : " + PlanName);

			Test_OutPut += OrderSubmission().split("@@")[1];
			CO.Action_Update("Add", MSISDN);

			if (Continue.get() & (Row_Count >= 3)) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Utlities.StoreValue("MSISDN", MSISDN);
				Utlities.StoreValue("SIM_NO", SIM);
				Result.fUpdateLog("Plan Selection for " + PlanName + "is done Successfully");
			} else {
				Status = "FAIL";
				Test_OutPut += "Plan Selection Failed" + ",";
				Result.takescreenshot("Plan Selection Failed");
				Result.fUpdateLog("Plan Selection Failed");
			}

		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: OrderSubmission
	 * Arguments			: None
	 * Use 					: Submits the Order, Waits for the Fulfillment Status and Displays the result 
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	public String OrderSubmission() {
		String Test_OutPut = "", Status = "";
		int COL_FUL_STATUS = 0;
		String OS_Status = "";
		Result.fUpdateLog("------Order Submission Event Details------");
		try {
			int Complete_Status = 0, Wait = 0, Row = 2, Col, Bill_Col, Row_Count;
			String EStatus = "Complete", FStatus = "Failed", Bill_Cycle;

			if (Browser.WebTable.exist("Line_Items"))
				Result.fUpdateLog("Proceeding Order Submission");
			CO.waitforload();
			if (UseCaseName.get().toLowerCase().contains("enterprise") || TestCaseN.get().toLowerCase().contains("vip")
					|| UseCaseName.get().contains("SIPT")) {
				CO.scroll("Ent_CreditLimit", "WebEdit");
				Browser.WebEdit.click("Ent_CreditLimit");
				Browser.WebEdit.Set("Ent_CreditLimit", "100");
			}

			// To get fulfillment status coloumn
			CO.scroll("Ful_Status", "WebButton");
			Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
			if (CO.Col_Data(Col).equalsIgnoreCase("fulfillment status"))
				COL_FUL_STATUS = Col;
			CO.scroll("Service", "WebButton");

			Browser.WebButton.waittillvisible("Validate");
			Browser.WebButton.click("Validate");

			// CO.isAlertExist();
			try {
				WebDriverWait wait = new WebDriverWait(cDriver.get(), 30);
				if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
					String popup = cDriver.get().switchTo().alert().getText();
					Result.fUpdateLog(popup);
					if (Validatedata("SmartLimit").equalsIgnoreCase("yes")) {
						String theDigits = CharMatcher.DIGIT.retainFrom(popup);
						Def_Smart_limit.set(theDigits);
					}
					if (popup.contains("Smart Limit")) {
						Continue.set(true);
					} else {
						Continue.set(false);
					}
				}
				Browser.alert.accept();
				Browser.Readystate();
			} catch (Exception e) {
				Result.fUpdateLog("No Alert Exist");
				e.getMessage();
			}

			if (Validatedata("SmartLimit").equalsIgnoreCase("yes") && !(Planname.get().contains("Mobile Broadband"))) {
				String Smartlimit = Utlities.FetchSmartlimit();
				if (Def_Smart_limit.get().equals(Smartlimit)) {
					Result.fUpdateLog("Default Smartlimit : " + Def_Smart_limit.get());
					Test_OutPut += "Default Smartlimit : " + Def_Smart_limit.get() + ",";
				} else {
					Continue.set(false);
				}
			}

			CO.waitmoreforload();
			if (Continue.get()) {
				switch (UseCaseName.get()) {
				case "ConsumerPostpaid_Provisioning":
					// case "Plan_UpgradeDowngrade":
				case "Consumer_Migration":
					switch (TestCaseN.get()) {
					case "NewCustomer":
					case "ExtCustomer":
						// case "ConsumerPostpaid":
					case "Prepaid_To_Postpaid":
						try {
							WebDriverWait wait = new WebDriverWait(cDriver.get(), 80);
							if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
								String popup = cDriver.get().switchTo().alert().getText();
								Result.fUpdateLog(popup);
								if (popup.contains("Smart Limit")) {
									Continue.set(true);
								} else {
									Continue.set(false);
								}
							}
							Browser.alert.accept();
							Browser.Readystate();
						} catch (Exception e) {
							Result.fUpdateLog("No Alert Exist");
							Continue.set(false);
							e.getMessage();
						}
						break;
					}

				}
			}

			if (Continue.get()) {
				Browser.WebButton.waittillvisible("Submit");
				CO.scroll("Submit", "WebButton");
				Browser.WebButton.click("Submit");
				CO.waitmoreforload();
				if (CO.isAlertExist()) {
					Continue.set(false);
				}
			}

			if (Continue.get()) {
				Result.takescreenshot("Order Submission is Successful");
				Col = COL_FUL_STATUS;
				cDriver.get().navigate().refresh();
				Browser.WebButton.waittillvisible("Submit");
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");

				do {
					Complete_Status = 0;
					// To refresh Page
					cDriver.get().navigate().refresh();
					Browser.WebButton.waittillvisible("Submit");
					CO.waitforload();

					for (int i = 2; i <= Row_Count; i++) {
						CO.scroll("Submit", "WebButton");
						CO.scroll("Ful_Status", "WebButton");
						OS_Status = Browser.WebTable.getCellData("Line_Items", i, Col);
						System.out.println("Round" + (i - 1) + " " + OS_Status);
						// To Find the Complete Status
						if (EStatus.equalsIgnoreCase(OS_Status)) {
							Complete_Status = Complete_Status + 1;
							if (Complete_Status == (Row_Count - 1)) {
								Wait = 101;
							}
						} else if (FStatus.equalsIgnoreCase(OS_Status)) {
							Continue.set(false);
							Wait = 101;
						}
					}
					Wait = Wait + 5;
					CO.waitmoreforload();
				} while (Wait < 100);
				Browser.WebButton.waittillvisible("Submit");

				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				CO.scroll("Submit", "WebButton");
				OS_Status = Browser.WebTable.getCellData("Line_Items", Row, Col);
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				Bill_Col = CO.Actual_Cell("Line_Items", "Bill Cycle");
				Bill_Cycle = Browser.WebTable.getCellData("Line_Items", Row, Bill_Col);
				billDate.set(Bill_Cycle);

				if (OS_Status.equalsIgnoreCase(EStatus) || Complete_Status == (Row_Count - 1)) {
					Continue.set(true);
				} else {
					Continue.set(false);
				}
			}
			CO.ToWait();
			if (Continue.get()) {
				Result.fUpdateLog("Order Status : " + OS_Status);
				Test_OutPut += "Order Status : " + OS_Status + ",";
				Result.takescreenshot("Order Status : " + OS_Status);
				Status = "PASS";
			} else {
				Result.fUpdateLog("Order Status : " + FStatus);
				Result.takescreenshot("Order Status : " + FStatus);
				Test_OutPut += "Order Status : " + FStatus + ",";
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Order Submission Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*-----------------------------------------------------------------------------------------------------
	 * Method Name			: Entp_AccountCreation
	 * Arguments			: None
	 * Use 					: Creates an Enterprise Account for Vanilla Journey
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Entp_AccountCreation() {
		String Test_OutPut = "", Status = "";
		String Account_No = "";
		Result.fUpdateLog("------Account Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				// Navigating to Accounts
				CO.waitforload();
				Browser.WebLink.waittillvisible("VQ_Account");
				CO.waitforobj("VQ_Account", "WebLink");
				Browser.WebLink.click("VQ_Account");
				CO.waitforload();

				// CO.Link_Select("My Accounts");
				CO.waitforobj("My_Account", "WebLink");
				Browser.WebLink.click("My_Account");

				CO.scroll("New_Account", "WebButton");
				Browser.WebButton.click("New_Account");
				String Acc;
				if (!(getdata("Account_Name").equals(""))) {
					Acc = getdata("Account_Name");
				} else if (!(pulldata("Account_Name").equals(""))) {
					Acc = pulldata("Account_Name") + R.nextInt(1000);
				} else {
					Acc = Utlities.randname() + R.nextInt(1000);
				}
				CO.scroll("Acc_Name", "WebEdit");
				Browser.WebEdit.Set("Acc_Name", Acc);

				if (!(getdata("CR_Type").equals(""))) {
					Browser.ListBox.select("CR_Type", getdata("CR_Type"));
				} else {
					Browser.ListBox.select("CR_Type", pulldata("CR_Type"));
				}

				Account_No = Browser.WebEdit.gettext("Account_No");
				Random R = new Random();
				if (!(getdata("CR_Type").equals(""))) {
					Browser.WebEdit.Set("CR_Number", getdata("CR_Number"));
				} else if (!(pulldata("CR_Number").equals(""))) {
					Browser.WebEdit.Set("CR_Number", pulldata("CR_Number"));
				} else {
					Browser.WebEdit.Set("CR_Number", "1" + R.nextInt(1000000));
				}

				if (!(getdata("SpecialManagement").equals(""))) {
					Browser.ListBox.select("Spcl_Mang", getdata("SpecialManagement"));
				} else {
					Browser.ListBox.select("Spcl_Mang", pulldata("SpecialManagement"));
				}

				CO.scroll("Customer_Segment", "ListBox");
				if (!(getdata("CustomerSegment").equals(""))) {
					Browser.ListBox.select("Customer_Segment", getdata("CustomerSegment"));
				} else {
					Browser.ListBox.select("Customer_Segment", pulldata("CustomerSegment"));
				}

				CO.waitforload();
				CO.scroll("Tier", "WebEdit");
				Browser.WebEdit.click("Tier");
				if (!(getdata("Tier").equals(""))) {
					Browser.WebEdit.Set("Tier", getdata("Tier"));
				} else {
					Browser.WebEdit.Set("Tier", pulldata("Tier"));
				}

				CO.Link_Select(Acc);
				do {
					CO.waitforload();
					Result.fUpdateLog("Loading...");
				} while (!Browser.WebLink.waitTillEnabled("Acc_Portal"));

				Browser.WebLink.click("Acc_Portal");

				Browser.WebLink.waittillvisible("Acc_Summary");

				CO.ToWait();
				if (Continue.get()) {
					Status = "PASS";
					Utlities.StoreValue("Account_No", Account_No);
					Test_OutPut += "Account_No : " + Account_No + ",";
					Result.takescreenshot("Account Created Account NO : " + Account_No);
				} else {
					Status = "FAIL";
					Test_OutPut += "Account Creation Failed" + ",";
					Result.takescreenshot("Account Creation Failed");
					Result.fUpdateLog("Account Creation Failed");
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Account Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Entp_ContactCreation0
	 * Arguments			: None
	 * Use 					: Creates a Contact in the Enterprise Account for Vanilla Journey
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Entp_ContactCreation() {
		String Test_OutPut = "", Status = "";
		String Last_Name = null;
		Result.fUpdateLog("------Contact Creation Event Details------");
		try {
			String Exi = getdata("Account_No");
			if (Exi.equals("")) {
				String Address;
				if (!(getdata("Address").equals(""))) {
					Address = getdata("Address");
				} else if (!(getdata("Kahramaa_ID").equals(""))) {
					Address = "Kar#" + getdata("Kahramaa_ID");
				} else if (!(pulldata("Kahramaa_ID").equals(""))) {
					Address = "Kar#" + pulldata("Kahramaa_ID");
				} else {
					Address = pulldata("Address");
				}

				do {
					Browser.WebButton.click("Address_Tab");
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Add_Address"));
				Browser.WebButton.waittillvisible("Add_Address");

				CO.waitforload();
				if (!(Address.equals(""))) {

					CO.waitforobj("Add_Address", "WebButton");
					// Browser.WebButton.waittillvisible("Add_Address");
					Browser.WebButton.click("Add_Address");

					// Search for Specific Address
					CO.waitforobj("Popup_Go", "WebButton");
					CO.scroll("Popup_Go", "WebButton");

					if (Address.contains("Kar#")) {
						Browser.ListBox.select("PopupQuery_List", "Kahramaa ID");
						Browser.WebEdit.Set("PopupQuery_Search", Address.split("#")[1]);
					} else {
						Browser.ListBox.select("PopupQuery_List", "Address Line 1");
						Browser.WebEdit.Set("PopupQuery_Search", Address);
					}
					CO.waitforload();
					Browser.WebButton.click("Popup_Go");

					CO.waitforload();
					CO.scroll("Add_OK", "WebButton");
					Browser.WebButton.click("Add_OK");
					/*
					 * do { Result.fUpdateLog("Page Loading....."); } while
					 * (Browser.WebButton.waitTillEnabled("Add_OK"));
					 */
					CO.waitforload();
					// Browser.WebButton.waittillvisible("Create_A/c");
				} else {
					String[] stat_add = AddressCreation().split("@@");
					Status = stat_add[0];
					Address = stat_add[1].split(",")[0];
				}
				Result.takescreenshot("Address Selected : " + Address);
				Result.fUpdateLog("Address Selected : " + Address);
				CO.waitforload();
				int x = 0;
				// int j = 1;
				do {
					CO.TabNavigator("Contacts");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("Contact_Valid_Name")) { j = 0; break; }
					 */
				} while (!Browser.WebEdit.waitTillEnabled("Contact_Valid_Name"));
				Browser.WebEdit.waittillvisible("Contact_Valid_Name");

				CO.waitforload();
				x = Browser.WebTable.getRowCount("Acc_Contact");
				if (x == 1) {
					Browser.WebButton.waittillvisible("Acc_Add_Contact");
					Browser.WebButton.click("Acc_Add_Contact");
				}
				int Row = 2, Col;
				Col = CO.Select_Cell("Acc_Contact", "First Name");
				if (!(getdata("FirstName").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", getdata("FirstName"));
				} else if (!(pulldata("FirstName").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", pulldata("FirstName"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", Utlities.randname());
				}

				Col = CO.Select_Cell("Acc_Contact", "Last Name");
				if (!(getdata("LastName").equals(""))) {
					Last_Name = getdata("LastName");
				} else if (!(pulldata("LastName").equals(""))) {
					Last_Name = pulldata("LastName") + R.nextInt(1000);
				} else {
					Last_Name = Utlities.randname();
				}
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Last_Name", Last_Name);

				Col = CO.Select_Cell("Acc_Contact", "Mr/Ms");
				if (!(getdata("Mr/Ms").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "M_M", getdata("Mr/Ms"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "M_M", pulldata("Mr/Ms"));
				}

				// Browser.WebTable.SetData("Acc_Contact", 2, Col, "Job_Title",getdata(""));

				/*
				 * Col = CO.Select_Cell("Acc_Contact", "Work Phone #");
				 * if(!(getdata("Cont_WorkPhone").equals(""))) {
				 * Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Work_Phone__",
				 * getdata("Cont_WorkPhone")); }else { Browser.WebTable.SetDataE("Acc_Contact",
				 * Row, Col, "Work_Phone__", "97498780980"); }
				 */

				Col = CO.Select_Cell("Acc_Contact", "Email");
				if (!(getdata("Email").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Email_Address", getdata("Email"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Email_Address", pulldata("Email"));
				}

				Col = CO.Select_Cell("Acc_Contact", "Date of Birth");
				if (!(getdata("DOB").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_DOB", getdata("DOB"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_DOB", pulldata("DOB"));
				}

				Col = CO.Select_Cell("Acc_Contact", "ID Expiration Date");
				if (!(getdata("IDExpiryDate").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date",
							getdata("IDExpiryDate"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date",
							pulldata("IDExpiryDate"));
				}

				Col = CO.Select_Cell("Acc_Contact", "ID Number");
				if (!(getdata("IDNumber").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number", getdata("IDNumber"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number",
							pulldata("IDNumber") + R.nextInt(100000));

				}

				// CO.scroll("Contact_ACC", "WebTable");

				// Col = CO.Select_Cell("Acc_Contact", "ID Type");
				Col++;
				if (!(getdata("IDType").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Type", getdata("IDType"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Type", pulldata("IDType"));
				}

				Col++;
				// Col = CO.Select_Cell("Acc_Contact", "Mobile Phone #");
				if (!(getdata("MobilePhone").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Cellular_Phone__", getdata("MobilePhone"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Cellular_Phone__", pulldata("MobilePhone"));
				}

				Col++;
				// Col = CO.Select_Cell("Acc_Contact", "Nationality");
				if (!(getdata("Nationality").equals(""))) {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_Nationality", getdata("Nationality"));
				} else {
					Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_Nationality", pulldata("Nationality"));
				}

				Col = Col + 4;
				// Col = CO.Select_Cell("Acc_Contact", "Gender");
				if (!(getdata("Gender").equals(""))) {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQA_M_F", getdata("Gender"));
				} else {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQA_M_F", pulldata("Gender"));
				}

				Col = CO.Select_Cell("Acc_Contact", "Preferred Language");
				if (!(getdata("PrefLang").equals(""))) {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQ_Preferred_Language", getdata("PrefLang"));
				} else {
					Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQ_Preferred_Language", pulldata("PrefLang"));
				}

				CO.waitforload();
				CO.scroll("Account360", "WebButton");
				Browser.WebButton.click("Address_Tab");
				if (CO.isAlertExist())
					if (CO.isAlertExist())
						Browser.WebButton.click("Address_Tab");

				CO.ToWait();
				if (Continue.get()) {
					Result.takescreenshot("Contact Ceated : " + Last_Name);
					Utlities.StoreValue("LastName", Last_Name);
					Utlities.StoreValue("Address", Address);
					Test_OutPut += "Address : " + Address + ",";
					Test_OutPut += "Contact : " + Last_Name + ",";
					Status = "PASS";
				} else {
					Test_OutPut += "Create_A/c button not exist" + ",";
					Result.takescreenshot("Create_A/c button not exist");
					Result.fUpdateLog("Create_A/c button not exist");
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Contact Creation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Modify
	 * Arguments			: None
	 * Use 					: Modification of Installed Assert is performed
	 * Designed By			: Vinodhini Raviprasad
	 * Latest Modified By	: Sravani
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Modify() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Add_Addon, Remove_Addon, Order_no, GetData;
		int Inst_RowCount, Col_P, Col_SID, Row_Count;

		Result.fUpdateLog("------Modify Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			if (!(getdata("Add_Addon").equals(""))) {
				Add_Addon = getdata("Add_Addon");
			} else {
				Add_Addon = pulldata("Add_Addon");
			}

			if (!(getdata("Remove_Addon").equals(""))) {
				Remove_Addon = getdata("Remove_Addon");
			} else {
				Remove_Addon = pulldata("Remove_Addon");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitmoreforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			// wait
			CO.waitmoreforload();
			CO.AddOnSelection(Add_Addon, "Add");
			CO.waitmoreforload();
			CO.AddOnSelection(Remove_Addon, "Delete");
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			if (CO.isAlertExist()) {
				Continue.set(false);
				System.out.println("Error On Clicking Done Button");
				System.exit(0);
			}
			/*
			 * // Use for "Ent_CreditLimit" String Product_Type; if
			 * (!(getdata("Product_Type").equals(""))) { Product_Type =
			 * getdata("Product_Type"); } else { Product_Type = pulldata("Product_Type"); }
			 * if (Product_Type.equals("Enterprise") || Product_Type.equals("VIP"))
			 * Browser.WebEdit.Set("Ent_CreditLimit", "100");// click("Ent_CreditLimit");
			 * else Browser.WebEdit.click("Credit_Limit");
			 */
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			// Add_Addon = "Paid Addons,Vodafone Passport";
			LineItemData.clear();
			CO.Status(Add_Addon);
			CO.waitforload();
			CO.Status(Remove_Addon);
			CO.waitforload();

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("Modification is Successful");
			} else
				Status = "FAIL";
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("Modify Event  Details - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Plan_UpgradeDowngrade
	 * Arguments			: None
	 * Use 					: Change of Plan
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Plan_UpgradeDowngrade() {

		String Test_OutPut = "", Status = "";
		String MSISDN, New_PlanName, GetData, Order_no;
		int Col, Col_P;
		Result.fUpdateLog("------Plan Upgrade/Downgrade Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_PlanName").equals(""))) {
				New_PlanName = getdata("New_PlanName");
			} else {
				New_PlanName = pulldata("New_PlanName");
			}
			Planname.set(New_PlanName);

			/*
			 * if (!(getdata("Existing_PlanName").equals(""))) { Existing_Plan =
			 * getdata("Existing_PlanName"); } else { Existing_Plan =
			 * pulldata("Existing_PlanName"); }
			 */
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.Plan_selection(GetData, MSISDN);
			CO.waitmoreforload();
			Browser.WebEdit.Set("PopupQuery_Search", New_PlanName);
			String Path[] = Utlities.FindObject("PopupQuery_Search", "WebEdit");
			cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
			CO.waitforload();

			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {
				CO.scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");
			} else {
				Continue.set(false);
				System.exit(0);
			}

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (LData.equalsIgnoreCase(New_PlanName)) {
					if (Action.equalsIgnoreCase("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				} /*
					 * else if (LData.equalsIgnoreCase(Existing_Plan)) { if
					 * (Action.equalsIgnoreCase("Delete")) { Result.fUpdateLog("Action Update   " +
					 * LData + ":" + Action); } else { Result.fUpdateLog(LData + ":" + Action);
					 * Continue.set(false); } }
					 */
			}
			if (Row_Count1 <= 4) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();

			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();
			Test_OutPut += OrderSubmission().split("@@")[1];

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
			} else {
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Plan Upgrade/Downgrade Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: SIMSWAP
	 * Arguments			: None
	 * Use 					: Sim Swap from Vanilla
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 09-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String SIMSwap() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Order_no, GetData, SIM, SData = "SIM Card";
		int Inst_RowCount, Col, Col_P, Col_S, Col_SID, Row_Val = 3, Row_Count;

		Result.fUpdateLog("------SIM Swap services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_SIM").equals(""))) {
				SIM = getdata("New_SIM");
			} else {
				SIM = pulldata("New_SIM");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.waitforload();
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			if (CO.isAlertExist()) {
				Continue.set(false);
				System.out.println("Error On Clicking Done Button");
				System.exit(0);
			}

			Result.takescreenshot("");

			CO.scroll("Line_Items", "WebTable");
			Browser.WebButton.waittillvisible("Expand");
			Browser.WebButton.click("Expand");
			Row_Count = Browser.WebTable.getRowCount("Line_Items");

			CO.waitforload();
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_S = CO.Select_Cell("Line_Items", "Service Id");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equalsIgnoreCase(LData)) {
					Row_Val = i;
					break;

				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", SIM);

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			CO.Action_Update("Update", "");
			Result.takescreenshot("");

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("SIMSWAP is Successful");
			} else
				Status = "FAIL";

		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("SIMSWAP - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Change_MSISDN
	 * Arguments			: None
	 * Use 					: Change MSISDN from Vanilla
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 22-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Change_MSISDN() {
		String Test_OutPut = "", Status = "";
		String Order_no, GetData, New_MSISDN;
		int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_cat, Col_pri;
		String Reserve, Category = "", StarNumber = null, ReservationToken = "", MSISDN = null;
		int Inst_RowCount, Col, Col_P, Col_SID, Row_Count;

		Result.fUpdateLog("------Change MSISDN services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("NEW_MSISDN").equals(""))) {
				New_MSISDN = getdata("NEW_MSISDN");
			} else {
				New_MSISDN = pulldata("NEW_MSISDN");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.waitforload();
			CO.Title_Select("a", "Home");

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.waitforload();

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");

			if (CO.isAlertExist()) {
				Continue.set(false);
				System.exit(0);
			}

			Result.takescreenshot("");

			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			int Row = 2;
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_S = CO.Select_Cell("Line_Items", "Service Id");
			Col_V = Col + 2;

			if (!(getdata("ReservationToken").equals(""))) {
				ReservationToken = getdata("ReservationToken");
			} else {
				ReservationToken = pulldata("ReservationToken");
			}

			if (!(getdata("StarNumber").equals(""))) {
				StarNumber = getdata("StarNumber");
			} else if (!(pulldata("StarNumber").equals(""))) {
				StarNumber = pulldata("StarNumber");
			}

			if (ReservationToken != "") {
				Browser.WebButton.click("Customize");
				Browser.WebEdit.waittillvisible("NumberReservationToken");
				Browser.WebEdit.Set("NumberReservationToken", ReservationToken);
				Result.takescreenshot("Providing Number Reservation Token");

				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist())
					Continue.set(false);
			}

			if (ReservationToken.equals("")) {
				CO.scroll("Numbers", "WebLink");
				Browser.WebLink.click("Numbers");
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Numbers");
				if (Row_Count == 1)
					Browser.WebButton.click("Number_Query");
				Browser.WebLink.click("Num_Manage");
				CO.waitforload();
				Browser.WebButton.waitTillEnabled("Reserve");
				Browser.WebButton.waittillvisible("Reserve");
				COl_STyp = CO.Select_Cell("Numbers", "Service Type");
				Col_Res = CO.Select_Cell("Numbers", "(Start) Number");
				Col_cat = CO.Select_Cell("Numbers", "Category");
				Col_pri = CO.Select_Cell("Numbers", "Price From");
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "Mobile");

				if (!New_MSISDN.equals("")) {
					Reserve = New_MSISDN.substring(3, New_MSISDN.length());
					Browser.WebTable.SetData("Numbers", Row, Col_Res, "Service_Id", Reserve);
					// Browser.WebButton.click("Number_Go");
					CO.waitmoreforload();
				} else {
					Browser.WebButton.click("Number_Go");
					CO.waitmoreforload();
					CO.waitforload();
					Browser.WebTable.click("Numbers", (Row + 1), Col);
					MSISDN = Browser.WebTable.getCellData("Numbers", (Row + 1), Col_Res);
				}

				Category = Browser.WebTable.getCellData("Numbers", Row, Col_cat);
				if (StarNumber == null) {
					StarNumber = Browser.WebTable.getCellData("Numbers", Row, Col_pri);
					StarNumber = StarNumber.substring(2, StarNumber.length());
				}
				Result.fUpdateLog("Category " + Category);
				Browser.WebButton.click("Reserve");
				CO.waitmoreforload();
				if (CO.isAlertExist()) {
					Result.takescreenshot("Number Resered");
					Result.fUpdateLog("Alert Handled");
				}

				Browser.WebLink.waittillvisible("Line_Items");
				Browser.WebLink.click("Line_Items");
				CO.waitforload();
				// Browser.WebLink.click("LI_Totals");
				CO.waitforload();

				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Category.contains("STAR")) {

					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
						if (GetData.equalsIgnoreCase(LData)) {
							Row_Val = i;
							break;
						}
					}
					Browser.WebTable.click("Line_Items", Row_Val, Col_V);
					CO.Text_Select("span", "Customize");
					CO.Link_Select("Others");
					CO.scroll("Star_Number_purch", "WebEdit");
					CO.waitforload();
					CO.Text_Select("option", "Default");
					CO.Text_Select("option", "For Testing Only");
					CO.waitforload();
					CO.scroll("Star_Number_purch", "WebEdit");
					CO.waitforload();
					Browser.WebEdit.Set("Star_Number_purch", StarNumber);
					CO.waitforload();
					CO.Text_Select("button", "Verify");
					CO.isAlertExist();
					CO.waitforload();
					CO.Text_Select("button", "Done");
					if (CO.isAlertExist()) {
						Continue.set(false);
						System.exit(0);
					}

				}
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				Col = CO.Actual_Cell("Line_Items", "Product");
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData)) {
						Row_Val = i;
					}
				}
				CO.waitforload();
				CO.waitforload();
				CO.Popup_Click("Line_Items", Row_Val, Col_S);
				CO.waitforload();
				Reserve = New_MSISDN.substring(3, New_MSISDN.length());
				CO.Popup_Selection("Number_Selection", "Number", Reserve);
				CO.waitforload();

			} else if (!ReservationToken.equals("")) {
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}
				Col_S = CO.Actual_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData))
						Row_Val = i;
				}
				Browser.WebTable.click("Line_Items", Row_Val, Col_S);
				Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", New_MSISDN);

			}
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			CO.waitforload();

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			CO.RTBScreen(New_MSISDN, "Active");
			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("MSISDN Change is Successful");
			} else {
				Status = "FAIL";
				Result.takescreenshot("MSISDN not Changed");
			}
		}

		catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("MSISDN Change - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Consumer_Migration
	 * Arguments			: None
	 * Use 					: Consumer Migration from Pre_to_Post and Post_to_Pre
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 11-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Consumer_Migration() {

		String Test_OutPut = "", Status = "";
		String MSISDN, New_PlanName, GetData, Order_no;
		int Col, Col_P;
		Result.fUpdateLog("------Consumer_Migration Event Details------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_PlanName").equals(""))) {
				New_PlanName = getdata("New_PlanName");
			} else {
				New_PlanName = pulldata("New_PlanName");
			}
			Planname.set(New_PlanName);

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();

			BillingProfileCreation();
			CO.waitforload();
			CO.Text_Select("a", "Account Summary");
			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
			CO.waitforload();
			CO.InstalledAssertChange("New Query                   [Alt+Q]");
			Col = CO.Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.Plan_selection(GetData, MSISDN);
			CO.waitforload();
			CO.waitforload();
			Browser.WebEdit.Set("PopupQuery_Search", New_PlanName);
			String Path[] = Utlities.FindObject("PopupQuery_Search", "WebEdit");
			cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
			Result.takescreenshot("");
			CO.waitforload();
			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {
				CO.scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");
				do {
					Result.fUpdateLog("Page Loading.....");
				} while (!Browser.WebButton.waitTillEnabled("LI_New"));
			} else {
				Continue.set(false);
				System.exit(0);
			}
			CO.waitforload();
			if (Billprofile_No != null) {
				CO.Webtable_Value("Billing Profile", Billprofile_No);
			}
			Result.takescreenshot("");
			CO.scroll("Line_Items", "WebTable");
			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			int Col_bp = CO.Actual_Cell("Line_Items", "Billing Profile");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				if (LData.equalsIgnoreCase(GetData) || LData.equalsIgnoreCase(New_PlanName)) {
					CO.Popup_Click("Line_Items", i, Col_bp);
					CO.waitforload();
					CO.Popup_Selection("Bill_Selection", "Name", Billprofile_No);
					Result.takescreenshot("");
				}
				if (LData.equalsIgnoreCase(New_PlanName)) {
					if (Action.equalsIgnoreCase("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				}
			}
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 4) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();

			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();
			Test_OutPut += OrderSubmission().split("@@")[1];

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
			} else {
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Consumer_Migration Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Enterprise_Migration
	 * Arguments			: None
	 * Use 					: Enterprise Migration from Pre_to_Post and Post_to_Pre
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 11-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Enterprise_Migration() {

		String Test_OutPut = "", Status = "";
		String MSISDN, New_PlanName, GetData, Order_no;
		int Col, Col_P;
		Result.fUpdateLog("------Consumer_Migration Event Details------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_PlanName").equals(""))) {
				New_PlanName = getdata("New_PlanName");
			} else {
				New_PlanName = pulldata("New_PlanName");
			}
			Planname.set(New_PlanName);

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);

			CO.waitforload();
			BillingProfileCreation();
			CO.waitforload();
			CO.Text_Select("a", "Account Summary");
			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
			CO.waitforload();
			CO.InstalledAssertChange("New Query                   [Alt+Q]");
			Col = CO.Select_Cell("Installed_Assert", "Service ID");
			Browser.WebTable.SetDataE("Installed_Assert", 2, Col, "Serial_Number", MSISDN);
			Browser.WebButton.click("InstalledAssert_Go");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.Plan_selection(GetData, MSISDN);
			CO.waitforload();
			CO.waitforload();
			Browser.WebEdit.Set("PopupQuery_Search", New_PlanName);
			String Path[] = Utlities.FindObject("PopupQuery_Search", "WebEdit");
			cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
			Result.takescreenshot("");
			CO.waitforload();
			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {
				CO.scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");
				do {
					Result.fUpdateLog("Page Loading.....");
				} while (!Browser.WebButton.waitTillEnabled("LI_New"));
			} else {
				Continue.set(false);
				System.exit(0);
			}
			CO.waitforload();
			if (Billprofile_No != null) {
				CO.Webtable_Value("Billing Profile", Billprofile_No);
			}
			Result.takescreenshot("");
			CO.scroll("Line_Items", "WebTable");
			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			int Col_bp = CO.Actual_Cell("Line_Items", "Billing Profile");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
				if (LData.equalsIgnoreCase(GetData) || LData.equalsIgnoreCase(New_PlanName)) {
					CO.Popup_Click("Line_Items", i, Col_bp);
					CO.waitforload();
					CO.Popup_Selection("Bill_Selection", "Name", Billprofile_No);
					Result.takescreenshot("");
				}
				if (LData.equalsIgnoreCase(New_PlanName)) {
					if (Action.equalsIgnoreCase("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				}
			}
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 4) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.LineItems_Data();

			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();
			Test_OutPut += OrderSubmission().split("@@")[1];

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
			} else {
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Enterprise_Migration Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: RealTimeBalance_Screen
	 * Arguments			: None
	 * Use 					: to take RealTimeBalance Screenshort
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String RealTimeBalance_Screen() {
		String Test_OutPut = "", Status = "";
		String MSISDN;
		Result.fUpdateLog("------RealTimeBalance_Screen Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			CO.waitforload();
			CO.RTBScreen(MSISDN, "Active");
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------RealTimeBalance_Screen Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Change_SmartLimit
	 * Arguments			: None
	 * Use 					: Change of Smart limit vanilla flow
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Change_SmartLimit() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData = null, Order_no;
		int Inst_RowCount, Col_P, Col_SID, Col, Col_s, row_value = 0;
		String SL_LimitAmount;
		Result.fUpdateLog("------Change SmartLimit Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			if (!(getdata("SL_LimitAmount").equals(""))) {
				SL_LimitAmount = getdata("SL_LimitAmount");
			} else {
				SL_LimitAmount = pulldata("SL_LimitAmount");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitmoreforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			// wait
			CO.waitmoreforload();
			CO.Link_Select("Others");
			CO.waitforload();
			CO.Radio_Select("Smart Limit");
			CO.waitforload();
			CO.Addon_Settings("Smart Limit");
			CO.waitmoreforload();
			Result.takescreenshot("");

			// CO.waitforload();
			Browser.WebEdit.clear("SL_LimitAmount");
			Browser.WebEdit.Set("SL_LimitAmount", SL_LimitAmount);
			String SL_Min_Value = Browser.WebEdit.gettext("SL_Min_Value");
			int SL_Min = Integer.parseInt(SL_Min_Value);
			int SL_Limit = Integer.parseInt(SL_LimitAmount);
			if (SL_Limit > SL_Min) {

				Continue.set(true);
			} else {
				Result.fUpdateLog("SL_LimitAmount is less than SL_Min_Value");
				Continue.set(false);

			}
			if (Continue.get()) {

				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				if (CO.isAlertExist()) {
					Continue.set(false);
					System.out.println("Error On Clicking Done Button");
					System.exit(0);
				}
				Result.takescreenshot("");
				CO.waitforload();
				Test_OutPut += OrderSubmission().split("@@")[1];
				CO.waitforload();

				// fetching Order_no
				Order_no = CO.Order_ID();
				Utlities.StoreValue("Order_no", Order_no);
				Test_OutPut += "Order_no : " + Order_no + ",";

				CO.Assert_Search(MSISDN, "Active");
				CO.waitforload();
				CO.Text_Select("a", GetData);
				CO.waitforload();

				int Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
				Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col = CO.Actual_Cell("Acc_Installed_Assert", "Product");
				Col_s = CO.Actual_Cell("Acc_Installed_Assert", "Special Rating List");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col);
					if (LData.equalsIgnoreCase("Smart Limit")) {
						// Browser.WebTable.click("Acc_Installed_Assert", i, Col_s);
						row_value = i;
						break;

					}
				}

				Browser.WebTable.click("Acc_Installed_Assert", row_value, Col_s);
				CO.waitforload();
				CO.Text_Select("a", "Installed Assets");
				CO.waitforload();
				CO.scroll("Attribute", "WebEdit");
				CO.Title_Select("td", "SL Default Value");
				CO.waitforload();

				String Amt = cDriver.get()
						.findElement(By.xpath("//td[.='SL Limit Amount']/..//td[contains(@id,'Value')]"))
						.getAttribute("title");
				CO.waitforload();
				if (SL_LimitAmount.equalsIgnoreCase(Amt.trim())) {

					Continue.set(true);
				} else {
					Result.fUpdateLog("SL_LimitAmount is less than SL_Min_Value");
					Continue.set(false);

				}

				CO.ToWait();
				CO.GetSiebelDate();
				if (Continue.get()) {
					Status = "PASS";
					Utlities.StoreValue("Sales_OrderNO", Order_no);
					Test_OutPut += "Order_No : " + Order_no + ",";
				} else {
					Status = "FAIL";
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		Result.fUpdateLog("-----Change SmartLimit Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name				: BillPayment
	 * Arguments				: None
	 * Use 						: BillPayment
	 * Designed By				: Vinodhini Raviprasad
	 * Last Modified Date 		: 12-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public String BillPayment() {

		String Test_OutPut = "", Status = "";
		String MSISDN, GetData, Channel, BillingProfile, BillAmt = "", Pay_Type, Reference;
		int Row_Count, Col_P, Col, Col_C, Col_A, Row = 2;
		Result.fUpdateLog("------BillPayment Event Details------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			if (!(getdata("Pay_Type").equals(""))) {
				Pay_Type = getdata("Pay_Type");
			} else {
				Pay_Type = pulldata("Pay_Type");
			}

			if (!(getdata("Channel").equals(""))) {
				Channel = getdata("Channel");
			} else {
				Channel = pulldata("Channel");
			}
			if (!(getdata("Reference").equals(""))) {
				Reference = getdata("Reference");
			} else {
				Reference = R.nextInt(100000) + pulldata("Reference") + R.nextInt(100000000);
			}
			// Fetching Billing Profile Name from the Provided MSISDN
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			Col_P = CO.Select_Cell("Acc_Installed_Assert", "Billing Profile");// Browser.WebTable.getRowCount("Acc_Installed_Assert");
			CO.waitforload();
			BillingProfile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_P);
			CO.waitforload();
			Result.takescreenshot("Bill No for the MSISDN " + MSISDN + " is " + BillingProfile);

			if (Pay_Type.equalsIgnoreCase("outstanding")) {
				CO.Link_Select("Profiles");
				CO.waitforload();
				Browser.WebButton.click("Profile_Query");
				Col_P = CO.Select_Cell("Bill_Prof", "Name");
				Col = CO.Select_Cell("Bill_Prof", "Status");
				Browser.WebTable.SetData("Bill_Prof", Row, Col_P, "Name", BillingProfile);
				CO.waitforload();
				if (Browser.WebTable.getRowCount("Bill_Prof") >= 2) {
					Browser.WebTable.click("Bill_Prof", Row, Col);
					Browser.WebTable.clickL("Bill_Prof", Row, Col_P);
				} else
					Continue.set(false);

				CO.waitforload();
				BillAmt = Browser.WebEdit.gettext("Balance");
				Test_OutPut += "Balance: " + BillAmt + ",";
				Result.takescreenshot("Getting Outstanding Balance" + BillAmt);
				CO.Assert_Search(MSISDN, "Active");
				CO.waitforload();
				CO.Text_Select("a", GetData);
				CO.waitforload();

			} else {
				if (!(getdata("BillAmt").equals(""))) {
					BillAmt = getdata("BillAmt");
				} else {
					BillAmt = pulldata("BillAmt");
				}
			}

			CO.Link_Select("Payments");
			CO.waitforload();
			Result.takescreenshot("Account level Payment");
			Col_P = CO.Select_Cell("AccountPayment", "Billing Profile");
			Col_C = CO.Select_Cell("AccountPayment", "Payment_Method");
			Col_A = CO.Select_Cell("AccountPayment", "Payment_Amount");
			CO.waitforobj("Pay_Add", "WebButton");
			Browser.WebButton.click("Pay_Add");
			String Bill_Status = "";
			Row = 2;

			do {
				int Col_S = CO.Actual_Cell("AccountPayment", "Status");
				Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
				if ((Bill_Status.equalsIgnoreCase("Open"))) {
					break;
				}
			} while (true);

			CO.waitforload();

			Browser.WebTable.SetDataE("AccountPayment", Row, Col_A, "Payment_Amount", BillAmt);
			Browser.WebTable.SetData("AccountPayment", Row, Col_C, "Payment_Method", Channel);
			Browser.WebTable.click("AccountPayment", Row, Col_P);
			Browser.WebTable.SetData("AccountPayment", Row, Col_P, "VFQA_Bill_Prof_Name", BillingProfile);

			CO.isAlertExist();
			if (Channel.equalsIgnoreCase("cash")) {
				CO.scroll("Reference_Number", "WebEdit");
				Browser.WebEdit.Set("Reference_Number", Reference);
			} else if (Channel.equalsIgnoreCase("cheque")) {
				Browser.WebEdit.Set("Cheque_Number", getdata("Cheque_Number"));
				Browser.WebEdit.Set("Bank_Name", getdata("Bank_Name"));

			} else if (Channel.equalsIgnoreCase("online")) {
				Browser.WebEdit.Set("Voucher_Number", getdata("Voucher_Number"));
				Browser.WebEdit.Set("Reference_Number", Reference);
			} else if (Channel.equalsIgnoreCase("voucher")) {
				Browser.WebEdit.Set("Voucher_Number", getdata("Voucher_Number"));
				Browser.WebEdit.Set("Reference_Number", Reference);
			}
			int Col_S = CO.Select_Cell("AccountPayment", "Channel Transaction #");
			String Txn = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);

			Browser.WebButton.click("Bill_Submit");
			CO.waitmoreforload();
			Result.takescreenshot("Bill Submittion for Payment");
			Browser.WebButton.click("Payment_Query");
			Browser.WebTable.SetData("AccountPayment", Row, Col_S, "VFQA_Channel_Transaction__", Txn);
			CO.waitforload();

			Col = CO.Select_Cell("AccountPayment", "Payment #");
			String Payment_Reference = Browser.WebTable.getCellData("AccountPayment", Row, Col);
			Test_OutPut += "Payment Reference Number:" + Payment_Reference + ",";

			Col_S = CO.Select_Cell("AccountPayment", "Status");
			Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
			if ((Bill_Status.equalsIgnoreCase("Submitted"))) {
				CO.Link_Select("Account Summary");
				CO.waitforload();
				CO.Link_Select("Payments");
				CO.waitforload();
				Browser.WebButton.click("Payment_Query");
				CO.waitforload();
				Browser.WebTable.SetData("AccountPayment", Row, Col, "Payment_Number", Payment_Reference);
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("AccountPayment");
				if (Row_Count == 1)
					Continue.set(false);
				Bill_Status = Browser.WebTable.getCellData("AccountPayment", Row, Col_S);
				Result.takescreenshot("Payment Status Verification" + Payment_Reference);

			} else if ((Bill_Status.equalsIgnoreCase("Success"))) {
				Continue.set(true);
			} else
				Continue.set(false);

			// To verify whether the Payment is reflected in Billing Profile

			CO.Link_Select("Profiles");
			CO.waitforload();
			Browser.WebButton.click("Profile_Query");
			Col_P = CO.Select_Cell("Bill_Prof", "Name");
			Col = CO.Select_Cell("Bill_Prof", "Status");
			Browser.WebTable.SetData("Bill_Prof", Row, Col_P, "Name", BillingProfile);
			Browser.WebTable.click("Bill_Prof", Row, Col);
			Browser.WebTable.clickL("Bill_Prof", Row, Col_P);
			do {
				CO.waitforload();
			} while (!Browser.WebButton.waitTillEnabled("Bill_Valid_Name"));
			CO.waitforload();

			if (Bill_Status.equalsIgnoreCase("success") & Pay_Type.equalsIgnoreCase("outstanding")) {
				String Outstanding = "";
				Outstanding = Browser.WebEdit.gettext("Balance");
				Result.takescreenshot("Outstanding after Payment");
				if (!(Outstanding.equalsIgnoreCase("qr0.00")))
					Continue.set(false);
			}

			CO.TabNavigator("Payments");
			CO.waitforload();
			Col_S = CO.Select_Cell("Payments", "Status");
			Col = CO.Select_Cell("Payments", "Payment #");
			Browser.WebButton.click("Payment_Query");
			CO.waitforload();
			Browser.WebTable.SetData("Payments", 2, Col, "Payment_Number", Payment_Reference);
			CO.waitforload();
			Result.takescreenshot("Payment Verification Bill level");
			Row_Count = Browser.WebTable.getRowCount("Payments");

			CO.ToWait();
			if (Continue.get() & (Row_Count > 1)
					& (Bill_Status.equalsIgnoreCase("success") || Bill_Status.equalsIgnoreCase("approved"))) {
				Test_OutPut += "";
				Result.takescreenshot("BillPayment is Successfull");
				Result.fUpdateLog("BillPayment is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "BillPayment Failed" + ",";
				Result.takescreenshot("BillPayment Failed");
				Result.fUpdateLog("BillPayment Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------BillPayment Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Disconnect
	 * Arguments			: None
	 * Use 					: Disconnection of Active line
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Disconnection() {

		String Test_OutPut = "", Status = "";
		String MSISDN, Order_no, Order_Reason, GetData;
		int Col, Col_P;
		Result.fUpdateLog("------Disconnect Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("Order_Reason").equals(""))) {
				Order_Reason = getdata("Order_Reason");
			} else {
				Order_Reason = pulldata("Order_Reason");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			int Col_S, Row_Count;
			String LData;
			Col = CO.Actual_Cell("Installed_Assert", "Product");
			Col_S = CO.Actual_Cell("Installed_Assert", "Service ID");
			Row_Count = Browser.WebTable.getRowCount("Installed_Assert");
			for (int i = 2; i <= Row_Count; i++) {
				LData = Browser.WebTable.getCellData("Installed_Assert", i, Col);
				if (LData.equalsIgnoreCase(GetData)) {
					if ((i % 2) == 0) {
						Browser.WebTable.click("Installed_Assert", (i + 1), Col_S);
						CO.waitforload();
						break;
					} else {
						Browser.WebTable.click("Installed_Assert", (i - 1), Col_S);
						CO.waitforload();
						break;
					}
				}

			}
			do {
				Browser.WebButton.click("VFQ_Disconnect");
				String x = Browser.WebEdit.gettext("Due_Date");
				if (!x.contains("/")) {
					Browser.WebButton.click("Date_Cancel");
					Browser.WebButton.click("VFQ_Disconnect");
				}
				CO.waitforload();
			} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			if (Browser.WebEdit.gettext("Due_Date").equals(""))
				Continue.set(false);
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();
			Result.takescreenshot("Disconnect Order : ");
			// CO.InstalledAssertChange("Disconnect");
			CO.waitforload();
			CO.Webtable_Value("Order Reason", Order_Reason);

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (Action.equalsIgnoreCase("Delete")) {
					Result.fUpdateLog("Action Update   " + LData + ":" + Action);
				} else {
					Result.fUpdateLog(LData + ":" + Action);
					Continue.set(false);
				}

			}

			Test_OutPut += OrderSubmission().split("@@")[1];
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.waitforload();

			CO.AssertSearch(MSISDN, "Inactive");
			CO.waitforload();
			Result.takescreenshot("");
			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
			} else {
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Disconnect Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TroubleTicket
	 * Arguments			: None
	 * Use 					: To raise TroubleTicket
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 26-Nov-2017
	--------------------------------------------------------------------------------------------------------*/
	public String TroubleTicket() {
		String Test_OutPut = "", Status = "";
		String MSISDN, TT_No;

		Result.fUpdateLog("------TroubleTicket services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.TabNavigator("Trouble Tickets");

			int Row = 2, Col;
			Browser.WebButton.waitTillEnabled("TT_New");
			CO.scroll("TT_New", "WebButton");
			Browser.WebButton.click("TT_New");
			CO.waitforload();

			Col = CO.Actual_Cell("TroubleTicket", "Ticket Id");
			TT_No = Browser.WebTable.getCellData("TroubleTicket", Row, Col);
			Result.takescreenshot("Trouble ticket raised : " + TT_No);
			Utlities.StoreValue("TroubleTicket_No", TT_No);
			Test_OutPut += "Trouble Ticket No: " + TT_No + ",";
			Browser.WebTable.clickL("TroubleTicket", Row, Col);
			CO.waitforload();

			Browser.ListBox.waitTillEnabled("TT_Source");
			if (!(getdata("TT_Source").equals(""))) {
				Browser.ListBox.select("TT_Source", getdata("TT_Source"));
			} else {
				Browser.ListBox.select("TT_Source", pulldata("TT_Source"));
			}

			if (!(getdata("TT_TicketType").equals(""))) {
				Browser.ListBox.select("TT_TicketType", getdata("TT_TicketType"));
			} else {
				Browser.ListBox.select("TT_TicketType", pulldata("TT_TicketType"));
			}

			if (!(getdata("TT_Area").equals(""))) {
				Browser.ListBox.select("TT_Area", getdata("TT_Area"));
			} else {
				Browser.ListBox.select("TT_Area", pulldata("TT_Area"));
			}

			if (!(getdata("TT_Sub_Area").equals(""))) {
				Browser.ListBox.select("TT_SubArea", getdata("TT_Sub_Area"));
			} else {
				Browser.ListBox.select("TT_SubArea", pulldata("TT_Sub_Area"));
			}

			Browser.WebButton.waitTillEnabled("TT_MSISDN");
			Browser.WebButton.click("TT_MSISDN");

			CO.waitforobj("Popup_Go", "WebButton");
			CO.scroll("Popup_Go", "WebButton");

			Browser.ListBox.select("PopupQuery_List", "Serial #");
			Browser.WebEdit.Set("PopupQuery_Search", MSISDN);

			CO.waitforload();
			Browser.WebButton.click("Popup_Go");
			CO.waitforload();
			Result.takescreenshot("");
			// Browser.WebEdit.Set("TT_Description", "");
			CO.Webtable_Value("Contact Role", "");

			CO.Title_Select("a", "Trouble Tickets");
			Browser.WebEdit.waitTillEnabled("TT_NumberSearch");
			CO.scroll("TT_NumberSearch", "WebEdit");
			Browser.WebEdit.Set("TT_NumberSearch", TT_No);
			CO.scroll("TT_SearchGo", "WebButton");
			Browser.WebButton.click("TT_SearchGo");

			do {
				CO.waitforload();
			} while (!Browser.WebTable.waitTillEnabled("TT_Table"));
			Col = CO.Actual_Cell("TT_Table", "Ticket Id");
			Browser.WebTable.click("TT_Table", Row, Col);

			Browser.WebEdit.waitTillEnabled("TT_Description");
			if (!(getdata("TT_Description").equals(""))) {
				Browser.WebEdit.Set("TT_Description", getdata("TT_Description"));
			} else {
				Browser.WebEdit.Set("TT_Description", pulldata("TT_Description"));
			}

			if (!(getdata("TT_Status").equals(""))) {
				Browser.ListBox.select("TT_Status", getdata("TT_Status"));
			} else {
				Browser.ListBox.select("TT_Status", pulldata("TT_Status"));
			}
			CO.waitforload();
			Result.takescreenshot("Resolved");
			CO.Webtable_Value("Contact Role", "");

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "TroubleTicket is done Successfully " + ",";
				Result.fUpdateLog("TroubleTicket is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "TroubleTicket Failed" + ",";
				Result.takescreenshot("TroubleTicket Failed");
				Result.fUpdateLog("TroubleTicket Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------TroubleTicket Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name             : Discounts
	 * Arguments               : None
	 * Use                     : Plan level Discounts
	 * Designed By             : Vinodhini Raviprasad
	 * Last Modified Date      : 12-Nov-2017
	 --------------------------------------------------------------------------------------------------------*/
	public String Discounts() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Discount, GetData, Order_no;
		int Inst_RowCount, Col_P, Col_SID;

		Result.fUpdateLog("------Discounts ------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("Discount").equals(""))) {
				Discount = getdata("Discount");
			} else {
				Discount = pulldata("Discount");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitmoreforload();
			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				do {
					Browser.WebButton.click("Assert_Modify");
					String x = Browser.WebEdit.gettext("Due_Date");
					if (!x.contains("/")) {
						Browser.WebButton.click("Date_Cancel");
						Browser.WebButton.click("Assert_Modify");
					}
					CO.waitforload();
				} while (!Browser.WebButton.waitTillEnabled("Date_Continue"));

			} else {
				CO.InstalledAssertChange("Modify");
			}

			CO.waitforload();
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			/*
			 * int Row_Count = Browser.WebTable.getRowCount("Line_Items"); Col =
			 * CO.Select_Cell("Line_Items", "Product"); Col_V = Col + 2;
			 * 
			 * for (int i = 2; i <= Row_Count; i++) { String LData =
			 * Browser.WebTable.getCellData("Line_Items", i, Col); if
			 * (GetData.equals(LData)) { Row_Val = i; break; } }
			 * Browser.WebTable.click("Line_Items", Row_Val, Col_V);
			 * Result.fUpdateLog("------Customising to Add Discount ------");
			 * CO.Text_Select("span", "Customize");
			 */
			CO.waitforload();
			if (TestCaseN.get().equalsIgnoreCase("PlanDiscount")) {
				Result.fUpdateLog("------Customising to Add Plan Discount ------");
				String PlanName;
				if (!(getdata("PlanBundle").equals(""))) {
					PlanName = getdata("PlanBundle");
				} else {
					PlanName = pulldata("PlanBundle");
				}
				CO.Radio_Select(PlanName);
				CO.waitforload();
				Result.takescreenshot("Customising to Select Discounts");
				WebElement Custom = cDriver.get().findElement(By.xpath("//i[@class='siebui-icon-settings']"));
				((RemoteWebDriver) cDriver.get()).executeScript("arguments[0].scrollIntoView(true)", Custom);
				cDriver.get().findElement(By.xpath("//i[@class='siebui-icon-settings']")).click();
				CO.waitforload();
				CO.Radio_Select(Discount);
				CO.waitforload();
				Result.fUpdateLog("------Discount Selected ------");
			} else {
				Result.fUpdateLog("------Customising to Add Addon Discount ------");
				Result.takescreenshot("Addon Level Discount");
				String Addon, AddonTab;
				if (!(getdata("Addon").equals(""))) {
					Addon = getdata("Addon");
				} else {
					Addon = pulldata("Addon");
				}
				if (!(getdata("AddonTab").equals(""))) {
					AddonTab = getdata("AddonTab");
				} else {
					AddonTab = "Paid Addons";
				}

				CO.Link_Select(AddonTab);
				CO.Discounts(Addon, Discount);
				CO.waitforload();
				Result.fUpdateLog("------Discount Selected ------");
			}

			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			Result.takescreenshot("Discounts Done");
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			Browser.WebButton.waittillvisible("Validate");
			Test_OutPut += OrderSubmission().split("@@")[1];

			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Discounts is done Successfully " + ",";
				Result.fUpdateLog("Discounts is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Discounts Failed" + ",";
				Result.takescreenshot("Discounts Failed");
				Result.fUpdateLog("Discounts Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Discounts Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name             : SIMSwap_Guided_Journey
	 * Arguments               : None
	 * Use                     : GJ Flow for SIM Swap
	 * Designed By             : Vinodhini Raviprasad
	 * Last Modified Date      : 05-Dec-2017
	 --------------------------------------------------------------------------------------------------------*/
	public String SIMSwap_Guided_Journey()

	{
		String MSISDN, New_SIM, Comments;
		int Row = 2, Col;
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------SIM swap Guided journey------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_SIM").equals(""))) {
				New_SIM = getdata("New_SIM");
			} else {
				New_SIM = pulldata("New_SIM");
			}

			if (!(getdata("Comments").equals(""))) {
				Comments = getdata("Comments");
			} else {
				Comments = pulldata("Comments");
			}
			Random R = new Random();
			CO.waitforload();
			CO.scroll("VQ_Assert", "WebLink");
			Browser.WebLink.click("VQ_Assert");
			CO.waitforload();

			CO.scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			CO.waitforload();

			Result.takescreenshot("Assert Search");
			Result.fUpdateLog("Assert Search Initiation");
			// Installed_Assert
			Col = CO.Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = CO.Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", "Active");
			Col = CO.Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			CO.waitforload();
			Col = CO.Select_Cell("Assert", "Account");
			int Assert_Row_Count = Browser.WebTable.getRowCount("Assert");
			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);
			Result.takescreenshot("SIM Swap Initiation in Account Summary View");
			Result.fUpdateLog("SIM Swap Initiation in Account Summary View");
			CO.waitforload();
			Browser.WebButton.waitTillEnabled("Asset_SIMSwap");
			Browser.WebButton.click("Asset_SIMSwap");

			CO.scroll("Cancel_gs", "WebButton");
			CO.waitforload();
			CO.waitforload();
			Browser.WebButton.waittillvisible("S_Continue");
			String IDNum, IDType, FirstName;
			CO.waitforload();

			Result.takescreenshot("Contact Verification for SIM Swap Guided Journey");
			Result.fUpdateLog("Contact Verification for SIM Swap Guided Journey");
			IDNum = Browser.WebEdit.gettext("Contact_IDNumber");
			IDType = Browser.WebEdit.gettext("Contact_IDType");
			FirstName = Browser.WebEdit.gettext("FirstName");
			Browser.WebButton.click("S_Continue");

			CO.waitmoreforload();
			Browser.WebEdit.Set("New_SIM_SNO", New_SIM);

			Result.takescreenshot("Providing New SIM");
			Result.fUpdateLog("Providing New SIM");
			CO.waitforload();

			Browser.WebButton.waittillvisible("Submit_SimSwap");
			Browser.WebButton.click("Submit_SimSwap");

			CO.waitforload();

			CO.scroll("Save_Finish", "WebLink");

			Browser.WebEdit.Set("Comments", Comments + R.nextInt(1000));

			Browser.WebLink.waittillvisible("Save_Finish");
			Result.fUpdateLog("Finishing SIM Swap Guided Procees");
			Result.takescreenshot("Finishing SIM Swap Guided Procees");
			Browser.WebLink.click("Save_Finish");

			CO.waitforload();
			Result.takescreenshot("Finish Process");
			Browser.WebLink.click("Finish_Process");

			CO.waitforload();
			if (Browser.WebButton.exist("VFQ_LeftScroll"))
				Browser.WebButton.click("VFQ_LeftScroll");
			Browser.WebLink.click("VQ_Assert");

			CO.waitforload();
			CO.scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			CO.waitforload();

			Result.fUpdateLog("Initiating Assert Search for Contact Verification");
			Result.takescreenshot("Initiating Assert Search for Contact Verification");
			// Installed_Assert
			Col = CO.Select_Cell("Assert", "Service ID");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Serial_Number", MSISDN);
			Col = CO.Select_Cell("Assert", "Status");
			Browser.WebTable.SetDataE("Assert", Row, Col, "Status", "Active");
			Col = CO.Select_Cell("Assert", "Product");
			Browser.WebButton.waitTillEnabled("Assert_Go");
			Browser.WebButton.click("Assert_Go");
			CO.waitforload();
			Col = CO.Select_Cell("Assert", "Account");
			Assert_Row_Count = Browser.WebTable.getRowCount("Assert");

			if (Assert_Row_Count > 1)
				Browser.WebTable.clickL("Assert", Row, Col);
			else
				Continue.set(false);

			CO.waitforload();
			Browser.WebButton.waitTillEnabled("Asset_SIMSwap");
			Browser.WebButton.click("Asset_SIMSwap");
			CO.waitforload();

			if (IDNum.equalsIgnoreCase(Browser.WebEdit.gettext("Contact_IDNumber"))
					& IDType.equalsIgnoreCase(Browser.WebEdit.gettext("Contact_IDType"))
					& FirstName.equalsIgnoreCase(Browser.WebEdit.gettext("FirstName"))) {
				Result.takescreenshot("Contact Verification is Successfull");
				Result.fUpdateLog("Contact Verification is Successfull");
			} else {
				Result.takescreenshot("Contact Verification Failure");
				Continue.set(false);
			}
			if (Continue.get()) {
				Test_OutPut += "";
				Result.takescreenshot("SIM Swap Guided_Journey is Successfull");
				Result.fUpdateLog("SIM Swap Guided_Journey is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "SIM Swap Guided_Journey Failed" + ",";
				Result.takescreenshot("SIM Swap Guided_Journey Failed");
				Result.fUpdateLog("SIM Swap Guided_Journey Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		Result.fUpdateLog("-----SIM swap Guided journey - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	* Method Name             : ConsumerPostpaid_GuidedJourney
	* Arguments               : None
	* Use                     : GJ Flow for Postpaid Consumer Provisioning
	* Designed By             : Vinodhini Raviprasad
	* Last Modified Date      : 05-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String ConsumerPostpaid_GuidedJourney() {
		String Test_OutPut = "", Status = "";
		String MSISDN, SIM, SP_Plan, ID_Type, ID_Expiry, Nationality, Gender, Language, First, Last, DOB, Job, Email,
				Mobile, File_Upload, IDNumber;
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			if (!(getdata("SIM").equals(""))) {
				SIM = getdata("SIM");
			} else {
				SIM = pulldata("SIM");
			}

			if (!(getdata("ID_Number").equals(""))) {
				IDNumber = getdata("ID_Number");
			} else {
				IDNumber = "8912" + R.nextInt(100000);
			}

			if (!(getdata("PlanName").equals(""))) {
				SP_Plan = getdata("PlanName");
			} else {
				SP_Plan = pulldata("PlanName");
			}
			if (!(getdata("ID_Type").equals(""))) {
				ID_Type = getdata("ID_Type");
			} else {
				ID_Type = pulldata("ID_Type");
			}

			if (!(getdata("ID_Expiry").equals(""))) {
				ID_Expiry = getdata("ID_Expiry");
			} else {
				ID_Expiry = pulldata("ID_Expiry");
			}
			if (!(getdata("Nationality").equals(""))) {
				Nationality = getdata("Nationality");
			} else {
				Nationality = pulldata("Nationality");
			}
			if (!(getdata("Language").equals(""))) {
				Language = getdata("Language");
			} else {
				Language = pulldata("Language");
			}
			if (!(getdata("Gender").equals(""))) {
				Gender = getdata("Gender");
			} else {
				Gender = pulldata("Gender");
			}

			if (!(getdata("FirstName").equals(""))) {
				First = getdata("FirstName");
			} else if (!(pulldata("FirstName").equals(""))) {
				First = pulldata("FirstName") + R.nextInt(100000);
			} else {
				First = Utlities.randname();
			}

			if (!(getdata("LastName").equals(""))) {
				Last = getdata("LastName");
			} else if (!(pulldata("LastName").equals(""))) {
				Last = pulldata("LastName") + R.nextInt(100000);
			} else {
				Last = Utlities.randname();
			}

			if (!(getdata("DOB").equals(""))) {
				DOB = getdata("DOB");
			} else {
				DOB = pulldata("DOB");
			}

			if (!(getdata("Job").equals(""))) {
				Job = getdata("Job");
			} else {
				Job = pulldata("Job");
			}

			if (!(getdata("Email").equals(""))) {
				Email = getdata("Email");
			} else {
				Email = pulldata("Email");
			}

			if (!(getdata("Mobile").equals(""))) {
				Mobile = getdata("Mobile");
			} else {
				Mobile = pulldata("Mobile");
			}

			if (!(getdata("File_Upload").equals(""))) {
				File_Upload = getdata("File_Upload");
			} else {
				File_Upload = pulldata("File_Upload");
			}

			CO.waitforobj("VQ_Contact", "WebLink");
			CO.waitforload();
			Browser.WebLink.click("VQ_Contact");
			CO.waitforload();
			CO.waitforobj("All_contacts", "WebLink");
			Browser.WebLink.click("All_contacts");
			CO.waitforload();
			CO.waitforobj("Acc_Add_Contact", "WebButton");
			Browser.WebButton.click("Acc_Add_Contact");
			CO.waitforobj("Consumer_Postpaid", "WebLink");
			Browser.WebLink.click("Consumer_Postpaid");
			CO.waitforload();
			Result.takescreenshot("Navigated to Consumer Postpaid");
			CO.waitforload();

			Browser.WebEdit.waittillvisible("C_ID_Number");
			Browser.WebEdit.waitTillEnabled("C_ID_Number");

			CO.scroll("C_ID_Number", "WebEdit");
			Browser.WebEdit.Set("C_ID_Number", IDNumber);

			CO.waitforload();
			CO.waitforobj("ID_Ctype", "ListBox");
			Browser.ListBox.select("ID_Ctype", ID_Type);
			CO.waitforobj("C_go", "WebButton");
			Browser.WebButton.click("C_go");
			CO.waitforload();

			Browser.WebEdit.waittillvisible("C_ID_Expiry");
			Browser.WebEdit.waitTillEnabled("C_ID_Expiry");

			Result.takescreenshot("");

			CO.waitforobj("C_ID_Expiry", "WebEdit");

			CO.scroll("C_ID_Expiry", "WebEdit");
			if (!(ID_Type.equalsIgnoreCase("Qatari ID"))) {
				Browser.WebEdit.Set("C_ID_Expiry", ID_Expiry);

				Browser.WebEdit.Set("C_Nationality", Nationality);

				Browser.WebEdit.Set("C_Gender", Gender);

				Browser.WebEdit.Set("C_First", First);

				Browser.WebEdit.Set("C_Last", Last);

				Browser.WebEdit.Set("C_DOB", DOB);

				Browser.WebEdit.Set("C_Job", Job);

			} else {
				if (Browser.WebEdit.CheckDisabled("C_First") == false & Browser.WebEdit.CheckDisabled("C_Last") == false
						& Browser.WebEdit.CheckDisabled("C_Nationality") == false)
					Continue.set(false);
			}

			Result.takescreenshot("");
			Browser.WebEdit.Set("C_Email", Email);
			Browser.WebEdit.Set("Contact_IDType", Language);
			Browser.WebEdit.Set("C_Mobile", Mobile);
			CO.Upload("File_Upload", File_Upload);
			CO.waitforload();
			Browser.WebButton.waittillvisible("c_Continue");
			Browser.WebButton.click("c_Continue");
			Result.fUpdateLog("Guided :  contact Completed");

			// Search for Specific Address

			String Address;
			if (!(getdata("Address").equals(""))) {
				Address = getdata("Address");
			} else if (!(getdata("Kahramaa_ID").equals(""))) {
				Address = "Kar#" + getdata("Kahramaa_ID");
			} else if (!(pulldata("Kahramaa_ID").equals(""))) {
				Address = "Kar#" + pulldata("Kahramaa_ID");
			} else {
				Address = pulldata("Address");
			}

			CO.waitforload();

			Browser.WebButton.waittillvisible("C_Add_Address");

			CO.waitforload();

			CO.waitforobj("C_Add_Address", "WebButton");

			Browser.WebButton.click("C_Add_Address");

			// Address

			CO.waitforobj("c_Popup_Go", "WebButton");
			CO.scroll("c_Popup_Go", "WebButton");

			if (Address.contains("Kar#")) {
				Browser.ListBox.select("PopupQuery_List", "Kahramaa ID");
				Browser.WebEdit.Set("PopupQuery_Search", Address.split("#")[1]);
			} else {
				Browser.ListBox.select("PopupQuery_List", "Address Line 1");
				Browser.WebEdit.Set("PopupQuery_Search", Address);
			}
			CO.waitforload();
			Browser.WebButton.click("c_Popup_Go");

			CO.waitforload();
			CO.scroll("Add_OK", "WebButton");
			Browser.WebButton.click("Add_OK");

			CO.waitforload();

			Result.takescreenshot("Address Selected : " + Address);
			Result.fUpdateLog("Address Selected : " + Address);

			CO.waitforload();

			CO.scroll("c_Continue", "WebButton");
			Browser.WebButton.waittillvisible("c_Continue");
			Browser.WebButton.click("c_Continue");
			Result.fUpdateLog("Guided :  Address Completed");

			CO.waitforload();

			// Account

			CO.scroll("C_segment", "ListBox");

			CO.waitforload();

			CO.scroll("c_Continue", "WebButton");
			Browser.WebButton.waittillvisible("c_Continue");
			Browser.WebButton.click("c_Continue");
			Result.fUpdateLog("Guided :  Account  Completed");

			// Billing

			CO.waitforload();

			CO.scroll("C_Media_type", "ListBox");
			CO.waitforobj("cb_Continue", "WebButton");
			Browser.WebButton.waittillvisible("cb_Continue");
			Browser.WebButton.click("cb_Continue");

			Result.fUpdateLog("Guided :  Billing profile Completed");
			// Reserved no

			CO.waitforobj("c_Reserve_Num", "WebButton");
			String Number = MSISDN, Temp;
			// String Number = Driver.msisdn_exe.get(), Temp;
			int Len = Number.length(), Col_Count = Browser.WebTable.getColCount("Unreserverd"), Col_Res = 0, Row = 2;
			String Reserve = Number.substring(3, Len);
			for (int i = 2; i < Col_Count; i++) {
				Temp = CO.Col_Data(i);
				Col_Res = i;
				if (Temp.toLowerCase().trim().equals("service type"))
					break;
			}

			/*
			 * if (Browser.WebButton.exist("Query_Unreserv_Num")) {
			 * CO.scroll("Query_Unreserv_Num", "WebButton");
			 * Browser.WebButton.click("Query_Unreserv_Num");}
			 */

			CO.scroll("Unreserverd", "WebTable");
			Browser.WebTable.SetDataE("Unreserverd", Row, Col_Res, "Service_Type", "Mobile");
			CO.scroll("Unreserverd", "WebTable");
			Browser.WebTable.SetData("Unreserverd", Row, Col_Res + 3, "Service_Id", Reserve);

			Browser.WebButton.waitTillEnabled("c_Reserve_Num");
			Browser.WebButton.waittillvisible("c_Reserve_Num");

			CO.waitforload();
			int Row_Count = Browser.WebTable.getRowCount("Unreserverd");
			Result.fUpdateLog(Row_Count + " Unreserved Row Count");
			// To check whether number available
			Result.takescreenshot("Number Reservation");
			if (Row_Count > 1) {
				CO.scroll("c_Reserve_Num", "WebButton");
				CO.Text_Select("span", "Reserve");
				CO.waitforload();
				CO.isAlertExist();
			} else {
				Driver.Continue.set(false);
				System.out.println("Guided : Check the MSISDN");
				System.exit(0);
			}
			// Reserved
			CO.scroll("Reserved", "WebTable");
			Browser.WebTable.SetDataE("Reserved", Row, Col_Res, "Service_Type", "Mobile");
			Browser.WebTable.SetData("Reserved", Row, Col_Res + 1, "Category", "");

			// To check whether the Number has been Reserved
			// CO.waitforload();
			CO.waitforobj("SP_Resrv_Continue", "WebButton");
			Row_Count = Browser.WebTable.getRowCount("Reserved");
			System.out.println("Guided : Unreserved" + Row_Count);
			if (Row_Count > 1)
				Result.takescreenshot("MSISDN Reserved " + Number);
			else {
				Driver.Continue.set(false);
				Result.fUpdateLog("Guided : Check the MSISDN");
				System.exit(0);
			}
			CO.scroll("SP_Resrv_Continue", "WebButton");
			CO.Text_Select("span", "Continue");
			Result.fUpdateLog("Guided :  Number Reserved");

			CO.waitforload();

			// To Provide SIM Number
			CO.waitforobj("SP_Continue", "WebButton");
			Browser.WebEdit.waittillvisible("SP_SIM");
			Browser.WebEdit.waitTillEnabled("SP_SIM");
			System.out.println("SIM");

			// CO.waitmoreforload();
			CO.waitforload();
			Driver.Continue.set(true);
			CO.scroll("SP_SIM", "WebEdit");
			Browser.WebEdit.click("SP_SIM");
			Browser.WebEdit.Set("SP_SIM", SIM);

			Result.takescreenshot("");
			Browser.WebButton.waittillvisible("SP_Continue");
			CO.scroll("SP_Continue", "WebButton");
			Browser.WebButton.click("SP_Continue");
			Result.fUpdateLog("Guided :  SIM Provided");

			// Plan

			CO.waitforload();
			if (TestCaseN.get().toLowerCase().contains("consumerpostpaid")) {
				CO.Category_Select("Postpaid Plans", "Postpaid Consumer Plans");
			}

			CO.waitforload();
			CO.Link_Select(SP_Plan);
			CO.Plan_Select("Plan_Search", SP_Plan);
			CO.waitmoreforload();
			CO.waitmoreforload();
			CO.waitforobj("SP_Plan_Customise", "WebButton");
			Browser.WebButton.waittillvisible("SP_Plan_Customise");
			Browser.WebButton.waitTillEnabled("SP_Plan_Customise");
			Browser.WebButton.waittillvisible("CAF_Payment");
			Browser.WebButton.waitTillEnabled("CAF_Payment");
			Result.takescreenshot("");
			CO.waitmoreforload();
			CO.scroll("CAF_Payment", "WebButton");
			Browser.WebButton.click("CAF_Payment");
			System.out.println("Guided : Plan Selected");
			Result.fUpdateLog("Guided : Plan Selected ");

			// Payment

			CO.waitforload();
			CO.waitmoreforload();
			CO.waitforload();
			CO.scroll("Generate_CAF", "WebButton");
			Browser.WebButton.click("Generate_CAF");

			CO.waitforload();

			// CAF
			CO.waitforload();
			CO.waitmoreforload();
			CO.waitforload();
			CO.scroll("New_File", "WebButton");

			Result.takescreenshot("");

			CO.Upload("New_File", File_Upload);
			CO.waitforload();
			Result.takescreenshot("");
			Result.takescreenshot("CAF Attachment done");
			Browser.WebButton.waittillvisible("SP_Attach_Continue");
			Browser.WebButton.waitTillEnabled("SP_Attach_Continue");
			CO.waitforobj("SP_Attach_Continue", "WebButton");
			CO.scroll("SP_Attach_Continue", "WebButton");
			Browser.WebButton.waittillvisible("SP_Attach_Continue");
			Result.takescreenshot("");
			Browser.WebButton.click("SP_Attach_Continue");
			CO.waitforload();
			Result.fUpdateLog("Guided : CAF Created");
			// Completion

			Result.takescreenshot(" Completion Process");
			String status = Browser.WebEdit.gettext("Resolution_Code");
			CO.scroll("Comments", "WebEdit");
			Browser.WebEdit.Set("Comments", status + "with Status");
			Result.takescreenshot("Order Submited and " + status);
			Result.takescreenshot("");
			CO.scroll("Save&Continue", "WebButton");
			Browser.WebButton.click("Save&Continue");
			CO.waitforload();
			Result.takescreenshot("");
			CO.Text_Select("span", "Finish Process");
			Result.fUpdateLog("Guided : Process Done");

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "";
				Result.takescreenshot("Postpaid_Guided_Journey is Successfull");
				Result.fUpdateLog("Postpaid_Guided_Journey is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "Postpaid_Guided_Journey Failed" + ",";
				Result.takescreenshot("Postpaid_Guided_Journey Failed");
				Result.fUpdateLog("Postpaid_Guided_Journey Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("Post-paid guided flow- Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	* Method Name             : SIMSwap_Global_Search
	* Arguments               : None
	* Use                     : GJ Flow for SIM Swap via Global Search
	* Designed By             : Vinodhini Raviprasad
	* Last Modified Date      : 05-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String SIMSwap_Global_Search()

	{
		String MSISDN, New_SIM, Comments, IDNum, IDType, FirstName;
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------SIM swap Global Search------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("New_SIM").equals(""))) {
				New_SIM = getdata("New_SIM");
			} else {
				New_SIM = pulldata("New_SIM");
			}

			if (!(getdata("Comments").equals(""))) {
				Comments = getdata("Comments");
			} else {
				Comments = pulldata("Comments");
			}

			CO.waitforload();
			Browser.WebLink.waittillvisible("Global_Search");
			Browser.WebLink.click("Global_Search");
			CO.waitforload();

			Browser.WebEdit.Set("GS_Phone", MSISDN);
			Result.fUpdateLog("Global Search Initiation");
			Result.takescreenshot("Global Search Initiation");
			Browser.WebLink.click("GS_G0");
			CO.waitforload();
			Thread.sleep(1000);

			Browser.WebButton.waittillvisible("GS_Simswap");
			Result.fUpdateLog("Global Search MSISDN Retrived");
			Result.takescreenshot("Global Search MSISDN Retrived");
			Browser.WebButton.click("GS_Simswap");
			CO.scroll("Cancel_gs", "WebButton");

			CO.waitforload();

			Browser.WebButton.waittillvisible("S_Continue");
			Result.fUpdateLog("Proceeding SIM Swap Procees");
			Result.takescreenshot("Proceeding SIM Swap Procees");

			Result.takescreenshot("Contact Verification for SIM Swap Guided Journey");
			Result.fUpdateLog("Contact Verification for SIM Swap Guided Journey");
			IDNum = Browser.WebEdit.gettext("Contact_IDNumber");
			IDType = Browser.WebEdit.gettext("Contact_IDType");
			FirstName = Browser.WebEdit.gettext("FirstName");
			Browser.WebButton.click("S_Continue");
			CO.waitforload();

			Browser.WebEdit.clear("New_SIM_SNO");
			Thread.sleep(2000);
			Browser.WebEdit.Set("New_SIM_SNO", New_SIM);

			CO.waitforload();

			Result.fUpdateLog("Providing New SIM");
			Result.takescreenshot("Providing New SIM");
			Browser.WebButton.waittillvisible("Submit_SimSwap");
			Browser.WebButton.click("Submit_SimSwap");

			CO.scroll("Save_Finish", "WebLink");

			CO.waitforload();

			Browser.WebEdit.Set("Comments", Comments + R.nextInt(100));
			Result.fUpdateLog("Providing Comments");
			Result.takescreenshot("Comments Provided");
			Browser.WebLink.click("Save_Finish");

			CO.waitforload();

			Result.fUpdateLog("Proceeding Finish Process");
			Result.takescreenshot("Proceeding Finish_Process");
			Browser.WebLink.click("Finish_Process");

			CO.waitforload();
			CO.waitforobj("GS_Simswap", "WebButton");
			Browser.WebButton.click("GS_Simswap");
			CO.waitforload();
			CO.scroll("Cancel_gs", "WebButton");
			CO.waitforload();

			if (IDNum.equalsIgnoreCase(Browser.WebEdit.gettext("Contact_IDNumber"))
					& IDType.equalsIgnoreCase(Browser.WebEdit.gettext("Contact_IDType"))
					& FirstName.equalsIgnoreCase(Browser.WebEdit.gettext("FirstName"))) {
				Result.takescreenshot("Contact Verification is Successfull");
				Result.fUpdateLog("Contact Verification is Successfull");
			} else {
				Result.takescreenshot("Contact Verification Failure");
				Continue.set(false);
			}

			if (Continue.get()) {
				Test_OutPut += "";
				Result.takescreenshot("SIM swap Global Search is Successfull");
				Result.fUpdateLog("SIM swap Global Search is Successfull");
				Status = "PASS";
			} else {
				Test_OutPut += "SIM swap Global Search Failed" + ",";
				Result.takescreenshot("SIM swap Global Search Failed");
				Result.fUpdateLog("SIM swap Global Search Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		Result.fUpdateLog("-----SIM swap Global Search - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TOS
	 * Arguments			: None
	 * Use 					: Transfer of active services
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 03-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String TransferOfService() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Account_No = "", GetData, Bil_Profile = "", Pymt_Type = "", New_billNo = "";
		int Inst_RowCount, Col_P, Col_SID, Row = 2, Col_Type = 0, Col_Nam = 0;
		Result.fUpdateLog("------Transfer of active services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);

			Col_Nam = CO.Select_Cell("Acc_Installed_Assert", "Billing Profile");
			Bil_Profile = Browser.WebTable.getCellData("Acc_Installed_Assert", Row, Col_Nam);

			CO.TabNavigator("Profiles");

			// CO.Text_Select("a", GetData);
			// CO.waitforload();

			Inst_RowCount = Browser.WebTable.getRowCount("Bill_Prof");
			Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
			Col_Type = CO.Select_Cell("Bill_Prof", "Payment Type");

			for (int i = 2; i <= Inst_RowCount; i++) {
				if (Browser.WebTable.getCellData("Bill_Prof", i, Col_Nam).equalsIgnoreCase(Bil_Profile)) {
					Pymt_Type = Browser.WebTable.getCellData("Bill_Prof", i, Col_Type);
					break;
				}
			}

			CO.TabNavigator("Account Summary");
			CO.waitforload();
			// Create new contact and account
			if ((getdata("TestCase").equals("NewAccount"))) {
				ContactCreation();
				AccountCreation();
				Account_No = Utlities.FetchStoredValue("TransferOfService", "NewAccount", "Account_No");

				TOS_BillingProfileCreation(Account_No, Pymt_Type);
				New_billNo = Utlities.FetchStoredValue("TransferOfService", "NewAccount", "Billing_NO");
			} else if ((getdata("TestCase").equals("ExistingAccount"))) {

				if (!(getdata("Ext_AccountNo").equals(""))) {
					Account_No = getdata("Ext_AccountNo");
				} else {
					Account_No = pulldata("Ext_AccountNo");
				}
				TOS_BillingProfileCreation(Account_No, Pymt_Type);
				New_billNo = Utlities.FetchStoredValue("TransferOfService", "ExistingAccount", "Billing_NO");
			} else {
				System.out.println();
			}

			// Click on Modify Assert
			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				Browser.WebButton.click("Assert_Modify");

			} else
				CO.InstalledAssertChange("Modify");
			CO.waitforload();

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();

			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			// Update Account Number and Billing Profile Number
			CO.waitforload();
			Browser.WebButton.click("Service_Account");

			CO.Popup_Selection("Acoount_PickTable", "Account Number", Account_No);
			CO.waitforload();

			Browser.WebButton.click("Billing_Profile");
			CO.Popup_Selection("Billing Profile", "Name", New_billNo);
			CO.waitforload();

			Browser.WebButton.waittillvisible("Validate");
			Test_OutPut += OrderSubmission().split("@@")[1];

			// Transfer of Service Validation

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();

			if (Browser.WebEdit.gettext("Account_No").equalsIgnoreCase(Account_No)) {

			} else
				Continue.set(false);

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Transfer of Service is done Successfully " + ",";
				Result.fUpdateLog("Transfer of Service is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Transfer of Service Failed" + ",";
				Result.takescreenshot("Transfer of Service Failed");
				Result.fUpdateLog("Transfer of Service Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Transfer of Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: TOS_BillingProfileCreation
	 * Arguments			: None
	 * Use 					: Creates a Billing Profile in the existing Account for Vanilla Journey
	 * Designed By			: Lavannya M
	 * Last Modified Date 	: 05-Dec-2017
	--------------------------------------------------------------------------------------------------------*/
	public String TOS_BillingProfileCreation(String Account_No, String Payment_Type) {
		String Test_OutPut = "", Status = "";
		String Bill_No = null;
		int Col_Nam, Row_va = 0;
		Result.fUpdateLog("------Billing Profile Creation Event Details------");
		try {
			String Exi = Account_No;
			if (!Exi.equals("")) {
				CO.Account_Search(Exi);
				Utlities.StoreValue("Account_No", Exi);
				Test_OutPut += "Account_No : " + Exi + ",";
				CO.waitforload();
			}
			if (Continue.get()) {
				CO.scroll("Profile_Tab", "WebButton");
				do {
					Browser.WebButton.click("Profile_Tab");
					CO.waitforload();
					/*
					 * if (Browser.WebEdit.waitTillEnabled("BP_Valid_Name")) { j = 0; break; }
					 */

				} while (!Browser.WebEdit.waitTillEnabled("BP_Valid_Name"));
				Browser.WebEdit.waittillvisible("BP_Valid_Name");

				CO.waitforload();
				int Row = 2, Col_Val = 0, Row_Count;

				String Bill_NewProfile = "No";
				if (!(getdata("Bill_NewProfile").equals(""))) {
					Bill_NewProfile = getdata("Bill_NewProfile");
				} else if (!(pulldata("Bill_NewProfile").equals(""))) {
					Bill_NewProfile = pulldata("Bill_NewProfile");
				}

				CO.waitforobj("Bill_Add", "WebButton");
				Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
				if (Row_Count >= Row) {
					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");
					Col_Nam = CO.Select_Cell("Bill_Prof", "Name");
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Val);
						if (Payment_Type.equalsIgnoreCase(LData)) {
							Bill_No = Browser.WebTable.getCellData_title("Bill_Prof", i, Col_Nam);
							break;
						}
						Row_va = i;
					}
				}

				if ((Row_Count < Row) || Bill_NewProfile.equals("Yes") || Row_Count == Row_va) {
					Browser.WebButton.waittillvisible("Bill_Add");
					CO.scroll("Bill_Add", "WebButton");
					int Row_Ct = Browser.WebTable.getRowCount("Bill_Prof");
					Browser.WebButton.click("Bill_Add");
					do {
						int Row_C = Browser.WebTable.getRowCount("Bill_Prof");
						if (Row_C > Row_Ct) {
							break;
						}
					} while (true);

					CO.waitforload();

					Browser.WebTable.waittillvisible("Bill_Prof");
					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");
					if (!(getdata("Bill_PayType").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Type", getdata("Bill_PayType"));
					} else if (!(pulldata("Bill_PayType").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Type", pulldata("Bill_PayType"));
					}

					Col_Val = CO.Select_Cell("Bill_Prof", "Payment Method");
					if (!(getdata("Bill_PayMethod").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method",
								getdata("Bill_PayMethod"));
					} else if (!(pulldata("Bill_PayMethod").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method",
								pulldata("Bill_PayMethod"));
					}
					CO.isAlertExist();

					if (Payment_Type.equalsIgnoreCase("Postpaid")) {
						Col_Val = CO.Select_Cell("Bill_Prof", "Bill Media");
						if (!(getdata("Bill_Media").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", getdata("Bill_Media"));
						} else if (!(pulldata("Bill_Media").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", pulldata("Bill_Media"));
						}

						Col_Val = CO.Select_Cell("Bill_Prof", "Bill Type");
						if (!(getdata("Bill_Type").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", getdata("Bill_Type"));
						} else if (!(pulldata("Bill_Type").equals(""))) {
							Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", pulldata("Bill_Type"));
						}

					}

					Col_Val = CO.Select_Cell("Bill_Prof", "Language");
					if (!(getdata("Bill_Lang").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code", getdata("Bill_Lang"));
					} else if (!(pulldata("Bill_Lang").equals(""))) {
						Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code",
								pulldata("Bill_Lang"));
					}

					int Col_v;
					Col_v = CO.Actual_Cell("Bill_Prof", "Name");
					Bill_No = Browser.WebTable.getCellData("Bill_Prof", Row, Col_v);
				}

				Billprofile_No = Bill_No;
				Utlities.StoreValue("Billing_NO", Bill_No);
				Test_OutPut += "Billing_NO : " + Bill_No + ",";

				Browser.WebButton.waittillvisible("Orders_Tab");
			}
			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("Billing Profile Created Billing_NO : " + Bill_No);
			} else {
				Result.takescreenshot("Orders Tab not exist");
				Test_OutPut += "Orders Tab not exist" + ",";
				Result.fUpdateLog("Orders Tab not exist");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		Result.fUpdateLog("------Billing Profile Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Suspend
	 * Arguments			: None
	 * Use 					: Suspend a Active Plan
	 * Designed By			: Vinodhini Raviprasad
	 * Latest Modified By	: Sravani
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Suspension() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Resume_Date, Order_no, GetData;
		int Col_Resume, Row = 2;
		Result.fUpdateLog("------Suspend Event Details------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();

			if (Browser.WebButton.exist("Suspend")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");

				Result.fUpdateLog(Col_P + "," + Col_SID);
				Result.fUpdateLog(Browser.WebTable.getCellData("Acc_Installed_Assert", 3, Col_P));
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P)
							.equalsIgnoreCase("Mobile Service Bundle")) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}

				// CO.InstalledAssertChange("Suspend");
				CO.scroll("Suspend", "WebButton");
				Browser.WebButton.click("Suspend");
			} else {
				CO.InstalledAssertChange("InstalledAssertChange");
			}
			CO.waitmoreforload();

			CO.scroll("Due_Date_chicklet", "WebButton");
			Browser.WebButton.click("Due_Date_chicklet");
			CO.waitforload();
			if (Browser.WebEdit.gettext("Due_Date").equals(""))
				Continue.set(false);
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.scroll("Resume_Date", "WebButton");
			Col_Resume = CO.Select_Cell("Line_Items", "Resume Date");
			Browser.WebTable.click("Line_Items", Row, Col_Resume);

			DateFormat ResumeDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
			Calendar cals = Calendar.getInstance();
			cals.add(Calendar.MONTH, 1);
			Resume_Date = ResumeDate.format(cals.getTime()).toString();
			Browser.WebTable.SetDataE("Line_Items", Row, Col_Resume, "Scheduled_Ship_Date", Resume_Date);
			Result.fUpdateLog(Resume_Date);

			// Result.fUpdateLog(CO.Col_Data(Col_Resume).trim());
			Result.takescreenshot("");

			CO.scroll("Service", "WebButton");
			CO.scroll("Line_Items", "WebLink");

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Action_Update("Suspend", MSISDN);

			Test_OutPut += OrderSubmission().split("@@")[1];
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			Result.takescreenshot("");

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Suspend the Plan is done Successfully " + ",";
				Result.fUpdateLog("Suspend the Plan is done Successfully ");
				Status = "PASS";
			} else {
				Result.fUpdateLog("Suspenstion Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("Suspend Login Event Details - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Resume
	 * Arguments			: None
	 * Use 					: Resume the Suspended Plan
	 * Designed By			: Vinodhini Raviprasad
	 * Latest Modified By	: Sravani
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Resume() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Resume_Date, Order_no, GetData;
		int Col_Resume, Row = 2;
		Result.fUpdateLog("------Resume Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Suspended");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			if (Browser.WebButton.exist("Resume")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				for (int i = 2; i <= Inst_RowCount; i++) {
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P)
							.equalsIgnoreCase("Mobile Service Bundle")) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				}
				CO.scroll("Resume", "WebButton");
				Browser.WebButton.click("Resume");
			} else {
				CO.InstalledAssertChange("Resume");
			}
			CO.waitforload();
			CO.scroll("Due_Date_chicklet", "WebButton");
			Browser.WebButton.click("Due_Date_chicklet");
			if (Browser.WebEdit.gettext("Due_Date").equals(""))
				Continue.set(false);
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.scroll("Resume_Date", "WebButton");
			Col_Resume = CO.Select_Cell("Line_Items", "Resume Date");

			Browser.WebTable.click("Line_Items", Row, Col_Resume);
			DateFormat ResumeDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
			Calendar cals = Calendar.getInstance();
			cals.add(Calendar.DATE, 1);
			Resume_Date = ResumeDate.format(cals.getTime()).toString();
			Result.fUpdateLog(Resume_Date);

			Browser.WebTable.SetDataE("Line_Items", Row, Col_Resume, "Scheduled_Ship_Date", Resume_Date);
			Result.fUpdateLog(CO.Col_Data(Col_Resume).trim());

			Result.takescreenshot("");
			CO.scroll("Ful_Status", "WebButton");
			CO.scroll("Service", "WebButton");
			CO.scroll("Line_Items", "WebLink");

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Action_Update("Resume", MSISDN);
			Test_OutPut += OrderSubmission().split("@@")[1];

			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			Result.takescreenshot("");

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Test_OutPut += "Resume Plan is done Successfully " + ",";
				Result.fUpdateLog("Resume Plan is done Successfully ");
				Status = "PASS";
			} else {
				Result.fUpdateLog("Resume Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("Resume Event Details - Completed");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Change_PrimaryNumber
	 * Arguments			: None
	 * Use 					: Change of Primary number
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Change_PrimaryNumber() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData = null, Order_no;
		Result.fUpdateLog("------Change Primary Number Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();
			CO.Tag_Select("span", "Primary MSISDN");
			if (Browser.WebButton.exist("Assert_Modify")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");

				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_P + 1);
						break;
					}
				Browser.WebButton.click("Assert_Modify");

			} else
				CO.InstalledAssertChange("Modify");
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();
			CO.Link_Select("Others");
			CO.Radio_Select("Make Primary MSISDN");
			Result.takescreenshot("");
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			if (CO.isAlertExist()) {
				Continue.set(false);
				System.out.println("Error On Clicking Done Button");
				System.exit(0);
			}
			Result.takescreenshot("");
			CO.waitforload();
			Test_OutPut += OrderSubmission().split("@@")[1];
			CO.waitforload();

			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			CO.RTBScreen(MSISDN, "Active");
			CO.waitforload();

			CO.ToWait();
			CO.GetSiebelDate();
			if (Continue.get()) {
				Status = "PASS";
				Utlities.StoreValue("Sales_OrderNO", Order_no);
				Test_OutPut += "Order_No : " + Order_no + ",";
			} else {
				Status = "FAIL";
			}

		} catch (Exception e) {
			Status = "FAIL";
			Result.takescreenshot("Exception occurred");
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();

		}
		Result.fUpdateLog("-----Change Primary Number Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: Barring
	 * Arguments			: None
	 * Use 					: Barring of active services
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 03-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Barring() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData, BarringOption;
		int Inst_RowCount, Col_P, Col_SID;
		Result.fUpdateLog("------Barring of active services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}

			if (!(getdata("Barring Options").equals(""))) {
				BarringOption = getdata("Barring Options");
			} else {
				BarringOption = pulldata("Barring Options");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				Browser.WebButton.click("Assert_Modify");

			} else
				CO.InstalledAssertChange("Modify");
			CO.waitforload();

			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();

			CO.Link_Select("Barring Options");

			CO.Radio_Select(BarringOption);

			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			Browser.WebButton.waittillvisible("Validate");
			Test_OutPut += OrderSubmission().split("@@")[1];
			;

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "Barring Service is done Successfully " + ",";
				Result.fUpdateLog("Barring Service is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "Barring Failed" + ",";
				Result.takescreenshot("Barring Failed");
				Result.fUpdateLog("Barring Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------Barring Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: UnBarring
	 * Arguments			: None
	 * Use 					: UnBarring of services
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 03-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String UnBarring() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData, BarringOption;
		int Inst_RowCount, Col_P, Col_SID;

		Result.fUpdateLog("------UnBarring of services------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			if (!(getdata("Barring Options").equals(""))) {
				BarringOption = getdata("Barring Options");
			} else {
				BarringOption = pulldata("Barring Options");
			}

			CO.Assert_Search(MSISDN, "Active");
			CO.waitforload();
			CO.Text_Select("a", GetData);
			CO.waitforload();

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				int Col_SR = CO.Actual_Cell("Acc_Installed_Assert", "Status");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_SR);
						break;
					}
				Browser.WebButton.click("Assert_Modify");

			} else
				CO.InstalledAssertChange("Modify");
			CO.waitforload();
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.Link_Select("Barring Options");

			CO.Radio_Select(BarringOption);

			CO.waitforload();
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Continue.set(false);

			Browser.WebButton.waittillvisible("Validate");
			Test_OutPut += OrderSubmission().split("@@")[1];
			;

			// CO.Assert_Search(MSISDN, "Active");

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "UnBarring Service is done Successfully " + ",";
				Result.fUpdateLog("UnBarring Service is done Successfully ");
				Status = "PASS";
			} else {
				Test_OutPut += "UnBarring Failed" + ",";
				Result.takescreenshot("UnBarring Failed");
				Result.fUpdateLog("UnBarring Failed");
				Status = "FAIL";
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------UnBarring Services Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
}
