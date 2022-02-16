/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityNStats;

import edu.ma02.core.interfaces.IGeographicCoordinates;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
*/
public class GeographicCoordinates implements IGeographicCoordinates{

    /**
     * Latitude
     */
    private double latitude;
    /**
     * Longitude
     */
    private double longitude;

    /**
     * Contrutor para coordenadas geograficas
     * @param latitude latitude
     * @param longitude longitude
     */
    public GeographicCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Getter para latitude
     * @return latitudue
     */
    @Override
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter para longitude
     * @return longitude
     */
    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * ToString das coordeanas
     * @return ToString das Coordenadas
     */
    @Override
    public String toString() {
        return "GeographicCoordinates{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
    }
    
    
}
