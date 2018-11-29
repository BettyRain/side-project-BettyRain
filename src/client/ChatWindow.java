package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatWindow extends javax.swing.JFrame {
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;

	private static final String IP_ADDR = "localhost";
	private static final int PORT = 7070;
//    private ServerConnection connection;

	public ChatWindow() {
		initComponents();
		setVisible(true);
		jButton1.setEnabled(false);
		jButton3.setEnabled(false);
	}

	private void initComponents() {
		setResizable(false);
		jTextField1 = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jTextField2 = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTextField1.setToolTipText("input");

		jButton1.setFont(new java.awt.Font("Comic Sans MS", Font.PLAIN, 11)); // NOI18N
		jButton1.setText("Post");
		jButton1.addActionListener(this::jButtonPostActionPerformed);

		jButton2.setFont(new java.awt.Font("Comic Sans MS", Font.PLAIN, 11)); // NOI18N
		jButton2.setText("Join");
		jButton2.addActionListener(this::jButtonJoinActionPerformed);

		jButton3.setFont(new java.awt.Font("Comic Sans MS", Font.PLAIN, 11)); // NOI18N
		jButton3.setText("Leave");
		jButton3.addActionListener(this::jButtonLeaveActionPerformed);

		jTextArea1.setColumns(20);
		jTextArea1.setFont(new java.awt.Font("Comic Sans MS", Font.PLAIN, 12)); // NOI18N
		jTextArea1.setRows(5);

		jTextArea1.setEditable(false);
		jScrollPane2.setViewportView(jTextArea1);

		jTextField2.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 14)); // NOI18N
		jTextField2.addActionListener(this::jTextField2ActionPerformed);

		jLabel1.setFont(new java.awt.Font("Comic Sans MS", Font.PLAIN, 11)); // NOI18N
		jLabel1.setText("Enter your name and press Join");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSeparator1)
				.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane2)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 72,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(0, 0, Short.MAX_VALUE))
										.addComponent(jTextField1))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 72,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(8, 8, 8))
						.addGroup(layout.createSequentialGroup()
								.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 152,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jLabel1).addGap(0, 112, Short.MAX_VALUE)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(31, 31, 31)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jTextField2).addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 222,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton1))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));

//        jTextField1.setText("test");
//        jTextField2.setText("test");

		jTextField2.setEnabled(true);
		jTextField1.setEnabled(false);
		jTextArea1.setLineWrap(true);
		jTextArea1.setWrapStyleWord(true);

		pack();
	}// </editor-fold>

	private void jButtonPostActionPerformed(java.awt.event.ActionEvent evt) {
		String msg = jTextField1.getText();
		if (msg.equals("")) {
			return;
		}
		jTextField1.setText(null);
		new Thread(() -> Launcher.chat.sendMessage(msg)).start();
	}

	private void jButtonJoinActionPerformed(java.awt.event.ActionEvent evt) {
		new Thread(() -> Launcher.chat.init(jTextField2.getText())).start();
		// todo: maybe we need some callback.

		jButton1.setEnabled(true);
		jButton2.setEnabled(false);
		jButton3.setEnabled(true);

		jLabel1.setVisible(false);

		jTextField2.setEnabled(false);
		jTextField1.setEnabled(true);
	}

	private void jButtonLeaveActionPerformed(java.awt.event.ActionEvent evt) {
		printDisconnect();
		Launcher.chat.disconnect();
		setStart();
		jButton1.setEnabled(false);
		jButton2.setEnabled(true);
		jButton3.setEnabled(false);
		jLabel1.setVisible(true);

	}

	public void printDisconnect() {
		SwingUtilities.invokeLater(() -> {
			jTextArea1.append("You left the chat\r\n");
			jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
		});
	}

	private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
		// add your handling code here:
	}

	public synchronized void printMsg(String msg) {
		SwingUtilities.invokeLater(() -> {
			String help = msg.split(" ", 2)[1];
			String nick = help.split(" ", 2)[0];
			String mess = help.split(" ", 2)[1];
			jTextArea1.append(nick + ": " + mess + "\r\n");
			jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
		});
	}

	public synchronized void printJoin(String msg) {
		SwingUtilities.invokeLater(() -> {
			jTextArea1.append(msg.split(" ")[1] + " joined the chat\r\n");
			jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
		});
	}

	public synchronized void printLeave(String msg) {
		SwingUtilities.invokeLater(() -> {
			jTextArea1.append(msg.split(" ")[1] + " left the chat\r\n");
			jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
		});
	}

	public synchronized void printError(String msg) {
		SwingUtilities.invokeLater(() -> {
			jTextArea1.append("FATAL ERROR: " + msg + "\r\n");
			jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
		});
	}

	public void setStart() {
		jButton1.setEnabled(false);
		jButton2.setEnabled(true);
		jButton3.setEnabled(false);

		jTextField2.setEnabled(true);
		jTextField1.setEnabled(false);

	}
}
