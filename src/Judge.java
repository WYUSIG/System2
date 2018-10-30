import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Judge extends JPanel implements ActionListener {
    private static Font defaultFont = new Font("SimSun", Font.PLAIN, 12); //默认字体
    private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    private JTable table;  //表格
    private Judge.MyTableModel model=new Judge.MyTableModel();  //声明模型对象
    private JButton safeButton,exitButton;
//    private Vector<Vector<Object>> formerData;
    private List<PCB> process=new ArrayList<>();
    private String available;
    public Judge(List<PCB> pcbs,String available){
//        this.formerData=formerData;
        this.process=pcbs;
        this.available=available;
        Calculation(pcbs,available);
        initView();
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
        JLabel bannerLabel = new JLabel("系统在T0时刻的安全序列:");
        bannerLabel.setFont(defaultFont);
        htemp1.add(bannerLabel);
        //表格
        table = new JTable(model);                     //把模型对象作为参数构造表格对象，这样就可以用表格显示出数据
        DefaultTableCellRenderer r   =   new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,   r);
        JScrollPane scroll = new JScrollPane(table);   //把表格控件放到滚动容器里，页面不够长显示可以滚动
        scroll.setPreferredSize(new Dimension(600, 300));
        htemp2.add(scroll);
        //底部按钮
        JLabel jLabel = new JLabel("P2发出请求向量Request(1,2,2,2)系统按照银行家算法进行检查:");
        jLabel.setFont(defaultFont);
        safeButton = new JButton("执行算法");
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

    private void Calculation(List<PCB> process,String available){
        for(int i=0;i<process.size();i++){
            if (process.get(i).finish==false){
                if(compare(available,process.get(i).need)){
                    Vector<Object> v = new Vector<Object>();
                    v.add(process.get(i).name);
                    v.add(available);
                    v.add(process.get(i).need);
                    v.add(process.get(i).allocation);
                    available=add(available,process.get(i).allocation);
                    process.get(i).finish=true;
                    v.add(available);
                    v.add(process.get(i).finish);
                    data.add(v);
                    process.remove(i);
                    Calculation(process,available);
                }
            }
        }

    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==safeButton){
//            for(process.get(i).finish)
            String s=new String("{");
            for(int i=0;i<data.size();i++){
                if((Boolean) data.get(i).get(5)==false){
                    return;
                }
                s+=data.get(i).get(0).toString();
                if(i<4){
                    s+=",";
                }
            }
            s+="}";
            JOptionPane.showMessageDialog(null, "系统在T0时刻存在一个安全序列"+s+",\n所以系统是安全的！", "执行结果", JOptionPane.PLAIN_MESSAGE);
        }else if(e.getSource()==exitButton){
            System.exit(0);
        }
    }
    private boolean compare(String a,String b){
        String a_temp[]=a.split(" ");
        String b_temp[]=b.split(" ");
        for(int i=0;i<4;i++){
            if(Integer.parseInt(a_temp[i])<Integer.parseInt(b_temp[i])){
                return false;
            }
        }
        return true;
    }
    private String add(String a,String b){
        String a_temp[]=a.split(" ");
        String b_temp[]=b.split(" ");
        String c=new String();
        for(int i=0;i<4;i++){
            int temp=Integer.parseInt(a_temp[i])+Integer.parseInt(b_temp[i]);
            c+=Integer.toString(temp);
            if(i<3){
                c+=" ";
            }
        }
        return c;
    }
    private String decrease(String a,String b){
        String a_temp[]=a.split(" ");
        String b_temp[]=b.split(" ");
        String c=new String();
        for(int i=0;i<4;i++){
            int temp=Integer.parseInt(a_temp[i])-Integer.parseInt(b_temp[i]);
            c+=Integer.toString(temp);
            if(i<3){
                c+=" ";
            }
        }
        return c;
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

            title.add("Work");

            title.add("Need");

            title.add("Allocation");

            title.add("Work+Allocation");

            title.add("Finish");



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

