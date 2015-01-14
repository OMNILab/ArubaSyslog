package cn.edu.sjtu.omnilab.sysloganonymizer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyslogPreprocess {
    
    private static final int[] errorCodeList = {124003, 132030, 132053,
            132197, 202086, 404400, 500010, 501044, 501080, 501093, 501095,
            501098, 501099, 501100, 501102, 501105, 501106, 501107, 501108,
            501109, 501111, 501114, 522005, 522006, 522008, 522010, 522026,
            522029, 522030, 522035, 522036, 522038, 522042, 522044, 522049,
            522050};

//	private static final int[] errorCodeList = {404400, 522008, 522026, 522038, 522042};

    private static final Map<String, String> APName = new TreeMap<String, String>();

    public SyslogPreprocess() {
        
		/*Initialize the map between real AP names and the names after anonymous.*/
        APName.put("DYST", "CantBldg01");
        APName.put("DEST", "CantBldg02");
        APName.put("D3ST", "CantBldg03");
        APName.put("DSST", "CantBldg04");
        APName.put("DWST", "CantBldg05");
        APName.put("LiuYuan", "CantBldg06");
        APName.put("HLGC", "CantBldg07");

        APName.put("JJT", "SocBldg01");
        APName.put("CJMSG", "SocBldg02");
        APName.put("XSSWZX-TSG", "SocBldg03");
        APName.put("GBL", "SocBldg04");
        APName.put("YFKJL", "SocBldg05");
        APName.put("XSFWZX", "SocBldg06");
        APName.put("BBY", "SocBldg07");

        APName.put("WLXXZX", "AdmBldg01");
        APName.put("CRQ-N", "AdmBldg02");
        APName.put("CRQ-S", "AdmBldg03");
        APName.put("LXZL", "AdmBldg04");
        APName.put("XXZL-B", "AdmBldg05");
        APName.put("XXZL-A", "AdmBldg06");
        APName.put("LWLZX", "AdmBldg07");

        APName.put("XNTYG", "AthBldg01");
        APName.put("XTYG-CYL", "AthBldg02");

        APName.put("XYY-N", "HospBldg01");
        APName.put("XYY-S", "HospBldg02");

        APName.put("TSG-1", "LibBldg01");
        APName.put("TSG-2", "LibBldg02");
        APName.put("TSG-3", "LibBldg03");
        APName.put("TSG-4", "LibBldg04");
        APName.put("BYGTSG", "LibBldg05");

        APName.put("JJC", "SuppBldg01");
        APName.put("DHZJF", "SuppBldg02");
        APName.put("MHHCS", "SuppBldg03");

        APName.put("XSY", "TeachBldg01");
        APName.put("XZY", "TeachBldg02");
        APName.put("XXY", "TeachBldg03");
        APName.put("DSY", "TeachBldg04");
        APName.put("DZY-1", "TeachBldg05");
        APName.put("DZY-2", "TeachBldg06");
        APName.put("DZY-3", "TeachBldg07");
        APName.put("DZY-4", "TeachBldg08");
        APName.put("DXY", "TeachBldg09");

        APName.put("XH-XJL", "TeachBldg01");
        APName.put("XH-XYY", "TeachBldg02");
        APName.put("XH-XBL", "TeachBldg03");
        APName.put("XH-FXL", "TeachBldg04");
        APName.put("XH-ZY", "TeachBldg05");
        APName.put("XH-HRDS", "TeachBldg06");
        APName.put("XH-JGHDZX", "TeachBldg07");
        APName.put("XH-XZY", "TeachBldg08");


        APName.put("CL-A", "AcadBldg01");
        APName.put("CL-B", "AcadBldg02");
        APName.put("CL-C", "AcadBldg03");
        APName.put("CL-D", "AcadBldg04");
        APName.put("CL-E", "AcadBldg05");
        APName.put("CL-F", "AcadBldg06");
        APName.put("SXL", "AcadBldg07");
        APName.put("WLL-Z", "AcadBldg08");
        APName.put("WLSYL", "AcadBldg09");

        APName.put("GCLXSYZX", "AcadBldg10");
        APName.put("STHJCLYJS", "AcadBldg11");
        APName.put("GCXLZX-SYL", "AcadBldg12");
        APName.put("HJKXL", "AcadBldg13");
        APName.put("HX-A", "AcadBldg14");
        APName.put("WXYXL", "AcadBldg15");
        APName.put("YXL-1", "AcadBldg16");
        APName.put("YXL-2", "AcadBldg17");
        APName.put("YXL-3", "AcadBldg18");
        APName.put("YXL-4", "AcadBldg19");

        APName.put("YXL-5", "AcadBldg20");
        APName.put("YXL-6", "AcadBldg21");
        APName.put("YXL-7", "AcadBldg22");
        APName.put("FXCSZX-1", "AcadBldg23");
        APName.put("FXCSZX-2", "AcadBldg24");
        APName.put("FXCSZX-3", "AcadBldg25");
        APName.put("JXDLXY-A-Z", "AcadBldg26");
        APName.put("JXDLXY-A-F", "AcadBldg27");
        APName.put("JXDLXY-B-N", "AcadBldg28");
        APName.put("JXDLXY-B-E", "AcadBldg29");

        APName.put("JXDLXY-B-E", "AcadBldg30");
        APName.put("JXDLXY-B-S", "AcadBldg31");
        APName.put("WDZXY", "AcadBldg32");
        APName.put("NXSWXY-F1", "AcadBldg33");
        APName.put("NXSWXY-F2", "AcadBldg34");
        APName.put("NXSWXY-F3", "AcadBldg35");
        APName.put("XTSWYJY-A", "AcadBldg36");
        APName.put("XTSWYJY-B", "AcadBldg37");
        APName.put("XTSWYJY-C", "AcadBldg38");
        APName.put("XTSWYJY-D", "AcadBldg39");

        APName.put("MLXY-S", "AcadBldg40");
        APName.put("MLXY-N", "AcadBldg41");
        APName.put("KYFXY", "AcadBldg42");
        APName.put("MTSJSYL-A", "AcadBldg43");
        APName.put("MTSJSYL-B", "AcadBldg44");
        APName.put("WYL", "AcadBldg45");
        APName.put("RWL", "AcadBldg46");
        APName.put("DXQL-1", "AcadBldg47");
        APName.put("DXQL-2", "AcadBldg48");
        APName.put("DXQL-3", "AcadBldg49");

        APName.put("DXQL-4-N", "AcadBldg50");
        APName.put("DXQL-4_S", "AcadBldg51");
        APName.put("DXQL-5", "AcadBldg52");
        APName.put("DXQL-LianLang", "AcadBldg53");
        APName.put("GCXLZX-B", "AcadBldg54");
        APName.put("LLDCYJS", "AcadBldg55");
        APName.put("JZG", "AcadBldg56");
        APName.put("ZYXY", "AcadBldg57");
        APName.put("ZYNYL", "AcadBldg58");
    }

    public static String Filter(String str) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("<[0-9]{6}>");
        matcher = pattern.matcher(str);

        if (!matcher.find()) return null;

        String tmp = matcher.group();
        int errorCode = Integer.parseInt(tmp.substring(1, 7));

        for (int num : errorCodeList) {
            if (errorCode == num) return str;
        }
        return null;
    }

    public static String APNameAnonymous(String str, ArrayList<String> apName) {

        if (str == null) return null;

        StringBuffer apModified = new StringBuffer();
        String[] apElseArray;
        int counter = 0;
        String result = null;
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("([A-Z0-9]+-){1,4}\\dF-\\d{2}");
        matcher = pattern.matcher(str);
        apElseArray = str.split("([A-Z0-9]+-){1,4}\\dF-\\d{2}");

        while (matcher.find()) {
            String temp = matcher.group();
            String floor = temp.split("-")[temp.split("-").length - 2];
            String[] tempArray = temp.split("-[0-9]F");

            if (APName.containsKey(tempArray[0])) tempArray[0] = APName.get(tempArray[0]);
            else apName.add(tempArray[0]);
            if (tempArray.length == 2) result = tempArray[0] + "-" + floor + tempArray[1];
            apModified.append(apElseArray[counter] + result);
            counter++;
        }
        if (counter == apElseArray.length - 1) apModified.append(apElseArray[counter]);
        return apModified.toString();
    }

    public static String APIPDeletion(String str) {

        if (str == null) return null;

        StringBuffer result = new StringBuffer();
        String ipv6 = "IP=(.)*:[0-9a-f]{1,4}";
        String ipv4At = "@([0-9]{1,3}\\.){3}[0-9]{1,3}";
        String ipv4Else = "\\s([0-9]{1,3}\\.){3}[0-9]{1,3}";


        String[] tempArray = str.split(ipv6);
        for (int i = 0; i < tempArray.length; i++) result.append(tempArray[i]);
        String tmp = result.toString();
        result = new StringBuffer();

        tempArray = tmp.split(ipv4At);
        for (int i = 0; i < tempArray.length; i++) result.append(tempArray[i]);
        tmp = result.toString();
        result = new StringBuffer();

        tempArray = tmp.split(ipv4Else);
        for (int i = 0; i < tempArray.length; i++) result.append(tempArray[i]);
        tmp = result.toString();

        return tmp;
    }

    public static String MacAnonymous(String str) {

        if (str == null) return null;

        StringBuffer macModified = new StringBuffer();
        String[] macElseArray;
        int counter = 0;
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("([0-9a-f]{2}:){5}[0-9a-f]{2}");
        matcher = pattern.matcher(str);
        macElseArray = str.split("([0-9a-f]{2}:){5}[0-9a-f]{2}");

        while (matcher.find()) {
            String temp = matcher.group();
            String[] tempArray = temp.split(":");
			
			/*Cyclic shift of each two-digit like "08"*/
            for (int i = 0; i < tempArray.length; i++) {
                int num = Integer.parseInt(tempArray[i], 16);

                if (i % 2 == 0) num = (num << i | num >>> (8 - i)) & 0xFF;
                else num = (num >>> i | num << (8 - i)) & 0xFF;

                if (num < 10) tempArray[i] = "0" + num;
                else tempArray[i] = Integer.toHexString(num);
            }
			
			/*Cyclic shift of three two-digit like "08:ed:fc" and ensure half length of MAC address is 6*/
            int num1 = Integer.parseInt(tempArray[0] + tempArray[1] + tempArray[2], 16);
            num1 = (num1 << 11 | num1 >>> 13) & 0xFFFFFF;
            String macStr1 = Integer.toHexString(num1);
            while (macStr1.length() != 6) macStr1 = "0" + macStr1;

            int num2 = Integer.parseInt(tempArray[3] + tempArray[4] + tempArray[5], 16);
            num2 = (num2 >>> 11 | num2 << 13) & 0xFFFFFF;
            String macStr2 = Integer.toHexString(num2);
            while (macStr2.length() != 6) macStr2 = "0" + macStr2;
			
			/* Transform to be a MAC-address-like one.*/
            String result = macStr1.substring(0, 2) + ":" + macStr1.substring(2, 4) + ":" + macStr1.substring(4, 6) + ":" + macStr2.substring(0, 2) + ":"
                    + macStr2.substring(2, 4) + ":" + macStr2.substring(4, 6);
			
			/*Append the processed part to the final StringBuffer.*/
            macModified.append(macElseArray[counter] + result);
            counter++;
        }
        if (counter == macElseArray.length - 1) macModified.append(macElseArray[counter]);
        return macModified.toString();
    }


    public static String IPAnonymous(String str) {

        if (str == null) return null;

        StringBuffer ipModified = new StringBuffer();
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("IP=([0-9]{1,3}\\.){3}[0-9]{1,3}");
        matcher = pattern.matcher(str);
		
		/*User IP can only show once in one line.*/
        if (matcher.find()) {
            String temp = matcher.group();
            String[] tempArray = temp.split("\\.");

            int num1 = Integer.parseInt(tempArray[2]);
            num1 = (num1 << 2 | num1 >>> 6) & 0xFF;
            int num2 = Integer.parseInt(tempArray[3]);
            num2 = (num2 >>> 5 | num2 << 3) & 0xFF;

			/*Cyclic shift of 2 cells like "10.150"*/
            int num = num1 * 256 + num2;
            num = (num << 7 | num >>> 9) & 0xFFFF;
            String ipStr = Integer.toHexString(num);
            while (ipStr.length() != 4) ipStr = "0" + ipStr;

            String result = "10.50." + Integer.parseInt(ipStr.substring(0, 2), 16) + "." + Integer.parseInt(ipStr.substring(2, 4), 16);

			/*Append the processed part to the final StringBuffer.*/
            ipModified.append(matcher.replaceAll("IP=" + result));
            return ipModified.toString();
        }
        return str;
    }

    public static String UserNameAnonymous(String str) throws NoSuchAlgorithmException {

        if (str == null) return null;

        StringBuffer nameModified = new StringBuffer();
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("username=[a-zA-Z0-9-_.]+");
        matcher = pattern.matcher(str);
		
		/*User name can only show once in one line.*/
        if (matcher.find()) {
            String temp = matcher.group();
            MessageDigest sha1 = MessageDigest.getInstance("sha-1");
            byte[] value = sha1.digest((temp.substring(9) + "UserNameAnonymous").getBytes());
            String result = Integer.toHexString(value[0] & 0xFF);

            if (value.length >= 8) {
                for (int i = 1; i < 8; i++) result += Integer.toHexString(value[i] & 0xFF);
            } else {
                for (int i = 1; i < value.length; i++) result += Integer.toHexString(value[i] & 0xFF);
            }
            nameModified.append(matcher.replaceAll("username=" + result));
            return (nameModified.toString());
        }
        return str;
    }
}
