/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

/**
 *
 * @author Milan RĹŻĹľiÄŤka, Nikolas Diesl
 */
public final class Hlavni extends JFrame {

    private static final long serialVersionUID = 1L;
    /**
     * Timer pro dig. hodiny
     */
    private final Timer timer1 = new Timer();
    /**
     * Timer pro stopky
     */
    private static Timer timer2;
    /**
     * Panel s ÄŤasem/stopkama
     */
    private Stopky stopky = new Stopky();
    /**
     * PoÄŤet kliknutĂ­ na tlaÄŤĂ­tko Mode 0 = Def 1 = Sec 2 = Min 4 = Hod 5 = St
     */
    private int pocet_kliku = 0;
    /**
     * PoÄŤet kliknutĂ­ na tlaÄŤĂ­tko Stop (pro vynulovĂˇnĂ­ stopek)
     */
    private static int vynulovani_stopek = 0;
    /**
     * DefaultnĂ­ barva dig. hodin
     */
    public static Color barva_hodin = Color.GREEN;
    /**
     * DefaultnĂ­ barva stopek
     */
    public static Color barva_stopek = Color.BLUE;
    /**
     * Barva mÄ›nÄ›nĂ˝ch hodnot
     */
    private final Color barva_hodnot = Color.RED;

    /**
     * Reprezentuje s
     */
    public static int s = 0;
    /**
     * Reprezentuje s'
     */
    public static int s2 = 0;
    /**
     * Reprezentuje m
     */
    public static int m = 0;
    /**
     * Reprezentuje m'
     */
    public static int m2 = 0;
    /**
     * Reprezentuje h
     */
    public static int h = 0;
    /**
     * Reprezentuje h'
     */
    public static int h2 = 0;

    /**
     * Stav zda bylo stisknuto tlacitko 0 = ne, 1 = ano
     */
    public static int startPressed = 0;

    /**
     * Stav zda bylo stisknuto tlacitko 0 = ne, 1 = ano
     */
    public static int stopPressed = 0;

    /**
     * Odpocitava po jedne sekunde (spusti se po sekunde)
     */
    public static int casovac = 0;

    /**
     * Konstruktor HlavnĂ­ tĹ™Ă­dy, nadefinovĂˇnĂ­ vzhledu okna a bÄ›ĹľĂ­ zde timer pro
     * dig. hodiny
     */
    public Hlavni() {

        this.setTitle("Stopky");
        this.add(levaTlacitka(), BorderLayout.WEST);
        this.add(pravaTlacitka(), BorderLayout.EAST);
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
                // .getTime().getTime() je tu proto aby mi to vrĂˇtilo milisekundy
                // jenom .getTime() vrĂˇtĂ­ ÄŤas ve formĂˇtu Time (mĹŻj getter ze tĹ™Ă­dy Stopky)
                casovac += 10;
                konecnyAutomat(pocet_kliku, stopky);
                stopky.setTime(new Time(getTimeInMilisec()));
                //aby se ÄŤas pĹ™ekresoval tam kde mĂˇ (lze poznat dle konzole Ĺľe to funguje)
                if (stopky.getMode() == 0) {
                    stopky.repaint();
                }
            }
        }, 10, 10);
    }

    /**
     * LevĂ˝ panel se tlaÄŤĂ­tkem Mode
     *
     * @return Panel s tlaÄŤĂ­tkem Mode
     */
    final Component levaTlacitka() {

        JPanel tlacitka = new JPanel();
        tlacitka.setPreferredSize(new Dimension(80, 50));
        tlacitka.setBackground(Color.BLACK);

        JButton mode = new JButton(new Mode());
        mode.setPreferredSize(new Dimension(70, 30));
        tlacitka.add(mode);

        return tlacitka;
    }

    /**
     * PravĂ˝ panel s tlaÄŤĂ­tky Start a Stop
     *
     * @return Panel s tlaÄŤĂ­tky Start a Stop
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
     * SlouĹľĂ­ k pĹ™epĂ­nĂˇnĂ­ mezi stavy aplikace (mÄ›nĂ­ se barviÄŤky, pocet_kliku -
     * pro tlaÄŤĂ­tka Start/Stop)
     *
     * @author Milan RĹŻĹľiÄŤka
     */
    class Mode extends AbstractAction {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (pocet_kliku) {
                case 0:
                    stopky.setBarva(barva_hodnot);
                    stopky.setIndex(2);
                    stopky.repaint();
                    pocet_kliku++;
                    break;
                case 1:
                    stopky.setBarva(barva_hodnot);
                    stopky.setIndex(1);
                    stopky.repaint();
                    pocet_kliku++;
                    break;
                case 2:
                    stopky.setBarva(barva_hodnot);
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
                    stopky.setBarva(barva_hodin);
                    stopky.setIndex(0);
                    stopky.repaint();
                    pocet_kliku = 0;
                    break;
            }
        }

        public Mode() {
            putValue(NAME, "Mode");
            putValue(SHORT_DESCRIPTION, "PĹ™epnutĂ­ reĹľimu stopek (ALT+M)");
            putValue(MNEMONIC_KEY, KeyEvent.VK_M);
        }
    }

    /**
     * Posle zpravu ze bylo stisknuto tlacitko
     *
     * @author Milan RĹŻĹľiÄŤka, Nikolas Diesl
     */
    class Start extends AbstractAction {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            startPressed = 1;
        }

        public Start() {
            putValue(NAME, "Start");
            putValue(SHORT_DESCRIPTION, "ZmÄ›na hodnoty o +1, nebo spuĹˇtÄ›nĂ­ stopek (ALT+A)");
            putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        }
    }

    /**
     * Posle zpravu, ze bylo toto tlacitko stiknuto
     *
     * @author Milan RĹŻĹľiÄŤka, Nikolas Diesl
     */
    class Stop extends AbstractAction {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            stopPressed = 1;
        }

        public Stop() {
            putValue(NAME, "Stop");
            putValue(SHORT_DESCRIPTION, "ZmÄ›na hodnoty o -1, nebo zastavenĂ­ stopek (ALT+S)");
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        }
    }

    /**
     * Vrati stav vynulovani
     * @return pozadavek na vynulovani stopek 0 = ne, 1 = ano
     */
    private static int getVynulovani() {
        return vynulovani_stopek;
    }
    
    /**
     * Nastavi pozadavek na vynulovani
     * @param val  muze byt 0/1 (0 = ne, 1 = ano)
     */
    private static void setVynulovani(int val) {
        vynulovani_stopek = val;
    }

    /**
     * VracĂ­ sekundy
     * @return sekundy
     *
     * @author Nikolas Diesl
     */
    private static int getSec() {
        return (s2 * 10) + s;
    }

    /**
     * VracĂ­ minuty
     * @return minuty
     *
     * @author Nikolas Diesl
     */
    private static int getMin() {
        return (m2 * 10) + m;
    }

    /**
     * VracĂ­ hodiny
     * @return hodiny
     *
     * @author Nikolas Diesl
     */
    private static int getHod() {
        return (h2 * 10) + h;
    }

    /**
     * VracĂ­ milisekundy
     * @return cas v milisekundach
     * @author Nikolas Diesl
     */
    private static int getTimeInMilisec() {
        return (-3600000 + ((getHod() * 3600000) + (getMin() * 60000) + (getSec() * 1000)));
    }

    /**
     * Metoda ktera ridi konecny automat, reaguje kazdych 10 milisekund. Kdyz
     * mine 1000 milisekund tak spusti update s++ a pote se zepta zda bylo
     * stisknuto nejake tlacitko pokud ano udela podle toho pokyn a cely dej se
     * opakuje znovu (update teto metody je nastaven na 10ms)
     *
     * @param pocet_kliku pocet kliku znamena ve kterem stavu (modu) se hodiny nachazeji
     * @param stopky predava stopky 
     *
     * @author Nikolas Diesl
     */
    public static void konecnyAutomat(int pocet_kliku, final Stopky stopky) {

        // HODINY
        if (casovac >= 1000) {
            s++;
            casovac = 0;
        }

        // KONTROLA CASU
        if (s >= 10) {
            s = 0;
            s2++;
            if (s2 >= 6) {
                s2 = 0;
                m++;
                if (m >= 10) {
                    m = 0;
                    m2++;
                    if (m2 >= 6) {
                        m2 = 0;
                        h++;
                        if (h >= 10) {
                            h = 0;
                            h2++;
                        }
                        if (h2 >= 2 && h >= 4) {
                            h2 = 0;
                            h = 0;
                        }
                    }
                }
            }
        }

        // STAV DEF
        if (pocet_kliku == 0) {
            // nachazime se v defaultnim modu
        }
        // STAV SEC
        if (pocet_kliku == 1) {
            // START
            if (startPressed == 1) {
                s++;
                stopky.repaint();
                startPressed = 0;
            }
            // MODE
            if (stopPressed == 1) {
                s--;
                stopky.repaint();
                stopPressed = 0;
            }
        }
        // STAV MIN
        if (pocet_kliku == 2) {
            // START
            if (startPressed == 1) {
                m++;
                startPressed = 0;
            }
            // MODE
            if (stopPressed == 1) {
                m--;
                stopky.repaint();
                stopPressed = 0;
            }
        }
        // STAV HOD
        if (pocet_kliku == 3) {
            // START
            if (startPressed == 1) {
                h++;
                stopky.repaint();
                startPressed = 0;
            }
            // MODE
            if (stopPressed == 1) {
                h--;
                stopky.repaint();
                stopPressed = 0;
            }
        }
        // STAV ST
        if (pocet_kliku == 4) {
            // START
            if (startPressed == 1) {
                if (getVynulovani() == 0) {
                    setVynulovani(1);
                    timer2 = new Timer();
                    timer2.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            stopky.setStopky(new Time(stopky.getStopky().getTime() + 1000));
                            if (stopky.getMode() == 1) {
                                stopky.repaint();
                            }
                        }
                    }, 1000, 1000);
                }
                startPressed = 0;
            }
            // MODE
            if (stopPressed == 1) {
                if (getVynulovani() == 1) {
                    timer2.cancel();
                    setVynulovani(0);
                } else if (getVynulovani() == 0) {
                    stopky.setStopky(new Time(-3600000)); // nastavi cas 00:00:00
                    stopky.repaint();
                }
                stopPressed = 0;
            }
        }
    }

    /**
     *
     * Vytvori okno okno a spusti KA
     *
     * @param args pole stringu (vstupni retezce)
     */
    public static void main(String[] args) {
        Hlavni hlavni = new Hlavni();
    }
}
