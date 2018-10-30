import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class SecondFrame extends JFrame {
    public SecondFrame(List<PCB> pcbs,String available){
        this.setLayout(new BorderLayout(10,5)); //默认为0，0；水平间距10，垂直间距5
        Judge judge=new Judge(pcbs,available);
        this.add(judge,BorderLayout.CENTER);
        this.setTitle("银行家算法");
        this.setResizable(true);
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
