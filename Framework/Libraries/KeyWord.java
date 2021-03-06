package Libraries;

public class KeyWord {

	Keyword_CRM KC = new Keyword_CRM();
	Keyword_API KA = new Keyword_API();
	Keyword_SIPT KS = new Keyword_SIPT();
	Keyword_Validations KV = new Keyword_Validations();
	Keyword_Putty KP = new Keyword_Putty();
	Keyword_Guided KG = new Keyword_Guided();

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

	public String Plan_UpgradeDowngrade() {
		return KC.Plan_UpgradeDowngrade();
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
	
	public String Change_SmartLimit() {
		return KC.Change_SmartLimit();
	}
	
	public String BillPayment() {
		return KC.BillPayment();
	}

	public String Disconnection() {
		return KC.Disconnection();
	}

	public String TroubleTicket() {
		return KC.TroubleTicket();
	}
	
	public String Discounts() {
		return KC.Discounts();
	}
	
	public String TransferOfService() {
		return KC.TransferOfService();
	}
	
	// ------------------Keyword CRM -------------------//
	
	// ------------------Keyword Guided -------------------//

	public String SIMSwap_Guided_Journey() {
		return KG.SIMSwap_Guided_Journey();
	}
	
	public String ConsumerPostpaid_GuidedJourney() {
		return KG.ConsumerPostpaid_GuidedJourney();
	}
	
	public String SIMSwap_Global_Search() {
		return KG.SIMSwap_Global_Search();
	}
	// ------------------Keyword Guided -------------------//

	// ---------------------Keyword API------------------------//

	public String RTB_Login() {
		return KA.RTB_Login();
	}

	public String RTB() {
		return KA.RTB();
	}

	public String RTB_Check() {
		return KA.RTB_Check();
	}
	
	public String RTB_Compare() {
		return KA.RTB_Compare();
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

	// ---------------------Keyword Putty------------------------//

		public String LoginSSH() {
			return KP.LoginSSH();
		}

		public String LogoutSSH() {
			return KP.LogoutSSH();
		}
		
		public String BillGeneration_AccountLevel() {
			return KP.BillGeneration_AccountLevel();
		}
	// ---------------------Keyword Putty------------------------//
}
