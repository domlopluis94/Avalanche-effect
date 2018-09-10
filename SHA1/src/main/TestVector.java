package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class TestVector {
	public static void SHA1String(String texto) throws NoSuchAlgorithmException {
		byte[] salidaV;
		byte[] EntradaB = texto.getBytes();
		MessageDigest pan = null;
		pan = MessageDigest.getInstance("SHA-1");
		pan.update(EntradaB);
		salidaV = pan.digest();
		System.out.println("HEXADECIMAL fin: "+DatatypeConverter.printHexBinary(salidaV));
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		System.out.println("Vector de prueba 1");
		System.out.println("Vector = 'abc' ");
		SHA1String("abc");
		System.out.println("Verify (a9993e36 4706816a ba3e2571 7850c26c 9cd0d89d)");
		System.out.println("Vector de prueba 2");
		System.out.println("Vector = '' ");
		SHA1String("");
		System.out.println("Verify (da39a3ee 5e6b4b0d 3255bfef 95601890 afd80709)");
		System.out.println("Vector de prueba 3");
		System.out.println("Vector = 'abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq' ");
		SHA1String("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");
		System.out.println("Verify (84983e44 1c3bd26e baae4aa1 f95129e5 e54670f1)");
		System.out.println("Vector de prueba 4");
		System.out.println("Vector = 'abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu' ");
		SHA1String("abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu");
		System.out.println("Verify (a49b2446 a02c645b f419f995 b6709125 3a04a259)");
	}

	
}
