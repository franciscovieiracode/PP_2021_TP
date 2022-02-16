/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityNStats;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.core.interfaces.ICityStatistics;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IExporter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
 */
public class City implements ICity, ICityStatistics, IExporter {

    /**
     * Id da cidadae
     */
    private String id;
    /**
     * Nome da cidade
     */
    private String name;
    /**
     * Array de estacoes
     */
    private Station[] stationArr;
    /**
     * Numero atual de estacoes
     */
    private int nStations;
    /**
     * Capacidade maxima de estacoes
     */
    private int maxStations;

    /**
     * Contrutor para uma cidade
     *
     * @param id id da cidade
     * @param name nome da cidade
     */
    public City(String id, String name) {
        this.id = id;
        this.name = name;
        this.maxStations = 10;
        this.stationArr = new Station[maxStations];
        this.nStations = 0;
    }

    /**
     * Getter para id
     *
     * @return id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Getter para name
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter para numero atual de estacoes
     *
     * @return numero de estaçoes
     */
    public int getnStations() {
        return nStations;
    }

    /**
     * Redimensiona o tamanho do array
     */
    private void rezise() {
        Station[] temp = new Station[2 * maxStations];
        for (int i = 0; i < nStations; i++) {
            temp[i] = stationArr[i];
        }
        maxStations *= 2;
        stationArr = temp;
    }

    /**
     * Adiciona uma estacao ao array
     *
     * @param stationID id da estacao
     * @return true caso adicione, false caso contrario
     * @throws CityException Caso id seja null
     */
    @Override
    public boolean addStation(String stationID) throws CityException {
        boolean add = false;
        IStation temp = new Station(stationID);

        if (stationID == null) {
            throw new CityException();
        } else if (findStation(stationID) != -1) {
            return add;
        } else {

            if (nStations >= maxStations) {
                rezise();
            }

            stationArr[nStations] = (Station) temp;
            nStations++;
            add = true;
        }

        return add;
    }

    /**
     * Procura uma estacao no array
     *
     * @param stationID id da estacao
     * @return estacao
     */
    private int findStation(String stationID) {
        int res = -1;

        for (int i = 0; i < nStations; i++) {
            if (stationArr[i].getName().equals(stationID)) {
                res = i;
            }
        }

        return res;
    }

    /**
     * Adicionar sensor a uma estacao
     *
     * @param stationID id da estacao
     * @param sensorID id do sensor
     * @param icc corrdenadas cartesianas
     * @param igc coordenadas geograficas
     * @return true caso adicione, false caso contrario
     * @throws CityException caso estacao seja null ou nao exista
     * @throws StationException caso sensor seja null
     * @throws SensorException caso seja null ou nao cumpra os requesitos
     */
    @Override
    public boolean addSensor(String stationID, String sensorID, ICartesianCoordinates icc, IGeographicCoordinates igc) throws CityException, StationException, SensorException {
        boolean add = false;
        int pos = -1;

        pos = findStation(stationID);

        if (stationID == null || pos == -1) {
            throw new CityException("Estacao nao encontrada");
        } else if (sensorID == null) {
            throw new StationException("Sensor nao encontrado");

        } else {

            SensorType sensorType = findSensorType(sensorID);

            if (sensorType == null) {
                throw new SensorException("ID invalido");
            } else {
                try {
                    ISensor temp = new Sensor(sensorID, sensorType, Parameter.PM2_5, icc, igc);
                    if (this.stationArr[pos].addSensor(temp) == true) {
                        add = true;
                    }
                } catch (SensorException ex) {
                    throw new SensorException("Sensor invalido");
                }
            }
        }
        return add;
    }

    /**
     * Encontra o tipo de parametro
     *
     * @param sensorID sensor id
     * @return tipo de parametro
     * @throws SensorException caso sensor nao exista
     */
    private Parameter findParameterType(String sensorID) throws SensorException {

        Parameter parameter = null;

        SensorType sensorType = findSensorType(sensorID);

        if (sensorType == null) {
            return null;
        }

        return parameter;
    }

    /**
     * Encontra tipo de sensor
     *
     * @param sensorID sensor id
     * @return tipo de sensor
     * @throws SensorException caso sensor seja invalido
     */
    private SensorType findSensorType(String sensorID) throws SensorException {

        SensorType sensorType = null;

        switch (sensorID.substring(0, 2)) {
            case "QA":
                sensorType = SensorType.AIR;
                break;
            case "RU":
                sensorType = SensorType.NOISE;
                break;
            case "ME":
                sensorType = SensorType.WEATHER;
                break;
            default:
                throw new SensorException("Tipo de sensor invalido");
        }

        return sensorType;
    }

    /**
     * Adiciona medida a um sensor de uma estacao
     *
     * @param stationID station id
     * @param sensorID sensor id
     * @param d valor
     * @param unit unidade
     * @param ldt hora/data
     * @return true caso adicione, false caso contrario
     * @throws CityException caso a cidade nao exista
     * @throws StationException caso sensor na exista na cidade
     * @throws SensorException caso erro na compatibilidade entre sensor e a
     * medida
     * @throws MeasurementException caso erro no parametro
     */
    @Override
    public boolean addMeasurement(String stationID, String sensorID, double d, String unit, LocalDateTime ldt) throws CityException, StationException, SensorException, MeasurementException {

        boolean add = false;
        int pos = findStation(stationID);

        if (pos == -1) {
            throw new CityException("Estacao nao existe não encontrada");
        } else if (stationArr[pos].getSensor(sensorID) == null) {
            throw new StationException("Sensor nao existe");
        } else {
            try {
                if (stationArr[pos].getSensor(sensorID).addMeasurement(d, ldt, unit) == true);
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
     * Getter para array de stations
     *
     * @return array de stations
     */
    @Override
    public IStation[] getStations() {
        IStation[] temp = new Station[this.nStations];

        System.arraycopy(stationArr, 0, temp, 0, nStations);

        return temp;
    }

    /**
     * Getter para uma station pelo id
     *
     * @param stationID station id
     * @return station
     */
    @Override
    public IStation getStation(String stationID) {
        IStation temp = null;

        int pos = findStation(stationID);

        if (pos == -1) {
            return null;
        } else {
            temp = stationArr[pos];
        }

        return temp;
    }

    /**
     * Conta o numero de sensores em todas a estacoes
     *
     * @return numero de sensores
     */
    public int countSensores() {
        int count = 0;

        for (int i = 0; i < nStations; i++) {
            count += stationArr[i].getnSensores();
        }

        return count;
    }

    /**
     * Conta o numero de medidas em todos os sensores em todas as estacoes
     *
     * @return numero de medidas
     */
    public int countMedidas() {
        int count = 0;

        for (int i = 0; i < nStations; i++) {
            for (int j = 0; j < stationArr[i].getnSensores(); j++) {
                count += stationArr[i].getSensors()[j].getNumMeasurements();
            }
        }

        return count;
    }

    /**
     * Getter para sensores por estacao
     *
     * @param stationID station id
     * @return sensores por estacao
     */
    @Override
    public ISensor[] getSensorsByStation(String stationID) {

        int count = 0;
        int pos = 0;

        for (int i = 0; i < nStations; i++) {
            if (stationArr[i].getName().equals(stationID)) {
                count = stationArr[i].getnSensores();
                pos = i;
            }
        }

        ISensor[] SensorsByStation = new Sensor[count];

        for (int i = 0; i < count; i++) {
            SensorsByStation[i] = stationArr[pos].getSensors()[i];
        }

        return SensorsByStation;
    }

    /**
     * Devolve numero de medidas por dado sensor
     *
     * @param sensorID sensor id
     * @return array de sensores
     */
    @Override
    public IMeasurement[] getMeasurementsBySensor(String sensorID) {

        int count = 0;
        int pos = 0;
        int pos1 = 0;

        for (int i = 0; i < nStations; i++) {
            for (int j = 0; j < stationArr[i].getnSensores(); j++) {
                if (stationArr[i].getSensors()[j].getId().equals(sensorID)) {
                    count = stationArr[i].getSensors()[j].getNumMeasurements();
                    pos = i;
                    pos1 = j;
                }
            }
        }

        IMeasurement[] temp = new Measurement[count];

        for (int i = 0; i < stationArr[pos].getSensors()[pos1].getNumMeasurements(); i++) {
            temp[i] = stationArr[pos].getSensors()[pos1].getMeasurements()[i];
        }

        return temp;
    }

    /**
     * Mostra estatisticas de medidas de um determinado intervalo de tempo por
     * estacao
     *
     * @param ao operador
     * @param prmtr parametro
     * @param ldt data inicio
     * @param ldt1 data fim
     * @return estatisica da medida
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator ao, Parameter prmtr, LocalDateTime ldt, LocalDateTime ldt1) {
        IStatistics[] statistics = new IStatistics[nStations];

        int countStats = 0;

        switch (ao) {
            case AVG:
                //statistics[countStats] = avgMedidasBySensor(sensors);
                countStats++;
                break;
            case COUNT:

                break;

            case MIN:

                break;
            case MAX:

                break;
        }

        return statistics;
    }

    /**
     * Mostra estatisticas de medida por estacao
     *
     * @param ao operador
     * @param prmtr parametro
     * @return estatisitca da medida
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator ao, Parameter prmtr) {
        IStatistics[] statistics = new IStatistics[nStations];

        int countStats = 0;

        switch (ao) {
            case AVG:
                //statistics[countStats] = avgMedidasBySensor(sensors);
                countStats++;
                break;
            case COUNT:

                break;

            case MIN:

                break;
            case MAX:

                break;
        }

        return statistics;
    }

    /**
     *
     * @param string
     * @param ao
     * @param prmtr
     * @param ldt
     * @param ldt1
     * @return
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String string, AggregationOperator ao, Parameter prmtr, LocalDateTime ldt, LocalDateTime ldt1) {
        IStation station = getStation(string);
        ISensor[] sensors = station.getSensors();
        IStatistics[] statistics = new IStatistics[sensors.length];

        int countStats = 0;

        switch (ao) {
            case AVG:
                //statistics[countStats] = avgMedidasBySensor(sensors);
                countStats++;
                break;
            case COUNT:

                break;

            case MIN:

                break;
            case MAX:

                break;
        }

        return statistics;
    }

    /**
     * Mostra estatisticas de medidas de um determinado intervalo de tempo por
     * sensor
     *
     * @param string station id
     * @param ao operador
     * @param prmtr parametro
     * @return estatisitca da medida
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String string, AggregationOperator ao, Parameter prmtr) {
        IStation station = getStation(string);
        ISensor[] sensors = station.getSensors();
        IStatistics[] statistics = new IStatistics[sensors.length];

        int countStats = 0;

        for (ISensor sensor : sensors) {
            if (sensor.getParameter() == prmtr) {
                switch (ao) {
                    case AVG:
                        //statistics[countStats] = avgMedidasBySensor(sensors);
                        countStats++;
                        break;
                    case COUNT:

                        break;

                    case MIN:

                        break;
                    case MAX:

                        break;
                }
            }
        }
        return statistics;
    }

    public IStatistics avgMedidasBySensor(ISensor is) {

        double soma = 0;
        double total = 0;

        for (int i = 0; i < is.getNumMeasurements(); i++) {
            soma += is.getMeasurements()[i].getValue();
        }

        total = soma / is.getNumMeasurements();

        IStatistics stat = new Statistics("Media total do sensor : ", total);

        return stat;
    }

    /**
     * Esporta para json os dados da cidade em ficheiro e ecra
     *
     * @return dados da cidade
     * @throws IOException
     */
    @Override
    public String export() throws IOException {

        String fileName = "files/city.json";
        JSONArray jsonArr = new JSONArray();

        for (int i = 0; i < nStations; i++) {
            for (int j = 0; j < stationArr[i].getnSensores(); j++) {
                for (int k = 0; k < stationArr[i].getSensors()[j].getNumMeasurements(); k++) {

                    JSONObject jsonObj = new JSONObject();
                    JSONObject cordenadas = new JSONObject();

                    jsonObj.put("address", stationArr[i].getName());
                    jsonObj.put("id", stationArr[i].getSensors()[j].getId());
                    jsonObj.put("dateStandard", "UTC");
                    jsonObj.put("unit", stationArr[i].getSensors()[j].getParameter().getUnit().toString());
                    jsonObj.put("date", stationArr[i].getSensors()[j].getMeasurements()[k].getTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
                    jsonObj.put("value", stationArr[i].getSensors()[j].getMeasurements()[k].getValue());

                    cordenadas.put("x", stationArr[i].getSensors()[j].getCartesianCoordinates().getX());
                    cordenadas.put("y", stationArr[i].getSensors()[j].getCartesianCoordinates().getY());
                    cordenadas.put("z", stationArr[i].getSensors()[j].getCartesianCoordinates().getZ());
                    cordenadas.put("lat", stationArr[i].getSensors()[j].getGeographicCoordinates().getLatitude());
                    cordenadas.put("lng", stationArr[i].getSensors()[j].getGeographicCoordinates().getLongitude());
                    jsonObj.put("coordinates", cordenadas);

                    jsonArr.add(jsonObj);
                }

            }

        }

        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.write(jsonArr.toJSONString());
            fileWriter.close();
        } catch (IOException ex) {
            throw new IOException();
        }
        return jsonArr.toJSONString();
    }

}
