/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuatrolinea.controlador;

import com.cuatrolinea.controlador.util.JsfUtil;
import com.cuatrolinea.modelo.Usuario;
import com.cuatrolinea.modelo.grafo.Arista;
import com.cuatrolinea.modelo.grafo.Ficha;
import com.cuatrolinea.modelo.grafo.Grafo;
import com.cuatrolinea.modelo.grafo.Vertice;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

/**
 *
 * @author carloaiza
 */
@Named(value = "damasAppBean")
@ApplicationScoped
public class CuatroLineaAppBean {

    //private int matriz [][]= new int [7][6];
    //LHC INICIO
    private int matriz[][] = new int[6][7];
    private boolean turno = true;
    //LHC FIN
    private int altoFichas = 4;
    private int altoIntermedio = 7;
    private byte distancia = 10;
    private DefaultDiagramModel model;
    private Grafo tablero = new Grafo();
    private String fichaClick = "";
    private String textoGanador = "";
    private boolean cambio = false;
    private boolean estadoJuego = false;

    private Date fechaSistema;

    public CuatroLineaAppBean() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    public Date getFechaSistema() {
        return new Date();
    }

    public void setFechaSistema(Date fechaSistema) {
        this.fechaSistema = fechaSistema;
    }

    public String getFichaClick() {
        return fichaClick;
    }

    public void setFichaClick(String fichaClick) {
        this.fichaClick = fichaClick;
    }

    public boolean isEstadoJuego() {
        return estadoJuego;
    }

    public void setEstadoJuego(boolean estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    /* public void llenarAristas() {
        //Crear aristas
       int limite =(altoFichas + altoIntermedio / 2);
       int limiteSup=limite+1;
        for (Vertice vert : tablero.getVertices()) {
            if (vert.getFicha().getNivel() <= limite) {
                tablero.adicionarArista(vert.getId(), vert.getId() + vert.getFicha().getNivel(), 1);
                tablero.adicionarArista(vert.getId(), vert.getId() + vert.getFicha().getNivel() + 1, 1);
               if(vert.getId() < calcularRegresion(vert.getFicha().getNivel()))
                {
                    tablero.adicionarArista(vert.getId(), vert.getId() + 1, 2);
                }
            }
            else if (vert.getFicha().getNivel() >= limiteSup){
                if(vert.getId() < calcularRegresion(vert.getFicha().getNivel())-
                        ((vert.getFicha().getNivel() - 
                        limiteSup + 1)*(vert.getFicha().getNivel()-limiteSup)))
                {
                    int vertice=(vert.getId() + vert.getFicha().getNivel())-
                            ((vert.getFicha().getNivel() - 
                            limiteSup)+(vert.getFicha().getNivel()-limiteSup));
                    tablero.adicionarArista(vert.getId(), vertice, 1);
                    tablero.adicionarArista(vert.getId()+1, vertice, 1);
                    tablero.adicionarArista(vert.getId(), vert.getId() + 1, 2);                    
                }               
            }
             
            
        }
        
    }
    
    private int calcularRegresion(int num){
        int suma=0;
        for(int i=1; i<= num;i++)
        {
            suma=suma+i;
        }            
        return suma;
    }
    

    @PostConstruct
    public void pintarTablero() {

        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        model.setConnectionsDetachable(false);

        int x = 35;
        int y = 0;
        String color = "Rojo";
        String styleColor = "ui-diagram-element-ficha";
        int contNivel=0;
        for (int i = 0; i <= (altoFichas + altoIntermedio / 2) + 1; i++) {
            x = 35 - (i * 2);
            if (i > altoFichas) {
                color = "blanca";
                styleColor = "ui-diagram-element-ficha-blanca";
            }

            for (int j = 1; j <= i; j++) {
                Ficha ficha=new Ficha(color, i);
                tablero.adicionarVertice(ficha);
                //Element ceo = new Element(ficha, x + "em", y + "em");
                Element ceo = new Element(tablero.getVertices().size(), x + "em", y + "em");
                ceo.setId(String.valueOf(tablero.getVertices().size()));
                ceo.setDraggable(false);
                ceo.setStyleClass(styleColor);
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
                model.addElement(ceo);
                x = x + 4;
            }
            y = y + 3;
            contNivel++;
        }
        
        for (int i = (altoFichas + altoIntermedio / 2); i >= 1; i--) {
            x = 35 - (i * 2);
            if (i <= altoFichas) {
                color = "azul";
                styleColor = "ui-diagram-element-ficha-azul";
            }

            for (int j = 1; j <= i; j++) {
                Ficha ficha=new Ficha(color, contNivel);
                tablero.adicionarVertice(ficha);                                
                Element ceo = new Element(tablero.getVertices().size(), x + "em", y + "em");
                ceo.setId(String.valueOf(tablero.getVertices().size()));
                ceo.setDraggable(false);
                ceo.setStyleClass(styleColor);
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
                model.addElement(ceo);
                x = x + 4;
            }
            y = y + 3;
            contNivel++;
        }
        llenarAristas();

        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:3}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
        model.setDefaultConnector(connector);
        //recorrer aristas
        for (Arista arista : tablero.getAristas()) {
            Element origen = model.getElements().get(arista.getOrigen() - 1);
            Element destino = model.getElements().get(arista.getDestino() - 1);
            switch (arista.getPeso()) {
                case 1:
                    model.connect(new Connection(origen.getEndPoints().get(0), destino.getEndPoints().get(1)));
                    break;
                case 2:
                    
                    model.connect(new Connection(origen.getEndPoints().get(3), destino.getEndPoints().get(2)));
                    break;
            }

        }
    }
     */
    @PostConstruct
    public void pintarTablero() {

        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        model.setConnectionsDetachable(false);

        int x = 35;
        int y = 0;
        String color = "";
        String styleColor = "";
        int contNivel = 0;
        //for (int i = 0; i <= (altoFichas + altoIntermedio / 2) + 1; i++) {
        for (int i = 0; i <= 5; i++) {
            x = 35 - (6 * 2);
            //x = 0;
            color = "blanca";
            styleColor = "ui-diagram-element-ficha-blanca";

            // ancho de tablero
            for (int j = 1; j <= 7; j++) {
                Ficha ficha = new Ficha(color, i);
                tablero.adicionarVertice(ficha);
                //Element ceo = new Element(ficha, x + "em", y + "em");
                Element ceo = new Element(tablero.getVertices().size(), x + "em", y + "em");
                ceo.setId(String.valueOf(tablero.getVertices().size()));
                ceo.setDraggable(false);
                ceo.setStyleClass(styleColor);
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
                model.addElement(ceo);
                x = x + 4;
            }
            y = y + 3;
            contNivel++;
        }

    }

    public int getAltoFichas() {
        return altoFichas;
    }

    public void setAltoFichas(int altoFichas) {
        this.altoFichas = altoFichas;
    }

    public int getAltoIntermedio() {
        return altoIntermedio;
    }

    public void setAltoIntermedio(int altoIntermedio) {
        this.altoIntermedio = altoIntermedio;
    }

    public byte getDistancia() {
        return distancia;
    }

    public void setDistancia(byte distancia) {
        this.distancia = distancia;
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    public void simularJugada() {
        //Element elem1=model.getElements().get(8-1);

        //Element elem2=model.getElements().get(12-1);
        //elem1.setStyleClass("ui-diagram-element-ficha-blanca");
        //elem2.setStyleClass("ui-diagram-element-ficha");
    }

    private int p(int y, int x) {
        int z = (y < 0 || x < 0 || y >= 6 || x >= 7) ? 0 : matriz[y][x];//;
        return z;
    }

    private int getWinner() {  //loops through rows, columns, diagonals, etc
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                if (p(y, x) != 0 && p(y, x) == p(y, x + 1) && p(y, x) == p(y, x + 2) && p(y, x) == p(y, x + 3)) {
                    return p(y, x);
                }
            }
        }
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                if (p(y, x) != 0 && p(y, x) == p(y + 1, x) && p(y, x) == p(y + 2, x) && p(y, x) == p(y + 3, x)) {
                    return p(y, x);
                }
            }
        }
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                for (int d = -1; d <= 1; d += 2) {
                    if (p(y, x) != 0 && p(y, x) == p(y + 1 * d, x + 1) && p(y, x) == p(y + 2 * d, x + 2) && p(y, x) == p(y + 3 * d, x + 3)) {
                        return p(y, x);
                    }
                }
            }
        }
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                if (p(y, x) == 0) {
                    return 0;
                }
            }
        }
        return -1; //tie
    }
    
    public void revisarGanador()
    {    
        switch(getWinner()){
            case 0:
                break;              
            case 1:
                model.clear();
                JsfUtil.addSuccessMessage("Jugador 1 Gana");
                break;
                
            case 2:
                //Aca ponga l oque va a pasar cuando el jugadaor 2 gana
                model.clear();
                JsfUtil.addSuccessMessage("Jugador 2 Gana");
                break;
                
            case -1:
                //aca ponga cuando empatan
                model.clear();
                JsfUtil.addSuccessMessage("Empate");
                break;

        }
        
    }
    
    public void cambiarTexto(String texto)
    {
        this.textoGanador = texto;
    }
    
    private boolean verificarJugador1()
    {
        if(matriz[5][0]==1 && matriz[4][0]==1 && matriz[3][0]==1 && matriz[5][1]==1 && matriz[3][1]==1 
                && matriz[5][2]==1 && matriz[4][2]==1 && matriz[3][2]==1 && matriz[5][3]==1 && matriz[5][4]==1 
                && matriz[4][4]==1 && matriz[3][4]==1 && matriz[5][5]==1 && matriz[3][5]==1 &&matriz[5][6]==1 
                && matriz[4][6]==1 && matriz[3][6]==1)return true;
        
        if(matriz[4][0]==1 && matriz[3][0]==1 && matriz[2][0]==1 && matriz[4][1]==1 && matriz[2][1]==1 
                && matriz[4][2]==1 && matriz[3][2]==1 && matriz[2][2]==1 && matriz[4][3]==1 && matriz[4][4]==1 
                && matriz[3][4]==1 && matriz[2][4]==1 && matriz[4][5]==1 && matriz[2][5]==1 &&matriz[4][6]==1 
                && matriz[3][6]==1 && matriz[2][6]==1)return true;
        
        if(matriz[3][0]==1 && matriz[2][0]==1 && matriz[1][0]==1 && matriz[3][1]==1 && matriz[1][1]==1 
                && matriz[3][2]==1 && matriz[2][2]==1 && matriz[1][2]==1 && matriz[3][3]==1 && matriz[3][4]==1 
                && matriz[2][4]==1 && matriz[1][4]==1 && matriz[3][5]==1 && matriz[1][5]==1 &&matriz[3][6]==1 
                && matriz[2][6]==1 && matriz[1][6]==1)return true;
        
       
        if(matriz[2][0]==1 && matriz[1][0]==1 && matriz[0][0]==1 && matriz[2][1]==1 && matriz[0][1]==1 
                && matriz[2][2]==1 && matriz[1][2]==1 && matriz[0][2]==1 && matriz[2][3]==1 && matriz[2][4]==1 
                && matriz[1][4]==1 && matriz[0][4]==1 && matriz[2][5]==1 && matriz[0][5]==1 &&matriz[2][6]==1 
                && matriz[1][6]==1 && matriz[0][6]==1)return true;
        
        return false;
    }
    
    private boolean verificarJugador2()
    {
        if(matriz[5][0]==2 && matriz[4][0]==2 && matriz[3][0]==2 && matriz[5][1]==2 && matriz[3][1]==2 
                && matriz[5][2]==2 && matriz[4][2]==2 && matriz[3][2]==2 && matriz[5][3]==2 && matriz[5][4]==2 
                && matriz[4][4]==2 && matriz[3][4]==2 && matriz[5][5]==2 && matriz[3][5]==2 &&matriz[5][6]==2 
                && matriz[4][6]==2 && matriz[3][6]==2)return true;
        
        if(matriz[4][0]==2 && matriz[3][0]==2 && matriz[2][0]==2 && matriz[4][1]==2 && matriz[2][1]==2 
                && matriz[4][2]==2 && matriz[3][2]==2 && matriz[2][2]==2 && matriz[4][3]==2 && matriz[4][4]==2 
                && matriz[3][4]==2 && matriz[2][4]==2 && matriz[4][5]==2 && matriz[2][5]==2 &&matriz[4][6]==2 
                && matriz[3][6]==2 && matriz[2][6]==2)return true;
        
        if(matriz[3][0]==2 && matriz[2][0]==2 && matriz[1][0]==2 && matriz[3][1]==2 && matriz[1][1]==2 
                && matriz[3][2]==2 && matriz[2][2]==2 && matriz[1][2]==2 && matriz[3][3]==2 && matriz[3][4]==2 
                && matriz[2][4]==2 && matriz[1][4]==2 && matriz[3][5]==2 && matriz[1][5]==2 &&matriz[3][6]==2 
                && matriz[2][6]==2 && matriz[1][6]==2)return true;
        
       
        if(matriz[2][0]==2 && matriz[1][0]==2 && matriz[0][0]==2 && matriz[2][1]==2 && matriz[0][1]==2 
                && matriz[2][2]==2 && matriz[1][2]==2 && matriz[0][2]==2 && matriz[2][3]==2 && matriz[2][4]==2 
                && matriz[1][4]==2 && matriz[0][4]==2 && matriz[2][5]==2 && matriz[0][5]==2 &&matriz[2][6]==2 
                && matriz[1][6]==2 && matriz[0][6]==2)return true;
        
        return false;
    }
    
    public void mensajeGanador()
    {
        if(verificarJugador1())
        {
            JsfUtil.addSuccessMessage("Eso me digito Jugador 1");
        }else if(verificarJugador2())
        {
            JsfUtil.addSuccessMessage("Eso me digito Jugador 2");
        }
        
    }

    public void jugadaUno() {
        int indice = 5;
        int num = 35;
        turno = true;
        while (true) {
            if (matriz[indice][0] == 0) {
                //TURNO = TRUE / COLOR ROJO / JUGADOR 1
                if (turno == true) {
                    matriz[indice][0] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                    //TURNO = FALSE / COLOR AZUL / JUGADOR 2
                } else {
                    matriz[indice][0] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
        mensajeGanador();
    }
    
    public void jugadaDos() {
        int indice = 5;
        int num = 36;
        turno = true;
        while (true) {
            if (matriz[indice][1] == 0) {
                
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][1] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][1] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
        mensajeGanador();

        
    }

    public void jugadaTres() {
        int indice = 5;
        int num = 37;
        turno = true;
        while (true) {
            if (matriz[indice][2] == 0) {
                
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][2] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][2] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
    }

    public void jugadaCuatro() {
        int indice = 5;
        int num = 38;
        turno = true;
        while (true) {
            if (matriz[indice][3] == 0) {
                
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][3] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][3] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;
        }
        mensajeGanador();
    }

    public void jugadaCinco() {
        int indice = 5;
        int num = 39;
        turno = true;
        while (true) {
            if (matriz[indice][4] == 0) {
                
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][4] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][4] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;
        }
        mensajeGanador();
        
    }

    public void jugadaSeis() {
        int indice = 5;
        int num = 40;
        turno = true;
        while (true) {
            if (matriz[indice][5] == 0) {
                
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][5] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][5] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
        mensajeGanador();
    }

    public void jugadaSiete() {
        int indice = 5;
        int num = 41;
        turno = true;
        while (true) {
            if (matriz[indice][6] == 0) {
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][6] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][6] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
        mensajeGanador();
    }
    //JUGADOR 2 
    public void jugadaUno2() {
        int indice = 5;
        int num = 35;
        turno =  false;
        while (true) {
            if (matriz[indice][0] == 0) {
                //TURNO = TRUE / COLOR ROJO / JUGADOR 1
                if (turno == true) {
                    matriz[indice][0] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                    //TURNO = FALSE / COLOR AZUL / JUGADOR 2
                } else {
                    matriz[indice][0] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
        mensajeGanador();
    }
 
    public void jugadaDos2() {
        int indice = 5;
        int num = 36;
        turno =  false;
        while (true) {
            if (matriz[indice][1] == 0) {
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][1] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][1] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
        mensajeGanador();

        
    }

    public void jugadaTres2() {
        int indice = 5;
        int num = 37;
        turno =  false;
        while (true) {
            if (matriz[indice][2] == 0) {
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][2] = 1;                
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][2] = 2; 
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
    }
    
    public void jugadaCuatro2() {
        int indice = 5;
        int num = 38;
        turno = false;
        while (true) {
            if (matriz[indice][3] == 0) {
                
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][3] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][3] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;
        }
        mensajeGanador();
    }

    public void jugadaCinco2() {
        int indice = 5;
        int num = 39;
        turno = false;
        while (true) {
            if (matriz[indice][4] == 0) {
                
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][4] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][4] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;
        }
        mensajeGanador();
        
    }

    public void jugadaSeis2() {
        int indice = 5;
        int num = 40;
        turno = false;
        while (true) {
            if (matriz[indice][5] == 0) {
                
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][5] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][5] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
        mensajeGanador();
    }

    public void jugadaSiete2() {
        int indice = 5;
        int num = 41;
        turno = false;
        while (true) {
            if (matriz[indice][6] == 0) {
                //String valor = num+"-1";
                if (turno == true) {
                    matriz[indice][6] = 1;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha");
                    turno = false;
                    break;
                } else {
                    matriz[indice][6] = 2;
                    Element elem1 = model.getElements().get(num);
                    elem1.setStyleClass("ui-diagram-element-ficha-azul");
                    turno = true;
                    break;
                }
            }
            num -= 7;
            indice--;

        }
        mensajeGanador();
    }
    
    

    public void onClickRight() {
        String id = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("elementId");

        fichaClick = id.replaceAll("frmTablero:tablero-", "");;

    }

    public void pruebaMenu() {
        JsfUtil.addSuccessMessage(fichaClick + " presionada");
    }

    public void activarJuego() {
        estadoJuego = true;
        JsfUtil.addSuccessMessage("Se ha habilitado el juego");
    }

    /**
     * @return the textoGanador
     */
    public String getTextoGanador() {
        return textoGanador;
    }

    /**
     * @param textoGanador the textoGanador to set
     */
    public void setTextoGanador(String textoGanador) {
        this.textoGanador = textoGanador;
    }

    /**
     * @return the cambio
     */
    public boolean isCambio() {
        return cambio;
    }

    /**
     * @param cambio the cambio to set
     */
    public void setCambio(boolean cambio) {
        this.cambio = cambio;
    }

}
