package Libraries;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class Utlities extends Driver {

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: FindObject
	 * Arguments			: Object Name and Object Type
	 * Use 					: Read the object property from the Object DataBase  excel and return the value
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 14-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static String[] FindObject(String name, String objtype) {
		try {
			Fillo fillo = new Fillo();
			Connection ORconn = fillo.getConnection(OR_File.get());
			Recordset rs = ORconn.executeQuery("Select * from " + objtype + " Where ObjectName = \'" + name + "\'");
			String cellval1 = "blank";
			String cellval2 = "blank";
			String cellval3 = "blank";
			String cellval4 = "blank";
			String cellval5 = "blank";
			while (rs.next()) {
				cellval1 = rs.getField(1).value();
				cellval2 = rs.getField(2).value();
				cellval3 = rs.getField(3).value();
				cellval4 = rs.getField(4).value();
				cellval5 = rs.getField(5).value();

			}
			String[] retval = { cellval1, cellval2, cellval3, cellval4, cellval5 };
			rs.close();
			ORconn.close();
			return retval;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: floadUseCases
	 * Arguments			: 
	 * Use 					: return all the usecases and testcases list
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 14-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	public static ArrayList<String[]> floadUseCases() {
		try {
			ArrayList<String[]> addresses = new ArrayList<String[]>();
			Fillo nfillo = new Fillo();
			Connection connection = nfillo.getConnection(TestDataDB_File.get());
			// String strQuery = "Select * from TestData where Control = \'YES\' ORDER BY
			// SeqNo ASC";
			String strQuery = "Select * from TestData where RunControl = \'YES\' ORDER BY SeqNo ASC";
			Recordset rs = connection.executeQuery(strQuery);
			rs.moveFirst();
			String[] ID = new String[rs.getCount()];
			String[] UseCases = new String[rs.getCount()];
			String[] Testcase = new String[rs.getCount()];
			String[] data = new String[rs.getCount()];
			String[] ValidationData = new String[rs.getCount()];
			String[] TC_Description = new String[rs.getCount()];

			for (int currs = 1; currs <= rs.getCount(); currs++) {
				ID[currs - 1] = rs.getField(2).value();
				UseCases[currs - 1] = rs.getField(3).value();
				Testcase[currs - 1] = rs.getField(4).value();
				TC_Description[currs - 1] = rs.getField(5).value();
				data[currs - 1] = rs.getField(6).value();
				ValidationData[currs - 1] = rs.getField(7).value();
				if (rs.hasNext()) {
					rs.moveNext();
				}

			}
			addresses.add(0, ID);
			addresses.add(1, UseCases);
			addresses.add(2, Testcase);
			addresses.add(3, TC_Description);
			addresses.add(4, data);
			addresses.add(5, ValidationData);
			rs.close();
			connection.close();
			return addresses;
		} catch (Exception ex) {
			System.err.print("Exception: ");
			System.err.println(ex.getMessage());
		}
		return null;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: floadKeywords
	 * Arguments			: The current Usecase that is being executed.
	 * Use 					: Returns the list of keywords,DataBinding,Description mapped under the Usecase 
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	public static ArrayList<String[]> floadKeywords(String currentUseCase) {
		try {
			ArrayList<String[]> addresses = new ArrayList<String[]>();
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(TestDataDB_File.get());
			String strQuery = "Select * from Keywords Where UseCase = \'" + currentUseCase + "\' ORDER BY SeqNo ASC";
			Recordset rs = connection.executeQuery(strQuery);

			String[] Keyword = new String[rs.getCount()];
			String[] DataBinding = new String[rs.getCount()];
			String[] Description = new String[rs.getCount()];

			rs.moveFirst();
			for (int currtc = 1; currtc <= rs.getCount(); currtc++) {
				Keyword[currtc - 1] = rs.getField(2).value();
				DataBinding[currtc - 1] = rs.getField(3).value();
				Description[currtc - 1] = rs.getField(4).value();

				if (rs.hasNext()) {
					rs.moveNext();
				}
			}
			addresses.add(0, Keyword);
			addresses.add(1, DataBinding);
			addresses.add(2, Description);
			rs.close();
			connection.close();
			return addresses;
		} catch (Exception ex) {
			System.err.print("Exception: ");
			System.err.println(ex.getMessage());
		}
		return null;
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: freaddata
	 * Arguments			: Data for the particular usecase
	 * Use 					: To split data with delimiter and assiging to hashmap
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static Object freaddata(String data) {
		Dictionary<String, String> dict = new Hashtable<String, String>();
		String[] DataSap = data.split("\\|\\|");

		for (int readloop = 0; readloop < DataSap.length; readloop++) {
			String[] Sapdata = DataSap[readloop].split("--");
			if (Sapdata.length == 2) {
				dict.put(Sapdata[0], Sapdata[1]);
			} else {
				dict.put(Sapdata[0], "");
			}

		}
		return dict;
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: fdatabase
	 * Arguments			: The current Usecase that is being executed.
	 * Use 					: to fetch data form database workbook
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static Object fdatabase(String data) {
		Dictionary<String, String> dict = new Hashtable<String, String>();
		try {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(Database_File.get());
			String strQuery = "Select * from " + data + " Where TestCase = \'" + TestCaseN.get().toString() + "\'";

			Recordset rs = connection.executeQuery(strQuery);
			int noOfColumns = rs.getFieldNames().size();
			ArrayList<String> fieldnames = rs.getFieldNames();
			rs.moveNext();
			for (int readloop = 0; readloop < noOfColumns; readloop++) {
				String colname = fieldnames.get(readloop);
				String dat = rs.getField(readloop).value();
				if (dat == null) {
					dict.put(colname, "");
				} else {
					dict.put(colname, dat);
				}
			}
			rs.close();
			connection.close();
			return dict;
		} catch (Exception e) {
			Result.fUpdateLog("No data is available in database sheet for particular Usecase : " + data);
			return null;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: freaddata_diff
	 * Arguments			: The current Keyword that is being executed.
	 * Use 					: fetch data form the TestdataDb workbook form Details sheet
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static Object freaddata_diff(String data) {
		try {
			String Screen = "LoginDetails";
			Dictionary<String, String> dict = new Hashtable<String, String>();

			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(TestDataDB_File.get());
			String strQuery = "Select * from " + Screen + "  Where Application_Details = \'" + data + "\'";

			Recordset rs = connection.executeQuery(strQuery);
			int noOfColumns = rs.getFieldNames().size();
			ArrayList<String> fieldnames = rs.getFieldNames();
			rs.moveNext();
			for (int readloop = 0; readloop < noOfColumns; readloop++) {
				String colname = fieldnames.get(readloop);
				if (!colname.equals("Application_Details")) {
					String dat = rs.getField(readloop).value();
					if (dat == null) {
						dict.put(colname, "");
					} else {
						dict.put(colname, dat);
					}
				}
			}
			rs.close();
			connection.close();
			return dict;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: StoreValue
	 * Arguments			: Dynamic_Name,Dynamic_Value
	 * Use 					: to store the data to storeDb
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void StoreValue(String Name, String Value) {
		try {
			Result.fUpdateLog(Name + " : " + Value);
			String StoreDBpth = Storage_FLD.get() + "/StoreDB.xlsx";
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(StoreDBpth);
			String StrQuery = "INSERT INTO Dynamic_DataStore(Dynamic_UseCase,Dynamic_TestCase,Dynamic_Name,Dynamic_Value,Keyword,Date) VALUES('"
					+ UseCaseName.get() + "','" + TestCaseN.get() + "','" + Name + "','" + Value + "','" + currKW.get()
					+ "','" + keywordstartdate.get() + "')";
			connection.executeUpdate(StrQuery);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: FetchStoredValue
	 * Arguments			: -
	 * Use 					: to get the data from the storeDb
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static String FetchStoredValue(String keyword) {
		try {
			String StoreDBpth = Storage_FLD.get() + "/StoreDB.xlsx";
			// System.out.println(StoreDBpth+","+keywordstartdate.get());//tempfold.get() +
			// "/" + BatchName.get() + "/StoreDB/StoreDB.xls";
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(StoreDBpth);
			String StrQuery = "Select * from Dynamic_DataStore where Dynamic_TestCase='" + TestCaseN.get()
					+ "' and Date='" + keywordstartdate.get() + "' and Dynamic_UseCase='" + UseCaseName.get()
					+ "' and Keyword='" + keyword + "'";
			Recordset rs = connection.executeQuery(StrQuery);
			rs.moveFirst();
			// System.out.println(rs.getField("Dynamic_Name")+"
			// "+rs.getField("Dynamic_Value"));
			rs.close();
			connection.close();
			return rs.getField("Dynamic_Value");

		} catch (Exception e) {
			// System.out.println("No Values");
			return "";
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: randname
	 * Arguments			: -
	 * Use 					: Picks random names from the name DB
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static String randname() {
		try {
			Random rn = new Random();
			int Min = 2;
			int Max = 859;
			int randnumber = rn.nextInt((Max - Min) + 1) + Min;
			String refid = "Name" + randnumber;
			String NameDBpth = Storage_FLD.get() + "/NameDB.xlsx";
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(NameDBpth);
			String strQuery = "Select * from NameDB Where RefID ='" + refid + "'";
			Recordset rs = connection.executeQuery(strQuery);
			rs.moveFirst();
			String refname = rs.getField(1).value();
			rs.close();
			connection.close();
			return refname;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: FetchStoredValue
	 * Arguments			: -
	 * Use 					: to get the data from the Product catalog sheet
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	public static ArrayList<String> FetchProcuctCatalogData() {
		ArrayList<String> PCSAll = new ArrayList<String>();
		try {
			String BundleID, StoreDBpth = Storage_FLD.get() + "/AutomationProductCatalog.xlsx";
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(StoreDBpth);
			Planname.set("Postpaid Connect 60 Promotionis");
			LineItemData.put("0", "Mobile Service Bundle");
			LineItemData.put("1", "Connect 60");
			LineItemData.put("2", "Postpaid Connect 60 Promotionis");
			LineItemData.put("3", "SIM Card");
			LineItemData.put("4", "Standard Pack");
			LineItemData.put("5", "Unlimited Family Calls");
			LineItemData.put("6", "Bill Manager");
			LineItemData.put("7", "Smart Limit");
			LineItemData.put("9", "Mobile Voicemail");
			int k = 0;
			for (int i = 0; i < LineItemData.size(); i++) {
				String Product = LineItemData.get(Integer.toString(i));
				switch (Product) {
				case "Smart Limit":
				case "SIM Card":
				case "Mobile Voicemail":
				case "Bill Manager":
				case "Mobile Service Bundle":
					System.out.println("Item available in Line items");
					break;
				default:
					if (!(Product.equalsIgnoreCase(Planname.get()))) {
						String StrQuery = "Select * from AutCatalogue where Siebel_Plan_Name='" + Product + "'";
						Recordset rs = connection.executeQuery(StrQuery);
						rs.moveNext();
						for (int currs = 1; currs <= rs.getCount(); currs++) {
							if ((!rs.getField("Siebel_Description").isEmpty())) {
								BundleID = rs.getField("BundleID");
								if (BundleID.isEmpty())
									BundleID = " ";
								String Type = rs.getField("SurePayID") + "||" + rs.getField("Benefit") + "||"
										+ rs.getField("BucketValue") + "||" + rs.getField("BucketUsageType") + "||"
										+ rs.getField("ProductValidity") + "||" + BundleID + "||"
										+ rs.getField("SubscriptionPrice") + "||" + rs.getField("Siebel_Description");
								PCSAll.add(k, Type);
								k++;	
							}
							if (rs.hasNext()) {
								rs.moveNext();
							}
						}
						rs.close();
					} else {
						Result.fUpdateLog("Plan name matches");
					}
				}
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return PCSAll;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * Method Name			: FetchStoredValue
	 * Arguments			: -
	 * Use 					: to get the data from the storeDb
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static String FetchSmartlimit() {
		try {
			String value;
			String StoreDBpth = Storage_FLD.get() + "/AutomationProductCatalog.xlsx";
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(StoreDBpth);
			String StrQuery = "Select * from Smartlimit where PlanName='" + Planname.get() + "'";
			Recordset rs = connection.executeQuery(StrQuery);
			rs.moveFirst();
			// System.out.println(rs.getField("Dynamic_Name")+"
			// "+rs.getField("Dynamic_Value"));
			value = rs.getField("DefaultSmartLimit");
			rs.close();
			connection.close();
			return value;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
