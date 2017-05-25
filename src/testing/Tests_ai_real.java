/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import com.trivialis.java.jassimp.port.include.assimp.defs;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;

/**
 *
 * @author frank
 */
public class Tests_ai_real {
    
    public static void main(String[] args) {
        defs.MODE=defs.Mode.DOUBLE;
        System.out.println(new ai_real(Math.PI).opMultiply(1000.0F));
        defs.MODE=defs.Mode.FLOAT;
        System.out.println(new ai_real(Math.PI).opMultiply(1000.0F));
    }
    
}
