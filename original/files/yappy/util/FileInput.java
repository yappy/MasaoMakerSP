package yappy.util;
import java.io.*;

/**
 * �t�@�C������̓��͂��ȒP�ɂ��郆�[�e�B���e�B�N���X�ł��B
 * @since 1.1
 */
public final class FileInput{
	private BufferedReader br;
	/**
	 * �t�@�C�������w�肵�ĐV����FileInput�I�u�W�F�N�g�𐶐����܂��B
	 * ��O�͖�������܂����A�������g�p�ł��܂���B
	 * @param filename �t�@�C����
	 * @since 1.1
	 */
	public FileInput(String filename){
		try{
			br=new BufferedReader(new FileReader(filename));
		}catch(FileNotFoundException e){}
	}
	/**
	 * �t�@�C������̓��̓X�g���[������܂��B
	 * �g���I�������K�����Ă��������B
	 */
	public void close(){
		try{
			br.close();
		}catch(IOException e){}
	}
	/**
	 * �t�@�C�����當�����1�s���͂��܂��B
	 * �t�@�C���̏I���ɒB���Ă�����A��O�����������ꍇ�́Anull���Ԃ���܂��B
	 * @return �ǂݍ��܂ꂽ������,�t�@�C���̏I���ɒB���Ă������O�����������ꍇ�� null
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
	 * �t�@�C�����當����������s���͂��Ĕz��Ƃ��ĕԂ��܂��B
	 * readStrings(num).length==num �ƂȂ�܂��B
	 * �t�@�C���̏I���ɒB�����ꍇ�́A��������null�ƂȂ�܂��B
	 * @param num ���s�ǂݍ��ނ�
	 * @return �ǂݍ��܂ꂽ������̔z��
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
	 * �t�@�C�����琮���l����͂��܂��B
	 * ��O�����������ꍇ�A0���Ԃ���܂��B
	 * @return �ǂݍ��܂ꂽ����,�t�@�C���̏I���ɒB���Ă������O�����������ꍇ��0
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
	 * �t�@�C�����琮���l�������s���͂��Ĕz��Ƃ��ĕԂ��܂��B
	 * readInts(num).length==num �ƂȂ�܂��B
	 * �t�@�C���̏I���ɒB�����ꍇ�́A��������0�ƂȂ�܂��B
	 * @param num ���s�ǂݍ��ނ�
	 * @return �ǂݍ��܂ꂽ�����l�̔z��
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
	 * �t�@�C����������l����͂��܂��B
	 * ��O�����������ꍇ�A0���Ԃ���܂��B
	 * @return �ǂݍ��܂ꂽ����,�t�@�C���̏I���ɒB���Ă������O�����������ꍇ��0
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
	 * �t�@�C����������l�������s���͂��Ĕz��Ƃ��ĕԂ��܂��B
	 * readInts(num).length==num �ƂȂ�܂��B
	 * �t�@�C���̏I���ɒB�����ꍇ�́A��������0�ƂȂ�܂��B
	 * @param num ���s�ǂݍ��ނ�
	 * @return �ǂݍ��܂ꂽ�����l�̔z��
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