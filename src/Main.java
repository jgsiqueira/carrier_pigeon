import connectors.Corona;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Corona corona = new Corona();

        ArrayList<String> provinces = new ArrayList<>();
        provinces.add("BritishColumbia");
        provinces.add("Quebec");

        String data = corona.getData(null, provinces);
        System.out.println(data);
    }
}
