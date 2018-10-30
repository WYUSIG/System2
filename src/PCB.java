import java.util.Vector;

public class PCB{
    public String name;
    public String need;
    public String allocation;
    public boolean finish;
    public PCB(String name,String need,String allocation,boolean finish){
        this.name=name;
        this.need=need;
        this.allocation=allocation;
        this.finish=finish;
    }
}
