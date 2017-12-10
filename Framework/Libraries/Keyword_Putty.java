package Libraries;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Keyword_Putty extends Driver {
	// private static ChannelShell channel;

	public Keyword_Putty() {
	}

	public String LoginSSH() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------BRM Putty Login Event Details------");
		String str_Host = getdata("URL/HOST");
		try {
			int int_SSHPort = 22;
			String str_Username = getdata("VQ_Login_User");
			String str_Password = getdata("VQ_Login_Pswd");

			JSch obj_JSch = new JSch();
			nsession.set(obj_JSch.getSession(str_Username, str_Host));
			nsession.get().setPort(int_SSHPort);
			nsession.get().setPassword(str_Password);

			Properties obj_Properties = new Properties();
			obj_Properties.put("StrictHostKeyChecking", "no");
			obj_Properties.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
			nsession.get().setConfig(obj_Properties);

			nsession.get().connect();
			Result.fUpdateLog("Session Connected to: " + str_Host);
			Test_OutPut += "Session Connected to: " + str_Host + ",";
			Status = "PASS";
		} catch (Exception e) {
			Test_OutPut += "Session Connection Failed at Host: " + str_Host + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------BRM Putty Login Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String LogoutSSH() {

		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------BRM Putty Logout Event Details------");
		try {
			tergetFile.get().close();
			nsession.get().disconnect();
			Result.fUpdateLog("Session disconnected successfully");
			Test_OutPut += "Session disconnected successfully" + ",";
			Status = "PASS";
		} catch (Exception e) {
			Test_OutPut += "Failed to disconnect session" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------BRM Putty logout Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String BillGeneration_AccountLevel() {
		String Test_OutPut = "", Status = "";
		Result.fUpdateLog("------Bill Generation Event Details------");
		String pvt = "", str_Content = "";
		try {
			String str_Directory = pulldata("str_Directory");
			String str_File = pulldata("str_File");
			String str_FileContent = "";

			if (!(getdata("AccountNo").equals(""))) {
				str_Content = getdata("AccountNo");
			} else if (!(getdata("MultipleAccountNo").equals(""))) {
				str_Content = getdata("MultipleAccountNo").replace("/,", "\\n");
			}

			str_FileContent = ReadFileFromLinux(nsession.get(), str_Directory, str_File);
			Result.fUpdateLog(str_FileContent);

			str_FileContent = WriteFileToLinux(nsession.get(), str_Content, str_Directory, str_File);
			Result.fUpdateLog(str_FileContent);

			str_FileContent = ReadFileFromLinux(nsession.get(), str_Directory, str_File);
			Result.fUpdateLog(str_FileContent);
			Test_OutPut += str_FileContent + ",";

			if (!(getdata("PVT_Date").equals(""))) {
				// "pvt -m2 030110002018"
				pvt = "pvt -m2 " + getdata("PVT_Date");
			} else {
				pvt = "pvt -m2 " + pulldata("PVT_Date");
			}

			List<String> commands = new ArrayList<String>();
			commands.add("test");
			commands.add("cd control_file_gen");
			commands.add("./bill_control_gen.pl");
			commands.add("cp PinBillRunControl.xml $PIN_HOME/apps/pin_billd");

			commands.add("apps");
			commands.add("cd pin_billd");
			commands.add("cat PinBillRunControl.xml");
			commands.add("pvt");
			commands.add(pvt);
			commands.add("pvt");
			commands.add("pin_deferred_act –verbose");
			commands.add("pin_cycle_fees -cycle_fees -verbose");
			// commands.add("pin_cycle_fees -purchase -verbose");
			commands.add("pin_cycle_fees -cancel -verbose");
			commands.add("pin_bill_accts -file PinBillRunControl.xml -verbose");
			commands.add("pvt -m0");
			commands.add("pvt");

			// commands.add("history");

			str_FileContent = Executecmd(nsession.get(), commands, "");
			CopytoDoc(str_FileContent);

			if (str_FileContent.contains("logout")) {
				Test_OutPut += "Commands Executed Successfully" + ",";
				Result.fUpdateLog(str_FileContent);
				Status = "PASS";
			} else {
				Test_OutPut += "Failed to Execute the commands" + ",";
				Result.fUpdateLog(str_FileContent);
				Status = "Fail";
			}

		} catch (Exception e) {
			Test_OutPut += "Failed to disconnect session" + ",";
			Result.fUpdateLog("Exception occurred *** " + e.getMessage());
			Status = "FAIL";
			e.printStackTrace();
		}
		Result.fUpdateLog("------Bill Generation Event Details - Completed------");
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String ReadFileFromLinux(Session obj_Session, String str_FileDirectory, String str_FileName) {
		StringBuilder obj_StringBuilder = new StringBuilder();
		try {
			Channel obj_Channel = obj_Session.openChannel("sftp");
			obj_Channel.connect();
			ChannelSftp obj_SFTPChannel = (ChannelSftp) obj_Channel;
			obj_SFTPChannel.cd(str_FileDirectory);
			InputStream obj_InputStream = obj_SFTPChannel.get(str_FileName);
			char[] ch_Buffer = new char[0x10000];
			Reader obj_Reader = new InputStreamReader(obj_InputStream, "UTF-8");
			int int_Line = 0;
			do {
				int_Line = obj_Reader.read(ch_Buffer, 0, ch_Buffer.length);
				if (int_Line > 0) {
					obj_StringBuilder.append(ch_Buffer, 0, int_Line);
				}
			} while (int_Line >= 0);
			obj_Reader.close();
			obj_InputStream.close();

			obj_SFTPChannel.exit();
			obj_Channel.disconnect();
			return str_FileName + " file Contains : " + obj_StringBuilder.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Fail to Read the file : " + str_FileName;
		}

	}

	public String WriteFileToLinux(Session obj_Session, String str_Content, String str_FileDirectory,
			String str_FileName) {

		try {
			Channel obj_Channel = obj_Session.openChannel("sftp");
			obj_Channel.connect();
			ChannelSftp obj_SFTPChannel = (ChannelSftp) obj_Channel;
			obj_SFTPChannel.cd(str_FileDirectory);
			InputStream obj_InputStream = new ByteArrayInputStream(str_Content.getBytes());
			obj_SFTPChannel.put(obj_InputStream, str_FileDirectory + str_FileName);
			obj_SFTPChannel.exit();
			obj_InputStream.close();
			obj_Channel.disconnect();
			return str_FileName + " File Uploaded Successfully : " + str_Content;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Fail to Uploaded : " + str_FileName;
		}
	}

	private Channel getChannel(Session obj_Session) {
		if (nchannel.get() == null || !nchannel.get().isConnected()) {
			try {
				nchannel.set((ChannelShell) obj_Session.openChannel("shell"));
				nchannel.get().connect();
				System.out.println("channel opened!");
			} catch (Exception e) {
				System.out.println("Error while opening channel: " + e);
			}
		}
		return nchannel.get();
	}

	public String Executecmd(Session obj_Session, List<String> commands, String Exitval) {
		String str = "";
		try {
			String str1 = "";
			Channel channel = getChannel(obj_Session);

			PrintStream out = new PrintStream(channel.getOutputStream());
			// out.println("#!/bin/bash");
			for (String command : commands) {
				out.println(command);
				Result.fUpdateLog(command);
			}
			out.println("exit");
			out.close();
			out.flush();

			InputStream in = channel.getInputStream();
			byte[] buffer = new byte[1024];

			while (true) {
				while (in.available() > 0) {
					int i = in.read(buffer, 0, 1024);
					if (i < 0) {
						break;
					}
					str1 = new String(buffer, 0, i);
					str = str + str1;
				}

				if (str1.contains("logout")) {
					break;
				}
				if (channel.isClosed()) {
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}

			in.close();
			channel.disconnect();
			Result.fUpdateLog("DONE");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public void CopytoDoc(String Data) {

		try {
			// String lf = "OBJECT_FILE";
			String lf = TCscreenfile.get();
			tergetFile.set(new FileOutputStream(lf));

			XWPFDocument document = new XWPFDocument();

			// create Paragraph
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();

			run.setText(Data);
			document.write(tergetFile.get());
			document.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
