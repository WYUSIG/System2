import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MainFrame extends JFrame  {

    //构造函数
    public MainFrame(){
        this.setLayout(new BorderLayout(10,5)); //默认为0，0；水平间距10，垂直间距5
        ShowResources showResources=new ShowResources();
        this.add(showResources,BorderLayout.CENTER);
        this.setTitle("银行家算法");
        this.setResizable(true);
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
