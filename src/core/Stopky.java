package core;

import java.awt.*;
import java.sql.*;
import java.text.*;

import javax.swing.*;

/**
 * T��da kter� se star� o vykreslen� panelu s �asem
 * 
 * @author Milan R�i�ka
 */
public class Stopky extends JPanel {
	private static final long serialVersionUID = 1L;
	/** M�d dig. hodin -> 0 = dig. hodiny, 1 = stopky */
	private int mode = 0;
	/** aktu�ln� nastavan� �as dig. hodin */
	private Time time = new Time(1000*60*60*(-1));
	/** aktu�ln� �as stopek */
	private Time stopky = new Time(1000*60*60*(-1));
	/** barva ��sti �asu (se kterou se manipuluje pomoc� Start/Stop) */
	private Color barva_hodnoty_kterou_menis = Color.GREEN;
	/** index ��sti �asu (se kterou se manipuluje pomoc� Start/Stop) */
	private int index = 0;
	
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public Time getStopky() {
		return stopky;
	}
	public void setStopky(Time stopky) {
		this.stopky = stopky;
	}
	public Color getBarva() {
		return barva_hodnoty_kterou_menis;
	}
	public void setBarva(Color barva) {
		this.barva_hodnoty_kterou_menis = barva;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	/** Form�t �asu kter� chceme */
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * Vykreslen� panelu
	 */
	public Stopky() {
		JPanel displej = new JPanel();
		displej.setBackground(Color.BLACK);
		displej.setPreferredSize(new Dimension(280, 65));
		this.add(displej);
	}
	
	/**
	 * Pl�tno, vol� si metodu paint2D
	 */
	public void paint(Graphics g) {
		super.paint(g);
		paint2D((Graphics2D)g);
	}
	
	/**
	 * Zde se kresl� na panel
	 * 
	 * @param g2 Grafick� kontext
	 */
	public void paint2D(Graphics2D g2) {
		if (mode == 0) {
			String cas = "" + dateFormat.format(time);
			new SegmentoveCislo(cas, g2, barva_hodnoty_kterou_menis, index);
			System.out.println(cas);
		}
		else if (mode == 1) {
			String cas = "" + dateFormat.format(stopky);
			new SegmentoveCislo(cas, g2, barva_hodnoty_kterou_menis, index);
			System.out.println(cas);
		}
	}
}
