package aed;

// Crear Examen O(R)

public class Examen {
    int[] preguntas;
    

    public Examen(int cantPreguntas){
        this.preguntas = new int[cantPreguntas];        //O(R)
        for (int i = 0; i < cantPreguntas -1; i++){     //O(R)
            this.preguntas[i] = -1;                     //O(1)
        }
    }

    public int cantidadPreguntasFaltantes(Examen vecino){
        int res = 0;                                                                    //O(1)
        for (int i = 0; i < this.preguntas.length; i++){                                //O(R)
            if (this.preguntas[i] == -1 && vecino.preguntas[i] != -1){                  //O(1)
                res += 1;                                                               //O(1)
            }
        }
        return res;
    }

    public void copiarPrimeraPregunta(Examen vecino){
        boolean encontre = false;
        while (encontre == false){
            for (int i = 0; i < this.preguntas.length; i++){
                if (this.preguntas[i] == -1 && vecino.preguntas[i] != -1){
                    this.preguntas[i] = vecino.preguntas[i];
                    encontre = true;
                }
            }
        }
    }
}
// Que pasa si los vecinos del q se quiere copiar no tienen ninguna respuesta q el no tenga?
