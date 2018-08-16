package cn.gs.sb_mongo.config;


public class YYTTEST {
	public static	int stringToInt(String s) throws Exception {
		char[] c = null ;
		String	cs="";
		int symbol =1;             //符号（ 正：1 ，负：-1）
		if (s.substring(0,1).equals("-")) {
			cs=s.substring(1, s.length());
			c =cs.toCharArray();
			symbol=-1;
		}else if (s.substring(0,1).equals("+")) {
			cs=s.substring(1, s.length());
			c =cs.toCharArray();
			symbol=1;
		}else {
			cs=s.substring(0, s.length());
			c = s.toCharArray();
		}
		for (char d : c) {           //字符串中有空格抛出异常
			String str=String.valueOf(d);
			if(str.equals(" ")){
				 throw new Exception("非法字符串..");
			}
		}
		int slength=cs.length();
		int resultInt=0;
		for (int i = slength; i > 0; i--) {
			resultInt+=Math.pow(10, i-1)*((int)c[slength-i]-'0');
		}
		return resultInt*symbol;
		
	}
	public static void main(String[] args) throws Exception {
		System.out.println(stringToInt("-12312323"));
	}
}
