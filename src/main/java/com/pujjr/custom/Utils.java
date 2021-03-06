package com.pujjr.custom;

import java.io.File;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;

import com.pujjr.domain.SysAccount;
import com.pujjr.domain.SysAccountFeatureKey;

public class Utils {
	/**
	 * 获取日期天数
	 * **/
	public static int getSpaceDay(Date beginDate,Date endDate)
	{
		return (int) ((endDate.getTime()-beginDate.getTime())/(24*60*60*1000)+1);
	}
	/**
	 * 按照指定格式获取当前日期
	 * **/
	public static String getCurrentTime(String format)
	{
		if(format==null||format==""||format.length()==0)
		{
			format="yyyyMMddHHmmss";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
		return df.format(new Date());
	}
	/**
	 * 根据日期获取年份
	 * **/
	public static String getYear(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
		return df.format(date);
	}
	/**
	 * 根据日期获取月份
	 * **/
	public static String getMonth(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("MM");//设置日期格式
		return df.format(date);
	}
	/**
	 * 日期转字符串
	 * **/
	public static String getFormatDate(Date date,String format)
	{
		SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
		return df.format(date);
	}
	/**
	 * 根据年份获取当年天数
	 * **/
	public static int getYearDays(String year)
	{
		if((Integer.valueOf(year)/4==0&&Integer.valueOf(year)/100!=0)||(Integer.valueOf(year)/400==0))
		{
			return 366;
		}
		else
		{
			return 365;
		}
	}
	/**
	 * 8日期字符串转日期格式
	 * @throws ParseException 
	 * **/
	public static Date str82date(String date) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		return df.parse(date);
	}
	public static Timestamp str2time(String time) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");//设置日期格式
		return new Timestamp((df.parse(time)).getTime());
	}
	public static String get16UUID()
	{
		String uuid=UUID.randomUUID().toString();
		byte[] outputByteArray;
		 try {
			MessageDigest messageDigest =MessageDigest.getInstance("MD5");
			byte[] inputByteArray=uuid.getBytes();
			messageDigest.update(inputByteArray);
			outputByteArray=messageDigest.digest();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} 
		 StringBuffer buf = new StringBuffer("");
         for (int offset = 0; offset < outputByteArray.length; offset++) {
            int  i = outputByteArray[offset];
             if (i < 0)
                 i += 256;
             if (i < 16)
                 buf.append("0");
             buf.append(Integer.toHexString(i));
         }
        return buf.toString().substring(8,24);
	}
	
	public static String getFileSuffix(String fileName)
	{
		int indexSuffix;
		if((indexSuffix=fileName.lastIndexOf("."))!=-1)
		{
			return fileName.substring(indexSuffix, fileName.length());
		}
		return fileName;
	}
	public static String getFileName(String filePath)
	{
		int indexSlash;
		if((indexSlash=filePath.lastIndexOf(File.separator))!=-1)
		{
			return filePath.substring(indexSlash+1, filePath.length());
		}
		return filePath;
	}
	public static ArrayList<String> getAccoutTypeListByFeatures(List<SysAccountFeatureKey> busifeatures)
	{
		ArrayList<String> typelist=new ArrayList<String>();
		for(int i=0;i<busifeatures.size();i++)
		{
			SysAccountFeatureKey feature=busifeatures.get(i);
			if(feature.getType().equals("1"))
			{
				if(feature.getValue().equals("1"))
				{
					typelist.add(AccountType.PUBLIC_CURRENT_ACCOUNT);
					typelist.add(AccountType.PUBLIC_FIXED_ACCOUNT);
					typelist.add(AccountType.PUBLIC_CREDIT_ACCOUNT);
					typelist.add(AccountType.PUBLIC_FINANCE_ACCOUNT);
				}
				else
				{
					typelist.add(AccountType.PERSON_CURRENT_ACCOUNT);
					typelist.add(AccountType.PERSON_FINANCE_ACCOUNT);
					typelist.add(AccountType.PERSON_FIXED_ACCOUNT);
					typelist.add(AccountType.PERSON_PLEDGE_ACCOUNT);
				}
			}
		}
		return typelist;
	}
	public static ArrayList<String> getFinanceAccoutTypeListByFeatures(List<SysAccountFeatureKey> busifeatures)
	{
		ArrayList<String> typelist=new ArrayList<String>();
		typelist.add(AccountType.PUBLIC_FINANCE_ACCOUNT);
		typelist.add(AccountType.PERSON_FINANCE_ACCOUNT);
		/*
		for(int i=0;i<busifeatures.size();i++)
		{
			SysAccountFeatureKey feature=busifeatures.get(i);
			if(feature.getType().equals("1"))
			{
				if(feature.getValue().equals("1"))
				{
					typelist.add(AccountType.PUBLIC_FINANCE_ACCOUNT);
				}
				else
				{
					typelist.add(AccountType.PERSON_FINANCE_ACCOUNT);
				}
			}
		}*/
		return typelist;
	}
	

	
	public static boolean isAuthorQueryAllBranchData(SysAccount sysAccount)
	{
		switch(Integer.valueOf(sysAccount.getProperty()))
		{
			case  1:
			case  6:
				return true;
			case  3:
			case  4:
			case  5:
			case  2:
				return false;
			default:
				return false;
		}
	}
	
	public static boolean isAuthorQueryOneSelfData(SysAccount sysAccount)
	{
		switch(Integer.valueOf(sysAccount.getProperty()))
		{
			case  1:
			case  2:
			case  6:
				return false;
			case  3:
			case  4:
			case  5:
				return true;
			default:
				return true;
		}
	}
	
	public static String getCellValue(Cell cell)
	{
		switch (cell.getCellType())
		{
		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC:
			short format=cell.getCellStyle().getDataFormat(); 
			if(format == 14 || format == 31 || format == 57 || format == 58)
			{
				double value = cell.getNumericCellValue();  
			    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value); 
			    return Utils.getFormatDate(date,"yyyy年MM月dd日");
			}
//			if (DateUtil.isCellDateFormatted(cell))
//			{
//				Date date=cell.getDateCellValue();
//				return Utils.getFormatDate(date,"yyyyMMdd");
//			}
			else
			{
				return NumberToTextConverter.toText(cell.getNumericCellValue());
				//BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
				//return bd.toPlainString();
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}
}
