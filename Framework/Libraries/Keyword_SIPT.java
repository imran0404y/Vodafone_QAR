package Libraries;

import java.util.Random;

public class Keyword_SIPT extends Driver {
	Common CO = new Common();
	Random R = new Random();

	public String SIPT() {
		String Test_OutPut = "", Status = "";
		String PlanName = null;
		Result.fUpdateLog("------Plan Selection Event Details------");
		try {

			int Row_Val = 3, Col_V, COl_STyp, Col_Res, Col_S;
			String Category, GetData, COSP_Plan, ReservationToken, To, MSISDN, // ,Reserve
					From = null;
			GetData = "Corporate SIP Trunk Bundle";
			CO.waitforload();

			if (!(getdata("PlanName").equals(""))) {
				PlanName = getdata("PlanName");
			} else {
				PlanName = pulldata("PlanName");
			}

			// To use Catalog view uncomment the below lines
			/*
			 * Browser.WebLink.click("VQ_Catalog");
			 * CO.Category_Select(pulldata("Plan_View"),pulldata("Plan_Catagory"),PlanName);
			 * Browser.WebButton.click("LI_New"); CO.waitforload();
			 */
			// ------------------------------------------------------------
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
			Col_V = Col + 2;

			for (int i = 2; i <= Row_Count; i++) {
				String LData = Browser.WebTable.getCellData("Line_Items", i, Col);
				if (GetData.equals(LData)) {
					Row_Val = i;
					break;
				}
			}
			Browser.WebTable.click("Line_Items", Row_Val, Col_V);

			if (!(getdata("COSP_Plan").equals(""))) {
				COSP_Plan = getdata("COSP_Plan");
			} else {
				COSP_Plan = pulldata("COSP_Plan");
			}

			if (!(getdata("From").equals(""))) {
				From = getdata("From");
			} else {
				From = pulldata("From");
			}

			if (!(getdata("To").equals(""))) {
				To = getdata("To");
			} else {
				To = pulldata("To");
			}

			if (!(getdata("ReservationToken").equals(""))) {
				ReservationToken = getdata("ReservationToken");
			} else {
				ReservationToken = pulldata("ReservationToken");
			}

			if (!(getdata("MSISDN").equals(""))) {
				MSISDN = getdata("MSISDN");
			} else {
				MSISDN = pulldata("MSISDN");
			}

			Browser.WebButton.click("Customize");

			CO.AddOnSelection("COSP Plans," + COSP_Plan, "Add");
			CO.NumberRangeProducts("DID Number Range", "1", From, To, ReservationToken);
			CO.waitforload();
			CO.Text_Select("button", "Verify");
			CO.isAlertExist();
			CO.waitforload();
			CO.Text_Select("button", "Done");
			CO.waitforload();
			if (CO.isAlertExist())
				Driver.Continue.set(false);

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
				Browser.WebTable.SetData("Numbers", Row, COl_STyp, "Service_Type", "SIPT");

				if (!MSISDN.equals("")) {

					// Reserve = MSISDN.substring(3, MSISDN.length());
					Browser.WebTable.SetData("Numbers", Row, Col_Res, "Service_Id", MSISDN);
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

				CO.Link_Select("Line Items");
				Col_S = CO.Select_Cell("Line_Items", "Service Id");
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
				if (Row_Count > 1) {
					Browser.WebButton.click("Reserved_Ok");
					Row_Count = Browser.WebTable.getRowCount("Line_Items");
				} else
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

			if (Driver.Continue.get() & (Row_Count >= 3)) {
				Status = "PASS";
				Utlities.StoreValue("PlanName", PlanName);
				Test_OutPut += "PlanName : " + PlanName + ",";
				Utlities.StoreValue("COSP_Plan", COSP_Plan);
				Test_OutPut += "COSP_Plan : " + COSP_Plan + ",";
				Utlities.StoreValue("From", From);
				Test_OutPut += "From : " + From + ",";
				Utlities.StoreValue("To", To);
				Test_OutPut += "To : " + To + ",";
				Utlities.StoreValue("SIPT", MSISDN);
				Test_OutPut += "SIPT : " + MSISDN + ",";
				Result.takescreenshot("Plan Selection is Successful : " + PlanName);
				Result.fUpdateLog("Plan Selection for " + PlanName + "is done Successfully");
			} else {
				Status = "FAIL";
				Test_OutPut += "SIPT Plan Selection Failed" + ",";
				Result.takescreenshot("SIPT Plan Selection Failed");
				Result.fUpdateLog("SIPT Plan Selection Failed");
			}

		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception occurred" + ",";
			Result.takescreenshot("Exception occurred");
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			e.printStackTrace();
		}
		Result.fUpdateLog("------SIPT Plan Selection Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}
}
