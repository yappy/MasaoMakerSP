package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

//�u���b�N����_�C�A���O
/**
 * �{�^�����������܂Ńv���O�������~������(�u���b�N����)�_�C�A���O�̒��ۊ��N���X�ł��B<br>
 * ����ă��[�_���ł��邱�Ƃ��K�v�ł��B<br>
 * �e�t���[���͂�����Ɛݒ肵�Ă��������B<br>
 * ���b�Z�[�W�� \n �ŋ�؂邱�Ƃɂ��3�s�ɕ����\�ł��B<br>
 * �܂��A���̃N���X�ɂ͕�����𕪉����� split ���\�b�h������܂��Bsplit���\�b�h��1.4�ȍ~�ł���java.lang.String�ɓ��ڂ���Ă��܂���B
 */
public abstract class BlockDialog extends DefCloseDlg implements ActionListener{
	/**�R���X�g���N�^�p�̃{�^���̎�ނ����߂�萔�ł��B�uOK�v�{�^����\�����܂��B*/
	public static final int OK=0;					//�{�^���̎��
	/**�R���X�g���N�^�p�̃{�^���̎�ނ����߂�萔�ł��B�u�͂��v�Ɓu�������v�{�^����\�����܂��B*/
	public static final int YES_NO=1;
	/**�R���X�g���N�^�p�̃{�^���̎�ނ����߂�萔�ł��B�uOK�v�Ɓu�L�����Z���v�{�^����\�����܂��B*/
	public static final int OK_CANCEL=2;
	/**�R���X�g���N�^�p�̃{�^���̎�ނ����߂�萔�ł��B�u�͂��v�Ɓu�������v�Ɓu�L�����Z���v�{�^����\�����܂��B*/
	public static final int YES_NO_CANCEL=3;

	/**�����ꂽ�{�^���𔻕ʂ���萔�ł��B�~�{�^���������ꂽ���Ƃ�\���܂��B*/
	public static final int CLOSE=-1;
	/**�����ꂽ�{�^���𔻕ʂ���萔�ł��B��ԍ��̃{�^���������ꂽ���Ƃ�\���܂��B*/
	public static final int FIRST=0;
	/**�����ꂽ�{�^���𔻕ʂ���萔�ł��B�������Ԗڂ̃{�^���������ꂽ���Ƃ�\���܂��B*/
	public static final int SECOND=1;
	/**�����ꂽ�{�^���𔻕ʂ���萔�ł��B������O�Ԗڂ̃{�^���������ꂽ���Ƃ�\���܂��B*/
	public static final int THIRD=2;

	private boolean oked=false;						//�u���b�N���������邱�Ƃ�\��
	private int pushed=0;							//�����ꂽ�{�^��
	private Label[] mes=new Label[3];				//���b�Z�[�W��3�s
	/**�_�C�A���O�㕔�̃R���e�i�ł��BGridLayout�ɂ��3�������ꃁ�b�Z�[�W�\���p�̃��x���������Ă��܂��B*/
	protected Panel ue=new Panel();
	/**�_�C�A���O�����̃R���e�i�ł��BFlowLayout�ɂ��{�^���������Ă��܂��B*/
	protected Panel sita=new Panel();

	/**
	 * �T�u�N���X�ł͕K������� super �ŌĂяo���Ă��������B
	 * @param own �_�C�A���O�̃I�[�i�[
	 * @param button �{�^���̎��,�萔�𗘗p���Ă�������
	 */
	protected BlockDialog(Frame own,int button){			//�R���X�g���N�^
		super(own,"",true,DefCloseDlg.HIDE);
		setBounds(200,200,300,200);
		setResizable(false);

		setLayout(new BorderLayout());
		ue.setLayout(new GridLayout(3,0));
		for(int i=0;i<mes.length;i++){
			mes[i]=new Label("",Label.CENTER);
			ue.add(mes[i]);
		}
		add(ue,BorderLayout.CENTER);

		Button[] btns;
		if(button==OK){
			Button b0=new Button("OK");
			btns=new Button[1];
			btns[0]=b0;
		}else if(button==YES_NO){
			Button b0=new Button("�͂�");
			Button b1=new Button("������");
			btns=new Button[2];
			btns[0]=b0;
			btns[1]=b1;
		}else if(button==OK_CANCEL){
			Button b0=new Button("OK");
			Button b1=new Button("�L�����Z��");
			btns=new Button[2];
			btns[0]=b0;
			btns[1]=b1;
		}else{									//button==YES_NO_CANCEL
			Button b0=new Button("�͂�");
			Button b1=new Button("������");
			Button b2=new Button("�L�����Z��");
			btns=new Button[3];
			btns[0]=b0;
			btns[1]=b1;
			btns[2]=b2;
		}
		for(int i=0;i<btns.length;i++){
			btns[i].setActionCommand(""+i);
			btns[i].addActionListener(this);
			sita.add(btns[i]);
		}
		add(sita,BorderLayout.SOUTH);
	}
	/**
	 * �����ɂ���Ă��܂��B
	 * ��p��show(String title,String mes)���g���Ă��������B
	 */
	public void show(){
		System.err.println("BlockDialog����x��");
		System.err.println("show()�̓I�[�o�[���C�h����Ė���������Ă��܂��B");
		System.err.println("show(String title,String mes)���Ăяo���Ă��������B");
	}
	/**
	 * �_�C�A���O��\�����܂��B�{�^�����������܂Ńu���b�N���܂��B
	 * ����ݒ�ł���̂ŁA�^�C�g���⃁�b�Z�[�W���ƂɃI�u�W�F�N�g��new�Ő�������K�v�͂���܂���B
	 * ���b�Z�[�W�́A\n�ŋ�؂邱�Ƃɂ��A3�s�܂ŉ��s�ł��܂��B
	 * @return �����ꂽ�{�^��,�萔�𗘗p���Ă��������B�\�����邾���Ȃ�߂�l�𗘗p����K�v�͂���܂���B
	 * @param title �^�C�g���o�[�ɕ\������^�C�g��
	 * @param mes �\�����郁�b�Z�[�W,\n�ŉ��s
	 */
	public int show(String title,String mes){
		setTitle(title);
		String[] splited=split(mes,"\n");			//"\n"�ŕ�����
		for(int i=0;i<this.mes.length;i++){
			this.mes[i].setText("");
		}
		for(int i=0;i<splited.length;i++){
			try{
			this.mes[i].setText(splited[i]);
			}catch(ArrayIndexOutOfBoundsException e){break;}
		}
		super.show();
		while(!oked){
			try{Thread.sleep(10);}catch(InterruptedException e){}
		}
		return pushed;
	}
	/**�{�^���N���b�N�C�x���g�ł��B*/
	public synchronized  final void actionPerformed(ActionEvent ae){		//while�̏�������Əd�Ȃ�Ȃ��悤�ɂ��邽��
		pushed=Integer.parseInt(ae.getActionCommand());
		oked=true;
		hide();
	}
	/**�~�������ꂽ�Ƃ��̃C�x���g�ł��B*/
	public final void windowClosing(WindowEvent we){
		oked=true;
		pushed=-1;
		hide();
	}

	/**
	 * ������𕪊����܂��B<br>
	 * java.lang.String��split��Java1.4�œ��ڂ���܂����B
	 * 1.1�݊��̃A�v���b�g�ȂǂŊ��p���Ă��������B
	 * @param target �����Ώە�����
	 * @param regex �����Ɏg��������
	 * @return �����ς݂̕�����̔z��
	 */
	public static String[] split(String target,String regex){			//target��regex�ŕ�������
		int count=0;
		int begin=0;
		while(true){
			if(target.indexOf(regex,begin) != -1){
				count++;
				begin=target.indexOf(regex,begin)+1;
			}else{
				break;
			}
		}
		String[] forret=new String[count+1];
		begin=0;
		for(int i=0;i<forret.length;i++){
			if(i==forret.length-1){
				forret[i]=target.substring(begin);
			}else{
				forret[i]=target.substring(begin,target.indexOf(regex,begin));
			}
			begin=target.indexOf(regex,begin)+regex.length();
		}
		return forret;
	}

}