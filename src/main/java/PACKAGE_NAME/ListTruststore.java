package PACKAGE_NAME;




import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ListTruststore {
    public static void main(String[] args) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyStore trustStore = KeyStore.getInstance("BKS", "BC");
            try (FileInputStream fis = new FileInputStream("/Users/brandonluismenesessolorzano/Desktop/TrustStore/truststore.bks")) {
                trustStore.load(fis, "changeit".toCharArray());
            }

            Enumeration<String> aliasesEnum = trustStore.aliases();
            List<String> aliasesList = new ArrayList<>();
            while (aliasesEnum.hasMoreElements()) {
                aliasesList.add(aliasesEnum.nextElement());
            }

            for (String alias : aliasesList) {
                Certificate cert = trustStore.getCertificate(alias);
                System.out.println("Alias: " + alias);
                System.out.println("Certificate: " + cert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
