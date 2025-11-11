package aed;
import java.util.ArrayList;

public class Edr {
    private Estudiante[] estudiantes;
    private Examen examenCanonico;
    private HeapMin idPorNotas;
    private int ladoAula;
    private int cantEst;
    private int cantEntregados;


    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico){
        this.examenCanonico = new Examen(ExamenCanonico.length);                            // O(R)
        this.examenCanonico.preguntas = ExamenCanonico;                                     // O(1)
        this.estudiantes = new Estudiante[Cant_estudiantes];                                // O(E)
        this.ladoAula = LadoAula;                                                           // O(1)
        this.cantEst = Cant_estudiantes;                                                    // O(1)
        this.cantEntregados = 0;                                                            // O(1)
        this.idPorNotas = new HeapMin(Cant_estudiantes);                                    // O(E)
        for (int i = 0; i < Cant_estudiantes; i++){                                         // O(E)
            this.estudiantes[i] = new Estudiante(i, ExamenCanonico.length, ladoAula);       // O(R)
        }
    } // O(R) + O(1) + O(E) + O(1) + O(E) * O(R) = O(E*R)
    

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


    public void resolver(int estudiante, int NroEjercicio, int res) {
        estudiantes[estudiante].examen.preguntas[NroEjercicio] = res;                               // O(1)
        if (examenCanonico.preguntas[NroEjercicio] == res){                                         // O(1)
            estudiantes[estudiante].correctas += 1;                                                 // O(1)
        }
        estudiantes[estudiante].nota = estudiantes[estudiante].correctas * 10;                      // O(1)
        this.idPorNotas.actualizarNotaDesdeHandle(estudiante, estudiantes[estudiante].nota);        // O(log E)
    }


//------------------------------------------------------------------------CONSULTAR DARK WEB--------------------------------------------------------------


    public void consultarDarkWeb(int k, int[] examenDW) {
        int notaNueva = calcularNota(examenDW, this.examenCanonico.preguntas);
        if(k <= this.cantEst){                                      // Si los que consultanDW son menos que la cant de est
            if(k <= cantEst - cantEntregados){                      // Si hay menos personas que cantidad de entregados
                intercambiarExamen(k, notaNueva, examenDW);
            }
            else{                                                   // Si k es mayor a cantEst - cantEntregados
                intercambiarExamen(cantEst - cantEntregados, notaNueva, examenDW);
            }
        }
        else{                                                       // Si todos los estudiantes consultanDW
            intercambiarExamen(cantEst, notaNueva, examenDW);
        }
    }

    public int calcularNota(int[] otro, int[] canonico){
        int nota = 0;                                                           // O(1)
        for (int i = 0; i < canonico.length; i++){                              // O(R)
            if (otro[i] == canonico[i]){                                        // O(1)
                nota += 10;                                                     // O(1)
            }                   
        }
        return nota;
    } // Complejidad: O(R)

    public void intercambiarExamen(int hasta, int notaNueva, int[] examenDW){
        for (int i = 0; i < hasta; i++){                                        // O(K)
            int e = idPorNotas.desencolar();                                    // O(log E)
            estudiantes[e].examen.preguntas = examenDW;                         // O(1)
            estudiantes[e].nota = notaNueva;                                    // O(1)
            idPorNotas.encolar(e);                                              // O(log E)
        }
    }


//------------------------------------------------------------------------ENTREGAR------------------------------------------------------------------------


    public void entregar(int estudiante) {
        estudiantes[estudiante].entrego = true;
        this.cantEntregados += 1;
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
