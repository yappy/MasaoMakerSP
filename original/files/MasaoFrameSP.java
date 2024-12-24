import java.awt.*;
import java.awt.event.*;
import yappy.awtdialog.*;

/*�܂������[�J�[�p�W���g���t���[��!*/

public class MasaoFrameSP extends Frame implements ActionListener{
	private byte[][] map;					//�}�b�v�z��ւ̎Q��
	private byte[][] backup;					//�}�b�v�̃o�b�N�A�b�v
	private MasaoMakerSP.MasaoCanvas mc;	//Canvas��repaint()���邽��
	private SettingFrame sf;				//�����Ńf�t�H���g�ɖ߂�����,�ڍאݒ�����[�h���邽��
	private MasaoList list;					//�S�ĕύX�Ȃǂ̂���

	private OutputDialog od=new OutputDialog("�o��");
	private InputDialog id=new InputDialog("���[�h");

	private byte toroku=99;					//�o�^

	public static int MENU=7;				//���j���[�̐�
	private Button[] btn=new Button[MENU];	//���j���[�{�^��

	public static MessageDialog			ALERT;		//�_�C�A���O
	public static YesNoDialog 			CONFIRM;
	public static yappy.awtdialog.InputDialog INPUT;

	public MasaoFrameSP(String title,byte[][] map,MasaoMakerSP.MasaoCanvas mc,MasaoList list,SettingFrame sf){
		super(title);
		setBounds(100,100,500,400);
		setBackground(Color.gray);

		ALERT=new MessageDialog(this);
		CONFIRM=new YesNoDialog(this);
		INPUT=new yappy.awtdialog.InputDialog(this);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				hide();
			}
		});
		this.map=map;
		this.mc=mc;
		this.list=list;
		this.sf=sf;

		setLayout(new GridLayout(MENU,0,0,10));
		btn[0]=new Button("�}�b�v�����ׂă��X�g�őI�΂�Ă�����̂ɕύX");
		btn[1]=new Button("�}�b�v�̈�ԉ������ׂă��X�g�őI�΂�Ă�����̂ɕύX");
		btn[2]=new Button("x�����w�肵�Ă��Ĉ������ׂă��X�g�őI�΂�Ă�����̂ɕύX");
		btn[3]=new Button("y�����w�肵�Ă悱�������ׂă��X�g�őI�΂�Ă�����̂ɕύX");
		btn[4]=new Button("�o�b�N�A�b�v�����");
		btn[5]=new Button("�o�b�N�A�b�v���畜��");
		btn[6]=new Button("���X�g�őI�΂�Ă�����̂�o�^(Alt+�N���b�N�Ŏg�p)");
		//btn[7]=new Button("");
		//btn[8]=new Button("");
		//btn[9]=new Button("");
		for(int i=0;i<btn.length;i++){
			add(btn[i]);
			btn[i].setActionCommand(""+i);
			btn[i].addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent ae){
		switch(Integer.parseInt(ae.getActionCommand())){
		case 0:						//���ׂĕύX
			byte selected=list.getSelectedMap();		//�I�΂�Ă���̂���x�ۑ�
			for(int i=0;i<5400;i++){
				map[i/30][i%30]=selected;
			}
			mc.repaint();
			hide();
		break;
		case 1:						//��ԉ���ύX
			selected=list.getSelectedMap();			//�I�΂�Ă���̂���x�ۑ�
			for(int i=0;i<180;i++){
				map[i][29]=selected;
			}
			mc.repaint();
			hide();
		break;
		case 2:						//x���ň�C(�^�e)
			try{
				selected=list.getSelectedMap();			//�I�΂�Ă���̂���x�ۑ�
				int targetx=Integer.parseInt(			//X���W����
						INPUT.show("X���W�L��","�ڕW��X���W\n(���C����ʂ̐Ԃ��Ƃ���̏�̐���)\n�������Ă�������(0�`179)","0"));
				for(int i=0;i<30;i++){
					map[targetx][i]=selected;
				}
				mc.repaint();
				hide();
			}catch(Exception e){		//NumberFormat,NullPointer,ArrayIndexOutOfBounds�S�����������璆�~
				ALERT.show("���s","�������ꂽ���A\n���l�ϊ��Ɏ��s�������A\n���l���͈͊O�ł��B");
			}
		break;
		case 3:						//y���ň�C(���R)
			try{
				selected=list.getSelectedMap();			//�I�΂�Ă���̂���x�ۑ�
				int targety=Integer.parseInt(			//X���W����
						INPUT.show("Y���W�L��","�ڕW��Y���W\n(���C����ʂ̐Ԃ��Ƃ���̍��̐���)\n�������Ă�������(0�`29)","0"));
				for(int i=0;i<180;i++){
					map[i][targety]=selected;
				}
				mc.repaint();
				hide();
			}catch(Exception e){		//NumberFormat,NullPointer,ArrayIndexOutOfBounds�S�����������璆�~
				ALERT.show("���s","�������ꂽ���A\n���l�ϊ��Ɏ��s�������A\n���l���͈͊O�ł��B");
			}
		break;
		case 4:						//�o�b�N�A�b�v
			backup=new byte[180][30];
			for(int i=0;i<5400;i++){
				backup[i/30][i%30]=map[i/30][i%30];
			}
			ALERT.show("����","�o�b�N�A�b�v���܂����B");
		break;
		case 5:						//�o�b�N�A�b�v���畜��
			try{
				for(int i=0;i<map.length;i++){
					System.arraycopy(backup[i],0,map[i],0,map[i].length);
				}
				mc.repaint();
				ALERT.show("����","�o�b�N�A�b�v���畜�����܂����B");
			}catch(NullPointerException e){
				ALERT.show("���s","�o�b�N�A�b�v���Ƃ��Ă��܂���B");
			}finally{
				hide();
			}
		break;
		case 6:						//�o�^
			toroku=list.getSelectedMap();
			ALERT.show("����",list.getSelectedItem()+"��o�^���܂����B");
		break;
		}
	}

	public byte getToroku(){
		return toroku;
	}
	public void output(String data){		//�o��
		od.output(data);
	}
	public void input(){					//���[�h
		id.show();
	}

	private class OutputDialog extends DefCloseDlg{		//�����N���X,�o�͗p�_�C�A���O
		private TextArea ta=new TextArea();			//�o�͐�

		public OutputDialog(String title){
			super(MasaoFrameSP.this,title,true,DefCloseDlg.HIDE);
			setBounds(100,100,600,500);
			ta.setEditable(false);
			setLayout(new BorderLayout());
			add(new Label("�R�s�[���āA�ۑ����Ă��������B�R�s�[:Ctrl(command)+C"),BorderLayout.NORTH);
			add(ta,BorderLayout.CENTER);
		}
		public void output(String data){							//�o��!
			ta.setText(data);
			ta.selectAll();
			show();
		}
	}
	private class InputDialog extends DefCloseDlg implements ActionListener{		//�����N���X,���[�h�p�_�C�A���O
		private TextArea ta=new TextArea();

		public InputDialog(String title){
			super(MasaoFrameSP.this,title,true,DefCloseDlg.HIDE);
			setBounds(100,100,600,500);
			setLayout(new BorderLayout());
			add(new Label("�o�͂��ꂽ���̂��R�s�[���ē\��t���Ă��������B���ꂩ�烍�[�h���邱�Ƃ��ł��܂��B�\��t��:Ctrl(command)+V"),BorderLayout.NORTH);
			add(ta,BorderLayout.CENTER);
			Button b=new Button("OK!���[�h�J�n!");
			b.setBackground(Color.red);
			b.setForeground(Color.yellow);
			b.addActionListener(this);
			add(b,BorderLayout.SOUTH);
		}
		public void show(){						//�I�[�o�[���C�h
			ta.setText("");
			super.show();
		}
		public void actionPerformed(ActionEvent ae){				//���[�h�J�n
			String data=ta.getText();							//���[�h����f�[�^
			byte[][] submap=new byte[180][30];				//�����������݂̂��ꂪ�R�s�[�����
			try{
				for(int i1=0;i1<3;i1++){					//3�̂����܂�
					for(int i2=0;i2<30;i2++){				//1�s
						int begin=data.indexOf("<PARAM NAME=\"map"+i1+"-"+i2+"\"");		//�n�܂��T��
						if(begin==-1){
							throw new DataLoadException("<PARAM NAME=\"map"+i1+"-"+i2+"\"");
						}
						begin+=29;							//60�̎n�܂�̃C���f�b�N�X
						String oneline=data.substring(begin,begin+60);
						for(int i3=0;i3<60;i3++){			//1����
							submap[i1*60+i3][i2]=MasaoMakerSP.charToMapByte(oneline.charAt(i3));
						}
					}
				}
				sf.loadByString(data);						//�ڍאݒ胍�[�h,��O�����A��

				/*��������͗�O�����������A�I������Ƃ�*/
				for(int i=0;i<map.length;i++){
					System.arraycopy(submap[i],0,map[i],0,map[i].length);
				}
				mc.repaint();
				ALERT.show("����","���[�h�ɐ������܂���\n����ł͂���΂��Ă��������B");
				hide();
			}catch(DataLoadException e){					//�L�[���[�h��������Ȃ���throw�����
				ta.setText(e.toString());
			}
		}
	}

}