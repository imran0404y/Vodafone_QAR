package Libraries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Keyword_Validations extends Driver {
	Common CO = new Common();

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: RTB_Validation
	 * Use 					: To perform RTBValidation with the Line Items fetched from FetchProcuctCatalogData Method
	 * Argument				: Null
	 * Designed By			: Vinodhini Raviprasad
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 18-Oct-2017  
	--------------------------------------------------------------------------------------------------------*/
	public String RTB_Validation() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------RTB Validation Event Details------");
		try {
			String Surepay, Benefits, Product_Validity, Siebel_Desc;// ,BID,SubscriptionPrice
			/*
			 * String[]a=
			 * {"399	||300_Mins	||Bill_Cycle	||Red3B	||750	||Red 750 roaming terminating voice: Your account has &CV minutes, expiring on &BE"
			 * ,
			 * "400	||0_Mins	||Bill_Cycle	||Red3D	||750	||Red 750 Roaming minutes to Vodafone Qatar: Your account has &CV minutes, expiring on &BE"
			 * ,
			 * "662	||50000_Units	||Bill_Cycle	||Red3A	||750	||Red 750 Local Voice/SMS: Your account has &CV Voice/SMS units, expiring on &BE"
			 * ,
			 * "665	||200_GB	||Bill_Cycle	||Red3B	||750	||Red 750 Local and Roaming Data: Your account has &CV Kbytes of data, expiring on &BE"
			 * ,
			 * "668	||0_kB	||Bill_Cycle	||Red3B	||750	||Red 750 Blackberry: Your account has &CV Kbytes of data, expiring on &BE"
			 * ,
			 * "790	||600_Mins	||Bill_Cycle	||Red3B	||750	||Red 750 international minutes: Your account has &CV minutes, expiring on &BE"
			 * ,
			 * "998	||15_GB	||Bill_Cycle	||Red3B	||750	||Red 750 data roaming: Your account has &CV Kbytes of data, expiring on &BE"
			 * ,
			 * "X92	||5_GB	||Bill_Cycle	||REDDTBS	||0	||Red Data Bonus:Your account has &CV Kbytes, expiring on &BE"
			 * };
			 */

			// ArrayList <String> FetchProduct =new ArrayList<String>(Arrays.asList(a));
			// Comment the above two declarations to get the exact values
			ArrayList<String> FetchProduct = Utlities.FetchProcuctCatalogData();
			for (int i = 0; i < FetchProduct.size(); i++) {
				String FetchPC[] = FetchProduct.get(i).split("\\|\\|");
				Surepay = FetchPC[0].trim();
				Benefits = FetchPC[1].trim();
				Product_Validity = FetchPC[2].trim();
				Siebel_Desc = FetchPC[5].trim();
				String BE = BE(Siebel_Desc, Benefits, Product_Validity);
				Result.fUpdateLog(BE);
				if (BE.equals(RTBOutputData.get(Surepay)))
					Continue.set(true);
				else
					Continue.set(false);
			}

			CO.ToWait();
			if (Continue.get()) {
				Test_OutPut += "RTB Validation is done Successfully " + ",";
				Result.fUpdateLog("RTB Validation is done Successfully ");
				Status = "PASS";
			} else {
				Result.fUpdateLog("RTB Validation Failed");
				Status = "FAIL";
			}

		} catch (Exception e) {
			Test_OutPut += "Exception occurred" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------RTB Validation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: BE
	 * Use 					: To find the Bundle Expiry Date for Siebel Description
	 * Argument				: Desc- Siebel Description,Benifit- Benifit, Product_Validity - Product Validity
	 * Designed By			: Vinodhini Raviprasad
	 * Modified By			: Vinodhini Raviprasad
	 * Last Modified Date 	: 17-Oct-2017  
	--------------------------------------------------------------------------------------------------------*/
	public String BE(String Desc, String Benifit, String Product_Validity) {
		try {
			DateFormat Date_Format = new SimpleDateFormat("dd/MM/yyyy");

			String billcycledate, Expiry, orderdate = "09/10/2017";// OrderSubmissionDate
			billcycledate = CO.FindBillingCycle(orderdate);
			Calendar cals = Calendar.getInstance();
			cals.set(Calendar.YEAR, Integer.parseInt(orderdate.split("/")[2]));
			cals.set(Calendar.MONTH, Integer.parseInt(orderdate.split("/")[1]) - 1);
			cals.set(Calendar.DATE, Integer.parseInt(orderdate.split("/")[0]));
			Date startDate = Date_Format.parse(orderdate);
			Date endDate = Date_Format.parse(billcycledate);
			int difInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
			int Total_Days = cals.getActualMaximum(Calendar.DATE);

			String Prorate = "";

			Expiry = orderdate;

			switch (Product_Validity.toLowerCase()) {
			case "bill_cycle":
				Prorate = CO.Prorated(Total_Days, difInDays, Benifit);
				Expiry = billcycledate;
				break;
			case "day":
				Prorate = CO.Prorated(1, 1, Benifit);
				Expiry = orderdate;
				break;
			case "month":
			case "black":
				Prorate = CO.Prorated(Total_Days, Total_Days, Benifit);
				cals.add(Calendar.DATE, Total_Days);
				Expiry = Date_Format.format(cals.getTime());// Total_Days+Month
				break;
			}

			Desc = Desc.replace("&CV", Prorate + "");
			Desc = Desc.replace("&BE", Expiry);
			return Desc;
		} catch (Exception e) {
			return "";
		}
	}

}
