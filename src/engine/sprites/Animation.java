package engine.sprites;

public class Animation {
    public Animation(String n, String s, boolean r) {
        this.name = n;
        this.repeat = r;
        
        String[] frames = s.split(",");
        this.sequence = new int[frames.length];
        for(int i=0; i<frames.length; i++) {
            try {
                int f = Integer.parseInt(frames[i]);
                this.sequence[i] = f;
            } catch (NumberFormatException e) {
                System.err.println("Unable to parse frame id");
            }
        }
    }
    
    public final String name;
    private int[] sequence;
    private boolean repeat;
}