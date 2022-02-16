/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import CityNStats.CartesianCoordinates;
import CityNStats.City;
import CityNStats.GeographicCoordinates;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
 */
public class Importer implements IImporter {

    /**
     * Estatisticas do import
     */
    IOStatistic stats = new IOStatistic();

    /**
     * Getter para estatisitcas
     *
     * @return estatisiticas
     */
    public IOStatistic getStats() {
        return stats;
    }

    /**
     * Importa dados para uma cidade de um ficheiro Json
     *
     * @param icity cidade
     * @param path caminho do ficheiro
     * @return estatisticas da importacao
     * @throws FileNotFoundException ficheiro nao encontrado
     * @throws IOException ficheiro nao existe
     * @throws CityException cidade nao existe
     */
    @Override
    public IOStatistics importData(ICity icity, String path) throws FileNotFoundException, IOException, CityException {

        if (icity == null) {
            throw new CityException("Cidade invalida");
        } else {
            City cidade = (City) icity;
            int countNStations = cidade.getnStations();
            int countNSensores = cidade.countSensores();
            int countNMedidas = cidade.countMedidas();
            int countNewSensores = 0;
            int countNewMedidas = 0;

            JSONParser parser = new JSONParser();

            try {
                Reader reader = new FileReader(path);
                JSONArray array = (JSONArray) parser.parse(reader);
                int countNewStations = 0;

                for (int i = 0; i < array.size() - 1; i++) {
                    JSONObject obj1 = (JSONObject) array.get(i);

                    try {
                        String s = (obj1.get("address").toString());
                        if (cidade.addStation(s) == true) {
                            countNewStations++;
                        }
                    } catch (CityException ex) {
                        System.out.println("Estacao ja existe");
                    } catch (NullPointerException ex) {
                        System.out.println("Erro de apontador");
                    }
                }
                stats.setNumberOfNewStationsRead(countNewStations);
                stats.setNumberOfStationsRead(countNStations + countNewStations);

                for (int i = 0; i < array.size() - 1; i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    try {
                        JSONObject cords = (JSONObject) obj.get("coordinates");
                        String iccXString = cords.get("x").toString();
                        double iccX = Double.parseDouble(iccXString);
                        String iccYString = cords.get("y").toString();
                        double iccY = Double.parseDouble(iccYString);
                        String iccZString = cords.get("z").toString();
                        double iccZ = Double.parseDouble(iccZString);

                        String igc2String = cords.get("lat").toString();
                        double igc2 = Double.parseDouble(igc2String);
                        String igc3String = cords.get("lng").toString();
                        double igc3 = Double.parseDouble(igc3String);

                        ICartesianCoordinates icc1 = new CartesianCoordinates(iccX, iccY, iccZ);
                        IGeographicCoordinates igc1 = new GeographicCoordinates(igc2, igc3);

                        if (icity.addSensor(obj.get("address").toString(), obj.get("id").toString(), icc1, igc1)) {
                            countNewSensores++;
                        }
                    } catch (StationException | SensorException ex) {
                        System.out.println("Erro ao adicionar sensor importado");
                    }
                }
                stats.setNumberOfSensorsRead(countNSensores);
                stats.setNumberOfNewSensorsRead(countNSensores - countNewSensores);

                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    String dateTemp = obj.get("date").toString();
                    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                    LocalDateTime dateTime = LocalDateTime.parse(dateTemp, pattern);

                    String valor1 = obj.get("value").toString();
                    double value = Double.parseDouble(valor1);

                    try {
                        if (icity.addMeasurement(obj.get("address").toString(), obj.get("id").toString(), value, obj.get("unit").toString(), dateTime) == true) {
                            countNewMedidas++;
                        }
                    } catch (StationException | SensorException | MeasurementException ex) {
                        System.out.println(ex.getMessage());
                    } catch (NullPointerException ex) {
                        System.out.println("Erro de apontador medida");
                    }
                }

            } catch (IOException | ParseException ex) {
                System.out.println(ex.getMessage());
            }
            stats.setNumberOfReadMeasurements(countNMedidas + countNewMedidas);
            stats.setNumberOfNewMeasurementsRead(countNewMedidas);

            return stats;
        }
    }

}
