package yappy.others;
import java.io.*;

/**
 * ��������������ɗ���������Ȃ��I�}�P��static���\�b�h�Q�ł��B
 * @since 1.3
 */
public class Omake{
	private Omake(){}
	/**
	 * ��̃t�@�C�����ʂɍ쐬�ł��܂��B<br>
	 * ��:<br>
	 * MakeEmptyFiles("C:\Data\test",3,".txt");<br>
	 * C:\Data\ �� test0.txt test1.txt test2.txt ��3�̃t�@�C�������܂��B
	 * @param before �t�@�C�����̑O�ɂ��镶����
	 * @param num ���t�@�C���̌�
	 * @param after �t�@�C�����̌�ɂ��镶����
	 * @exception java.io.IOException ���o�̓G���[�����������ꍇ
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