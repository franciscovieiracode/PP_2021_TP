/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityNStats;

import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import java.time.LocalDateTime;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
*/
public class Station implements IStation {

    /**
     * nome da estacao
     */
    private String name;
    /**
     * Array de sensores
     */
    private Sensor sensores[];
    /**
     * numero atual de sensores
     */
    private int nSensores;
    /**
     * Numero maximo de sensores
     */
    private int maxSensores;

    /**
     * Contrutor para station
     * @param name nome station
     */
    public Station(String name) {
        this.name = name;
        this.maxSensores = 10;
        this.nSensores = 0;
        this.sensores = new Sensor[maxSensores];
    }

    /**
     * Getter para nome
     * @return nome
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Adiciona sensor
     * @param is sensor
     * @return true caso adicione, false caso contrario
     * @throws StationException caso sensor seja null
     * @throws SensorException id ou parametro sejam invalidos
     */
    @Override
    public boolean addSensor(ISensor is) throws StationException, SensorException {
        boolean add = false;

        if (is == null) {
            throw new StationException("Sensor invalido");
        } else if (is.getId().length() != 10) {
            throw new SensorException("ID de sensor invalido");
        } else if (is.getType() == null || is.getParameter() == null) {
            throw new SensorException("Parametro ou Tipo de sensor invalido");
        } else if (findSensorByObject(is) != -1) {
            return add;
        } else {

            if (nSensores >= maxSensores) {
                rezise();
            }

            sensores[nSensores] = (Sensor) is;
            nSensores++;
            add = true;

        }
        return add;
    }

    /**
     * Encontra sensor 
     * @param is sensor a econtrar
     * @return posicao do array
     */
    private int findSensorByObject(ISensor is) {
        int res = -1;

        for (int i = 0; i < nSensores; i++) {
            if (sensores[i].getId().equals(is.getId())) {
                res = i;
            }
        }

        return res;
    }

    /**
     * Encontra sensor pelo id
     * @param sensorID snesor id
     * @return sensor
     */
    private int findSensorByString(String sensorID) {
        int res = -1;

        for (int i = 0; i < nSensores; i++) {
            if (sensores[i].getId().equals(sensorID)) {
                res = i;
            }
        }

        return res;
    }

    /**
     * Redimesiona o arrau de sensores
     */
    private void rezise() {
        Sensor[] temp = new Sensor[2 * maxSensores];
        for (int i = 0; i < nSensores; i++) {
            temp[i] = sensores[i];
        }
        maxSensores *= 2;
        sensores = temp;
    }

    /**
     * Adiciona medida a um sensor
     * @param sensorID sensor id
     * @param d valor
     * @param ldt data
     * @param unit unidade
     * @return true caso adicione, false caso contrario
     * @throws StationException Sensor na existe
     * @throws SensorException Falha de compatibilidade
     * @throws MeasurementException Falha de compatibilidade 
     */
    @Override
    public boolean addMeasurement(String sensorID, double d, LocalDateTime ldt, String unit) throws StationException, SensorException, MeasurementException {

        int pos = -1;
        boolean add = false;

        pos = findSensorByString(sensorID);

        if (pos == -1) {
            throw new StationException("Erro de parametro");
        } else if (!(sensores[pos].getParameter().getUnit() == Unit.getUnitFromString(unit))) {
            throw new SensorException("Parametro do sensor invalido");
        } else if (d == -99) {
            throw new MeasurementException("Valor invalido");
        } else {
            try {
                if (this.sensores[pos].addMeasurement(d, ldt, unit) == true);
                add = true;
            } catch (SensorException ex) {
                throw new SensorException("Falha de compatibilidade de unidade");
            } catch (MeasurementException ex) {
                throw new MeasurementException("Falha de parametro");

            }
        }

        return add;
    }

    /**
     * Getter para array de sensores
     * @return array de sensores
     */
    @Override
    public ISensor[] getSensors() {
        ISensor[] temp = new Sensor[this.nSensores];

        System.arraycopy(sensores, 0, temp, 0, nSensores);

        return temp;
    }

    /**
     * Getter para sensor por id
     * @param string sensor id
     * @return sensor
     */
    @Override
    public ISensor getSensor(String string) {

        ISensor temp = null;

        for (int i = 0; i < nSensores; i++) {
            if (sensores[i].getId().equals(string)) {
                temp = sensores[i];
            }
        }
        return temp;
    }

    /**
     * Getter para numeor de sensores
     * @return numero de sensores
     */
    public int getnSensores() {
        return nSensores;
    }

}
