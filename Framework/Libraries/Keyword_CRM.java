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

			if (!(getdata("URL").equals(""))) {
				URL.set(getdata("URL"));
			} else {
				URL.set("Chrome");
			}

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

			if (Payment_Type.equalsIgnoreCase("Postpaid")) {
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

			Browser.WebButton.click("Orders_Tab");
			if (CO.isAlertExist())
				Browser.WebButton.click("Orders_Tab");

			int Row = 2, Col, Col_new;
			Browser.WebButton.waitTillEnabled("Order_New");
			CO.scroll("Order_New", "WebButton");
			Browser.WebButton.click("Order_New");

			Col = CO.Get_Col("Order_Table", Row, "Sales Order");
			Browser.WebTable.click("Order_Table", Row, Col);
			Order_No = Browser.WebTable.getCellData("Order_Table", 2, (Col - 1));

			String OD_Date;
			Col_new = CO.Actual_Cell("Order_Table", "Order Date");
			Browser.WebTable.click("Order_Table", Row, Col_new);
			OD_Date = Browser.WebTable.getCellData_title ("Order_Table", 2, Col_new);
			String[] Date = OD_Date.split(" ")[0].split("/");
			OrderDate.set((Date[1] + "-" + Date[0] + "-" + Date[2]));
			Browser.WebTable.click("Order_Table", Row, (Col - 1));

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

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S;
			String Reserve, Category, GetData, Add_Addon, Remove_Addon, ReservationToken,StartNumber, SIM, MSISDN = null,
					SData = "SIM Card";
			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}
			Planname.set(PlanName);

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
			// String Field = CO.Col_Data(Col_S);
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
			
			if (!(getdata("StartNumber").equals(""))) {
				StartNumber = getdata("StartNumber");
			} else {
				StartNumber = pulldata("StartNumber");
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
				Col = CO.Select_Cell("Line_Items", "Product");
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
					CO.Text_Select("span","Customize");
					CO.Link_Select("Others");
					CO.scroll("Star_Number_purch","WebEdit");
					CO.waitforload();
					CO.Text_Select("option","Default");
					//CO.Tag_Select("option","For Testing Only");
					CO.Text_Select("option","For Testing Only");
					CO.waitforload();
					CO.scroll("Star_Number_purch","WebEdit");
					Browser.WebEdit.Set("Star_Number_purch", StartNumber);

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

				/*
				 * if (Field.equalsIgnoreCase("previous service id")) Col_S =
				 * CO.Actual_Cell("Line_Items", "Service Id");
				 */
				Col = CO.Select_Cell("Line_Items", "Product");
				Col_S = CO.Select_Cell("Line_Items", "Service Id");
				for (int i = 2; i <= Row_Count; i++) {
					String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
					if (GetData.equalsIgnoreCase(LData)) {
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
					Continue.set(false);
			} else if (!ReservationToken.equals("")) {
				Row_Count = Browser.WebTable.getRowCount("Line_Items");
				if (Row_Count <= 3) {
					Browser.WebButton.waittillvisible("Expand");
					Browser.WebButton.click("Expand");
				}

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

			OrderSubmission();
			CO.Action_Update("Add", MSISDN);

			if (Continue.get() & (Row_Count >= 3)) {
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
			int Complete_Status = 0, R_S = 0, Wait = 0, Row = 2, Col,Bill_Col, Row_Count;
			String EStatus = "Complete", FStatus = "Failed",Bill_Cycle;

			if (Browser.WebTable.exist("Line_Items"))
				Result.fUpdateLog("Proceeding Order Submission");

			if (UseCaseName.get().toLowerCase().contains("enterprise") || TestCaseN.get().toLowerCase().contains("vip")
					|| UseCaseName.get().contains("SIPT")) {
				CO.scroll("Ent_CreditLimit", "WebEdit");
				Browser.WebEdit.click("Ent_CreditLimit");
				Browser.WebEdit.Set("Ent_CreditLimit", "100");
			} else {
				CO.scroll("Credit_Limit", "WebEdit");
				Browser.WebEdit.click("Credit_Limit");
			}

			// To get fulfillment status coloumn
			CO.scroll("Ful_Status", "WebButton");
			Col = CO.Select_Cell("Line_Items", "Fulfillment Status");
			if (CO.Col_Data(Col).equalsIgnoreCase("fulfillment status"))
				COL_FUL_STATUS = Col;
			CO.scroll("Service", "WebButton");

			Browser.WebButton.waittillvisible("Validate");
			Browser.WebButton.click("Validate");

			CO.isAlertExist();
			if (Validatedata("SmartLimit").equalsIgnoreCase("yes")) {
				String Smartlimit = Utlities.FetchSmartlimit();
				if(Def_Smart_limit.get().equals(Smartlimit)) {
					Result.fUpdateLog("Default Smartlimit : " + Def_Smart_limit.get() );
					Test_OutPut += "Default Smartlimit : " + Def_Smart_limit.get() + ",";
				}else {
					Continue.set(false);
				}
			}
			CO.waitmoreforload();
			if (CO.isAlertExist()) {
				CO.waitmoreforload();
			}

			Browser.WebButton.waittillvisible("Submit");
			CO.scroll("Submit", "WebButton");
			Browser.WebButton.click("Submit");
			CO.waitmoreforload();
			CO.isAlertExist();
			Result.takescreenshot("Order Submission is Successful");

			Col = COL_FUL_STATUS;
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			Status = Browser.WebTable.getCellData("Line_Items", Row, Col);
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}

			Browser.WebButton.waittillvisible("Submit");
			do {
				for (int i = 2; i <= 3; i++) {
					CO.scroll("Submit", "WebButton");
					CO.scroll("Ful_Status", "WebButton");
					OS_Status = Browser.WebTable.getCellData("Line_Items", i, Col);
					System.out.println("Round" + (i - 1) + " " + OS_Status);
					if (EStatus.equalsIgnoreCase(OS_Status)) {// To Find the Complete Status
						Complete_Status = Complete_Status + 1;
						R_S++;
						Wait = Wait + 80;
					} else {
						if (FStatus.equalsIgnoreCase(OS_Status)) {
							Continue.set(false);
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
				Bill_Col = CO.Actual_Cell("Line_Items", "Bill Cycle");
				Bill_Cycle=Browser.WebTable.getCellData("Line_Items", Row, Bill_Col);
				billDate.set(Bill_Cycle);
			}

			if (OS_Status.equalsIgnoreCase(EStatus) || Complete_Status == 2) {
				Complete_Status = 2;
			}else {
				Continue.set(false);
			}

			CO.ToWait();
			if (Continue.get() ) {
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
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", getdata("Cont_FirstName"));
			} else if (!(pulldata("Cont_FirstName").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", pulldata("Cont_FirstName"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "First_Name", Utlities.randname());
			}

			Col = CO.Select_Cell("Acc_Contact", "Last Name");
			if (!(getdata("Cont_LastName").equals(""))) {
				Last_Name = getdata("Cont_LastName");
			} else if (!(pulldata("Cont_LastName").equals(""))) {
				Last_Name = pulldata("Cont_LastName") + R.nextInt(1000);
			} else {
				Last_Name = Utlities.randname();
			}
			Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Last_Name", Last_Name);

			Col = CO.Select_Cell("Acc_Contact", "Mr/Ms");
			if (!(getdata("Cont_Mr/Ms").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "M_M", getdata("Cont_Mr/Ms"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "M_M", pulldata("Cont_Mr/Ms"));
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
			if (!(getdata("Cont_Email").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Email_Address", getdata("Cont_Email"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Email_Address", pulldata("Cont_Email"));
			}

			Col = CO.Select_Cell("Acc_Contact", "Date of Birth");
			if (!(getdata("Cont_DOB").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_DOB", getdata("Cont_DOB"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_DOB", pulldata("Cont_DOB"));
			}

			Col = CO.Select_Cell("Acc_Contact", "ID Expiration Date");
			if (!(getdata("Cont_IDExpiryDate").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date",
						getdata("Cont_IDExpiryDate"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Expiration_Date",
						pulldata("Cont_IDExpiryDate"));
			}

			Col = CO.Select_Cell("Acc_Contact", "ID Number");
			if (!(getdata("Cont_IDNumber").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number", getdata("Cont_IDNumber"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Number",
						pulldata("Cont_IDNumber") + R.nextInt(100000));

			}

			// CO.scroll("Contact_ACC", "WebTable");

			// Col = CO.Select_Cell("Acc_Contact", "ID Type");
			Col++;
			if (!(getdata("Cont_IDType").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Type", getdata("Cont_IDType"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_ID_Type", pulldata("Cont_IDType"));
			}

			Col++;
			// Col = CO.Select_Cell("Acc_Contact", "Mobile Phone #");
			if (!(getdata("Cont_MobilePhone").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Cellular_Phone__", getdata("Cont_MobilePhone"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "Cellular_Phone__", pulldata("Cont_MobilePhone"));
			}

			Col++;
			// Col = CO.Select_Cell("Acc_Contact", "Nationality");
			if (!(getdata("Cont_Nationality").equals(""))) {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_Nationality", getdata("Cont_Nationality"));
			} else {
				Browser.WebTable.SetDataE("Acc_Contact", Row, Col, "VFQ_Nationality", pulldata("Cont_Nationality"));
			}

			Col = Col + 4;
			// Col = CO.Select_Cell("Acc_Contact", "Gender");
			if (!(getdata("Cont_Gender").equals(""))) {
				Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQA_M_F", getdata("Cont_Gender"));
			} else {
				Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQA_M_F", pulldata("Cont_Gender"));
			}

			Col = CO.Select_Cell("Acc_Contact", "Preferred Language");
			if (!(getdata("Cont_PrefLang").equals(""))) {
				Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQ_Preferred_Language", getdata("Cont_PrefLang"));
			} else {
				Browser.WebTable.SetData("Acc_Contact", Row, Col, "VFQ_Preferred_Language", pulldata("Cont_PrefLang"));
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
			CO.Assert_Search(MSISDN, "Active", GetData);

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				// To Find the Record with Mobile Service Bundle and MSISDN
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P).equalsIgnoreCase(GetData)
							& Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_SID)
									.equalsIgnoreCase(MSISDN)) {
						CO.waitforload();
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_P + 1);
						break;
					}
				Browser.WebButton.click("Assert_Modify");
				CO.waitforload();

			} else
				CO.InstalledAssertChange("Modify");
			CO.waitforload();
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
			Add_Addon = "Paid Addons,Vodafone Passport";
			LineItemData.clear();
			CO.Status(Add_Addon);
			CO.waitforload();
			CO.Status(Remove_Addon);
			CO.waitforload();

			// OrderSubmission();
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
			CO.Assert_Search(MSISDN, "Active", GetData);

			if (Browser.WebButton.exist("Suspend")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				int Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");

				Result.fUpdateLog(Col_P + "," + Col_SID);
				Result.fUpdateLog(Browser.WebTable.getCellData("Acc_Installed_Assert", 3, Col_P));
				for (int i = 2; i <= Inst_RowCount; i++)
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P)
							.equalsIgnoreCase("Mobile Service Bundle")) {
						Browser.WebTable.click("Acc_Installed_Assert", i, Col_P + 1);
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
			Browser.WebButton.click("Date_Now");
			Browser.WebButton.click("Date_Done");
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

			OrderSubmission();
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
			CO.Assert_Search(MSISDN, "Suspended", GetData);
			if (Browser.WebButton.exist("Resume")) {
				int Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				int Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				for (int i = 2; i <= Inst_RowCount; i++) {
					if (Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col_P)
							.equalsIgnoreCase("Mobile Service Bundle")) {
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
	 * Method Name			: Plan_UpgradeDowngrade
	 * Arguments			: None
	 * Use 					: Change of Plan
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Plan_UpgradeDowngrade() {

		String Test_OutPut = "", Status = "";
		String MSISDN, New_PlanName, Existing_Plan, GetData;
		int Row_Count, Col, Col_P;
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

			if (!(getdata("Existing_PlanName").equals(""))) {
				Existing_Plan = getdata("Existing_PlanName");
			} else {
				Existing_Plan = pulldata("Existing_PlanName");
			}
			if (!(getdata("GetData").equals(""))) {
				GetData = getdata("GetData");
			} else {
				GetData = pulldata("GetData");
			}
			CO.Assert_Search(MSISDN, "Active", GetData);
			Col = CO.Select_Cell("Acc_Installed_Assert", "Product");
			Row_Count = Browser.WebTable.getRowCount("Acc_Installed_Assert");
			for (int i = 1; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Acc_Installed_Assert", i, Col);
				if (LData.equalsIgnoreCase(Existing_Plan)) {
					Browser.WebTable.click("Acc_Installed_Assert", i, (Col + 1));
					CO.InstalledAssertChange("Upgrade Promotion");

					break;
				}

			}
			CO.waitmoreforload();
			Browser.WebEdit.Set("Promotion_Upgrade", New_PlanName);
			String Path[] = Utlities.FindObject("Promotion_Upgrade", "WebEdit");
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
				} else if (LData.equalsIgnoreCase(Existing_Plan)) {
					if (Action.equalsIgnoreCase("Delete")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				}
			}

			CO.waitforload();
			OrderSubmission();

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
			CO.Assert_Search(MSISDN, "Active", GetData);
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

				if (LData.equalsIgnoreCase(GetData) & (MSDN.equalsIgnoreCase(MSISDN))) {
					BP = Browser.WebTable.getCellData("Line_Items", i, Col_B);
					break;
				}

			}

			CO.RTBScreen(MSISDN, "Active", BP);
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
	 * Method Name			: Disconnect
	 * Arguments			: None
	 * Use 					: Disconnection of Active line
	 * Designed By			: Sravani Reddy
	 * Last Modified Date 	: 27-Sep-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Disconnect() {

		String Test_OutPut = "", Status = "";
		String MSISDN, PlanName, Order_no, Order_Reason, GetData;
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
			Planname.set(PlanName);

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
			CO.Assert_Search(MSISDN, "Active", GetData);
			int Row_Count1 = Browser.WebTable.getRowCount("Installed_Assert");
			Col = CO.Select_Cell("Installed_Assert", "Product");
			Col_P = CO.Actual_Cell("Installed_Assert", "Service ID");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Installed_Assert", i, Col);
				if (LData.equalsIgnoreCase(PlanName)) {
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
				Continue.set(false);
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitmoreforload();
			Result.takescreenshot("Disconnect Order : ");
			// CO.InstalledAssertChange("Disconnect");
			CO.waitforload();
			CO.Webtable_Value("Order Reason", Order_Reason);

			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_P = CO.Actual_Cell("Line_Items", "Action");
			Row_Count1 = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count1; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				String Action = Browser.WebTable.getCellData("Line_Items", i, Col_P);

				if (LData.equalsIgnoreCase(PlanName)) {
					if (Action.equalsIgnoreCase("Delete")) {
						Result.fUpdateLog("Action Update   " + LData + ":" + Action);
					} else {
						Result.fUpdateLog(LData + ":" + Action);
						Continue.set(false);
					}
				}

			}

			OrderSubmission();
			Order_no = CO.Order_ID();
			Utlities.StoreValue("Order_no", Order_no);
			Test_OutPut += "Order_no : " + Order_no + ",";
			String BP = null;
			int Row_Count = Browser.WebTable.getRowCount("Line_Items");
			int Col1 = CO.Select_Cell("Line_IStems", "Product");
			int Col_P1 = CO.Actual_Cell("Line_Items", "Service Id");
			CO.Tag_Select("div", "Billing Profile");
			int Col_B = CO.Select_Cell("Line_Items", "Billing Profile");
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			for (int i = 2; i <= Row_Count; i++) {
				CO.Tag_Select("div", "Product");
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col1);
				String MSDN = Browser.WebTable.getCellData("Line_Items", i, Col_P1);
				if (LData.equalsIgnoreCase("Mobile Service Bundle") & (MSDN.equalsIgnoreCase(MSISDN))) {
					BP = Browser.WebTable.getCellData("Line_Items", i, Col_B);
					break;
				}

			}
			CO.waitforload();

			CO.RTBScreen(MSISDN, "Active", BP);
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
	 * Method Name			: Barring
	 * Arguments			: None
	 * Use 					: Barring of active services
	 * Designed By			: Lavannya Mahalingam
	 * Last Modified Date 	: 03-Oct-2017
	--------------------------------------------------------------------------------------------------------*/
	public String Barring() {
		String Test_OutPut = "", Status = "";
		String MSISDN, GetData;
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
			CO.Assert_Search(MSISDN, "Active", GetData);

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				// To Find the Record with Mobile Service Bundle and MSISDN
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

			Browser.WebButton.waittillvisible("Validate");
			OrderSubmission();

			// CO.Assert_Search(MSISDN, "Active");

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
		return Status + "@@" + Test_OutPut;
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
		String MSISDN, GetData;
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
			CO.Assert_Search(MSISDN, "Active", GetData);

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				// To Find the Record with Mobile Service Bundle and MSISDN
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
			CO.waitforload();
			CO.scroll("Date_Continue", "WebButton");
			Browser.WebButton.click("Date_Continue");
			CO.waitforload();

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
			OrderSubmission();

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
		return Status + "@@" + Test_OutPut;
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

			CO.Assert_Search(MSISDN, "Active", GetData);

			if (Browser.WebButton.exist("Assert_Modify")) {

				Inst_RowCount = Browser.WebTable.getRowCount("Acc_Installed_Assert");
				Col_P = CO.Select_Cell("Acc_Installed_Assert", "Product");
				Col_SID = CO.Select_Cell("Acc_Installed_Assert", "Service ID");
				// To Find the Record with Mobile Service Bundle and MSISDN
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

			Row_Count = Browser.WebTable.getRowCount("Line_Items");

			Browser.WebButton.waittillvisible("Expand");
			Browser.WebButton.click("Expand");

			CO.waitforload();
			Col = CO.Select_Cell("Line_Items", "Product");
			Col_S = CO.Select_Cell("Line_Items", "Service Id");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equalsIgnoreCase(LData))
					Row_Val = i;
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", SIM);

			OrderSubmission();
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
}
