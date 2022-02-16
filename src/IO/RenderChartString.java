/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import CityNStats.City;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.interfaces.ICity;

/*
* Nome: <Francisco Joao Moreira Vieira>
* Número: <8170464>
* Turma: <LEI1T2>
*/
public class RenderChartString {
    
    /**
     * String json do grafico
     */
    private String vis;


    /**
     * Getter para json do grafico
     * @return json do grafico
     */
    public String getVis() {
        return vis;
    }

    /**
     * Setter para String json
     * @param vis string json
     */
    public void setVis(String vis) {
        this.vis = vis;
    }

    /**
     * 
     * @param icity cidade 
     * @return string com o json
     */
    public String buildStringChart(ICity icity){
        
        
        int count1=0;
        int count2=0;
        int count3=0;
        City cidade = (City) icity;
        
        for (int i =0;i<cidade.getnStations();i++){
            for (int j =0;j<cidade.getStations()[i].getSensors().length-1;j++){
                if (cidade.getStations()[i].getSensors()[j].getType()== SensorType.AIR)
                    count1++;
                else if (cidade.getStations()[i].getSensors()[j].getType()== SensorType.WEATHER)
                    count2++;
                else{
                    count3++;
                }
            }
        }
        
        String part1 ="{type:'pie',data:{labels:['AIR','WEATHER','NOISE'], datasets:[{label:'Quantidade de tipo de sensores',data:[";
        String part2 = count1+","+count2+","+count3;
        String part3 = "]}]}}";
        
        this.vis = part1+part2+part3;
        
        return vis;
        
    }
    
    
}
