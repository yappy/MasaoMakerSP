package yappy.util;
import java.io.*;

/**
	*標準入力を簡単にするユーティリティクラスです。
	*標準入力をBufferedReaderでラップしたものを最初からフィールドとして保持します。
	*最後に _E がつくメソッドは例外を無視します。
	*ただし入出力例外は標準入力では発生しないと見なしてすべて無視されます。
*/
public final class InUtil{
	private InUtil(){}
	/**
		*標準入力をBufferedReaderでラップしたものです。<br>
		*値は new BufferedReader(new InputStreamReader(System.in)) です。
	*/
	public static final BufferedReader IN=new BufferedReader(new InputStreamReader(System.in));

	/**
		*標準入力から文字列を1行入力します。<br> 
		*@return 入力された1行の文字列
	*/
	public static final String readString(){
		try{
			return IN.readLine();
		}catch(IOException e){
			return null;
		}
	}
	/**
	 * 標準入力から文字列を引数行入力して配列として返します。
	 * @param n 入力する回数
	 * @return 入力された文字列の配列
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
		*標準入力から整数値を入力します。<br>
		*例外が発生した場合は無視されます。
		*@return 入力されたint値,例外が発生した場合は0
	*/
	public static final int readInt(){
		try{
			return Integer.parseInt(IN.readLine());
		}catch(Exception e){
			return 0;
		}
	}
	/**
		*標準入力から整数値を入力します。<br>
		*例外処理はユーザーが行います。
		*@return 入力されたint値
		*@exception java.lang.NumberFormatException 数値変換エラーが発生した場合
	*/
	public static final int readInt_E()throws NumberFormatException{
		int ret=0;
		try{
			ret=Integer.parseInt(IN.readLine());
		}catch(IOException e){}
		return ret;
	}
	/**
	 * 標準入力から整数値を引数回入力して配列として返します。
	 * @param n 入力する回数
	 * @return 入力されたint値の配列
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
		*標準入力から実数値を入力します。<br>
		*例外が発生した場合は無視されます。
		*@return 入力されたdouble値,例外が発生した場合は0
	*/
	public static final double readDouble(){
		try{
			return Double.parseDouble(IN.readLine());
		}catch(Exception e){
			return 0L;
		}
	}
	/**
		*標準入力から実数値を入力します。<br>
		*例外処理はユーザーが行います。
		*@return 入力されたdouble値
		*@exception java.lang.NumberFormatException 数値変換エラーが発生した場合
	*/
	public static final double readDouble_E()throws NumberFormatException{
		double d=0.0;
		try{
			d=Double.parseDouble(IN.readLine());
		}catch(IOException e){}
		return d;
	}
	/**
	 * 標準入力から実数値を引数回入力して配列として返します。
	 * @param n 入力する回数
	 * @return 入力されたdouble値の配列
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