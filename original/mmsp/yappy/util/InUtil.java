package yappy.util;
import java.io.*;

/**
	*�W�����͂��ȒP�ɂ��郆�[�e�B���e�B�N���X�ł��B
	*�W�����͂�BufferedReader�Ń��b�v�������̂��ŏ�����t�B�[���h�Ƃ��ĕێ����܂��B
	*�Ō�� _E �������\�b�h�͗�O�𖳎����܂��B
	*���������o�͗�O�͕W�����͂ł͔������Ȃ��ƌ��Ȃ��Ă��ׂĖ�������܂��B
*/
public final class InUtil{
	private InUtil(){}
	/**
		*�W�����͂�BufferedReader�Ń��b�v�������̂ł��B<br>
		*�l�� new BufferedReader(new InputStreamReader(System.in)) �ł��B
	*/
	public static final BufferedReader IN=new BufferedReader(new InputStreamReader(System.in));

	/**
		*�W�����͂��當�����1�s���͂��܂��B<br> 
		*@return ���͂��ꂽ1�s�̕�����
	*/
	public static final String readString(){
		try{
			return IN.readLine();
		}catch(IOException e){
			return null;
		}
	}
	/**
	 * �W�����͂��當����������s���͂��Ĕz��Ƃ��ĕԂ��܂��B
	 * @param n ���͂����
	 * @return ���͂��ꂽ������̔z��
	 * @since 1.2
	 */
	public static final String[] readStrings(int n){
		String[] ret=new String[n];
		for(int i=0;i<n;i++){
			ret[n]=readString();
		}
		return ret;
	}
	/**
		*�W�����͂��琮���l����͂��܂��B<br>
		*��O�����������ꍇ�͖�������܂��B
		*@return ���͂��ꂽint�l,��O�����������ꍇ��0
	*/
	public static final int readInt(){
		try{
			return Integer.parseInt(IN.readLine());
		}catch(Exception e){
			return 0;
		}
	}
	/**
		*�W�����͂��琮���l����͂��܂��B<br>
		*��O�����̓��[�U�[���s���܂��B
		*@return ���͂��ꂽint�l
		*@exception java.lang.NumberFormatException ���l�ϊ��G���[�����������ꍇ
	*/
	public static final int readInt_E()throws NumberFormatException{
		int ret=0;
		try{
			ret=Integer.parseInt(IN.readLine());
		}catch(IOException e){}
		return ret;
	}
	/**
	 * �W�����͂��琮���l����������͂��Ĕz��Ƃ��ĕԂ��܂��B
	 * @param n ���͂����
	 * @return ���͂��ꂽint�l�̔z��
	 * @since 1.2
	 */
	public static final int[] readInts(int n){
		int[] ret=new int[n];
		for(int i=0;i<n;i++){
			ret[n]=readInt();
		}
		return ret;
	}
	/**
		*�W�����͂�������l����͂��܂��B<br>
		*��O�����������ꍇ�͖�������܂��B
		*@return ���͂��ꂽdouble�l,��O�����������ꍇ��0
	*/
	public static final double readDouble(){
		try{
			return Double.parseDouble(IN.readLine());
		}catch(Exception e){
			return 0L;
		}
	}
	/**
		*�W�����͂�������l����͂��܂��B<br>
		*��O�����̓��[�U�[���s���܂��B
		*@return ���͂��ꂽdouble�l
		*@exception java.lang.NumberFormatException ���l�ϊ��G���[�����������ꍇ
	*/
	public static final double readDouble_E()throws NumberFormatException{
		double d=0.0;
		try{
			d=Double.parseDouble(IN.readLine());
		}catch(IOException e){}
		return d;
	}
	/**
	 * �W�����͂�������l����������͂��Ĕz��Ƃ��ĕԂ��܂��B
	 * @param n ���͂����
	 * @return ���͂��ꂽdouble�l�̔z��
	 * @since 1.2
	 */
	public static final double[] readDoubles(int n){
		double[] ret=new double[n];
		for(int i=0;i<n;i++){
			ret[n]=readDouble();
		}
		return ret;
	}
}