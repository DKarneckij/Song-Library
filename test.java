public class test {
    public class Y {
        public int y1;
    }
    public class Z extends Y {
        public int z1;
    }
    Z z = new Y();
    System.out.println(z.y1);
}
