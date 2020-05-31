package com.HLF.World;

public class Camera {
	
	/*VARIAVEIS*/
	
	public static int x = 0, y = 0;
	
	public static int clamp(int Atual, int Min, int Max) {
		if(Atual < Min) {
			Atual = Min;
		} 
		
		if(Atual > Max) {
			Atual = Max;
		}
		
		return Atual;
			
	}
	
	/***/
	

}
