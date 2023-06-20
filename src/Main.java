import java.util.*;

public class Main {
    private static List<Product> inventory = new ArrayList<>();
    private static Map<Integer, Product> inventoryMap = new HashMap<>();

    public static void main(String[] args) {
        addProduct(new Product(1, "Telefon", 999.99, 10));
        addProduct(new Product(1, "Telefon", 1020.00, 5));
        addProduct(new Product(2, "Laptop", 1999.99, 5));
        addProduct(new Product(3, "Televizor", 2499.99, 3));
        addProduct(new Product(4, "Căști", 99.99, 20));
        addProduct(new Product(5, "Mouse", 19.99, 15));
        addProduct(new Product(6, "Tastatură", 49.99, 10));
        addProduct(new Product(7, "Monitor", 499.99, 8));
        addProduct(new Product(8, "Hard disk extern", 149.99, 12));
        addProduct(new Product(9, "Imprimantă", 299.99, 6));
        addProduct(new Product(10, "Router", 79.99, 10));
        updateStock(7, 5);
        updateStock(3, 2);
        deleteProduct(8);

        printAvailableProducts();
        Product foundProduct = searchProductByName("Telefon");
    }

    /*Daca produsul exista - se recalculeaza pretul si stocul, astfel evitam dublarea produselor in
    ListArray*/
    public static void addProduct(Product product) {
        if (inventoryMap.containsKey(product.getId())) {
            Product existingProduct = inventoryMap.get(product.getId());
            int updatedStock = existingProduct.getStock() + product.getStock();
            double updatedPrice =
                    (existingProduct.getPrice() * existingProduct.getStock() + product.getPrice() * product.getStock()) / updatedStock;

            existingProduct.setStock(updatedStock);
            existingProduct.setPrice(updatedPrice);
        } else {
            inventory.add(product);
            inventoryMap.put(product.getId(), product);
        }
    }

    public static void updateStock(int productId, int newStock) {
        if (inventoryMap.containsKey(productId)) {
            inventoryMap.get(productId).setStock(newStock);
        } else System.out.println(String.format("Produs cu id=%d nu exista", productId));
    }

    public static void deleteProduct(int productId) {
        if (inventoryMap.containsKey(productId)) {
            Product productToRemove = inventoryMap.get(productId);
            if (productToRemove != null) {
                inventory.remove(productToRemove);
                inventoryMap.remove(productId);
            }
        } else System.out.println(String.format("Produs cu id=%d nu exista", productId));
    }

    public static void printAvailableProducts() {
        Collections.sort(inventory);
        System.out.println("Produse disponibile cu stock > 0 (sortate în ordine alfabetică):");
        for (Product product : inventory) {
            if (product.getStock() > 0) {
                System.out.println(product.toString());
            }
        }
    }
    public static Product searchProductByName(String name) {
        for (Product product : inventoryMap.values()) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.println("Produsul găsit: " + product.toString());
                return product;
            }
        }
        System.out.println("Produsul nu a fost găsit.");
        return null;
    }
}
