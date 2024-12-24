package yappy.others;
import java.io.*;

/**
 * もしかしたら役に立つかもしれないオマケのstaticメソッド群です。
 * @since 1.3
 */
public class Omake{
	private Omake(){}
	/**
	 * 空のファイルを大量に作成できます。<br>
	 * 例:<br>
	 * MakeEmptyFiles("C:\Data\test",3,".txt");<br>
	 * C:\Data\ に test0.txt test1.txt test2.txt の3つのファイルを作ります。
	 * @param before ファイル名の前につける文字列
	 * @param num 作るファイルの個数
	 * @param after ファイル名の後につける文字列
	 * @exception java.io.IOException 入出力エラーが発生した場合
	 * @since 1.3
	 */
	public static void MakeEmptyFiles(String before,int num,String after)throws IOException{
		for(int i=0;i<num;i++){
			FileWriter w=null;
			try{
				w=new FileWriter(before+i+after);
				w.write("",0,0);
			}finally{
				try{w.close();}catch(IOException e){}
			}
		}
	}
}