/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bonschallenge;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        mapBlockChoose = new JPanel();
        customBoard = new JPanel();
        JButton but1 = new JButton("test");
        BonsFrame.jTabbedPane1.addTab("Size", sizeTab);
        sizeTab.add(but1, BorderLayout.SOUTH);
        BonsFrame.jTabbedPane1.addTab("Map Blocks", mapBlockChoose);
        generateBoard();
    }
    
    private JPanel setupSizePanel(){
        JPanel sizePanel = new JPanel();
        JButton smaller = new JButton("<");
        JButton bigger = new JButton(">");
        JTextField width = new JTextField("10");
        JTextField height = new JTextField("10");
        JLabel x = new JLabel("X");
        sizePanel.add(smaller, BorderLayout.WEST);
        sizePanel.add(width, BorderLayout.WEST);
        sizePanel.add(x, BorderLayout.CENTER);
        sizePanel.add(height, BorderLayout.EAST);
        sizePanel.add(bigger, BorderLayout.EAST);
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
            }
        });
        return sizePanel;
    }  
    private void handleHold(boolean inc, JTextField width, JTextField height){
        boolean increase = inc;
        buttonHeld = new Timer();
        class MyTimerTask extends TimerTask{
            public void run() {
                if(increase){
                    boardWidth++;
                    boardHeight++;
                }else{
                    if(boardWidth - 1 > 0){
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
        generateBoard();
    }

    
    private void generateBoard(){
        JLabel[][] board = new JLabel[boardWidth][boardHeight];
        customBoard.setLayout(new GridLayout(boardWidth, boardHeight));
        customBoard.setSize(20*boardWidth, 20*boardHeight);
        customBoard.removeAll();
        BonsFrame.jPanel2.removeAll();
        customBoard.revalidate();
        BonsFrame.jPanel2.revalidate();
        BonsFrame.jPanel2.repaint();
        customBoard.repaint();
        for(int i = 0; i < boardWidth-1; i++){
            for(int j = 0; j < boardHeight-1; j++){
                board[i][j] = new JLabel("");
                board[i][j].setIcon(new ImageIcon(getClass().getResource("/bonschallenge/plain.png")));
                board[i][j].setSize(20, 20);
                customBoard.add(board[i][j]);
            }
        }
        BonsFrame.jPanel2.add(customBoard);
        BonsFrame.jPanel2.setSize(customBoard.getSize());
        customBoard.revalidate();
        BonsFrame.jPanel2.revalidate();
        BonsFrame.jPanel2.repaint();
        customBoard.repaint();
        
    }

}
