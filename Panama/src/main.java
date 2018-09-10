import java.awt.BorderLayout;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.xml.bind.DatatypeConverter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 * 
 * @author Luis Dominguez Lopez
 * 
 */
public class main {
	
	/**
	 *  
	 * @param C1
	 * @param C2
	 * @return retorna la distancia de haming entre los dos vectores 
	 */
	private static int hamming (byte[] C1, byte[] C2) {
		int conversion1 = 0, conversion2 = 0;
		int Hamming = 0;
		for(int i=0; i<C1.length; i++){		
			conversion1 = (int) C1[i] & 0x000000FF;
			conversion2 = (int) C2[i] & 0x000000FF;	
			Hamming += Integer.bitCount(conversion1 ^ conversion2);
		}
		return Hamming;
	}
	// Funcion que calcula la moda de Hamming
	/**
	 * 
	 * @param Hamming
	 * @return
	 */
		private static int moda(int[] Hamming){
			int moda = 0;
			int vecesEncontrado = 0;
			int maxVecesEncontrado = 0;
			
			for(int i=0; i<Hamming.length; i++){
				vecesEncontrado = 0;
				for(int j=0; j<Hamming.length; j++){
					if(Hamming[j]==Hamming[i])
						vecesEncontrado++;
				}
				if(vecesEncontrado > maxVecesEncontrado){
					moda = Hamming[i];
					maxVecesEncontrado = vecesEncontrado;
				}
			}
			
			return moda;
		}
		
		/**
		 * 
		 * @param Hamming
		 * @return
		 */
		private static int media(int[] Hamming){
			int media = 0;
			long acumulador = 0;
			
			for(int i=0; i<Hamming.length; i++)
				acumulador += Hamming[i];
			
			media = (int) (acumulador/Hamming.length);
			
			return media;
		}

		// Funcion que calcula la desviacion de Hamming
		private static float desviacion(int[] Hamming){
			float desviacion = 0;
			long acumulador = 0;
			int media = media(Hamming);
			
			for(int i=0; i<Hamming.length; i++)
				acumulador += Math.pow(Hamming[i]-media, 2);
			
			desviacion = (float) Math.sqrt(acumulador/Hamming.length);
			
			return desviacion;
		}
		
		// Funcion que calcula la asimetria de Hamming
		/**
		 * 
		 * @param Hamming
		 * @return
		 */
		private static float asimetria(int[] Hamming){
			float asimetria = 0;
			int moda = moda(Hamming);
			int media = media(Hamming);
			float desviacion = desviacion(Hamming);
			asimetria = (media-moda)/desviacion;				
			return asimetria;
		}
		
		public static void medidaEstadistica (int [] HammingR , DefaultCategoryDataset Datos,DefaultCategoryDataset Datos2) {
			JFreeChart Grafica;
			JFreeChart Grafica2;
			// Se muestran las medidas estadisticas
			int modaH = moda(HammingR);
			int mediaH = media(HammingR);
			float desviacionH = desviacion(HammingR);
			float asimetriaH = asimetria(HammingR);
						
			System.out.println("Moda: " + modaH);
			System.out.println("Media: " + mediaH);
			System.out.println("Desviacion: " + desviacionH);
			System.out.println("Asimetria: " + asimetriaH);
			
			// Dibujar graficas
			Grafica = ChartFactory.createBarChart("Panama", "Pruebas", "Distancias de Hamming", Datos, PlotOrientation.VERTICAL, true, true, false);
			Grafica2 = ChartFactory.createBarChart("Panama", "Pruebas", "Probabilidades de distribucion", Datos2, PlotOrientation.VERTICAL, true, true, false);
			
			ChartPanel Panel = new ChartPanel(Grafica);
			ChartPanel Panel2 = new ChartPanel(Grafica2);
			
			JFrame Ventana = new JFrame("Estudio estadistico Panama");
			Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Ventana.getContentPane().add(Panel, BorderLayout.CENTER);
			Ventana.pack();
			Ventana.setVisible(true);
			
			JFrame Ventana2 = new JFrame("Estudio estadistico Panama");
			Ventana2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Ventana2.getContentPane().add(Panel2, BorderLayout.CENTER);
			Ventana2.pack();
			Ventana2.setVisible(true);
		}
	
	public static void main (String[] args) throws NoSuchAlgorithmException
	{

		DefaultCategoryDataset Datos = new DefaultCategoryDataset();
		DefaultCategoryDataset Datos2 = new DefaultCategoryDataset();
		int[] HammingR = new int[256];	
		// array para las pruebas
		int hPunt = 0;
		// var para los valores haming 
		float probabilidad[] = new float[256];
		// array para la distribucion de probabilidades 
		byte [] entrada = new byte [32] ;
		byte[] salida = new byte [32];
		byte[] salidaH = new byte [32];
		for(int i=0; i<32; i++)
			entrada[i] = 0;
		
		PANAMA pan = new PANAMA();
		salida = pan.digest(entrada);
		
		for(int i=0; i<32; i++){
			for(int j=0; j<8; j++){
				entrada[i] = (byte) Math.pow(2, j);
				// Pasamos a la funcion el complementado convertido a cadena de bytes como parametro
				salidaH = pan.digest(entrada);
				// Calculamos y guardamos la distancia de Hamming respecto a la primera salida de la funcion 
				HammingR[hPunt] = hamming(salida, salidaH);
				
				Datos.addValue(HammingR[hPunt], "Salidas de la funcion Panama", Integer.toString(hPunt+1));
				System.out.println("Distancia Hamming: " + HammingR[hPunt]);
				hPunt++;
				// Mostramos por terminal los datos obtenidos
				System.out.println("HEXADECIMAL original: "+DatatypeConverter.printHexBinary(entrada));
				System.out.println("HEXADECIMAL resultante: "+DatatypeConverter.printHexBinary(salidaH));
				System.out.println();
			}
			entrada[i] = (byte) 0;
		}
		for(int i=0; i<HammingR.length; i++){
			int vecesAparece = 0;
			for(int j=0; j<HammingR.length; j++){
				if(HammingR[j]==HammingR[i]){
					vecesAparece++;
				}
			}
			probabilidad[i]=(float)vecesAparece/(float)HammingR.length;
			Datos2.addValue(probabilidad[i], "Distribuciones de probabilidad", Integer.toString(i+1));
		}
		medidaEstadistica(HammingR,Datos,Datos2);			
	
	}
}
