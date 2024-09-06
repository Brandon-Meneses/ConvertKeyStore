

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.Security;
import java.util.Enumeration;

public class ConvertKeyStore {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // Actualiza esta ruta a la ubicación correcta de tu archivo truststore.jks
        String jksPath = "/Users/brandonluismenesessolorzano/Desktop/TrustStore/JKS/truststore2.jks";
        String bksPath = "/Users/brandonluismenesessolorzano/Desktop/TrustStore/truststore2.bks";

        // Load the JKS keystore
        FileInputStream jksInputStream = new FileInputStream(jksPath);
        KeyStore jksKeyStore = KeyStore.getInstance("JKS");
        jksKeyStore.load(jksInputStream, "changeit".toCharArray());
        jksInputStream.close();

        // Create a BKS keystore
        KeyStore bksKeyStore = KeyStore.getInstance("BKS", "SC");
        bksKeyStore.load(null, "changeit".toCharArray());

        // Copy the entries from the JKS keystore to the BKS keystore
        Enumeration<String> aliases = jksKeyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            if (jksKeyStore.isKeyEntry(alias)) {
                KeyStore.Entry entry = jksKeyStore.getEntry(alias, new KeyStore.PasswordProtection("changeit".toCharArray()));
                bksKeyStore.setEntry(alias, entry, new KeyStore.PasswordProtection("changeit".toCharArray()));
            } else if (jksKeyStore.isCertificateEntry(alias)) {
                java.security.cert.Certificate cert = jksKeyStore.getCertificate(alias);
                bksKeyStore.setCertificateEntry(alias, cert);
            }
        }

        // Save the BKS keystore
        FileOutputStream bksOutputStream = new FileOutputStream(bksPath);
        bksKeyStore.store(bksOutputStream, "changeit".toCharArray());
        bksOutputStream.close();
    }
}