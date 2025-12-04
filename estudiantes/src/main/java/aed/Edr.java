package aed;
import java.util.ArrayList;

public class Edr {

    /*
    COMENTARIO SOBRE CORRECCIÓN: 
        HeapMin se crea primero ya que es necesario para los Handles, y estos últimos se crean desde el método 
        de crearHandle del HeapMin 

        el HeapMin se crea vacío y se van encolando los Handles conforme se van creando 
    */

    private HeapMin.Handle[] estudiantes;
    private Examen examenCanonico;
    private HeapMin idPorNotas;
    private int ladoAula;
    private int cantEstudiantes;
    private int cantSospechosos;
    private int cantPreguntas;
    // Usamos muchos atributos ya que no nos dieron limite de almacenamiento, pero muchos se pueden sacar de otro lado


//-------------------------------------------------CREAR EDR-----------------------------------------------------------------------


    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico){


        this.ladoAula = LadoAula;                                                           // O(1)
        this.cantEstudiantes = Cant_estudiantes;                                            // O(1)
        this.cantSospechosos = 0;                                                           // O(1)
        this.cantPreguntas = ExamenCanonico.length;                                         // O(1)

        this.examenCanonico = new Examen(ExamenCanonico.length);                            // O(1)
        for (int p = 0; p < ExamenCanonico.length; p++) {                                   // O(R)
            this.examenCanonico.resolverPregunta(p, ExamenCanonico[p]);                     // O(1)
        }
       
        // cremos HeapMin vacío
        this.idPorNotas = new HeapMin(Cant_estudiantes); 

        // creamos los handles y vamos llenando el Heap
        this.estudiantes = new HeapMin.Handle[Cant_estudiantes];                            // O(E)
        for (int i = 0; i < Cant_estudiantes; i++){                                         // O(E)
            Estudiante est = new  Estudiante(i, ExamenCanonico.length, ladoAula);           // O(R)
            HeapMin.Handle h = idPorNotas.crearHandle(est);                                  // O(1)                        
            this.estudiantes[i] = h;                                                        // O(1)
            idPorNotas.encolar(h); 
        }
                
    } // Complejidad: O(E*R)


//-------------------------------------------------NOTAS--------------------------------------------------------------------------

    public double[] notas(){

        double[] res = new double[estudiantes.length];                                  // O(E)

        for (int i = 0; i < estudiantes.length; i++){                                   // O(E)
            res[i] = estudiantes[i].obtenerEstudiante().obtenerNota();                  // O(1)
        }
        return res;

    } // Complejidad: O(E)


//------------------------------------------------COPIARSE------------------------------------------------------------------------


    public void copiarse(int est){
        Estudiante[] vecinos = consguirVecinos(est);                                                                        // O(1)
        Estudiante mejorVecino = mejorVecinoParaCopiarse(estudiantes[est].obtenerEstudiante(), vecinos);                    // O(R)
        for (int i = 0; i < cantPreguntas; i++){                                                                            // O(R)
            if (estudiantes[est].obtenerEstudiante().obtenerExamen().obtenerRespuesta(i) == -1 && 
                    mejorVecino.obtenerExamen().obtenerRespuesta(i) != -1){                                                 // O(1)

                resolver(est, i, mejorVecino.obtenerExamen().obtenerRespuesta(i));                                          // O(log E)
                break;                                                                                                      // O(1)
            }
        }
    } // Complejidad: O(R + log E)


    public Estudiante[] consguirVecinos(int est) { //consigue todos los vecinos del estudiante de los cuales se pueda copiar
        int estPorFila = (ladoAula + 1) / 2;                                                                                                    // O(1)
        ArrayList<Estudiante> vecinos = new ArrayList<>();                                                                                      // O(1)
        if (est + 1 < estudiantes.length && estaEnRango(estudiantes[est].obtenerEstudiante(), estudiantes[est + 1].obtenerEstudiante()) &&      
                estudiantes[est+1].obtenerEstudiante().obtenerEstadoEntrega()==false) {  //vecino a la derecha                                  // O(1)

            vecinos.add(estudiantes[est + 1].obtenerEstudiante());                                                                              // O(1)
        }                                                                                                                                       // O(1)
        if (est - 1 >= 0 && estaEnRango(estudiantes[est].obtenerEstudiante(), estudiantes[est - 1].obtenerEstudiante()) &&                      
                estudiantes[est-1].obtenerEstudiante().obtenerEstadoEntrega()==false) {  //vecino a la izquierda                                // O(1)

            vecinos.add(estudiantes[est - 1].obtenerEstudiante());                                                                              // O(1)
        }
        if (est - estPorFila >= 0 && estaEnRango(estudiantes[est].obtenerEstudiante(), estudiantes[est - estPorFila].obtenerEstudiante()) &&    
                estudiantes[est-estPorFila].obtenerEstudiante().obtenerEstadoEntrega()==false) { //vecino adelante                              // O(1)

            vecinos.add(estudiantes[est - estPorFila].obtenerEstudiante());                                                                     // O(1)
        }
        return vecinos.toArray(new Estudiante[0]);                                                                                              // O(1)
    } // Complejidad: O(1)


    public Estudiante mejorVecinoParaCopiarse(Estudiante est, Estudiante[] vecinos){ //decide cual de todos los vecinos es el mejor para copiarse segun el criterio del enunciado
        if (vecinos.length == 0) {                                                                                          // O(1)
            return est;                                                                                                     // O(1)
        }
        Estudiante res = null;                                                                                              // O(1)
        int mayor = -1;                                                                                                     // O(1)
        for (int i = 0; i < vecinos.length; i++){   // Como maximo son 3 vecinos                                            // O(1)
            int contador = 0;                                                                                               // O(1)
            for (int j = 0; j < cantPreguntas; j++){                                                                        // O(R)
                if (est.tienePreguntaSinResponder(j) && !vecinos[i].tienePreguntaSinResponder(j)){                          // O(1)
                    contador++;                                                                                             // O(1)
                }
            }
            if (contador > mayor || (contador == mayor && (res == null || vecinos[i].obtenerId() > res.obtenerId()))) {     // O(1)
                mayor = contador;                                                                                           // O(1)
                res = vecinos[i];                                                                                           // O(1)
            }
        }
        return res;
    } // Complejidad: O(R)


    public boolean estaEnRango(Estudiante est, Estudiante vecino){   // chequea si el vecino esta en un rango adentro de las posibilidades de las dimensiones del aula
        if (est.obtenerFila() - 1 == vecino.obtenerFila() && est.obtenerColumna() == vecino.obtenerColumna()) {         // O(1)
            return true;
        }
        if (est.obtenerFila() == vecino.obtenerFila() && est.obtenerColumna() == vecino.obtenerColumna() + 2) {         // O(1)
            return true;
        }
        if (est.obtenerFila() == vecino.obtenerFila() && est.obtenerColumna() == vecino.obtenerColumna() - 2) {         // O(1)
            return true;
        }
        return false;
    } // Complejidad: O(1)


//-----------------------------------------------RESOLVER----------------------------------------------------------------

    
    public void resolver(int estudiante, int NroEjercicio, int res) {
        Estudiante est = this.estudiantes[estudiante].obtenerEstudiante();                  // O(1)
        int respuestaCorrecta = this.examenCanonico.obtenerRespuesta(NroEjercicio);         // O(1)
        int totalPreguntas = examenCanonico.cantidadPreguntas();                            // O(1)

        est.resolverPregunta(NroEjercicio, res, respuestaCorrecta, totalPreguntas);         // O(1)
        
        this.idPorNotas.actualizar(this.estudiantes[estudiante]);            // O(log E)  Actualizo el heap con el handel de l estudiante que ya tuvo actualizada su nota 
    } // Complejidad: O(log E)


//------------------------------------------------CONSULTAR DARK WEB-------------------------------------------------------


    public void consultarDarkWeb(int k, int[] examenDW) {
        int i = 0;
        HeapMin.Handle[] restaurar = new HeapMin.Handle[k];     //  O(K)
        // desencolar los k peores
        while(i < k) {                                   // se realiza el bucle K-veces
            restaurar[i] = idPorNotas.desencolarHandle();     // O(1) + O(log (E))
            i++;
        }

        // cambiarle el examen a los k en restaurar
        for (int j = 0; j < restaurar.length; j++){                        // O(K)
            restaurar[j].obtenerEstudiante().cambiarExamenCompleto(examenDW, examenCanonico);  // O(R)  
            idPorNotas.encolar(restaurar[j]);     // O(log(E))
        }
    } // O(K)+O(K*(1+log(E))+O(k*(R+log(E)) = O(k*(R+log(E))


//-------------------------------------------------ENTREGAR-------------------------------------------------------------
    

    public void entregar(int estudiante) {
        estudiantes[estudiante].obtenerEstudiante().marcarEntregado();          // O(1)
        this.idPorNotas.actualizar(estudiantes[estudiante]);     // O(log (E))
    } // Complejidad O(log E)


//-----------------------------------------------------CORREGIR---------------------------------------------------------


public NotaFinal[] corregir() {
        
        // lleno el array de res con notasfinal de estudiantes, 
        // ordenadas de menor a mayor y desempatadas por menor id
        NotaFinal[]  res = notasFinalesEnOrdenInverso();                // O(E*log(E))

        // doy vuelta el array de res para que me quede en el orden correcto,
        // notas ordenadas de mayor a menor, desmpatada por mayor id
        NotaFinal[] resOrdenCorecto = invertirOrden(res);               // O(E)
        
        // saco del array de notaFinal, los estudiantes que se copiaron
        NotaFinal[] resOrdenCorectoSinCopiones = sacarSospechosos(resOrdenCorecto); // O(E), en el peor caso (ninguno se copio)
     
        // restauro idspornota
        restuararIdPorNotasCompleto();                                  // O(E*log(E))

        // devuelvo las notas de los examenes de los estudiantes que 
        //no se hayan copiado ordenada por NotaFinal.nota de forma
        //decreciente. En caso de empate, se desempata por mayor 
        //NotaFinal.id de estudiante
        return resOrdenCorectoSinCopiones;                             // - o(1)
    }


    private NotaFinal[] notasFinalesEnOrdenInverso(){                   // O(E*log(E))

        // lleno el array de res con notasfinal de estudiantes, ordenadas de menor a mayor y desempatadas por menor id

        NotaFinal[] res = new NotaFinal[(this.cantEstudiantes)];                // - O(E)
        int i = 0;                                                      // - O(1)

        while(i<this.cantEstudiantes){                                          // repito el cuerpo E-veces - O(E*(cuerpo))
            
            Estudiante est  = idPorNotas.desencolar();                           // desencolo ids por nota al id con menor nota - O(log(E)) como maximo.
                                                                                // obtengo el objeto de clase Estudiante corespondiente al id - O(1)

            NotaFinal notaFinalEst = new NotaFinal(est.obtenerNota(), est.obtenerId());   // creo la nota final del estudiante con sus datos de sus atributos - O(1)

            res[i] = notaFinalEst;                                      // se agrega la notaFinal del estudiante con menor nota en la posicion 
                                                                        // i-esima del res -  O(1)
            i = i + 1;                                                  // aumeento en 1 el iterador -  O(1)

        }

        return res;                                                     // - O(1)
    }


    private NotaFinal[] invertirOrden( NotaFinal[] res ){               // - O(E)

        // da vuelta el orden de un array siendo E la longitud de dicho array

        NotaFinal[] resOrdenCorecto = new NotaFinal[(res.length)];      // - O(E)
        int j = 0;                                                      // - O(1)

        while(j<res.length){                                          // repito el cuerpo E-veces - O(E*(cuerpo))

            resOrdenCorecto[j]=res[res.length-1-j];                     // se invierte el orden de la ubicacion de elementos
                                                                        // lo que estaba ultimo en un array, ahota esta primero en el otro - O(1)
            j = j + 1;                                                  // aumento en 1 el itreador j - O(1)

        }

        return resOrdenCorecto;                                         // - O(1)
    }


    private NotaFinal[] sacarSospechosos(NotaFinal[] conSospechosos){     // - O(E) , en el peor caso

        //saco del array los estudiantes que se copiaron.
        //para eso a todos los estudiantes que no se copiaron,
        // los agrego a otro array

        int cantNoSospechosos = this.cantEstudiantes-this.cantSospechosos;        // cantidad de alumnos que no son sopechosos de copiarse - O(1)
        NotaFinal[] sinSospechosos = new NotaFinal[cantNoSospechosos];    // en el peor caso esto es de longitud E - O(E)

        int j = 0;                                                        // iterador sobre el array de notafinal de estudiantes sin copiones - O(1)
        int i = 0;                                                        // iterador sobre el array de NotaFinal de estudiantes con copiones - O(1)

        while(i<conSospechosos.length){                                   // itero sobla el array de NotaFinal de estudiantes  
            int id = conSospechosos[i]._id;                         
            if(this.estudiantes[id].obtenerEstudiante().obtenerEstadoSospechoso()==false){                   // si el estudiante no es sospechoso de copiarse,
                sinSospechosos[j]=conSospechosos[i];                      // lo agrego al array de notaFinal de estudiantes que no se copiaron - O(1)                j=j+1;
                j = j + 1;
            }

            i=i+1;
        }                                                                 // la complejidad de este while en el peor caso es - O(E)

        return sinSospechosos;                                            // - O(1)

    }   


    private void restuararIdPorNotasCompleto(){                        // O(E*log(E))
        // apartir de Estudiantes, se  llena idpornotas con
        // todos los elementos de Estduiantes, se asume que 
        // id por nota esta vacio al momento de este proc.

        int k = 0;                                                      // inicializo el iterador - O(1)

        while (k<this.cantEstudiantes){                                         // repito el cuerpo E-veces - O(E*(cuerpo))

            this.idPorNotas.encolar(estudiantes[k]);                                 // encolo uno a uno cada estudiante con su id -O(log(E)) como maximo
            k = k + 1;
        }
    }


//-------------------------------------------------------CHEQUEAR COPIAS-------------------------------------------------


    public int[] chequearCopias() {
        int[][] grilla = armarGrillaDeRespuestas();             // O(E*R)
        double umbral = (cantEstudiantes - 1) * 0.25;           // O(1)
        clasificarSospechososSegunCriterio(grilla, umbral);     // O(E*R)
        int res[] = armarListaDeSospechosos();                  // O(E)
        this.cantSospechosos = res.length;                      // O(1)
        return res;                                             // O(1)
    } // Complejidad: O(E*R) 


    public int[][] armarGrillaDeRespuestas() {
        int[][] grilla = new int[this.cantPreguntas][10];                                   // O(1)
        for (int i = 0; i < cantEstudiantes; i++) {                                         // O(E)
            for (int j = 0; j < this.cantPreguntas; j++) {                                  // O(R)
                int respuesta = estudiantes[i].obtenerEstudiante().obtenerRespuesta(j);     // O(1)
                if (respuesta == -1) {                                                      // O(1)
                    continue;                                                   
                } 
                grilla[j][respuesta] += 1;                                                  // O(1)
            }
        }
        return grilla;                                                                      
    } // Complejidad: O(E*R)


    public void clasificarSospechososSegunCriterio(int[][] grilla, double umbral) {
        for (int i = 0; i < cantEstudiantes; i++) {                                         // O(E)
            boolean esSospechoso = true;                                                    // O(1)

            for (int j = 0; j < cantPreguntas; j++) {                                       // O(R)
                int respuesta = estudiantes[i].obtenerEstudiante().obtenerRespuesta(j);     // O(1)
                if (respuesta == -1) {                                                      // O(1)
                    continue; 
                }

                if (grilla[j][respuesta] -1 < umbral) {                                     // O(1)
                    esSospechoso = false; 
                    break; 
                }
            }
            if (estudiantes[i].obtenerEstudiante().obtenerPreguntasRespondidas() == 0) {    // O(1)
                esSospechoso = false;                                                       
            }

            estudiantes[i].obtenerEstudiante().marcarSospechoso(esSospechoso);              // O(1)
        }
    } // Complejidad: O(E*R)


    public int[] armarListaDeSospechosos() {
        int tramposos = contarSospechosos();                                        // O(E)
        int[] res = new int[tramposos];                                             // O(E)
        int k = 0;                                                                  // O(1)

        for (int i = 0; i<cantEstudiantes;i++){                                     // O(E)
            if (estudiantes[i].obtenerEstudiante().obtenerEstadoSospechoso()) {     // O(1)
                res[k] = i;                             
                k++;
            }
        }
        return res; 
    } // Complejidad: O(E)


    public int contarSospechosos() {
        int contador = 0;                                                           // O(1)
        
        for (int i = 0; i < cantEstudiantes; i++) {                                 // O(E)
            if (estudiantes[i].obtenerEstudiante().obtenerEstadoSospechoso()) {     // O(1)
                contador++; 
            }
        }
        return contador; 
    } // Complejidad: O(E)
}
