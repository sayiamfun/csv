package com.sayiamfun.common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kudu.client.*;
import org.apache.kudu.client.SessionConfiguration.FlushMode;

import java.lang.reflect.Field;
import java.util.*;

//import org.apache.kudu.ColumnSchema;

public class KuduOperUtil {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KuduOperUtil.class);

	private static KuduClient client = null;
	static String masterList ;

	public void setMasterList(String masterList) {
		KuduOperUtil.masterList = "10.11.3.18:7051,10.11.3.19:7051";
	}

	{
		List<String> masters = new LinkedList<String>();
//		this.masterList = ConfigLoader.syscfg.getProperty("apache.kudu.master").trim();
		if (null != masterList) {

			this.client = getNewClient();
		}
	}

	static KuduClient getClient(){

		if (null != client ) {
			return client;
		}
		KuduClient client = getNewClient();
		return client;
	}

	static KuduClient getNewClient(){

		if (null != masterList) {
			String [] masterArr = masterList.split(",");
			int len = masterArr.length;
			if (len > 0) {
				List<String> masters = new LinkedList<String>();
				for (int i = 0; i < len; i++) {
					masters.add(masterArr[i].trim());
				}
				KuduClient client = new KuduClient.KuduClientBuilder(masters).build();
				KuduOperUtil.client = client;
				return client;
			}
		}
//		List<String> masters = new LinkedList<String>();
//		masters.add("10.10.11.188:17051");
//		masters.add("10.10.11.189:17051");
//		masters.add("10.10.11.190:17051");
//		KuduClient client = new KuduClient.KuduClientBuilder(masters).build();
//		new KuduClient.KuduClientBuilder("").build();
//		this.client = client;
		return null;
	}

	void createTab(){
//		ColumnSchema schema = null;
	}

	public static void insert(String name,List<Object[]> params,String [] columnNames) {
		KuduClient client = null;
		KuduSession session = null;

		try {
			if (!StringUtils.isEmpty(name)
					&& null != params
					&& params.size() > 0
					&& null != columnNames
					&& columnNames.length > 0) {

				//name = "impala::default."+name;
				KuduTable table = null;
				try {
					client = getClient();
					if (null != client) {
						table = client.openTable(name);
					}
				} catch (Exception e) {
					client = getNewClient();
					if (null != client) {
						table = client.openTable(name);
					}
					logger.error(e.getMessage(), e);
				}
                System.out.println("table--"+table);
				if (null == client) {
					return ;
				}

				session = client.newSession();
				session.setFlushMode(FlushMode.MANUAL_FLUSH);
				int fieldLen = columnNames.length;
				int count = 0;

				for (Object[] objects : params) {

					if (null != objects ) {

						int len = objects.length;

						if (len >= fieldLen) {

							count++;
							Insert insert = table.newInsert();
							PartialRow row = insert.getRow();

							for (int i = 0; i < fieldLen; i++) {

								Object value = objects[i];
								if (value instanceof Integer) {
									row.addInt(columnNames[i], (int)value);
								} else if (value instanceof Long) {
									row.addLong(columnNames[i], (long)value);
								} else if (value instanceof Double) {
									row.addDouble(columnNames[i], (double)value);
								} else if (value instanceof Float) {
									row.addFloat(columnNames[i], (float)value);
								} else {
									row.addString(columnNames[i], ""+value);
								}
							}
                            System.out.println("insert--"+insert.toString());
							session.apply(insert);

							if (0 == count%200) {
								session.flush();
							}
						}

					}
				}

				session.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (null != session) {
				try {
					session.close();
				} catch (KuduException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}

//	public static void insert(String tabname,BlockingQueue<Object[]> params,String [] columnNames) {
//
//		KuduClient client = null;
//		KuduSession session = null;
//
//		try {
//			if (! StringUtils.isEmpty(tabname)
//					&& null != params
//					&& params.size() > 0
//					&& null != columnNames
//					&& columnNames.length > 0) {
//				//tabname = "impala::default."+tabname;
//				KuduTable table = null;
//				try {
//					client = getClient();
//					if (null != client) {
//						table = client.openTable(tabname);
//					}
//				} catch (Exception e) {
//					client = getNewClient();
//					if (null != client) {
//						table = client.openTable(tabname);
//					}
//					e.printStackTrace();
//				}
//				if (null == client) {
//					return ;
//				}
//				session = client.newSession();
//				session.setFlushMode(FlushMode.MANUAL_FLUSH);
//				int fieldLen = columnNames.length;
//				int count = 0;
//
//				Object[] objects = params.poll();
//				while(null != objects){
//
//					int len = objects.length;
//
//					if (len >= fieldLen) {
//
//						count++;
//						Insert insert = table.newInsert();
//						PartialRow row = insert.getRow();
//
//						for (int i = 0; i < fieldLen; i++) {
//
//							Object value = objects[i];
//							if (value instanceof Integer) {
//								row.addInt(columnNames[i], (int)value);
//							} else if (value instanceof Long) {
//								row.addLong(columnNames[i], (long)value);
//							} else if (value instanceof Double) {
//								row.addDouble(columnNames[i], (double)value);
//							} else if (value instanceof Float) {
//								row.addFloat(columnNames[i], (float)value);
//							} else {
//								row.addString(columnNames[i], ""+value);
//							}
//						}
//						session.apply(insert);
//
//						if (0 == count%200) {
//							session.flush();
//						}
//					}
//
//					objects = params.poll();
//				}
//
//				session.flush();
//			}
//		} catch (KuduException e) {
//			e.printStackTrace();
//		} finally {
//			if (null != session) {
//				try {
//					session.close();
//				} catch (KuduException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}

	public static void update(String name,List<Object[]> params,String [] columnNames) {
		KuduClient client = null;
		KuduSession session = null;

		try {
			if (! StringUtils.isEmpty(name)
					&& null != params
					&& params.size() > 0
					&& null != columnNames
					&& columnNames.length > 0) {
				//name = "impala::default."+name;
				KuduTable table = null;
				try {
					client = getClient();
					if (null != client) {
						table = client.openTable(name);
					}
				} catch (Exception e) {
					client = getNewClient();
					if (null != client) {
						table = client.openTable(name);
					}
					logger.error(e.getMessage(), e);
				}
//                System.out.println("table--"+table);
				if (null == client) {
					return ;
				}

				session = client.newSession();
				session.setFlushMode(FlushMode.MANUAL_FLUSH);
				int fieldLen = columnNames.length;
				int count = 0;

				for (Object[] objects : params) {

					if (null != objects ) {

						int len = objects.length;

						if (len >= fieldLen) {

							count++;
							Update update = table.newUpdate();
							PartialRow row = update.getRow();

							for (int i = 0; i < fieldLen; i++) {

								Object value = objects[i];
								if (value instanceof Integer) {
									row.addInt(columnNames[i], (int)value);
								} else if (value instanceof Long) {
									row.addLong(columnNames[i], (long)value);
								} else if (value instanceof Double) {
									row.addDouble(columnNames[i], (double)value);
								} else if (value instanceof Float) {
									row.addFloat(columnNames[i], (float)value);
								} else {
									row.addString(columnNames[i], ""+value);
								}
							}
//                            System.out.println("update--"+update.toString());
							session.apply(update);

							if (0 == count%100) {
								session.flush();
							}
						}

					}
				}

				session.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (null != session) {
				try {
					session.close();
				} catch (KuduException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}

//	public static void main(String[] args) {
//		insert
//	}

	public static  void update(String tabname, java.util.concurrent.BlockingQueue<Object[]> params, String [] columnNames) {

		KuduClient client = null;
		KuduSession session = null;

		try {
			if (! StringUtils.isEmpty(tabname)
					&& null != params
					&& params.size() > 0
					&& null != columnNames
					&& columnNames.length > 0) {
				//tabname = "impala::default."+tabname;
				KuduTable table = null;
				try {
					client = getClient();
					if (null != client) {
						table = client.openTable(tabname);
					}
				} catch (Exception e) {
					client = getNewClient();
					if (null != client) {
						table = client.openTable(tabname);
					}
					logger.error(e.getMessage(), e);
				}
				if (null == client) {
					return ;
				}
				session = client.newSession();
				session.setFlushMode(FlushMode.MANUAL_FLUSH);
				int fieldLen = columnNames.length;
				int count = 0;

				Object[] objects = params.poll();
				while(null != objects){

					int len = objects.length;

					if (len >= fieldLen) {

						count++;
						Update update = table.newUpdate();
						PartialRow row = update.getRow();

						for (int i = 0; i < fieldLen; i++) {

							Object value = objects[i];
							if (value instanceof Integer) {
								row.addInt(columnNames[i], (int)value);
							} else if (value instanceof Long) {
								row.addLong(columnNames[i], (long)value);
							} else if (value instanceof Double) {
								row.addDouble(columnNames[i], (double)value);
							} else if (value instanceof Float) {
								row.addFloat(columnNames[i], (float)value);
							} else {
								row.addString(columnNames[i], ""+value);
							}
						}
						session.apply(update);

						if (0 == count%100) {
							session.flush();
						}
					}

					objects = params.poll();
				}

				session.flush();
			}
		} catch (KuduException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (null != session) {
				try {
					session.close();
				} catch (KuduException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}


	public static Map<String,List<Object[]>> Object2TwoMapArray(Object obj){
		Class cls = obj.getClass();
		//得到所有属性
		Field[] fields = cls.getDeclaredFields();
		Object[] params = new Object[20];
		String[] colums = new String[20];
		for(int i=0;i<fields.length; i++){
			Field field = fields[i];
			field.setAccessible(true);
			String name = field.getName();
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}
			if(value!=null){
				params[i] = value ;
				colums[i] = ImpalaUtil.humpToLine(name) ;
			}
		}
		Map map = new HashMap();
		List<Object[]> param = new ArrayList();
		List<Object[]> colum = new ArrayList();
		param.add(params);
		colum.add(colums);
		map.put("param",param);
		map.put("colums",colum);
		return map;
	}

	public static Map<String,List<Object[]>> Object2TwoMapArrayL(Object obj){
		Class cls = obj.getClass();
		//得到所有属性
		Field[] fields = cls.getDeclaredFields();
        Object[] params = new Object[30];
        String[] colums = new String[30];
        for(int i=0;i<fields.length; i++){
            Field field = fields[i];
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
            if(value!=null && !"null".equals(value) && !"".equals(value)){
                params[i] = value ;
                colums[i] = ImpalaUtil.humpToLine(name) ;
            }
        }
        Object[] params1  = replaceNull(params);
        String[] colums1  = replaceNull(colums);
		Map map = new HashMap();
		List<Object[]> param = new ArrayList();
		List<Object[]> colum = new ArrayList();

		param.add(params1);
		colum.add(colums1);
		map.put("param",param);
		map.put("colums",colum);
//		System.out.println("param--"+GsonUtil.beanToJson(param));
//		System.out.println("colum--"+GsonUtil.beanToJson(colum));
		return map;
	}

    private static String[] replaceNull(String[] str){
		//用StringBuffer来存放数组中的非空元素，用“;”分隔
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<str.length; i++) {
			if("".equals(str[i]) || str[i] == null || "null".equals(str[i])) {
				continue;
			}
			sb.append(str[i]);
			if(i != str.length - 1) {
				sb.append(";");
			}
		}
		//用String的split方法分割，得到数组
		str = sb.toString().split(";");
		return str;
    }

    private static Object[] replaceNull(Object[] str) {
//		int count = str.length;
//		Object[] ss = str;
	    for (int i = 0;i<str.length;i++) {
            if ("".equals(str[i]) || str[i] == null || "null".equals(str[i])) {
				str = ArrayUtils.remove(str,i);
//				除去一个值坐标前移一位
				if(i != 0){
					i--;
				}
            }
        }
        return str;
    }

	public static void main(String[] args) {
		Object[] ss = new Object[]{"a88507ba9258470295c8cea6132fdb2b","LJPBFD2X6HB000207","2019-08-14 17:45:48",null,"8a8a868c6ac4df12016ad99d0729010e","绝缘故障报警",null,null,null,null,null};
		replaceNull(ss);
	}

}
