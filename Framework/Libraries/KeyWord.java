package Libraries;

public class KeyWord {

	Keyword_CRM KC = new Keyword_CRM();
	Keyword_API KA = new Keyword_API();
	Keyword_SIPT KS = new Keyword_SIPT();
	Keyword_Validations KV = new Keyword_Validations();

	// ------------------Keyword CRM -------------------//
	public String Siebel_Login() {
		return KC.Siebel_Login();
	}

	public String Siebel_Logout() {
		return KC.Siebel_Logout();
	}

	public String ContactCreation() {
		return KC.ContactCreation();
	}

	public String AccountCreation() {
		return KC.AccountCreation();
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

	public String OrderSubmission() {
		return KC.OrderSubmission();
	}

	public String Entp_AccountCreation() {
		return KC.Entp_AccountCreation();
	}

	public String Entp_ContactCreation() {
		return KC.Entp_ContactCreation();
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

	public String UnBarring() {
		return KC.UnBarring();
	}

	public String Barring() {
		return KC.Barring();
	}

	public String SIMSwap() {
		return KC.SIMSwap();
	}

	public String Change_MSISDN() {
		return KC.Change_MSISDN();
	}

	public String Consumer_Migration() {
		return KC.Consumer_Migration();
	}

	public String Enterprise_Migration() {
		return KC.Enterprise_Migration();
	}

	public String RealTimeBalance_Screen() {
		return KC.RealTimeBalance_Screen();
	}

	// ------------------Keyword CRM -------------------//

	// ---------------------Keyword API------------------------//

	public String RTB_Login() {
		return KA.RTB_Login();
	}

	public String RTB() {
		return KA.RTB();
	}

	// ---------------------Keyword API------------------------//

	// ---------------------Keyword Validation------------------------//

	public String RTB_Validation() {
		return KV.RTB_Validation();
	}

	// ---------------------Keyword Validation------------------------//

	// ---------------------Keyword SIPT------------------------//

	public String SIPT() {
		return KS.SIPT();
	}

	// ---------------------Keyword SIPT------------------------//

}
