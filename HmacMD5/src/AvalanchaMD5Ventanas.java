import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import javax.swing.JOptionPane;

public class AvalanchaMD5Ventanas {

	private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
	/**
	 * funcion que encripta un string en MD5
	 * @param stringAEncriptar
	 * @return retorna el texto encriptado
	 */
	public static String encriptaEnMD5(String stringAEncriptar)
	{
		try
		{
			MessageDigest msgd = MessageDigest.getInstance("MD5");
			byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
			StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
			for (int i = 0; i < bytes.length; i++)
			{
				int bajo = (int)(bytes[i] & 0x0f);
				int alto = (int)((bytes[i] & 0xf0) >> 4);
				strbCadenaMD5.append(CONSTS_HEX[alto]);
				strbCadenaMD5.append(CONSTS_HEX[bajo]);
			}
			return strbCadenaMD5.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	/**
	 * funcion que suma 1 al ultimo bit de un texto
	 * @param texto
	 * @return texto con la suma de 1 al ultimo bit
	 */
	public static String avalancha (String texto){
		int iterador;
		//byte[] b = texto.getBytes(StandardCharsets.UTF_8); // Java 7+ only
		String binary = new BigInteger(texto.getBytes()).toString(2);
		char[] b = binary.toCharArray();
		if(b[b.length-1]=='0'){
			b[b.length-1]='1';
		}else{
			b[b.length-1]='0';
			for(iterador= 2; iterador > 0 ;iterador++){
				if(b[b.length-iterador]=='0'){
					b[b.length-iterador]='1';
					//System.out.println("As binary: cambia 1");
					break;
				}else{
					b[b.length-iterador]='0';
					//System.out.println("As binary: 0 ");
				}
			}
		}
		String st = String.valueOf(b);	

		String text3 = new String(new BigInteger(st, 2).toByteArray());
		return text3; 

	}
	/**
	 * calcula los bits iguales dentro de dos textos
	 * @param texto
	 * @param texto2
	 * @return distancia de hamming
	 */
	public static int distanciadeHaming (String texto, String texto2){
		String binary = new BigInteger(texto.getBytes()).toString(2);
		char[] b = binary.toCharArray();
		String binary2 = new BigInteger(texto2.getBytes()).toString(2);
		char[] b2 = binary2.toCharArray();
		int cont=0;
		int i=0;
		while(i<b.length-1){
			if(b2[i]==b[i]){
				cont++;
			}
			i++;
		}
		return cont;
	}
	/**
	 * 
	 * @param valores
	 * @return retorna la media de valores de un array de enteros
	 */
	public static int media( int [] valores){
		int i;
		int f= valores.length;
		int media = 0;
		for(i=0; i<f;i++){
			media+= valores[i];
		}	
		return media/f;	
	}

	/**
	 * funcion que realiza pruebas sobre md5 y imprime las medias de hamming
	 * @param nejemplos a realizar
	 */
	public static void efectoavalancha(int nejemplos){
		int it;
		int fin = nejemplos;
		String textos;

		int contHam[] = new int [fin];
		for(it=1;it<fin;it++){
			textos= palabraAleatoria(10);
			//efectoavalanchade(textos[it]);
			contHam[it]= distanciadeHaming(encriptaEnMD5(textos),encriptaEnMD5(avalancha(textos)));
		}
		JOptionPane.showMessageDialog(null, "media de hamming :"+media(contHam));
	}
	/**
	 * 
	 * @param tamañop
	 * @return palabra del tamaño indicado y aleatoria
	 */
	public static String palabraAleatoria(int tamañop){

		String [] abecedario = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J","K", "L", "M","N","O","P","Q","R","S","T","U","V","W", "X","Y","Z" };
		int num1=0;
		int num2=26;
		int numAleatorio=(int)Math.floor(Math.random()*(num1-num2)+num2);
		String result = abecedario[numAleatorio];
		for(int tam=0; tam<tamañop ;tam++){
			numAleatorio=(int)Math.floor(Math.random()*(num1-num2)+num2);
			result = abecedario[numAleatorio] + result;
		}
		return result;

	}
	
	public static int moda(int[] datos){
		
		Map<Integer, Integer> dato = new HashMap<>();
		int[] keys =new int [datos.length];
		int i;
		int cont = 0;
		int moda=0;
		for(i=0;i<datos.length;i++){
				if(dato.containsKey(datos[i])){
					dato.put(datos[i], dato.get(datos[i])+ 1);
				}else{
					dato.put(datos[i], 1);
					keys[cont]=datos[i];
					cont++;
				}
		}
		int j=0;
		for(i=0;i<keys.length || i==cont ;i++){
			if(dato.get(keys[j])>dato.get(keys[i])){
				moda=dato.get(keys[j]);
			}else{
				j=i;
			}
		}
		return moda;
	}
	public static void efectoavalanchade(String texto){
		System.out.println("\n\nTexto : " + texto);
		System.out.println("\n\nSuma de bits: "+ avalancha(texto) +"'");
		System.out.println("\n\nEncriptacion en MD5 de texto1: '"+encriptaEnMD5(texto)+"'");
		System.out.println("\n\nEncriptacion en MD5 de texto1 + 1 bti: '"+encriptaEnMD5(avalancha(texto))+"'");
		System.out.println("\n\n distancia de hamming " + distanciadeHaming(encriptaEnMD5(texto),encriptaEnMD5(avalancha(texto))) + " ." );
	}

	public static void main(String[] args) throws Exception {
		String seleccion = JOptionPane.showInputDialog( null,"Introducir el numero de ejemplos ",JOptionPane.QUESTION_MESSAGE);
		int val = Integer.parseInt(seleccion) ;
		efectoavalancha(val);
		String seleccion2 = JOptionPane.showInputDialog( null,"Introducir frase a comprimir",JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(null, encriptaEnMD5(seleccion2));
		JOptionPane.showMessageDialog(null, "\n\nejem 10 : ");
		efectoavalancha(10);
/*		JOptionPane.showMessageDialog(null, "\n\nejem 100 : ");
		efectoavalancha(100);
		JOptionPane.showMessageDialog(null, "\n\nejem 1000 : ");
		efectoavalancha(1000);
		JOptionPane.showMessageDialog(null, "\n\nejem 10000 : ");
		efectoavalancha(10000);
		JOptionPane.showMessageDialog(null, "\n\nejem 100000 : ");
		efectoavalancha(100000);
		JOptionPane.showMessageDialog(null, "\n\nejem 1000000 : ");
		efectoavalancha(1000000);
		JOptionPane.showMessageDialog(null, "\n\nejem 10000000 : ");
		efectoavalancha(10000000);
*/
		while(true){
			String seleccion3 = JOptionPane.showInputDialog( null,"Introducir el numero de ejemplos (introducir 0 para terminar)",JOptionPane.QUESTION_MESSAGE);
			int val3 = Integer.parseInt(seleccion3) ;
			if(val3==0){
				break;
			}
			efectoavalancha(val3);
			String seleccion4 = JOptionPane.showInputDialog( null,"Introducir frase a comprimir",JOptionPane.QUESTION_MESSAGE);
			JOptionPane.showMessageDialog(null, encriptaEnMD5(seleccion4));
		}

	}
}

