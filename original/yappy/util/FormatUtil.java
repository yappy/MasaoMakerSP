package yappy.util;
import java.text.*;

/**
 * ������������w�肳�ꂽ�P�^�܂łɐ؂�̂Ă郆�[�e�B���e�B�N���X�ł��B
 * @since 1.1
 */
public final class FormatUtil{
	private FormatUtil(){}
	/**
	 * double�l���� keta �P�^�܂łɐ؂�̂Ă܂��B
	 * ���Ƃ��΁A�����_�ȉ�3�P�^�܂ł̐��ɂ������̂Ȃ� keta �� 1000 ��n���܂��B<br>
	 * ��:<br>
	 * FormatUtil.format( 1.23456 , 3 ) �� 1.234<br>
	 * FormatUtil.format( -9.87654 , 2 ) �� -9.87<br>
	 * FormatUtil.format( 5.99 , 0 ) �� 5<br>
	 * @since 1.1
	 * @param val �t�H�[�}�b�g�Ώێ���
	 * @param keta �����_�ȉ����P�^�܂łɂ��邩
	 * @return �؂�̂Ă�ꂽ������
	 */
	public static final String format(double val,int keta){
		double d=(double)((int)(val*Math.pow(10,keta)));
		double dd=d/(double)Math.pow(10,keta);
		DecimalFormat df=new DecimalFormat();
		df.setMinimumFractionDigits(keta);
		return df.format(dd);
	}
	/**
	 * int�l�� keta �̈ʂ܂łɐ؂�̂Ă܂��B
	 * ���Ƃ��΁A��̈ʂ܂ł̐��ɂ������̂Ȃ� keta �� 1000 ��n���܂��B<br>
	 * �x��: keta �� 10^n(n�͔C�ӂ̐�) �ȊO�̐��l��n���Ƃ��������Ȃ�܂��B<br>
	 * ��:<br>
	 * FormatUtil.format( 123456 , 1000 ) �� 123000<br>
	 * FormatUtil.format( -987654 ,100  ) �� -987600<br>
	 * FormatUtil.format( 123456 , 1 ) �� 123456<br>
	 * @since 1.1
	 * @param val �t�H�[�}�b�g�Ώې���
	 * @param keta ���̈ʂ܂łɂ��邩,10�̗ݏ�
	 * @return �؂�̂Ă�ꂽ������
	 */
	public static final String format(int val,int keta){
		int i=(int)(val/keta)*keta;
		return Integer.toString(i);
	}
}