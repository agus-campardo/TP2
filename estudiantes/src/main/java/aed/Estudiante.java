package aed;

// Crear estudiante O(R)

public class Estudiante {
    int id;
    int fila;
    int columna;
    int correctas;
    int nota;
    boolean entrego;
    boolean sospechoso;
    Examen examen;


//------------------------------------------------------------------------Constructor--------------------------------------------------


    public Estudiante(int id, int cantPreguntas, int ladoAula){
        this.id = id;                                                           // O(1)
        this.fila = calcularFila(id, ladoAula);                                 // O(1)
        this.columna = calcularColumna(id, ladoAula);                           // O(1)
        this.correctas = 0;                                                     // O(1)
        this.nota = 0;                                                          // O(1)
        this.entrego = false;                                                   // O(1)
        this.sospechoso = true;                                                 // O(1)
        this.examen = new Examen(cantPreguntas);                                // O(R)
    } // Complejidad: O(R)


    public int calcularFila(int id, int ladoAula){
        int estPorFila = (ladoAula + 1) / 2;                                           // O(1)
        int res = id / estPorFila;                                                     // O(1)
        return res;                                                                    // O(1)
    } // O(R) + O(1) + O(1) + O(1) = O(R)

    public int calcularColumna(int id, int ladoAula){
        int estPorFila = (ladoAula + 1) / 2;                                        // O(1)
        int res = (id % estPorFila)*2;                                              // O(1)
        return res;                                                                 // O(1)
    } // O(R) + O(1) + O(1) + O(1) = O(R)

}