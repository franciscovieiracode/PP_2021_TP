/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityNStats;

import edu.ma02.core.interfaces.ICartesianCoordinates;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
*/
public class CartesianCoordinates implements ICartesianCoordinates{

    /**
     * Corrdenada X
     */
    private double x;
    /**
     * Coordenada Y
     */
    private double y;
    /**
     * Coordenada Z
     */
    private double z;

    /**
     * Contrutor para Coordenadas Cartesianas
     * @param x
     * @param y
     * @param z 
     */
    public CartesianCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Getter para X
     * @return X
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * Getter para Y
     * @return Y
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * Getter para Z
     * @return Z
     */
    @Override
    public double getZ() {
        return z;
    }

    /**
     * ToString para Cordenadas Cartesianas
     * @return String das cordenadas
     */
    @Override
    public String toString() {
        return "CartesianCoordinates{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
    
    
    
}
