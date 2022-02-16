/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
*/
public class IOStatistic implements edu.ma02.io.interfaces.IOStatistics {

    /**
     * Numero de medidas lidas
     */
    private int numberOfReadMeasurements;
    /**
     * Numero de novas estacoes lidas
     */
    private int numberOfNewStationsRead;
    /**
     * Numero de estacoes lidas
     */
    private int numberOfStationsRead;
    /**
     * Numero de sensores lidos
     */
    private int numberOfSensorsRead;
    /**
     * Numero de novos sensores lidos
     */
    private int numberOfNewSensorsRead;
    /**
     * Numero de novas medidas lidas
     */
    private int numberOfNewMeasurementsRead;
    /**
     * Array de excecoes
     */
    private String[] excecoes;
    /**
     * Numero maximo de excecoes
     */
    private int maxEx;
    /**
     * Numero atual de excecoes
     */
    private int nEx;

    /**
     * Contrutor para Estatisticas
     */
    public IOStatistic() {
        this.numberOfReadMeasurements = 0;
        this.numberOfNewStationsRead = 0;
        this.numberOfStationsRead=0;
        this.numberOfSensorsRead=0;
        this.numberOfNewSensorsRead=0;
        this.numberOfNewMeasurementsRead=0;
        this.maxEx=10;
        this.nEx=0;
        this.excecoes = new String[maxEx];
        
    }

    /**
     * Getter para numero de medidas lidas
     * @return medidas lidas
     */
    @Override
    public int getNumberOfReadMeasurements() {
        return numberOfReadMeasurements;
    }

    /**
     * Getter para novas estacoes lidas
     * @return novas estacoes lidas
     */
    @Override
    public int getNumberOfNewStationsRead() {
        return numberOfNewStationsRead;
    }

    /**
     * Getter para estacoes lidas
     * @return estacoes lidas
     */
    @Override
    public int getNumberOfStationsRead() {
        return numberOfStationsRead;
    }

    /**
     * Getter para numero de sensore lidos
     * @return sensores lidos
     */
    @Override
    public int getNumberOfSensorsRead() {
        return numberOfSensorsRead;
    }

    /**
     * Getter para novos sensores lidos
     * @return novos sensores lidos
     */
    @Override
    public int getNumberOfNewSensorsRead() {
        return numberOfNewSensorsRead;
    }

    /**
     * Getter para novas medidas lidas
     * @return novas medidas lidas
     */
    @Override
    public int getNumberOfNewMeasurementsRead() {
        return numberOfNewMeasurementsRead;
    }

    /**
     * Setter para medidas lidas
     * @param numberOfReadMeasurements medidas lidas
     */
    public void setNumberOfReadMeasurements(int numberOfReadMeasurements) {
        this.numberOfReadMeasurements = numberOfReadMeasurements;
    }

    /**
     * Setter para novas estacoes lidas
     * @param numberOfNewStationsRead novas estacoes lidas
     */
    public void setNumberOfNewStationsRead(int numberOfNewStationsRead) {
        this.numberOfNewStationsRead = numberOfNewStationsRead;
    }

    /**
     * setter para numero de estacoes lidas
     * @param numberOfStationsRead estacoes lidas
     */
    public void setNumberOfStationsRead(int numberOfStationsRead) {
        this.numberOfStationsRead = numberOfStationsRead;
    }

    /**
     * Setter para numero de sensores lidos
     * @param numberOfSensorsRead sensores lidos
     */
    public void setNumberOfSensorsRead(int numberOfSensorsRead) {
        this.numberOfSensorsRead = numberOfSensorsRead;
    }

    /**
     * Setter para novas medidas lidas
     * @param numberOfNewMeasurementsRead medias lidas
     */
    public void setNumberOfNewMeasurementsRead(int numberOfNewMeasurementsRead) {
        this.numberOfNewMeasurementsRead = numberOfNewMeasurementsRead;
    }

    /**
     * Setter para novos sensores lidos
     * @param numberOfNewSensorsRead sensores lidos
     */
    public void setNumberOfNewSensorsRead(int numberOfNewSensorsRead) {
        this.numberOfNewSensorsRead = numberOfNewSensorsRead;
    }

    /**
     * Getter para array de excecoes
     * @return array de excecoes
     */
    @Override
    public String[] getExceptions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
