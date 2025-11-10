package aed;

// Crear estudiante O(R)

public class Estudiante {
    private int id;
    private int fila;
    private int columna;
    private int nota;
    private boolean entrego;
    private boolean sospechoso;
    private Examen examen;
    heapMin.Handle handleNota;
    // AGREGAR EL HANDLE PARA LA NOTA ¿?

    public Estudiante(int id, int cantPreguntas){
        this.id = id;                                               //O(1)
        this.fila = -1;                                             //O(1)
        this.columna = -1;                                          //O(1)
        this.nota = 0;                                              //O(1)
        this.entrego = false;                                       //O(1)
        this.sospechoso = true;                                     //O(1)
        this.examen = new Examen(cantPreguntas);                    //O(R)
    }

    public int calcularNota(Examen alumno, Examen canonico){
        int nota = 0;                                               //O(1)
        for (int i = 0; i < alumno.preguntas.length; i++){          //O(R)
            if (alumno.preguntas[i] == canonico.preguntas[i]){      //O(1)
                nota += 1;                                          //O(1)
            }
        }
        nota = nota/alumno.preguntas.length;                        //O(1)
        return nota;
    }
}