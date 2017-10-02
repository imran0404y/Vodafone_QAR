package Libraries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;

public class Keyword_CRM extends Driver {
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

			Browser.OpenBrowser(browser.get(), getdata("URL"));

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

			if (Continue.get() & Browser.WebButton.exist("VF_Search_Identify")) {
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
			Result.takescreenshot("Exception occurred");
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

			if (Driver.Continue.get()) {
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
				CO.waitforobj("Add_Go", "WebButton");
				CO.scroll("Add_Go", "WebButton");

				if (Address.contains("Kar#")) {
					Browser.ListBox.select("Add_List", "Kahramaa ID");
					Browser.WebEdit.Set("Add_Search", Address.split("#")[1]);
				} else {
					Browser.ListBox.select("Add_List", "Address Line 1");
					Browser.WebEdit.Set("Add_Search", Address);
				}

				Browser.WebButton.click("Add_Go");

				CO.scroll("Add_OK", "WebButton");
				Browser.WebButton.click("Add_OK");

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
			if (Continue.get() & Browser.WebButton.exist("Create_A/c")) {
				Utlities.StoreValue("LastName", Last_Name);
				Utlities.StoreValue("Address", Address);
				Status = "PASS";
			} else {
				Result.takescreenshot("Create_A/c button not exist");
				Test_OutPut += "Create_A/c button not exist" + ",";
				Result.fUpdateLog("Create_A/c button not exist");
				Status = "FAIL";
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
				Driver.Continue.set(false);
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

			// Browser.WebLink.waittillvisible("Acc_address");
			CO.waitforload();
			if (Browser.WebLink.exist("Acc_address"))
				Result.fUpdateLog("Proceeding Consumer Address Creation");
			else if (Browser.WebButton.exist("Address_Tab")) {
				Result.fUpdateLog("Proceeding Enterprise Address Creation");
			}

			Browser.WebButton.click("Add_Address");

			int Row = 2, Col;
			CO.scroll("Acc_Add_New", "WebButton");
			Browser.WebButton.click("Acc_Add_New");

			String Add1, Add2;
			Col = CO.Select_Cell("Address", "Address Line 1");
			if (!(getdata("Add_AddressLine1").equals(""))) {
				Add1 = getdata("Add_AddressLine1");
			} else if (!(pulldata("Add_AddressLine1").equals(""))) {
				Add1 = pulldata("Add_AddressLine1");
			} else {
				Add1 = Utlities.randname();
			}
			Browser.WebTable.SetDataE("Address", Row, Col, "Street_Address", Add1);
			Utlities.StoreValue("Address line1", Add1);

			Col = CO.Select_Cell("Address", "Address Line 2");
			if (!(getdata("Add_AddressLine2").equals(""))) {
				Add2 = getdata("Add_AddressLine2");
			} else if (!(pulldata("Add_AddressLine2").equals(""))) {
				Add2 = pulldata("Add_AddressLine2");
			} else {
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
			} else if (pulldata("Add_Kahramaa_ID").equals("Automatic")) {
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
		Result.fUpdateLog("------Billing Profile Creation Event Details------");
		try {
			Browser.WebButton.exist("Profile_Tab");
			System.out.println("Proceeds with BillingProfileCreation");
			CO.scroll("Profile_Tab", "WebButton");
			Browser.WebButton.click("Profile_Tab");
			CO.waitforload();
			int Row = 2, Col_Val = 0, Row_Count;

			String Payment_Type;

			CO.waitforobj("Bill_Add", "WebButton");
			Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			if (Row_Count < Row) {
				CO.scroll("Bill_Add", "WebButton");
				Browser.WebButton.click("Bill_Add");
			}

			Col_Val = CO.Select_Cell("Bill_Prof", "Payment Type");

			CO.scroll("Bill_Prof", "WebTable");
			if (!(getdata("Bill_PayType").equals(""))) {
				Payment_Type = getdata("Bill_PayType");
			} else {
				Payment_Type = pulldata("Bill_PayType");
			}
			Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Type", Payment_Type);

			CO.waitforload();

			Col_Val = CO.Select_Cell("Bill_Prof", "Payment Method");
			if (!(getdata("Bill_PayMethod").equals(""))) {
				Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method", getdata("Bill_PayMethod"));
			} else {
				Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Payment_Method", pulldata("Bill_PayMethod"));
			}

			if (Payment_Type.equals("Postpaid")) {
				Col_Val = CO.Select_Cell("Bill_Prof", "Bill Media");
				if (!(getdata("Bill_Media").equals(""))) {
					Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", getdata("Bill_Media"));
				} else {
					Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Media_Type", pulldata("Bill_Media"));
				}

				Col_Val = CO.Select_Cell("Bill_Prof", "Bill Type");
				if (!(getdata("Bill_Type").equals(""))) {
					Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", getdata("Bill_Type"));
				} else {
					Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bill_Type", pulldata("Bill_Type"));
				}

			}

			Col_Val = CO.Select_Cell("Bill_Prof", "Language");
			if (!(getdata("Bill_Lang").equals(""))) {
				Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code", getdata("Bill_Lang"));
			} else {
				Browser.WebTable.SetData("Bill_Prof", Row, Col_Val, "Bank_Language_Code", pulldata("Bill_Lang"));
			}

			Col_Val = CO.Select_Cell("Bill_Prof", "Name");
			Bill_No = Browser.WebTable.getCellData("Bill_Prof", Row, Col_Val);
			Utlities.StoreValue("Billing_NO", Bill_No);
			Test_OutPut += "Billing_NO : " + Bill_No + ",";

			Browser.WebButton.waittillvisible("Orders_Tab");

			CO.ToWait();
			if (Driver.Continue.get() & Browser.WebButton.exist("Orders_Tab")) {
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

			if (Browser.WebButton.exist("Orders_Tab"))
				System.out.println("Proceeding with Sales Order");
			else if (!Utlities.FetchStoredValue("BillingProfileCreation").isEmpty()) {
				Driver.Continue.set(true);
			}
			Browser.WebButton.click("Orders_Tab");
			if (CO.isAlertExist())
				Browser.WebButton.click("Orders_Tab");

			int Row = 2, Col = 6;
			Browser.WebButton.waitTillEnabled("Order_New");
			CO.scroll("Order_New", "WebButton");
			Browser.WebButton.click("Order_New");

			Col = CO.Get_Col("Order_Table", Row, "Sales Order");
			Browser.WebTable.click("Order_Table", Row, Col);
			Order_No = Browser.WebTable.getCellData("Order_Table", 2, (Col - 1));
			Browser.WebTable.click("Order_Table", Row, (Col - 1));

			Browser.WebButton.waittillvisible("LI_New");

			CO.ToWait();
			if (Driver.Continue.get() & Browser.WebButton.exist("LI_New")) {
				Status = "PASS";
				Utlities.StoreValue("Sales_OrderNO", Order_No);
				Test_OutPut += "Order_No : " + Order_No + ",";
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

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S;
			String Reserve, Category, GetData = "Mobile Service Bundle", Add_Addon, Remove_Addon, ReservationToken, SIM,
					Field, MSISDN = null, SData = "SIM Card";

			if (Browser.WebButton.exist("LI_New"))
				System.out.println("Proceeding Plan Selection");
			else {
				Driver.Continue.set(true);
				// CO.OrderSearch(Utlities.FetchStoredValue("SalesOrder"));
			}
			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
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
			Field = CO.Col_Data(Col_S);
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

			if (!(getdata("SIM").equals(""))) {
				SIM = getdata("SIM");
			} else {
				SIM = pulldata("SIM");
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
				CO.AddOnSelection(getdata("Addons_Add"), "Add");
				// Result.takescreenshot("");
				CO.AddOnSelection(getdata("Addons_Delete"), "Delete");
				// Result.takescreenshot("");
				CO.waitforload();
				CO.Text_Select("button", "Verify");
				CO.isAlertExist();
				CO.waitforload();
				CO.Text_Select("button", "Done");
				CO.waitforload();
				if (CO.isAlertExist())
					Driver.Continue.set(false);
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

				Category = Browser.WebTable.getCellData("Numbers", Row, COl_STyp + 1);
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
						if (GetData.equals(LData)) {
							Row_Val = i;
							break;
						}
					}
					Browser.WebTable.click("Line_Items", Row_Val, Col_V);
					Browser.WebButton.click("Customize");
					CO.AddOnSelection(MSISDN, "STAR");
					CO.waitforload();
					CO.Text_Select("button", "Verify");
					CO.isAlertExist();
					CO.waitforload();
					CO.Text_Select("button", "Done");
					if (CO.isAlertExist()) {
						Driver.Continue.set(false);
						System.exit(0);
					}

				}
				CO.waitforload();
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}

				if (Field.equalsIgnoreCase("previous service id"))
					Col_S = CO.Actual_Cell("Line_Items", "Service Id");

				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equals(LData)) {
						Row_Val = i;
					}
				}
				CO.waitforload();
				CO.waitforload();
				Browser.WebTable.Popup("Line_Items", Row_Val, Col_S);
				CO.waitforload();
				Browser.WebButton.waittillvisible("Reserved_Ok");
				Browser.WebButton.waitTillEnabled("Reserved_Ok");
				Row_Count = Browser.WebTable.getRowCount("Number_Selection");
				if (Row_Count > 1)
					Browser.WebButton.click("Reserved_Ok");
				else
					Driver.Continue.set(false);
			} else if (!ReservationToken.equals("")) {
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}

				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equals(LData))
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
				if (SData.equals(LData))
					Row_Val = i;
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", SIM);

			if (Driver.Continue.get() & (Row_Count >= 3)) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Test_OutPut += "PlanName : " + PlanName + ",";
				Utlities.StoreValue("MSISDN", MSISDN);
				Test_OutPut += "MSISDN : " + MSISDN + ",";
				Utlities.StoreValue("SIM_NO", SIM);
				Test_OutPut += "SIM_NO : " + SIM + ",";
				Result.takescreenshot("Plan Selection is Successful : " + PlanName);
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
	public String OrderSubmission() {
		String Test_OutPut = "", Status = "";
		int COL_FUL_STATUS = 0;
		String OS_Status;
		Result.fUpdateLog("------Order Submission Event Details------");
		try {
			int Complete_Status = 0, R_S = 0, Wait = 0, Row = 2, Col;
			String EStatus = "Complete", FStatus = "Failed";

			if (Browser.WebTable.exist("Line_Items"))
				Result.fUpdateLog("Proceeding Order Submission");
			String Product_Type;
			if (!(getdata("Product_Type").equals(""))) {
				Product_Type = getdata("Product_Type");
			} else {
				Product_Type = pulldata("Product_Type");
			}

			if (Product_Type.equals("Enterprise") || Product_Type.equals("VIP"))
				Browser.WebEdit.Set("Ent_CreditLimit", "100");
			else
				Browser.WebEdit.click("Credit_Limit");

			// To get fulfillment status coloumn
			CO.scroll("Ful_Status", "WebButton");
			Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
			if (CO.Col_Data(Col).equalsIgnoreCase("fulfillment status"))
				COL_FUL_STATUS = Col;
			CO.scroll("Service", "WebButton");

			Browser.WebButton.waittillvisible("Validate");
			Browser.WebButton.click("Validate");
			CO.waitmoreforload();
			CO.isAlertExist();

			Browser.WebButton.waittillvisible("Submit");
			CO.scroll("Submit", "WebButton");
			Browser.WebButton.click("Submit");
			CO.waitmoreforload();
			CO.isAlertExist();
			Result.takescreenshot("Order Submission is Successful");

			Col = COL_FUL_STATUS;
			int Row_Count = Browser.WebTable.getRowCount("Line_Items");
			Status = Browser.WebTable.getCellData("Line_Items", Row, Col);
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}

			Browser.WebButton.waittillvisible("Submit");
			do {
				Complete_Status = 0;
				R_S = 0;
				CO.scroll("Submit", "WebButton");
				CO.scroll("Ful_Status", "WebButton");

				for (int i = 2; i <= 3; i++) {

					OS_Status = Browser.WebTable.getCellData("Line_Items", i, Col);
					System.out.println("Round" + (i - 1) + " " + OS_Status);
					if (EStatus.equals(OS_Status)) {// To Find the Complete Status
						Complete_Status = Complete_Status + 1;
						R_S++;
						Wait = Wait + 80;
						Driver.Continue.set(true);
					} else {
						if (FStatus.equals(OS_Status)) {
							Driver.Continue.set(false);
							R_S = 2;
							Wait = 101;
						}
						// To refresh Page
						cDriver.get().navigate().refresh();
						Wait = Wait + 10;
						Browser.WebButton.waittillvisible("Submit");
						CO.waitforload();
					}

				}
			} while ((R_S < 2) || Wait < 100);
			Browser.WebButton.waittillvisible("Submit");

			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			CO.scroll("Submit", "WebButton");
			OS_Status = Browser.WebTable.getCellData("Line_Items", Row, Col);
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}

			if (OS_Status.equals(EStatus) || Complete_Status == 2) {
				Complete_Status = 2;
				Driver.Continue.set(true);
			}

			CO.ToWait();
			if (Continue.get() & (Complete_Status == 2)) {
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
				Acc = pulldata("Account_Name");
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
			CO.waitforload();
			Browser.WebLink.click("Acc_Portal");

			Browser.WebLink.waittillvisible("Acc_Contacts");

			CO.ToWait();
			if (Driver.Continue.get() & Browser.WebLink.exist("Acc_Contacts")) {
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

			Browser.WebButton.click("Address_Tab");
			CO.waitforload();
			if (!(Address.equals(""))) {

				CO.waitforobj("Add_Address", "WebButton");
				// Browser.WebButton.waittillvisible("Add_Address");
				Browser.WebButton.click("Add_Address");

				// Search for Specific Address
				CO.waitforobj("Add_Go", "WebButton");
				CO.scroll("Add_Go", "WebButton");

				if (Address.contains("Kar#")) {
					Browser.ListBox.select("Add_List", "Kahramaa ID");
					Browser.WebEdit.Set("Add_Search", Address.split("#")[1]);
				} else {
					Browser.ListBox.select("Add_List", "Address Line 1");
					Browser.WebEdit.Set("Add_Search", Address);
				}

				Browser.WebButton.click("Add_Go");

				CO.scroll("Add_OK", "WebButton");
				Browser.WebButton.click("Add_OK");

				CO.waitmoreforload();
				// Browser.WebButton.waittillvisible("Create_A/c");
				Result.takescreenshot("Address Selected : " + Address);
				Result.fUpdateLog("Address Selected : " + Address);
			} else {
				String[] stat_add = AddressCreation().split("@@");
				Status = stat_add[0];
				Address = stat_add[1].split(",")[0];
				Result.takescreenshot("Address Created : " + Address);
				Result.fUpdateLog("New Address Created : " + Address);
			}

			CO.waitforload();
			int x = 0;
			Browser.WebLink.click("Acc_Contacts");
			CO.waitforload();
			x = Browser.WebTable.getRowCount("Acc_Contact");
			if (x == 1) {
				Browser.WebButton.click("Acc_Add_Contact");
			}
			int Row = 2, Col;
			Col = CO.Select_Cell("Acc_Contact", "First Name");
			if (!(getdata("Cont_FirstName").equals(""))) {
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
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date", getdata("IDExpiryDate"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date", pulldata("IDExpiryDate"));
			}

			Col = CO.Select_Cell("Acc_Contact", "ID Number");
			if (!(getdata("IDNumber").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number", getdata("IDNumber"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number",
						pulldata("ID_Number") + R.nextInt(100000));

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
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Modify() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Add_Addon, Remove_Addon, Order_no;
		int Inst_RowCount, Col_P, Col_SID, Row_Count, Col, Length;

		Result.fUpdateLog("------Modify Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			CO.Assert_Search(MSISDN, "Active", "");

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equals("Mobile Service Bundle")
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID).equals(MSISDN)) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_P + 1);
						break;
					}
				Browser.WebButton.click("Assert_Modify");

			} else
				CO.InstalledAssertChange("Modify");
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();
			// wait

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

			CO.AddOnSelection(Add_Addon, "Add");
			Result.takescreenshot("");
			CO.waitmoreforload();
			CO.AddOnSelection(Remove_Addon, "Delete");
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			if (CO.isAlertExist()) {
				Driver.Continue.set(false);
				System.out.println("Error On Clicking Done Button");
				System.exit(0);
			}

			Result.takescreenshot("");
			/*
			 * String Product_Type; if (!(getdata("Product_Type").equals(""))) {
			 * Product_Type = getdata("Product_Type"); } else { Product_Type =
			 * pulldata("Product_Type"); } if (Product_Type.equals("Enterprise") ||
			 * Product_Type.equals("VIP")) Browser.WebEdit.Set("Ent_CreditLimit", "100");//
			 * click("Ent_CreditLimit"); else Browser.WebEdit.click("Credit_Limit");
			 * 
			 * //Browser.WebButton.waittillvisible("Validate"); //CO.waitforload();// Use
			 * for "Ent_CreditLimit"
			 */
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}

			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count = Browser.WebTable.getRowCount("Line_Items");

			String Product_Tabs[] = Add_Addon.split("<>");
			for (int j = 0; j < Product_Tabs.length; j++) {
				String Prod_array[] = Product_Tabs[j].split(",");
				Length = Prod_array.length;
				System.out.println(Length);
				String Sdata = Prod_array[1];
				if (Length > 1) {
					for (int i = 2; i <= Row_Count; i++) {
						String LData = Browser.WebTable.getCellData("Line_Items", i, Col);

						if (Sdata.equals(LData)) {
							String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);
							if (Action.equals("Add") || Action.equals("Delete")) {
								Result.fUpdateLog("Status  updated sucuessfully");
								Result.fUpdateLog("Add/Delition Event is Successfull");
							} else {
								Driver.Continue.set(false);
								Result.fUpdateLog("Event is UnSuccessfull");

							}

						}
					}
				}
			}

			OrderSubmission();
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			Result.takescreenshot("");

			CO.ToWait();
			if (Continue.get()) {
				Status = "PASS";
				Result.takescreenshot("Modification is Successful");
			} else
				Status = "FAIL";

		}

		catch (Exception e) {
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
	 * Method Name			: Suspend
	 * Arguments			: None
	 * Use 					: Suspend a Active Plan
	 * Designed By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Suspension() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Resume_Date, Order_no;
		int Col_Resume, Row = 2;
		Result.fUpdateLog("------Suspend Event Details------");
		try {

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			CO.Assert_Search(MSISDN, "Active", "Suspend");

			if (Browser.WebButton.exist("Suspend")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");

				Result.fUpdateLog(Col_P + "," + Col_SID);
				Result.fUpdateLog(Browser.WebTable.getCellData("Acc_Installed_Assert", 3, Col_P));
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P)
							.equals("Mobile Service Bundle")) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_P + 1);
						break;
					}

				// CO.InstalledAssertChange("Suspend");
				CO.scroll("Suspend", "WebButton");
				Browser.WebButton.click("Suspend");
			} else {
				CO.InstalledAssertChange("InstalledAssertChange");
			}
			CO.waitforload();

			CO.scroll("Due_Date_chicklet", "WebButton");
			Browser.WebButton.click("Due_Date_chicklet");
			Browser.WebButton.click("Date_Now");
			Browser.WebButton.click("Date_Done");
			if (Browser.WebEdit.gettext("Due_Date1").equals(""))
				Driver.Continue.set(false);
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
			CO.Text_Select("a", "Line Items");

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count1 <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Action_Update("Suspend", MSISDN);

			OrderSubmission();
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			Result.takescreenshot("");

			CO.ToWait();
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
	 * Last Modified Date 	: 22-March-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Resume() {
		String Test_OutPut = "", Status = "";
		String MSISDN, Field, Resume_Date, Order_no;
		int Col_F, Col_Resume, Row = 2;
		Result.fUpdateLog("------Resume Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			CO.Assert_Search(MSISDN, "Suspended", "Resume");
			if (Browser.WebButton.exist("Resume")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				for (int i = 2; i <= Inst_RowCount; i++) {
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P)
							.equals("Mobile Service Bundle")) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_P + 1);
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
			Browser.WebButton.click("Date_Now");
			Browser.WebButton.click("Date_Done");
			if (Browser.WebEdit.gettext("Due_Date").equals(""))
				Driver.Continue.set(false);
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();

			CO.scroll("Resume_Date", "WebButton");
			Col_Resume = CO.Select_Cell("Line_Items", "Resume Date");

			Resume_Date = (CO.Col_Data(Col_Resume).trim());
			Result.fUpdateLog(Resume_Date);
			if (Resume_Date.toLowerCase().equals("resume date"))
				Driver.Continue.set(true);
			else
				Driver.Continue.set(false);
			Browser.WebTable.click("Line_Items", Row, Col_Resume);
			CO.ResumeDate("Line_Items", Row, Col_Resume);
			Result.takescreenshot("");
			CO.scroll("Ful_Status", "WebButton");
			Col_F = CO.Select_Cell("Line_Items", "Fulfillment Status");
			Field = (CO.Col_Data(Col_F).trim());
			Result.fUpdateLog(Field);
			if (Field.toLowerCase().equals("fulfillment status"))
				COL_FUL_STATUS = Col_F;
			CO.scroll("Service", "WebButton");
			int Row_Count1;
			CO.scroll("Service", "WebButton");
			CO.scroll("Line_Items", "WebLink");
			CO.Text_Select("a", "Line Items");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");

			if (Row_Count1 <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Action_Update("Resume", MSISDN);
			OrderSubmission();

			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";

			Result.takescreenshot("");
			if (!(getdata("MSISDN").equals(""))) {
				CO.Assert_Search(getdata("MSISDN"), "Active", "Resume");
			} else {
				CO.Assert_Search(pulldata("MSISDN"), "Active", "Resume");
			}

			CO.ToWait();
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
	 * Method Name			: Plan_UpgradeDowngrade
	 * Arguments			: None
	 * Use 					: Change of Plan
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Plan_UpgradeDowngrade() {

		String Test_OutPut = "", Status = "";
		String MSISDN, PlanName, PlanChange_From;
		int Row_Count, Col, Col_P;
		Result.fUpdateLog("------Plan Upgrade/Downgrade Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}
			if (!(getdata("PlanChange_From").equals(""))) {
				PlanChange_From = getdata("PlanChange_From");
			} else {
				PlanChange_From = pulldata("PlanChange_From");
			}
			CO.Assert_Search(MSISDN, "Active", "PlanChange");
			Col = CO.Select_Cell("Acc_Installed_Assert", "Product");
			Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
			for (int i = 1; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col);
				if (LData.equals(PlanChange_From)) {
					Browser.WebTable.click("Acc_Installed_Assert", i, (Col + 1));
					CO.InstalledAssertChange("Upgrade Promotion");

					break;
				}

			}
			CO.waitforload();
			Browser.WebEdit.Set("Promotion_Upgrade", PlanName);
			String Path[] = Utlities.FindObject("Promotion_Upgrade", "WebEdit");
			cDriver.get().findElement(By.xpath(Path[0])).sendKeys(Keys.ENTER);
			CO.waitforload();
			
			if (Browser.WebTable.getRowCount("Promotion_Upgrades") >= 2) {
				CO.scroll("Upgrade_OK", "WebButton");
				Browser.WebButton.click("Upgrade_OK");
			} else {
				Driver.Continue.set(false);
				System.exit(0);
			}

			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (LData.equals(PlanName)) {
					if (Action.equals("Add")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Driver.Continue.set(false);
					}
				}
			}

			CO.waitforload();
			OrderSubmission();

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
		Result.fUpdateLog("------Plan Upgrade/Downgrade Event Details - Completed------");
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
		String MSISDN;
		String Order_no;
		Result.fUpdateLog("------Change Primary Number Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}
			CO.Assert_Search(MSISDN, "Active", "Change_PN");
			CO.Tag_Select("span", "Primary MSISDN");
			if (Browser.WebButton.exist("Assert_Modify")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equals("Mobile Service Bundle")
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID).equals(MSISDN)) {
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
				Driver.Continue.set(false);
				System.out.println("Error On Clicking Done Button");
				System.exit(0);
			}
			Result.takescreenshot("");
			CO.waitforload();
			OrderSubmission();
			CO.waitforload();
			// fetching Order_no
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			String BP = null;
			int Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			int Col1 = CO.Select_Cell("Line_Items", "Product");
			int Col_P1 = CO.Actual_Cell("Line_Items", "Service Id");
			CO.Tag_Select("div", "Billing Profile");
			int Col_B = CO.Select_Cell("Line_Items", "Billing Profile");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				CO.Tag_Select("div", "Product");
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col1);
				String MSDN = Browser.WebTable.getCellData("Line_Items", i, Col_P1);

				if (LData.equals("Mobile Service Bundle") & (MSDN.equals(MSISDN))) {
					BP = Browser.WebTable.getCellData("Line_Items", i, Col_B);
					break;
				}

			}
			CO.waitforload();
			int Row = 2, Col;
			if (Browser.WebButton.exist("VFQ_LeftScroll")) {
				Browser.WebButton.click("VFQ_LeftScroll");
			}
			Browser.WebLink.waittillvisible("VQ_Assert");
			Browser.WebLink.click("VQ_Assert");
			CO.scroll("Assert_Search", "WebLink");
			Browser.WebLink.click("Assert_Search");
			CO.waitforload();
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
				Driver.Continue.set(false);
			Browser.WebLink.waittillvisible("Acc_Portal");
			CO.waitforload();
			Browser.WebLink.click("Acc_Portal");
			Browser.WebLink.waittillvisible("Inst_Assert_ShowMore");
			CO.scroll("Profile_Tab", "WebButton");
			Browser.WebButton.click("Profile_Tab");
			CO.waitforload();
			int Row_Count = Browser.WebTable.getRowCount("Bill_Prof");
			int Col_Val = CO.Select_Cell("Bill_Prof", "Name");
			for (int i = 2; i <= Row_Count; i++) {
				String BillPro = Browser.WebTable.getCellData("Bill_Prof", i, Col_Val);
				if (BillPro.equals(BP)) {
					Browser.WebTable.click("Bill_Prof", i, Col_Val);
					break;
				}

			}
			CO.Text_Select("a", "Unbilled Usage");
			CO.waitforload();

			CO.ToWait();
			if (Driver.Continue.get()) {
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
	 * Method Name			: Disconnect
	 * Arguments			: None
	 * Use 					: Disconnection of Active line
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Disconnect() {

		String Test_OutPut = "", Status = "";
		String MSISDN, PlanName;
		int Col, Col_P;
		Result.fUpdateLog("------Disconnect Event Details------");
		try {
			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}
			CO.Assert_Search(MSISDN, "Active", "Disconnect");
			int Row_Count1 = Browser.WebTable.getRowCount("Installed_Assert");
			Col = CO.Select_Cell("Installed_Assert", "Product");
			Col_P = CO.Actual_Cell("Installed_Assert", "Service ID");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Installed_Assert", i, Col);
				if (LData.equals(PlanName)) {
					Browser.WebTable.click("Installed_Assert", i, Col_P);
					CO.waitforload();
					break;

				}

			}
			Browser.WebButton.click("VFQ_Disconnect");
			CO.waitforload();
			CO.scroll("Due_Date_chicklet", "WebButton");
			Browser.WebButton.click("Due_Date_chicklet");
			Browser.WebButton.click("Date_Now");
			Browser.WebButton.click("Date_Done");
			if (Browser.WebEdit.gettext("Due_Date").equals(""))
				Driver.Continue.set(false);
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();
			Result.takescreenshot("Disconnect Order : ");
			// CO.InstalledAssertChange("Disconnect");
			CO.waitforload();
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (LData.equals(PlanName)) {
					if (Action.equals("Delete")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Driver.Continue.set(false);
					}
				}

			}
			OrderSubmission();
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

}
