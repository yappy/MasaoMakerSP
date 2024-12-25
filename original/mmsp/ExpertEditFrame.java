import java.awt.*;
import java.awt.event.*;

/*
�G�L�X�p�[�g�ҏW�E�B���h�E!
*/

public class ExpertEditFrame extends Frame implements ActionListener{
	//���j���[�̃��x��(null�̓Z�p���[�^�[)
	private static final String[][] MENU_STRING={
		{"�I��","���ׂđI��",null,"�I��͈͂̍���1�g��","�I��͈͂̉E��1�g��","�I��͈͂̏��1�g��","�I��͈͂̉���1�g��",null,
			"�I��͈͂̍���1�k��","�I��͈͂̉E��1�k��","�I��͈͂̉���1�k��","�I��͈͂̏��1�k��"},
		{"�͈͕ҏW","���X�g�őI�΂�Ă�����̂œh��Ԃ�","�u�Ȃɂ��Ȃ��v�ŃN���A",null,
			"�R�s�[","�؂���","�\��t��","�I��͈͂ɃN���b�s���O���ē\��t��"},
		{"�͈͈ړ�","���E���]","�㉺���]","�㉺���E���]",null,"�E�ֈړ�","���ֈړ�","��ֈړ�","���ֈړ�"},
		{"�V�X�e��","��ʂ��X�V",null,"����"}
	};
	//���j���[�̃V���[�g�J�b�g
	private static final int[][] SHORTCUTS={
		//�I��
		{KeyEvent.VK_A,0,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN,0,
			KeyEvent.VK_RIGHT,KeyEvent.VK_LEFT,KeyEvent.VK_DOWN,KeyEvent.VK_UP},
		//�͈͕ҏW
		{KeyEvent.VK_F,KeyEvent.VK_DELETE,0,KeyEvent.VK_C,KeyEvent.VK_X,KeyEvent.VK_V,KeyEvent.VK_P},
		//�͈͈ړ�
		{KeyEvent.VK_H,KeyEvent.VK_V,KeyEvent.VK_B,0,KeyEvent.VK_R,KeyEvent.VK_L,KeyEvent.VK_U,KeyEvent.VK_D},
		//�V�X�e��
		{KeyEvent.VK_R,0,KeyEvent.VK_X}
	};
	//SHIFT�L�[�����邩
	private static final boolean[][] SHIFT={
		{false,false,false,false,false,false,false,true,true,true,true},
		{false,false,false,false,false,false,false},
		{false,false,false,false,false,false,false,false},
		{false,false,false}
	};

	//�{�̂���󂯎��
	private byte[][] map;
	private Image mainimg,subimg;
	private Graphics subgra;
	private MasaoList mlist;

	//���C���L�����o�X
	private PreCan can=new PreCan();

	//�X�e�[�^�X�\��
	private Label lx=new Label("0");
	private Label ly=new Label("0");
	private Label selstrx=new Label("0");
	private Label selstry=new Label("0");
	private Label selendx=new Label("0");
	private Label selendy=new Label("0");
	//�I��͈�
	private int strx,stry,endx,endy;
	//�N���b�v�{�[�h
	private byte[][] clip=new byte[0][0];

	public ExpertEditFrame(byte[][] map,Image mainimg,Image subimg,MasaoList mlist){
		super("�G�L�X�p�[�g�ҏW");
		this.map=map;
		this.mainimg=mainimg;
		this.subimg=subimg;
		this.mlist=mlist;
		subgra=subimg.getGraphics();
		//���j���[�쐬
		MenuBar mb=new MenuBar();
		for(int i=0;i<MENU_STRING.length;i++){
			Menu m=new Menu(MENU_STRING[i][0]);
			for(int j=1;j<MENU_STRING[i].length;j++){
				if(MENU_STRING[i][j]==null){
					m.addSeparator();
				}else{
					MenuItem mi=new MenuItem(MENU_STRING[i][j],new MenuShortcut(SHORTCUTS[i][j-1],SHIFT[i][j-1]));
					mi.setActionCommand(i+","+(j-1));
					mi.addActionListener(this);
					m.add(mi);
				}
			}
			mb.add(m);
		}
		setMenuBar(mb);
		//�R���|�[�l���g�z�u
		ScrollPane sp=new ScrollPane();
		sp.add(can);
		Panel info=new Panel();
		info.setLayout(new GridLayout(4,4));
		info.add(new Label("X���W"));
		info.add(lx);
		info.add(new Label("Y���W"));
		info.add(ly);
		info.add(new Label("�I���n�_-X"));
		info.add(selstrx);
		info.add(new Label("�I���n�_-Y"));
		info.add(selstry);
		info.add(new Label("�I���I�_-X"));
		info.add(selendx);
		info.add(new Label("�I���I�_-Y"));
		info.add(selendy);
		info.add(new Label("�N���b�N�Ŏn�_�I��"));
		info.add(new Label("Shift+�N���b�N�ŏI�_�I��"));
		add(sp,BorderLayout.CENTER);
		add(info,BorderLayout.SOUTH);

		setBounds(10,10,600,500);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){setVisible(false);}});
	}

	//���j���[�N���b�N
	public void actionPerformed(ActionEvent ae){
		String src=ae.getActionCommand();
		//���j���[�̃C���f�b�N�X
		int menu_index=Integer.parseInt(src.substring(0,src.indexOf(",")));
		//���j���[�A�C�e���̃C���f�b�N�X
		int item_index=Integer.parseInt(src.substring(src.indexOf(",")+1));
		switch(menu_index){
		//�I��
		case 0:
			switch(item_index){
			//���ׂđI��
			case 0:
				strx=0;
				stry=0;
				endx=179;
				endy=29;
			break;
			//����1�g��
			case 2:
				if(strx!=0){strx-=1;}
			break;
			//�E��1�g��
			case 3:
				if(endx!=179){endx+=1;}
			break;
			//���1�g��
			case 4:
				if(stry!=0){stry-=1;}
			break;
			//����1�g��
			case 5:
				if(endy!=29){endy+=1;}
			break;
			//�E��1�k��
			case 7:
				if(strx!=endx){strx++;}
			break;
			//����1�k��
			case 8:
				if(strx!=endx){endx--;}
			break;
			//���1�k��
			case 9:
				if(stry!=endy){endy--;}
			break;
			//����1�k��
			case 10:
				if(stry!=endy){stry++;}
			break;
			}
		break;
		//�I��͈͕ҏW
		case 1:
			switch(item_index){
				//�h��Ԃ�
			case 0:
				byte selitem=mlist.getSelectedMap();
				for(int i=strx;i<=endx;i++){
					for(int j=stry;j<=endy;j++){
						map[i][j]=selitem;
					}
				}
			break;
			//�N���A
			case 1:
				for(int i=strx;i<=endx;i++){
					for(int j=stry;j<=endy;j++){
						map[i][j]=99;
					}
				}
			break;
			//�R�s�[
			case 3:
				clip=new byte[endx-strx+1][endy-stry+1];
				for(int i=0;i<clip.length;i++){
					for(int j=0;j<clip[i].length;j++){
						clip[i][j]=map[i+strx][j+stry];
					}
				}
			break;
			//�؂���
			case 4:
				clip=new byte[endx-strx+1][endy-stry+1];
				for(int i=0;i<clip.length;i++){
					for(int j=0;j<clip[i].length;j++){
						clip[i][j]=map[i+strx][j+stry];
						map[i+strx][j+stry]=99;
					}
				}
			break;
			//�\��t��
			case 5:
				for(int i=0;i<clip.length;i++){
					for(int j=0;j<clip[i].length;j++){
						try{
							map[i+strx][j+stry]=clip[i][j];
						}catch(ArrayIndexOutOfBoundsException e){
						}
					}
				}
			break;
			//�N���b�s���O���ē\��t��
			case 6:
				for(int i=0;i<clip.length;i++){
					for(int j=0;j<clip[i].length;j++){
						try{
							//�I��͈͓��Ȃ�
							if(i<=endx-strx && j<=endy-stry){
								map[i+strx][j+stry]=clip[i][j];
							}
						}catch(ArrayIndexOutOfBoundsException e){
						}
					}
				}
			break;
			}
		break;
		//�I��͈͈ړ�
		case 2:
			//�I��͈͂̃}�b�v�f�[�^�o�b�t�@
			byte[][] buf=new byte[endx-strx+1][endy-stry+1];
			//�o�b�t�@1�ɑI��͈͂��R�s�[
			for(int i=0;i<buf.length;i++){
				for(int j=0;j<buf[i].length;j++){
					buf[i][j]=map[i+strx][j+stry];
				}
			}
			switch(item_index){
			//���E���]
			case 0:
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[buf.length-1-i+strx][j+stry]=buf[i][j];
					}
				}
				System.out.println("left");
			break;
			//�㉺���]
			case 1:
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][buf[i].length-1-j+stry]=buf[i][j];
					}
				}
			break;
			//�㉺���E���]
			case 2:
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[buf.length-1-i+strx][buf[i].length-1-j+stry]=buf[i][j];
					}
				}
			break;
			//�E�ֈړ�
			case 4:
				//���̏ꏊ����x�N���A
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][j+stry]=99;
					}
				}
				//�E�ɂ��炵�đ��
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						try{
							map[i+strx+1][j+stry]=buf[i][j];
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
				//�I��͈͂��E�ֈړ�
				if(strx!=179){strx++;}
				if(endx!=179){endx++;}
			break;
			//���ֈړ�
			case 5:
				//���̏ꏊ����x�N���A
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][j+stry]=99;
					}
				}
				//���ɂ��炵�đ��
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						try{
							map[i+strx-1][j+stry]=buf[i][j];
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
				//�I��͈͂����ֈړ�
				if(strx!=0){strx--;}
				if(endx!=0){endx--;}
			break;
			//��ֈړ�
			case 6:
				//���̏ꏊ����x�N���A
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][j+stry]=99;
					}
				}
				//��ɂ��炵�đ��
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						try{
							map[i+strx][j+stry-1]=buf[i][j];
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
				//�I��͈͂���ֈړ�
				if(stry!=0){stry--;}
				if(endy!=0){endy--;}
			break;
			//���ֈړ�
			case 7:
				//���̏ꏊ����x�N���A
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][j+stry]=99;
					}
				}
				//���ɂ��炵�đ��
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						try{
							map[i+strx][j+stry+1]=buf[i][j];
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
				//�I��͈͂����ֈړ�
				if(stry!=29){stry++;}
				if(endy!=29){endy++;}
			break;
			}
		break;
		//�V�X�e��
		case 3:
			switch(item_index){
				//��ʂ��X�V
				case 0:
					//�������Ȃ��Ă�����B
				break;
				//����
				case 2:
					setVisible(false);
				break;
			}
		break;
		}
		can.repaint();
	}

	//�v���r���[�L�����o�X
	private class PreCan extends Canvas implements MouseListener,MouseMotionListener{
		public PreCan(){
			setBounds(0,0,2880,480);
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		public void paint(Graphics g){
			subgra.setColor(Color.cyan);
			subgra.fillRect(0,0,2880,480);
			//�L������`��
			for(int i=0;i<30;i++){
				for(int j=0;j<180;j++){
					if(map[j][i]!=99){
						subgra.drawImage(mainimg,j*16,i*16,j*16+16,i*16+16,map[j][i]%10*32,map[j][i]/10*32,map[j][i]%10*32+32,map[j][i]/10*32+32,null);
					}
				}
			}
			//�O���b�h�`��
			subgra.setColor(Color.yellow);
			for(int i=0;i<30;i++){
				subgra.drawLine(0,i*16,2879,i*16);
			}
			for(int i=0;i<180;i++){
				subgra.drawLine(i*16,0,i*16,479);
			}
			//�I��͈͕`��
			subgra.setColor(Color.red);
			subgra.drawRect(strx*16,stry*16,(endx-strx)*16+15,(endy-stry)*16+15);
			//�I��͈̓f�[�^�X�V
			selstrx.setText(""+strx);
			selstry.setText(""+stry);
			selendx.setText(""+endx);
			selendy.setText(""+endy);
			//�o�b�t�@����ڂ�
			g.drawImage(subimg,0,0,null);
		}
		public void update(Graphics g){
			paint(g);
		}
		//�}�E�X���������Ƃ�
		public void mouseMoved(MouseEvent me){
			lx.setText(""+me.getX()/16);
			ly.setText(""+me.getY()/16);
		}
		//�N���b�N���ꂽ�Ƃ�
		public void mouseClicked(MouseEvent me){
			//SHIFT��������Ă���
			if(me.isShiftDown()){
				endx=me.getX()/16;
				endy=me.getY()/16;
			//������Ă��Ȃ�
			}else{
				strx=me.getX()/16;
				stry=me.getY()/16;
			}
			//�␳
			if(strx>endx){
				endx=strx;
			}
			if(stry>endy){
				endy=stry;
			}
			repaint();
		}
		//��(�X�J)
		public void mouseEntered(MouseEvent me){}
		public void mouseExited(MouseEvent me){}
		public void mousePressed(MouseEvent me){}
		public void mouseReleased(MouseEvent me){}
		public void mouseDragged(MouseEvent me){}
	}
}