package core;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

/**
 * 
 * @author Milan R�i�ka
 */
public class Hlavni extends JFrame {
	private static final long serialVersionUID = 1L;
	/** Timer pro dig. hodiny */
	private Timer timer1 = new Timer();
	/** Timer pro stopky */
	private Timer timer2;
	/** Panel s �asem/stopkama */
	private Stopky stopky = new Stopky();
	/** Po�et kliknut� na tla��tko Mode */
	private int pocet_kliku = 0;
	/** Po�et kliknut� na tla��tko Stop (pro vynulov�n� stopek) */
	private int vynulovani_stopek = 0;

	/**
	 * Konstruktor Hlavn� t��dy, nadefinov�n� vzhledu okna a b�� zde timer pro dig. hodiny
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
				//Nastav� se �as o jednu sekundu v�t��
				// .getTime().getTime() je tu proto aby mi to vr�tilo milisekundy
				// jenom .getTime() vr�t� �as ve form�tu Time (m�j getter ze t��dy Stopky)
				stopky.setTime(new Time(stopky.getTime().getTime()+1000));
				//aby se �as p�ekresoval tam kde m� (lze poznat dle konzole �e to funguje)
				if (stopky.getMode() == 0)
					stopky.repaint();
			}
		}, 1000, 1000);	
	}
	
	/**
	 * Lev� panel se tla��tkem Mode
	 * 
	 * @return Panel s tla��tkem Mode
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
	 * Prav� panel s tla��tky Start a Stop
	 * 
	 * @return Panel s tla��tky Start a Stop
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
	 * Slou�� k p�ep�n�n� mezi stavy aplikace (m�n� se barvi�ky, pocet_kliku -> pro tla��tka Start/Stop)
	 * 
	 * @author Milan R�i�ka
	 */
	class Mode extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (pocet_kliku) {
				case 0: 
					stopky.setBarva(Color.RED); 
					stopky.setIndex(2); 
					stopky.repaint();
					pocet_kliku++;
					break;
				case 1: 
					stopky.setBarva(Color.RED); 
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
            putValue(SHORT_DESCRIPTION, "P�epnut� re�imu stopek (ALT+M)");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_M));
        }
	}
	
	/**
	 * Zde se p�id�vaj� sekundy/minuty/hodiny dle pocet_kliku na Mode a spou�t� se zde stopky
	 * 
	 * @author Milan R�i�ka
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
	        putValue(SHORT_DESCRIPTION, "Zm�na hodnoty o +1, nebo spu�t�n� stopek (ALT+A)");
	        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
	    }
	}
	
	/**
	 * Zde se odeb�raj� sekundy/minuty/hodiny dle pocet_kliku na Mode a zastavuj�/nuluj� se zde stopky
	 * 
	 * @author Milan R�i�ka
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
	        putValue(SHORT_DESCRIPTION, "Zm�na hodnoty o -1, nebo zastaven� stopek (ALT+S)");
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
