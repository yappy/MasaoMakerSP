import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import yappy.awtdialog.*;

/*�܂������[�J�[��SP�ƂȂ��ĕ��������̂��B*/

public class MasaoMakerSP extends Applet{
	byte[][] map=new byte[180][30];		//�}�b�v�z��
	Image img=null;						//�摜
	MasaoCanvas mc;						//���C���L�����o�X
	MapCanvas mapcan;					//�}�b�v�\���p
	MasaoFrameSP mf;					//�g���t���[��
	SettingFrame sf;					//�ڍאݒ�t���[��
	ExpertEditFrame ef;					//����ҏW�t���[��
	MasaoList list;						//���X�g

	String before="";					//HTML�̍ŏ��ƍŌ�ɂ��镶����
	String after="";

	Button top=new Button("��");		//�ړ��{�^��
	Button left=new Button("��");
	Button right=new Button("��");
	Button low=new Button("��");

	Button output=new Button("�o��!");		//�e��{�^��
	Button input=new Button("���[�h");
	Button delete=new Button("����");
	Button menu=new Button("�g�����j���[");
	Button setting=new Button("�ڍאݒ�");
	Button preview=new Button("�G�L�X�p�[�g�ҏW");

//������
//----------------------------------------------------------------------------------------------------------
	public void init(){
		img=getImage(getDocumentBase(),getParameter("image_file"));			//�C���[�W�ǂݍ���
		MediaTracker mt=new MediaTracker(this);
		mt.addImage(img,0);
		try{mt.waitForAll();}catch(InterruptedException e){}
		if(img==null){
			System.err.println("MasaoMakerSP����x��:�C���[�W�t�@�C����ǂݍ��߂܂���B");
			System.err.println("FileName: "+getParameter("image_file"));
			return;
		}

		BufferedReader br=null;
		try{					//���X�g�̂��߂̃f�[�^�ǂݍ���
			br=new BufferedReader(
				new InputStreamReader(new URL(getDocumentBase(),getParameter("chara_data")).openStream()));
			String[] lister=new String[66];
			for(int i=0;i<lister.length;i++){
				lister[i]=br.readLine();
			}
			list=new MasaoList(lister);
		}catch(IOException e){
			System.err.println("MasaoMakerSP����x��:�L�����f�[�^�t�@�C����ǂݍ��߂܂���B");
			System.err.println("FileName: "+getParameter("chara_data"));
			return;
		}finally{
			try{br.close();}catch(Exception e){}
		}
		try{					//HTML�̑O��̃f�[�^�ǂݍ���
			br=new BufferedReader(
				new InputStreamReader(new URL(getDocumentBase(),getParameter("before")).openStream()));
			String data;
			while((data=br.readLine())!=null){
				before+=data+System.getProperty("line.separator");
			}
			br=new BufferedReader(
				new InputStreamReader(new URL(getDocumentBase(),getParameter("after")).openStream()));
			while((data=br.readLine())!=null){
				after+=data+System.getProperty("line.separator");
			}
		}catch(IOException e){
			System.err.println("MasaoMakerSP����x��:HTML�̑O��t�@�C����ǂݍ��߂܂���B");
			System.err.println("FileName: "+getParameter("before")+"�܂���"+getParameter("after"));
			return;
		}finally{
			try{br.close();}catch(Exception e){}
		}
		try{					//�ڍאݒ�̂��߂̃f�[�^�ǂݍ���
			br=new BufferedReader(
				new InputStreamReader(new URL(getDocumentBase(),getParameter("setting_data")).openStream()));
			String[] txtdata=new String[SettingFrame.SET_NUM*3];
			for(int i=0;i<SettingFrame.SET_NUM*3;i++){
				txtdata[i]=br.readLine();
			}
			sf=new SettingFrame("�ڍאݒ�",txtdata);
		}catch(IOException e){
			System.err.println("MasaoMakerSP����x��:�ڍאݒ�f�[�^�t�@�C����ǂݍ��߂܂���B");
			System.err.println("FileName: "+getParameter("setting_data"));
		}finally{
			try{br.close();}catch(Exception e){}
		}
		for(int i=0;i<5400;i++){			//�}�b�v������
			map[i/30][i%30]=99;
		}

		ActionListener al=new ActionListener(){				//�ړ��{�^���C�x���g
			public void actionPerformed(ActionEvent ae){
				if(ae.getSource()==top){
					mc.up();
				}else if(ae.getSource()==low){
					mc.down();
				}else if(ae.getSource()==left){
					mc.left();
				}else if(ae.getSource()==right){
					mc.right();
				}
				mapcan.repaint();
			}
		};
		top.addActionListener(al);
		left.addActionListener(al);
		right.addActionListener(al);
		low.addActionListener(al);

		al=new ActionListener(){								//�R�}���h�{�^���C�x���g
			public void actionPerformed(ActionEvent ae){
				if(ae.getSource()==output){			//�o��
					mf.output(makeHTML());
				}else if(ae.getSource()==input){	//����
					mf.input();
				}else if(ae.getSource()==delete){	//����
					if(MasaoFrameSP.CONFIRM.show("�{���ɂ����ł���","�{���ɂ��ׂď������Ă����ł����B\n���ɂ͖߂��܂���B\n"+"�ڍאݒ�����ׂăf�t�H���g�ɖ߂�܂��B")==BlockDialog.FIRST){
						for(int i=0;i<5400;i++){
						map[i/30][i%30]=99;
					}
					sf.delete();			//�ڍאݒ菉����
					mc.repaint();
				}
				}else if(ae.getSource()==menu){		//���j���[�Ăяo��
					mf.show();
				}else if(ae.getSource()==setting){	//�ڍאݒ�E�B���h�E�Ăяo��
					sf.show();
				}else if(ae.getSource()==preview){	//�v���r���[�E�B���h�E�Ăяo��
					ef.show();
				}
			}
		};
		output.addActionListener(al);
		input.addActionListener(al);
		delete.addActionListener(al);
		menu.addActionListener(al);
		setting.addActionListener(al);
		preview.addActionListener(al);

		add(mapcan=new MapCanvas());					//�n�}��add

		Panel ue=new Panel();						//�R���|�[�l���g�z�u
		Panel btn_box=new Panel();
		btn_box.setBounds(352,0,90,90);
		btn_box.setLayout(new GridLayout(3,3));
		btn_box.add(new Label());		btn_box.add(top);			btn_box.add(new Label());
		btn_box.add(left);				btn_box.add(new Label());	btn_box.add(right);
		btn_box.add(new Label());		btn_box.add(low);			btn_box.add(new Label());
		
		ue.setLayout(null);
		ue.setBounds(0,0,500,352);
		ue.add(btn_box);
		ue.add(mc=new MasaoCanvas());
		ue.add(list);

		Panel sita=new Panel();
		sita.setLayout(new GridLayout(2,4,5,5));
		sita.setBounds(0,352,300,48);
		sita.setBackground(Color.green);
		sita.add(output);
		sita.add(input);
		sita.add(delete);
		sita.add(menu);
		sita.add(setting);
		sita.add(preview);

		setLayout(null);
		add(ue);
		add(sita);

		mf=new MasaoFrameSP("�W���g���@�\",map,mc,list,sf);			//�W���g���t���[���p��
		ef=new ExpertEditFrame(map,img,createImage(2880,480),list);
		list.select(0);				//���X�g��I��ł���
	}

	public String makeHTML(){							//HTML�𐶐�,JavaScript������g��
		String maked="";
		for(int i1=0;i1<3;i1++){
			for(int i2=0;i2<30;i2++){
				String linedata="";				//1�s
				for(int i3=0;i3<60;i3++){
					linedata=linedata+MasaoMakerSP.mapByteToString(map[60*i1+i3][i2]);
				}
				if(i2<10){
					linedata="<PARAM NAME=\"map"+i1+"-"+i2+"\"  VALUE=\""+linedata+"\">"+System.getProperty("line.separator");
				}else{
					linedata="<PARAM NAME=\"map"+i1+"-"+i2+"\" VALUE=\""+linedata+"\">"+System.getProperty("line.separator");
				}
				maked+=linedata;
			}
			maked+=System.getProperty("line.separator");
		}
		maked= before + maked + sf.toStr() + after;		//�ڍאݒ�Ƃ��̑���ǉ�

		return maked;
	}

	public String makeCompactHTML(){					//�A�v���b�g�^�O����������HTML
		String maked="";
		for(int i1=0;i1<3;i1++){
			for(int i2=0;i2<30;i2++){
				String linedata="";				//1�s
				for(int i3=0;i3<60;i3++){
					linedata=linedata+MasaoMakerSP.mapByteToString(map[60*i1+i3][i2]);
				}
				if(i2<10){
					linedata="<PARAM NAME=\"map"+i1+"-"+i2+"\"  VALUE=\""+linedata+"\">"+System.getProperty("line.separator");
				}else{
					linedata="<PARAM NAME=\"map"+i1+"-"+i2+"\" VALUE=\""+linedata+"\">"+System.getProperty("line.separator");
				}
				maked+=linedata;
			}
			maked+=System.getProperty("line.separator");
		}
		maked=maked+sf.toStr();
		return maked;
	}

	public String makeCompactSave(){					//�e�ʂ�}���������̗���,JavaScript����g��
		String maked="";
		for(int i1=0;i1<30;i1++){
			for(int i2=0;i2<180;i2++){
				int now=map[i2][i1];
				if(now<10){
					maked=maked+"0"+now;
				}else{
					maked+=now;
				}
			}
		}
		return maked;
	}

	public String getDefaultSetting(){					//�f�t�H���g�̏ڍאݒ�,JavaScript����g��
		return sf.getDefaultString();
	}

	public static String mapByteToString(byte map){		//int�̃}�b�v�f�[�^��HTML�ł�String�ɕϊ�
		char c='.';
		if(map<26){					//A�`Z
			c=(char)(map+'A');
		}else if(map<52){			//a�`z
			c=(char)(map-26+'a');
		}else if(map<61){			//1�`9
			c=(char)(map-52+'1');
		}else if(map<65){			//+,-,*,/
			switch(map){
			case 61:
				c='+';
			break;
			case 62:
				c='-';
			break;
			case 63:
				c='*';
			break;
			case 64:
				c='/';
			break;
			}
		}else{
			c='.';
		}
		return new Character(c).toString();
	}
	public static byte charToMapByte(char c)throws DataLoadException{	//HTML�ł�String�̃}�b�v�f�[�^��char�ɂ������̂�byte�ɕϊ�
		if(c>='A' && c<='Z'){			//A�`Z
			return (byte)(c-'A');		//A�Ƃ̍���Ԃ�
		}else if(c>='a' && c<='z'){		//a�`z
			return (byte)(c-'a'+26);	//a�Ƃ̍�+26��Ԃ�
		}else if(c>='1' && c<='9'){		//1�`9
			return (byte)(c-'1'+52);
		}else if(c=='+'){return 61;}
		else if(c=='-'){return 62;}
		else if(c=='*'){return 63;}
		else if(c=='/'){return 64;}
		else if(c=='.'){return 99;}
		else{
			throw new DataLoadException(new Character(c).toString(),DataLoadException.FORMAT_ERROR);
		}
	}
//-----------------------------------------------------------------------------------------------------------

	public class MasaoCanvas extends Canvas{					//�����N���X,�}�b�v�\���p�L�����o�X
		private int x=0;			//����̍��W
		private int y=0;
		private Image subimg;		//�_�u���X�N���[��
		private Graphics subg;

		public MasaoCanvas(){
			super();
			setBounds(0,0,352,352);
			subimg=MasaoMakerSP.this.createImage(352,352);
			subg=subimg.getGraphics();
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			addMouseMotionListener(new MouseMotionAdapter(){	//�h���b�O
				public void mouseDragged(MouseEvent me){
					mauseDragAndClick(me);
				}
			});
			addMouseListener(new MouseAdapter(){				//�N���b�N
				public void mouseClicked(MouseEvent me){
					mauseDragAndClick(me);
				}
			});
		}
		public void mauseDragAndClick(MouseEvent me){			//�h���b�O���N���b�N���ꏏ�Ȃ̂ł܂Ƃ߂�
			if(me.getX()<32||me.getY()<32||me.getX()>352-1||me.getY()>352-1){
				return;
			}
			if(me.isShiftDown()){							//Shift�������Ȃ���ŏ���
				map[(me.getX()-32)/32+x][(me.getY()-32)/32+y]=99;
			}else if(me.isAltDown()){						//Alt�������Ȃ���œo�^�����̂��g�p
				map[(me.getX()-32)/32+x][(me.getY()-32)/32+y]=mf.getToroku();
			}else{											//�W��
				map[(me.getX()-32)/32+x][(me.getY()-32)/32+y]=list.getSelectedMap();
			}
			repaint();
		}
		public void paint(Graphics g){							//�y�C���g
			subg.setColor(Color.red);
			subg.fillRect(0,0,352,352);		//��ʂ��N���A
			subg.setColor(Color.black);
			for(int i=0;i<10;i++){							//���W�\��
				subg.drawString(Integer.toString(x+i),32*i+32+10,20);
			}
			for(int i=0;i<10;i++){
				subg.drawString(Integer.toString(y+i),10,32*i+32+20);
			}
			for(int i=0;i<10;i++){
				for(int ii=0;ii<10;ii++){
					if(map[x+i][y+ii]==99){
						subg.setColor(Color.cyan);
						subg.fillRect(i*32+32,ii*32+32,32,32);
					}else{
						subg.drawImage(img,i*32+32,ii*32+32,i*32+64,ii*32+64,
							map[x+i][y+ii]%10*32,map[x+i][y+ii]/10*32,map[x+i][y+ii]%10*32+32,map[x+i][y+ii]/10*32+32,this);
					}
				}
			}
			subg.setColor(Color.yellow);
			for(int i=0;i<11;i++){							//�O���b�h�ŋ�؂�
				subg.drawLine(0,i*32-1,351,i*32-1);
			}
			for(int i=0;i<11;i++){
				subg.drawLine(i*32+32,0,i*32+32,351);
			}
			g.drawImage(subimg,0,0,this);
		}
		public void update(Graphics g){
			paint(g);
		}
		public int getX(){			//x,y��Ԃ�
			return x;
		}
		public int getY(){
			return y;
		}
		public void up(){			//��ֈړ�
			if(y!=0){
				y-=10;
			}
			repaint();
		}
		public void down(){			//���ֈړ�
			if(y!=20){
				y+=10;
			}
			repaint();
		}
		public void right(){		//�E�ֈړ�
			if(x!=170){
				x+=10;
			}
			repaint();
		}
		public void left(){			//���ֈړ�
			if(x!=0){
				x-=10;
			}
			repaint();
		}
		public void reset(){		//(0,0)�ֈړ�
			x=0;
			y=0;
			repaint();
		}
	}

	private class MapCanvas extends Canvas{			//���݈ʒu�������n�}�̃L�����o�X
		public final int WIDTH=10;
		public final int HEIGHT=10;
		public final int START_X;
		public final int START_Y;

		public MapCanvas(){
			super();
			setBounds(300,352,200,400-352);
			START_X=(200-WIDTH*18)/2;
			START_Y=(400-352-HEIGHT*3)/2;
			setBackground(Color.cyan);
		}
		public void paint(Graphics g){			//���C��
			g.setColor(Color.yellow);
			g.fillRect(START_X,START_Y,WIDTH*18,HEIGHT*3);
			g.setColor(Color.red);
			g.fillRect(START_X+mc.getX()/10*WIDTH,START_Y+mc.getY()/10*HEIGHT,WIDTH,HEIGHT);
		}
		public void update(Graphics g){
			paint(g);
		}
		public int getWidth(){
			return getBounds().width;
		}
		public int getHeight(){
			return getBounds().height;
		}
	}

}

/*
<html>
<head><title>�܂������[�J�[SP</title></head>
<body>

<applet code="MasaoMakerSP.class"width="500"height="400">
	<param name="image_file"value="makerpat.gif">
	<param name="before"value="Before.txt">
	<param name="after"value="After.txt">
	<param name="chara_data"value="CharaData.txt">
	<param name="setting_data"value="SettingData.txt">
</applet>

</body>
*/