/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bonschallenge;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Josh
 */
public class DeveloperMode {
    private byte boardWidth, boardHeight;
    private JPanel sizeTab, mapBlockChoose, customBoard;
    Timer buttonHeld;
    TimerTask task;
    
    public DeveloperMode(){
        addDeveloperTabs();
        
    }
    
    private void addDeveloperTabs(){
        sizeTab = setupSizePanel();
        mapBlockChoose = setupMapBlockChoosePanel();
        customBoard = new JPanel();
        BonsFrame.jTabbedPane1.addTab("Size", sizeTab);
        BonsFrame.jTabbedPane1.addTab("Map Blocks", mapBlockChoose);
        generateBoard();
    }
    
    private JPanel setupSizePanel(){
        JPanel sizePanel = new JPanel();
        JButton smaller = new JButton("<");
        JButton bigger = new JButton(">");
        JButton custom = new JButton("Set");
        JTextField width = new JTextField("10");
        JTextField height = new JTextField("10");
        boardWidth = 10;
        boardHeight = 10;
        JLabel x = new JLabel("X");
        sizePanel.add(smaller, BorderLayout.WEST);
        sizePanel.add(width, BorderLayout.WEST);
        sizePanel.add(x, BorderLayout.CENTER);
        sizePanel.add(height, BorderLayout.EAST);
        sizePanel.add(bigger, BorderLayout.EAST);
        sizePanel.add(custom,  BorderLayout.SOUTH);
        boardWidth = Byte.parseByte(width.getText());
        boardHeight = Byte.parseByte(height.getText());
        smaller.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {   
                handleHold(false, width, height);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                buttonHeld.cancel();
                generateBoard();
            }
        });
        bigger.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                handleHold(true, width, height); 
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                buttonHeld.cancel();
                generateBoard();
            }
        });
        custom.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {   
                boardWidth = Byte.parseByte(width.getText());
                boardHeight = Byte.parseByte(height.getText());
            }
            @Override
            public void mouseReleased(MouseEvent e){
                generateBoard();
            }
        });
        return sizePanel;
    }  
    
    private JPanel setupMapBlockChoosePanel(){
    	JPanel mapBChoose = new JPanel();
    	JPanel playerPan = new JPanel();
    	JLabel players = new JLabel("Players");
    	playerPan.setLayout(new BorderLayout());
    	playerPan.add(players);
    	JLabel harv = new JLabel("");
    	JLabel fig = new JLabel("");
    	handleAnimatingIcons("harvey", harv, 4);
    	handleAnimatingIcons("figaro", fig, 4);
    	JPanel lowerPlayerPan = new JPanel();
    	lowerPlayerPan.setLayout(new BorderLayout());
    	lowerPlayerPan.add(harv, BorderLayout.WEST);
    	lowerPlayerPan.add(fig, BorderLayout.EAST);
    	playerPan.add(lowerPlayerPan, BorderLayout.SOUTH);
    	mapBChoose.add(playerPan);
    	
    	return mapBChoose;
    }
    
    private void handleAnimatingIcons(String iconBase, JLabel lab, int max){
    	ActionListener taskPerformer = new ActionListener(){
    		int currentIndex = 1;
    		public void actionPerformed(ActionEvent evt){
    			lab.setIcon(new ImageIcon(getClass().getResource("/bonschallenge/" + iconBase + "/" + currentIndex + ".png")));
    			if(currentIndex + 1 == max+1){
    				currentIndex = 1;
    			}else{
    				currentIndex++;
    			}
    		}
    	};
    	javax.swing.Timer animate = new javax.swing.Timer(250, taskPerformer);
    	animate.start();
    }
    
    
    private void handleHold(boolean inc, JTextField width, JTextField height){
        boolean increase = inc;
        buttonHeld = new Timer();
        class MyTimerTask extends TimerTask{
            public void run() {
                if(increase){
                    boardWidth++;
                    boardHeight++;
                    System.out.println("increased");
                }else{
                    if(boardWidth - 1 > 2 && boardHeight - 1 > 2){
                        boardWidth--;
                        boardHeight--;
                    }
                }
                width.setText(boardWidth + "");
                height.setText(boardHeight + "");
            }
        }
        task = new MyTimerTask();
        buttonHeld.scheduleAtFixedRate(task, 0, 250);
    }

    
    private void generateBoard(){
        JLabel[][] board = new JLabel[boardWidth][boardHeight];        
        customBoard.setLayout(new FlowLayout(FlowLayout.LEADING, 0,0));
        customBoard.setSize(20*boardWidth, 20*boardHeight);
        customBoard.removeAll();
        BonsFrame.jPanel2.removeAll();
        for(int i = 0; i < boardWidth; i++){
            for(int j = 0; j < boardHeight; j++){
                board[i][j] = new JLabel("");
                board[i][j].setIcon(new ImageIcon(getClass().getResource("/bonschallenge/plain.png")));
                board[i][j].setSize(20, 20);
                customBoard.add(board[i][j]);
            }
        }
        BonsFrame.jPanel2.setSize(customBoard.getSize());
        BonsFrame.jPanel2.add(customBoard);
        customBoard.revalidate();
        BonsFrame.jPanel2.revalidate();
        BonsFrame.jPanel2.repaint();
        customBoard.repaint();
        System.out.println("jPanel2 size is " +  BonsFrame.jPanel2.getWidth() + "x" +  BonsFrame.jPanel2.getHeight());
        
    }

}
