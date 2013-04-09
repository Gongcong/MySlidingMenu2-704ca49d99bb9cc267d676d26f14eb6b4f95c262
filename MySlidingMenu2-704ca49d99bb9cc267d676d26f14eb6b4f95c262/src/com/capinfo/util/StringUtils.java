package com.capinfo.util;

import java.io.IOException;
import java.io.InputStream;

public class StringUtils {
	/**
	 *  �жϸ����ַ����Ƿ�հ״���
	 * �հ״���ָ�ɿո��Ʊ�����س��������з���ɵ��ַ���
	 * �������ַ���Ϊnull����ַ���������true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}
	/**
	 * InputStream תΪ String
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
}
