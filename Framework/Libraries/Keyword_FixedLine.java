package Libraries;

public class Keyword_FixedLine extends Driver {
	Common CO = new Common();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection_FL(Under Construction)
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public String PlanSelection_FL() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S, Col_b, Col_Vo;
			String SData = "SIM Card", GetData, Field, Add_Addon, Remove_Addon, MSISDN, ReservationToken;

			if (Browser.WebButton.exist("LI_New"))
				System.out.println("Proceeding Plan Selection");
			else {
				Continue.set(true);
				// CO.OrderSearch(Utlities.FetchStoredValue("SalesOrder"));
			}
			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}

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
			// Browser.WebButton.click("LI_New");
			int Row = 2, Col;
			Col = CO.Select_Cell("Line_Items", "Product");
			// Testing

			// -----------------------

			int Row_Count = Browser.WebTable.getRowCount("Line_Items");

			Col_S = CO.Select_Cell("Line_Items", "Service Id");
			Field = CO.Col_Data(Col_S);
			// To select the Mobile Bundle
			Col_V = Col + 2;

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

			if (!(getdata("ReservationToken").equals(""))) {
				ReservationToken = getdata("ReservationToken");
			} else {
				ReservationToken = pulldata("ReservationToken");
			}

			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			GetData = "Broadband Internet Service";

			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData))
					Row_Val = i;
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", MSISDN);

			// To Provide SIM No
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.waitforload();
			SData = "VoIP Service";
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (SData.equals(LData))
					Row_Val = i;
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_S);
			Browser.WebTable.SetData("Line_Items", Row_Val, Col_S, "Service_Id", MSISDN);
			int Col_SP = CO.Select_Cell("Line_Items", "Service Point");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			CO.waitforload();

			Browser.WebTable.Popup("Line_Items", Row_Val, Col_SP);
			CO.Title_Select("button", "Pick Service Point:Query");
			int Col1;
			Col1 = CO.Actual_Cell("LI_ServPoint_head", "Service Point");
			Browser.WebTable.SetDataE("LI_ServPoint", Row, Col1, "Service_Point_Id", MSISDN);
			Col = CO.Actual_Cell("LI_ServPoint_head", "Location");
			Browser.WebTable.SetDataE("LI_ServPoint", Row, Col, "Location", MSISDN);
			Browser.WebButton.click("Li_ServPoint_go");

			CO.Title_Select("button", "Pick Service Point:OK");

			GetData = "Broadband Internet Service";
			Col = CO.Select_Cell("Line_Items", "Product");

			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			int Con_No = CO.Select_Cell("Line_Items", "Notification Contact");
			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			CO.waitforload();
			Browser.WebTable.Popup("Line_Items", Row_Val, Con_No);
			CO.waitforload();
			CO.Popup_Selection("Number_Selection", "Number", MSISDN);
			
			CO.Title_Select("button", "Pick Contact:OK");

			Browser.WebButton.click("Activ_New");

			CO.Text_Select("span", "Book Appointment");
			CO.Text_Select("span", "Confirm");

			CO.Action_Update("Add", "MSISDN");

			if (Continue.get()) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Test_OutPut += "PlanName : " + PlanName + ",";
				Utlities.StoreValue("MSISDN", MSISDN);
				Test_OutPut += "MSISDN : " + MSISDN + ",";

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

	/*--------------------------------------------------------------------------------------------------------
	 * Method Name			: PlanSelection_FL_ENT(Under Construction)
	 * Arguments			: None
	 * Use 					: Specified Plan is selected for the Order in Vanilla Journey
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public String PlanSelection_FL_ENT() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {
			String Charge, IP_Type, Res_CPE, Corp_SLA, Discount, Pf_Report;
			String Add_Addon, GetData, MSISDN = null, SData = "SIM Card", Order_no;

			if (Browser.WebButton.exist("LI_New"))
				System.out.println("Proceeding Plan Selection");
			else {
				Continue.set(true);
				// CO.OrderSearch(Utlities.FetchStoredValue("SalesOrder"));
			}
			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
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
			CO.scroll("LI_New", "WebButton");
			Browser.WebButton.click("LI_New");
			int Row = 2, Col;
			Col = CO.Select_Cell("Line_Items", "Product");
			Browser.WebTable.SetDataE("Line_Items", Row, Col, "Product", PlanName);
			Browser.WebTable.click("Line_Items", Row, Col + 1);
			CO.waitforload();
			// -----------------------

			int Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (!(getdata("Connection_Charge").equals(""))) {
				Charge = getdata("Charges");
			} else {
				Charge = pulldata("Charges");
			}

			if (!(getdata("Static_&_Dynamic_IP_Address").equals(""))) {
				IP_Type = getdata("Static_&_Dynamic_IP_Address");
			} else {
				IP_Type = pulldata("Static_&_Dynamic_IP_Address");
			}
			if (!(getdata("Resilience_&_CPE").equals(""))) {
				Res_CPE = getdata("Resilience_&_CPE");
			} else {
				Res_CPE = pulldata("Resilience_&_CPE");
			}
			if (!(getdata("Corporate_SLA").equals(""))) {
				Corp_SLA = getdata("Corporate_SLA");
			} else {
				Corp_SLA = pulldata("Corporate_SLA");
			}
			if (!(getdata("Discount").equals(""))) {
				Discount = getdata("Discount");
			} else {
				Discount = pulldata("Discount");
			}
			if (!(getdata("Performance_Reporting").equals(""))) {
				Pf_Report = getdata("Performance_Reporting");
			} else {
				Pf_Report = pulldata("Performance_Reporting");
			}

			if (!(Charge == "")) {
				CO.AddOnSelection(Add_Addon, "Add");
			}

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
			CO.waitforload();
			Row_Count = Browser.WebTable.getRowCount("Line_Items");
			if (Row_Count <= 3) {
				Browser.WebButton.waittillvisible("Expand");
				Browser.WebButton.click("Expand");
			}
			CO.Status(Add_Addon);
			CO.waitforload();

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
}
