/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityNStats;

import edu.ma02.core.interfaces.IMeasurement;
import java.time.LocalDateTime;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
*/
public class Measurement implements IMeasurement{

    /**
     * Data da medida
     */
    private LocalDateTime date;
    /**
     * Valor da medida
     */
    private double value;

    /**
     * Contrutor para medida
     * @param value valor da medida
     */
    public Measurement(double value) {
        this.value = value;
        this.date = LocalDateTime.now();
    }
    
    /**
     * Getter para o tempo
     * @return tempo
     */
    @Override
    public LocalDateTime getTime() {
        return date;
    }

    /**
     * Getter para value
     * @return value
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * Equals para medidas
     * @param obj medida a comparar
     * @return true caso sejam o mesmo, false caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Measurement other = (Measurement) obj;
        if (this.value != other.value) {
            return false;
        }
        if (!(this.getTime().isEqual(other.getTime()))) {
            return false;
        }
        
        return true;
    }
    
    
    
}
