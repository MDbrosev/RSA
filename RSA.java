import static java.lang.System.*;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
/**
 *
 * @author Yoseph
 */
public class RSA {
    String fd,fm,fc,pHex,qHex,eHex,mHex,cHex;
    BigInteger p,q,e,m,c,n,d,pm,en,de,phi,gcd,phiP,phiQ,encrypt,decrypt,onePlusPhi;
    PrintWriter writer;

    public void writer() {
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            out.println("File Not Found or Unsupported Encoding Exception");
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void loadFile(String fileName) {
        File file = new File(fileName);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            pHex = br.readLine();
            qHex = br.readLine();
            eHex = br.readLine();
            mHex = br.readLine();
            cHex = br.readLine();
        } catch (FileNotFoundException ex) {
            out.println("File Not Found!");
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            out.println("IO Error!");
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeFile(String s) {        
        writer.println(s);      
    }
    
    public void toDecimal(){
        p = new BigInteger(pHex,16);
        q = new BigInteger(qHex,16);
        e = new BigInteger(eHex,16);
        m = new BigInteger(mHex,16);
        c = new BigInteger(cHex,16);
    }
    
    public void getN() {
        n = p.multiply(q);
    }
    
    public void bigIntToString(BigInteger i){
        String er = i.toString();
        encrypt = new BigInteger(er, 10);
        fd = encrypt.toString(16);
    }
    
    public void getGCD() {
        pm = new BigInteger("1");
        phiP = p.subtract(pm);
        phiQ = q.subtract(pm);
        phi = phiP.multiply(phiQ);
        gcd = e.gcd(phi);
    }
    
    public void getD() {
        d = e.modInverse(phi);
        String er = d.toString();
        encrypt = new BigInteger(er, 10);
        fd = encrypt.toString(16);
        writeFile(fd);
    }

    public void encrypt() {
        en = m.modPow(e, n);
        String er = en.toString();
        encrypt = new BigInteger(er, 10);
        fm = encrypt.toString(16);
        writeFile(fm);
    }
    
    public void decrypt() {
        de = c.modPow(d, n);
        String er = de.toString();
        decrypt = new BigInteger(er, 10);
        fc = decrypt.toString(16);
        writeFile(fc);
        writer.close();
    }
    
    public void runRSA(String inputFile) {
        loadFile(inputFile);
        writer();
        toDecimal();
        getN();
        getGCD();
        getD();
        encrypt();
        decrypt();
        out.println("Success! Output.txt has been created!");     
    }
    
    public static void main(String[] args) {
        RSA rsa = new RSA();
        rsa.runRSA("input.txt");
    }   
}
