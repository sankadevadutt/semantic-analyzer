package semantic;

import java.util.*;
import java.lang.*;
import java.util.regex.*;

public class main {
	//validation of variable name
	static boolean validate_var(String s)
	{
		Pattern p = Pattern.compile("^[_a-zA-Z][0-9a-zA-Z_]*");
		Matcher m = p.matcher(s);
		return m.find();
	}
	
	
	//validating data type of the value
	static String retdata(String s)
	{
		Pattern p1 = Pattern.compile("^[0-9]+$");
		Matcher m1 = p1.matcher(s);
		
		Pattern p2 = Pattern.compile("\\d+\\.\\d+$");
		Matcher m2 = p2.matcher(s);
		
		Pattern p3 = Pattern.compile("'[\\w\\d]?'$");
		Matcher m3 = p3.matcher(s);
		

		Pattern p4 = Pattern.compile("\"[\\w\\d]*\"$");
		Matcher m4 = p4.matcher(s);
		
		if(m2.find())
		{
			return "double";
		}else if(m1.find())
		{
			return "int";
		}else if(m3.find())
		{
			return "char";
		}else if(m4.find())
		{
			return "String";
		}else
		{
			return "invalid";
		}
	}
	
	
	
	
	public static void main(String[] args) {
		ArrayList<String> Keywords = new ArrayList<String>(Arrays.asList(
				"abstract","continue","for","new","switch","assert","default","goto","package","synchronized","true","false","null","boolean",
				"do","if","private","this","break","double","implements","protected","throw","byte","else","import","public","throws","case",
				"enum","instanceof","return","transient","catch","extends","int","short","try","char","final","interface","static","void",
				"class","finally","long","strictfp","volatile","const","float","native","super","while","String","float","long"));
		
		ArrayList<String> Datatypes = new ArrayList<String>(Arrays.asList("int","double","char","String"));
		Scanner scan = new Scanner(System.in);
		HashMap<String,String> var_val= new HashMap<String,String>(); 
		HashMap<String,String> var_data = new HashMap<String,String>();
		String str,var,exprsn,datatype,returneddata;
		int val;
		str = scan.nextLine();
		if(str.equals("start"))
		{ 
			while(true)
			{
				str = scan.nextLine();
				String[] splitarray = str.split(" ");
				if(Datatypes.contains(splitarray[0]))
				{
					datatype = splitarray[0];
					var = splitarray[1];
					//identifier can't be a keyword
					if(Keywords.contains(var))
					{
						System.err.println("Invalid!! variable name should not match with reserved keywords ");
					}else if(!validate_var(var))  // valid identifier name 
					{
						System.err.println("Invalid variable name");
					}else if(var_data.containsKey(var)) //validation of multiple identifier declaration 
					{
						System.err.println("Invalid!! Multiple var_dataaration of variable : "+var);
					}else if(splitarray[2].equals("=")){ //initialization during declaration
						if(Arrays.toString(splitarray).contains("+"))  // expression
						{	
							exprsn = splitarray[3];
							String[] operat = exprsn.split("\\+");
							String op1="",op2="";
							char charval=' ',charval1=' ' ;
							String strval=" ",strval1 = " ";
							boolean isvar1=false,isvar2=false;
							if(Keywords.contains(operat[0]) || Keywords.contains(operat[1])  )
							{
								System.err.println("Invalid expression!! operands should not match with reserved keywords ");
							}else if(var_data.containsKey(operat[0]) && var_data.containsKey(operat[1]))
							{
								if(var_val.get(operat[0]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[0]+ " is not initialized");
								}else if(var_val.get(operat[1]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[1]+ " is not initialized");
								}else {
									op1 = var_data.get(operat[0]);
									op2 = var_data.get(operat[1]);
									operat[0]=var_val.get(operat[0]);
									operat[1]=var_val.get(operat[1]);
									isvar1=true;
									isvar2=true;

									}
							}else if(var_data.containsKey(operat[0]))
							{
								if(var_val.get(operat[0]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[0]+ " is not initialized");
								}else {
									op2 = retdata(operat[1]);
									op1 = var_data.get(operat[0]);
									operat[0] = var_val.get(operat[0]);
									isvar1=true;
									isvar2=false;
								}
							}else if(var_data.containsKey(operat[1]))
							{
								if(var_val.get(operat[1]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[1]+ " is not initialized");
								}else {
									op2 = var_data.get(operat[1]);
									operat[1] = var_val.get(operat[1]);
									op1 = retdata(operat[0]);
									isvar2=true;
									isvar1=false;
								}
							}else if(validate_var(operat[0]))
							{
								System.err.println("Variable "+operat[0]+ " is not declared");
							}else if(validate_var(operat[1]))
							{
								System.err.println("Variable "+operat[1]+ " is not declared");
							}else {
								op1 = retdata(operat[0]);
								isvar1=false;
								op2 = retdata(operat[1]);
								isvar2=false;
							}
							
							switch(op1)
							{
							case "char" :
								char[] arr = operat[0].toCharArray();
								if(isvar1)
								{
									charval = arr[0];	
								}else {
									charval = arr[1];
								}
								break;
							case "String" :
								if(isvar1)
								{
									strval = operat[0];
								}else {
									strval = operat[0].substring(1,operat[0].length()-1);
								}
								break;
							}
							
							switch(op2)
							{
							case "char" :
								char[] arr = operat[1].toCharArray();
								if(isvar2)
								{
									charval1 = arr[0];	
								}else {
									charval1 = arr[1];
								}
								break;
							case "String" :
								if(isvar2)
								{
									strval1 = operat[1];
								}else {
									strval1 = operat[1].substring(1,operat[1].length()-1);
								}
								break;
							}
							
							switch(op1)
							{
							case "int":
								switch(op2)
								{
								case "int":
									if(datatype.equals("int"))
									{
										var_val.put(var, String.valueOf(Integer.parseInt(operat[0])+Integer.parseInt(operat[1]))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("char"))
									{
										var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])+Integer.parseInt(operat[1])))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("double"))
									{
										var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])+Integer.parseInt(operat[1])))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("String"))
									{
										System.err.println("Invalid datatype assignment int cannot be converted into String");	
									}
									break;
								case "char":
									if(datatype.equals("int"))
									{
										var_val.put(var, String.valueOf(Integer.parseInt(operat[0])+(int)charval1)); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("char"))
									{
										var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])+(int)charval1))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("double"))
									{
										var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])+(int)charval1))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("String"))
									{
										System.err.println("Invalid datatype assignment int cannot be converted into String");	
									}
									break;
								case "String":
									if(datatype.equals("String"))
									{
										var_val.put(var, operat[0]+strval1); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
									}
									break;
								case "double":
									if(datatype.equals("double"))
									{
										var_val.put(var, String.valueOf(Double.parseDouble(operat[0])+Double.parseDouble(operat[1]))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment double cannot be converted into "+datatype);
									}
									break;
								case "invalid":
									System.err.println("Invalid value for expression : "+operat[1]);
									break;
								}
								break;
							case "char":
								switch(op2)
								{
								case "int":
									if(datatype.equals("int"))
									{
										var_val.put(var, String.valueOf(Integer.parseInt(operat[1])+(int)charval)); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("char"))
									{
										var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[1])+(int)charval))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("double"))
									{
										var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[1])+(int)charval))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("String"))
									{
										System.err.println("Invalid datatype assignment int cannot be converted into String");	
									}
									break;
								case "char":
									if(datatype.equals("int"))
									{
										var_val.put(var, String.valueOf((int)charval+(int)charval1)); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("char"))
									{
										var_val.put(var, String.valueOf((char)((int)charval+(int)charval1))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("double"))
									{
										var_val.put(var, String.valueOf((float)((int)charval+(int)charval1))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else if(datatype.equals("String"))
									{
										System.err.println("Invalid datatype assignment int cannot be converted into String");	
									}
									break;
								case "String":
									if(datatype.equals("String"))
									{
										var_val.put(var, strval+strval1); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
									}
									break;
								case "double":
									if(datatype.equals("double"))
									{
										var_val.put(var, String.valueOf((float)((float)charval+Double.parseDouble(operat[1])))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment double cannot be converted into "+datatype);
									}
									break;
								case "invalid":
									System.err.println("Invalid value for expression : "+operat[1]);
									break;
								}
								break;
							case "String":
								switch(op2)
								{
								case "int" :
									if(datatype.equals("String"))
									{
										var_val.put(var, strval+operat[1]); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
									}
									break;
								case "char" :
									if(datatype.equals("String"))
									{
										var_val.put(var, strval+strval1); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
									}
									break;
								case "String" :
									if(datatype.equals("String"))
									{
										var_val.put(var, strval+strval1); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
									}
									break;
								case "double" :
									if(datatype.equals("String"))
									{
										var_val.put(var, strval+operat[1]);
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
									}
									break;
								case "invalid":
									System.err.println("Invalid value for expression : "+operat[1]);
									break;
								}
								break;
							case "double" :
								switch(op2)
								{
								case "int" :
								case "double" :
									if(datatype.equals("double"))
									{
										var_val.put(var, String.valueOf(Double.parseDouble(operat[0])+Double.parseDouble(operat[1]))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment double cannot be converted into "+datatype);
									}
									break;
								case "char":
									if(datatype.equals("double"))
									{
										var_val.put(var, String.valueOf((float)((float)charval1+Double.parseDouble(operat[0])))); //assigning value of var2 to var1
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment double cannot be converted into "+datatype);
									}
									break;
								case "String" :
									if(datatype.equals("String"))
									{
										var_val.put(var, operat[0]+strval1);
										var_data.put(var, datatype);
									}else {
										System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
									}
									break;
								case "invalid":
									System.err.println("Invalid value for expression : "+operat[1]);
									break;
								}
								break;
							case "invalid" :
								System.err.println("Invalid value for expression : "+operat[0]);
								break;
							}
						}else if(Arrays.toString(splitarray).contains("-") ||
								Arrays.toString(splitarray).contains("/") || Arrays.toString(splitarray).contains("*")
								|| Arrays.toString(splitarray).contains("%")) 
						{
							String operator="";
							exprsn = splitarray[3];
							String[] operat = {"0","0"};
							String op1="",op2="";
							char charval=' ',charval1=' ' ;
							boolean isvar1=false,isvar2=false;
							
							
							if(Arrays.toString(splitarray).contains("-"))
							{
								operator = "-" ;
								operat = exprsn.split("\\-");
							}else if(Arrays.toString(splitarray).contains("/"))
							{
								operator = "/" ;
								operat = exprsn.split("\\/");
							}else if(Arrays.toString(splitarray).contains("*"))
							{
								operator = "*" ;
								operat = exprsn.split("\\*");
							}else if(Arrays.toString(splitarray).contains("%"))
							{
								operator = "%" ;
								operat = exprsn.split("\\%");
							}
							if(Keywords.contains(operat[0]) || Keywords.contains(operat[1])  )
							{
								System.err.println("Invalid expression!! operands should not match with reserved keywords ");
							}else if(var_data.containsKey(operat[0]) && var_data.containsKey(operat[1]))
							{
								if(var_val.get(operat[0]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[0]+ " is not initialized");
								}else if(var_val.get(operat[1]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[1]+ " is not initialized");
								}else {
									op1 = var_data.get(operat[0]);
									op2 = var_data.get(operat[1]);
									operat[0]=var_val.get(operat[0]);
									operat[1]=var_val.get(operat[1]);
									isvar1=true;
									isvar2=true;

									}
							}else if(var_data.containsKey(operat[0]))
							{
								if(var_val.get(operat[0]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[0]+ " is not initialized");
								}else {
									op2 = retdata(operat[1]);
									op1 = var_data.get(operat[0]);
									operat[0] = var_val.get(operat[0]);
									isvar1=true;
									isvar2=false;
								}
							}else if(var_data.containsKey(operat[1]))
							{
								if(var_val.get(operat[1]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[1]+ " is not initialized");
								}else {
									op2 = var_data.get(operat[1]);
									operat[1] = var_val.get(operat[1]);
									op1 = retdata(operat[0]);
									isvar2=true;
									isvar1=false;
								}
							}else if(validate_var(operat[0]))
							{
								System.err.println("Variable "+operat[0]+ " is not declared");
							}else if(validate_var(operat[1]))
							{
								System.err.println("Variable "+operat[1]+ " is not declared");
							}else {
								op1 = retdata(operat[0]);
								isvar1=false;
								op2 = retdata(operat[1]);
								isvar2=false;
							}
							if(op1.equals("char"))
							{
								char[] arr = operat[0].toCharArray();
								if(isvar1)
								{
									charval = arr[0];	
								}else {
									charval = arr[1];
								}
							}
							if(op2.equals("char"))
							{
								char[] arr = operat[1].toCharArray();
								if(isvar2)
								{
									charval1 = arr[0];	
								}else {
									charval1 = arr[1];
								}	
							}
							switch(op1)
							{
							case "int":
								switch(op2)
								{
								case "int" :
									switch(datatype)
									{
									case "int" :
										switch(operator)
										{
										case "-" :
											val = Integer.parseInt(operat[0])-Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf(val));
											var_data.put(var, datatype);
											break;
										case "/" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception division by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])/Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf(val));
												var_data.put(var, datatype);
												break;	
											}
										case "*" :
											val = Integer.parseInt(operat[0])*Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf(val));
											var_data.put(var, datatype);
											break;
										case "%" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception mod by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])%Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf(val));
												var_data.put(var, datatype);
												break;	
											}
										}
										break;
									case "char" :
										switch(operator)
										{
										case "-" :
											val = Integer.parseInt(operat[0])-Integer.parseInt(operat[1]);
											if(val<0)
											{
												System.err.println("int value is invalid to assign for char");
											}else {
												var_val.put(var, String.valueOf((char)val));
												var_data.put(var, datatype);	
											}
											break;
										case "/" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception division by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])/Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf((char)val));
												var_data.put(var, datatype);
												break;	
											}
										case "*" :
											val = Integer.parseInt(operat[0])*Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf((char)val));
											var_data.put(var, datatype);
											break;
										case "%" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception mod by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])%Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf((char)val));
												var_data.put(var, datatype);
												break;	
											}
										}
										break;
									case "double" :
										switch(operator)
										{
										case "-" :
											val = Integer.parseInt(operat[0])-Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf((float)val));
											var_data.put(var, datatype);
											break;
										case "/" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception division by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])/Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf((float)val));
												var_data.put(var, datatype);
												break;	
											}
										case "*" :
											val = Integer.parseInt(operat[0])*Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf((float)val));
											var_data.put(var, datatype);
											break;
										case "%" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception mod by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])%Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf((float)val));
												var_data.put(var, datatype);
												break;	
											}
										}
										break;
									case "String" :
										System.err.println("Invalid initialization int cannot be converted into "+datatype);
										break;
									}
									break;
								case "char" :
									
									switch(datatype)
									{
									case "int" :
										switch(operator)
										{
										case "-" :
											var_val.put(var, String.valueOf(Integer.parseInt(operat[0])-(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/" :
											var_val.put(var, String.valueOf(Integer.parseInt(operat[0])/(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*" :
											var_val.put(var, String.valueOf(Integer.parseInt(operat[0])*(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%" :
											var_val.put(var, String.valueOf(Integer.parseInt(operat[0])%(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
										break;
									case "char" :
										switch(operator)
										{
										case "-" :
											val = Integer.parseInt(operat[0])-(int)charval1;
											if(val<0)
											{
												System.err.println("int value is invalid to assign for char");
												break;
											}else {
												var_val.put(var, String.valueOf((char)val)); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;	
											}
										case "/":
											var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])/(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*":
											var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])*(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])%(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
										break ;
									case "double" :
										switch(operator)
										{
										case "-" :
											var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])-(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])/(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*":
											var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])*(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])%(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
									case "String" :
										System.err.println("Invalid initialization int cannot be converted into "+datatype);
										break;
									}
									break;
								case "double" :
									if(datatype.equals("double"))
									{
										switch(operator)
										{
										case "-" :
											var_val.put(var, String.valueOf(Double.parseDouble(operat[0])-Double.parseDouble(operat[1]))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											if(operat[1].equals("0.0"))
											{
												System.err.println("Arthemetic exception division by 0.0 is invalid");
											}else {
												var_val.put(var, String.valueOf(Double.parseDouble(operat[0])/Double.parseDouble(operat[1]))); //assigning value of var2 to var1
												var_data.put(var, datatype);	
											}
											break;
										case "*":
											var_val.put(var, String.valueOf(Double.parseDouble(operat[0])*Double.parseDouble(operat[1]))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											if(operat[1].equals("0.0"))
											{
												System.err.println("Arthemetic exception mod by 0.0 is invalid");	
											}else {
												var_val.put(var, String.valueOf(Double.parseDouble(operat[0])%Double.parseDouble(operat[1]))); //assigning value of var2 to var1
												var_data.put(var, datatype);
											}
											break;
										}
									}else {
										System.err.println("Invalid initialization double cannot be converted into "+datatype);
										break;
									}
									break ;
								case "String" :
									System.err.println("Operation  "+operator+" is undefined for String");
									break;
								case "invalid" :
									System.err.println("Invalid value "+operat[1]+" in the expression");
									break;
								}
								break ;
							case "char" :
								switch(op2)
								{
								case "int" :
									switch(datatype)
									{
									case "int" :
											switch(operator)
											{
											case "-" :
												var_val.put(var, String.valueOf((int)charval-Integer.parseInt(operat[1]))); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;
											case "/" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception division by 0 is invalid");
													break;
												}else {
													var_val.put(var, String.valueOf((int)charval/Integer.parseInt(operat[1]))); //assigning value of var2 to var1
													var_data.put(var, datatype);
													break;	
												}
											case "*" :
												var_val.put(var, String.valueOf(Integer.parseInt(operat[1])*(int)charval)); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;
											case "%" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception mod by 0 is invalid");
													break;
												}else {
													var_val.put(var, String.valueOf((int)charval%Integer.parseInt(operat[1]))); //assigning value of var2 to var1
													var_data.put(var, datatype);
													break;	
												}
											}
											break;
									case "char" :
											switch(operator)
											{
											case "-" :
												val = (int)charval - Integer.parseInt(operat[1]);
												if(val<0)
												{
													System.err.println("Invalid int value");
												}else {
													var_val.put(var, String.valueOf((char)val));
													var_data.put(var, datatype);
												}
												break;
											case "*" :
												var_val.put(var, String.valueOf((char)((int)charval*Integer.parseInt(operat[1]))));
												var_data.put(var, datatype);
												break;
											case "/" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception division with 0 is invalid");
												}else {
													var_val.put(var, String.valueOf((char)((int)charval/Integer.parseInt(operat[1]))));
													var_data.put(var, datatype);
												}
												break;
											case "%" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception mod with 0 is invalid");
												}else {
													var_val.put(var, String.valueOf((char)((int)charval%Integer.parseInt(operat[1]))));
													var_data.put(var, datatype);
												}
											}
											break;
									case "double" :
											switch(operator)
											{
											case "-" :
												var_val.put(var, String.valueOf((float)((int)charval-Integer.parseInt(operat[1])))); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;
											case "/" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception division by 0 is invalid");
													break;
												}else {
													var_val.put(var, String.valueOf((float)((int)charval/Integer.parseInt(operat[1])))); //assigning value of var2 to var1
													var_data.put(var, datatype);
													break;	
												}
											case "*" :
												var_val.put(var, String.valueOf((float)((int)charval*Integer.parseInt(operat[1])))); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;
											case "%" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception mod by 0 is invalid");
													break;
												}else {
													var_val.put(var, String.valueOf((float)((int)charval%Integer.parseInt(operat[1])))); //assigning value of var2 to var1
													var_data.put(var, datatype);
													break;	
												}
											}
											break;
									case "String" :
											System.err.println("Invalid initialization int cannot be converted into "+datatype);
											break;
									}
										break;
								case "char" :
									switch(datatype)
									{
									case "int" :
										switch(operator)
										{
										case "-":
											var_val.put(var, String.valueOf((int)charval-(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											var_val.put(var, String.valueOf((int)charval/(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*":
											var_val.put(var, String.valueOf((int)charval*(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((int)charval%(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
										break;
									case "char":
										switch(operator)
										{
										case "-" :
											val = (int)charval-(int)charval1;
											if(val<0)
											{
												System.err.println("int value is invalid to assign for char");
											}else {
												var_val.put(var, String.valueOf((char)val)); //assigning value of var2 to var1
												var_data.put(var, datatype);	
											}
											break;
										case "*":
											var_val.put(var, String.valueOf((char)((int)charval*(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											var_val.put(var, String.valueOf((char)((int)charval/(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((char)((int)charval%(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
									case "double":
										switch(operator)
										{
										case "-":
											var_val.put(var, String.valueOf((float)((int)charval-(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*":
											var_val.put(var, String.valueOf((float)((int)charval*(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											var_val.put(var, String.valueOf((float)((int)charval/(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((float)((int)charval%(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
										break;
									case "String" :
										System.err.println("Invalid initialization int cannot be converted into "+datatype);
										break;
									}
									break;
							case "double" :
								switch(datatype)
								{
								case "double" :
									switch(operator)
									{
									case "-":
										var_val.put(var,String.valueOf((float)charval-Double.parseDouble(operat[1])));
										var_data.put(var,datatype);
										break;
									case "/":
										if(operat[1].equals("0.0"))
										{
											System.err.println("Arthemetic exception division by 0.0 is invalid");
										}else {
											var_val.put(var,String.valueOf((float)charval/Double.parseDouble(operat[1])));
											var_data.put(var,datatype);
											break;	
										}
										break;
									case "%" :
										if(operat[1].equals("0.0"))
										{
											System.err.println("Arthemetic exception mod by 0.0 is invalid");
										}else {
											var_val.put(var,String.valueOf((float)charval%Double.parseDouble(operat[1])));
											var_data.put(var,datatype);
											break;	
										}
										break;
									case "*":
										var_val.put(var,String.valueOf((float)charval*Double.parseDouble(operat[1])));
										var_data.put(var,datatype);
										break;
									}
									break;//double
								default :
									System.err.println("Invalid initialization double cannot be converted into "+datatype);
									break;
							}
								break;
							case "String" :
								System.err.println("Operation "+operator+" is undefined for String");
								break;
							case "invalid" :
								System.err.println("Invalid value "+operat[1]+" in the expression");
								break;
								}
								break;
					case "double" :
						switch(op2)
						{
						case "int":
							switch(datatype)
							{
							case "double" :
								switch(operator)
								{
								case "-" :
									var_val.put(var,String.valueOf((float)(Double.parseDouble(operat[0])-Double.parseDouble(operat[1]))));
									var_data.put(var, datatype);
									break;
								case "/":
									if(operat[1].equals("0"))
									{
										System.err.println("Arthemetic exception division by 0 is invalid");
									}else {
										var_val.put(var,String.valueOf((float)(Double.parseDouble(operat[0])/Double.parseDouble(operat[1]))));
										var_data.put(var, datatype);
									}
									break;
								case "%" :
									if(operat[1].equals("0"))
									{
										System.err.println("Arthemetic exception mod by 0 is invalid");
									}else {
										var_val.put(var,String.valueOf((float)(Double.parseDouble(operat[0])%Double.parseDouble(operat[1]))));
										var_data.put(var, datatype);
									}
									break;
								case "*" :
									var_val.put(var,String.valueOf((float)(Double.parseDouble(operat[0])*Double.parseDouble(operat[1]))));
									var_data.put(var, datatype);
									break;
								}
								break;
							default :
								System.err.println("Invalid initialization double cannot be converted into "+datatype);
								break;
							}
							break;
						case "char" :
							switch(datatype)
							{
							case "double":
								switch(operator)
								{
								case "-" :
									double val2 = Double.parseDouble(operat[0])-(float)charval1;
									if(val2<0.0)
									{
										System.err.println("double value is invalid to assign for char");	
									}else {
										var_val.put(var, String.valueOf((float)(val2)));
										var_data.put(var, datatype);	
									}
									break;
								case "/" :
									var_val.put(var, String.valueOf((float)(Double.parseDouble(operat[0])/(float)charval1)));
									var_data.put(var, datatype);
									break;
								case "%" :
									var_val.put(var, String.valueOf((float)(Double.parseDouble(operat[0])%(float)charval1)));
									var_data.put(var, datatype);
									break;
								case "*" :
									var_val.put(var, String.valueOf((float)(Double.parseDouble(operat[0])*(float)charval1)));
									var_data.put(var, datatype);
									break;
								}
								break;
							default :
								System.err.println("Invalid initialization double cannot be converted into "+datatype);
								break;	
							}
							break ;
						case "double" :
							switch(datatype)
							{
							case "double":
								switch(operator)
								{
								case "-" :
									var_val.put(var, String.valueOf(Double.parseDouble(operat[0])-Double.parseDouble(operat[1])));
									var_data.put(var, datatype);
									break;
								case "/" :
									if(operat[1].equals("0.0"))
									{
										System.err.println("Arthemetic exception division by 0.0 is invalid");
									}else {
										var_val.put(var, String.valueOf(Double.parseDouble(operat[0])/Double.parseDouble(operat[1])));
										var_data.put(var, datatype);	
									}
									break;
								case "%" :
									if(operat[1].equals("0.0"))
									{
										System.err.println("Arthemetic exception mod by 0.0 is invalid");
									}else {
										var_val.put(var, String.valueOf(Double.parseDouble(operat[0])%Double.parseDouble(operat[1])));
										var_data.put(var, datatype);	
									}
									break;
								case "*" :
									var_val.put(var, String.valueOf(Double.parseDouble(operat[0])*Double.parseDouble(operat[1])));
									var_data.put(var, datatype);
									break;
								}
								break;
							default :
								System.err.println("Invalid initialization double cannot be converted into "+datatype);
								break;	
							}
							break;	
						case "String" :
							System.err.println("Operation "+operator+" is undefined for String");
							break;
						case "invalid" :
							System.err.println("Invalid value "+operat[1]+" in the expression");
							break;
						}
						break;	
					case "String" :
						System.err.println("Operation "+operator+" is undefined for String");
						break;
					case "invalid" :
						System.err.println("Invalid value "+operat[0]+" in the expression");
						break;
				}	
						}else if(var_data.containsKey(splitarray[3]))  //initialize with variable name
						{
							String var2 = splitarray[3];
							if(var_val.get(var2).equals("not initialized"))  //var2 not initialized
							{
								System.err.println("Variable "+var2+" is not initialized");
							}else {
								switch(datatype)
								{
								case "int" :
									switch(var_data.get(var2))
									{
									case "int" :
										var_val.put(var, var_val.get(var2)); //assigning value of var2 to var1
										var_data.put(var, datatype);
									case "char" :
										val = (int)var_val.get(var2).charAt(0);
										var_val.put(var, String.valueOf(val));
										var_data.put(var, datatype);
										break;
									case "double" :
									case "String" :
										System.err.println("Invalid datatype assignment "+var_data.get(var2)+" cannot be converted"
												+ " into "+datatype);
										break;
									}
									break;
								case "char" :
									switch (var_data.get(var2)) 
									{
									case "char" :
										var_val.put(var, var_val.get(var2));
										var_data.put(var, datatype);
										break;
									case "int" :
										char t = (char)Integer.parseInt(var_val.get(var2));
										var_val.put(var, t+"" );
										var_data.put(var, datatype);
										break;
									case "double" :
									case "String" :
										System.err.println("Invalid datatype assignment "+var_data.get(var2)+" cannot be converted"
												+ " into "+datatype);
										break;
									}
									break;
								case "String":
									switch(var_data.get(var2))
									{
									case "String" :
										var_val.put(var, var_val.get(var2));
										var_data.put(var, datatype);
										break;
									case "double" :
									case "int" :
									case "char" :
										System.err.println("Invalid datatype assignment "+var_data.get(var2)+" cannot be converted"
												+ " into "+datatype);
										break;
									}
									break;
								case "double" :
									switch(var_data.get(var2))
									{
									case "char" :
										val = (int)var_val.get(var2).charAt(0);
										var_val.put(var, String.valueOf(val)+".0");
										var_data.put(var, datatype);
										break;
									case "int" :
										var_val.put(var, var_val.get(var2)+".0" );
										var_data.put(var, datatype);
										break;
									case "double" :
										var_val.put(var, var_val.get(var2));
										var_data.put(var, datatype);
										break;
									case "String" :
										System.err.println("Invalid datatype assignment "+var_data.get(var2)+" cannot be converted"
												+ " into "+datatype);
										break;
									case "invalid" :
										System.err.println("Invalid datatype assignment ");
										break;
									}
									break;
								
								}	
							}
						}else if(Keywords.contains(splitarray[3]))
						{
							System.err.println("Variable cannot be initialized with keyword");
						}else if (validate_var(splitarray[3])) {
							System.err.println("Variable "+splitarray[3]+" not declared");
						}else {
							returneddata = retdata(splitarray[3]);
							switch(datatype)
							{
							case "int":
								switch(returneddata) {
								case "int" :
									var_val.put(var, splitarray[3]);
									var_data.put(var, datatype);
									break;
								case "char" :
									char[] split2 = splitarray[3].toCharArray();
									val = (int)split2[1];
									var_val.put(var, String.valueOf(val));
									var_data.put(var, datatype);
									break;
								case "double" :
								case "String" :
									System.err.println("Invalid datatype assignment "+returneddata+" cannot be converted"
											+ " into "+datatype);
									break;
								case "invalid" :
									System.err.println("Invalid datatype assignment ");
									break;
								}
								break;
							
							case "char":
								switch(returneddata)
								{
								case "char" :
									char[] split2 = splitarray[3].toCharArray();
									var_val.put(var, split2[1]+"");
									var_data.put(var, datatype);
									break;
								case "int" :
									char t = (char)Integer.parseInt(splitarray[3]);
									var_val.put(var, t+"" );
									var_data.put(var, datatype);
									break;
								case "double" :
								case "String" :
									System.err.println("Invalid datatype assignment "+returneddata+" cannot be converted"
											+ " into "+datatype);
									break;
								case "invalid" :
									System.err.println("Invalid datatype assignment ");
									break;
								}
								break;
							case "String":
								switch(returneddata)
								{
								case "String" :
									String value = splitarray[3].substring(1,splitarray[3].length()-1);
									var_val.put(var, value);
									var_data.put(var, datatype);
									break;
								case "double" :
								case "int" :
								case "char" :
									System.err.println("Invalid datatype assignment "+returneddata+" cannot be converted"
											+ " into "+datatype);
									break;
								case "invalid" :
									System.err.println("Invalid datatype assignment ");
									break;
								}
								break;
							case "double" :
								switch(returneddata)
								{
								case "char" :
									char[] split2 = splitarray[3].toCharArray();
									val = (int)split2[1];
									var_val.put(var, String.valueOf(val)+".0");
									var_data.put(var, datatype);
									break;
								case "int" :
									var_val.put(var, splitarray[3]+".0" );
									var_data.put(var, datatype);
									break;
								case "double" :
									var_val.put(var, splitarray[3]);
									var_data.put(var, datatype);
									break;
								case "String" :
									System.err.println("Invalid datatype assignment "+returneddata+" cannot be converted"
											+ " into "+datatype);
									break;
								case "invalid" :
									System.err.println("Invalid datatype assignment ");
									break;
								}
								break;
							}
							
						}
					}else if(splitarray[2].equals(";"))
					{
						switch(datatype)
						{
						case "int":
							var_val.put(var, "not initialized");
							var_data.put(var, datatype);
							break;
						case "double":
							var_val.put(var, "not initialized");
							var_data.put(var, datatype);
							break;
						case "char" :
							var_val.put(var, "not initialized");
							var_data.put(var, datatype);
							break;
						case "String" :
							var_val.put(var, "not initialized");
							var_data.put(var, datatype);
							break;
						}
					}else {
						System.err.println("Invalid declaration of variable");
					}
				}else if(var_data.containsKey(splitarray[0]))
				{
					var = splitarray[0];
					datatype = var_data.get(var);
					if(splitarray[1].equals("="))
					{
						if(Arrays.toString(splitarray).contains("+"))  // expression
						{
						exprsn = splitarray[2];
						String[] operat = exprsn.split("\\+");
						String op1="",op2="";
						char charval=' ',charval1=' ' ;
						String strval=" ",strval1 = " ";
						boolean isvar1=false,isvar2=false;
						if(Keywords.contains(operat[0]) || Keywords.contains(operat[1])  )
						{
							System.err.println("Invalid expression!! operands should not match with reserved keywords ");
						}else if(var_data.containsKey(operat[0]) && var_data.containsKey(operat[1]))
						{
							if(var_val.get(operat[0]).equals("not initialized"))
							{
								System.err.println("Variable "+operat[0]+ " is not initialized");
							}else if(var_val.get(operat[1]).equals("not initialized"))
							{
								System.err.println("Variable "+operat[1]+ " is not initialized");
							}else {
								op1 = var_data.get(operat[0]);
								op2 = var_data.get(operat[1]);
								operat[0]=var_val.get(operat[0]);
								operat[1]=var_val.get(operat[1]);
								isvar1=true;
								isvar2=true;

								}
						}else if(var_data.containsKey(operat[0]))
						{
							if(var_val.get(operat[0]).equals("not initialized"))
							{
								System.err.println("Variable "+operat[0]+ " is not initialized");
							}else {
								op2 = retdata(operat[1]);
								op1 = var_data.get(operat[0]);
								operat[0] = var_val.get(operat[0]);
								isvar1=true;
								isvar2=false;
							}
						}else if(var_data.containsKey(operat[1]))
						{
							if(var_val.get(operat[1]).equals("not initialized"))
							{
								System.err.println("Variable "+operat[1]+ " is not initialized");
							}else {
								op2 = var_data.get(operat[1]);
								operat[1] = var_val.get(operat[1]);
								op1 = retdata(operat[0]);
								isvar2=true;
								isvar1=false;
							}
						}else if(validate_var(operat[0]))
						{
							System.err.println("Variable "+operat[0]+ " is not declared");
						}else if(validate_var(operat[1]))
						{
							System.err.println("Variable "+operat[1]+ " is not declared");
						}else {
							op1 = retdata(operat[0]);
							isvar1=false;
							op2 = retdata(operat[1]);
							isvar2=false;
						}
						switch(op1)
						{
						case "char" :
							char[] arr = operat[0].toCharArray();
							if(isvar1)
							{
								charval = arr[0];	
							}else {
								charval = arr[1];
							}
							break;
						case "String" :
							if(isvar1)
							{
								strval = operat[0];
							}else {
								strval = operat[0].substring(1,operat[0].length()-1);
							}
							break;
						}
						
						switch(op2)
						{
						case "char" :
							char[] arr = operat[1].toCharArray();
							if(isvar2)
							{
								charval1 = arr[0];	
							}else {
								charval1 = arr[1];
							}
							break;
						case "String" :
							if(isvar2)
							{
								strval1 = operat[1];
							}else {
								strval1 = operat[1].substring(1,operat[1].length()-1);
							}
							break;
						}
						
						switch(op1)
						{
						case "int":
							switch(op2)
							{
							case "int":
								if(datatype.equals("int"))
								{
									var_val.put(var, String.valueOf(Integer.parseInt(operat[0])+Integer.parseInt(operat[1]))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("char"))
								{
									var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])+Integer.parseInt(operat[1])))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("double"))
								{
									var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])+Integer.parseInt(operat[1])))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("String"))
								{
									System.err.println("Invalid datatype assignment int cannot be converted into String");	
								}
								break;
							case "char":
								if(datatype.equals("int"))
								{
									var_val.put(var, String.valueOf(Integer.parseInt(operat[0])+(int)charval1)); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("char"))
								{
									var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])+(int)charval1))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("double"))
								{
									var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])+(int)charval1))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("String"))
								{
									System.err.println("Invalid datatype assignment int cannot be converted into String");	
								}
								break;
							case "String":
								if(datatype.equals("String"))
								{
									var_val.put(var, operat[0]+strval1); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
								}
								break;
							case "double":
								if(datatype.equals("double"))
								{
									var_val.put(var, String.valueOf(Double.parseDouble(operat[0])+Double.parseDouble(operat[1]))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment double cannot be converted into "+datatype);
								}
								break;
							case "invalid":
								System.err.println("Invalid value for expression : "+operat[1]);
								break;
							}
							break;
						case "char":
							switch(op2)
							{
							case "int":
								if(datatype.equals("int"))
								{
									var_val.put(var, String.valueOf(Integer.parseInt(operat[1])+(int)charval)); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("char"))
								{
									var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[1])+(int)charval))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("double"))
								{
									var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[1])+(int)charval))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("String"))
								{
									System.err.println("Invalid datatype assignment int cannot be converted into String");	
								}
								break;
							case "char":
								if(datatype.equals("int"))
								{
									var_val.put(var, String.valueOf((int)charval+(int)charval1)); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("char"))
								{
									var_val.put(var, String.valueOf((char)((int)charval+(int)charval1))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("double"))
								{
									var_val.put(var, String.valueOf((float)((int)charval+(int)charval1))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else if(datatype.equals("String"))
								{
									System.err.println("Invalid datatype assignment int cannot be converted into String");	
								}
								break;
							case "String":
								if(datatype.equals("String"))
								{
									var_val.put(var, strval+strval1); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
								}
								break;
							case "double":
								if(datatype.equals("double"))
								{
									var_val.put(var, String.valueOf((float)((float)charval+Double.parseDouble(operat[1])))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment double cannot be converted into "+datatype);
								}
								break;
							case "invalid":
								System.err.println("Invalid value for expression : "+operat[1]);
								break;
							}
							break;
						case "String":
							switch(op2)
							{
							case "int" :
								if(datatype.equals("String"))
								{
									var_val.put(var, strval+operat[1]); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
								}
								break;
							case "char" :
								if(datatype.equals("String"))
								{
									var_val.put(var, strval+strval1); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
								}
								break;
							case "String" :
								if(datatype.equals("String"))
								{
									var_val.put(var, strval+strval1); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
								}
								break;
							case "double" :
								if(datatype.equals("String"))
								{
									var_val.put(var, strval+operat[1]);
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
								}
								break;
							case "invalid":
								System.err.println("Invalid value for expression : "+operat[1]);
								break;
							}
							break;
						case "double" :
							switch(op2)
							{
							case "int" :
							case "double" :
								if(datatype.equals("double"))
								{
									var_val.put(var, String.valueOf(Double.parseDouble(operat[0])+Double.parseDouble(operat[1]))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment double cannot be converted into "+datatype);
								}
								break;
							case "char":
								if(datatype.equals("double"))
								{
									var_val.put(var, String.valueOf((float)((float)charval1+Double.parseDouble(operat[0])))); //assigning value of var2 to var1
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment double cannot be converted into "+datatype);
								}
								break;
							case "String" :
								if(datatype.equals("String"))
								{
									var_val.put(var, operat[0]+strval1);
									var_data.put(var, datatype);
								}else {
									System.err.println("Invalid datatype assignment String cannot be converted into "+datatype);
								}
								break;
							case "invalid":
								System.err.println("Invalid value for expression : "+operat[1]);
								break;
							}
							break;
						case "invalid" :
							System.err.println("Invalid value for expression : "+operat[0]);
							break;
						}
						}else if(Arrays.toString(splitarray).contains("-") ||
								Arrays.toString(splitarray).contains("/") || Arrays.toString(splitarray).contains("*")
								|| Arrays.toString(splitarray).contains("%")) 
						{
							String operator="";
							exprsn = splitarray[2];
							String[] operat = {"0","0"};
							String op1="",op2="";
							char charval=' ',charval1=' ' ;
							boolean isvar1=false,isvar2=false;
							
							
							if(Arrays.toString(splitarray).contains("-"))
							{
								operator = "-" ;
								operat = exprsn.split("\\-");
							}else if(Arrays.toString(splitarray).contains("/"))
							{
								operator = "/" ;
								operat = exprsn.split("\\/");
							}else if(Arrays.toString(splitarray).contains("*"))
							{
								operator = "*" ;
								operat = exprsn.split("\\*");
							}else if(Arrays.toString(splitarray).contains("%"))
							{
								operator = "%" ;
								operat = exprsn.split("\\%");
							}
							if(Keywords.contains(operat[0]) || Keywords.contains(operat[1])  )
							{
								System.err.println("Invalid expression!! operands should not match with reserved keywords ");
							}else if(var_data.containsKey(operat[0]) && var_data.containsKey(operat[1]))
							{
								if(var_val.get(operat[0]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[0]+ " is not initialized");
								}else if(var_val.get(operat[1]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[1]+ " is not initialized");
								}else {
									op1 = var_data.get(operat[0]);
									op2 = var_data.get(operat[1]);
									operat[0]=var_val.get(operat[0]);
									operat[1]=var_val.get(operat[1]);
									isvar1=true;
									isvar2=true;

									}
							}else if(var_data.containsKey(operat[0]))
							{
								if(var_val.get(operat[0]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[0]+ " is not initialized");
								}else {
									op2 = retdata(operat[1]);
									op1 = var_data.get(operat[0]);
									operat[0] = var_val.get(operat[0]);
									isvar1=true;
									isvar2=false;
								}
							}else if(var_data.containsKey(operat[1]))
							{
								if(var_val.get(operat[1]).equals("not initialized"))
								{
									System.err.println("Variable "+operat[1]+ " is not initialized");
								}else {
									op2 = var_data.get(operat[1]);
									operat[1] = var_val.get(operat[1]);
									op1 = retdata(operat[0]);
									isvar2=true;
									isvar1=false;
								}
							}else if(validate_var(operat[0]))
							{
								System.err.println("Variable "+operat[0]+ " is not declared");
							}else if(validate_var(operat[1]))
							{
								System.err.println("Variable "+operat[1]+ " is not declared");
							}else {
								op1 = retdata(operat[0]);
								isvar1=false;
								op2 = retdata(operat[1]);
								isvar2=false;
							}
							if(op1.equals("char"))
							{
								char[] arr = operat[0].toCharArray();
								if(isvar1)
								{
									charval = arr[0];	
								}else {
									charval = arr[1];
								}
							}
							if(op2.equals("char"))
							{
								char[] arr = operat[1].toCharArray();
								if(isvar2)
								{
									charval1 = arr[0];	
								}else {
									charval1 = arr[1];
								}	
							}
							switch(op1)
							{
							case "int":
								switch(op2)
								{
								case "int" :
									switch(datatype)
									{
									case "int" :
										switch(operator)
										{
										case "-" :
											val = Integer.parseInt(operat[0])-Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf(val));
											var_data.put(var, datatype);
											break;
										case "/" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception division by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])/Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf(val));
												var_data.put(var, datatype);
												break;	
											}
										case "*" :
											val = Integer.parseInt(operat[0])*Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf(val));
											var_data.put(var, datatype);
											break;
										case "%" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception mod by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])%Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf(val));
												var_data.put(var, datatype);
												break;	
											}
										}
										break;
									case "char" :
										switch(operator)
										{
										case "-" :
											val = Integer.parseInt(operat[0])-Integer.parseInt(operat[1]);
											if(val<0)
											{
												System.err.println("int value is invalid to assign for char");
											}else {
												var_val.put(var, String.valueOf((char)val));
												var_data.put(var, datatype);	
											}
											break;
										case "/" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception division by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])/Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf((char)val));
												var_data.put(var, datatype);
												break;	
											}
										case "*" :
											val = Integer.parseInt(operat[0])*Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf((char)val));
											var_data.put(var, datatype);
											break;
										case "%" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception mod by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])%Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf((char)val));
												var_data.put(var, datatype);
												break;	
											}
										}
										break;
									case "double" :
										switch(operator)
										{
										case "-" :
											val = Integer.parseInt(operat[0])-Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf((float)val));
											var_data.put(var, datatype);
											break;
										case "/" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception division by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])/Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf((float)val));
												var_data.put(var, datatype);
												break;	
											}
										case "*" :
											val = Integer.parseInt(operat[0])*Integer.parseInt(operat[1]);
											var_val.put(var, String.valueOf((float)val));
											var_data.put(var, datatype);
											break;
										case "%" :
											if(operat[1].equals("0"))
											{
												System.err.println("Arthemetic exception mod by 0 is invalid");
												break;
											}else {
												val = Integer.parseInt(operat[0])%Integer.parseInt(operat[1]);
												var_val.put(var, String.valueOf((float)val));
												var_data.put(var, datatype);
												break;	
											}
										}
										break;
									case "String" :
										System.err.println("Invalid initialization int cannot be converted into "+datatype);
										break;
									}
									break;
								case "char" :
									
									switch(datatype)
									{
									case "int" :
										switch(operator)
										{
										case "-" :
											var_val.put(var, String.valueOf(Integer.parseInt(operat[0])-(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/" :
											var_val.put(var, String.valueOf(Integer.parseInt(operat[0])/(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*" :
											var_val.put(var, String.valueOf(Integer.parseInt(operat[0])*(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%" :
											var_val.put(var, String.valueOf(Integer.parseInt(operat[0])%(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
										break;
									case "char" :
										switch(operator)
										{
										case "-" :
											val = Integer.parseInt(operat[0])-(int)charval1;
											if(val<0)
											{
												System.err.println("int value is invalid to assign for char");
												break;
											}else {
												var_val.put(var, String.valueOf((char)val)); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;	
											}
										case "/":
											var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])/(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*":
											var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])*(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((char)(Integer.parseInt(operat[0])%(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
										break ;
									case "double" :
										switch(operator)
										{
										case "-" :
											var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])-(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])/(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*":
											var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])*(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((float)(Integer.parseInt(operat[0])%(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
									case "String" :
										System.err.println("Invalid initialization int cannot be converted into "+datatype);
										break;
									}
									break;
								case "double" :
									if(datatype.equals("double"))
									{
										switch(operator)
										{
										case "-" :
											var_val.put(var, String.valueOf(Double.parseDouble(operat[0])-Double.parseDouble(operat[1]))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											if(operat[1].equals("0.0"))
											{
												System.err.println("Arthemetic exception division by 0.0 is invalid");
											}else {
												var_val.put(var, String.valueOf(Double.parseDouble(operat[0])/Double.parseDouble(operat[1]))); //assigning value of var2 to var1
												var_data.put(var, datatype);	
											}
											break;
										case "*":
											var_val.put(var, String.valueOf(Double.parseDouble(operat[0])*Double.parseDouble(operat[1]))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											if(operat[1].equals("0.0"))
											{
												System.err.println("Arthemetic exception mod by 0.0 is invalid");	
											}else {
												var_val.put(var, String.valueOf(Double.parseDouble(operat[0])%Double.parseDouble(operat[1]))); //assigning value of var2 to var1
												var_data.put(var, datatype);
											}
											break;
										}
									}else {
										System.err.println("Invalid initialization double cannot be converted into "+datatype);
										break;
									}
									break ;
								case "String" :
									System.err.println("Operation "+operator+" is undefined for String");
									break;
								case "invalid" :
									System.err.println("Invalid value "+operat[1]+" in the expression");
									break;
								}
								break ;
							case "char" :
								switch(op2)
								{
								case "int" :
									switch(datatype)
									{
									case "int" :
											switch(operator)
											{
											case "-" :
												var_val.put(var, String.valueOf((int)charval-Integer.parseInt(operat[1]))); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;
											case "/" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception division by 0 is invalid");
													break;
												}else {
													var_val.put(var, String.valueOf((int)charval/Integer.parseInt(operat[1]))); //assigning value of var2 to var1
													var_data.put(var, datatype);
													break;	
												}
											case "*" :
												var_val.put(var, String.valueOf(Integer.parseInt(operat[1])*(int)charval)); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;
											case "%" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception mod by 0 is invalid");
													break;
												}else {
													var_val.put(var, String.valueOf((int)charval%Integer.parseInt(operat[1]))); //assigning value of var2 to var1
													var_data.put(var, datatype);
													break;	
												}
											}
											break;
									case "char" :
											switch(operator)
											{
											case "-" :
												val = (int)charval - Integer.parseInt(operat[1]);
												if(val<0)
												{
													System.err.println("Invalid int value");
												}else {
													var_val.put(var, String.valueOf((char)val));
													var_data.put(var, datatype);
												}
												break;
											case "*" :
												var_val.put(var, String.valueOf((char)((int)charval*Integer.parseInt(operat[1]))));
												var_data.put(var, datatype);
												break;
											case "/" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception division with 0 is invalid");
												}else {
													var_val.put(var, String.valueOf((char)((int)charval/Integer.parseInt(operat[1]))));
													var_data.put(var, datatype);
												}
												break;
											case "%" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception mod with 0 is invalid");
												}else {
													var_val.put(var, String.valueOf((char)((int)charval%Integer.parseInt(operat[1]))));
													var_data.put(var, datatype);
												}
											}
											break;
									case "double" :
											switch(operator)
											{
											case "-" :
												var_val.put(var, String.valueOf((float)((int)charval-Integer.parseInt(operat[1])))); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;
											case "/" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception division by 0 is invalid");
													break;
												}else {
													var_val.put(var, String.valueOf((float)((int)charval/Integer.parseInt(operat[1])))); //assigning value of var2 to var1
													var_data.put(var, datatype);
													break;	
												}
											case "*" :
												var_val.put(var, String.valueOf((float)((int)charval*Integer.parseInt(operat[1])))); //assigning value of var2 to var1
												var_data.put(var, datatype);
												break;
											case "%" :
												if(operat[1].equals("0"))
												{
													System.err.println("Arthemetic exception mod by 0 is invalid");
													break;
												}else {
													var_val.put(var, String.valueOf((float)((int)charval%Integer.parseInt(operat[1])))); //assigning value of var2 to var1
													var_data.put(var, datatype);
													break;	
												}
											}
											break;
									case "String" :
											System.err.println("Invalid initialization int cannot be converted into "+datatype);
											break;
									}
										break;
								case "char" :
									switch(datatype)
									{
									case "int" :
										switch(operator)
										{
										case "-":
											var_val.put(var, String.valueOf((int)charval-(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											var_val.put(var, String.valueOf((int)charval/(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*":
											var_val.put(var, String.valueOf((int)charval*(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((int)charval%(int)charval1)); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
										break;
									case "char":
										switch(operator)
										{
										case "-" :
											val = (int)charval-(int)charval1;
											if(val<0)
											{
												System.err.println("int value is invalid to assign for char");
											}else {
												var_val.put(var, String.valueOf((char)val)); //assigning value of var2 to var1
												var_data.put(var, datatype);	
											}
											break;
										case "*":
											var_val.put(var, String.valueOf((char)((int)charval*(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											var_val.put(var, String.valueOf((char)((int)charval/(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((char)((int)charval%(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
									case "double":
										switch(operator)
										{
										case "-":
											var_val.put(var, String.valueOf((float)((int)charval-(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "*":
											var_val.put(var, String.valueOf((float)((int)charval*(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "/":
											var_val.put(var, String.valueOf((float)((int)charval/(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										case "%":
											var_val.put(var, String.valueOf((float)((int)charval%(int)charval1))); //assigning value of var2 to var1
											var_data.put(var, datatype);
											break;
										}
										break;
									case "String" :
										System.err.println("Invalid initialization int cannot be converted into "+datatype);
										break;
									}
									break;
							case "double" :
								switch(datatype)
								{
								case "double" :
									switch(operator)
									{
									case "-":
										var_val.put(var,String.valueOf((float)charval-Double.parseDouble(operat[1])));
										var_data.put(var,datatype);
										break;
									case "/":
										if(operat[1].equals("0.0"))
										{
											System.err.println("Arthemetic exception division by 0.0 is invalid");
										}else {
											var_val.put(var,String.valueOf((float)charval/Double.parseDouble(operat[1])));
											var_data.put(var,datatype);
											break;	
										}
										break;
									case "%" :
										if(operat[1].equals("0.0"))
										{
											System.err.println("Arthemetic exception mod by 0.0 is invalid");
										}else {
											var_val.put(var,String.valueOf((float)charval%Double.parseDouble(operat[1])));
											var_data.put(var,datatype);
											break;	
										}
										break;
									case "*":
										var_val.put(var,String.valueOf((float)charval*Double.parseDouble(operat[1])));
										var_data.put(var,datatype);
										break;
									}
									break;//double
								default :
									System.err.println("Invalid initialization double cannot be converted into "+datatype);
									break;
								}
								break;
							case "String" :
								System.err.println("Operation "+operator+" is undefined for String");
								break;
							case "invalid" :
								System.err.println("Invalid value "+operat[1]+" in the expression");
								break;
								}
								break;
					case "double" :
						switch(op2)
						{
						case "int":
							switch(datatype)
							{
							case "double" :
								switch(operator)
								{
								case "-" :
									var_val.put(var,String.valueOf((float)(Double.parseDouble(operat[0])-Double.parseDouble(operat[1]))));
									var_data.put(var, datatype);
									break;
								case "/":
									if(operat[1].equals("0"))
									{
										System.err.println("Arthemetic exception division by 0 is invalid");
									}else {
										var_val.put(var,String.valueOf((float)(Double.parseDouble(operat[0])/Double.parseDouble(operat[1]))));
										var_data.put(var, datatype);
									}
									break;
								case "%" :
									if(operat[1].equals("0"))
									{
										System.err.println("Arthemetic exception mod by 0 is invalid");
									}else {
										var_val.put(var,String.valueOf((float)(Double.parseDouble(operat[0])%Double.parseDouble(operat[1]))));
										var_data.put(var, datatype);
									}
									break;
								case "*" :
									var_val.put(var,String.valueOf((float)(Double.parseDouble(operat[0])*Double.parseDouble(operat[1]))));
									var_data.put(var, datatype);
									break;
								}
								break;
							default :
								System.err.println("Invalid initialization double cannot be converted into "+datatype);
								break;
							}
							break;
						case "char" :
							switch(datatype)
							{
							case "double":
								switch(operator)
								{
								case "-" :
									double val2 = Double.parseDouble(operat[0])-(float)charval1;
									if(val2<0.0)
									{
										System.err.println("double value is invalid to assign for char");	
									}else {
										var_val.put(var, String.valueOf((float)(val2)));
										var_data.put(var, datatype);	
									}
									break;
								case "/" :
									var_val.put(var, String.valueOf((float)(Double.parseDouble(operat[0])/(float)charval1)));
									var_data.put(var, datatype);
									break;
								case "%" :
									var_val.put(var, String.valueOf((float)(Double.parseDouble(operat[0])%(float)charval1)));
									var_data.put(var, datatype);
									break;
								case "*" :
									var_val.put(var, String.valueOf((float)(Double.parseDouble(operat[0])*(float)charval1)));
									var_data.put(var, datatype);
									break;
								}
								break;
							default :
								System.err.println("Invalid initialization double cannot be converted into "+datatype);
								break;	
							}
							break ;
						case "double" :
							switch(datatype)
							{
							case "double":
								switch(operator)
								{
								case "-" :
									var_val.put(var, String.valueOf(Double.parseDouble(operat[0])-Double.parseDouble(operat[1])));
									var_data.put(var, datatype);
									break;
								case "/" :
									if(operat[1].equals("0.0"))
									{
										System.err.println("Arthemetic exception division by 0.0 is invalid");
									}else {
										var_val.put(var, String.valueOf(Double.parseDouble(operat[0])/Double.parseDouble(operat[1])));
										var_data.put(var, datatype);	
									}
									break;
								case "%" :
									if(operat[1].equals("0.0"))
									{
										System.err.println("Arthemetic exception mod by 0.0 is invalid");
									}else {
										var_val.put(var, String.valueOf(Double.parseDouble(operat[0])%Double.parseDouble(operat[1])));
										var_data.put(var, datatype);	
									}
									break;
								case "*" :
									var_val.put(var, String.valueOf(Double.parseDouble(operat[0])*Double.parseDouble(operat[1])));
									var_data.put(var, datatype);
									break;
								}
								break;
							default :
								System.err.println("Invalid initialization double cannot be converted into "+datatype);
								break;	
							}
							break;	
						case "String" :
							System.err.println("Operation "+operator+" is undefined for String");
							break;
						case "invalid" :
							System.err.println("Invalid value "+operat[1]+" in the expression");
							break;
						}
						break;	
					case "String" :
						System.err.println("Operation "+operator+" is undefined for String");
						break;
					case "invalid" :
						System.err.println("Invalid value "+operat[0]+" in the expression");
						break;
				}	
	
						}else if(var_data.containsKey(splitarray[2]))  //initialize with variable name
						{
							String var2 = splitarray[2];
							if(var_val.get(var2).equals("not initialized"))  //var2 not initialized
							{
								System.err.println("Variable "+var2+" is not initialized");
							}else {
								switch(datatype)
								{
								case "int" :
									switch(var_data.get(var2))
									{
									case "int" :
										var_val.put(var, var_val.get(var2)); //assigning value of var2 to var1
										var_data.put(var, datatype);
									case "char" :
										val = (int)var_val.get(var2).charAt(0);
										var_val.put(var, String.valueOf(val));
										var_data.put(var, datatype);
										break;
									case "double" :
									case "String" :
										System.err.println("Invalid datatype assignment "+var_data.get(var2)+" cannot be converted"
												+ " into "+datatype);
										break;
									}
									break;
								case "char" :
									switch (var_data.get(var2)) 
									{
									case "char" :
										var_val.put(var, var_val.get(var2));
										var_data.put(var, datatype);
										break;
									case "int" :
										char t = (char)Integer.parseInt(var_val.get(var2));
										var_val.put(var, t+"" );
										var_data.put(var, datatype);
										break;
									case "double" :
									case "String" :
										System.err.println("Invalid datatype assignment "+var_data.get(var2)+" cannot be converted"
												+ " into "+datatype);
										break;
									}
									break;
								case "String":
									switch(var_data.get(var2))
									{
									case "String" :
										var_val.put(var, var_val.get(var2));
										var_data.put(var, datatype);
										break;
									case "double" :
									case "int" :
									case "char" :
										System.err.println("Invalid datatype assignment "+var_data.get(var2)+" cannot be converted"
												+ " into "+datatype);
										break;
									}
									break;
								case "double" :
									switch(var_data.get(var2))
									{
									case "char" :
										char[] split2 = var_val.get(var2).toCharArray();
										val = (int)var_val.get(var2).charAt(0);
										var_val.put(var, String.valueOf(val)+".0");
										var_data.put(var, datatype);
										break;
									case "int" :
										var_val.put(var, var_val.get(var2)+".0" );
										var_data.put(var, datatype);
										break;
									case "double" :
										var_val.put(var, var_val.get(var2));
										var_data.put(var, datatype);
										break;
									case "String" :
										System.err.println("Invalid datatype assignment "+var_data.get(var2)+" cannot be converted"
												+ " into "+datatype);
										break;
									case "invalid" :
										System.err.println("Invalid datatype assignment ");
										break;
									}
									break;
								
								}	
							}
						}else if(Keywords.contains(splitarray[2]))
						{
							System.err.println("Variable cannot be initialized with keyword");
						}else if (validate_var(splitarray[2])) {
							System.err.println("Variable "+splitarray[2]+" not declared");
						}else {
							returneddata = retdata(splitarray[2]);
							switch(datatype)
							{
							case "int":
								switch(returneddata) {
								case "int" :
									var_val.put(var, splitarray[2]);
									var_data.put(var, datatype);
									break;
								case "char" :
									char[] split2 = splitarray[2].toCharArray();
									val = (int)split2[1];
									var_val.put(var, String.valueOf(val));
									var_data.put(var, datatype);
									break;
								case "double" :
								case "String" :
									System.err.println("Invalid datatype assignment "+returneddata+" cannot be converted"
											+ " into "+datatype);
									break;
								case "invalid" :
									System.err.println("Invalid datatype assignment ");
									break;
								}
								break;
							
							case "char":
								switch(returneddata)
								{
								case "char" :
									char[] split2 = splitarray[2].toCharArray();
									var_val.put(var, split2[1]+"");
									var_data.put(var, datatype);
									break;
								case "int" :
									char t = (char)Integer.parseInt(splitarray[2]);
									var_val.put(var, t+"" );
									var_data.put(var, datatype);
									break;
								case "double" :
								case "String" :
									System.err.println("Invalid datatype assignment "+returneddata+" cannot be converted"
											+ " into "+datatype);
									break;
								case "invalid" :
									System.err.println("Invalid datatype assignment ");
									break;
								}
								break;
							case "String":
								switch(returneddata)
								{
								case "String" :
									String value = splitarray[2].substring(1,splitarray[2].length()-1);
									var_val.put(var, value);
									var_data.put(var, datatype);
									break;
								case "double" :
								case "int" :
								case "char" :
									System.err.println("Invalid datatype assignment "+returneddata+" cannot be converted"
											+ " into "+datatype);
									break;
								case "invalid" :
									System.err.println("Invalid datatype assignment ");
									break;
								}
								break;
							case "double" :
								switch(returneddata)
								{
								case "char" :
									char[] split2 = splitarray[2].toCharArray();
									val = (int)split2[1];
									var_val.put(var, String.valueOf(val)+".0");
									var_data.put(var, datatype);
									break;
								case "int" :
									var_val.put(var, splitarray[2]+".0" );
									var_data.put(var, datatype);
									break;
								case "double" :
									var_val.put(var, splitarray[2]);
									var_data.put(var, datatype);
									break;
								case "String" :
									System.err.println("Invalid datatype assignment "+returneddata+" cannot be converted"
											+ " into "+datatype);
									break;
								case "invalid" :
									System.err.println("Invalid datatype assignment ");
									break;
								}
								break;
							}
						}
					}else {
						System.err.println("INVALID line!!\nInsert values to complete variable declaration");
					}
				
				}else if(str.equals("stop"))
				{
					System.out.println("Variable\tdatatype\tvalue");
					for(String i : var_data.keySet())
					{
						System.out.println(i+"\t\t"+var_data.get(i)+"\t\t"+var_val.get(i));
					}
					break;
				}else if(Keywords.contains(splitarray[0])) 
				{
					System.err.println("Keywords can't be used");
				}else if(validate_var(splitarray[0]))
				{
					System.err.println("Variable "+splitarray[0]+" not declared ");
				}else
				{
					System.err.println("INVALID line!!");
				}
			}
		}else {
			System.err.println("INVALID!!required start statement");
		}
	}
}
