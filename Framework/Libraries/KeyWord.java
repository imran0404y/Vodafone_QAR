package Libraries;

public class KeyWord {

	Keyword_CRM KC = new Keyword_CRM();
	Keyword_API KA = new Keyword_API();

	// ------------------Keyword CRM -------------------//
	public String Siebel_Login() {
		return KC.Siebel_Login();
	}

	public String ContactCreation() {
		return KC.ContactCreation();
	}

	public String AccountCreation() {
		return KC.AccountCreation();
	}

	public String Entp_AccountCreation() {
		return KC.Entp_AccountCreation();	
	}

	public String Entp_ContactCreation() {
		return KC.Entp_ContactCreation();
	}

	public String BillingProfileCreation() {
		return KC.BillingProfileCreation();
	}

	public String SalesOrder() {
		return KC.SalesOrder();
	}

	public String PlanSelection() {
		return KC.PlanSelection();
	}

	/*public String Customize() {
		return KC.Customize();
	}

	public String Validate() {
		return KC.Validate();
	}*/

	public String OrderSubmission() {
		return KC.OrderSubmission();
	}

	public String Siebel_Logout() {
		return KC.Siebel_Logout();
	}
	
	public String Modify() {
		return KC.Modify();
	}
	
	public String Suspension() {
		return KC.Suspension();
	}
	
	public String Resume() {
		return KC.Resume();
	}
	
	public String Plan_UpgradeDowngrade() {
		return KC.Plan_UpgradeDowngrade();
	}
	
	public String Change_PrimaryNumber() {
		return KC.Change_PrimaryNumber();
	}
	
	public String Disconnect() {
		return KC.Disconnect();
	}
	
	public String SIMSwap() {
		return KC.SIMSwap();
	}
	
	// ------------------Keyword CRM -------------------//
	
	// ---------------------Keyword API------------------------//
	
	public String RTB_Login() {
		return KA.RTB_Login();
	}
	
	public String RTBValidation() {
		return KA.RTBValidation();
	}
	
	// ---------------------Keyword API------------------------//
}
