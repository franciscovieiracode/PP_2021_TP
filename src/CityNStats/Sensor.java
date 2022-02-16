/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityNStats;

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import java.time.LocalDateTime;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
*/
public class Sensor implements ISensor {

    /**
     * sensor id
     */
    private String id;
    /**
     * Tipo de sensor
     */
    private SensorType tipoSensor;
    /**
     * Tipo de parametro
     */
    private Parameter parametro;
    /**
     * Coordenadas cartesianas
     */
    private ICartesianCoordinates coordenadasCarte;
    /**
     * Coordenadas geograficas
     */
    private IGeographicCoordinates coordenadasGeo;
    /**
     * Array de medidas
     */
    private Measurement[] medidas;
    /**
     * Numero maximo de medidas
     */
    private int maxMedidas;
    /**
     * Numero atual de medidas
     */
    private int nMedidas;

    /**
     * Contrutor para um sensor
     * @param id sensor id
     * @param tipoSensor tipo sensor
     * @param parametro parametro do sensor
     * @param coordenadasCarte coordenadas cartesianas
     * @param coordenadasGeo coordenadas geograficas
     */
    public Sensor(String id, SensorType tipoSensor, Parameter parametro, ICartesianCoordinates coordenadasCarte, IGeographicCoordinates coordenadasGeo) {
        this.id = id;
        this.tipoSensor = tipoSensor;
        this.parametro = parametro;
        this.coordenadasCarte = coordenadasCarte;
        this.coordenadasGeo = coordenadasGeo;
        this.maxMedidas = 10;
        this.medidas = new Measurement[maxMedidas];
        this.nMedidas = 0;
    }

    /**
     * Getter para id
     * @return id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Getter para tipo de sensor
     * @return tipo sensor
     */
    @Override
    public SensorType getType() {
        return tipoSensor;
    }

    /**
     * Getter para pametro
     * @return 
     */
    @Override
    public Parameter getParameter() {
        return parametro;
    }

    /**
     * Getter para coordenadas cartesianas
     * @return coordenadas cartesianas
     */
    @Override
    public ICartesianCoordinates getCartesianCoordinates() {
        return coordenadasCarte;
    }

    /**
     * Getter para coordenadas geograficas
     * @return coordenadas geograficas
     */
    @Override
    public IGeographicCoordinates getGeographicCoordinates() {
        return coordenadasGeo;
    }

    /**
     * Adiciona uma medida a um sensor
     * @param d valor 
     * @param ldt data
     * @param string unit
     * @return true caso adicione, false caso contrario
     * @throws SensorException Caso sensor seja null ou nao exista
     * @throws MeasurementException Caso parametros nao batam certo
     */
    @Override
    public boolean addMeasurement(double d, LocalDateTime ldt, String string) throws SensorException, MeasurementException {

        boolean add = false;

        if (d == -99 || string == null || ldt == null) {
            throw new MeasurementException();
        } else if (parametro.getUnit() == Unit.getUnitFromString(string)) {
            throw new SensorException();
        } else if (findMedida(ldt, d) != -1) {
            return add;
        } else {
            IMeasurement temp = new Measurement(d);

            medidas[nMedidas] = (Measurement) temp;
            nMedidas++;
            add = true;
        }

        return add;
    }

    /**
     * Encontra medida no array
     * @param ldt data
     * @param d valor
     * @return medida
     */
    private int findMedida(LocalDateTime ldt, double d) {
        int res = -1;

        for (int i = 0; i < nMedidas; i++) {
            if (medidas[i].getTime().isEqual(ldt) && medidas[i].getValue() == d) {
                res = i;
            }
        }

        return res;
    }

    /**
     * Getter para numero de medidas
     * @return numero de medidas
     */
    @Override
    public int getNumMeasurements() {
        return nMedidas;
    }

    /**
     * Getter para array de medidas
     * @return array de medidas
     */
    @Override
    public IMeasurement[] getMeasurements() {
        IMeasurement [] temp = new Measurement[this.nMedidas];
        
        System.arraycopy(medidas, 0, temp, 0, nMedidas);
        
        return temp;
    }

    /**
     * getter para numero de medidas
     * @return numero de medidas
     */
    public int getnMedidas() {
        return nMedidas;
    }

    /**
     * Getter para medidas por data
     * @param start dato de incio
     * @param end data do fim
     * @return medidas
     */
    public IMeasurement[] getMeasurementsByDate(LocalDateTime start, LocalDateTime end) {
        int count = 0;
        int j = 0;

        for (int i = 0; i < nMedidas; i++) {
            if (medidas[i].getTime().isAfter(start) && medidas[i].getTime().isBefore(end)) {
                count++;
            }
        }

        if (count == 0) {
            return null;
        }

        IMeasurement[] temp = new Measurement[count];

        for (int i = 0; i < nMedidas; i++) {
            if (medidas[i].getTime().isAfter(start) && medidas[i].getTime().isBefore(end)) {
                temp[j] = medidas[i];
                j++;
            }

        }
        return temp;
    }

}
