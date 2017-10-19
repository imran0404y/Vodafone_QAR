package Libraries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Result extends Driver {

	public static ThreadLocal<String> logfilepth = new ThreadLocal<String>();
	public static ThreadLocal<String> masterrephtml = new ThreadLocal<String>();
	public static ThreadLocal<String> UCscreenfilepth = new ThreadLocal<String>();
	public static ThreadLocal<String> UC = new ThreadLocal<String>();
	public static ThreadLocal<String> TCscreenfile = new ThreadLocal<String>();
	public static String updatelogmsg = "";

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: fCreateReportFiles
	 * Arguments			: current usecase
	 * Use 					: to create all the report files
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void fCreateReportFiles(int SNo, String Usecase) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		try {

			File resfold = new File(Result_FLD.get() + "/" + dateFormat.format(cal.getTime()));
			if ((!resfold.exists()))
				resfold.mkdir();

			String timefold = ExecutionStarttimestr.get().replace(":", "-").replace(" ", "_");
			File tresfold = new File(resfold + "/" + timefold);
			if ((!tresfold.exists()))
				tresfold.mkdir();

			UC.set(SNo + Usecase);
			UCscreenfilepth.set(tresfold + "/" + UC.get());
			File bthresfold = new File(tresfold + "/" + UC.get());
			if ((!bthresfold.exists()))
				bthresfold.mkdir();

			XMLfilepth.set(UCscreenfilepth.get() + "/XML");
			File XMLfilepth = new File(UCscreenfilepth.get() + "/XML");
			if ((!XMLfilepth.exists()))
				XMLfilepth.mkdir();

			File scriptfold = new File(tresfold + "/Scripts");
			if ((!scriptfold.exists()))
				scriptfold.mkdir();

			String tempref = Templete_FLD.get();
			File scriptrepsource = new File(tempref + "/Scripts");

			FileUtils.copyDirectory(scriptrepsource, scriptfold);

			logfilepth.set(bthresfold + "/Logs.txt");

			File logfile = new File(logfilepth.get());
			if (!logfile.exists()) {
				logfile.createNewFile();
			}

			// File masterhtml = new File(tempref + "/MasterReport.HTML");
			// FileUtils.copyFileToDirectory(masterhtml, tresfold);
			masterrephtml.set(tresfold.toString() + "\\MasterReport.HTML");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: fUpdateLog
	 * Arguments			: logmessage
	 * Use 					: to update the log for each case
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void fUpdateLog(String logmessage) {
		File logfile = new File(logfilepth.get());
		FileWriter fw;
		try {
			fw = new FileWriter(logfile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(logmessage);
			bw.write(logmessage + "\r\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: Takescreenshot
	 * Arguments			: screenshot name
	 * Use 					: Take screenshot and save it in screen shots folder
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void takescreenshot(String LogMessage) {
		try {
			File scrFile = ((TakesScreenshot) cDriver.get()).getScreenshotAs(OutputType.FILE);

			CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File(TCscreenfile.get())));
			FileOutputStream fos = new FileOutputStream(new File(TCscreenfile.get()));
			String id = document.addPictureData(new FileInputStream(new File(scrFile.toString())),
					Document.PICTURE_TYPE_PNG);
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setText(LogMessage);
			document.createPicture(id, document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 640, 360);
			document.write(fos);
			fos.flush();
			fos.close();
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: fcreateMasterHTML
	 * Arguments			: None
	 * Use 					: Create HTML Master report
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void fcreateMasterHTML() throws IOException {
		File logfile = new File(masterrephtml.get());
		FileWriter fw = new FileWriter(logfile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String logmessage = "<!-- saved from url=(0016)http://localhost -->" + "<html>" + "<head>"
				+ "<title>Execution Results</title>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
				+ "<link rel=\"stylesheet\" href=\"" + "Scripts\\style.css\" type=\"text/css\">" + "<script src=\""
				+ "Scripts\\amcharts.js\" type=\"text/javascript\"></script>" + "<style>" + "table {font-size: 12px;"
				+ "background:#E6E6E6;" + "}" + "</style>" + "<script>" + "var chart;" + "var chartData = [{"
				+ "Status: \"Pass\"," + "Count:" + passUC + "}, {" + "Status: \"Fail\"," + "Count:" + failUC + "}];"
				+ "AmCharts.ready(function () {" + "chart = new AmCharts.AmPieChart();" +
				// "chart.addTitle(\"Execution Status\", 16);"+
				"chart.dataProvider = chartData;" + "chart.titleField = \"Status\";" + "chart.valueField = \"Count\";"
				+ "chart.sequencedAnimation = true;" + "chart.startEffect = \"elastic\";"
				+ "chart.innerRadius = \"30%\";" + "chart.startDuration = 2;" + "chart.labelRadius = 5;"
				+ "chart.depth3D = 12;" + "chart.angle = 20;" + "chart.write(\"chartdiv\");" + "});" + "</script>" +
				// "</script>"+
				"</head>" + "<body bgcolor = \"green\">" + "<div id = \"lastres\">" + "<table width='100%' border=2>"
				+ "<tr>" + "<td border='0'>" + "<img src ='" + "Scripts\\"
				+ "Client-logo.jpg' height = 30% width = 100%>" + "</td>" + "<td width =70% Style=\"color:green\">"
				+ "<center><h1> Master Report </h1>  </center>" + "</td>" + "<td border='0'>" + "<img src ='"
				+ "Scripts\\" + "maveric-logo.jpg' height = 30% width = 100%>" + "</td>" + "</tr>"
				+ "<table width='100%' border=2>" + "<tr>"
				+ "<td align=\"center\" width='50%' colspan=2><h3>Execution overview </h3></td>"
				+ "<td align=\"center\" width='50%' colspan=2><h3>Execution status </h3></td>" + "</tr>" + "<tr>"
				+ "<td width='50%' align=\"center\" colspan = 2><div id=\"chartdiv\" style=\"width:450px; height:150px;\"></div></td>"
				+ "<td valign ='top'>" + "<table border =1 width = 100%>" + "<tr>"
				+ "<td align=\"center\"><b>Total</b></td>" + "<td align=\"center\"><b>Pass</b></td>"
				+ "<td align=\"center\"><b>Fail</b></td>" + "</tr>" + "<tr>" + "<td align=\"center\" id=\"tot\">"
				+ totalUC + "</td>" + "<td align=\"center\" id=\"totpass\">" + passUC + "</td>"
				+ "<td align=\"center\" id=\"totfail\">" + failUC + "</td>" + "</tr>" + "</table>" + "</tr>"
				+ "</table>" + "<table width='100%' border=2>" + "<tr>"
				+ "<td align=\"center\" width='50%' colspan=3 Style=\"color:blue\"><h3> Detail Summary Report </h3></td>"
				+ "</tr>" + "<table border =1 width = 100%>" + "<tr>"
				+ "<td width = 8%><b><center>Environment</center></b></td>"
				+ "<td width = 12%><b><center>Usecase</center></b></td>"
				+ "<td width = 10%><b><center>TestCase</center></b></td>"
				+ "<td width = 14%><b><center>Testcase ID/Description</center></b></td>"
				+ "<td width = 22%><b><center>UserInputs</center></b></td>"
				+ "<td width = 28%><b><center>TestCase Output</center></b></td>"
				+ "<td width = 6%><b><center>Status</center></b></td>" + "</tr>";
		bw.write(logmessage + "\r\n");
		logmessage = "";
		updatelogmsg = updatelogmsg + "<tr>" + "<td width = 8%><center>" + Environment.get() + "</center></td>";
		updatelogmsg = updatelogmsg + "<td width = 12%><center>" + UC.get() + "</center></td>";
		updatelogmsg = updatelogmsg + "<td width = 10%><center><a href = .\\" + UC.get() + "\\" + TestCaseN.get()
				+ ".docx" + ">" + TestCaseN.get() + "</a></center></td>";
		updatelogmsg = updatelogmsg + "<td width = 14%>" + TestCaseDes.get() + "</td>";
		updatelogmsg = updatelogmsg + "<td width = 22%>" + TestCaseData.get() + "</td>";
		if (currUCstatus.get().equals("Pass")) {
			updatelogmsg = updatelogmsg + "<td width = 28%>" + TestOutput + "</td>";
			updatelogmsg = updatelogmsg + "<td width = 6% Style=\"color:green\"><b><center>Pass</center></b></td></tr>";
		} else if (currUCstatus.get().equals("Fail")) {
			updatelogmsg = updatelogmsg + "<td width = 28%>" + TestOutput + "\n" + "Failed at " + currKW_Des.get()
					+ "</td>";
			updatelogmsg = updatelogmsg
					+ "<td width = 6% Style=\\\"color:Red\\\"><b><center>Fail</center></b></td></tr>";
		}

		bw.write(updatelogmsg);
		bw.close();
	}

	/*----------------------------------------------------------------------------------------------------
	 * Method Name			: createTCScreenshotFold
	 * Arguments			: None
	 * Use 					: to create the screenshot folder
	 * Designed By			: Imran Baig
	 * Last Modified Date 	: 23-Aug-2017
	--------------------------------------------------------------------------------------------------------*/
	public static void createTCScreenshotFold() {
		File tcscreenfold = new File(UCscreenfilepth.get() + "/" + TestCaseN.get() + ".docx");
		TCscreenfile.set(tcscreenfold.toString());
		try {
			@SuppressWarnings("resource")
			XWPFDocument document = new XWPFDocument();
			// Write the Document in file system
			FileOutputStream out = new FileOutputStream(new File(TCscreenfile.get()));
			document.write(out);
			out.close();
		} catch (Exception e) {

		}
	}

	public static void DisplayHTMLReport() {
		System.setProperty("webdriver.chrome.driver", WorkingDir.get() + "\\chromedriver.exe");
		String url = masterrephtml.get();
		System.out.println(url);
		try {
			// killexeTask();
			WebDriver driver = new ChromeDriver();
			driver.get(url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
