package cn.com.HandleTableData.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.com.HandleTableData.dao.IHandleTableDao;
import cn.com.domain.DtuData;

public class CHandleTableDaoImpl implements IHandleTableDao {

	@Override
	public void clear(JTable jTable) {
		// TODO Auto-generated method stub
		DefaultTableModel tableModel=(DefaultTableModel)jTable.getModel();
		int rows=tableModel.getRowCount();
		if(rows!=0)
		{
			for(int i=0;i<rows;i++)
			{
				tableModel.removeRow(0);
			}
		}
	}

	@Override
	public void show(JTable jTable, List<DtuData> list){
		// TODO Auto-generated method stub
		clear(jTable);
		DefaultTableModel tableModel=(DefaultTableModel)jTable.getModel();
		DtuData dtudata=null;
		for(int i=0;i<list.size();i++)
		{
			dtudata=list.get(i);
			String id=String.valueOf(dtudata.getId());
			Timestamp tm=dtudata.getTime();
			//tm.getda
			DateFormat  df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr=df.format(tm);
			String showContent=dealWithIfXml(dtudata.getContent());
			String []data={id,dateStr,showContent};
			tableModel.addRow(data);
		}
	}

	@SuppressWarnings("finally")
	private String dealWithIfXml(String content) {
		// TODO Auto-generated method stub
		String showdata="";
	//	Map map = new LinkedHashMap();
		Document doc = null;
		boolean IsXmlFile=false;
		try {
			doc = DocumentHelper.parseText(content);
			 // 获取根节点
			Element rootElt = doc.getRootElement(); 
	        Iterator ite=rootElt.elementIterator();
	        while(ite.hasNext())
	        {
	       	 Element data=(Element)ite.next();
	       	 String key=data.getName().toString();
	       	 String value=data.getData().toString();
//		      System.out.println(key);
//		      System.out.println(value);
	       	// map.put(key, value);
	       	 showdata+=key+" = "+value+"  ";   
	       	 IsXmlFile=true;
	        } 
		}catch (DocumentException e) {
			IsXmlFile=false;
			//JOptionPane.showMessageDialog(null,"这个不是XML文件,显示所有数据");
			e.printStackTrace();
			
		} 
		finally
		{
			if(true==IsXmlFile)
				return showdata;
			else
				return content;
		}

	}

}
