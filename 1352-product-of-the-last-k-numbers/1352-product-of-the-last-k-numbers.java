class ProductOfNumbers {
    private java.util.ArrayList<Integer> prefix;

    public ProductOfNumbers() {
        prefix = new java.util.ArrayList<>();
        prefix.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            // zero ke pehle ka sab irrelevant, reset
            prefix.clear();
            prefix.add(1);
        } else {
            prefix.add(prefix.get(prefix.size() - 1) * num);
        }
    }

    public int getProduct(int k) {
        int size = prefix.size();
        // k numbers window mein zero aaya tha
        if (k >= size) return 0;
        return prefix.get(size - 1) / prefix.get(size - 1 - k);
    }
}