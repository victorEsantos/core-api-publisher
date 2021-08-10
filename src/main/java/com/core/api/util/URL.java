package com.core.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class URL
{
	private static Logger LOG = LoggerFactory.getLogger(URL.class);

	public static List<UUID> decodeStringList(String s)
	{

		String[] stringList = s.split(",");
		List<UUID> uuidList = new ArrayList<>();
		for (String uniqueValue : stringList)
		{
			uuidList.add(UUID.fromString(uniqueValue));
		}

		return uuidList;
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
