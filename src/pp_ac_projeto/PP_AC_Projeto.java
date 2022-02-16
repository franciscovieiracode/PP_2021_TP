/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp_ac_projeto;

import CityNStats.CartesianCoordinates;
import CityNStats.City;
import CityNStats.GeographicCoordinates;
import CityNStats.Sensor;
import CityNStats.Station;
import IO.Importer;
import IO.RenderChartString;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.dashboards.Dashboard;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franc
 */
public class PP_AC_Projeto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Station stat1 = new Station("felgueiras");
        Station stat2 = new Station("amarante");
        City cit1 = new City("123", "porto");
        IGeographicCoordinates coord1 = new GeographicCoordinates(2222, 2223);
        IGeographicCoordinates coord2 = new GeographicCoordinates(222231200, 222321000);
        ICartesianCoordinates carte1 = new CartesianCoordinates(1, 2, 3);
        Sensor sen1 = new Sensor("QA00NO2001", SensorType.AIR, Parameter.CO, carte1, coord1);
        Sensor sen2 = new Sensor("QA00NO2002", SensorType.AIR, Parameter.CO, carte1, coord1);
        Sensor sen3 = new Sensor("QA00NO2003", SensorType.AIR, Parameter.CO, carte1, coord1);

        try {
            System.out.println(cit1.addStation("felgueiras"));
        } catch (CityException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println(cit1.addSensor("felgueiras", sen2.getId(), carte1, coord2));
        } catch (CityException | StationException | SensorException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(cit1.addStation("amarante"));
        } catch (CityException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println(cit1.addSensor("felgueiras", sen1.getId(), carte1, coord1));
        } catch (CityException | StationException | SensorException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(cit1.addSensor("amarante", sen1.getId(), carte1, coord1));
        } catch (CityException | StationException | SensorException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(cit1.addSensor("felgueiras", sen3.getId(), carte1, coord1));
        } catch (CityException | StationException | SensorException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(cit1.addMeasurement("felgueiras", "QA00NO2001", 5, "UG", LocalDateTime.now()));
        } catch (CityException | StationException | SensorException | MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(cit1.addMeasurement("amarante", "QA00NO2001", 5, "UG", LocalDateTime.now()));
        } catch (CityException | StationException | SensorException | MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(cit1.addMeasurement("felgueiras", "QA00NO2002", 10, "UG", LocalDateTime.now()));
        } catch (CityException | StationException | SensorException | MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(cit1.addMeasurement("felgueiras", "QA00NO2003", 15, "UG", LocalDateTime.now()));
        } catch (CityException | StationException | SensorException | MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(cit1.export());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        String s = "";

        System.out.println("123");

        Importer imp1 = new Importer();
        try {
            imp1.importData(cit1, "files/importJson.json");
        } catch (IOException | CityException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(imp1.getStats().getNumberOfNewMeasurementsRead());

        
        //Abre no browser um grafico que representa o tipo de sensor com mais 
        //apariçoes
        
        RenderChartString ren = new RenderChartString();

        String p = ren.buildStringChart(cit1);

        String[] vis = {p};

        try {
            Dashboard.render(vis);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


    }

}
