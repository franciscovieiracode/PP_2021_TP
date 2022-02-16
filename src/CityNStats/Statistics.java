/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityNStats;

import edu.ma02.core.interfaces.IStatistics;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
*/
public class Statistics implements IStatistics {

    /**
     * Descricao
     */
    private String description;
    /**
     * Valor
     */
    private double value;

/**
 * Contrutor para Estatisica
 * @param description descricao
 * @param value valor
 */
    public Statistics(String description, double value) {
        this.description = description;
        this.value = value;
    }


    /**
     * Getter para descricao
     * @return descricao
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Getter para descricao
     * @return 
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * Setter para descricao
     * @param description descricao
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter para valor
     * @param value valor
     */
    public void setValue(double value) {
        this.value = value;
    }

    

    
    
}
