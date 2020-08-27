package readerandwriter;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.Color;

class myThread extends Thread {
	static ReadWriteLock Lock = new ReadWriteLock();
	Random r = new Random();
	static int readcount = 0;
	static int writecount = 0;
	boolean flag;
	String a, b;
	double c;

	myThread(String q, String s, boolean choose) {
		c = r.nextDouble() * 1;
		a = q;
		b = s;
		flag = choose;
	}

	public void run() {
		if (b == "R") {
			Lock.aa.fill("读线程" + a + "被创建\n");
			/*
			 * try{ Thread.sleep((int)(1000*c)); } catch(InterruptedException e){}
			 */
			Lock.aa.fill("读线程" + a + "申请读操作\n");
			try {
				if (!flag) {
					Lock.rreadlock(a);
				} else {
					Lock.wreadlock(a);
				}
			} catch (InterruptedException e1) {
			}
			Lock.aa.fill("读线程" + a + "开始读操作\n");
			try {
				Thread.sleep((int) (1000 * c));
			} catch (InterruptedException e) {
			}
			Lock.aa.fill("读线程" + a + "结束读操作\n");
			Lock.readunlock(a);
		} else if (b == "W") {
			Lock.aa.fill("写线程" + a + "被创建\n");
			/*
			 * try{ Thread.sleep((int)(1000*c)); } catch(InterruptedException e){}
			 */
			Lock.aa.fill("写线程" + a + "申请写操作\n");
			try {
				Lock.writelock(a);
			} catch (InterruptedException e1) {
			}
			Lock.aa.fill("写线程" + a + "开始写操作\n");
			try {
				Thread.sleep((int) (1000 * c));
			} catch (InterruptedException e) {
			}
			Lock.aa.fill("写线程" + a + "结束写操作\n");
			Lock.writeunlock(a);
		}
	}
}

class ReadWriteLock {
	public ResultJFrame aa = new ResultJFrame();
	private int readingThreads = 0;
	private int writingThreads = 0;
	private int waitingThreads = 0;

	public synchronized void rreadlock(String a) throws InterruptedException {// 读优先
		while ((writingThreads > 0)) {
			aa.fill("读线程" + a + "等待\n");
			this.wait();
		}
		readingThreads++;
	}

	public synchronized void wreadlock(String a) throws InterruptedException {// 写优先
		while ((writingThreads > 0 || (waitingThreads > 0))) {
			aa.fill("读线程" + a + "等待\n");
			this.wait();
		}
		readingThreads++;
	}

	public synchronized void readunlock(String a) {
		readingThreads--;
		aa.fill("读线程" + a + "释放\n");
		notifyAll();
		// notify();
	}

	public synchronized void writelock(String a) throws InterruptedException {
		waitingThreads++;
		try {
			while (readingThreads > 0 || writingThreads > 0) {
				aa.fill("写线程" + a + "等待\n");
				this.wait();
			}
		} finally {
			waitingThreads--;
		}
		writingThreads++;
	}

	public synchronized void writeunlock(String a) {
		writingThreads--;
		aa.fill("写线程" + a + "释放\n");
		notifyAll();
		// notify();
	}
}

/**
 *
 * @author jumper
 */
public class MainJFrame extends javax.swing.JFrame {

	/**
	 * Creates new form NewJFrame
	 */
	public MainJFrame() {
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setForeground(Color.WHITE);
		initComponents();
	}

	static boolean flag = false;
	String ch[] = new String[20];
	String str[] = new String[20];
	String st = "";
	int count = 0;
	boolean choose;

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jPanel1.setBackground(Color.WHITE);
		jLabel1 = new javax.swing.JLabel();
		jToggleButton1 = new javax.swing.JToggleButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jTextArea1.setBackground(Color.WHITE);
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBackground(Color.WHITE);
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("读者写者问题展示");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setPreferredSize(new java.awt.Dimension(350, 350));
		setResizable(false);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		jPanel1.setPreferredSize(new java.awt.Dimension(150, 115));

		jLabel1.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
		jLabel1.setText(" 读者优先 ");

		jToggleButton1.setText("转换按钮");
		jToggleButton1.setMaximumSize(new java.awt.Dimension(80, 25));
		jToggleButton1.setMinimumSize(new java.awt.Dimension(80, 25));
		jToggleButton1.setPreferredSize(new java.awt.Dimension(80, 25));
		jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jToggleButton1ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(
						jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
										.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 126,
												Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(26, 26, 26)
										.addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(40, Short.MAX_VALUE)));

		jTextArea1.setColumns(10);
		jTextArea1.setFont(new Font("宋体", Font.BOLD, 24)); // NOI18N
		jTextArea1.setRows(5);
		jTextArea1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
		jTextArea1.setEnabled(false);
		jTextArea1.setSelectionColor(new java.awt.Color(255, 255, 255));
		jScrollPane1.setViewportView(jTextArea1);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		jPanel2.setPreferredSize(new java.awt.Dimension(135, 150));

		jButton1.setText("添加读者");
		jButton1.setMaximumSize(new java.awt.Dimension(80, 25));
		jButton1.setMinimumSize(new java.awt.Dimension(80, 25));
		jButton1.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("添加写者");
		jButton2.setMaximumSize(new java.awt.Dimension(80, 25));
		jButton2.setMinimumSize(new java.awt.Dimension(80, 25));
		jButton2.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton4.setText("清除");
		jButton4.setMaximumSize(new java.awt.Dimension(80, 25));
		jButton4.setMinimumSize(new java.awt.Dimension(80, 25));
		jButton4.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		jButton3.setText("开始");
		jButton3.setMaximumSize(new java.awt.Dimension(80, 25));
		jButton3.setMinimumSize(new java.awt.Dimension(80, 25));
		jButton3.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout
				.createSequentialGroup().addGap(28)
				.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(jButton3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(jButton2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
						.addComponent(jButton1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
						.addComponent(jButton4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
				.addContainerGap(31, Short.MAX_VALUE)));
		jPanel2Layout
				.setVerticalGroup(
						jPanel2Layout.createParallelGroup(Alignment.LEADING)
								.addGroup(
										jPanel2Layout.createSequentialGroup().addContainerGap()
												.addComponent(jButton1, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(jButton2, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(jButton4, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(jButton3, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel2.setLayout(jPanel2Layout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE))
						.addComponent(jScrollPane1))
				.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jToggleButton1ActionPerformed
		// TODO add your handling code here:
		count = 0;
		st = "";
		jTextArea1.setText(st);
		myThread a = new myThread("", "", choose);
		a.Lock.aa.zero();
		if (jToggleButton1.isSelected()) {
			jLabel1.setText(" 写者优先 ");
			choose = true;
		} else {
			jLabel1.setText(" 读者优先 ");
			choose = false;
		}
	}// GEN-LAST:event_jToggleButton1ActionPerformed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		// TODO add your handling code here:
		myThread a = new myThread("", "", choose);
		a.Lock.aa.start();
		bg();
	}// GEN-LAST:event_jButton3ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here
		add("R");
		st += "添加一个读者  ";
		jTextArea1.setColumns(10);
		jTextArea1.setLineWrap(true);
		jTextArea1.setText(st);
	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		// TODO add your handling code here:
		add("W");
		st += "添加一个写者  ";
		jTextArea1.setColumns(10);
		jTextArea1.setLineWrap(true);
		jTextArea1.setText(st);
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		// TODO add your handling code here:
		count = 0;
		st = "";
		jTextArea1.setText(st);
		myThread a = new myThread("", "", choose);
		a.Lock.aa.zero();
	}// GEN-LAST:event_jButton4ActionPerformed

	/**
	 * @param args the command line arguments
	 */
	void add(String b) {
		ch[count] = b;
		count++;
	}

	void bg() {
		int i;
		myThread mythread[] = new myThread[10];
		for (i = 0; i < count; i++) {
			str[i] = "";
			str[i] += '1' + i - 48;
			mythread[i] = new myThread(str[i], ch[i], choose);
			mythread[i].start();
			try {
				sleep(100);
			} catch (InterruptedException ex) {
				Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			// java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			// name, ex);
		} catch (InstantiationException ex) {
			// java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			// name, ex);
		} catch (IllegalAccessException ex) {
			// java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			// name, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			// java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			// name, ex);
		}
		// </editor-fold>
		/* Create and display the form */

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainJFrame().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JToggleButton jToggleButton1;
	// End of variables declaration//GEN-END:variables
}
