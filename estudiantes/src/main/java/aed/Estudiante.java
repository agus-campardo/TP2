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


    public Estudiante(int id, int cantPreguntas){
        this.id = id;                                                           // O(1)
        this.fila = -1;                                                         // O(1)
        this.columna = -1;                                                      // O(1)
        this.correctas = 0;                                                     // O(1)
        this.nota = 0;                                                          // O(1)
        this.entrego = false;                                                   // O(1)
        this.sospechoso = true;                                                 // O(1)
        this.examen = new Examen(cantPreguntas);                                // O(R)
    } // Complejidad: O(R)
}