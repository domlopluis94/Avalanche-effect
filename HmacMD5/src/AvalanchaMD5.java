/**
 * @author Luis Dominguez Lopez
 */
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import javax.swing.JOptionPane;
public class AvalanchaMD5 {
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
		//System.out.println("\n\nnumero de bits totales para la media de hamming : " + b.length);
		//255 bits de encriptacion
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
	 * distancia de hamming a nivel de caracter
	 * @param texto
	 * @param texto2
	 * @return dist de hamming
	 */
	public static int distanciadeHamingtext (String texto, String texto2){
		//System.out.println("\n\nnumero de bits totales para la media de hamming : " + b.length);
		//255 bits de encriptacion
		char b[]; 
		b = texto.toCharArray();
		System.out.println("\n\nnumero de bits totales para la media de hamming : " + b.length);
		//caracteres de encriptacion
		char b2[];
		b2 = texto2.toCharArray();
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
		int conthamingtxt[] = new int [fin];
		int contHam[] = new int [fin];
		for(it=1;it<fin;it++){
			textos= palabraAleatoria(10);
			//efectoavalanchade(textos[it]);
			contHam[it]= distanciadeHaming(encriptaEnMD5(textos),encriptaEnMD5(avalancha(textos)));
			conthamingtxt[it]=distanciadeHamingtext(encriptaEnMD5(textos),encriptaEnMD5(avalancha(textos)));
		}
		System.out.println("\n\nmedia de hamming : " + media(contHam));
		System.out.println("\n\nmediatext de hamming : " + media(conthamingtxt));
		System.out.println("\n\nmoda de hamming : " + moda(contHam));
		System.out.println("\n\nmodatext de hamming : " + moda(conthamingtxt));
		System.out.println("\n\nvarianza de hamming : " + varianza(contHam));
		System.out.println("\n\nvarianzatext de hamming : " + varianza(conthamingtxt));
		System.out.println("\n\ndesviacion tipica de hamming : " + Math.sqrt(varianza(contHam)));
		System.out.println("\n\ndesviaciontxt tipica de hamming : " + Math.sqrt(varianza(conthamingtxt)));
		//System.out.println("\n\ncurtosis de hamming : " + curtosis(contHam));
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
	/**
	 * moda de un array de datos
	 * @param datos
	 * @return
	 */
	public static int moda(int[] datos){

		Map<Integer, Integer> dato = new HashMap<>();
		int[] keys =new int [datos.length];
		int i;
		int cont = 0;
		int moda=0;
		for(i=0;i<datos.length;i++){
			if(dato.containsKey(datos[i])){
				dato.put(datos[i], (dato.get(datos[i]) + 1));
			}else{
				dato.put(datos[i], 1);
				keys[cont]=datos[i];
				cont++;
			}
		}
		int[] key2 =new int [cont];
		for(i=0;i<cont;i++){
			key2[i]=keys[i];
		}
		int j=0;
		for(i=0;i<key2.length-1 || i==cont-1 ;i++){
/*			System.out.println("\n\nmoda error cont" + cont);
			System.out.println("\n\nmoda error " + i);
			System.out.println("\n\nmoda error keyslength" + key2.length);*/
			if( dato.get(key2[j]) > dato.get(key2[i]) ){
				moda=key2[j];
			}else{
				j=i;
			}
		}
		return moda;
	}
	/**
	 * La varianza es la media aritmética del cuadrado de las desviaciones respecto a la media
	 * @param datos
	 * @return
	 */
	public static double varianza(int[] datos){
		double num = 0;
		int i;
		for(i=0;i<datos.length-1;i++){
			num+=(datos[i]-media(datos))^2;
		}
		return num/datos.length;	
	}
	/**
	 * La curtosis se mide promediando la cuarta potencia de la diferencia entre cada elemento del conjunto y la media, dividido entre la desviacin tpica elevado tambin a la cuarta potencia.
	 * @param datos
	 * @return
	 */
	public static double curtosis(int[] datos){
		int media= media(datos);
		double desvacion= Math.sqrt(varianza(datos));
		double desvacionf= desvacion*desvacion*desvacion*desvacion;
		double num = 0;
		int i;
		for(i=0;i<datos.length-1;i++){
			num+=(datos[i]-media)^4;
		}
		return (num/desvacionf)-3;
	}
	/**
	 * efecto avalanchas obre un texto determinado
	 * @param texto
	 */
	public static void efectoavalanchade(String texto){
		System.out.println("\n\nTexto : " + texto);
		System.out.println("\n\nSuma de bits: "+ avalancha(texto) +"'");
		System.out.println("\n\nEncriptacion en MD5 de texto1: '"+encriptaEnMD5(texto)+"'");
		System.out.println("\n\nEncriptacion en MD5 de texto1 + 1 bti: '"+encriptaEnMD5(avalancha(texto))+"'");
		System.out.println("\n\n distancia de hamming " + distanciadeHaming(encriptaEnMD5(texto),encriptaEnMD5(avalancha(texto))) + " ." );
	}

	public static void main(String[] args) throws Exception {
		System.out.println("\n\nejem 10 : " + palabraAleatoria(10));
		efectoavalancha(10);
		System.out.println("\n\nejem 100 : ");
		efectoavalancha(100);
		System.out.println("\n\nejem 1000 : ");
		efectoavalancha(1000);
		System.out.println("\n\nejem 10000 : ");
		efectoavalancha(10000);
		System.out.println("\n\nejem 100000 : ");
		efectoavalancha(100000);
		System.out.println("\n\nejem 1000000 : ");
		efectoavalancha(1000000);
		System.out.println("\n\nejem 10000000 : ");
		efectoavalancha(10000000);
	}
}
