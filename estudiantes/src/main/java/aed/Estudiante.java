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


//------------------------------------------------------------------------Calcular nota--------------------------------------------------


    public double calcularNota(Examen canonico){
        int nota = 0;                                                           // O(1)
        for (int i = 0; i < this.examen.preguntas.length; i++){                 // O(R)
            if (this.examen.preguntas[i] == canonico.preguntas[i]){             // O(1)
                nota += 10;                                                     // O(1)
            }                   
        }
        return nota;
    } // Complejidad: O(R)
}
