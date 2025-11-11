package aed;

// Crear estudiante O(R)

public class Estudiante {
    int id;
    int fila;
    int columna;
    int correctas;
    double nota;
    boolean entrego;
    boolean sospechoso;
    Examen examen;


//------------------------------------------------------------------------Constructor--------------------------------------------------


    public Estudiante(int id, int cantPreguntas, int ladoAula){
        this.id = id;                                                           // O(1)                        // O(1)
        this.correctas = 0;                                                     // O(1)
        this.nota = 0.0;                                                          // O(1)
        this.entrego = false;                                                   // O(1)
        this.sospechoso = true;                                                 // O(1)
        this.examen = new Examen(cantPreguntas);                                // O(R)
        
        calcularPosicion(ladoAula);                                             // O(1)
    
    } // Complejidad: O(R)

    private void calcularPosicion(int ladoAula) {
        int estudiantesPorFila = (ladoAula + 1) / 2;
        this.fila = id / estudiantesPorFila;
        this.columna = (id % estudiantesPorFila) * 2;
    } // O(1)


    public void esSospechoso () {
        this.sospechoso = true;
    }

}