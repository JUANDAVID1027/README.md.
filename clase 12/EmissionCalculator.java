import java.util.ArrayList;
import java.util.List;

public class EmissionCalculator {

    // Factores de emisión (g CO₂/km)
    private static final double EMISSION_GASOLINE = 120.0;
    private static final double EMISSION_DIESEL   =  95.0;
    private static final double EMISSION_GAS      = 120.0;

    // Modelo para cada categoría de vehículo
    static class Category {
        String name;
        int totalVehicles;
        double pctGasoline; // en [0,1]
        double pctDiesel;
        double pctGas;

        Category(String name, int totalVehicles,
                 double pctGasoline, double pctDiesel, double pctGas) {
            this.name = name;
            this.totalVehicles = totalVehicles;
            this.pctGasoline = pctGasoline;
            this.pctDiesel   = pctDiesel;
            this.pctGas      = pctGas;
        }
    }

    public static void main(String[] args) {
        // Definimos todas las categorías con sus datos
        List<Category> categories = List.of(
            new Category("Camiones livianos", 400_000, 0.30, 0.40, 0.30),
            new Category("Camiones medianos", 280_000, 0.25, 0.50, 0.25),
            new Category("Camiones pesados", 380_000, 0.10, 0.80, 0.10),
            new Category("Tractocamiones",    1_150_000, 0.05, 0.90, 0.10),
            new Category("Volquetas",         936_000, 0.40, 0.40, 0.20)
        );

        System.out.printf("%-20s | %-10s | %-15s | %-15s%n",
                          "Categoría",
                          "Combustible",
                          "Emisión (g CO₂/km)",
                          "Emisión (t CO₂/km)");
        System.out.println("");

        for (Category cat : categories) {
            // Gasolina
            calcAndPrint(cat, "Gasolina", cat.pctGasoline, EMISSION_GASOLINE);
            // Diésel
            calcAndPrint(cat, "Diésel",   cat.pctDiesel,   EMISSION_DIESEL);
            // Gas
            calcAndPrint(cat, "Gas",      cat.pctGas,      EMISSION_GAS);
        }
    }

    private static void calcAndPrint(Category cat, String fuelType,
                                     double pct, double factorGPerKm) {
        int vehicles = (int)(cat.totalVehicles * pct);
        double totalGPerKm = vehicles * factorGPerKm;
        double totalTPerKm = totalGPerKm / 1_000_000.0; // de gramos a toneladas

        System.out.printf("%-20s | %-10s | %,15.0f | %,15.2f%n",
                          cat.name,
                          fuelType,
                          totalGPerKm,
                          totalTPerKm);
    }
}
