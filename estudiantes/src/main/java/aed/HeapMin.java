package aed; 

public class HeapMin {
    
    /* 
    COMENTARIOS SOBRE CORRECCIONES HECHAS:
        antes, teníamos una clase HandleEst.java separada
        esto nos traía problemas con el encapsulamiento, ya que como se guardaba la posición en el Heap, 
        dependía, y le daba sentido, solo si este último estaba implementado sobre array

        AHORA, es clase interna del HeapMin
        entonces, conseguimos que el HeapMin sea el único responsable de actualizar las posiciones y, así, 
        respetar el encapsulamiento

        simplificamos el constructor: ahora crea el un Heap vacio y se va llenando conforme se crean los estudiantes
        cuando se usa en el EdR con encolar()

        agregamos métodos: desencolarHandle() (para devolver un Handle en el ejercicio de consultarDarkWeb) y 
                           crearHandle()
    */

// ------- PARA EL HANDLE 
    
    public class Handle {
        private int posEnHeap; 
        private Estudiante est; 

        public Handle(Estudiante est) {
            this.est = est; 
        }

        // Getters - todos O(1)
        public Estudiante obtenerEstudiante() {
            return this.est; 
        }

        public int obtenerPosicionEnHeap() {
            return this.posEnHeap; 
        }

        // Setter - O(1)
        public void cambiarPosicionEnHeap(int pos) {
            this.posEnHeap = pos; 
        }
    }

//------------------------------------------------------------------------Constructor--------------------------------------------------
    
    private Handle[] heap;              // los elementos del heap son handles con estudiantes y su psocion en el heaps  
    private int tamaño;                // hanldes que tenemos


    public HeapMin(int capacidad) {
        this.tamaño = 0;  // empieza vacío
        this.heap = new Handle[capacidad];
    } 


    public Handle crearHandle(Estudiante est) {
        return new Handle(est); 
    }

//------------------------------------------------------------------------Encolar--------------------------------------------------


    public void encolar(Handle  hEst) {
        
       
        heap[tamaño] = hEst;              // Inserta el handle del nuevo estudiante en la última posición libre del heap              // O(1)

        hEst.cambiarPosicionEnHeap(tamaño);
        tamaño++;                       // Aumenta el tamaño del heap, ya que ahora contiene un elemento más                      // O(1)
        siftUp(tamaño - 1);             // Reacomoda el nuevo elemento hacia arriba si su prioridad es mayor que la de su padre        // O(log E)
    } // Complejiidad: O(log E)


//------------------------------------------------------------------------Desencolar--------------------------------------------------


    public Estudiante desencolar() {
        Estudiante estConPeorNota = heap[0].obtenerEstudiante();  //guardo el estudiante con peor nota(raiz del heap)                // O(1)

        intercambiar(0, tamaño - 1);        // Intercambia la raíz con el último elemento del heap                            // O(1)
        tamaño--;                           // Reduce el tamaño del heap, "eliminando" el último elemento (el peor)           // O(1)
        siftDown(0);                        // Reacomoda el nuevo elemento en la raíz para restaurar el orden del heap        // O(log E)
        return estConPeorNota;                      // Devuelve el estudiante con peor nota                                    // O(1)
    } // Complejidad: O(log E)



    public Handle desencolarHandle() {
        Handle handleConPeorNota = heap[0]; 

        intercambiar(0, tamaño - 1); 
        tamaño--; 
        siftDown(0); 

        return handleConPeorNota; 
    }

//------------------------------------------------------------------------Actualizar nota--------------------------------------------------


    public void actualizar(Handle  h) {
        int pos = h.obtenerPosicionEnHeap();            // como los handels estan pasados por referencia.
                                                        // la nota ya esta actualizada. lo unico que nos falta,
                                                        // es actualizar la posicion del handel en el heap.

        siftUp(pos);                    // Sube al handel del estudiante en el heap hasta restaurar el orden               // O(log E)
        siftDown(pos);                  // Baja al handel del estudiante en el heap hasta restaurar el orden               // O(log E)
    } // Complejidad: O(log E)

    /*
    Este método enccuentra la poosicion del heap del estudiante que cambio su nota o su estado de entrega.
    Luego reacomoda el handel en el heap por si su cambio del estudiante involucro un cambio en su prioridad.
    */

//------------------------------------------------------------------------SiftUp--------------------------------------------------------------
   


    private void siftUp(int pos) { 

        while (pos > 0 && debeSubir(pos, (pos - 1) / 2)) {   // Recorre hacia arriba mientras no sea la raíz y el handel del estudiante actual deba subir      // O(log E)
            int padre = (pos - 1) / 2;                       // Calcula el índice del handel del estudiante padre                                              // O(1)
            intercambiar(pos, padre);                        // Intercambia el estudiante con su padre en el heap                                   // O(1)
            pos = padre;                                     // Actualiza la posición actual al nuevo índice                                        // O(1)
        }        
    }   // Complejidad total: O(log E)



//------------------------------------------------------------------------SiftDown--------------------------------------------------------------

    private void siftDown(int pos) { 
        while (pos < tamaño) {                                           // O(log E)
            int izq = 2 * pos + 1;                                       // O(1)
            int der = 2 * pos + 2;                                       // O(1)
            int menor = pos;                                             // O(1)
            if (izq < tamaño && debeSubir(izq, menor)){                   // O(1)
                menor = izq;
            }
            if (der < tamaño && debeSubir(der, menor)){                   // O(1)
                menor = der;
            }
            if (menor == pos){                                            // O(1)
                break;        
            }                                                            // Ya está en el lugar correcto
            intercambiar(pos, menor);                                    // O(1)
            pos = menor;                                                 // O(1)
            }
    }
 // Complejidad total: O(log E)


//------------------------------------------------------------------------Extras--------------------------------------------------------


    public void intercambiar(int i, int j) { 
        Handle  hEnI = heap[i]; 
        Handle  hEnJ = heap[j]; 
        
        // intercambiamos handels en el heap y actualizamos las posiciones
        heap[i] = hEnJ;
        heap[j] = hEnI;
        hEnI.cambiarPosicionEnHeap(j);
        hEnJ.cambiarPosicionEnHeap(i);
    } //O(1)




    
    private boolean debeSubir(int hijo, int padre) { 
        Handle  hHijo = heap[hijo];                                                                           
        Handle  hPadre = heap[padre]; 
        boolean res;

        Estudiante estHijo = hHijo.obtenerEstudiante();
        Estudiante estPadre = hPadre.obtenerEstudiante();

        if (estHijo.compareTo(estPadre)==1) {   // si el hijo tiene mayor prioridad que el padre, el hijo debe subir. 
            res = true; 
        } else {
            res = false;
        }
        return res; 
    }

    


   

    
    public boolean vacio() {
        return tamaño == 0;
    }

    

}
