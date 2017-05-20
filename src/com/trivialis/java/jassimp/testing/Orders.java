/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trivialis.java.jassimp.testing;

/**
 *
 * @author frank
 */
public class Orders {
    
    public static void main(String[] args) {
        for(int i = 0 ; i<16; ++i) {
            System.out.println(i);
        }
        
        Double d1 = 2.0D;
        Float d2 = 1.0F;
        System.out.println(((Number)(((double)d1)*((float)d2))).getClass());
        System.out.println(((Number)(((float)d2)*((double)d1))).getClass());
    }
    
}
