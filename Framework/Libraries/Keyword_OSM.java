package Libraries;

public class Keyword_OSM extends Driver{
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
				browser.set("Chrome");
			}

			Browser.OpenBrowser(browser.get(), getdata("URL"));

			Result.fUpdateLog("Browser Opened Successfully");
			Result.takescreenshot("Opening Browser and navigating to the URL");
			Browser.WebLink.waittillvisible("OSM_Link");
			Browser.WebLink.click("OSM_Link");
			Browser.WebEdit.Set("OSM_Login_User", getdata("OSM_Login_User"));
			Browser.WebEdit.Set("OSM_Login_Pswd", getdata("OSM_Login_Pswd"));
			Browser.WebLink.click("OSM_Submit");
			Browser.WebLink.waittillvisible("OSM_Submit");
			Browser.WebLink.click("OSM_Submit");
			

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
}
