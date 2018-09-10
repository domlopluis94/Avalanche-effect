import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;
/**
 * 
 * @author Luis Dominguez Lopez
 * 
 */
public class TestVectors extends PANAMA {
	
	public static void panamaString(String texto) {
		byte[] salidaV;
		byte[] EntradaB = texto.getBytes();
		PANAMA pan = new PANAMA();
		pan.update(EntradaB);
		salidaV = pan.digest();
		System.out.println("HEXADECIMAL fin: "+DatatypeConverter.printHexBinary(salidaV));
	}
	
	public static void main (String[] args) throws NoSuchAlgorithmException
	{

		System.out.println("Vector de prueba 1");
		System.out.println("Vector = '' ");
		panamaString("");
		System.out.println("Verify");
		System.out.println("Vector de prueba 2");
		System.out.println("Vector = 'The quick brown fox jumps over the lazy dog' ");
		panamaString("The quick brown fox jumps over the lazy dog");
		System.out.println("Verify");		
	}	
}
