import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import unyoawt.*;

/* �V�@�\!  �ڍאݒ�t���[�� */

public class SettingFrame extends Frame implements ActionListener,ItemListener,TextListener{
	public static final int SET_NUM=71;			//�ݒ���e�̌�
	public static final String SPLIT=",";		//��������؂�ۂ̕���

	private String[][] info=new String[SET_NUM][];		//����
	private String[] name=new String[SET_NUM];			//<param>��name
	private String[] value=new String[SET_NUM];			//value
	private String[] def=new String[SET_NUM];			//<param>�̏�����Ԃ�value

	private Choice sel=new Choice();					//�R���{�{�b�N�X
	private TextArea ta=new TextArea("��������"+System.getProperty("line.separator")+"0��ݒ肷��Ɩ�����");	//�����\��
	private TextField val=new TextField("0");			//�l�ݒ�p
	private Button color=new Button("�F������");				//�{�^��
	private Button defal=new Button("�S�ăf�t�H���g�ɖ߂�");

	private ColorDialog cd=new ColorDialog(this,"�F�̃e�X�g");

	public SettingFrame(String title,String[] txtdata){			//�R���X�g���N�^
		super(title);
		setBounds(100,100,500,300);

		setLayout(new BorderLayout());						//�R���|�[�l���g�z�u
		sel.setBackground(Color.pink);
		add(sel,BorderLayout.NORTH);
		add(ta,BorderLayout.CENTER);

		Panel p=new Panel();
		Label mes=new Label("�������Œl��ݒ肵�܂��B");
		mes.setBackground(Color.yellow);
		ta.setEditable(false);
		ta.setBackground(Color.green);
		val.setBackground(Color.cyan);
		color.setBackground(Color.red);
		defal.setBackground(Color.black);
		defal.setForeground(Color.white);

		color.addActionListener(this);				//�C�x���g�ݒ�
		defal.addActionListener(this);
		val.addTextListener(this);
		sel.addItemListener(this);

		p.setLayout(new GridLayout(4,0));
		p.add(mes);
		p.add(val);
		p.add(color);
		p.add(defal);
		add(p,BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				hide();
			}
		});
		for(int i=0;i<SET_NUM;i++){							//�f�[�^�ǂݍ���
			info[i]=split(txtdata[i*3],SPLIT);					//","�ŋ�؂��Ăł��������̔z�����
			sel.add(info[i][0]);
			name[i]=txtdata[i*3+1];
			value[i]=txtdata[i*3+2];
			def[i]=value[i];
		}
	}

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
	public void show(){									//�I�[�o�[���C�h
		super.show();
		val.setText(value[sel.getSelectedIndex()]);		//���I�΂�Ă���ݒ�l�ɂ���
	}

	public void itemStateChanged(ItemEvent ie){			//�R���{�{�b�N�X�ύX
		updateValue();
	}
	public void textValueChanged(TextEvent te){			//�e�L�X�g�t�B�[���h�ύX
		value[sel.getSelectedIndex()]=val.getText();
	}
	public void actionPerformed(ActionEvent ae){		//�{�^��������
		if(ae.getSource()==color){			//�F�e�X�g
			cd.show();
		}else if(ae.getSource()==defal){	//�f�t�H���g�ɖ߂�
			delete();
			updateValue();
		}
	}

	public void updateValue(){					//�l���X�V
		int num=sel.getSelectedIndex();
		String str="";
		val.setText(value[num]);
		for(int i=0;i<info[num].length;i++){
			str=str+info[num][i]+System.getProperty("line.separator");
		}
		ta.setText(str);
	}
	public void delete(){							//�f�t�H���g�ɖ߂�
		System.arraycopy(def,0,value,0,def.length);		//def����value�ɃR�s�[
	}

	public String toStr(){						//HTML�ɂ���
		String str="";
		for(int i=0;i<SET_NUM;i++){
			str+="<PARAM NAME=\""+name[i]+"\" VALUE=\""+value[i]+"\">"+System.getProperty("line.separator");	//�{��
			String infoall="";
			/*
			for(int ii=0;ii<info[i].length;ii++){		//�������Ȃ�
				infoall+=info[i][ii];
			}
			str+="<!-- "+infoall+" -->"+System.getProperty("line.separator");
			*/
		}
		return str;
	}

	public String getDefaultString(){					//�f�t�H���g�̏ڍאݒ��HTML(JavaScript�p)
		String str="";
		for(int i=0;i<name.length;i++){
			str+="<PARAM NAME=\""+name[i]+"\" VALUE=\""+def[i]+"\">"+System.getProperty("line.separator");
		}
		return str;
	}

	public void loadByString(String data)throws DataLoadException{			//HTML���烍�[�h
		for(int i=0;i<value.length;i++){
			int begin=data.indexOf("<PARAM NAME=\""+name[i]+"\"");		//�����o���͂��߂̈ʒu
			if(begin==-1){
				throw new DataLoadException("<PARAM NAME=\""+name[i]+"\"");
			}
			begin=begin+22+name[i].length();
			int end=data.indexOf("\"",begin);							//�����o�������̈ʒu,begin�̒���� �u"�v
			value[i]=data.substring(begin,end);
		}
	}

}