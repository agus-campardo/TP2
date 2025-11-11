package aed;
import java.util.ArrayList;

public class Edr {
    private Estudiante[] estudiantes;
    private Examen examenCanonico;
    private HeapMin idPorNotas;
    private int ladoAula;


    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico){
        this.examenCanonico = new Examen(ExamenCanonico.length);                        // O(R)
        this.examenCanonico.preguntas = ExamenCanonico;                                 // O(1)
        this.estudiantes = new Estudiante[Cant_estudiantes];                            // O(E)
        this.ladoAula = LadoAula;                                                       // O(1)
        for (int i = 0; i < Cant_estudiantes; i++){                                     // O(E)
            this.estudiantes[i] = crearEstudiante(i, ExamenCanonico.length);            // O(R)
        }
    } // O(R) + O(1) + O(E) + O(1) + O(E) * O(R) = O(E*R)
    
    public Estudiante crearEstudiante(int id, int preguntas){
        Estudiante res = new Estudiante(id, preguntas);                                 // O(R)
        int estPorFila = (ladoAula + 1) / 2;                                            // O(1)
        res.fila = id / estPorFila;                                                     // O(1)
        res.columna = (id % estPorFila)*2;                                              // O(1)
        return res;                                                                     // O(1)
    } // O(R) + O(1) + O(1) + O(1) = O(R)
    

//------------------------------------------------------------------------NOTAS--------------------------------------------------------------------------


    public double[] notas(){
        double[] res = new double[estudiantes.length];                                  // O(E)
        for (int i = 0; i < estudiantes.length; i++){                                   // O(E)
            res[i] = estudiantes[i].nota;                                               // O(1)
        }
        return res;
    } // O(E) + O(E) + O(1) = O(E)


//------------------------------------------------------------------------COPIARSE------------------------------------------------------------------------

// Falta terminar
    public void copiarse(int est) {
        int estPorFila = (ladoAula + 1) / 2;                                            // O(1)
        ArrayList<Estudiante> vecinos = new ArrayList<>();                              // O(1)
        if (est == 0){
            if (ladoAula > 2){
                vecinos.add(estudiantes[est + 1]);
            }
        }
        else if(est == estudiantes.length - 1){
            vecinos.add(estudiantes[est - 1]);
            vecinos.add(estudiantes[est - estPorFila]);
        }
        else{
            if(estaEnRango(estudiantes[est], estudiantes[est + 1])){
                vecinos.add(estudiantes[est + 1]);
            }
            if(estaEnRango(estudiantes[est], estudiantes[est - 1])){
                vecinos.add(estudiantes[est - 1]);
            }
            if(estaEnRango(estudiantes[est], estudiantes[est - estPorFila])){
                vecinos.add(estudiantes[est - estPorFila]);
            }
        }
    }

    public boolean estaEnRango(Estudiante est, Estudiante vecino){
        if (est.fila == vecino.fila + 2 && est.columna == vecino.columna){              // O(1)
            return true;
        }
        else if (est.fila == vecino.fila - 2 && est.columna == vecino.columna){         // O(1)
            return true;
        }
        else if (est.fila == vecino.fila && est.columna == vecino.columna - 1){         // O(1)
            return true;
        }
        return false;
    }


//------------------------------------------------------------------------RESOLVER------------------------------------------------------------------------

// Falta actualizar el heap
    public void resolver(int estudiante, int NroEjercicio, int res) {
        estudiantes[estudiante].examen.preguntas[NroEjercicio] = res;                   // O(1)
        if (examenCanonico.preguntas[NroEjercicio] == res){                             // O(1)
            estudiantes[estudiante].correctas += 1;                                     // O(1)
        }
        estudiantes[estudiante].nota = estudiantes[estudiante].correctas * 10;          // O(1)
    }


//------------------------------------------------------------------------CONSULTAR DARK WEB--------------------------------------------------------------


    public void consultarDarkWeb(int n, int[] examenDW) {
        throw new UnsupportedOperationException("Sin implementar");
    }


//------------------------------------------------------------------------ENTREGAR------------------------------------------------------------------------


    public void entregar(int estudiante) {
        throw new UnsupportedOperationException("Sin implementar");
    }


//------------------------------------------------------------------------CORREGIR------------------------------------------------------------------------


    public NotaFinal[] corregir() {
        throw new UnsupportedOperationException("Sin implementar");
    }


//------------------------------------------------------------------------CHEQUEAR COPIAS-----------------------------------------------------------------


    public int[] chequearCopias() {
        throw new UnsupportedOperationException("Sin implementar");
    }
}
