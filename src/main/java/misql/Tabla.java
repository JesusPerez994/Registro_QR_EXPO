/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misql;

/**
 *
 * @author jesue
 */
public interface Tabla {
    abstract String getId();
    
    default String withId(){
        if (this.getId()==null) {
            return this.toString();
        }
        return String.format("%s %s", this,this.getId());
    }

}
