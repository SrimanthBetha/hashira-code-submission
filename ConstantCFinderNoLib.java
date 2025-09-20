import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class ConstantCFinderNoLib {
    public static void main(String[] args) {
        try {
            // 1️⃣ Read the entire JSON file as text
            String filename = "roots.json"; // name of your JSON file
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
            br.close();
            String json = sb.toString();

            // 2️⃣ Extract n and k
            long n = Long.parseLong(json.split("\"n\"\\s*:\\s*")[1].split("[,}]")[0]);
            long k = Long.parseLong(json.split("\"k\"\\s*:\\s*")[1].split("[,}]")[0]);
            long m = k - 1;  // degree of polynomial

            // 3️⃣ Extract all root entries ("1", "2", ...)
            Map<String, String[]> rootsMap = new TreeMap<>(); // keep order
            for (int i = 1; i <= 100; i++) { // max 100 roots
                String key = "\"" + i + "\"";
                if (!json.contains(key)) break;

                String part = json.split(key)[1];
                String base = part.split("\"base\"\\s*:\\s*\"")[1].split("\"")[0];
                String value = part.split("\"value\"\\s*:\\s*\"")[1].split("\"")[0];
                rootsMap.put(key, new String[]{base, value});
            }

            // 4️⃣ Convert first k roots to BigInteger and calculate product
            BigInteger product = BigInteger.ONE;
            int count = 0;
            for (String[] entry : rootsMap.values()) {
                int base = Integer.parseInt(entry[0]);
                String valueStr = entry[1];
                BigInteger val = new BigInteger(valueStr, base);
                product = product.multiply(val);
                count++;
                if (count == k) break;
            }

            // 5️⃣ Apply sign for constant term
            if (m % 2 != 0) product = product.negate();

            // 6️⃣ Print only c
            System.out.println(product);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
