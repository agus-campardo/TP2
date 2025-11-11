package aed; 

public class heapMin {
    private int[] heap;         // ids de estudiantes  
    private int[] posiciones;   // posiciones[id] = índice en heap 
    private int[] notas;        // notas[id] = nota actual 
    private int tamaño;         // cuántos hay realmente (por si desencolamos gente)
    private int capacidad;      // max. estudiantes que puede tener el heap (siempre = E)

    public class Handle {
        private int id; 
        private heapMin miHeap; 

        private Handle(int id, heapMin padre) { 
            this.id = id; 
            this.miHeap = padre; 
        }

        public int id() {
            return id; 
        }

        public int nota() {
            return miHeap.notas[id];
        }

        // Complejidad = O(log E)
        public void actualizarNota(int nuevaNota) {
            miHeap.actualizarNotaDesdeHandle(id, nuevaNota);    // O(log E)
        }
    }

    // CONSTRUCTOR - usamos heapify para crear el heap en O(E)
    public heapMin(int cantEstudiantes, int[] notasIniciales) {
        this.capacidad = cantEstudiantes; 
        this.heap = new int[capacidad]; 
        this.posiciones = new int[capacidad]; 
        this.notas = new int[capacidad]; 
        this.tamaño = capacidad; // al principio, todos están en el heap 

        armarHeap(notasIniciales);  // O(E)
        usarHeapify();              // O(E)
    }

    // NOTAS INICIALES SON TODOS 0 AL INICIO. POR AHI NO ES NECESARIO PASAR LAS NOTAS INICIALES
    // Complejidad = O(E)
    private void armarHeap(int[] notasIniciales) {
        for (int i = 0; i < capacidad; i++) {   // E iteraciones
            heap[i] = i;                        // O(1)
            posiciones[i] = i;                  // O(1)
            notas[i] = notasIniciales[i];       // O(1)
        }
    }

    /**
     * ¿Heapify? - O(E)
     * Hacemos siftDown por cada uno de los niveles, "yendo hacia atrás", hasta la raíz. 
     * 
     * 1. Empezamos desde el último padre (el nodo más abajo que tiene dos hijos)
     * 2. Hacemos siftDown a este padre y a todos los padres de arriba.
     * 3. Subimos de nivel hasta llegar a la raiz.
     */

    // Complejidad = O(E)
    private void usarHeapify() {
        int ultimoPadre = encontrarUltimoPadre();
        usarSiftDownDesde(ultimoPadre); 
    }

    private int encontrarUltimoPadre() {
        int ultimoElemento = tamaño -1; 
        int padreDelUltimo = (ultimoElemento - 1)/2;
        return padreDelUltimo;
    }

    private void usarSiftDownDesde(int empezarDesde) {
        for (int posActual = empezarDesde; posActual >= 0; posActual--) {
            siftDown(posActual);
        }
    }

    // actualizar notas con handle
    // Complejidad = O(log E)
    private void actualizarNotaDesdeHandle(int id, int nuevaNota) { 
        int notaVieja = notas[id]; 
        notas[id] = nuevaNota;
        int pos = posiciones[id]; 

        if (nuevaNota < notaVieja) { 
            siftUp(pos);                        // O(log E)
        } else if (nuevaNota > notaVieja) { 
            siftDown(pos);                      // O(log E)
        }
    }

    // Complejidad = O(log E)
    private void siftUp(int pos) { 
        while (pos > 0) {                  // máximo altura del árbol = log E
            int padre = (pos - 1)/2; 
            
            if (debeSubir(pos, padre)) {
                intercambiar(pos, padre);
                pos = padre; 
                // seguimos viendo desde la nueva pos 
            } else { 
                break; // VER SI PODES USAR BREAK
            }
        }
    }

    // Complejidad = O(log E)
    private void siftDown(int pos) { 
        while (pos < tamaño) { 
            int izq = 2 * pos + 1; 
            int der = 2 * pos + 2;
            int menor = encontrarMenor(pos, izq, der); 
            
            if (menor == pos) {
                break; // encontró su lugar 
            } else {
                intercambiar(pos, menor);
                pos = menor; // seguimos viendo desde la nueva pos
            }
        }
    }

    public void intercambiar(int i, int j) { 
        int idEnI = heap[i]; 
        int idEnJ = heap[j]; 
        
        // intercambiamos ids en el heap y actualizamos las posiciones
        heap[i] = idEnJ;
        heap[j] = idEnI;
        posiciones[idEnI] = j;
        posiciones[idEnJ] = i;
    }

    // USAR COMPARE TO ¿?
    private boolean debeSubir(int i, int j) { 
        int idPosI = heap[i]; 
        int idPosJ = heap[j]; 
        boolean res = true;

        // comparamos la nota. si es la misma, se desempata por menor id
        if ((notas[idPosI] < notas[idPosJ]) || (notas[idPosI] == notas[idPosJ] && idPosI < idPosJ)) { 
            res = true; 
        } else {
            res = false;
        }

        return res; 
    }

    public int encontrarMenor(int pos, int izq, int der) { 
        int menor = pos; 

        // veamos hijo izquierdo
        if (izq < tamaño && debeSubir(izq, menor)) {
            menor = izq;
        }

        // ver hijo derecho
        if (der < tamaño && debeSubir(der, menor)) {
            menor = der;
        }

        return menor;
    }

    // PARA 4.consultarDarkWeb - sacar el peor estudiante 
    // Complejidad = O(log E)
    public int desencolar() {

        int peorId = heap[0]; 

        intercambiar(0, tamaño-1); // mandamos el peor al final 
        tamaño--;                    // lo sacamos del heap
        siftDown(0);            // reacomodamos desde la raiz

        return peorId;
    }

    // Complejiidad = O(log E)
    public void encolar(int id) {
        heap[tamaño] = id; 
        posiciones[id] = tamaño;  
        tamaño++; 
        siftUp(tamaño - 1);
    }
    
    // Complejidad = O(1)
    public Handle obtenerHandle(int id) {
        Handle handleParaId = new Handle(id, this); 
        return handleParaId;
    }

    // TAL VEZ SIRVE ¿? BORRAR SI NO
    public boolean vacio() {
        return tamaño == 0;
    }

}