public class Scratch {
    public static void main(String[] args) {
        DNADB it = new DNADB();
        it.insert("AAA");
        it.insert("AAC");
        it.insert("CA");
        it.remove("CA");
        String result = it.print();
        System.out.println("---- RESULT BEG ----");
        System.out.print(result);
        System.out.println("---- RESULT END ----");
        System.out.println("startsWith? " + result.startsWith("tree dump:\r\nI"));
    }
}
