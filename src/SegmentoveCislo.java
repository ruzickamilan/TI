/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;

/**
 * Třída SegmentoveCislo přijme do konstruktoru cas ve stringu, grafický kontext a barvu/index dané části
 * módu dig. hodinek a vykreslí na předaný grafický kontext čas v sedmisegmentovém tvaru
 * 
 * @author Milan Růžička
 */
public final class SegmentoveCislo {
    /** Čas, který se bude vykreslovat */
    private final String cas;
    /** Barva písma dig. hodin */
    private Color barva_pisma;

    /**
     * Základní konstruktor této třídy, rozseká čas na jednotlivé pozice(hh:mm:ss) a zavolá si metodu 
     * vykresli pro vykreslení dané části času
     * 
     * @param cas Celý čas ve stringu
     * @param g2 Grafický kontext
     * @param barva Barva části času (se kterou se manipuluje pomocí Start/Stop)
     * @param index Index části času (se kterou se manipuluje pomocí Start/Stop)
     */
    public SegmentoveCislo(String cas, Graphics2D g2, Color barva, int index) {
        this.cas = cas;
        String[] pole = this.cas.split(":");
        for (int i = 0; i < pole.length; i++) {
            int cislo = Integer.parseInt(pole[i]);
            vykresli(g2, cislo, i, barva, index);
        }
    }

    /**
     * Metoda která vykresluje postupně jednotlivé číslice času. Jsou zde nadefinovány všechny souřadnice
     * dílků sedmisegmentového písma a dle číslice se dílky vykreslují
     * 
     * @param g2 Grafický kontext
     * @param cislo Číslo které cheme vykreslit
     * @param posun Posun o který se posune číslo doprava (aby se čísla nepřekrývala a tvořila opravdu čas)
     */
    public void vykresliSegmentoveCislo(Graphics2D g2, int cislo, int posun) {
        int[] dilek1X = {10+posun,15+posun,30+posun,35+posun,30+posun,15+posun};
        int[] dilek1Y = {10,5,5,10,15,15};
        int[] dilek2X = {9+posun,4+posun,4+posun,9+posun,14+posun,14+posun};
        int[] dilek2Y = {11,16,31,36,31,16};
        int[] dilek3X = {36+posun,31+posun,31+posun,36+posun,41+posun,41+posun};
        int[] dilek3Y = {11,16,31,36,31,16};
        int[] dilek4X = {10+posun,15+posun,30+posun,35+posun,30+posun,15+posun};
        int[] dilek4Y = {37,32,32,37,42,42};
        int[] dilek5X = {9+posun,4+posun,4+posun,9+posun,14+posun,14+posun};
        int[] dilek5Y = {38,43,58,63,58,43};
        int[] dilek6X = {36+posun,31+posun,31+posun,36+posun,41+posun,41+posun};
        int[] dilek6Y = {38,43,58,63,58,43};
        int[] dilek7X = {10+posun,15+posun,30+posun,35+posun,30+posun,15+posun};
        int[] dilek7Y = {64,59,59,64,69,69};

        if (cislo == 0) {
            g2.fillPolygon(dilek1X, dilek1Y, 6);
            g2.fillPolygon(dilek2X, dilek2Y, 6);
            g2.fillPolygon(dilek3X, dilek3Y, 6);
            g2.fillPolygon(dilek5X, dilek5Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
            g2.fillPolygon(dilek7X, dilek7Y, 6);
        }
        if (cislo == 1) {
            g2.fillPolygon(dilek3X, dilek3Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
        }
        if (cislo == 2) {
            g2.fillPolygon(dilek1X, dilek1Y, 6);
            g2.fillPolygon(dilek3X, dilek3Y, 6);
            g2.fillPolygon(dilek4X, dilek4Y, 6);
            g2.fillPolygon(dilek5X, dilek5Y, 6);
            g2.fillPolygon(dilek7X, dilek7Y, 6);
        }
        if (cislo == 3) {
            g2.fillPolygon(dilek1X, dilek1Y, 6);
            g2.fillPolygon(dilek3X, dilek3Y, 6);
            g2.fillPolygon(dilek4X, dilek4Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
            g2.fillPolygon(dilek7X, dilek7Y, 6);
        }
        if (cislo == 4) {
            g2.fillPolygon(dilek2X, dilek2Y, 6);
            g2.fillPolygon(dilek3X, dilek3Y, 6);
            g2.fillPolygon(dilek4X, dilek4Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
        }
        if (cislo == 5) {
            g2.fillPolygon(dilek1X, dilek1Y, 6);
            g2.fillPolygon(dilek2X, dilek2Y, 6);
            g2.fillPolygon(dilek4X, dilek4Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
            g2.fillPolygon(dilek7X, dilek7Y, 6);
        }
        if (cislo == 6) {
            g2.fillPolygon(dilek1X, dilek1Y, 6);
            g2.fillPolygon(dilek2X, dilek2Y, 6);
            g2.fillPolygon(dilek4X, dilek4Y, 6);
            g2.fillPolygon(dilek5X, dilek5Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
            g2.fillPolygon(dilek7X, dilek7Y, 6);
        }
        if (cislo == 7) {
            g2.fillPolygon(dilek1X, dilek1Y, 6);
            g2.fillPolygon(dilek3X, dilek3Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
        }
        if (cislo == 8) {
            g2.fillPolygon(dilek1X, dilek1Y, 6);
            g2.fillPolygon(dilek2X, dilek2Y, 6);
            g2.fillPolygon(dilek3X, dilek3Y, 6);
            g2.fillPolygon(dilek4X, dilek4Y, 6);
            g2.fillPolygon(dilek5X, dilek5Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
            g2.fillPolygon(dilek7X, dilek7Y, 6);
        }
        if (cislo == 9) {
            g2.fillPolygon(dilek1X, dilek1Y, 6);
            g2.fillPolygon(dilek2X, dilek2Y, 6);
            g2.fillPolygon(dilek3X, dilek3Y, 6);
            g2.fillPolygon(dilek4X, dilek4Y, 6);
            g2.fillPolygon(dilek6X, dilek6Y, 6);
            g2.fillPolygon(dilek7X, dilek7Y, 6);
        }
    }

    /**
     * Metoda která si volá o vykreslení jednotlivých číslic metodě vykresliSegmentovaciCislo
     * 
     * @param g2 Grafický kontext
     * @param cislo Požadované dvoučíslí z času které chceme vykreslit
     * @param pozice Pozice na které chceme dvoučíslí vykreslit (hh:mm:ss) - (0:1:2)
     * @param barva Barva části času (se kterou se manipuluje pomocí Start/Stop)
     * @param index Index části času (se kterou se manipuluje pomocí Start/Stop)
     */
    public void vykresli(Graphics2D g2, int cislo, int pozice, Color barva, int index) {
        if (index == 3)
            barva_pisma = Hlavni.barva_stopek;
        else
            barva_pisma = Hlavni.barva_hodin;

        g2.setColor(barva_pisma);

        String cislo_string = "" + cislo;
        int prvni_cislo = Integer.parseInt(cislo_string.substring(0, 1));
        int druhe_cislo;
        try {
            druhe_cislo = Integer.parseInt(cislo_string.substring(1, 2));
        }
        catch (Exception e) {
            prvni_cislo = 0;
            druhe_cislo = Integer.parseInt(cislo_string.substring(0, 1));
        }

        if (pozice == 0) {
            if (index == 0)
                g2.setColor(barva);
            vykresliSegmentoveCislo(g2, prvni_cislo, 0);
            vykresliSegmentoveCislo(g2, druhe_cislo, 40);
            g2.setColor(barva_pisma);
            g2.fillOval(85, 22, 10, 10);
            g2.fillOval(85, 42, 10, 10);
        }
        else if (pozice == 1) {
            if (index == 1)
                g2.setColor(barva);
            vykresliSegmentoveCislo(g2, prvni_cislo, 95);
            vykresliSegmentoveCislo(g2, druhe_cislo, 135);
            g2.setColor(barva_pisma);
            g2.fillOval(180, 22, 10, 10);
            g2.fillOval(180, 42, 10, 10);
        }
        else if (pozice == 2) {
            if (index == 2)
                g2.setColor(barva);
            vykresliSegmentoveCislo(g2, prvni_cislo, 190);
            vykresliSegmentoveCislo(g2, druhe_cislo, 230);
            g2.setColor(barva_pisma);
        }
    }
}
