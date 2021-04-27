package yappy.util;
import java.io.*;

/**
 * ファイルからの入力を簡単にするユーティリティクラスです。
 * @since 1.1
 */
public final class FileInput{
	private BufferedReader br;
	/**
	 * ファイル名を指定して新しいFileInputオブジェクトを生成します。
	 * 例外は無視されますが、正しく使用できません。
	 * @param filename ファイル名
	 * @since 1.1
	 */
	public FileInput(String filename){
		try{
			br=new BufferedReader(new FileReader(filename));
		}catch(FileNotFoundException e){}
	}
	/**
	 * ファイルからの入力ストリームを閉じます。
	 * 使い終わったら必ず閉じてください。
	 */
	public void close(){
		try{
			br.close();
		}catch(IOException e){}
	}
	/**
	 * ファイルから文字列を1行入力します。
	 * ファイルの終わりに達していたり、例外が発生した場合は、nullが返されます。
	 * @return 読み込まれた文字列,ファイルの終わりに達していたり例外が発生した場合は null
	 * @since 1.1
	 */
	public String readString(){
		try{
			return br.readLine();
		}catch(IOException e){
			return null;
		}
	}
	/**
	 * ファイルから文字列を引数行入力して配列として返します。
	 * readStrings(num).length==num となります。
	 * ファイルの終わりに達した場合は、そこからnullとなります。
	 * @param num 何行読み込むか
	 * @return 読み込まれた文字列の配列
	 * @since 1.1
	 */
	public String[] readStrings(int num){
		String[] ret=new String[num];
		for(int i=0;i<num;i++){
			ret[i]=readString();
		}
		return ret;
	}
	/**
	 * ファイルから整数値を入力します。
	 * 例外が発生した場合、0が返されます。
	 * @return 読み込まれた整数,ファイルの終わりに達していたり例外が発生した場合は0
	 * @since 1.1
	 */
	public int readInt(){
		try{
			return Integer.parseInt(br.readLine());
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * ファイルから整数値を引数行入力して配列として返します。
	 * readInts(num).length==num となります。
	 * ファイルの終わりに達した場合は、そこから0となります。
	 * @param num 何行読み込むか
	 * @return 読み込まれた整数値の配列
	 * @since 1.1
	 */
	public int[] readInts(int num){
		int[] ret=new int[num];
		for(int i=0;i<num;i++){
			ret[i]=readInt();
		}
		return ret;
	}
	/**
	 * ファイルから実数値を入力します。
	 * 例外が発生した場合、0が返されます。
	 * @return 読み込まれた実数,ファイルの終わりに達していたり例外が発生した場合は0
	 * @since 1.1
	 */
	public double readDouble(){
		try{
			return Double.parseDouble(br.readLine());
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * ファイルから実数値を引数行入力して配列として返します。
	 * readInts(num).length==num となります。
	 * ファイルの終わりに達した場合は、そこから0となります。
	 * @param num 何行読み込むか
	 * @return 読み込まれた実数値の配列
	 * @since 1.1
	 */
	public double[] readDoubles(int num){
		double[] ret=new double[num];
		for(int i=0;i<num;i++){
			ret[i]=readDouble();
		}
		return ret;
	}
}