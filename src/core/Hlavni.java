/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

/**
 * 
 * @author Milan Růžička
 */
public class Hlavni extends JFrame {
	private static final long serialVersionUID = 1L;
	/** Timer pro dig. hodiny */
	private Timer timer1 = new Timer();
	/** Timer pro stopky */
	private Timer timer2;
	/** Panel s časem/stopkama */
	private Stopky stopky = new Stopky();
	/** Počet kliknutí na tlačítko Mode */
	private int pocet_kliku = 0;
	/** Počet kliknutí na tlačítko Stop (pro vynulování stopek) */
	private int vynulovani_stopek = 0;

	/**
	 * Konstruktor Hlavní třídy, nadefinování vzhledu okna a běží zde timer pro dig. hodiny
	 */
	public Hlavni() {
		this.setTitle("Stopky");
		this.add(levaTlacitka(),BorderLayout.WEST);
		this.add(pravaTlacitka(),BorderLayout.EAST);
		this.add(stopky, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(450, 110));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		stopky.setBackground(Color.BLACK);

		timer1.schedule(new TimerTask() {
			@Override
			public void run() {
				//Nastaví se čas o jednu sekundu větší
				// .getTime().getTime() je tu proto aby mi to vrátilo milisekundy
				// jenom .getTime() vrátí čas ve formátu Time (můj getter ze třídy Stopky)
				stopky.setTime(new Time(stopky.getTime().getTime()+1000));
				//aby se čas překresoval tam kde má (lze poznat dle konzole že to funguje)
				if (stopky.getMode() == 0)
					stopky.repaint();
			}
		}, 1000, 1000);	
	}
	
	/**
	 * Levý panel se tlačítkem Mode
	 * 
	 * @return Panel s tlačítkem Mode
	 */
	Component levaTlacitka() {
		JPanel tlacitka = new JPanel();
		tlacitka.setPreferredSize(new Dimension(80, 50));
		tlacitka.setBackground(Color.BLACK);
		
		JButton mode = new JButton(new Mode());
		mode.setPreferredSize(new Dimension(70, 30));
		tlacitka.add(mode);
		
		return tlacitka;
	}
	
	/**
	 * Pravý panel s tlačítky Start a Stop
	 * 
	 * @return Panel s tlačítky Start a Stop
	 */
	Component pravaTlacitka() {
		JPanel tlacitka = new JPanel();
		tlacitka.setPreferredSize(new Dimension(80, 50));
		tlacitka.setBackground(Color.BLACK);
		
		JButton start = new JButton(new Start());
		start.setPreferredSize(new Dimension(70, 30));
		JButton stop = new JButton(new Stop());
		stop.setPreferredSize(new Dimension(70, 30));
		tlacitka.add(start);
		tlacitka.add(stop);
		
		return tlacitka;
	}
	
	/**
	 * Slouží k přepínání mezi stavy aplikace (mění se barvičky, pocet_kliku -> pro tlačítka Start/Stop)
	 * 
	 * @author Milan Růžička
	 */
	class Mode extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (pocet_kliku) {
				case 0: 
					stopky.setBarva(Color.YELLOW); 
					stopky.setIndex(2); 
					stopky.repaint();
					pocet_kliku++;
					break;
				case 1: 
					stopky.setBarva(Color.PINK); 
					stopky.setIndex(1); 
					stopky.repaint(); 
					pocet_kliku++;
					break;
				case 2: 
					stopky.setBarva(Color.RED); 
					stopky.setIndex(0); 
					stopky.repaint(); 
					pocet_kliku++;
					break;
				case 3: 
					stopky.setMode(1);
					stopky.setIndex(3);
					stopky.repaint(); 
					pocet_kliku++;
					break;
				default: 
					stopky.setMode(0);
					stopky.setBarva(Color.GREEN); 
					stopky.setIndex(0); 
					stopky.repaint(); 
					pocet_kliku = 0;
					break;
			}
		}
		
		public Mode() {
            putValue(NAME, "Mode");
            putValue(SHORT_DESCRIPTION, "Přepnutí režimu stopek (ALT+M)");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_M));
        }
	}
	
	/**
	 * Zde se přidávají sekundy/minuty/hodiny dle pocet_kliku na Mode a spouští se zde stopky
	 * 
	 * @author Milan Růžička
	 */
	class Start extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (pocet_kliku) {
				case 1:
					stopky.setTime(new Time(stopky.getTime().getTime()+1000));
					stopky.repaint();
					break;
				case 2:
					stopky.setTime(new Time(stopky.getTime().getTime()+(1000*60)));
					stopky.repaint();
					break;
				case 3:
					stopky.setTime(new Time(stopky.getTime().getTime()+(1000*60*60)));
					stopky.repaint();
					break;
				case 4:
					if (vynulovani_stopek == 0) {
						vynulovani_stopek = 1;
						timer2 = new Timer();
						timer2.schedule(new TimerTask() {
							@Override
							public void run() {
								stopky.setStopky(new Time(stopky.getStopky().getTime()+1000));
								if (stopky.getMode() == 1)
									stopky.repaint();
							}
						}, 1000, 1000);
					}
				default: break;
			}
		}
	
		public Start() {
	        putValue(NAME, "Start");
	        putValue(SHORT_DESCRIPTION, "Změna hodnoty o +1, nebo spuštění stopek (ALT+A)");
	        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
	    }
	}
	
	/**
	 * Zde se odebírají sekundy/minuty/hodiny dle pocet_kliku na Mode a zastavují/nulují se zde stopky
	 * 
	 * @author Milan Růžička
	 */
	class Stop extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (pocet_kliku) {
				case 1:
					stopky.setTime(new Time(stopky.getTime().getTime()-1000));
					stopky.repaint();
					break;
				case 2:
					stopky.setTime(new Time(stopky.getTime().getTime()-(1000*60)));
					stopky.repaint();
					break;
				case 3:
					stopky.setTime(new Time(stopky.getTime().getTime()-(1000*60*60)));
					stopky.repaint();
					break;
				case 4:
					if (vynulovani_stopek == 1) {
						timer2.cancel();
						vynulovani_stopek = 0;
					}
					else if (vynulovani_stopek == 0) {
						stopky.setStopky(new Time(-3600000));
						stopky.repaint();
					}
				default: break;
			}
		}
	
		public Stop() {
	        putValue(NAME, "Stop");
	        putValue(SHORT_DESCRIPTION, "Změna hodnoty o -1, nebo zastavení stopek (ALT+S)");
	        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
	    }
	}
	
	/**
	 *
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Hlavni();
	}
}
