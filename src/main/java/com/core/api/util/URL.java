package com.core.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL
{
	private static Logger LOG = LoggerFactory.getLogger(URL.class);

	public static List<Integer> decodeIntList(String s)
	{

		String[] intList = s.split(",");
		List<Integer> integerList = new ArrayList<>();
		for (String uniqueValue : intList)
		{
			integerList.add(Integer.parseInt(uniqueValue));
		}

		return integerList;
	}

	public static String decodeString(String string)
	{
		try
		{
			return URLDecoder.decode(string, "UTF-8");

		}
		catch (UnsupportedEncodingException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.error("Error to decode this string");
			}

			return "";
		}
	}
}
