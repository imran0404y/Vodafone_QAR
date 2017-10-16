package Libraries;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;

@SuppressWarnings("rawtypes")
public class Driver {

	static Calendar cal = Calendar.getInstance();
	public static ThreadLocal<String> WorkingDir = new ThreadLocal<String>();
	public static ThreadLocal<String> Base_Path = new ThreadLocal<String>();
	public static ThreadLocal<String> Storage_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> OR_File = new ThreadLocal<String>();
	public static ThreadLocal<String> StoreDB_File = new ThreadLocal<String>();
	public static ThreadLocal<String> Directory_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> Database_File = new ThreadLocal<String>();
	public static ThreadLocal<String> TestDataDB_File = new ThreadLocal<String>();
	public static ThreadLocal<String> Result_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> Templete_FLD = new ThreadLocal<String>();
	public static ThreadLocal<String> XMLfilepth = new ThreadLocal<String>();
	public static ThreadLocal<String> URL = new ThreadLocal<String>();
	
	public static int passUC = 0;
	public static int failUC = 0;
	public static int totalUC = 0;
	public static String TestOutput;

	public static ThreadLocal<Boolean> Continue = new ThreadLocal<Boolean>();
	public static ThreadLocal<String> ExecutionStarttimestr = new ThreadLocal<String>();
	public static ThreadLocal<String> Environment = new ThreadLocal<String>();
	public static ThreadLocal<String> UseCaseName = new ThreadLocal<String>();
	public static ThreadLocal<String> UseCaseIDP = new ThreadLocal<String>();
	public static ThreadLocal<String> Dependancy = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseIDP = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseN = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseData = new ThreadLocal<String>();
	public static ThreadLocal<String> ValidationData = new ThreadLocal<String>();
	public static ThreadLocal<String> TestCaseDes = new ThreadLocal<String>();
	public static ThreadLocal<String> currUCstatus = new ThreadLocal<String>();
	public static ThreadLocal<String> currKWstatus = new ThreadLocal<String>();
	public static ThreadLocal<String> currKW = new ThreadLocal<String>();
	public static ThreadLocal<String> currKW_DB = new ThreadLocal<String>();
	public static ThreadLocal<String> currKW_Des = new ThreadLocal<String>();
	public static ThreadLocal<String> browser = new ThreadLocal<String>();
	public static ThreadLocal<String> keywordstartdate = new ThreadLocal<String>();

	public static ThreadLocal<Dictionary> TestData = new ThreadLocal<Dictionary>();
	public static ThreadLocal<Dictionary> ValidateDT = new ThreadLocal<Dictionary>();
	public static ThreadLocal<Dictionary> database = new ThreadLocal<Dictionary>();
	protected static ThreadLocal<WebDriver> cDriver = new ThreadLocal<WebDriver>();
	HashMap<String, Object> RTBOutputData = new HashMap<String, Object>();

	public static void main(String[] args) throws IOException {
		System.out.println("Intialization");
		killexeTask();

		WorkingDir.set(System.getProperty("user.dir").replace("\\", "/"));
		Base_Path.set(WorkingDir.get() + "/Framework");
		Storage_FLD.set(Base_Path.get() + "/Storage");
		OR_File.set(Storage_FLD.get() + "/ObjectRepository.xlsx");
		StoreDB_File.set(Storage_FLD.get() + "/StoreDB.xlsx");
		Database_File.set(Storage_FLD.get() + "/CommonDirectory.xlsx");
		Directory_FLD.set(Base_Path.get() + "/Directory");
		TestDataDB_File.set(Directory_FLD.get() + "/VFQA_TestDesign.xlsm");
		Result_FLD.set(Base_Path.get() + "/Results");
		Templete_FLD.set(Base_Path.get() + "/Templates");
		String Keyword_Result = null;

		File resfold = new File(Result_FLD.get());
		if ((!resfold.exists()))
			resfold.mkdir();

		DateFormat ExecutionStarttime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		ExecutionStarttimestr.set(ExecutionStarttime.format(cal.getTime()).toString());
		System.out.println("Execution initiated at ---> " + ExecutionStarttime.format(cal.getTime()));

		ArrayList<String[]> addUsecase = Utlities.floadUseCases();
		String[] IDP = addUsecase.get(0);
		String[] totUseCases = addUsecase.get(1);
		String[] totTestCases = addUsecase.get(2);
		String[] totTestcase_Des = addUsecase.get(3);
		String[] totUseCases_data= addUsecase.get(4);
		String[] totvalidation_data= addUsecase.get(5);
		totalUC = totUseCases.length;

		for (int currUseCase = 0; currUseCase < totalUC; currUseCase++) {
			String DP = IDP[currUseCase];
			Dependancy.set(DP);
			if(DP.equalsIgnoreCase("IDP")) {
				UseCaseIDP.set(totUseCases[currUseCase]);
				TestCaseIDP.set(totTestCases[currUseCase]);
			}
			UseCaseName.set(totUseCases[currUseCase]);
			TestCaseN.set(totTestCases[currUseCase]);
			TestCaseDes.set(totTestcase_Des[currUseCase]);
			TestCaseData.set(totUseCases_data[currUseCase]);			
			ValidationData.set(totvalidation_data[currUseCase]);
			
			TestOutput = "";
			Result.fCreateReportFiles(currUseCase+1 , UseCaseName.get());
			ArrayList<String[]> addresses = Utlities.floadKeywords(UseCaseName.get());
			String totKeywords[] = addresses.get(0);
			String DataBinding[] = addresses.get(1);
			String Description[] = addresses.get(2);

			System.out.println("No of Keywords to be executed in " + UseCaseName.get() + ":" + totKeywords.length);
			if (DP.equalsIgnoreCase("DP") && currUCstatus.get().equalsIgnoreCase("Fail")) {
				currUCstatus.set("Fail");
				Continue.set(false);
				TestOutput = "******* Interdependant Failure Blocked*******"+ "<br/>";
				Result.fUpdateLog("******* Interdependant Failure Blocked******");
			} else {
				Continue.set(true);
				currUCstatus.set("Pass");
				database.set((Dictionary<?, ?>) Utlities.fdatabase(UseCaseName.get()));
			}
			
			Result.createTCScreenshotFold();

			for (int currKeyword = 0; currKeyword < totKeywords.length; currKeyword++) {
				if (Continue.get() == true) {
					DateFormat currkeywordstartdate = new SimpleDateFormat("dd-MMM-yyyy");
					keywordstartdate.set(currkeywordstartdate.format(cal.getTime()).toString());

					currKW.set(totKeywords[currKeyword]);
					currKW_DB.set(DataBinding[currKeyword]);
					currKW_Des.set(Description[currKeyword]);
					System.out.println("Current Keyword ----> " + currKW.get());
					currKWstatus.set("Pass");
					if (currKW_DB.get().toString().equalsIgnoreCase("Data")) {
						TestData.set((Dictionary<?, ?>) Utlities.freaddata(TestCaseData.get()));
						ValidateDT.set((Dictionary<?, ?>) Utlities.freaddata(ValidationData.get()));
					} else {
						TestData.set((Dictionary<?, ?>) Utlities.freaddata_diff(currKW_DB.get()));
						Environment.set(getdata("Environment"));
					}

					try {
						Class<?> cls = Class.forName("Libraries.KeyWord");
						Object obj = cls.newInstance();
						Method method = cls.getMethod(currKW.get());
						Keyword_Result = (String) method.invoke(obj);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (!Keyword_Result.equals(null)) {
						String[] ResultandDes = Keyword_Result.split("@@");

						if (ResultandDes[0].equalsIgnoreCase("PASS")) {
							currKWstatus.set("Pass");
							Continue.set(true);
						} else {
							currKWstatus.set("Fail");
							Continue.set(false);
						}
						if (TestOutput == null) {
							if (ResultandDes[1] != "") {
								TestOutput = ResultandDes[1];
							}
						} else {
							if (ResultandDes[1] != "") {
								TestOutput = TestOutput + ResultandDes[1];
							}
						}

					}

				}

			}
			if (currKWstatus.get().equalsIgnoreCase("Fail")) {
				failUC = failUC + 1;
				currUCstatus.set("Fail");
			} else {
				passUC = passUC + 1;
			}
			Result.fcreateMasterHTML();
		}
		Result.DisplayHTMLReport();
	}

	public static String getdata(String colname) {
		String c = "";
		try {
			c = TestData.get().get(colname).toString();
			return c;
		} catch (Exception e) {
			return c;
		}

	}
	
	public static String Validatedata(String colname) {
		String c = "";
		try {
			c = ValidateDT.get().get(colname).toString();
			return c;
		} catch (Exception e) {
			return c;
		}

	}

	public static String pulldata(String colname) {
		String c = "";
		try {
			c = database.get().get(colname).toString();
			return c;
		} catch (Exception e) {
			return c;
		}

	}

	public static void killexeTask() {
		try {
			Runtime.getRuntime().exec("taskkill /im chrome.exe /f");
			Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
			Runtime.getRuntime().exec("taskkill /im conhost.exe /f");
			Runtime.getRuntime().exec("taskkill /im geckodriver.exe /f");
			Runtime.getRuntime().exec("taskkill /im IEDriverServer.exe /f");
			Runtime.getRuntime().exec("taskkill /im iexplore.exe /f");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
