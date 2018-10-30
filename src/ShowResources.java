

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ShowResources extends JPanel implements ActionListener {
    private static Font defaultFont = new Font("SimSun", Font.PLAIN, 12); //默认字体
    private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    private JTable table;  //表格
    private MyTableModel model=new MyTableModel();  //声明模型对象
    private JButton safeButton,exitButton;
    private List<PCB> pcbs=new ArrayList<>();
    private String available;
    public ShowResources(){
        initData();
        initView();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==safeButton){
            SecondFrame secondFrame=new SecondFrame(pcbs,available);
        }else if(e.getSource()==exitButton){
            System.exit(0);
        }
    }
    //初始化窗口
    private void initView(){
        Box baseBox = Box.createVerticalBox();     //根盒子
        baseBox.setSize(5000, 200);

//        baseBox.add(bannerLabel);
        //-------------------容器内容------------------------------
        JPanel showPanel = new JPanel();
        Box vtemp = Box.createVerticalBox();
        Box htemp1 = Box.createHorizontalBox();
        Box htemp2 = Box.createHorizontalBox();
        Box htemp3 = Box.createHorizontalBox();
        //标题
        JLabel bannerLabel = new JLabel("系统在T0时刻的资源分配表:");
        bannerLabel.setFont(defaultFont);
        htemp1.add(bannerLabel);
        //表格
        table = new JTable(model);                     //把模型对象作为参数构造表格对象，这样就可以用表格显示出数据
        DefaultTableCellRenderer   r   =   new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,   r);
        JScrollPane scroll = new JScrollPane(table);   //把表格控件放到滚动容器里，页面不够长显示可以滚动
        scroll.setPreferredSize(new Dimension(600, 300));
        htemp2.add(scroll);
        //底部按钮
        JLabel jLabel = new JLabel("检查T0时刻系统的安全性:");
        jLabel.setFont(defaultFont);
        safeButton = new JButton("安全检查");
        safeButton.addActionListener(this);
        exitButton=new JButton("退出程序");
        exitButton.addActionListener(this);
        htemp3.add(jLabel);
        htemp3.add(Box.createHorizontalStrut(30));//创建间隔
        htemp3.add(safeButton);
        htemp3.add(Box.createHorizontalStrut(10));//创建间隔
        htemp3.add(exitButton);
        //-------------------容器内容------------------------------
        vtemp.add(htemp1);
        vtemp.add(Box.createVerticalStrut(15));                  //创建上下空间距离
        vtemp.add(htemp2);
        vtemp.add(Box.createVerticalStrut(15));                  //创建上下空间距离
        vtemp.add(htemp3);
        showPanel.add(vtemp);
        baseBox.add(showPanel);


        this.add(baseBox, BorderLayout.NORTH);
    }
    private void initData(){
        Vector<Object> v0 = new Vector<Object>();
        v0.add(new String("P0"));
        v0.add(new String("0 0 3 2"));
        v0.add(new String("0 0 1 2"));
        v0.add(new String("1 6 2 2"));
//        pcbs.add(new PCB(v0.get(0))
        Vector<Object> v1 = new Vector<Object>();
        v1.add(new String("P1"));
        v1.add(new String("1 0 0 0"));
        v1.add(new String("1 7 5 0"));
        v1.add(new String(""));
        Vector<Object> v2 = new Vector<Object>();
        v2.add(new String("P2"));
        v2.add(new String("1 3 5 4"));
        v2.add(new String("2 3 5 6"));
        v2.add(new String(""));
        Vector<Object> v3 = new Vector<Object>();
        v3.add(new String("P3"));
        v3.add(new String("0 3 3 2"));
        v3.add(new String("0 6 5 2"));
        v3.add(new String(""));
        Vector<Object> v4 = new Vector<Object>();
        v4.add(new String("P4"));
        v4.add(new String("0 0 1 4"));
        v4.add(new String("0 6 5 6"));
        v4.add(new String(""));
        data.add(v0);
        data.add(v1);
        data.add(v2);
        data.add(v3);
        data.add(v4);
        for(int i=0;i<data.size();i++){
            pcbs.add(new PCB(data.get(i).get(0).toString(),data.get(i).get(2).toString(),data.get(i).get(1).toString(),false));
        }
        available=new String("1 6 2 2");
    }
    class MyTableModel extends AbstractTableModel  //模型类
    {

        Vector<String> title = new Vector<String>();// 列名


        /**
         * 构造方法，初始化二维动态数组data对应的数据
         */
        public MyTableModel()                       //构造方法
        {
            title.add("Process");

            title.add("Allocation");

            title.add("Need");

            title.add("Available");



        }

        // 以下为继承自AbstractTableModle的方法，可以自定义
        /**
         * 得到列名
         */
        @Override
        public String getColumnName(int column)
        {
            return title.elementAt(column);
        }

        /**
         * 重写方法，得到表格列数
         */
        @Override
        public int getColumnCount()
        {
            return title.size();
        }

        /**
         * 得到表格行数
         */
        @Override
        public int getRowCount()
        {
            return data.size();
        }

        /**
         * 得到数据所对应对象
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            //return data[rowIndex][columnIndex];
            return data.elementAt(rowIndex).elementAt(columnIndex);
        }

        /**
         * 得到指定列的数据类型
         */
        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            return data.elementAt(0).elementAt(columnIndex).getClass();
        }

        /**
         * 指定设置数据单元是否可编辑.
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }

        /*
         * 适配数据行数
         */
        public void addTableItem() {
            fireTableDataChanged();
        }

    }
}
